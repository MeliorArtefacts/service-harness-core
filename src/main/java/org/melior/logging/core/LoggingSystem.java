/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.logging.core;
import java.util.Collections;
import java.util.List;
import org.melior.logging.adapter.LoggerRegistry;
import org.springframework.boot.logging.LogFile;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggingInitializationContext;
import org.springframework.core.env.Environment;

/**
 * Initializes the internal logging system.
 * @author Melior
 * @since 2.1
 */
public class LoggingSystem extends org.springframework.boot.logging.LoggingSystem
{

	/**
	 * Constructor.
	 * @param classLoader The class loader
	 */
	public LoggingSystem(
		final ClassLoader classLoader)
	{
				super();
	}

	/**
	 * Prepare logging system for initialization.
	 */
	public void beforeInitialize()
	{
	}

	/**
	 * Initialize logging system.
	 * @param initializationContext The logging initialization context
	 * @param configLocation The logging configuration location
	 * @param logFile The log file name/path
	 */
	public void initialize(
		final LoggingInitializationContext initializationContext,
		final String configLocation,
		final LogFile logFile)
	{
				Environment environment;
		LoggerContext loggerContext;

				environment = initializationContext.getEnvironment();

				loggerContext = LoggerContext.getInstance();

				loggerContext.initialize(environment);
	}

	/**
	 * Set logging level for logger.
	 * @param loggerName The logger name
	 * @param loggingLevel The logging level
	 */
	public void setLogLevel(
		final String loggerName,
		final LogLevel loggingLevel) 
	{
				LoggerRegistry loggerRegistry;

				loggerRegistry = LoggerRegistry.getInstance();

				loggerRegistry.setLoggingLevel((loggerName == null) ? ROOT_LOGGER_NAME : loggerName, loggingLevel);
	}

	/**
	 * Get logger configurations.
	 * @return The list of logger configurations
	 */
	public List<LoggerConfiguration> getLoggerConfigurations()
	{
		return Collections.emptyList();
	}

	/**
	 * Get logger configuration.
	 * @param loggerName The logger name
	 * @return The logger configuration
	 */
	public LoggerConfiguration getLoggerConfiguration(
		final String loggerName)
	{
		return null;
	}

}
