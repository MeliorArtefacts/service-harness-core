/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.util.semaphore;

/**
 * Provides write-only access to a semaphore.
 * @author Melior
 * @since 2.0
 * @see Semaphore
 */
public interface WriteOnlySemaphore {

    /**
     * Release a permit.
     */
    public void release();

    /**
     * Release specified number of permits.
     * @param permits The number of permits
     */
    public void release(
        final int permits);

}
