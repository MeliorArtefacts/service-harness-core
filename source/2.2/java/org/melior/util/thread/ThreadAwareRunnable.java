/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.thread;

/**
 * Provides a runnable with access to the thread that is running it.
 * @author Melior
 * @since 2.2
 */
@FunctionalInterface
public interface ThreadAwareRunnable{

  /**
   * When an object implementing interface <code>ThreadAwareRunnable</code> is used
   * to create a thread, starting the thread causes the object's <code>run</code>
   * method to be called in that separately executing thread, with the <code>Thread</code>
   * being passed into the <code>run</code> method.
   * @param thread The thread that is executing the object's <code>run</code> method
   */
  public abstract void run(
    final Thread thread);

}
