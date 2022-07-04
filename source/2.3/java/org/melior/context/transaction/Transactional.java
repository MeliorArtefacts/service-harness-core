/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.context.transaction;
import java.util.List;
import java.util.UUID;

import org.melior.service.work.BatchProcessor;
import org.melior.service.work.SingletonProcessor;

/**
 * TODO
 * @author Melior
 * @since 2.3
 */
public class Transactional<T> implements BatchProcessor<T>, SingletonProcessor<T>{
    private BatchProcessor<T> batchProcessor;

    private SingletonProcessor<T> singletonProcessor;

  /**
   * Constructor.
   * @param batchProcessor The batch processor
   */
  private Transactional(
    final BatchProcessor<T> batchProcessor){
        super();

        this.batchProcessor = batchProcessor;
  }

  /**
   * Constructor.
   * @param singletonProcessor The singleton processor
   */
  private Transactional(
    final SingletonProcessor<T> singletonProcessor){
        super();

        this.singletonProcessor = singletonProcessor;
  }

  /**
   * Construct transactional batch processor.
   * @param batchProcessor The original batch processor
   * @return The transactional batch processor
   */
  public static <T> BatchProcessor<T> ofBatch(
    final BatchProcessor<T> batchProcessor){
    return new Transactional<T>(batchProcessor);
  }

  /**
   * Construct transactional singleton processor.
   * @param singletonProcessor The original singleton processor
   * @return The transactional singleton processor
   */
  public static <T> SingletonProcessor<T> ofSingle(
    final SingletonProcessor<T> singletonProcessor){
    return new Transactional<T>(singletonProcessor);
  }

  /**
   * Process batch of items.
   * @param items The list of items
   * @throws Throwable if unable to process the batch of items
   */
  public void process(
    final List<T> items) throws Throwable{
        TransactionContext transactionContext;

        transactionContext = TransactionContext.get();
    transactionContext.startTransaction();
    transactionContext.setTransactionId(UUID.randomUUID().toString());

        batchProcessor.process(items);

        transactionContext.reset();
  }

  /**
   * Process item.
   * @param item The item
   * @throws Throwable if unable to process the item
   */
  public void process(
    final T item) throws Throwable{
        TransactionContext transactionContext;

        transactionContext = TransactionContext.get();
    transactionContext.startTransaction();
    transactionContext.setTransactionId(UUID.randomUUID().toString());

        singletonProcessor.process(item);

        transactionContext.reset();
  }

}
