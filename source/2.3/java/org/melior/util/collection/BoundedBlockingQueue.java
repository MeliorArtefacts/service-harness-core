/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.melior.util.semaphore.Semaphore;

/**
 * Implements a bounded blocking queue.  When a caller attempts to add an item
 * to the queue while the queue is full, then the caller is blocked until an item
 * is removed from the queue.  When a caller attempts to remove an item from the
 * queue while the queue is empty, then the caller is blocked until an item is
 * added to the queue.
 * @author Melior
 * @since 2.3
 */
public class BoundedBlockingQueue<T>{
    private List<T> list;

    private Semaphore producerSemaphore;
  private Semaphore consumerSemaphore;

  /**
   * Constructor.
   * @param capacity The capacity of the queue
   */
  public BoundedBlockingQueue(
    final int capacity){
        super();

        list = new LinkedList<T>();

        producerSemaphore = Semaphore.of(capacity, capacity);
    consumerSemaphore = Semaphore.of();
  }

  /**
   * Add item to queue, if capacity is available.  Otherwise, wait for item to be removed from queue.
   * @param item The item
   * @return true if the item was added, false otherwise
   * @throws InterruptedException if the thread is interrupted
   */
  public boolean add(
    final T item) throws InterruptedException{
        boolean result;

        producerSemaphore.acquire();

    synchronized (this){
            result = list.add(item);
    }

        if (result == true){
            consumerSemaphore.release();
    }
    else{
            producerSemaphore.release();
    }

    return result;
  }

  /**
   * Remove item from queue, if one is available.  Otherwise, wait for item to be added to queue.
   * @return The item
   * @throws InterruptedException if the thread is interrupted
   */
  public T remove() throws InterruptedException{
        T item;

        consumerSemaphore.acquire();

    synchronized (this){
            item = list.remove(0);
    }

        producerSemaphore.release();

    return item;
  }

  /**
   * Remove item from queue, if one is available.  Otherwise, wait for item to be added to queue.
   * @param timeout The time to wait
   * @param timeUnit The time unit
   * @return The item
   * @throws InterruptedException if the thread is interrupted
   */
  public T remove(
    final long timeout,
    final TimeUnit timeUnit) throws InterruptedException{
        boolean available;
    T item;

        available = consumerSemaphore.acquire(timeout, timeUnit);

        if (available == false){
      return null;
    }

    synchronized (this){
            item = list.remove(0);
    }

        producerSemaphore.release();

    return item;
  }

  /**
   * Get size.
   * @return The size
   */
  public int size(){
    return list.size();
  }

}
