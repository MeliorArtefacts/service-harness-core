/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.context.service;
import org.melior.service.config.Configuration;
import org.melior.service.core.ServiceState;
import org.melior.service.core.WorkManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Implements a service context that allows important parameters and
 * constructs to be visible to all components of the service without
 * having to inject the parameters and constructs into each component
 * individually.
 * <p>
 * For example, all the components of the service can see the status
 * of the service and can immediately react to changes in the status.
 * @author Melior
 * @since 2.0
 * @see {@code ServiceState}
 * @see {@code Configuration}
 */
@Component
@ComponentScan(basePackages = "org.melior")
public class ServiceContext
{
		private ServiceState serviceState;

		private String serviceName;

		private String hostName;

		private String environmentName;

		private Configuration configuration;

		private WorkManager workManager;

	/**
	 * Constructor.
	 * @param configuration The configuration
	 * @param workManager The work manager
	 */
	public ServiceContext(
		final Configuration configuration,
		@Lazy final WorkManager workManager)
	{
				super();

				serviceState = ServiceState.RUNNING;

				serviceName = configuration.getProperty("service.name", configuration.getProperty("spring.application.name", "application"));

		try
		{
						hostName = java.net.InetAddress.getLocalHost().getHostName();
		}
		catch (java.lang.Exception exception)
		{
			throw new RuntimeException("Failed to retrieve host name.", exception);
		}

				environmentName = configuration.getProperty("environment.name", hostName);

				this.configuration = configuration;

				this.workManager = workManager;
	}

	/**
	 * Get service state.
	 * @return The service state
	 */
	public final ServiceState getServiceState()
	{
		return serviceState;
	}

	/**
	 * Set service state.
	 * @param serviceState The service state
	 */
	public final void setServiceState(
		final ServiceState serviceState)
	{
		this.serviceState = serviceState;
	}

	/**
	 * Get service name.
	 * @return The service name
	 */
	public final String getServiceName()
	{
		return serviceName;
	}

	/**
	 * Get host name.
	 * @return The host name
	 */
	public final String getHostName()
	{
		return hostName;
	}

	/**
	 * Get environment name.
	 * @return The environment name
	 */
	public final String getEnvironmentName()
	{
		return environmentName;
	}

	/**
	 * Get configuration.
	 * @return The configuration
	 */
	public final Configuration getConfiguration()
	{
		return configuration;
	}

	/**
	 * Get work manager.
	 * @return The work manager
	 */
	public WorkManager getWorkManager()
	{
		return workManager;
	}

	/**
	 * Set work manager.
	 * @param workManager The work manager
	 */
	public void setWorkManager(
		final WorkManager workManager)
	{
		this.workManager = workManager;
	}

}
