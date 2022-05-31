/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.service.config;
import org.melior.service.exception.ApplicationException;
import org.melior.service.exception.ExceptionType;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Provides access to the service configuration.  This construct is available
 * to the service and all of its components.  The service configuration may be
 * accessed anywhere and at any time, rather than only during service startup
 * and during configuration refresh.
 * @author Melior
 * @since 2.0
 */
@Component
public class Configuration
{
		private Environment environment;

	/**
	 * Constructor.
	 * @param environment The environment
	 */
	public Configuration(
		final Environment environment)
	{
				super();

				this.environment = environment;
	}

	/**
	 * Get property value.
	 * @param name The name of the property
	 * @return The value of the property
	 * @throws ApplicationException if the property does not exist
	 */
	public String getProperty(
		final String name) throws ApplicationException
	{
				String value;

				value = environment.getProperty(name, (String) null);

				if (value == null)
		{
			throw new ApplicationException(ExceptionType.LOCAL_APPLICATION, "Configuration property [" + name + "] not found.");
		}

				value = environment.resolvePlaceholders(value);

		return value;
	}

	/**
	 * Get property value.
	 * @param name The name of the property
	 * @param clazz The class of the property
	 * @return The value of the property
	 * @throws ApplicationException if the property does not exist
	 */
	public <T> T getProperty(
		final String name,
		final Class<T> clazz) throws ApplicationException
	{
				T value;

				value = environment.getProperty(name, clazz, null);

				if (value == null)
		{
			throw new ApplicationException(ExceptionType.LOCAL_APPLICATION, "Configuration property [" + name + "] not found.");
		}

		return value;
	}

	/**
	 * Get property value.
	 * @param name The name of the property
	 * @param defaultValue A default value for the property
	 * @return The value of the property, or defaultValue if the property does not exist
	 */
	public String getProperty(
		final String name,
		final String defaultValue)
	{
				String value;

				value = environment.getProperty(name, defaultValue);

				value = environment.resolvePlaceholders(value);

		return value;
	}

	/**
	 * Get property value.
	 * @param name The name of the property
	 * @param clazz The class of the property
	 * @param defaultValue A default value for the property
	 * @return The value of the property, or defaultValue if the property does not exist
	 */
	public <T> T getProperty(
		final String name,
		final Class<T> clazz,
		final T defaultValue)
	{
				T value;

				value = environment.getProperty(name, clazz, defaultValue);

		return value;
	}

}
