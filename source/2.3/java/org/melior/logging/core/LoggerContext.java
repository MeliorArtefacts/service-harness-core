/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.logging.core;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.melior.context.transaction.TransactionContext;
import org.melior.logging.appender.Appender;
import org.melior.logging.appender.AppenderConfig;
import org.melior.logging.appender.ConsoleAppender;
import org.melior.logging.appender.FileAppender;
import org.melior.logging.appender.Stream;
import org.melior.util.number.Clamp;
import org.melior.util.object.ObjectUtil;
import org.melior.util.thread.DaemonThread;
import org.melior.util.thread.ThreadControl;
import org.melior.util.time.AccurateLocalDateTime;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.env.Environment;
import org.springframework.util.unit.DataSize;

/**
 * Implementation of the internal logger context.  It sets up the appender
 * configuration and instantiates the relevant internal appenders.  It
 * generates the timestamps for the log entries and directs the logging
 * events at the internal appenders.
 * @author Melior
 * @since 2.1
 * @see Appender
 */
public class LoggerContext{
    private static LoggerContext instance;

    private String hostName;

    private DateTimeFormatter dateTimeFormatter;

    private AppenderConfig configuration;

    private Appender normalTraceAppender;
  private Appender errorTraceAppender;
  private Appender normalTransactionAppender;
  private Appender errorTransactionAppender;

    private int loggingLevel;

    private int currentDay;

    private String timestamp;

  /**
   * Constructor.
   */
  LoggerContext(){
        super();

    try{
            hostName = InetAddress.getLocalHost().getHostName();
    }
    catch (Exception exception){
      throw new RuntimeException("Failed to retrieve host name.", exception);
    }

    try{
            dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    }
    catch (Exception exception){
      throw new RuntimeException("Failed to create date time formatter.", exception);
    }

        generateTimestamp();

        DaemonThread.create(() -> generateTimestamps());

        configuration = new AppenderConfig();

    try{
            normalTraceAppender = new ConsoleAppender(Stream.TRACE, configuration);
      errorTraceAppender = new ConsoleAppender(Stream.TRACE_ERROR, configuration);
      normalTransactionAppender = new ConsoleAppender(Stream.TRANSACTION, configuration);
      errorTransactionAppender = new ConsoleAppender(Stream.TRANSACTION_ERROR, configuration);
    }
    catch (Exception exception){
      throw new RuntimeException("Failed to create default appenders: " + exception.getMessage());
    }

        loggingLevel = LogLevel.DEBUG.ordinal();
  }

  /**
   * Get singleton instance.
   * @return The singleton instance
   */
  static LoggerContext get(){

        if (instance == null){
            instance = new LoggerContext();
    }

    return instance;
  }

  /**
   * Initialize.
   * @param environment The environment
   */
  void initialize(
    final Environment environment){
        String fileName;
    String filePath;
    String serviceName;
    long maxSize;
    String historyPath;
    int maxHistory;
    String format;

        loggingLevel = LogLevel.valueOf(environment.getProperty("logging.level", "DEBUG")).ordinal();

        fileName = environment.getProperty("logging.file.name");

        filePath = environment.getProperty("logging.file.path");

        if ((fileName != null) || (filePath != null)){
            serviceName = environment.getProperty("service.name", environment.getProperty("spring.application.name"));

            maxSize = DataSize.parse(environment.getProperty("logging.file.max-size", "10MB")).toBytes();

            historyPath = environment.getProperty("logging.file.history-path");

            maxHistory = Integer.parseInt(environment.getProperty("logging.file.max-history", "7"));

            configuration.setServiceName(serviceName);
      configuration.setFileName(fileName);
      configuration.setFilePath(filePath);
      configuration.setMaxFileSize(maxSize);
      configuration.setHistoryPath(historyPath);
      configuration.setMaxFileHistory(maxHistory);

      try{
                normalTraceAppender = new FileAppender(Stream.TRACE, configuration);
        errorTraceAppender = new FileAppender(Stream.TRACE_ERROR, configuration);
        normalTransactionAppender = new FileAppender(Stream.TRANSACTION, configuration);
        errorTransactionAppender = new FileAppender(Stream.TRANSACTION_ERROR, configuration);
      }
      catch (Exception exception){
        throw new RuntimeException("Failed to create appenders: " + exception.getMessage());
      }

    }
    else{
            format = environment.getProperty("logging.console.format");

            configuration.setFormat(format);
    }

  }

