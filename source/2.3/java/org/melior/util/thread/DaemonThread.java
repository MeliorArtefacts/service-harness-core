/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.thread;
import org.melior.context.transaction.TransactionContext;

/**
 * Creates threads as daemon threads that don't hold up the JVM when
 * it terminates.  A thread may be created as a child thread that
 * inherits some parameters from the callers transaction context.
 * @author Melior
 * @since 2.2
 */
public interface DaemonThread{

  /**
   * Create thread to execute given runnable.
   * @param runnable The runnable to execute
   */
  public static void create(
    final Runnable runnable){
        Thread thread;

        thread = new Thread(runnable);
    thread.setDaemon(true);
    thread.start();
  }

  /**
   * Create child thread to execute given runnable.
   * The child inherits the following parameters from the
   * transaction context of the caller.
   * <p><ul>
   * <li>The origin identifier
   * <li>The transaction identifier
   * </ul><p>
   * @param runnable The runnable to execute
   */
  public static void createAsChild(
    final Runnable runnable){
        TransactionContext callerContext;
    String originId;
    String transactionId;
    Thread thread;

        callerContext = TransactionContext.get();
    originId = callerContext.getOriginId();
    transactionId = callerContext.getTransactionId();

        thread = new Thread(){

      public void run(){
                TransactionContext childContext;

                childContext = TransactionContext.get();
        childContext.setOriginId(originId);
        childContext.setTransactionId(transactionId);

                runnable.run();
      }

    };
    thread.setDaemon(true);
    thread.start();
  }

  /**
   * Create thread to execute given runnable.
   * @param runnable The thread aware runnable to execute
   */
  public static void create(
    final ThreadAwareRunnable runnable){
        Thread thread;

        thread = new Thread(){

      public void run(){
                runnable.run(this);
      }

    };
    thread.setDaemon(true);
    thread.start();
  }

}
