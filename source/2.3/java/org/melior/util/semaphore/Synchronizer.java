/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.util.semaphore;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Used internally by the simple {@code Semaphore} to manage the permits.
 * @author Melior
 * @since 2.0
 */
class Synchronizer extends AbstractQueuedSynchronizer {

    private int maximum;

    /**
     * Constructor.  Sets the initial number of permits and maximum number of permits.
     * @param initial The initial number of permits
     * @param maximum The maximum number of permits
     */
    Synchronizer(
        final int initial,
        final int maximum) {

        super();

        setState(initial);

        this.maximum = maximum;
    }

    /**
     * Get current number of permits.
     * @return The current number of permits
     */
    public int getPermits() {

        return getState();
    }

    /**
     * Get maximum number of permits.
     * @return The maximum number of permits
     */
    public int getMaximum() {
        return maximum;
    }

    /**
     * Set maximum number of permits.
     * @param maximum The maximum number of permits
     */
    public void setMaximum(
        final int maximum) {

        this.maximum = maximum;
    }

    /**
     * Internal - attempt to acquire permits.
     * @param permits The number of permits
     * @return true if the acquire was successful, false otherwise
     */
    protected int tryAcquireShared(
        final int permits) {

        for (;;) {
            int available = getState();
            int remaining = available - permits;
            if (remaining < 0 || compareAndSetState(available, remaining) == true)
                return remaining;
        }

    }

    /**
     * Internal - attempt to release permits.
     * @param permits The number of permits
     * @return true if the release was successful, false otherwise
     */
    protected boolean tryReleaseShared(
        final int permits) {

        for (;;) {
            int current = getState();
            if (current >= maximum)
                return false;
            int next = current + permits;
            if (next > maximum)
                next = maximum;
            if (compareAndSetState(current, next) == true)
                return true;
        }

    }

    /**
     * Internal - Drain all permits.
     * @return The number of permits drained
     */
    protected int drain() {

        for (;;) {
            int current = getState();
            if (current == 0 || compareAndSetState(current, 0) == true)
                return current;
        }

    }

}
