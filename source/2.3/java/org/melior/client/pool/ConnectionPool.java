/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.client.pool;
import java.util.concurrent.TimeUnit;
import org.melior.client.core.ClientConfig;
import org.melior.client.core.Connection;
import org.melior.client.core.ConnectionFactory;
import org.melior.client.exception.RemotingException;
import org.melior.logging.core.Logger;
import org.melior.logging.core.LoggerFactory;
import org.melior.service.core.ServiceState;
import org.melior.util.collection.BlockingQueue;
import org.melior.util.collection.Queue;
import org.melior.util.number.Clamp;
import org.melior.util.number.Counter;
import org.melior.util.object.ObjectUtil;
import org.melior.util.semaphore.Semaphore;
import org.melior.util.thread.DaemonThread;
import org.melior.util.thread.ThreadControl;
import org.melior.util.time.Timer;

/**
 * Implements a pool of {@code Connection} objects.
 * <p>
 * The pool adds additional {@code Connection} objects as and when demand requires,
 * but at the same time employs elision logic to ensure that the pool does not add
 * surplus {@code Connection} objects when the demand subsides following a surge.
 * <p>
 * The pool may also be configured to be bounded, in which case the pool will not
 * exceed its bounds when demand surges or subsides.
 * @author Melior
 * @since 2.3
 */
public class ConnectionPool<C extends ClientConfig, T extends Connection<C, T, P>, P>{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private C configuration;

    private ConnectionFactory<C, T, P> connectionFactory;

    private String poolId;

    private ThreadLocal<T> threadIndex;

    private Counter totalConnections;

    private BlockingQueue<T> availableConnectionQueue;

    private Counter connectionsSupply;

    private Semaphore demandSemaphore;

    private long activeConnectionsCeiling;

    private BlockingQueue<T> retireConnectionQueue;

    private Throwable lastException;

    private long lastExceptionTime;

    private long backoffPeriod;

    private Counter churnedConnections;

    private long lastPruneTime;

  /**
   * Constructor.
   * @param configuration The configuration
   * @param connectionFactory The connection factory
   */
  public ConnectionPool(
    final C configuration,
    final ConnectionFactory<C, T, P> connectionFactory){
        super();

        this.configuration = configuration;

        this.connectionFactory = connectionFactory;

        poolId = ObjectUtil.coalesce(configuration.getName(), String.valueOf(this.hashCode()));

        threadIndex = new ThreadLocal<T>();

        totalConnections = Counter.of(0);

        availableConnectionQueue = Queue.ofBlocking();

        connectionsSupply = Counter.of(0);

        demandSemaphore = Semaphore.of();

        activeConnectionsCeiling = 0;

        retireConnectionQueue = Queue.ofBlocking();

        lastException = null;

        lastExceptionTime = 0;

        backoffPeriod = 0;

        churnedConnections = Counter.of(0);

        lastPruneTime = System.currentTimeMillis();

        resizePool();

        DaemonThread.create(() -> openNewConnections());

        DaemonThread.create(() -> pruneExpiredConnections());

        DaemonThread.create(() -> retireConnections());
  }

  /**
   * Get connection pool identifier.
   * @return The connection pool identifier
   */
  public final String getPoolId(){
    return poolId;
  }

  /**
   * Get total number of connections.
   * @return The total number of connections
   */
  public long getTotalConnections(){
    return totalConnections.get();
  }

  /**
   * Get number of active connections.
   * @return The number of active connections
   */
  public long getActiveConnections(){
    return totalConnections.get() - availableConnectionQueue.size();
  }

  /**
   * Get number of connections in deficit.
   * @return The number of connections in deficit
   */
  public long getConnectionDeficit(){
    return Math.abs(Clamp.clampLong(connectionsSupply.get(), Integer.MIN_VALUE, 0));
  }

  /**
   * Get number of churned connections.
   * @return The number of churned connections
   */
  public long getChurnedConnections(){
    return churnedConnections.get();
  }

  /**
   * Get connection from pool.
   * @return The connection
   * @throws RemotingException if unable to get a connection
   */
  public P getConnection() throws RemotingException{
        String methodName = "getConnection";
    boolean reuse;
    T connection;
    Timer timer;

        reuse = true;

        connection = threadIndex.get();

        if (connection == null){
            reuse = false;

            connectionsSupply.decrement();

            timer = Timer.ofMillis().start();

            while (true){

        try{
                    connection = availableConnectionQueue.remove(1, TimeUnit.MILLISECONDS);

                    if (connection == null){
                        demandSemaphore.release();

            logger.debug(methodName, "Wait for connection to become available.");

                        connection = availableConnectionQueue.remove((configuration.getConnectionTimeout()) - timer.elapsedTime(), TimeUnit.MILLISECONDS);
          }

        }
        catch (Exception exception){
                    connectionsSupply.increment();

          throw new RemotingException("Failed to get connection: " + exception.getMessage(), exception);
        }

                if (connection == null){
                    connectionsSupply.increment();

          throw new RemotingException("Timed out waiting for connection.");
        }

                if (connection.isValid(configuration.isValidateOnBorrow()) == false){
          logger.debug(methodName, "Connection [", connection.getConnectionDescriptor(), "] is no longer valid and is being retired.");

                    connectionsSupply.decrement();

                    churnedConnections.increment();

                    totalConnections.decrement();

                    retireConnectionQueue.add(connection);
        }
                else if (connection.isEndOfLife() == true){
          logger.debug(methodName, "Connection [", connection.getConnectionDescriptor(), "] has reached end-of-life and is being retired.");

                    connectionsSupply.decrement();

                    totalConnections.decrement();

                    retireConnectionQueue.add(connection);
        }
        else{
          break;
        }

      }

    }

        connection.allocate(Thread.currentThread());

        activeConnectionsCeiling = Math.max(totalConnections.get() - availableConnectionQueue.size(), activeConnectionsCeiling);

        threadIndex.set(connection);

    logger.debug(methodName, "Connection [", connection.getConnectionDescriptor(), ", reuse=", reuse, "] allocated.");

    return connection.getProxy();
  }

