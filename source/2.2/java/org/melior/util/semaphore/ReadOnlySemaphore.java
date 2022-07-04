/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.semaphore;

import java.util.concurrent.TimeUnit;

/**
 * Provides read-only access to a semaphore.
 * @author Melior
 * @since 2.0
 * @see {@code Semaphore}
 */
public interface ReadOnlySemaphore{

  /**
   * Wait to acquire a permit indefinitely.
   * @throws InterruptedException when the thread is interrupted
   */
  public void acquire() throws InterruptedException;

  /**
   * Wait to acquire a permit for specified timeout.
   * @param timeout The time to wait
   * @param timeUnit The time unit
   * @return true if a permit could be obtained, false otherwise
   * @throws InterruptedException when the thread is interrupted
   */
  public boolean acquire(
    final long timeout,
    final TimeUnit timeUnit) throws InterruptedException;

  /**
   * Wait to acquire a permit indefinitely.
   * Drains semaphore of all permits.
   * @throws InterruptedException when the thread is interrupted
   */
  public void acquireAndDrain() throws InterruptedException;

  /**
   * Wait to acquire a permit for specified timeout.
   * Drains semaphore of all permits.
   * @param timeout The time to wait
   * @param timeUnit The time unit
   * @return true if a permit could be obtained, false otherwise
   * @throws InterruptedException when the thread is interrupted
   */
  public boolean acquireAndDrain(
    final long timeout,
    final TimeUnit timeUnit) throws InterruptedException;

}
