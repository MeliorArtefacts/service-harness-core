/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.service.exception;

/**
 * A standard application exception.  For use in the implementation of a
 * service and its components to simplify exception handling.  It records
 * the time that the exception was generated so that it may be used when
 * collecting telemetry.
 * @author Melior
 * @since 2.0
 */
public class ApplicationException extends Exception{
  public static final long serialVersionUID = 1620250046171L;

    protected long time;

    protected ExceptionType type;

    protected String code;

  /**
   * Constructor.
   * @param type The exception type
   * @param message The exception message
   */
  public ApplicationException(
    final ExceptionType type,
    final String message){
        super(message);

        populateException(type, null, null);
  }

  /**
   * Constructor.
   * @param type The exception type
   * @param code The exception code
   * @param message The exception message
   */
  public ApplicationException(
    final ExceptionType type,
    final String code,
    final String message){
        super(message);

        populateException(type, code, null);
  }

  /**
   * Constructor.
   * @param type The exception type
   * @param message The exception message
   * @param cause The exception cause
   */
  public ApplicationException(
    final ExceptionType type,
    final String message,
    final Throwable cause){
        super(message, cause);

        populateException(type, code, cause);
  }

  /**
   * Constructor.
   * @param type The exception type
   * @param cause The exception cause
   */
  public ApplicationException(
    final ExceptionType type,
    final Throwable cause){
        super(cause.getMessage(), cause);

        populateException(type, code, cause);
  }

  /**
   * Constructor.
   * @param type The exception type
   * @param code The exception code
   * @param message The exception message
   * @param cause The exception cause
   */
  public ApplicationException(
    final ExceptionType type,
    final String code,
    final String message,
    final Throwable cause){
        super(message, cause);

        populateException(type, code, cause);
  }

  /**
   * Populate exception.
   * @param type1 The exception type
   * @param code1 The exception code
   * @param cause The exception cause
   */
  private void populateException(
    final ExceptionType type1,
    final String code1,
    final Throwable cause){
        time = System.currentTimeMillis();

        type = (cause instanceof ApplicationException) ? ((ApplicationException) cause).type : type1;

        code = code1;
  }

  /**
   * Get time exception was generated.
   * @return The time the exception was generated
   */
  public final long getTime(){
    return time;
  }

  /**
   * Get exception type.
   * @return The exception type
   */
  public final ExceptionType getType(){
    return type;
  }

  /**
   * Get exception code.
   * @return The exception code
   */
  public final String getCode(){
    return code;
  }

}
