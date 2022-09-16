/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.context.transaction;
import java.util.ArrayList;
import java.util.List;
import org.melior.util.time.Timer;
import org.springframework.stereotype.Component;

/**
 * Implements a transaction context for a thread to track the
 * current transaction that is being processed by the thread.
 * If the transaction is initiated by a call into the service
 * then a timer is automatically started and the transaction
 * identifier (for example {@code X-RequestId} for HTTP requests)
 * is automatically captured from the incoming call.
 * @author Melior
 * @since 2.0
 */
@Component
public class TransactionContext{
    private static ThreadLocal<TransactionContext> threadIndex = new ThreadLocal<TransactionContext>();

    private Thread thread;

    private String threadId;

    private Timer timer;

    private String originId;

    private String transactionId;

    private String operation;

    private String transactionType;

    private List<Argument> argumentList;

  /**
   * Constructor.
   */
  private TransactionContext(){
        super();

        argumentList = new ArrayList<Argument>();
  }

  /**
   * Get transaction context for current thread.
   * @return The transaction context
   */
  public static TransactionContext get(){
        TransactionContext transactionContext;
    Thread thread;

        transactionContext = threadIndex.get();

        if (transactionContext == null){
            thread = Thread.currentThread();

            transactionContext = new TransactionContext();
      transactionContext.setThread(thread);
      transactionContext.setThreadId(Integer.toString(thread.hashCode()));

            threadIndex.set(transactionContext);
    }

    return transactionContext;
  }

  /**
   * Reset transaction context for current thread.
   */
  public void reset(){
        timer = null;
    originId = null;
    transactionId = null;
    operation = null;
    transactionType = null;
    argumentList.clear();
  }

  /**
   * Clear transaction context for current thread.
   */
  public void clear(){
        threadIndex.set(null);
  }

  /**
   * Get thread.
   * @return The thread
   */
  public Thread getThread(){
    return thread;
  }

  /**
   * Set thread.
   * @param thread The thread
   */
  private void setThread(
    final Thread thread){
    this.thread = thread;
  }

  /**
   * Get thread identifier.
   * @return The thread identifier
   */
  public String getThreadId(){
    return threadId;
  }

  /**
   * Set thread identifier.
   * @param threadId The thread identifier
   */
  private void setThreadId(
    final String threadId){
    this.threadId = threadId;
  }

  /**
   * Get the start time in milliseconds.
   * @return The start time
   */
  public long getStartTimeMillis(){
    return timer.startTime();
  }

  /**
   * Get the elapsed time in milliseconds.
   * @return The elapsed time
   */
  public long getElapsedTimeMillis(){
    return timer.elapsedTime();
  }

  /**
   * Start transaction.
   * @return The transaction context
   */
  public TransactionContext startTransaction(){
    this.timer = Timer.ofMillis().start();

    return this;
  }

  /**
   * Get origin identifier.
   * @return The origin identifier
   */
  public String getOriginId(){
    return originId;
  }

  /**
   * Set origin identifier.
   * @param originId The origin identifier
   */
  public void setOriginId(
    final String originId){
    this.originId = originId;
  }

  /**
   * Get transaction identifier.
   * @return The transaction identifier
   */
  public String getTransactionId(){
    return transactionId;
  }

  /**
   * Set transaction identifier.
   * @param transactionId The transaction identifier
   */
  public void setTransactionId(
    final String transactionId){
    this.transactionId = transactionId;
  }

  /**
   * Get operation.
   * @return The operation
   */
  public String getOperation(){
    return operation;
  }

  /**
   * Set operation.
   * @param operation The operation
   */
  public void setOperation(
    final String operation){
    this.operation = operation;
  }

  /**
   * Get transaction type.
   * @return The transaction type
   */
  public String getTransactionType(){
    return transactionType;
  }

  /**
   * Set transaction type.
   * @param transactionType The transaction type
   */
  public void setTransactionType(
    final String transactionType){
    this.transactionType = transactionType;
  }

  /**
   * Get argument list.
   * @return The argument list.
   */
  public List<Argument> getArgumentList(){
    return argumentList;
  }

  /**
   * Add argument if argument value is not {@code null}.
   * @param The argument name
   * @param The argument value
   */
  public void addNonNullArgument(
    final String name,
    final String value){

        if (value != null){
            addArgument(name, value);
    }

  }

  /**
   * Add argument.
   * @param The argument name
   * @param The argument value
   */
  public void addArgument(
    final String name,
    final String value){
        Argument argument;

        argument = getArgument(name);

        if (argument == null){
            argument = new Argument();
      argument.setName(name);

            argumentList.add(argument);
    }

        argument.setValue(value);
  }

  /**
   * Get argument.
   * @param The argument name
   * @return The argument, or null if the argument does not exist
   */
  private Argument getArgument(
    final String name){

        for (Argument argument : argumentList){

            if (argument.getName().equals(name) == true){
        return argument;
      }

    }

    return null;
  }

}
