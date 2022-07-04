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
 * Implements a simple semaphore.
 * @author Melior
 * @since 2.0
 */
public class Semaphore implements ReadOnlySemaphore, WriteOnlySemaphore{
    private Synchronizer synchronizer;

  /**
   * Constructor.  Sets the initial number of permits to 0 and
   * the maximum number of permits to Integer.MAX_VALUE.
   */
  public Semaphore(){
    this(0, Integer.MAX_VALUE);
  }

  /**
   * Constructor.  Sets the initial number of permits and maximum number of permits.
   * @param initial The initial number of permits
   * @param maximum The maximum number of permits
   */
  public Semaphore(
    final int initial,
    final int maximum){
        super();

        synchronizer = new Synchronizer(initial, maximum);
  }

  /**
   * Get current number of permits.
   * @return The current number of permits
   */
  public int getPermits(){
        return synchronizer.getPermits();
  }

  /**
   * Get maximum number of permits.
   * @return The maximum number of permits
   */
  public int getMaximum(){
    return synchronizer.getMaximum();
  }

  /**
   * Set maximum number of permits.
   * @param maximum The maximum number of permits
   */
  public void setMaximum(
    final int maximum){
        this.synchronizer.setMaximum(maximum);
  }

  /**
   * Wait to acquire a permit indefinitely.
   * @throws InterruptedException when the thread is interrupted
   */
  public void acquire() throws InterruptedException{

    try{
            synchronizer.acquireSharedInterruptibly(1);
    }
    catch (InterruptedException exception){
      Thread.currentThread().interrupt();
      throw exception;
    }

  }

  /**
   * Wait to acquire a permit for specified timeout.
   * @param timeout The time to wait
   * @param timeUnit The time unit
   * @return true if a permit could be obtained, false otherwise
   * @throws InterruptedException when the thread is interrupted
   */
  public boolean acquire(
    final long timeout,
    final TimeUnit timeUnit) throws InterruptedException{
        boolean decrement;

    try{
            decrement = synchronizer.tryAcquireSharedNanos(1, TimeUnit.NANOSECONDS.convert(timeout, timeUnit));
    }
    catch (InterruptedException exception){
      Thread.currentThread().interrupt();
      throw exception;
    }

    return decrement;
  }

  /**
   * Wait to acquire a permit indefinitely.
   * Drains semaphore of all permits.
   * @throws InterruptedException when the thread is interrupted
   */
  public void acquireAndDrain() throws InterruptedException{

    try{
            synchronizer.acquireSharedInterruptibly(1);
    }
    catch (InterruptedException exception){
      Thread.currentThread().interrupt();
      throw exception;
    }

        synchronizer.drain();
  }

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
    final TimeUnit timeUnit) throws InterruptedException{
        boolean acquired;

    try{
            acquired = synchronizer.tryAcquireSharedNanos(1, TimeUnit.NANOSECONDS.convert(timeout, timeUnit));
    }
    catch (InterruptedException exception){
      Thread.currentThread().interrupt();
      throw exception;
    }

        if (acquired == true){
            synchronizer.drain();
    }

    return acquired;
  }

  /**
   * Release a permit.
   */
  public void release(){
        synchronizer.releaseShared(1);
  }

  /**
   * Release specified number of permits.
   * @param permits The number of permits
   */
  public void release(
    final int permits){
        synchronizer.releaseShared(permits);
  }

}
