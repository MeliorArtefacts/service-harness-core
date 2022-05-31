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
 * Implements a simple timer with millisecond precision.
 * @author Melior
 * @since 2.2
 * @see {@code Timer}
 */
public class MilliTimer implements Timer
{
		private long startTime = 0;

		private long stopTime = 0;

    /**
     * Constructor. 
     */
	MilliTimer()
	{
				super();
	}

	/**
	 * Start the timer.
	 * @return The timer
	 */
	public Timer start()
	{
				startTime = System.currentTimeMillis();

		return this;
	}

	/**
	 * Stop the timer.
	 * @return The timer
	 */
	public Timer stop()
	{
				stopTime = System.currentTimeMillis();

		return this;
	}

	/**
	 * Get the start time in the native time unit.
	 * @return The start time
	 */
	public long startTime()
	{
		return startTime;
	}

	/**
	 * Get the stop time in the native time unit.
	 * @return The stop time
	 */
	public long stopTime()
	{
		return stopTime;
	}

	/**
	 * Get the elapsed time in the native time unit.
	 * @return The elapsed time
	 */
	public long elapsedTime()
	{
		return ((stopTime == 0) ? System.currentTimeMillis() : stopTime) - startTime;
	}

	/**
	 * Get the elapsed time in the given time unit.
	 * @param timeUnit The time unit
	 * @return The elapsed time
	 */
	public long elapsedTime(
		final TimeUnit timeUnit)
	{
		return timeUnit.convert(elapsedTime(), TimeUnit.MILLISECONDS);
	}

}
