/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.logging.adapter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.logging.LogLevel;

/**
 * Maintains a registry of the SLF4J loggers and their logging levels.
 * @author Melior
 * @since 2.1
 */
public class LoggerRegistry
{
		private static LoggerRegistry instance;

		private Map<String, LoggerAdapter> loggerMap;

		private LogLevel rootLoggingLevel;

		private Map<String, LogLevel> loggingLevelMap;

	/**
	 * Constructor.
	 */
	private LoggerRegistry()
	{
				super();

				loggerMap = new HashMap<String, LoggerAdapter>();

				rootLoggingLevel = LogLevel.ERROR;

				loggingLevelMap = new LinkedHashMap<String, LogLevel>();
	}

	/**
	 * Get singleton instance.
	 * @return The singleton instance
	 */
	public static LoggerRegistry getInstance()
	{

				if (instance == null)
		{
						instance = new LoggerRegistry();
		}

		return instance;
	}

	/**
	 * Get logger.
	 * @param loggerName The logger name
	 * @return The logger
	 */
	public LoggerAdapter getLogger(
		final String loggerName)
	{
				return loggerMap.get(loggerName);
	}

	/**
	 * Add logger.
	 * @param loggerName The logger name
	 * @param logger The logger
	 */
	public void addLogger(
		final String loggerName,
		final LoggerAdapter logger)
	{
				LogLevel loggingLevel;

				loggingLevel = rootLoggingLevel;

		for (String name : loggingLevelMap.keySet())
		{

						if (loggerName.startsWith(name) == true)
			{
								loggingLevel = loggingLevelMap.get(name);
			}

		}

				logger.setLoggingLevel(loggingLevel.ordinal());

				loggerMap.put(loggerName, logger);
	}

	/**
	 * Set logging level.
	 * @param loggerName The logger name
	 * @param loggingLevel The logging level
	 */
	public void setLoggingLevel(
		final String loggerName,
		final LogLevel loggingLevel)
	{

				if ((loggerName == null) || (loggerName.equals(LoggerAdapter.ROOT_LOGGER_NAME) == true))
		{
						rootLoggingLevel = loggingLevel;

						for (LoggerAdapter logger : loggerMap.values())
			{
								logger.setLoggingLevel(loggingLevel.ordinal());
			}

		}
		else
		{
						loggingLevelMap.put(loggerName, loggingLevel);

						for (String name : loggerMap.keySet())
			{

								if (name.startsWith(loggerName) == true)
				{
										LoggerAdapter logger = loggerMap.get(name);

										logger.setLoggingLevel(loggingLevel.ordinal());
				}

			}

		}

	}

}
