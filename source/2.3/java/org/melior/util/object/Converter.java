/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.util.object;

/**
 * Converts from one type of object to another.
 * @author Melior
 * @since 2.3
 */
@FunctionalInterface
public interface Converter<T, U> {

    /**
     * Convert from one type of object to another.
     * @param obj The input object
     * @return The output object
     */
    public U convert(
        final T obj);

}
