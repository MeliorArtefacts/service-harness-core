/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.cache;
import java.util.concurrent.TimeUnit;

/**
 * Create caches of simple, LRU and timed variety.
 * @author Melior
 * @since 2.3
 */
public interface Cache{

  /**
   * Get instance of simple cache with bounded capacity.
   * @param capacity The capacity of the cache
   * @return The cache
   */
  public static <K, V> SimpleCache<K, V> ofSimple(
    final int capacity){
    return new SimpleCache<K, V>(capacity);
  }

  /**
   * Get instance of LRU cache with bounded capacity.
   * @param capacity The capacity of the cache
   * @return The cache
   */
  public static <K, V> LRUCache<K, V> ofLRU(
    final int capacity){
    return new LRUCache<K, V>(capacity);
  }

  /**
   * Get instance of timed cache with bounded capacity.
   * @param capacity The capacity of the cache
   * @param lifetime The lifetime of the cache entries
   * @param timeUnit The time unit of the lifetime
   * @return The cache
   */
  public static <K, V> TimedCache<K, V> ofTimed(
    final int capacity,
    final long lifetime,
    final TimeUnit timeUnit){
    return new TimedCache<K, V>(capacity, lifetime, timeUnit);
  }

  /**
   * Get instance of auto-refresh cache.
   * @param cacheLoader The cache loader
   * @param lifetime The lifetime of the cache entries
   * @param timeUnit The time unit of the lifetime
   * @return The cache
   */
  public static <K, V> AutoRefreshCache<K, V> ofAutoRefresh(
    final CacheLoader<K, V> cacheLoader,
    final long lifetime,
    final TimeUnit timeUnit){
    return new AutoRefreshCache<K, V>(cacheLoader, lifetime, timeUnit);
  }

}
