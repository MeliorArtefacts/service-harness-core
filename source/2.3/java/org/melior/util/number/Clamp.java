/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.number;

/**
 * Clamps a value between the minimum and maximum values provided.
 * @author Melior
 * @since 2.1
 */
public interface Clamp{

  /**
   * Clamp {@code int} value between minimum and maximum.
   * @param value The value
   * @param min The minimum
   * @param max The maximum
   * @return The clamped value
   */
  public static int clampInt(
    final int value,
    final int min,
    final int max){
    return (value > max) ? max : (value < min) ? min : value;
  }

  /**
   * Clamp {@code float} value between minimum and maximum.
   * @param value The value
   * @param min The minimum
   * @param max The maximum
   * @return The clamped value
   */
  public static float clampFloat(
    final float value,
    final float min,
    final float max){
    return (value > max) ? max : (value < min) ? min : value;
  }

  /**
   * Clamp {@code long} value between minimum and maximum.
   * @param value The value
   * @param min The minimum
   * @param max The maximum
   * @return The clamped value
   */
  public static long clampLong(
    final long value,
    final long min,
    final long max){
    return (value > max) ? max : (value < min) ? min : value;
  }

  /**
   * Clamp {@code double} value between minimum and maximum.
   * @param value The value
   * @param min The minimum
   * @param max The maximum
   * @return The clamped value
   */
  public static double clampDouble(
    final double value,
    final double min,
    final double max){
    return (value > max) ? max : (value < min) ? min : value;
  }

}
