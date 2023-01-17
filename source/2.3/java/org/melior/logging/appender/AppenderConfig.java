/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.logging.appender;

/**
 * The configuration for the internal logging appenders.
 * @author Melior
 * @since 2.1
 */
public class AppenderConfig {

    private String serviceName;

    private String fileName;

    private String filePath;

    private long maxFileSize;

    private String historyPath;

    private int maxFileHistory;

    private String format;

    /**
     * Constructor.
     */
    public AppenderConfig() {

        super();
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the maxFileSize
     */
    public long getMaxFileSize() {
        return maxFileSize;
    }

    /**
     * @param maxFileSize the maxFileSize to set
     */
    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    /**
     * @return the historyPath
     */
    public String getHistoryPath() {
        return historyPath;
    }

    /**
     * @param historyPath the historyPath to set
     */
    public void setHistoryPath(String historyPath) {
        this.historyPath = historyPath;
    }

    /**
     * @return the maxFileHistory
     */
    public int getMaxFileHistory() {
        return maxFileHistory;
    }

    /**
     * @param maxFileHistory the maxFileHistory to set
     */
    public void setMaxFileHistory(int maxFileHistory) {
        this.maxFileHistory = maxFileHistory;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(String format) {
        this.format = format;
    }

}