  /**
   * Release connection into connection pool.
   * @param connection The connection to release
   * @throws RemotingException if unable to release the connection
   */
  public void releaseConnection(
    final T connection) throws RemotingException{
        String methodName = "releaseConnection";

        threadIndex.set(null);

        connection.release(Thread.currentThread());

        if (connection.isValid(false) == false){
      logger.debug(methodName, "Connection [", connection.getConnectionDescriptor(), "] is no longer valid and is being retired.");

            churnedConnections.increment();

            totalConnections.decrement();

            retireConnectionQueue.add(connection);
    }
    else{
            connectionsSupply.increment();

            availableConnectionQueue.add(connection);

      logger.debug(methodName, "Connection [", connection.getConnectionDescriptor(), "] released.");
    }

  }

  /**
   * Open new connections.
   */
  protected void openNewConnections(){
        String methodName = "openNewConnections";
    long remainingBackoff;
    T connection;

        while (ServiceState.isActive() == true){

      try{
                demandSemaphore.acquire();

                while (((connectionsSupply.get() < 0)
          || (totalConnections.get() < configuration.getMinimumConnections()))
          && (totalConnections.get() < configuration.getMaximumConnections())){

                    if (lastException != null){
                        remainingBackoff = backoffPeriod - (System.currentTimeMillis() - lastExceptionTime);

                        if (remainingBackoff > 0){
              logger.debug(methodName, "Backing off for ", (remainingBackoff / 1000), " seconds.");

                            ThreadControl.sleep(remainingBackoff, TimeUnit.MILLISECONDS);

              continue;
            }

          }

          try{
                        connection = connectionFactory.createConnection(configuration, this);

                        totalConnections.increment();

                        connectionsSupply.increment();

                        availableConnectionQueue.add(connection);

                        lastException = null;

                        backoffPeriod = 0;
          }
          catch (Exception exception){
            logger.error(methodName, "Failed to open connection: ", exception.getMessage(), exception);

                        captureException(exception);

                        backoffPeriod = (backoffPeriod == 0) ? configuration.getBackoffPeriod() : Clamp.clampLong(
              (long) (backoffPeriod * configuration.getBackoffMultiplier()), 0, configuration.getBackoffLimit());
          }

        }

      }
      catch (Exception exception){
        logger.error(methodName, "Failed to open new connections: ", exception.getMessage(), exception);
      }

    }

  }

  /**
   * Periodically prune expired connections.
   */
  public void pruneExpiredConnections(){
        String methodName = "pruneExpiredConnections";
    T connection;

        while ((ServiceState.isActive() == true) && (configuration.getInactivityTimeout() > 0) && (configuration.getPruneInterval() > 0)){

      try{

                if ((System.currentTimeMillis() - lastPruneTime) > configuration.getInactivityTimeout()){
                    lastPruneTime = System.currentTimeMillis();

                    while (totalConnections.get() > Math.max(configuration.getMinimumConnections(), activeConnectionsCeiling)){
                        connection = availableConnectionQueue.remove(1, TimeUnit.MILLISECONDS);

                        if (connection == null){
              break;
            }

            logger.debug(methodName, "Connection [", connection.getConnectionDescriptor(), "] has expired and is being retired.");

                        connectionsSupply.decrement();

                        totalConnections.decrement();

                        retireConnectionQueue.add(connection);
          }

                    activeConnectionsCeiling = 0;
        }

                ThreadControl.wait(this, configuration.getPruneInterval(), TimeUnit.MILLISECONDS);
      }
      catch (Exception exception){
        logger.error(methodName, "Failed to prune expired connections: ", exception.getMessage(), exception);
      }

    }

  }

  /**
   * Retire connections.
   */
  private void retireConnections(){
        String methodName = "retireConnections";
    T connection;

        while (ServiceState.isActive() == true){

      try{
                connection = retireConnectionQueue.remove();

                connectionFactory.destroyConnection(connection);
      }
      catch (Exception exception){
        logger.error(methodName, "Failed to retire connections: ", exception.getMessage(), exception);
      }

    }

  }

  /**
   * Resize connection pool to fit dimensions.
   */
  public void resizePool(){
        String methodName = "resizePool";

    try{

            if (totalConnections.get() < configuration.getMinimumConnections()){
        logger.debug(methodName, "Connection pool [", poolId, "] resized to fit dimensions.");

                demandSemaphore.release();
      }

    }
    catch (Exception exception){
      logger.error(methodName, "Failed to resize connection pool: ", exception.getMessage(), exception);
    }

  }

  /**
   * Capture last exception.
   * @param exception The last exception
   */
  void captureException(
    final Throwable exception){
        lastException = exception;

        lastExceptionTime = System.currentTimeMillis();
  }

}
