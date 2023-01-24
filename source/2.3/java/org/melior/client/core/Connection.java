/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.client.core;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;
import org.melior.client.exception.ExceptionTarget;
import org.melior.client.exception.RemotingException;
import org.melior.client.pool.ConnectionPool;
import org.melior.logging.core.Logger;
import org.melior.logging.core.LoggerFactory;
import org.melior.service.exception.ExceptionType;
import org.melior.util.object.ObjectUtil;
import org.melior.util.time.Timer;

/**
 * Base class for connection objects that are produced by a connection
 * factory on behalf of a remoting client.  The connection objects may
 * be pooled for improved performance.
 * <p>
 * When pooled, a connection is aware of the connection pool from which
 * is was borrowed, so that the connection may return itself to the
 * connection pool when it is no longer in use.
 * @author Melior
 * @since 2.3
 * @see ConnectionFactory
 */
public abstract class Connection<C extends ClientConfig, T extends Connection<C, T, P>, P> implements ExceptionTarget, InvocationHandler {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected C configuration;

    protected ConnectionPool<C, T, P> connectionPool;

    private String connectionId;

    private Timer lifetimeTimer;

    private String connectionDescriptor;

    private Thread ownerThread;

    protected Throwable lastException;

    protected P delegate;

    private P proxy;

    /**
     * Constructor.
     * @param configuration The client configuration
     * @param connectionPool The connection pool
     * @throws RemotingException if an error occurs during the construction
     */
    public Connection(
        final C configuration,
        final ConnectionPool<C, T, P> connectionPool) throws RemotingException {

        super();

        this.configuration = configuration;

        this.connectionPool = connectionPool;

        connectionId = String.valueOf(this.hashCode());

        lifetimeTimer = Timer.ofMillis().start();

        setConnectionDescriptor();

        ownerThread = null;

        lastException = null;

        delegate = null;

        proxy = null;
    }

    /**
     * Get connection descriptor.
     * @return The connection descriptor
     */
    public String getConnectionDescriptor() {
        return connectionDescriptor;
    }

    /**
     * Build connection descriptor.
     */
    private void setConnectionDescriptor() {

        connectionDescriptor = "id=" + connectionId;
    }

    /**
     * Get proxy.
     * @return The proxy
     */
    public P getProxy() {
        return proxy;
    }

    /**
     * Allocate connection to specified thread.
     * @param thread The thread that will own the connection
     */
    public void allocate(
        final Thread thread) {

        ownerThread = thread;

        lastException = null;
    }

    /**
     * Release connection from specified thread.
     * @param thread The thread that owns the connection
     * @throws RemotingException if the connection has already been released
     */
    public void release(
        final Thread thread) throws RemotingException {

        if ((ownerThread == null) || (ownerThread != thread)) {
            throw new RemotingException("Connection has already been released.  Consider passing connection between methods.");
        }

        ownerThread = null;
    }

    /**
     * Check whether connection is still valid.
     * @param fullValidation The full validation indicator
     * @return true if the connection is still valid, false otherwise
     */
    public boolean isValid(
        final boolean fullValidation) {

        if (lastException != null) {
            return false;
        }

        return true;
    }

