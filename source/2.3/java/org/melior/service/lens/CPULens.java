/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.service.lens;
import java.lang.management.ManagementFactory;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.melior.logging.core.Logger;
import org.melior.logging.core.LoggerFactory;
import org.melior.service.core.ServiceState;
import org.melior.service.exception.ApplicationException;
import org.melior.service.exception.ExceptionType;
import org.melior.util.semaphore.Semaphore;
import org.melior.util.thread.DaemonThread;
import org.melior.util.thread.ThreadControl;
import org.springframework.stereotype.Component;

/**
 * Implements a lens that is focused on the CPU usage
 * of the service.  It periodically glimpses and logs
 * the CPU usage.
 * @author Melior
 * @since 2.2
 */
@Component
public class CPULens{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Semaphore semaphore;

    private MBeanServer beanServer;

    private ObjectName operatingSystemTypeName;

  /**
   * Constructor.
   * @throws ApplicationException if an error occurs during the construction
   */
  public CPULens() throws ApplicationException{
        super();

        semaphore = Semaphore.of();

        beanServer = ManagementFactory.getPlatformMBeanServer();

    try{
            operatingSystemTypeName = ObjectName.getInstance("java.lang:type=OperatingSystem");
    }
    catch (Exception exception){
      throw new ApplicationException(ExceptionType.LOCAL_APPLICATION, "Failed to create operating system type name.", exception);
    }

        DaemonThread.create(() -> glimpse());
  }

  /**
   * Request refocus of CPU lens.
   */
  public void refocus(){
        semaphore.release();
  }

  /**
   * Glimpse CPU load.
   */
  private void glimpse(){
        String methodName = "glimpse";
    AttributeList attributeList;
    double processCPULoad;
    double nodeCPULoad;

        while (ServiceState.isActive() == true){

      try{
                semaphore.acquireAndDrain();

                attributeList = beanServer.getAttributes(operatingSystemTypeName, new String[] {"ProcessCpuLoad", "SystemCpuLoad"});

                if (attributeList.isEmpty() == false){
                    processCPULoad = ((Double) ((Attribute) attributeList.get(0)).getValue()).doubleValue();
          nodeCPULoad = ((Double) ((Attribute) attributeList.get(1)).getValue()).doubleValue();

                    if ((processCPULoad != -1.0) && (nodeCPULoad != -1.0)){
            logger.debug(methodName, "cpu: process=", String.format("%.3f", processCPULoad * 100), " %",
              ", node=", String.format("%.3f", nodeCPULoad * 100), " %");
          }

        }

                ThreadControl.wait(this, 100);
      }
      catch (Exception exception){
        logger.error(methodName, "Failed to glimpse CPU load: ", exception.getMessage(), exception);
      }

    }
      
  }

}
