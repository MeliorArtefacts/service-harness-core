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
import org.melior.logging.core.Logger;
import org.melior.logging.core.LoggerFactory;
import org.melior.service.exception.ExceptionType;
import org.melior.util.time.Timer;

/**
 * Base class for managed objects that form part of a remoting client,
 * but that are not connection objects.  A client object may be cached
 * by its parent.  A client object may have a connection object as its
 * parent.
 * @author Melior
 * @since 2.3
 */
public abstract class ClientObject<C extends ClientConfig, T extends ExceptionTarget, P> implements ExceptionTarget, InvocationHandler {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private String name;

    protected C configuration;

    protected T parent;

    protected P delegate;

    private P proxy;

    /**
     * Constructor.
     * @param name The object name
     * @param configuration The client configuration
     * @param parent The parent
     * @param clazz The proxy class
     * @throws RemotingException if an error occurs during the construction
     */
    @SuppressWarnings("unchecked")
    public ClientObject(
        final String name,
        final C configuration,
        final T parent,
        final Class clazz) throws RemotingException {

        super();

        this.name = name;

        this.configuration = configuration;

        this.parent = parent;

        delegate = null;

        try {

            proxy = (P) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[] {clazz}, this);
        }
        catch (Exception exception) {
            throw new RemotingException(ExceptionType.LOCAL_APPLICATION, "Failed to create connection proxy: " + exception.getMessage(), exception);
        }

    }

    /**
     * Get delegate.
     * @return The delegate
     */
    public P getDelegate() {
        return delegate;
    }

    /**
     * Set delegate.
     * @param delegate The delegate
     */
    public void setDelegate(
        final P delegate) {
        this.delegate = delegate;
    }

    /**
     * Get proxy.
     * @return The proxy
     */
    public P getProxy() {
        return proxy;
    }

    /**
     * Capture last exception.
     * @param exception The last exception
     */
    public void captureException(
        final Throwable exception) {

        parent.captureException(exception);
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

            logger.debug(methodName, name, " ", successMessage, ".  Duration = ", duration, " ms.");
        }
        catch (InvocationTargetException exception) {

            duration = timer.elapsedTime(TimeUnit.MILLISECONDS);

            logger.error(methodName, name, " ", failureMessage, ".  Duration = ", duration, " ms.");

            captureException(exception.getCause());

            throw exception.getCause();
        }
        catch (Throwable exception) {

            duration = timer.elapsedTime(TimeUnit.MILLISECONDS);

            logger.error(methodName, name, " ", failureMessage, ".  Duration = ", duration, " ms.");

            captureException(exception);

            throw exception;
        }

        return invocationResult;
    }

}
