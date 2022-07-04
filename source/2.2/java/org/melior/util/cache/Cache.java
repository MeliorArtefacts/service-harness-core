/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.cache;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implements a generic bounded cache.
 * @author Melior
 * @since 2.0
 */
public class Cache<K, V>{
    private int capacity;

    protected Map<K, V> cacheMap;

  /**
   * Constructor.
   * @param capacity The initial capacity of the cache
   */
  public Cache(
    final int capacity){
        super();

        this.capacity = capacity;

        cacheMap = new HashMap<K, V>();
  }

  /**
   * Get capacity of cache.
   * @return The capacity
   */
  public int getCapacity(){
    return capacity;
  }

  /**
   * Set capacity of cache.
   * @param capacity The capacity
   */
  public void setCapacity(
    final int capacity){
    this.capacity = capacity;
  }

  /**
   * Add entry to cache.
   * @param key The key of the entry
   * @param value The value of the entry
   * @return true if an entry was added to the cache, false otherwise
   */
  public boolean add(
    final K key,
    final V value){

        if (get(key) == null){

            if (atCapacity() == true){
                evict();
      }

            cacheMap.put(key, value);

      return true;
    }

    return false;
  }

  /**
   * Get value from cache.
   * @param key The key to use to find the value
   * @return The value, or null if the key is not found in the cache
   */
  public V get(
    final K key){
        return cacheMap.get(key);
  }

  /**
   * Evict entry from cache.
   */
  protected void evict(){
        evict(cacheMap.keySet().iterator().next());
  }

  /**
   * Evict entry from cache.
   * @param key The key to use to find the entry
   */
  public void evict(
    final K key){
        destroyValue(cacheMap.remove(key));
  }

  /**
   * Destroy value.
   * @param value The value to destroy
   */
  protected void destroyValue(
    final V value){

        if (value == null){
      return;
    }

        try {value.getClass().getMethod("destroy", (Class[]) null).invoke(value, (Object[]) null);} catch (Exception exception) {}
  }

  /**
   * Clear cache.  Evict all entries from cache.
   */
  public void clear(){
        List<K> keyList;

        keyList = new LinkedList<K>(cacheMap.keySet());

        keyList.forEach(key -> evict(key));
  }

  /**
   * Check whether cache is at capacity.
   * @return true if the cache is at capacity, false otherwise
   */
  public boolean atCapacity(){
    return ((capacity > 0) && (cacheMap.size() >= capacity));
  }

}
