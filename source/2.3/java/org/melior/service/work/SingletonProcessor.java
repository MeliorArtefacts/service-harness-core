/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.service.work;

/**
 * Process a single item.  If the item is a managed item then a {@code Throwable} must be thrown
 * when the processing fails, to ensure that the item is correctly managed.
 * @author Melior
 * @since 2.3
 */
@FunctionalInterface
public interface SingletonProcessor<T> {

    /**
     * Process item.
     * @param item The item
     * @throws Throwable if unable to process the item
     */
    public void process(
        final T item) throws Throwable;

}
