/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.client.exception;

/**
 * TODO
 * @since 2.3
 */
public interface ExceptionTarget {

  /**
   * Capture exception.
   * @param exception The exception
   */
  void captureException(
    final Throwable exception);

}
