/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.number;

/**
 * Implements a simple atomic counter.  It wraps an {@code AtomicInteger}
 * to provide better cosmetics.
 * @author Melior
 * @since 2.0
 */
public class Counter{
    private java.util.concurrent.atomic.AtomicInteger count;

  /**
   * Constructor.
   * @param initial The initial count
   */
  Counter(
    final int initial){
        super();

        count = new java.util.concurrent.atomic.AtomicInteger(initial);
  }

  /**
   * Get instance of counter with initial count.
   * @param initial The initial count
   * @return The counter
   */
  public static Counter of(
    final int initial){
    return new Counter(initial);
  }

  /**
   * Get count.
   * @return The count
   */
  public int get(){
    return count.get();
  }

  /**
   * Increment count.
   */
  public void increment(){
        count.incrementAndGet();
  }

  /**
   * Increment count.
   * @param value The value to increment by
   */
  public void increment(
    final int value){
        count.addAndGet(value);
  }

  /**
   * Decrement count.
   */
  public void decrement(){
        count.decrementAndGet();
  }

  /**
   * Decrement count.
   * @param value The value to decrement by
   */
  public void decrement(
    final int value){
        count.addAndGet(-value);
  }

  /**
   * Reset count.
   * @return The current count
   */
  public int reset(){
        return count.getAndSet(0);
  }

}
