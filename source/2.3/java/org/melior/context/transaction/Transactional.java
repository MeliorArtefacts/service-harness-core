/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.context.transaction;
import java.util.concurrent.Callable;

import org.melior.service.work.BatchProcessor;
import org.melior.service.work.SingletonProcessor;
import org.melior.util.resilience.NoReturnCallable;

/**
 * Create transactional incarnations of processors, callables and runnables.
 * @author Melior
 * @since 2.3
 */
public interface Transactional<T>{

  /**
   * Construct transactional batch processor.
   * @param batchProcessor The original batch processor
   * @return The transactional batch processor
   */
  public static <T> BatchProcessor<T> ofBatch(
    final BatchProcessor<T> batchProcessor){
    return new BatchTransaction<T>(batchProcessor);
  }

  /**
   * Construct transactional singleton processor.
   * @param singletonProcessor The original singleton processor
   * @return The transactional singleton processor
   */
  public static <T> SingletonProcessor<T> ofSingle(
    final SingletonProcessor<T> singletonProcessor){
    return new SingletonTransaction<T>(singletonProcessor);
  }

  /**
   * Construct transactional callable.
   * @param callable The original callable
   * @return The transactional callable
   */
  public static <T> Callable<T> ofCallable(
    final Callable<T> callable){
    return new CallableTransaction<T>(callable);
  }

  /**
   * Construct transactional callable.
   * @param callable The original callable
   * @return The transactional callable
   */
  public static <T> NoReturnCallable<T> ofCallable(
    final NoReturnCallable<T> callable){
    return new NoReturnCallableTransaction<T>(callable);
  }

  /**
   * Construct transactional runnable.
   * @param runnable The original runnable
   * @return The transactional runnable
   */
  public static <T> Runnable ofRunnable(
    final Runnable runnable){
    return new RunnableTransaction(runnable);
  }

}
