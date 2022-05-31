/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.component.core;
import org.melior.context.service.ServiceContext;
import org.melior.logging.core.Logger;
import org.melior.logging.core.LoggerFactory;
import org.melior.service.config.Configuration;
import org.melior.service.core.ServiceState;
import org.melior.service.exception.ApplicationException;

/**
 * A base class for service components.  It furnishes a service component
 * with important constructs like the service context, the service configuration
 * and a logger.
 * @author Melior
 * @since 2.0
 * @see {@code ServiceContext}
 * @see {@code Configuration}
 */
public class ServiceComponent
{
		protected ServiceContext serviceContext;

		protected Configuration configuration;

		protected Logger logger;

	/**
	 * Constructor.
	 * @param serviceContext The service context
	 * @throws ApplicationException when the initialization fails
	 */
	public ServiceComponent(
		final ServiceContext serviceContext) throws ApplicationException
	{
				super();

				this.serviceContext = serviceContext;

				this.configuration = serviceContext.getConfiguration();

				logger = LoggerFactory.getLogger(this.getClass());

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
	 * Configure service component.
	 * @throws ApplicationException when unable to configure the service component
	 */
	protected void configure() throws ApplicationException
	{
	}

}
