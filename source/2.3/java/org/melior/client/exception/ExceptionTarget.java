/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.client.exception;

/**
 * Captures an exception that occurred in a remoting client while opening a new connection
 * or while executing a remoting request.  If the calling object is a managed client object
 * then the exception is propagated to the client objects parent.
 * @since 2.3
 */
public interface ExceptionTarget  {

    /**
     * Capture exception.
     * @param exception The exception
     */
    void captureException(
        final Throwable exception);

}
