/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.logging.core;

import org.melior.context.transaction.TransactionContext;

/**
 * The internal logger that is available to the service and
 * to all of the service components.
 * @author Melior
 * @since 2.1
 */
public interface Logger{

  /**
   * Get name.
   * @return The name
   */
  public String getName();

  /**
   * Indicate whether error level logging is enabled.
   * @return true if error level logging is enabled, false otherwise
   */
  public boolean isErrorEnabled();

  /**
   * Log message at error logging level, if it is enabled.
   * @param methodName The method name
   * @param messageParts The message parts
   */
  public void error(
    final String methodName,
    final Object...messageParts);

  /**
   * Indicate whether warn level logging is enabled.
   * @return true if warn level logging is enabled, false otherwise
   */
  public boolean isWarnEnabled();

  /**
   * Log message at warn logging level, if it is enabled.
   * @param methodName The method name
   * @param messageParts The message parts
   */
  public void warn(
    final String methodName,
    final Object...messageParts);

  /**
   * Indicate whether info level logging is enabled.
   * @return true if info level logging is enabled, false otherwise
   */
  public boolean isInfoEnabled();

  /**
   * Log message at info logging level, if it is enabled.
   * @param methodName The method name
   * @param messageParts The message parts
   */
  public void info(
    final String methodName,
    final Object...messageParts);

  /**
   * Indicate whether debug level logging is enabled.
   * @return true if debug level logging is enabled, false otherwise
   */
  public boolean isDebugEnabled();

  /**
   * Log message at debug logging level, if it is enabled.
   * @param methodName The method name
   * @param messageParts The message parts
   */
  public void debug(
    final String methodName,
    final Object...messageParts);

  /**
   * Indicate whether trace level logging is enabled.
   * @return true if trace level logging is enabled, false otherwise
   */
  public boolean isTraceEnabled();

  /**
   * Log message at trace logging level, if it is enabled.
   * @param methodName The method name
   * @param messageParts The message parts
   */
  public void trace(
    final String methodName,
    final Object...messageParts);

  /**
   * Log successful transaction.
   * @param methodName The method name
   * @param transactionContext The transaction context
   */
  public void transaction(
    final String methodName,
    final TransactionContext transactionContext);

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
    final Throwable throwable);

}
