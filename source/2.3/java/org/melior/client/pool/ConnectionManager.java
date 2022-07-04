/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.client.pool;
import org.melior.client.core.ClientConfig;
import org.melior.client.core.Connection;
import org.melior.client.core.ConnectionFactory;
import org.melior.client.exception.RemotingException;
import org.melior.logging.core.Logger;
import org.melior.logging.core.LoggerFactory;

/**
 * Base class for objects that manage connections on behalf of
 * remoting clients.  The connection manager uses a connection
 * pool for improved performance.
 * @author Melior
 * @since 2.3
 */
public class ConnectionManager<C extends ClientConfig, T extends Connection<C, T, P>, P>{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private C configuration;

    private ConnectionFactory<C, T, P> connectionFactory;

    private ConnectionPool<C, T, P> connectionPool;

  /**
   * Constructor.
   * @param configuration The client configuration
   * @param connectionFactory The connection factory
   */
  public ConnectionManager(
    final C configuration,
    final ConnectionFactory<C, T, P> connectionFactory){
        super();

        this.configuration = configuration;

        this.connectionFactory = connectionFactory;
  }

  /**
   * Initialize connection manager.
   * @throws RemotingException if unable to initialize the connection manager
   */
  public void initialize() throws RemotingException{

        if (connectionPool != null){
      return;
    }

    try{
            connectionPool = new ConnectionPool<C, T, P>(configuration, connectionFactory);
    }
    catch (Exception exception){
      throw new RemotingException("Failed to create connection pool: " + exception.getMessage(), exception);
    }

  }

  /**
   * Get connection from pool.
   * @return The connection
   * @throws RemotingException if unable to get a connection
   */
  public P getConnection() throws RemotingException{
        String methodName = "getConnection";
    P connection;

        initialize();

    logger.debug(methodName, "Connection pool [", connectionPool.getPoolId(), "]: total=", connectionPool.getTotalConnections(),
      ", active=", connectionPool.getActiveConnections(), ", deficit=", connectionPool.getConnectionDeficit()
      , ", churn=", connectionPool.getChurnedConnections());

        connection = connectionPool.getConnection();

    return connection;
  }

}
