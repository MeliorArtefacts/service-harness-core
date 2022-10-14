/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.logging.appender;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.melior.context.transaction.Argument;
import org.melior.service.exception.ApplicationException;
import org.melior.service.exception.ExceptionType;
import org.melior.util.collection.BlockingQueue;
import org.melior.util.collection.Queue;
import org.melior.util.exception.StackTrace;
import org.melior.util.number.Clamp;
import org.melior.util.thread.DaemonThread;
import org.springframework.boot.logging.LogLevel;

/**
 * Implementation of internal appender that logs to files in the file system.
 * Uses ISO 8601 compliant timestamps.  The transaction identifier from the
 * transaction context is included in the log entries and may be used for
 * correlation.  The transaction parameters from the transaction context
 * are included when logging to the transaction log.
 * @author Melior
 * @since 2.1
 * @see Appender
 */
public class FileAppender extends Appender{
    private static final String FORMAT_FILE_DATE = "yyyy-MM-dd";
  private static final String FORMAT_FILE_INDEX = "%06d";

    private String baseFileName;

    private String fileExtension;

    private long maxFileSize;

    private String historyPath;

    private int maxFileHistory;

    private int lastDay;

    private String currentFileDate;

    private int currentFileIndex;

    private File currentFile;

    private int currentFileSize;

    private boolean needToRoll;

    private PrintWriter printWriter;

    private BlockingQueue<File> archiveQueue;

  /**
   * Constructor.
   * @param stream The stream
   * @param configuration The configuration
   * @throws ApplicationException if an error occurs during the construction
   */
  public FileAppender(
    final Stream stream,
    final AppenderConfig configuration) throws ApplicationException{
        super(stream, configuration);

        lastDay = LocalDateTime.now().minusDays(1).getDayOfMonth();

        currentFileIndex = 0;

        currentFileSize = 0;

        needToRoll = false;

        printWriter = null;

        archiveQueue = Queue.ofBlocking();

        DaemonThread.create(() -> archiveFiles());

        archiveOrphanedFiles();
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
        String stackTrace;
    StringBuilder stringBuilder;
    String fullMessage;

        stackTrace = null;

        if (day != lastDay){

            if (rollFileOnDate(day) == false){
        return;
      }

    }

        if ((maxFileSize != 0) && (currentFileSize >= maxFileSize)){

            if (rollFileOnSize() == false){
        return;
      }

    }

        stringBuilder = new StringBuilder(256);
    stringBuilder.append(timestamp).append(", ").append(loggingLevel).append(", ")
      .append(hostName).append(", ") .append(threadId).append(", ")
      .append(transactionId).append(", ").append(location).append(", ");

        for (int i = 0; i < messageParts.length; i++){
      stringBuilder.append(String.valueOf(messageParts[i]));
    }

        fullMessage = stringBuilder.toString();

        if (throwable != null){
            stackTrace = StackTrace.getFull(throwable);
    }

    synchronized (this){

            if (stackTrace != null){
                printWriter.println(fullMessage);

                currentFileSize += fullMessage.length();

                printWriter.println(stackTrace);

                currentFileSize += stackTrace.length();
      }
      else{
                printWriter.println(fullMessage);

                currentFileSize += fullMessage.length();
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
        String stackTrace;
    StringBuilder stringBuilder;
    String fullMessage;

        stackTrace = null;

        if (day != lastDay){

            if (rollFileOnDate(day) == false){
        return;
      }

    }

        if ((maxFileSize != 0) && (currentFileSize >= maxFileSize)){

            if (rollFileOnSize() == false){
        return;
      }

    }

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

        if (throwable != null){
            stackTrace = StackTrace.getMinimal(throwable);
    }

    synchronized (this){

            if (stackTrace != null){
                printWriter.print(fullMessage);

                currentFileSize += fullMessage.length();

                printWriter.print(", " + stackTracePrefix);
        printWriter.println(stackTrace);

                currentFileSize += (stackTracePrefix.length() + stackTrace.length() + 2);
      }
      else{
                printWriter.println(fullMessage);

                currentFileSize += fullMessage.length();
      }

    }

  }

  /**
   * Roll file to starting sequence number for new day.
   * @return true if able to roll to the next file, false otherwise
   */
  private synchronized boolean rollFileOnDate(
    final int day){
        File directory;

        if (day != lastDay){
            currentFileIndex = 1;
      currentFileSize = 0;
      needToRoll = true;

            currentFileDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT_FILE_DATE));

            directory = new File(baseFileName).getParentFile();
      directory.mkdirs();

            pruneArchivedFiles();

            if (rollFile() == false){
        return false;
      }

            lastDay = day;
    }

    return true;
  }

  /**
   * Roll file to next sequence number for same day.
   * @return true if able to roll to the next file, false otherwise
   */
  private synchronized boolean rollFileOnSize(){

        if ((maxFileSize != 0) && (currentFileSize >= maxFileSize)){
            currentFileIndex++;
      needToRoll = true;

            if (rollFile() == false){
        return false;
      }

            currentFileSize = 0;
    }

    return true;
  }

