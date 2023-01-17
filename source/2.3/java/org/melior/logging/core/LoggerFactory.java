/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.logging.core;

/**
 * Implementation of the internal logger factory.
 * @author Melior
 * @since 2.1
 */
public class LoggerFactory {

    /**
     * Constructor.
     */
    private LoggerFactory() {

        super();
    }

    /**
     * Get logger.
     * @param ownerClass The owner class
     * @return The logger
     */
    public static Logger getLogger(
        final Class<?> ownerClass) {

        return getLogger(ownerClass.getName());
    }

    /**
     * Get logger.
     * @param loggerName The logger name
     * @return The logger
     */
    public static synchronized Logger getLogger(
        final String loggerName) {

        return new LoggerFacade(loggerName, LoggerContext.get());
    }

}
