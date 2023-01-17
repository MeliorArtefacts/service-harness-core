/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.logging.adapter;
import java.lang.reflect.Method;
import org.melior.logging.core.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.boot.logging.LogLevel;

/**
 * Adapts the SLF4J logger to the internal logger.
 * @author Melior
 * @since 2.1
 */
public class LoggerAdapter implements org.slf4j.Logger {

    private static final int LOGGING_LEVEL_TRACE = LogLevel.TRACE.ordinal();
    private static final int LOGGING_LEVEL_DEBUG = LogLevel.DEBUG.ordinal();
    private static final int LOGGING_LEVEL_INFO = LogLevel.INFO.ordinal();
    private static final int LOGGING_LEVEL_WARN = LogLevel.WARN.ordinal();
    private static final int LOGGING_LEVEL_ERROR = LogLevel.ERROR.ordinal();
    private static final int LOGGING_LEVEL_FATAL = LogLevel.FATAL.ordinal();

    private static int stackTraceIndex;

    private static Method stackTraceMethod;

    private Logger logger;

    private int loggingLevel;

    /**
     * Constructor.
     * @param logger The logger
     */
    public LoggerAdapter(
        final Logger logger) {

        super();

        this.logger = logger;

        loggingLevel = LOGGING_LEVEL_ERROR;
    }

    /**
     * Get name.
     * @return The name
     */
    public String getName() {
        return logger.getName();
    }

    /**
     * Set logging level.
     * @param loggingLevel The logging level
     */
    public void setLoggingLevel(
        final int loggingLevel) {

        this.loggingLevel = loggingLevel;
    }

    /**
     * Indicate whether error level logging is enabled.
     * @return true if error level logging is enabled, false otherwise
     */
    public boolean isErrorEnabled() {
        return loggingLevel <= LOGGING_LEVEL_FATAL;
    }

    /**
     * Log message at error logging level, if it is enabled.
     * @param message The message
     */
    public void error(
        final String message) {

        if (isErrorEnabled() == true) {

            logger.error(getMethodName(), message);
        }

    }

    /**
     * Log message at error logging level, if it is enabled.
     * @param format The format
     * @param arg The argument
     */
    public void error(
        final String format,
        final Object arg) {

        if (isErrorEnabled() == true) {

            logger.error(getMethodName(), MessageFormatter.format(format, arg).getMessage());
        }

    }

    /**
     * Log message at error logging level, if it is enabled.
     * @param format The format
     * @param arg1 The argument
     * @param arg2 The argument
     */
    public void error(
        final String format,
        final Object arg1,
        final Object arg2) {

        if (isErrorEnabled() == true) {

            logger.error(getMethodName(), MessageFormatter.format(format, arg1, arg2).getMessage());
        }

    }