    /**
     * Open connection.
     * @throws RemotingException if the open attempt fails
     */
    @SuppressWarnings("unchecked")
    public void open() throws RemotingException {

        String methodName = "open";
        Timer timer;
        long duration;

        timer = Timer.ofNanos().start();

        try {
            logger.debug(methodName, "Connection [", connectionDescriptor, "] attempting to open.  URL = ", configuration.getUrl());

            delegate = openConnection();

            try {

                proxy = (P) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    ObjectUtil.getAllInterfaces(delegate.getClass()), this);
            }
            catch (Exception exception) {
                throw new RemotingException(ExceptionType.LOCAL_APPLICATION, "Failed to create connection proxy: " + exception.getMessage(), exception);
            }

            duration = timer.elapsedTime(TimeUnit.MILLISECONDS);

            logger.debug(methodName, "Connection [", connectionDescriptor, "] opened successfully.  Duration = ", duration, " ms.");
        }
        catch (Exception exception) {

            duration = timer.elapsedTime(TimeUnit.MILLISECONDS);

            logger.error(methodName, "Connection [", connectionDescriptor, "] open attempt failed.  Duration = ", duration, " ms.");

            captureException(exception);

            throw new RemotingException(exception.getMessage(), exception);
        }

    }

    /**
     * Open raw connection.
     * @return The raw connection
     * @throws Exception if unable to open the raw connection
     */
    protected abstract P openConnection() throws Exception;

    /**
     * Close connection.
     */
    public void close() {

        String methodName = "close";

        try {

            if ((delegate != null)) {

                closeConnection(delegate);
            }

            logger.debug(methodName, "Connection [", connectionDescriptor, "] closed successfully.");
        }
        catch (Exception exception) {
            logger.error(methodName, "Connection [", connectionDescriptor, "] close attempt failed.", exception);
        }
        finally {

            delegate = null;

            setConnectionDescriptor();
        }

    }

    /**
     * Close raw connection.
     * @param connection The raw connection
     * @throws Exception if unable to close the raw connection
     */
    protected abstract void closeConnection(
        final P connection) throws Exception;

    /**
     * Check whether connection has reached end-of-life
     * @return true if the connection has reached end-of-life, false otherwise
     */
    public boolean isEndOfLife() {
        return (configuration.getMaximumLifetime() > 0)
            && (lifetimeTimer.elapsedTime() > configuration.getMaximumLifetime());
    }

    /**
     * Capture last exception.
     * @param exception The last exception
     */
    public void captureException(
        final Throwable exception) {

        lastException = exception;
    }

    /**
     * Release connection into pool.
     * @param connection The connection
     * @throws RemotingException if unable to release the connection
     */
    protected void releaseConnection(
        final T connection) throws RemotingException {

        connectionPool.releaseConnection(connection);
    }

    /**
     * Handle proxy invocation.
     * @param method The method to invoke
     * @param methodArgs The arguments to invoke with
     * @return The result of the invocation
     * @throws Throwable if the invocation fails
     */
    protected Object invoke(
        final Method method,
        final Object[] methodArgs) throws Throwable {

        Object invocationResult;

        try {

            invocationResult = method.invoke(delegate, methodArgs);
        }
        catch (InvocationTargetException exception) {

            captureException(exception.getCause());

            throw exception.getCause();
        }
        catch (Throwable exception) {

            captureException(exception);

            throw exception;
        }

        return invocationResult;
    }

    /**
     * Handle proxy invocation.
     * @param method The method to invoke
     * @param methodName The method name
     * @param methodArgs The arguments to invoke with
     * @param successMessage The message to log on success
     * @param failureMessage The message to log on failure
     * @return The result of the invocation
     * @throws Throwable if the invocation fails
     */
    protected Object invokeMeasured(
        final Method method,
        final String methodName,
        final Object[] methodArgs,
        final String successMessage,
        final String failureMessage) throws Throwable {

        Timer timer;
        Object invocationResult;
        long duration;

        timer = Timer.ofNanos().start();

        try {

            invocationResult = method.invoke(delegate, methodArgs);

            duration = timer.elapsedTime(TimeUnit.MILLISECONDS);

            logger.debug(methodName, "Connection [", connectionDescriptor, "] ", successMessage, ".  Duration = ", duration, " ms.");
        }
        catch (InvocationTargetException exception) {

            duration = timer.elapsedTime(TimeUnit.MILLISECONDS);

            logger.error(methodName, "Connection [", connectionDescriptor, "] ", failureMessage, ".  Duration = ", duration, " ms.");

            captureException(exception.getCause());

            throw exception.getCause();
        }
        catch (Throwable exception) {

            duration = timer.elapsedTime(TimeUnit.MILLISECONDS);

            logger.error(methodName, "Connection [", connectionDescriptor, "] ", failureMessage, ".  Duration = ", duration, " ms.");

            captureException(exception);

            throw exception;
        }

        return invocationResult;
    }

}
