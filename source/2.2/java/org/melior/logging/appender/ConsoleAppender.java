/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.logging.appender;
import java.util.List;
import org.melior.context.transaction.Argument;
import org.melior.service.exception.ApplicationException;
import org.melior.util.exception.StackTrace;
import org.springframework.boot.logging.LogLevel;

/**
 * Implementation of internal appender that logs to the console.
 * Uses ISO 8601 compliant timestamps.  The transaction identifier from the
 * transaction context is included in the log entries and may be used for
 * correlation.  The transaction parameters from the transaction context
 * are included when logging to the transaction log.
 * @author Melior
 * @since 2.1
 * @see {@code Appender}
 */
public class ConsoleAppender extends Appender{
    private java.io.PrintStream printStream;

  /**
   * Constructor.
   * @param stream The stream
   * @param configuration The configuration
   * @throws ApplicationException when the initialization fails
   */
  public ConsoleAppender(
    final Stream stream,
    final Configuration configuration) throws ApplicationException{
        super(stream, configuration);
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
  public void write(
    final int day,
    final String timestamp,
    final LogLevel loggingLevel,
    final String hostName,
    final String threadId,
    final String transactionId,
    final String location,
    final Object[] messageParts,
    final Throwable throwable){
        StringBuilder stringBuilder;
    String fullMessage;
    String stackTrace;

        stringBuilder = new StringBuilder(256);
    stringBuilder.append(stream.getAlias()).append(", ").append(timestamp).append(", ")
      .append(loggingLevel).append(", ").append(hostName).append(", ")
      .append(threadId).append(", ").append(transactionId).append(", ")
      .append(location).append(", ");

        for (int i = 0; i < messageParts.length; i++){
      stringBuilder.append(String.valueOf(messageParts[i]));
    }

        fullMessage = stringBuilder.toString();

        printStream.println(fullMessage);

        if (throwable != null){
            stackTrace = StackTrace.getFull(throwable);

            printStream.println(stackTrace);
    }

  }

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
   * @param originId The origin identifier
   * @param argumentList The transaction argument list
   * @param stackTracePrefix The stack trace prefix
   * @param throwable The throwable
   */
  public void write(
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
    final Throwable throwable){
        StringBuilder stringBuilder;
    String fullMessage;
    String stackTrace;

        stringBuilder = new StringBuilder(256);
    stringBuilder.append(timestamp).append(", ")
      .append(hostName).append(", ") .append(threadId).append(", ")
      .append(transactionId).append(", ").append(location).append(", ")
      .append(transactionType).append(", ").append(status).append(", ")
      .append(duration);

        for (Argument argument : argumentList){
      stringBuilder.append(", ").append(argument.getName())
        .append(", ").append(argument.getValue());
    }

        fullMessage = stringBuilder.toString();

        printStream.print(fullMessage);

        if (throwable != null){
            stackTrace = StackTrace.getMinimal(throwable);

            printStream.print(", " + stackTracePrefix);
      printStream.println(stackTrace);
    }

  }

  /**
   * Configure appender.
   * @throws ApplicationException when unable to configure the appender
   */
  protected void configure() throws ApplicationException{
        printStream = ((stream == Stream.TRACE_ERROR) || (stream == Stream.TRANSACTION_ERROR)) ? System.err : System.out;
  }

}
