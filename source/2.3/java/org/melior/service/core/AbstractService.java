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
 * @see ServiceContext
 * @see Configuration
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.melior")
public abstract class AbstractService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getSuperclass());

    protected ServiceContext serviceContext;

    protected Configuration configuration;

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
        final boolean webService) {

        ConfigurableApplicationContext applicationContext;

        applicationContext = SpringApplication.run(serviceClass, args);

        return applicationContext;
    }

    /**
     * Constructor.
     * @param serviceContext The service context
     * @param webService The web service indicator
     * @throws ApplicationException if an error occurs during the construction
     */
    public AbstractService(
        final ServiceContext serviceContext,
        final boolean webService) throws ApplicationException {

        super();

        this.serviceContext = serviceContext;

        this.configuration = ServiceContext.getConfiguration();

        configure();
    }

    /**
     * Indicate whether service component is active.
     * @return true if the service component is active, false otherwise
     */
    public boolean isActive() {
        return ServiceState.isActive();
    }

    /**
     * Indicate whether service component is suspended.
     * @return true if the service component is suspended, false otherwise
     */
    public boolean isSuspended() {
        return ServiceState.isSuspended();
    }

    /**
     * Configure service.
     * @throws ApplicationException if unable to configure the service
     */
    protected void configure() throws ApplicationException {
    }

}
