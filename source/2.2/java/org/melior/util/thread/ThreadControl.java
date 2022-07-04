/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.thread;
import org.melior.logging.core.Logger;
import org.melior.logging.core.LoggerFactory;

/**
 * Provides better optics for thread control functions {@code sleep} and {@code wait.}
 * @author Melior
 * @since 2.2
 */
public class ThreadControl{
    private static Logger logger = LoggerFactory.getLogger(ThreadControl.class);

  /**
   * Constructor.
   */
  private ThreadControl(){
        super();
  }

  /**
   * Wait for given number of milliseconds.
   * @param lock The lock object to wait on
   * @param millis The number of milliseconds
   */
  public static void wait(
    final Object lock,
    final long millis){
        String methodName = "wait";

        synchronized (lock){

      try{
                lock.wait(millis);
      }
      catch (Exception exception){
        logger.error(methodName, "Thread has been interrupted.", exception);
      }

    }

  }

  /**
   * Sleep for given number of milliseconds.
   * @param millis The number of milliseconds
   */
  public static void sleep(
    final long millis){
        String methodName = "sleep";

        try{
            java.lang.Thread.sleep(millis);
    }
    catch (Exception exception){
      logger.error(methodName, "Thread has been interrupted.", exception);
    }

  }

}
