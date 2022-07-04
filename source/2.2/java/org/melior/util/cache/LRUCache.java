/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.cache;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implements an LRU cache with bounded capacity.  When the cache reaches capacity
 * then the eldest entry is evicted to make room for new entries.
 * @author Melior
 * @since 2.0
 */
public class LRUCache<K, V> extends Cache<K, V>{

  /**
   * Implements a linked hash map with access order and bounded capacity.
   */
  @SuppressWarnings("hiding")
  protected class LRUMap<K, V> extends LinkedHashMap<K, V>{
        private LRUCache<K, V> lruCache;

    /**
     * Constructor.
     * @param lruCache The LRU cache
     */
    public LRUMap(
      final LRUCache<K, V> lruCache){
            super(lruCache.getCapacity(), 0.75f, true);

            this.lruCache = lruCache;
    }

    /**
     * Remove eldest entry when at capacity.
     * @param lruEntry The eldest entry
     * @return true if the entry was removed, false otherwise
     */
    protected boolean removeEldestEntry(
      final Map.Entry<K, V> lruEntry){
            boolean remove;

            if ((remove = (size() > lruCache.getCapacity())) == true){
                lruCache.destroyValue(lruEntry.getValue());
      }

      return remove;
    }

  }

  /**
   * Constructor.
   * @param capacity The initial capacity of the LRU cache
   */
  public LRUCache(
    final int capacity){
        super(capacity);

        cacheMap = new LRUMap<K, V>(this);
  }

  /**
   * Add entry to cache.
   * @param key The key of the entry
   * @param value The value of the entry
   * @return true if an entry was added to the cache, false otherwise
   */
  public final boolean add(
    final K key,
    final V value){
        return ((cacheMap.put(key, value) == null) && (value != null));
  }

}
