/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.util.resilience;

/**
 * A task that may throw an exception.
 * Implementors define a single method with no arguments called
 * {@code call}.
 * <p>
 * The {@code NoReturnCallable} interface is similar to {@code
 * Runnable}.  A {@code Runnable}, however, cannot throw a
 * checked exception.
 * @author Melior
 * @since 2.2
 * @see Runnable
 */
@FunctionalInterface
public interface NoReturnCallable<V> {

    /**
     * Computes a result, or throws an exception if unable to do so.
     * @throws Exception if unable to compute a result
     */
    void call() throws Exception;

}
