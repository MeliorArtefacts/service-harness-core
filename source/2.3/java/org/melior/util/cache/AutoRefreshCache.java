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
import java.util.concurrent.TimeUnit;
import org.melior.logging.core.Logger;
import org.melior.logging.core.LoggerFactory;
import org.melior.service.core.ServiceState;
import org.melior.service.exception.ApplicationException;
import org.melior.service.exception.ExceptionType;
import org.melior.util.thread.DaemonThread;
import org.melior.util.thread.ThreadControl;

/**
 * TODO
 * @author Melior
 * @since 2.3
 */
public class AutoRefreshCache<K, V>{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private CacheLoader<K, V> cacheLoader;

    private long lifetime;

    protected Map<K, V> cacheMap;

  /**
   * Constructor
   * @param cacheLoader The cache loader
   * @param lifetime The lifetime of the cache entries
   * @param timeUnit The time unit of the lifetime
   */
  AutoRefreshCache(
    final CacheLoader<K, V> cacheLoader,
    final long lifetime,
    final TimeUnit timeUnit){
        super();

        this.cacheLoader = cacheLoader;

        this.lifetime = timeUnit.toMillis(lifetime);

        DaemonThread.create(() -> refreshCacheData());
  }

  /**
   * Get entry lifetime.
   * @return The entry lifetime
   */
  public long getLifetime(){
    return lifetime;
  }

  /**
   * Set entry lifetime.
   * @param lifetime The entry lifetime
   */
  public void setLifetime(
    final int lifetime){
        this.lifetime = lifetime;
  }

  /**
   * Get value from cache.
   * @param key The key to use to find the value
   * @return The value, or null if the key is not found in the cache
   * @throws ApplicationException if unable to access the cache data
   */
  public V get(
    final K key) throws ApplicationException{

        if (cacheMap == null){
            loadCacheData();
    }

        return cacheMap.get(key);
  }

  /**
   * Get cache data loaded indicator.
   * @return true if the cache data has been loaded, false otherwise
   */
  public boolean loaded(){
    return (cacheMap != null);
  }

  /**
   * Load cache data.
   * @throws ApplicationException if unable to load the cache data
   */
  private synchronized void loadCacheData() throws ApplicationException{
        String methodName = "loadCacheData";

        if (cacheMap == null){
      logger.debug(methodName, "Load cache data.");

      try{
                cacheMap = cacheLoader.load();
      }
      catch (Exception exception){
        throw new ApplicationException(ExceptionType.LOCAL_APPLICATION,
          "Failed to load cache data: " + exception.getMessage(), exception);
      }

    }

  }

  /**
   * Refresh cache data.
   */
  private void refreshCacheData(){
        String methodName = "refreshCacheData";

        while (ServiceState.isActive() == true){

      try{
                ThreadControl.sleep(lifetime);

                while (ServiceState.isSuspended() == true){
          ThreadControl.sleep(100);
        }

        logger.debug(methodName, "Refresh cache data.");

                cacheMap = cacheLoader.load();

        logger.debug(methodName, "Refresh complete.");
      }
      catch (java.lang.Exception exception){
        logger.error(methodName, "Failed to refresh cache data.", exception);
      }

    }

  }

}
