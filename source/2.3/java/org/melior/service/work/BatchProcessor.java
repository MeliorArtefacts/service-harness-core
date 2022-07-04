/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.service.work;
import java.util.List;

/**
 * TODO
 * @author Melior
 * @since 2.3
 */
@FunctionalInterface
public interface BatchProcessor<T>{

  /**
   * Process batch of items.
   * @param items The list of items
   * @throws Throwable if unable to process the batch of items
   */
  public void process(
    final List<T> items) throws Throwable;

}
