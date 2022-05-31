/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.util.time;
import java.util.concurrent.TimeUnit;

/**
 * Base class for simple timers.
 * @author Melior
 * @since 2.2
 */
public interface Timer
{

	/**
	 * Get instance of timer with millisecond precision.
	 * The instance is not started yet.
	 * @return The timer
	 */
	public static MilliTimer ofMillis()
	{
		return new MilliTimer();
	}

	/**
	 * Get instance of timer with nanosecond precision.
	 * The instance is not started yet.
	 * @return The timer
	 */
	public static NanoTimer ofNanos()
	{
		return new NanoTimer();
	}

	/**
	 * Start the timer.
	 * @return The timer
	 */
	public Timer start();

	/**
	 * Stop the timer.
	 * @return The timer
	 */
	public Timer stop();

	/**
	 * Get the start time in the native time unit.
	 * @return The start time
	 */
	public long startTime();

	/**
	 * Get the stop time in the native time unit.
	 * @return The stop time
	 */
	public long stopTime();

	/**
	 * Get the elapsed time in the native time unit.
	 * @return The elapsed time
	 */
	public long elapsedTime();

	/**
	 * Get the elapsed time in the given time unit.
	 * @param timeUnit The time unit
	 * @return The elapsed time
	 */
	public long elapsedTime(
		final TimeUnit timeUnit);

}
