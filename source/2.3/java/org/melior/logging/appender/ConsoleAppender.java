/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.logging.appender;
import java.io.PrintStream;
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
    private PrintStream printStream;

  /**
   * Constructor.
   * @param stream The stream
   * @param configuration The configuration
   * @throws ApplicationException if an error occurs during the construction
   */
  public ConsoleAppender(
    final Stream stream,
    final AppenderConfig configuration) throws ApplicationException{
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
  public synchronized void write(
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
    String stackTrace;

        if ("json".equals(configuration.getFormat()) == true){
            stringBuilder = new StringBuilder(256);
      stringBuilder.append("{\"stream\": \"").append(stream.getAlias())
        .append("\", \"timestamp\": \"").append(timestamp)
        .append("\", \"loggingLevel\": \"").append(loggingLevel)
        .append("\", \"hostName\": \"").append(hostName)
        .append("\", \"threadId\": \"").append(threadId)
        .append("\", \"transactionId\": \"").append(transactionId)
        .append("\", \"location\": \"").append(location)
        .append("\", \"message\": \"");

            for (int i = 0; i < messageParts.length; i++){
        stringBuilder.append(String.valueOf(messageParts[i]));
      }

            if (throwable != null){
                printStream.print(stringBuilder.toString());

                stackTrace = StackTrace.getCompact(throwable);

                printStream.print("\", \"stackTrace\": \"");
        printStream.print(stackTrace);
        printStream.println("\"}");
      }
      else{
                stringBuilder.append("\"}");
        printStream.println(stringBuilder.toString());
      }

    }
    else{
            stringBuilder = new StringBuilder(256);
      stringBuilder.append(stream.getAlias()).append(", ").append(timestamp).append(", ")
        .append(loggingLevel).append(", ").append(hostName).append(", ")
        .append(threadId).append(", ").append(transactionId).append(", ")
        .append(location).append(", ");

            for (int i = 0; i < messageParts.length; i++){
        stringBuilder.append(String.valueOf(messageParts[i]));
      }

            if (throwable != null){
                printStream.print(stringBuilder.toString());

                stackTrace = StackTrace.getCompact(throwable);

                printStream.print(", ");
        printStream.println(stackTrace);
      }
      else{
                printStream.println(stringBuilder.toString());
      }

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
  public synchronized void write(
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
    String stackTrace;

        if ("json".equals(configuration.getFormat()) == true){
            stringBuilder = new StringBuilder(256);
      stringBuilder.append("{\"stream\": \"").append(stream.getAlias())
        .append("\", \"timestamp\": \"").append(timestamp)
        .append("\", \"hostName\": \"").append(hostName)
        .append("\", \"threadId\": \"").append(threadId)
        .append("\", \"transactionId\": \"").append(transactionId)
        .append("\", \"location\": \"").append(location)
        .append("\", \"transactionType\": \"").append(transactionType)
        .append("\", \"status\": \"").append(status)
        .append("\", \"duration\": \"").append(duration);

            for (Argument argument : argumentList){
        stringBuilder.append("\", \"").append(argument.getName())
          .append("\": \"").append(argument.getValue());
      }

            if (throwable != null){
                printStream.print(stringBuilder.toString());

                stackTrace = StackTrace.getMinimal(throwable);

                printStream.print("\", \"stackTrace\": \"" + stackTracePrefix);
        printStream.print(stackTrace);
        printStream.println("\"}");
      }
      else{
                stringBuilder.append("\"}");
        printStream.println(stringBuilder.toString());
      }

    }
    else{
            stringBuilder = new StringBuilder(256);
      stringBuilder.append(stream.getAlias()).append(", ")
        .append(timestamp).append(", ")
        .append(hostName).append(", ")
        .append(threadId).append(", ")
        .append(transactionId).append(", ")
        .append(location).append(", ")
        .append(transactionType).append(", ")
        .append(status).append(", ")
        .append(duration);

            for (Argument argument : argumentList){
        stringBuilder.append(", ").append(argument.getName())
          .append(", ").append(argument.getValue());
      }

            if (throwable != null){
                printStream.print(stringBuilder.toString());

                stackTrace = StackTrace.getMinimal(throwable);

                printStream.print(", " + stackTracePrefix);
        printStream.println(stackTrace);
      }
      else{
                printStream.println(stringBuilder.toString());
      }

    }

  }

  /**
   * Configure appender.
   * @throws ApplicationException if unable to configure the appender
   */
  protected void configure() throws ApplicationException{
        printStream = /*((stream == Stream.TRACE_ERROR) || (stream == Stream.TRANSACTION_ERROR)) ? System.err : */System.out;
  }

}