  /**
   * Roll to next file.
   * @return true if able to roll to the next file, false otherwise
   */
  private boolean rollFile(){
        File newFile;

        while (needToRoll == true){

      try{
                newFile = new File(getFileName());
      }
      catch (Exception exception){
                return false;
      }

            if (fileExists(newFile) == false){

                if (printWriter != null){
                    printWriter.flush();
          printWriter.close();
          printWriter = null;

                    archiveQueue.add(currentFile);
        }

        try{
                    printWriter = new PrintWriter(new FileWriter(newFile, true), true);
        }
        catch (Exception exception){
                    return false;
        }

                currentFile = newFile;

                needToRoll = false;
      }
      else{
                currentFileIndex++;
      }

    }

    return true;
  }

  /**
   * Archive files that have been orphaned by service termination.
   */
  private void archiveOrphanedFiles(){
        File directory;
    File[] files;

    try{
            directory = new File(baseFileName).getParentFile();

            files = directory.listFiles();

            for (int i = 0; i < files.length; i++){

                if (files[i].getName().endsWith(fileExtension) == true){
                    archiveQueue.add(files[i]);
        }

      }

    }
    catch (Exception exception){
    }

  }

  /**
   * Archive files that have been completed.
   */
  private void archiveFiles(){
        File file;

        while (true){

      try{
                file = archiveQueue.remove();

                archiveFile(file);
      }
      catch (Exception exception){
      }

    }

  }

  /**
   * Archive file.  The file is moved to the file history path and compressed.
   * @param fileName The file name
   */
  private void archiveFile(
    final File file){
        FileInputStream fileInputStream;
    String archiveFileName;
    File archiveDirectory;
    ZipOutputStream zipOutputStream;
    byte[] buffer;
    int size;

        if (historyPath == null){
      return;
    }

    try{
            fileInputStream = new FileInputStream(file);

      try{
                archiveFileName = getArchiveFileName(file);

                archiveDirectory = new File(archiveFileName).getParentFile();
        archiveDirectory.mkdirs();

                zipOutputStream = new ZipOutputStream(new FileOutputStream(new File(archiveFileName)));

        try{
                    zipOutputStream.putNextEntry(new ZipEntry(file.getName()));

                    buffer = new byte[1024];

                    while ((size = fileInputStream.read(buffer)) >= 0){
            zipOutputStream.write(buffer, 0, size);
          }

        }
        finally{
                    zipOutputStream.flush();
          zipOutputStream.close();
        }

      }
      finally{
                fileInputStream.close();
      }

            file.delete();
    }
    catch (Exception exception){
    }

  }

  /**
   * Prune archived files which have aged out of scope.
   */
  private void pruneArchivedFiles(){
        String fileDate;
    File directory;
    File[] files;

        if (historyPath == null){
      return;
    }

    try{
            fileDate = LocalDateTime.now().minusDays(maxFileHistory).format(DateTimeFormatter.ofPattern(FORMAT_FILE_DATE));

            directory = new File(historyPath.replace("%d", fileDate));

            files = directory.listFiles();

            for (int i = 0; i < files.length; i++){
        files[i].delete();
      }

            while (true){

                if (directory.delete() == false){
          break;
        }

                directory = directory.getParentFile();
      }

    }
    catch (Exception exception){
    }

  }

  /**
   * Build file name from current file parameters.
   * @return The file name
   */
  private String getFileName(){
    return baseFileName + "." + currentFileDate + "." + String.format(FORMAT_FILE_INDEX, currentFileIndex) + fileExtension;
  }

  /**
   * Get archive file name for given file.
   * @param file The file
   * @return The archive file name
   */
  private String getArchiveFileName(
    final File file){
        String fileDate;
    File archiveDirectory;
    String archiveFileName;

        fileDate = file.getName().split("[.]")[1];

        archiveDirectory = new File(historyPath.replace("%d", fileDate));

        archiveFileName = archiveDirectory.getAbsolutePath() + "/" + file.getName() + ".zip";

    return archiveFileName;
  }

  /**
   * Indicate whether given file already exists.
   * @param file The file
   * @return true if either the file or the archived version of the file already exists, false otherwise
   */
  private boolean fileExists(
    final File file){
    return (file.exists() || new File(getArchiveFileName(file)).exists());
  }

  /**
   * Configure appender.
   * @throws ApplicationException if unable to configure the appender
   */
  protected void configure() throws ApplicationException{
        String newBaseFileName;

        if (configuration.getFileName() != null){
            newBaseFileName = new File(configuration.getFileName()).getAbsolutePath();
    }
        else if ((configuration.getFilePath() != null) && (configuration.getServiceName() != null)){
            newBaseFileName = new File(configuration.getFilePath() + "/" + configuration.getServiceName()).getAbsolutePath();
    }
    else{
      throw new ApplicationException(ExceptionType.LOCAL_APPLICATION, "Either logging file name or logging file path must be configured.");
    }

        if ((baseFileName != null) && (baseFileName.equals(newBaseFileName) == false)){
            currentFileIndex = 1;
      currentFileSize = 0;
      needToRoll = true;
    }

        baseFileName = newBaseFileName;

        if ((fileExtension != null) && (fileExtension.equals(stream.getExtension()) == false)){
            currentFileIndex = 1;
      currentFileSize = 0;
      needToRoll = true;
    }

        fileExtension = stream.getExtension();

        maxFileSize = (configuration.getMaxFileSize() <= 0) ? Long.MAX_VALUE : configuration.getMaxFileSize();

        historyPath = configuration.getHistoryPath();

        maxFileHistory = Clamp.clampInt(configuration.getMaxFileHistory(), 1, Integer.MAX_VALUE);
  }

}
