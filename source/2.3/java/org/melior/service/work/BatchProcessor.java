/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.service.work;
import java.util.List;

/**
 * Process a batch of items.  The implementer must ensure that processing of the
 * batch of items either succeeds atomically or fails atomically.  If the items
 * are managed items then a {@code Throwable} must be thrown when the processing
 * fails, to ensure that the items are correctly managed.
 * @author Melior
 * @since 2.3
 */
@FunctionalInterface
public interface BatchProcessor<T> {

    /**
     * Process batch of items.
     * @param items The list of items
     * @throws Throwable if unable to process the batch of items
     */
    public void process(
        final List<T> items) throws Throwable;

}
