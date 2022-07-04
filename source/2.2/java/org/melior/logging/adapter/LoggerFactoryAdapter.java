/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.logging.adapter;
import org.melior.logging.core.LoggerFactory;
import org.slf4j.Logger;

/**
 * Adapts the SLF4J logger factory to the internal logger factory.
 * @author Melior
 * @since 2.1
 */
public class LoggerFactoryAdapter implements org.slf4j.ILoggerFactory{

  /**
   * Constructor.
   */
  public LoggerFactoryAdapter(){
        super();
  }

  /**
   * Get logger.
   * @param loggerName The logger name
   * @return The logger
   */
  public Logger getLogger(
    final String loggerName){
        LoggerRegistry loggerRegistry;
    LoggerAdapter logger;

        loggerRegistry = LoggerRegistry.getInstance();

        logger = loggerRegistry.getLogger(loggerName);

        if (logger == null){
            logger = new LoggerAdapter(LoggerFactory.getLogger(loggerName));

            loggerRegistry.addLogger(loggerName, logger);
    }

    return logger;
  }

}
