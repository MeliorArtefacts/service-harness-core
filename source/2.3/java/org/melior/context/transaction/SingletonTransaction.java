/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.context.transaction;
import java.util.UUID;
import org.melior.service.work.SingletonProcessor;

/**
 * Performs the processing of a {@code SingletonProcessor} in the scope of a transaction.
 * In the transaction context of the calling thread a timer is automatically started
 * and a transaction identifier is automatically generated and assigned for the
 * duration of the processing.
 * @author Melior
 * @since 2.3
 */
public class SingletonTransaction<T> implements SingletonProcessor<T> {

    private SingletonProcessor<T> singletonProcessor;

    /**
     * Constructor.
     * @param singletonProcessor The singleton processor
     */
    SingletonTransaction(
        final SingletonProcessor<T> singletonProcessor) {

        super();

        this.singletonProcessor = singletonProcessor;
    }

    /**
     * Process item.
     * @param item The item
     * @throws Throwable if unable to process the item
     */
    public void process(
        final T item) throws Throwable {

        TransactionContext transactionContext;

        transactionContext = TransactionContext.get();
        transactionContext.startTransaction();
        transactionContext.setTransactionId(UUID.randomUUID().toString());

        singletonProcessor.process(item);

        transactionContext.reset();
    }

}
