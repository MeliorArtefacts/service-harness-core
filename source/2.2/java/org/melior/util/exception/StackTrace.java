/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.exception;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Writes an exception stack trace to a {@code String} in either
 * full, compact or minimal format.
 * @author Melior
 * @since 2.1
 */
public class StackTrace{

  /**
   * Constructor.
   */
  private StackTrace(){
        super();
  }

  /**
   * Get full stack trace of {@code Throwable} as a {@code String}.
   * @param throwable The throwable
   * @return The full stack trace as a string
   */
  public static String getFull(
    final Throwable throwable){
        StringWriter stringWriter;
    PrintWriter printWriter;
    String stackTrace;

        stringWriter = new StringWriter();
    printWriter = new PrintWriter(stringWriter);
    throwable.printStackTrace(printWriter);
    stackTrace = stringWriter.toString();

    return stackTrace;
  }

  /**
   * Get compact stack trace of {@code Throwable} as a {@code String}.
   * @param throwable The throwable
   * @return The compact stack trace as a string
   */
  public static String getCompact(
    final Throwable throwable){
        StringBuilder fullMessage;
    Throwable cause;
    String causeMessage;

        fullMessage = new StringBuilder((throwable.getMessage() == null) ? throwable.toString() : throwable.getMessage());

        cause = throwable.getCause();

    while (cause != null){
            causeMessage = (cause.getMessage() == null) ? cause.toString() : cause.getMessage();

            if (fullMessage.indexOf(causeMessage) == -1){
                fullMessage.append(" <-- ").append(causeMessage);
      }

            cause = cause.getCause();
    }

    return fullMessage.toString();
  }

  /**
   * Get minimal stack trace of {@code Throwable} as a {@code String}.
   * @param throwable The throwable
   * @return The minimal stack trace as a string
   */
  public static String getMinimal(
    final Throwable throwable){
        String fullMessage;
    Throwable lastCause;
    Throwable cause;
    String causeMessage;

        fullMessage = (throwable.getMessage() == null) ? throwable.toString() : throwable.getMessage();

        lastCause = throwable;
    cause = throwable.getCause();

    while (cause != null){
            lastCause = cause;

            cause = cause.getCause();
    }

        causeMessage = (lastCause.getMessage() == null) ? lastCause.toString() : lastCause.getMessage();

        if (fullMessage.indexOf(causeMessage) == -1){
            fullMessage = fullMessage + " <-- " + causeMessage;
    }

    return fullMessage;
  }

}
