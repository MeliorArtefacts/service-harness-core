/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.service.lens;

/**
 * Used to capture the parameters of a memory usage measurement.
 * @author Melior
 * @since 2.2
 */
public class Memory {

    private long count;

    private long sum;

    private long cur;

    private long min;

    private long max;

    /**
     * Constructor.
     */
    public Memory() {

        super();

        count = 0;
        sum = 0;
        cur = 0;
        min = Long.MAX_VALUE;
        max = Long.MIN_VALUE;
    }

    /**
     * Add measurement.
     * @param value The value
     */
    public void addMeasurement(
        final long value) {

        count++;
        sum += value;
        cur = value;
        min = Math.min(min, value);
        max = Math.max(max, value);
    }

    /**
     * Get string representation.
     * @return The string representation
     */
    public String toString() {
        return "current=" + format(cur) + ", minimum=" + format(min) + ", average=" + format(sum / count) + ", maximum=" + format(max);
    }
    /**
     * Format value as MBs.
     * @param value The value
     * @return The formatted value
     */
    private String format(
        final long value) {
        return String.format("%.3f MB", (value / 1048576D));
    }

}
