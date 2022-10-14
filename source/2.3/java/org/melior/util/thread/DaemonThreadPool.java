/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.thread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.melior.context.transaction.TransactionContext;
import org.melior.util.number.Clamp;
import org.melior.util.time.Timer;

/**
 * Implements a pool of daemon threads that don't hold up the JVM when
 * it terminates.  A task may be executed by a child thread that
 * inherits some parameters from the callers transaction context.
 * @author Melior
 * @since 2.2
 */
public class DaemonThreadPool{

    private static ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactory(){

    public Thread newThread(
      final Runnable runnable){
            Thread thread;

            thread = Executors.defaultThreadFactory().newThread(runnable);
      thread.setDaemon(true);

      return thread;
    }

  });

  /**
   * Constructor.
   */
  private DaemonThreadPool(){
        super();
  }

  /**
   * Get instance of daemon thread pool.
   * @return The daemon thread pool
   */
  public static DaemonThreadPool of(){
    return new DaemonThreadPool();
  }

  /**
   * Use thread to execute given callable.
   * @param <T> The type
   * @param callable The callable to execute
   * @return The future for the callable
   */
  public <T> Future<T> execute(
    final Callable<T> callable){
        return executor.submit(callable);
  }

  /**
   * Use child thread to execute given callable.
   * The child inherits the following parameters from the
   * transaction context of the caller.
   * <ul>
   * <li>The origin identifier
   * <li>The transaction identifier
   * </ul>
   * @param <T> The type
   * @param callable The callable to execute
   * @return The future for the callable
   */
  public <T> Future<T> executeAsChild(
    final Callable<T> callable){
        TransactionContext callerContext;
    String originId;
    String transactionId;

        callerContext = TransactionContext.get();
    originId = callerContext.getOriginId();
    transactionId = callerContext.getTransactionId();

        return executor.submit(new Callable<T>(){

      public T call() throws Exception{
                TransactionContext childContext;

                childContext = TransactionContext.get();
        childContext.setOriginId(originId);
        childContext.setTransactionId(transactionId);

                return callable.call();
      }

    });
  }

  /**
   * Use child threads to execute given callables.
   * The children inherits the following parameters from the
   * transaction context of the caller.
   * <ul>
   * <li>The origin identifier
   * <li>The transaction identifier
   * </ul>
   * @param <T> The type
   * @param timeout The maximum amount of time to wait
   * @param timeUnit The time unit of the timeout
   * @param postProcessor The callback to call if a callable has produced a result
   * @param errorProcessor The callback to call if a callable has failed with an exception
   * @param callables The callables to execute
   */
  @SafeVarargs
  public final <T> void executeAsChild(
    final long timeout,
    final TimeUnit timeUnit,
    final Consumer<T> postProcessor,
    final Consumer<Throwable> errorProcessor,
    final Callable<T>...callables){
        long remainingTime;
    Timer timer;
    List<Future<T>> futures;
    T result;

        remainingTime = timeUnit.toMillis(timeout);

        timer = Timer.ofMillis().start();

        futures = new ArrayList<Future<T>>(callables.length);

        for (Callable<T> callable : callables){
      futures.add(executeAsChild(callable));
    }

        for (Future<T> future : futures){

      try{
                result = future.get(Clamp.clampLong(remainingTime - timer.elapsedTime(), 0, Long.MAX_VALUE), TimeUnit.MILLISECONDS);

                if (postProcessor != null){
                    postProcessor.accept(result);
        }

      }
      catch (java.lang.Throwable exception){
                future.cancel(true);

                if (errorProcessor != null){
                    errorProcessor.accept(exception);
        }

      }

    }

  }

  /**
   * Use thread to execute given runnable.
   * @param runnable The runnable to execute
   * @return The future for the callable
   */
  public Future<?> execute(
    final Runnable runnable){
        return executor.submit(runnable);
  }

  /**
   * Use child thread to execute given runnable.
   * The child inherits the following parameters from the
   * transaction context of the caller.
   * <ul>
   * <li>The origin identifier
   * <li>The transaction identifier
   * </ul>
   * @param runnable The runnable to execute
   * @return The future for the callable
   */
  public Future<?> executeAsChild(
    final Runnable runnable){
        TransactionContext callerContext;
    String originId;
    String transactionId;

        callerContext = TransactionContext.get();
    originId = callerContext.getOriginId();
    transactionId = callerContext.getTransactionId();

        return executor.submit(new Runnable(){

      public void run(){
                TransactionContext childContext;

                childContext = TransactionContext.get();
        childContext.setOriginId(originId);
        childContext.setTransactionId(transactionId);

                runnable.run();
      }

    });
  }

  /**
   * Use child threads to execute given runnables.
   * The children inherits the following parameters from the
   * transaction context of the caller.
   * <ul>
   * <li>The origin identifier
   * <li>The transaction identifier
   * </ul>
   * @param timeout The maximum amount of time to wait
   * @param timeUnit The time unit of the timeout
   * @param errorProcessor The callback to call if a runnable has failed with an exception
   * @param runnables The runnables to execute
   */
  @SafeVarargs
  public final void executeAsChild(
    final long timeout,
    final TimeUnit timeUnit,
    final Consumer<Throwable> errorProcessor,
    final Runnable...runnables){
        long remainingTime;
    Timer timer;
    List<Future<?>> futures;

        remainingTime = timeUnit.toMillis(timeout);

        timer = Timer.ofMillis().start();

        futures = new ArrayList<Future<?>>(runnables.length);

        for (Runnable runnable : runnables){
      futures.add(executeAsChild(runnable));
    }

        for (Future<?> future : futures){

      try{
                future.get(Clamp.clampLong(remainingTime - timer.elapsedTime(), 0, Long.MAX_VALUE), TimeUnit.MILLISECONDS);
      }
      catch (java.lang.Throwable exception){
                future.cancel(true);

                if (errorProcessor != null){
                    errorProcessor.accept(exception);
        }

      }

    }

  }

}
