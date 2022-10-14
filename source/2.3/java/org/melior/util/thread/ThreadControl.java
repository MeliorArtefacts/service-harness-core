/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.thread;
import java.util.concurrent.TimeUnit;

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
   * Wait for given amount of time.
   * @param lock The lock object to wait on
   * @param time The amount of time to wait
   * @param timeUnit The time unit
   */
  public static void wait(
    final Object lock,
    final long time,
    final TimeUnit timeUnit){
        String methodName = "wait";

        synchronized (lock){

      try{
                lock.wait(TimeUnit.MILLISECONDS.convert(time, timeUnit));
      }
      catch (Exception exception){
        logger.error(methodName, "Thread has been interrupted.", exception);
      }

    }

  }

  /**
   * Sleep for given amount of time.
   * @param time The amount of time to wait
   * @param timeUnit The time unit
   */
  public static void sleep(
    final long time,
    final TimeUnit timeUnit){
        String methodName = "sleep";

    try{

            if (timeUnit == TimeUnit.NANOSECONDS){
                Thread.sleep(0, (int) time);
      }
      else{
                Thread.sleep(TimeUnit.MILLISECONDS.convert(time, timeUnit));
      }

    }
    catch (Exception exception){
      logger.error(methodName, "Thread has been interrupted.", exception);
    }

  }

}
