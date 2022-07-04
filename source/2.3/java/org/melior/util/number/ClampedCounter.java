/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.number;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implements a simple bounded atomic counter.  It wraps an {@code AtomicLong}
 * to provide better cosmetics.
 * @author Melior
 * @since 2.0
 */
public class ClampedCounter{
    private long min;

    private long max;

    private AtomicLong count;

  /**
   * Constructor.
   * @param initial The initial count
   * @param min The minimum
   * @param max The maximum
   */
  ClampedCounter(
    final long initial,
    final long min,
    final long max){
        super();

        this.min = min;

        this.max = max;

        count = new AtomicLong(Clamp.clampLong(initial, min, max));
  }

  /**
   * Get instance of counter with initial count.
   * @param initial The initial count
   * @param min The minimum
   * @param max The maximum
   * @return The counter
   */
  public static ClampedCounter of(
    final long initial,
    final long min,
    final long max){
    return new ClampedCounter(initial, min, max);
  }

  /**
   * Get count.
   * @return The count
   */
  public long get(){
    return count.get();
  }

  /**
   * Increment count.
   */
  public void increment(){
        count.updateAndGet(i -> Clamp.clampLong(i + 1, min, max));
  }

  /**
   * Increment count.
   * @param value The value to increment by
   */
  public void increment(
    final long value){
        count.updateAndGet(i -> Clamp.clampLong(i + value, min, max));
  }

  /**
   * Decrement count.
   */
  public void decrement(){
        count.updateAndGet(i -> Clamp.clampLong(i - 1, min, max));
  }

  /**
   * Decrement count.
   * @param value The value to decrement by
   */
  public void decrement(
    final long value){
        count.updateAndGet(i -> Clamp.clampLong(i - value, min, max));
  }

  /**
   * Reset count.
   * @return The current count
   */
  public long reset(){
        return count.getAndSet(Clamp.clampLong(0, min, max));
  }

  /**
   * Reset count.
   * @param value The value to reset to
   * @return The current count
   */
  public long reset(
    final long value){
        return count.getAndSet(Clamp.clampLong(value, min, max));
  }

}