  /**
   * Generate timestamp at intervals.
   */
  private void generateTimestamps(){
        long previousTime;
    long currentTime;
    long duration = 500000;

        previousTime = System.nanoTime();

    while (true){
            ThreadControl.sleep(duration, TimeUnit.NANOSECONDS);

            generateTimestamp();

            currentTime = System.nanoTime();

            duration = Clamp.clampLong(500000 - (currentTime - previousTime - duration), 250000, 500000);

            previousTime = currentTime;
    }

  }

  /**
   * Generate timestamp.
   */
  private void generateTimestamp(){
        LocalDateTime localDateTime;

        localDateTime = AccurateLocalDateTime.now();

        currentDay = localDateTime.getDayOfMonth();

        timestamp = localDateTime.format(dateTimeFormatter);
  }

  /**
   * Get logging level.
   * @return The logging level
   */
  int getLoggingLevel(){
    return loggingLevel;
  }

  /**
   * Write trace event.
   * @param loggingLevel The logging level
   * @param loggerName The logger name
   * @param methodName The method name
   * @param messageParts The message parts
   * @param throwable The throwable
   */
  void write(
    final LogLevel loggingLevel,
    final String loggerName,
    final String methodName,
    final Object[] messageParts,
    final Throwable throwable){
        String transactionId;
    String location;
    TransactionContext transactionContext;

        transactionContext = TransactionContext.get();

        transactionId = ObjectUtil.coalesce(transactionContext.getTransactionId(), "<blank>");

        location = loggerName + ((methodName == null) ? "" : "." + methodName + "()");

        normalTraceAppender.write(currentDay, timestamp, loggingLevel, hostName, transactionContext.getThreadId(),
      transactionId, location, messageParts, throwable);

        if ((errorTraceAppender != null) && (loggingLevel == LogLevel.ERROR)){
            errorTraceAppender.write(currentDay, timestamp, loggingLevel, hostName, transactionContext.getThreadId(),
        transactionId, location, messageParts, throwable);
    }

  }

  /**
   * Write transaction event.
   * @param loggerName The logger name
   * @param methodName The method name
   * @param transactionContext The transaction context
   * @param stackTracePrefix The stack trace prefix, or null if no prefix is required
   * @param throwable The throwable
   */
  void write(
    final String loggerName,
    final String methodName,
    final TransactionContext transactionContext,
    final String stackTracePrefix,
    final Throwable throwable){
        String transactionId;
    String location;

        transactionId = ObjectUtil.coalesce(transactionContext.getTransactionId(), "<blank>");

        location = loggerName + ((methodName == null) ? "" : "." + methodName + "()");

        normalTransactionAppender.write(currentDay, timestamp, hostName, transactionContext.getThreadId(),
      transactionId, location, ObjectUtil.coalesce(transactionContext.getTransactionType(), methodName),
      (throwable == null) ? "SUCCESSFUL" : "FAILED", transactionContext.getElapsedTimeMillis(),
      transactionContext.getArgumentList(), (stackTracePrefix == null) ? "" : stackTracePrefix, throwable);

        if ((errorTransactionAppender != null) && (throwable != null)){
            errorTransactionAppender.write(currentDay, timestamp, hostName, transactionContext.getThreadId(),
        transactionId, location, ObjectUtil.coalesce(transactionContext.getTransactionType(), methodName),
        (throwable == null) ? "SUCCESSFUL" : "FAILED", transactionContext.getElapsedTimeMillis(),
        transactionContext.getArgumentList(), (stackTracePrefix == null) ? "" : stackTracePrefix, throwable);
    }

  }

}
