/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.context.service;
import java.net.InetAddress;
import org.melior.service.config.Configuration;
import org.melior.service.core.ServiceState;
import org.melior.service.work.WorkManager;
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
 * @see ServiceState
 * @see Configuration
 */
@Component
@ComponentScan(basePackages = "org.melior")
public class ServiceContext {

    private static String serviceName;

    private static String hostName;

    private static String environmentName;

    private static Configuration configuration;

    private static WorkManager workManager;

    /**
     * Constructor.
     * @param configuration The configuration
     * @param workManager The work manager
     */
    public ServiceContext(
        final Configuration configuration,
        @Lazy final WorkManager workManager) {

        super();

        ServiceState.set(ServiceState.RUNNING);

        serviceName = configuration.getProperty("service.name", configuration.getProperty("spring.application.name", "application"));

        try {

            hostName = InetAddress.getLocalHost().getHostName();
        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to retrieve host name.", exception);
        }

        environmentName = configuration.getProperty("environment.name", hostName);

        ServiceContext.configuration = configuration;

        ServiceContext.workManager = workManager;
    }

    /**
     * Get service name.
     * @return The service name
     */
    public static final String getServiceName() {
        return serviceName;
    }

    /**
     * Get host name.
     * @return The host name
     */
    public static final String getHostName() {
        return hostName;
    }

    /**
     * Get environment name.
     * @return The environment name
     */
    public static final String getEnvironmentName() {
        return environmentName;
    }

    /**
     * Get configuration.
     * @return The configuration
     */
    public static final Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Get work manager.
     * @return The work manager
     */
    public static WorkManager getWorkManager() {
        return workManager;
    }

    /**
     * Set work manager.
     * @param workManager The work manager
     */
    public static void setWorkManager(
        final WorkManager workManager) {
        ServiceContext.workManager = workManager;
    }

}
