/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.resilience;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

/**
 * Improves service resilience by attempting to execute an action a specified
 * number of times.  If an exception predicate is provided, then it is evaluated
 * to determine whether or not another attempt should be made.  When the attempts
 * have been exhausted, then any exception that was raised during the final
 * attempt is propagated to the caller.
 * @author Melior
 * @since 2.2
 */
public class Retry{

  /**
   * Constructor.
   */
  private Retry(){
        super();
  }

  /**
   * Attempt to execute action up to specified number of attempts.
   * @param attempts The number of attempts
   * @param action The action to execute
   * @return The return from the action
   * @throws Exception when the action cannot be executed successfully
   */
  public static <T> T execute(
    final int attempts,
    final Callable<T> action) throws Exception{
    return execute(attempts, action, exception -> {return true;});
  }

  /**
   * Attempt to execute action up to specified number of attempts.
   * @param attempts The number of attempts
   * @param action The action to execute
   * @param predicate The exception predicate
   * @return The return from the action
   * @throws Exception when the action cannot be executed successfully
   */
  public static <T> T execute(
    final int attempts,
    final Callable<T> action,
    final Predicate<Throwable> predicate) throws Exception{

        for (int i = 1; i <= attempts; i++){

      try{
                return action.call();
      }
      catch (Exception exception){

                if ((i == attempts) || (predicate.test(exception) == false)){
          throw exception;
        }

      }
      
    }

    return null;
  }

  /**
   * Attempt to execute action up to specified number of attempts.
   * @param attempts The number of attempts
   * @param action The action to execute
   * @return The return from the action
   * @throws Exception when the action cannot be executed successfully
   */
  public static <T> void execute(
    final int attempts,
    final NoReturnCallable<T> action) throws Exception{
    execute(attempts, action, exception -> {return true;});
  }

  /**
   * Attempt to execute action up to specified number of attempts.
   * @param attempts The number of attempts
   * @param action The action to execute
   * @param predicate The exception predicate
   * @return The return from the action
   * @throws Exception when the action cannot be executed successfully
   */
  public static <T> void execute(
    final int attempts,
    final NoReturnCallable<T> action,
    final Predicate<Throwable> predicate) throws Exception{

        for (int i = 1; i <= attempts; i++){

      try{
                action.call();

        return;
      }
      catch (Exception exception){

                if ((i == attempts) || (predicate.test(exception) == false)){
          throw exception;
        }

      }
      
    }

  }

}
