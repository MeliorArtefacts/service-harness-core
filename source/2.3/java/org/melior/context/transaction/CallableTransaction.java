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
import java.util.concurrent.Callable;

/**
 * Calls a {@code Callable} in the scope of a transaction.  In the transaction context
 * of the calling thread a timer is automatically started and a transaction identifier
 * is automatically generated and assigned for the duration of the call.
 * @author Melior
 * @since 2.3
 */
public class CallableTransaction<T> implements Callable<T> {

    private Callable<T> callable;

    /**
     * Constructor.
     * @param callable The callable
     */
    CallableTransaction(
        final Callable<T> callable) {

        super();

        this.callable = callable;
    }

    /**
     * Call callable.
     * @throws Exception if unable to call the callable
     */
    public T call() throws Exception {

        TransactionContext transactionContext;
        T result;

        transactionContext = TransactionContext.get();
        transactionContext.startTransaction();
        transactionContext.setTransactionId(UUID.randomUUID().toString());

        result = callable.call();

        transactionContext.reset();

        return result;
    }

}
