/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.util.time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Implements a simple ISO 8601 compliant timestamp and date formatter.
 * @author Melior
 * @since 2.0
 */
public class DateFormatter
{
		public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Constructor. 
     */
	private DateFormatter()
	{
				super();
	}

	/**
	 * Format timestamp to string.
	 * @param timestamp The timestamp to format
	 * @return The timestamp string
	 */
    public static String formatTimestamp(
		final LocalDateTime timestamp)
	{

				if (timestamp == null)
		{
			return null;
		}

				return DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT).format(timestamp);
	}

	/**
	 * Format timestamp to string.
	 * @param timestamp The timestamp to format
	 * @param customFormat The custom format
	 * @return The timestamp string
	 */
	public static String formatTimestamp(
		final LocalDateTime timestamp,
		final String customFormat)
	{

				if (timestamp == null)
		{
			return null;
		}

				return DateTimeFormatter.ofPattern(customFormat).format(timestamp);
	}

	/**
	 * Format date to string.
	 * @param date The date to format
	 * @return The date string
	 */
	public static String formatDate(
		final LocalDate date)
	{

				if (date == null)
		{
			return null;
		}

				return DateTimeFormatter.ofPattern(DATE_FORMAT).format(date);
	}

	/**
	 * Format date to string.
	 * @param date The date to format
	 * @param customFormat The custom format
	 * @return The date string
	 */
	public static String formatDate(
		final LocalDate date,
		final String customFormat)
	{

				if (date == null)
		{
			return null;
		}

				return DateTimeFormatter.ofPattern(customFormat).format(date);
	}

}
