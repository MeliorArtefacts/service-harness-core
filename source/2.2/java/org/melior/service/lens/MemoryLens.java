/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.service.lens;
import org.melior.component.core.ServiceComponent;
import org.melior.context.service.ServiceContext;
import org.melior.service.exception.ApplicationException;
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
public class MemoryLens extends ServiceComponent{
    private Semaphore semaphore;

    private Memory totalMemory;

    private Memory usedMemory;

  /**
   * Constructor.
   * @param serviceContext The service context
   * @throws ApplicationException when the initialization fails
   */
  public MemoryLens(
    final ServiceContext serviceContext) throws ApplicationException{
        super(serviceContext);

        semaphore = new Semaphore();

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

        while (isActive() == true){

      try{
                semaphore.acquireAndDrain();

                runtime = Runtime.getRuntime();

                total = runtime.totalMemory();
        totalMemory.addMeasurement(total);
        usedMemory.addMeasurement(total - runtime.freeMemory());

        logger.debug(methodName, "memory: total: ", totalMemory, " | used: ", usedMemory);

                ThreadControl.wait(this, 100);
      }
      catch (Exception exception){
        logger.error(methodName, "Failed to glimpse memory usage: ", exception.getMessage(), exception);
      }

    }

  }

}
