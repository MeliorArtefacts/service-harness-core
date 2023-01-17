/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.logging.core;
import java.util.Arrays;
import org.melior.context.transaction.TransactionContext;
import org.springframework.boot.logging.LogLevel;

/**
 * Binds the individual internal loggers to the internal logger context.
 * @author Melior
 * @since 2.1
 * @see {@code LoggerContext}
 */
class LoggerFacade implements Logger {

    private static final int LOGGING_LEVEL_TRACE = LogLevel.TRACE.ordinal();
    private static final int LOGGING_LEVEL_DEBUG = LogLevel.DEBUG.ordinal();
    private static final int LOGGING_LEVEL_INFO = LogLevel.INFO.ordinal();
    private static final int LOGGING_LEVEL_WARN = LogLevel.WARN.ordinal();
    private static final int LOGGING_LEVEL_ERROR = LogLevel.ERROR.ordinal();

    private String loggerName;

    private LoggerContext logger;

    /**
     * Constructor.
     * @param loggerName The logger name
     * @param logWriter The logger
     */
    public LoggerFacade(
        final String loggerName,
        final LoggerContext logger) {

        super();

        this.loggerName = loggerName;

        this.logger = logger;
    }

    /**
     * Get name.
     * @return The name
     */
    public String getName() {
        return loggerName;
    }

    /**
     * Get logger.
     * @return The logger
     */
    LoggerContext getLogger() {
        return logger;
    }

    /**
     * Indicate whether error level logging is enabled.
     * @return true if error level logging is enabled, false otherwise
     */
    public boolean isErrorEnabled() {
        return logger.getLoggingLevel() <= LOGGING_LEVEL_ERROR;
    }

    /**
     * Log message at error logging level, if it is enabled.
     * @param methodName The method name
     * @param messageParts The message parts
     */
    public void error(
        final String methodName,
        final Object... messageParts) {

        Object messagePart;

        if (isErrorEnabled() == true) {

            messagePart = messageParts[messageParts.length - 1];

            if (messagePart instanceof Throwable) {

                logger.write(LogLevel.ERROR, loggerName, methodName, Arrays.copyOf(messageParts, messageParts.length - 1), (Throwable) messagePart);
            }
            else {

                logger.write(LogLevel.ERROR, loggerName, methodName, messageParts, null);
            }

        }

    }

    /**
     * Indicate whether warn level logging is enabled.
     * @return true if warn level logging is enabled, false otherwise
     */
    public boolean isWarnEnabled() {
        return logger.getLoggingLevel() <= LOGGING_LEVEL_WARN;
    }

    /**
     * Log message at warn logging level, if it is enabled.
     * @param methodName The method name
     * @param messageParts The message parts
     */
    public void warn(
        final String methodName,
        final Object... messageParts) {

        if (isWarnEnabled() == true) {

            logger.write(LogLevel.WARN, loggerName, methodName, messageParts, null);
        }

    }

    /**
     * Indicate whether info level logging is enabled.
     * @return true if info level logging is enabled, false otherwise
     */
    public boolean isInfoEnabled() {
        return logger.getLoggingLevel() <= LOGGING_LEVEL_INFO;
    }

    /**
     * Log message at info logging level, if it is enabled.
     * @param methodName The method name
     * @param messageParts The message parts
     */
    public void info(
        final String methodName,
        final Object... messageParts) {

        if (isInfoEnabled() == true) {

            logger.write(LogLevel.INFO, loggerName, methodName, messageParts, null);
        }

    }

    /**
     * Indicate whether debug level logging is enabled.
     * @return true if debug level logging is enabled, false otherwise
     */
    public boolean isDebugEnabled() {
        return logger.getLoggingLevel() <= LOGGING_LEVEL_DEBUG;
    }

    /**
     * Log message at debug logging level, if it is enabled.
     * @param methodName The method name
     * @param messageParts The message parts
     */
    public void debug(
        final String methodName,
        final Object... messageParts) {

        if (isDebugEnabled() == true) {

            logger.write(LogLevel.DEBUG, loggerName, methodName, messageParts, null);
        }

    }

    /**
     * Indicate whether trace level logging is enabled.
     * @return true if trace level logging is enabled, false otherwise
     */
    public boolean isTraceEnabled() {
        return logger.getLoggingLevel() == LOGGING_LEVEL_TRACE;
    }

    /**
     * Log message at trace logging level, if it is enabled.
     * @param methodName The method name
     * @param messageParts The message parts
     */
    public void trace(
        final String methodName,
        final Object... messageParts) {

        if (isTraceEnabled() == true) {

            logger.write(LogLevel.TRACE, loggerName, methodName, messageParts, null);
        }

    }

    /**
     * Log successful transaction.
     * @param methodName The method name
     * @param transactionContext The transaction context
     */
    public void transaction(
        final String methodName,
        final TransactionContext transactionContext) {

        logger.write(loggerName, methodName, transactionContext, null, null);
    }

    /**
     * Log failed transaction.
     * @param methodName The method name
     * @param transactionContext The transaction context
     * @param stackTracePrefix The stack trace prefix, or null if no prefix is required
     * @param throwable The throwable
     */

    public void transaction(
        final String methodName,
        final TransactionContext transactionContext,
        final String stackTracePrefix,
        final Throwable throwable) {

        logger.write(loggerName, methodName, transactionContext, stackTracePrefix, throwable);
    }

}
