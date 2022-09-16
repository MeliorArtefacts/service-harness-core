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

/**
 * Runs a {@code Runnable} in the scope of a transaction.  In the transaction context
 * of the calling thread a timer is automatically started and a transaction identifier
 * is automatically generated and assigned for the duration of the run.
 * @author Melior
 * @since 2.3
 */
public class RunnableTransaction<T> implements Runnable{
    private Runnable runnable;

  /**
   * Constructor.
   * @param runnable The runnable
   */
  RunnableTransaction(
    final Runnable runnable){
        super();

        this.runnable = runnable;
  }

  /**
   * Run runnable.
   */
  public void run(){
        TransactionContext transactionContext;

        transactionContext = TransactionContext.get();
    transactionContext.startTransaction();
    transactionContext.setTransactionId(UUID.randomUUID().toString());

        runnable.run();

        transactionContext.reset();
  }

}
