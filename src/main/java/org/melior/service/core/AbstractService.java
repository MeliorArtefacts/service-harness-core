/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.service.core;
import org.melior.context.service.ServiceContext;
import org.melior.logging.core.Logger;
import org.melior.logging.core.LoggerFactory;
import org.melior.service.config.Configuration;
import org.melior.service.exception.ApplicationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * A base class for service implementations.  It furnishes a service with
 * important constructs like the service context, the service configuration
 * and a logger.
 * @author Melior
 * @since 2.0
 * @see {@code ServiceContext}
 * @see {@code Configuration}
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.melior")
public abstract class AbstractService
{
		protected ServiceContext serviceContext;

		protected Configuration configuration;

		protected Logger logger;

	/**
	 * Bootstrap service.
	 * @param serviceClass The service class
	 * @param args The command line arguments
	 * @param webService The web service indicator
	 * @return The application context
	 */
	public static ConfigurableApplicationContext run(
		final Class<?> serviceClass,
		final String[] args,
		final boolean webService)
	{
				ConfigurableApplicationContext applicationContext;

				applicationContext = SpringApplication.run(serviceClass, args);

		return applicationContext;
	}

	/**
	 * Constructor.
	 * @param serviceContext The service context
	 * @param webService The web service indicator
	 * @throws ApplicationException when the initialization fails
	 */
	public AbstractService(
		final ServiceContext serviceContext,
		final boolean webService) throws ApplicationException
	{
				super();

				this.serviceContext = serviceContext;

				this.configuration = serviceContext.getConfiguration();

				logger = LoggerFactory.getLogger(this.getClass().getSuperclass());

				configure();
	}

	/**
	 * Indicate whether service component is active.
	 * @return true if the service component is active, false otherwise
	 */
	public boolean isActive()
	{
		return (serviceContext.getServiceState() == ServiceState.RUNNING) || (serviceContext.getServiceState() == ServiceState.SUSPENDED);
	}

	/**
	 * Indicate whether service component is suspended.
	 * @return true if the service component is suspended, false otherwise
	 */
	public boolean isSuspended()
	{
		return (serviceContext.getServiceState() == ServiceState.SUSPENDED);
	}

	/**
	 * Configure service.
	 * @throws ApplicationException when unable to configure the service
	 */
	protected void configure() throws ApplicationException
	{
	}

}