    /**
     * Log message at error logging level, if it is enabled.
     * @param format The format
     * @param arguments The arguments
     */
    public void error(
        final String format,
        final Object... arguments) {

        if (isErrorEnabled() == true) {

            logger.error(getMethodName(), MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

    }

    /**
     * Log message at error logging level, if it is enabled.
     * @param message The message
     * @param throwable The throwable
     */
    public void error(
        final String message,
        final Throwable throwable) {

        if (throwable != null) {

            if (isErrorEnabled() == true) {

                logger.error(getMethodName(), message, throwable);
            }

        }
        else {

            if (isErrorEnabled() == true) {

                logger.error(getMethodName(), message);
            }

        }

    }

    /**
     * Indicate whether error level logging is enabled.
     * @param marker The marker
     * @return true if error level logging is enabled, false otherwise
     */
    public boolean isErrorEnabled(
        final Marker marker) {
        return loggingLevel <= LOGGING_LEVEL_FATAL;
    }

    /**
     * Log message at error logging level, if it is enabled.
     * @param marker The marker
     * @param message The message
     */
    public void error(
        final Marker marker,
        final String message) {

        if (isErrorEnabled(marker) == true) {

            logger.error(getMethodName(), message);
        }

    }

    /**
     * Log message at error logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arg The argument
     */
    public void error(
        final Marker marker,
        final String format,
        final Object arg) {

        if (isErrorEnabled(marker) == true) {

            logger.error(getMethodName(), MessageFormatter.format(format, arg).getMessage());
        }

    }

    /**
     * Log message at error logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arg1 The argument
     * @param arg2 The argument
     */
    public void error(
        final Marker marker,
        final String format,
        final Object arg1,
        final Object arg2) {

        if (isErrorEnabled(marker) == true) {

            logger.error(getMethodName(), MessageFormatter.format(format, arg1, arg2).getMessage());
        }

    }

    /**
     * Log message at error logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arguments The arguments
     */
    public void error(
        final Marker marker,
        final String format,
        final Object... arguments) {

        if (isErrorEnabled(marker) == true) {

            logger.error(getMethodName(), MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

    }

    /**
     * Log message at error logging level, if it is enabled.
     * @param marker The marker
     * @param message The message
     * @param throwable The throwable
     */
    public void error(
        final Marker marker,
        final String message,
        final Throwable throwable) {

        if (throwable != null) {

            if (isErrorEnabled(marker) == true) {

                logger.error(getMethodName(), message, throwable);
            }

        }
        else {

            if (isErrorEnabled(marker) == true) {

                logger.error(getMethodName(), message);
            }

        }

    }

    /**
     * Indicate whether warn level logging is enabled.
     * @return true if warn level logging is enabled, false otherwise
     */
    public boolean isWarnEnabled() {
        return loggingLevel <= LOGGING_LEVEL_WARN;
    }

    /**
     * Log message at warn logging level, if it is enabled.
     * @param message The message
     */
    public void warn(
        final String message) {

        if (isWarnEnabled() == true) {

            logger.warn(getMethodName(), message);
        }

    }

    /**
     * Log message at warn logging level, if it is enabled.
     * @param format The format
     * @param arg The argument
     */
    public void warn(
        final String format,
        final Object arg) {

        if (isWarnEnabled() == true) {

            logger.warn(getMethodName(), MessageFormatter.format(format, arg).getMessage());
        }

    }

    /**
     * Log message at warn logging level, if it is enabled.
     * @param format The format
     * @param arg1 The argument
     * @param arg2 The argument
     */
    public void warn(
        final String format,
        final Object arg1,
        final Object arg2) {

        if (isWarnEnabled() == true) {

            logger.warn(getMethodName(), MessageFormatter.format(format, arg1, arg2).getMessage());
        }

    }

    /**
     * Log message at warn logging level, if it is enabled.
     * @param format The format
     * @param arguments The arguments
     */
    public void warn(
        final String format,
        final Object... arguments) {

        if (isWarnEnabled() == true) {

            logger.warn(getMethodName(), MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

    }

    /**
     * Log message at warn logging level, if it is enabled.
     * @param message The message
     * @param throwable The throwable
     */
    public void warn(
        final String message,
        final Throwable throwable) {

        if (throwable != null) {

            if (isErrorEnabled() == true) {

                logger.error(getMethodName(), message, throwable);
            }

        }
        else {

            if (isWarnEnabled() == true) {

                logger.warn(getMethodName(), message);
            }

        }

    }

    /**
     * Indicate whether warn level logging is enabled.
     * @param marker The marker
     * @return true if warn level logging is enabled, false otherwise
     */
    public boolean isWarnEnabled(
        final Marker marker) {
        return loggingLevel <= LOGGING_LEVEL_WARN;
    }

    /**
     * Log message at warn logging level, if it is enabled.
     * @param marker The marker
     * @param message The message
     */
    public void warn(
        final Marker marker,
        final String message) {

        if (isWarnEnabled(marker) == true) {

            logger.warn(getMethodName(), message);
        }

    }

    /**
     * Log message at warn logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arg The argument
     */
    public void warn(
        final Marker marker,
        final String format,
        final Object arg) {

        if (isWarnEnabled(marker) == true) {

            logger.warn(getMethodName(), MessageFormatter.format(format, arg).getMessage());
        }

    }

    /**
     * Log message at warn logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arg1 The argument
     * @param arg2 The argument
     */
    public void warn(
        final Marker marker,
        final String format,
        final Object arg1,
        final Object arg2) {

        if (isWarnEnabled(marker) == true) {

            logger.warn(getMethodName(), MessageFormatter.format(format, arg1, arg2).getMessage());
        }

    }

    /**
     * Log message at warn logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arguments The arguments
     */
    public void warn(
        final Marker marker,
        final String format,
        final Object... arguments) {

        if (isWarnEnabled(marker) == true) {

            logger.warn(getMethodName(), MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

    }

    /**
     * Log message at warn logging level, if it is enabled.
     * @param marker The marker
     * @param message The message
     * @param throwable The throwable
     */
    public void warn(
        final Marker marker,
        final String message,
        final Throwable throwable) {

        if (throwable != null) {

            if (isErrorEnabled(marker) == true) {

                logger.error(getMethodName(), message, throwable);
            }

        }
        else {

            if (isWarnEnabled(marker) == true) {

                logger.warn(getMethodName(), message);
            }

        }

    }

    /**
     * Indicate whether info level logging is enabled.
     * @return true if info level logging is enabled, false otherwise
     */
    public boolean isInfoEnabled() {
        return loggingLevel <= LOGGING_LEVEL_INFO;
    }

    /**
     * Log message at info logging level, if it is enabled.
     * @param message The message
     */
    public void info(
        final String message) {

        if (isInfoEnabled() == true) {

            logger.info(getMethodName(), message);
        }

    }

    /**
     * Log message at info logging level, if it is enabled.
     * @param format The format
     * @param arg The argument
     */
    public void info(
        final String format,
        final Object arg) {

        if (isInfoEnabled() == true) {

            logger.info(getMethodName(), MessageFormatter.format(format, arg).getMessage());
        }

    }

    /**
     * Log message at info logging level, if it is enabled.
     * @param format The format
     * @param arg1 The argument
     * @param arg2 The argument
     */
    public void info(
        final String format,
        final Object arg1,
        final Object arg2) {

        if (isInfoEnabled() == true) {

            logger.info(getMethodName(), MessageFormatter.format(format, arg1, arg2).getMessage());
        }

    }

    /**
     * Log message at info logging level, if it is enabled.
     * @param format The format
     * @param arguments The arguments
     */
    public void info(
        final String format,
        final Object... arguments) {

        if (isInfoEnabled() == true) {

            logger.info(getMethodName(), MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

    }

    /**
     * Log message at info logging level, if it is enabled.
     * @param message The message
     * @param throwable The throwable
     */
    public void info(
        final String message,
        final Throwable throwable) {

        if (throwable != null) {

            if (isErrorEnabled() == true) {

                logger.error(getMethodName(), message, throwable);
            }

        }
        else {

            if (isInfoEnabled() == true) {

                logger.info(getMethodName(), message);
            }

        }

    }

    /**
     * Indicate whether info level logging is enabled.
     * @param marker The marker
     * @return true if info level logging is enabled, false otherwise
     */
    public boolean isInfoEnabled(
        final Marker marker) {
        return loggingLevel <= LOGGING_LEVEL_INFO;
    }

    /**
     * Log message at info logging level, if it is enabled.
     * @param marker The marker
     * @param message The message
     */
    public void info(
        final Marker marker,
        final String message) {

        if (isInfoEnabled(marker) == true) {

            logger.info(getMethodName(), message);
        }

    }

    /**
     * Log message at info logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arg The argument
     */
    public void info(
        final Marker marker,
        final String format,
        final Object arg) {

        if (isInfoEnabled(marker) == true) {

            logger.info(getMethodName(), MessageFormatter.format(format, arg).getMessage());
        }

    }

    /**
     * Log message at info logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arg1 The argument
     * @param arg2 The argument
     */
    public void info(
        final Marker marker,
        final String format,
        final Object arg1,
        final Object arg2) {

        if (isInfoEnabled(marker) == true) {

            logger.info(getMethodName(), MessageFormatter.format(format, arg1, arg2).getMessage());
        }

    }

    /**
     * Log message at info logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arguments The arguments
     */
    public void info(
        final Marker marker,
        final String format,
        final Object... arguments) {

        if (isInfoEnabled(marker) == true) {

            logger.info(getMethodName(), MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

    }

    /**
     * Log message at info logging level, if it is enabled.
     * @param marker The marker
     * @param message The message
     * @param throwable The throwable
     */
    public void info(
        final Marker marker,
        final String message,
        final Throwable throwable) {

        if (throwable != null) {

            if (isErrorEnabled(marker) == true) {

                logger.error(getMethodName(), message, throwable);
            }

        }
        else {

            if (isInfoEnabled(marker) == true) {

                logger.info(getMethodName(), message);
            }

        }

    }

    /**
     * Indicate whether debug level logging is enabled.
     * @return true if debug level logging is enabled, false otherwise
     */
    public boolean isDebugEnabled() {
        return loggingLevel <= LOGGING_LEVEL_DEBUG;
    }

    /**
     * Log message at debug logging level, if it is enabled.
     * @param message The message
     */
    public void debug(
        final String message) {

        if (isDebugEnabled() == true) {

            logger.debug(getMethodName(), message);
        }

    }

    /**
     * Log message at debug logging level, if it is enabled.
     * @param format The format
     * @param arg The argument
     */
    public void debug(
        final String format,
        final Object arg) {

        if (isDebugEnabled() == true) {

            logger.debug(getMethodName(), MessageFormatter.format(format, arg).getMessage());
        }

    }

    /**
     * Log message at debug logging level, if it is enabled.
     * @param format The format
     * @param arg1 The argument
     * @param arg2 The argument
     */
    public void debug(
        final String format,
        final Object arg1,
        final Object arg2) {

        if (isDebugEnabled() == true) {

            logger.debug(getMethodName(), MessageFormatter.format(format, arg1, arg2).getMessage());
        }

    }

    /**
     * Log message at debug logging level, if it is enabled.
     * @param format The format
     * @param arguments The arguments
     */
    public void debug(
        final String format,
        final Object... arguments) {

        if (isDebugEnabled() == true) {

            logger.debug(getMethodName(), MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

    }

    /**
     * Log message at debug logging level, if it is enabled.
     * @param message The message
     * @param throwable The throwable
     */
    public void debug(
        final String message,
        final Throwable throwable) {

        if (throwable != null) {

            if (isErrorEnabled() == true) {

                logger.error(getMethodName(), message, throwable);
            }

        }
        else {

            if (isDebugEnabled() == true) {

                logger.debug(getMethodName(), message);
            }

        }

    }

    /**
     * Indicate whether debug level logging is enabled.
     * @param marker The marker
     * @return true if debug level logging is enabled, false otherwise
     */
    public boolean isDebugEnabled(
        final Marker marker) {
        return loggingLevel <= LOGGING_LEVEL_DEBUG;
    }

    /**
     * Log message at debug logging level, if it is enabled.
     * @param marker The marker
     * @param message The message
     */
    public void debug(
        final Marker marker,
        final String message) {

        if (isDebugEnabled(marker) == true) {

            logger.debug(getMethodName(), message);
        }

    }

    /**
     * Log message at debug logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arg The argument
     */
    public void debug(
        final Marker marker,
        final String format,
        final Object arg) {

        if (isDebugEnabled(marker) == true) {

            logger.debug(getMethodName(), MessageFormatter.format(format, arg).getMessage());
        }

    }

    /**
     * Log message at debug logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arg1 The argument
     * @param arg2 The argument
     */
    public void debug(
        final Marker marker,
        final String format,
        final Object arg1,
        final Object arg2) {

        if (isDebugEnabled(marker) == true) {

            logger.debug(getMethodName(), MessageFormatter.format(format, arg1, arg2).getMessage());
        }

    }

    /**
     * Log message at debug logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arguments The arguments
     */
    public void debug(
        final Marker marker,
        final String format,
        final Object... arguments) {

        if (isDebugEnabled(marker) == true) {

            logger.debug(getMethodName(), MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

    }

    /**
     * Log message at debug logging level, if it is enabled.
     * @param marker The marker
     * @param message The message
     * @param throwable The throwable
     */
    public void debug(
        final Marker marker,
        final String message,
        final Throwable throwable) {

        if (throwable != null) {

            if (isErrorEnabled(marker) == true) {

                logger.error(getMethodName(), message, throwable);
            }

        }
        else {

            if (isDebugEnabled(marker) == true) {

                logger.debug(getMethodName(), message);
            }

        }

    }

    /**
     * Indicate whether trace level logging is enabled.
     * @return true if trace level logging is enabled, false otherwise
     */
    public boolean isTraceEnabled() {
        return loggingLevel == LOGGING_LEVEL_TRACE;
    }

    /**
     * Log message at trace logging level, if it is enabled.
     * @param message The message
     */
    public void trace(
        final String message) {

        if (isTraceEnabled() == true) {

            logger.trace(getMethodName(), message);
        }

    }

    /**
     * Log message at trace logging level, if it is enabled.
     * @param format The format
     * @param arg The argument
     */
    public void trace(
        final String format,
        final Object arg) {

        if (isTraceEnabled() == true) {

            logger.trace(getMethodName(), MessageFormatter.format(format, arg).getMessage());
        }

    }

    /**
     * Log message at trace logging level, if it is enabled.
     * @param format The format
     * @param arg1 The argument
     * @param arg2 The argument
     */
    public void trace(
        final String format,
        final Object arg1,
        final Object arg2) {

        if (isTraceEnabled() == true) {

            logger.trace(getMethodName(), MessageFormatter.format(format, arg1, arg2).getMessage());
        }

    }

    /**
     * Log message at trace logging level, if it is enabled.
     * @param format The format
     * @param arguments The arguments
     */
    public void trace(
        final String format,
        final Object... arguments) {

        if (isTraceEnabled() == true) {

            logger.trace(getMethodName(), MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

    }

    /**
     * Log message at trace logging level, if it is enabled.
     * @param message The message
     * @param throwable The throwable
     */
    public void trace(
        final String message,
        final Throwable throwable) {

        if (throwable != null) {

            if (isErrorEnabled() == true) {

                logger.error(getMethodName(), message, throwable);
            }

        }
        else {

            if (isTraceEnabled() == true) {

                logger.trace(getMethodName(), message);
            }

        }

    }

    /**
     * Indicate whether trace level logging is enabled.
     * @param marker The marker
     * @return true if trace level logging is enabled, false otherwise
     */
    public boolean isTraceEnabled(
        final Marker marker) {
        return loggingLevel == LOGGING_LEVEL_TRACE;
    }

    /**
     * Log message at trace logging level, if it is enabled.
     * @param marker The marker
     * @param message The message
     */
    public void trace(
        final Marker marker,
        final String message) {

        if (isTraceEnabled(marker) == true) {

            logger.trace(getMethodName(), message);
        }

    }

    /**
     * Log message at trace logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arg The argument
     */
    public void trace(
        final Marker marker,
        final String format,
        final Object arg) {

        if (isTraceEnabled(marker) == true) {

            logger.trace(getMethodName(), MessageFormatter.format(format, arg).getMessage());
        }

    }

    /**
     * Log message at trace logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arg1 The argument
     * @param arg2 The argument
     */
    public void trace(
        final Marker marker,
        final String format,
        final Object arg1,
        final Object arg2) {

        if (isTraceEnabled(marker) == true) {

            logger.trace(getMethodName(), MessageFormatter.format(format, arg1, arg2).getMessage());
        }

    }

    /**
     * Log message at trace logging level, if it is enabled.
     * @param marker The marker
     * @param format The format
     * @param arguments The arguments
     */
    public void trace(
        final Marker marker,
        final String format,
        final Object... arguments) {

        if (isTraceEnabled(marker) == true) {

            logger.trace(getMethodName(), MessageFormatter.arrayFormat(format, arguments).getMessage());
        }

    }

    /**
     * Log message at trace logging level, if it is enabled.
     * @param marker The marker
     * @param message The message
     * @param throwable The throwable
     */
    public void trace(
        final Marker marker,
        final String message,
        final Throwable throwable) {

        if (throwable != null) {

            if (isErrorEnabled(marker) == true) {

                logger.error(getMethodName(), message, throwable);
            }

        }
        else {

            if (isTraceEnabled(marker) == true) {

                logger.trace(getMethodName(), message);
            }

        }

    }

    static {

        stackTraceIndex = 0;

        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            stackTraceIndex++;

            if (stackTraceElement.getClassName().equals(LoggerAdapter.class.getName()) == true) {
                break;
            }

        }

        try {
            stackTraceMethod = Throwable.class.getDeclaredMethod("getStackTraceElement", int.class);
            stackTraceMethod.setAccessible(true);
        }
        catch (Exception exception) {
        }

    }

    /**
     * Get name of calling method.
     * @return The method name
     */
    private String getMethodName() {

        StackTraceElement stackTraceElement;
        String methodName;

        try {

            stackTraceElement = (StackTraceElement) stackTraceMethod.invoke(new Throwable(), stackTraceIndex + 1);

            methodName = stackTraceElement.getMethodName();
        }
        catch (Exception exception) {

            methodName = null;
        }

        return methodName;
    }

}
