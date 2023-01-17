/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.logging.core;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import org.springframework.boot.logging.LogLevel;

/**
 * Allows any of the "standard" system output streams to be redirected to a logger.
 * @author Melior
 * @since 2.3
 */
public class StreamSink {

    private LoggerContext logger;

    private String loggerName;

    private String methodName;

    private LogLevel loggingLevel;

    /**
     * Get instance of stream sink.
     * @param logger The logger
     * @param methodName The method name
     * @param loggingLevel The logging level
     * @return The stream sink
     */
    public static StreamSink of(
        final Logger logger,
        final String methodName,
        final LogLevel loggingLevel) {
        return new StreamSink(logger, methodName, loggingLevel);
    }

    /**
     * Constructor.
     * @param logger The logger
     * @param methodName The method name
     * @param loggingLevel The logging level
     */
    StreamSink(
        final Logger logger,
        final String methodName,
        final LogLevel loggingLevel) {

        super();

        this.logger = ((LoggerFacade) logger).getLogger();

        this.loggerName = logger.getName();

        this.methodName = methodName;

        this.loggingLevel = loggingLevel;
    }

    /**
     * Get stream that will redirect to the logger.
     * @return The stream
     */
    public PrintStream getStream() {

        return new PrintStream(new OutputStream() {

            /**
             * Write byte array to logger.
             * @param b The byte array
             * @throws IOException if an error occurs while writing to the logger
             */
            public void write(
                final byte[] b) throws IOException {
                write(new String(b));
            }

            /**
             * Write byte array to logger.
             * @param b The byte array
             * @param o The offset
             * @param l The length
             * @throws IOException if an error occurs while writing to the logger
             */
            public void write(
                final byte[] b,
                final int o,
                final int l) throws IOException {
                write(new String(b, o, l));
            }

            /**
             * Write integer to logger.
             * @param b The integer
             * @throws IOException if an error occurs while writing to the logger
             */
            public void write(
                final int b) throws IOException {
                write(String.valueOf(b));
            }

            /**
             * Write string to logger.
             * @param s The string
             * @throws IOException if an error occurs while writing to the logger
             */
            private void write(
                final String s) throws IOException {

                if (logger.getLoggingLevel() <= loggingLevel.ordinal()) {

                    if (s.equals(System.lineSeparator()) == false) {

                        logger.write(loggingLevel, loggerName, methodName, new String[] {s}, null);
                    }

                }

            }

        });

    }

}
