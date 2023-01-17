/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.util.time;
import java.time.LocalDateTime;

/**
 * Implements a more accurate {@code LocalDateTime}.  It drops an
 * anchor and uses a nanosecond time for better accuracy.
 * @author Melior
 * @since 2.1
 */
public class AccurateLocalDateTime {

    private static LocalDateTime anchorDateTime;

    private static long anchorNanos;

    static {

        LocalDateTime currentDateTime;

        currentDateTime = LocalDateTime.now();

        while ((anchorDateTime = LocalDateTime.now()).equals(currentDateTime) == true) {
            anchorNanos = System.nanoTime();
        }

        anchorNanos = System.nanoTime();
    }

    /**
     * Constructor. 
     */
    private AccurateLocalDateTime() {

        super();
    }

    /**
     * Get current local date time.
     * @return The local date time
     */
    public static LocalDateTime now() {

        return anchorDateTime.plusNanos(System.nanoTime() - anchorNanos);
    }

}
