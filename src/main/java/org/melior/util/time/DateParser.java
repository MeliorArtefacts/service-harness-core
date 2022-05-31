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
 * Implements a simple ISO 8601 compliant timestamp and date parser.
 * @author Melior
 * @since 2.0
 */
public class DateParser
{
		public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Constructor. 
     */
	private DateParser()
	{
				super();
	}

	/**
	 * Parse timestamp from string.
	 * @param string The string to parse
	 * @return The timestamp
	 * @throws Exception when unable to parse the string
	 */
	public static LocalDateTime parseTimestamp(
		final String string) throws Exception
	{

				if ((string == null) || (string.length() == 0))
		{
			return null;
		}

				return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT));
	}

	/**
	 * Parse timestamp from string.
	 * @param string The string to parse
	 * @param customFormat The custom format
	 * @return The timestamp
	 * @throws Exception when unable to parse the string
	 */
	public static LocalDateTime parseTimestamp(
		final String string,
		final String customFormat) throws Exception
	{

				if ((string == null) || (string.length() == 0))
		{
			return null;
		}

				return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(customFormat));
	}

	/**
	 * Parse date from string.
	 * @param string The string to parse
	 * @return The date
	 * @throws Exception when unable to parse the string
	 */
	public static LocalDate parseDate(
		final String string) throws Exception
	{

				if ((string == null) || (string.length() == 0))
		{
			return null;
		}

				return LocalDate.parse(string, DateTimeFormatter.ofPattern(DATE_FORMAT));
	}

	/**
	 * Parse date from string.
	 * @param string The string to parse
	 * @param customFormat The custom format
	 * @return The date
	 * @throws Exception when unable to parse the string
	 */
	public static LocalDate parseDate(
		final String string,
		final String customFormat) throws Exception
	{

				if ((string == null) || (string.length() == 0))
		{
			return null;
		}

				return LocalDate.parse(string, DateTimeFormatter.ofPattern(customFormat));
	}

}
