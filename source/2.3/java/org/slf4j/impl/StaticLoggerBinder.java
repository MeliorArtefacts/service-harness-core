/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.slf4j.impl;
import org.melior.logging.adapter.LoggerFactoryAdapter;
import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

/**
 * Binds the SLF4J logger factory to the internal logger factory and
 * enables the internal logging system.
 * @author Melior
 * @since 2.1
 */
public class StaticLoggerBinder implements LoggerFactoryBinder {

    public static String REQUESTED_API_VERSION = "1.7.16";

    private static StaticLoggerBinder instance = new StaticLoggerBinder();

    private ILoggerFactory loggerFactory; {

        System.setProperty("org.springframework.boot.logging.LoggingSystem", "org.melior.logging.core.LoggingSystem");
        System.setProperty("org.jboss.logging.provider", "slf4j");
        System.setProperty("logging.level.root", "WARN");
    }

    /**
     * Constructor.
     */
    private StaticLoggerBinder() {

        super();

        loggerFactory = new LoggerFactoryAdapter();
    }

    /**
     * Get singleton instance.
     * @return The singleton instance
     */
    public static final StaticLoggerBinder getSingleton() {
        return instance;
    }

    /**
     * Get logger factory.
     * @return The logger factory
     */
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    /**
     * Get logger factory class name.
     * @return The logger factory class name
     */
    public String getLoggerFactoryClassStr() {
        return loggerFactory.getClass().getName();
    }

}
