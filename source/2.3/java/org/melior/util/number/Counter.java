/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.util.number;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implements a simple unbounded atomic counter.  It wraps an {@code AtomicLong}
 * to provide better cosmetics.
 * @author Melior
 * @since 2.0
 */
public class Counter {

    private AtomicLong count;

    /**
     * Constructor.
     * @param initial The initial count
     */
    Counter(
        final long initial) {

        super();

        count = new AtomicLong(initial);
    }

    /**
     * Get instance of counter with initial count.
     * @param initial The initial count
     * @return The counter
     */
    public static Counter of(
        final long initial) {
        return new Counter(initial);
    }

    /**
     * Get count.
     * @return The count
     */
    public long get() {
        return count.get();
    }

    /**
     * Increment count.
     */
    public void increment() {

        count.incrementAndGet();
    }

    /**
     * Increment count.
     * @param value The value to increment by
     */
    public void increment(
        final long value) {

        count.addAndGet(value);
    }

    /**
     * Decrement count.
     */
    public void decrement() {

        count.decrementAndGet();
    }

    /**
     * Decrement count.
     * @param value The value to decrement by
     */
    public void decrement(
        final long value) {

        count.addAndGet(-value);
    }

    /**
     * Reset count.
     * @return The current count
     */
    public long reset() {

        return count.getAndSet(0);
    }

    /**
     * Reset count.
     * @param value The value to reset to
     * @return The current count
     */
    public long reset(
        final long value) {

        return count.getAndSet(value);
    }

}
