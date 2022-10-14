/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.collection;

/**
 * Create blocking queues, bounded or unbounded.
 * @author Melior
 * @since 2.3
 */
public interface Queue{

  /**
   * Get instance of unbounded blocking queue.
   * @param <T> The type
   * @return The unbounded blocking queue
   * @see BlockingQueue
   */
  public static <T> BlockingQueue<T> ofBlocking(){
    return new BlockingQueue<T>();
  }

  /**
   * Get instance of bounded blocking queue.
   * @param <T> The type
   * @param capacity The capacity of the queue
   * @return The bounded blocking queue
   * @see BoundedBlockingQueue
   */
  public static <T> BoundedBlockingQueue<T> ofBoundedBlocking(
    final int capacity){
    return new BoundedBlockingQueue<T>(capacity);
  }

}
