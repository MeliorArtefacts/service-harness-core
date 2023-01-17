/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.logging.appender;
import java.util.List;
import org.melior.context.transaction.Argument;
import org.melior.service.config.Configuration;
import org.melior.service.exception.ApplicationException;
import org.springframework.boot.logging.LogLevel;

/**
 * Base class for internal log appenders that write trace logs
 * and/or transaction logs.  Provides access to the logging
 * stream and to the logger configuration to allow an appender
 * to configure itself.
 * @author Melior
 * @since 2.1
 * @see Stream
 * @see Configuration
 */
public abstract class Appender {

    protected Stream stream;

    protected AppenderConfig configuration;

    /**
     * Constructor.
     * @param stream The stream
     * @param configuration The configuration
     * @throws ApplicationException if an error occurs during the construction
     */
    protected Appender(
        final Stream stream,
        final AppenderConfig configuration) throws ApplicationException {

        super();

        this.stream = stream;

        this.configuration = configuration;

        configure();
    }

    /**
     * Write trace event to log.
     * @param day The day
     * @param timestamp The timestamp
     * @param loggingLevel The logging level
     * @param hostName The host name
     * @param threadId The thread identifier
     * @param transactionId The transaction identifier
     * @param location The location [caller]
     * @param messageParts The message parts
     * @param throwable The throwable
     */
    public abstract void write(
        final int day,
        final String timestamp,
        final LogLevel loggingLevel,
        final String hostName,
        final String threadId,
        final String transactionId,
        final String location,
        final Object[] messageParts,
        final Throwable throwable);

    /**
     * Write transaction event to log.
     * @param day The day
     * @param timestamp The timestamp
     * @param hostName The host name
     * @param threadId The thread identifier
     * @param transactionId The transaction identifier
     * @param location The location [caller]
     * @param transactionType The transaction type
     * @param status The transaction status
     * @param duration The transaction duration
     * @param argumentList The transaction argument list
     * @param stackTracePrefix The stack trace prefix
     * @param throwable The throwable
     */
    public abstract void write(
        final int day,
        final String timestamp,
        final String hostName,
        final String threadId,
        final String transactionId,
        final String location,
        final String transactionType,
        final String status,
        final long duration,
        final List<Argument> argumentList,
        final String stackTracePrefix,
        final Throwable throwable);

    /**
     * Configure appender.
     * @throws ApplicationException if unable to configure the appender
     */
    protected void configure() throws ApplicationException {
    }

}
