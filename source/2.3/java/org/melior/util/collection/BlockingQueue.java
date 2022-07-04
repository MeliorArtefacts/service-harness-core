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
 * Implements an unbounded blocking queue.  When a caller attempts to remove an item
 * from the queue while the queue is empty, then the caller is blocked until an item
 * is added to the queue.
 * @author Melior
 * @since 2.0
 */
public class BlockingQueue<T>{
    private List<T> list;

    private Semaphore semaphore;

  /**
   * Constructor.
   */
  public BlockingQueue(){
        super();

        list = new LinkedList<T>();

        semaphore = Semaphore.of();
  }

  /**
   * Add item to queue.
   * @param item The item
   * @return true if the item was added, false otherwise
   */
  public boolean add(
    final T item){
        boolean result;

    synchronized (this){
            result = list.add(item);
    }

        if (result == true){
            semaphore.release();
    }

    return result;
  }

  /**
   * Remove item from queue, if one is available.  Otherwise, wait for item to be added to queue.
   * @return The item
   * @throws InterruptedException if the thread is interrupted
   */
  public T remove() throws InterruptedException{
        semaphore.acquire();

    synchronized (this){
            return list.remove(0);
    }

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

        available = semaphore.acquire(timeout, timeUnit);

        if (available == false){
      return null;
    }

    synchronized (this){
            return list.remove(0);
    }

  }

  /**
   * Get size.
   * @return The size
   */
  public int size(){
    return list.size();
  }

}
