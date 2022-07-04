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
import java.util.concurrent.Callable;

import org.melior.service.exception.ApplicationException;
import org.melior.service.exception.ExceptionType;

/**
 * Implements a simple cache with bounded capacity.  When the cache reaches capacity
 * then the eldest entry is evicted to make room for new entries.
 * @author Melior
 * @since 2.0
 */
public class SimpleCache<K, V>{
    private int capacity;

    protected Map<K, V> cacheMap;

  /**
   * Constructor.
   * @param capacity The capacity of the cache
   */
  SimpleCache(
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
  public synchronized boolean add(
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
  public synchronized V get(
    final K key){
        return cacheMap.get(key);
  }

  /**
   * Get value from cache.  If the value is not found in the cache then
   * the producer will be called to produce a value and the value will
   * be added to the cache.
   * @param key The key to use to find the value
   * @param producer The producer that will provide the value
   * @return The value
   */
  public synchronized V get(
    final K key,
    final Callable<V> producer) throws ApplicationException{
        V value;

        value = get(key);

        if (value == null){

      try{
                value = producer.call();
      }
      catch (Exception exception){
        throw new ApplicationException(ExceptionType.LOCAL_APPLICATION, exception);
      }

            add(key, value);
    }

    return value;
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
  public synchronized void evict(
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
  public synchronized void clear(){
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
