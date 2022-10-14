/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.service.lens;
import java.util.concurrent.TimeUnit;

import org.melior.logging.core.Logger;
import org.melior.logging.core.LoggerFactory;
import org.melior.service.core.ServiceState;
import org.melior.util.semaphore.Semaphore;
import org.melior.util.thread.DaemonThread;
import org.melior.util.thread.ThreadControl;
import org.springframework.stereotype.Component;

/**
 * Implements a lens that is focused on the memory usage
 * of the service.  It periodically glimpses and logs
 * the memory usage.
 * @author Melior
 * @since 2.2
 */
@Component
public class MemoryLens{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Semaphore semaphore;

    private Memory totalMemory;

    private Memory usedMemory;

  /**
   * Constructor.
   */
  public MemoryLens(){
        super();

        semaphore = Semaphore.of();

        totalMemory = new Memory();

        usedMemory = new Memory();

        DaemonThread.create(() -> glimpse());
  }

  /**
   * Request refocus of memory lens.
   */
  public void refocus(){
        semaphore.release();
  }

  /**
   * Glimpse memory usage.
   */
  private void glimpse(){
        String methodName = "glimpse";
    Runtime runtime;
    long total;

        while (ServiceState.isActive() == true){

      try{
                semaphore.acquireAndDrain();

                runtime = Runtime.getRuntime();

                total = runtime.totalMemory();
        totalMemory.addMeasurement(total);
        usedMemory.addMeasurement(total - runtime.freeMemory());

        logger.debug(methodName, "memory: total: ", totalMemory, " | used: ", usedMemory);

                ThreadControl.wait(this, 100, TimeUnit.MILLISECONDS);
      }
      catch (Exception exception){
        logger.error(methodName, "Failed to glimpse memory usage: ", exception.getMessage(), exception);
      }

    }

  }

}
