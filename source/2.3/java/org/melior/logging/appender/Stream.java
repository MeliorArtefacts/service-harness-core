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
 * List of logging appender streams.
 * @author Melior
 * @since 2.1
 */
public enum Stream {
    TRACE("trc", ".trc"),
    TRACE_ERROR("err", ".err"),
    TRANSACTION("trx", ".trx"),
    TRANSACTION_ERROR("erx", ".erx");

    private String alias;

    private String extension;

    /**
     * Constructor.
     * @param alias The stream alias
     * @param extension The file extension
     */
    private Stream(
        final String alias,
        final String extension) {

        this.alias = alias;

        this.extension = extension;
    }

    /**
     * Get stream alias.
     * @return The stream alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Get file extension.
     * @return The file extension
     */
    public String getExtension() {
        return extension;
    }

}
