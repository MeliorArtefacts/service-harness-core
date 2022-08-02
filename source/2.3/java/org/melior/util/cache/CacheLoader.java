/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.cache;
import java.util.Map;
import org.melior.service.exception.ApplicationException;

/**
 * TODO
 * @author Melior
 * @since 2.3
 */
@FunctionalInterface
public interface CacheLoader<K, V>{

  /**
   * Load cache data.
   * @return The map with the cache data
   * @throws ApplicationException if unable to load the cache data
   */
  public Map<K, V> load() throws ApplicationException;

}
