/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.context.transaction;
import java.util.UUID;
import org.melior.util.resilience.NoReturnCallable;

/**
 * Calls a {@code NoReturnCallable} in the scope of a transaction.  In the transaction context
 * of the calling thread a timer is automatically started and a transaction identifier
 * is automatically generated and assigned for the duration of the call.
 * @author Melior
 * @since 2.3
 */
public class NoReturnCallableTransaction<T> implements NoReturnCallable<T>{
    private NoReturnCallable<T> callable;

  /**
   * Constructor.
   * @param callable The callable
   */
  NoReturnCallableTransaction(
    final NoReturnCallable<T> callable){
        super();

        this.callable = callable;
  }

  /**
   * Call callable.
   * @throws Exception if unable to call the callable
   */
  public void call() throws Exception{
        TransactionContext transactionContext;

        transactionContext = TransactionContext.get();
    transactionContext.startTransaction();
    transactionContext.setTransactionId(UUID.randomUUID().toString());

        callable.call();

        transactionContext.reset();
  }

}
