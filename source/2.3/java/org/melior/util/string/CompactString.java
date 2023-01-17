/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.util.string;

/**
 * Interface for objects that wish to provide a more compact {@code String}
 * than what is normally returned by the {@code toString} method.
 * @author Melior
 * @since 2.3
 */
public interface CompactString  {

    /**
     * Returns a string representation of the object, in compact form.
     * @return The compact string representation of the object
     */
    public String toCompactString();

}
