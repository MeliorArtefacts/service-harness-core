/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.service.work;
import org.melior.component.core.ServiceComponent;
import org.melior.context.service.ServiceContext;
import org.melior.context.transaction.TransactionContext;
import org.melior.service.exception.ApplicationException;
import org.melior.service.lens.CPULens;
import org.melior.service.lens.MemoryLens;
import org.melior.util.number.Counter;
import org.springframework.stereotype.Component;

/**
 * Implementation of a work manager that tracks and manages the
 * processing of calls that come into the service.  It logs the
 * lifecycle of incoming calls and also periodically refocuses
 * the CPU usage and memory usage lenses.
 * @author Melior
 * @since 2.0
 * @see ServiceComponent
 * @see MemoryLens
 * @see CPULens
 */
@Component
public class WorkManager extends ServiceComponent {

    private MemoryLens memoryLens;

    private CPULens cpuLens;

    private Counter totalRequests;

    private Counter failedRequests;

    private Counter activeRequests;

    /**
     * Constructor.
     * @param serviceContext The service context
     * @param memoryLens The service memory lens
     * @param cpuLens The service CPU lens
     * @throws ApplicationException if an error occurs during the construction
     */
    public WorkManager(
        final ServiceContext serviceContext,
        final MemoryLens memoryLens,
        final CPULens cpuLens) throws ApplicationException {

        super(serviceContext);

        this.memoryLens = memoryLens;

        this.cpuLens = cpuLens;

        totalRequests = Counter.of(0);
        failedRequests = Counter.of(0);
        activeRequests = Counter.of(0);
    }

    /**
     * Start processing request.
     * @param transactionContext The transaction context
     * @throws ApplicationException if unable to start processing the request
     */
    public void startRequest(
        final TransactionContext transactionContext) throws ApplicationException {

        String methodName = "startRequest";

        memoryLens.refocus();
        cpuLens.refocus();

        logger.debug(methodName, "Started processing request [" + transactionContext.getOperation() + "]: total=", totalRequests.get(),
            ", failed=", failedRequests.get(), ", active=", activeRequests.get(), ".");

        totalRequests.increment();

        activeRequests.increment();
    }

    /**
     * Complete processing request. 
     * @param transactionContext The transaction context
     * @param hadException true if the request experienced an exception, false otherwise
     * @throws ApplicationException if unable to complete processing the request
     */
    public void completeRequest(
        final TransactionContext transactionContext,
        final boolean hadException) throws ApplicationException {

        String methodName = "completeRequest";
        long duration;

        activeRequests.decrement();

        if (hadException == true) {

            failedRequests.increment();
        }

        duration = transactionContext.getElapsedTimeMillis();

        logger.debug(methodName, "Completed processing request [" + transactionContext.getOperation() + "]. Duration = ", duration, " ms.");
    }

}
