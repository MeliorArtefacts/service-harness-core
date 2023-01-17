/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.util.exception;
import java.util.concurrent.Callable;
import org.melior.util.resilience.NoReturnCallable;

/**
 * Utility functions that apply to exceptions.
 * @author Melior
 * @since 2.3
 */
public interface ExceptionUtil {

    /**
     * Call callable and swallow any exception that is thrown.
     * @param <T> The type
     * @param callable The callable
     * @return The result of the call, or null if the call threw an exception
     */
    public static <T> T swallow(
        final Callable<T> callable) {

        try {

            return callable.call();
        }
        catch (Exception exception) {
            return null;
        }

    }

    /**
     * Call callable and swallow any exception that is thrown.
     * @param <T> The type
     * @param callable The callable
     * @param defaultValue The default value
     * @return The result of the call, or the default value if the call threw an exception
     */
    public static <T> T swallow(
        final Callable<T> callable,
        final T defaultValue) {

        try {

            return callable.call();
        }
        catch (Exception exception) {
            return defaultValue;
        }

    }

    /**
     * Call callable and swallow any exception that is thrown.
     * @param <T> The type
     * @param callable The callable
     */
    public static <T> void swallow(
        final NoReturnCallable<T> callable) {

        try {

            callable.call();
        }
        catch (Exception exception) {
        }

    }

}
