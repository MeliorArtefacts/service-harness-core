/* __  __      _ _            
  |  \/  |    | (_)           
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

/**
 * Performs the processing of a {@code BatchProcessor} in the scope of a transaction.
 * In the transaction context of the calling thread a timer is automatically started
 * and a transaction identifier is automatically generated and assigned for the
 * duration of the processing.
 * @author Melior
 * @since 2.3
 */
public class BatchTransaction<T> implements BatchProcessor<T> {

    private BatchProcessor<T> batchProcessor;

    /**
     * Constructor.
     * @param batchProcessor The batch processor
     */
    BatchTransaction(
        final BatchProcessor<T> batchProcessor) {

        super();

        this.batchProcessor = batchProcessor;
    }

    /**
     * Process batch of items.
     * @param items The list of items
     * @throws Throwable if unable to process the batch of items
     */
    public void process(
        final List<T> items) throws Throwable {

        TransactionContext transactionContext;

        transactionContext = TransactionContext.get();
        transactionContext.startTransaction();
        transactionContext.setTransactionId(UUID.randomUUID().toString());

        batchProcessor.process(items);

        transactionContext.reset();
    }

}
