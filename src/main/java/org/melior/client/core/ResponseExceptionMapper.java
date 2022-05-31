/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.client.core;
import org.melior.service.exception.ExceptionType;

/**
 * A client response payload may implement this interface to indicate how a
 * standard {@code RemotingException} may be generated from their content
 * when the content represents a failure.
 * @author Melior
 * @since 2.0
 */
public interface ResponseExceptionMapper
{

	/**
	 * Get exception type.
	 * @return The exception type
	 */
	public ExceptionType getExceptionType();

	/**
	 * Get exception code.
	 * @return The exception code
	 */
	public String getExceptionCode();

	/**
	 * Get exception message.
	 * @return The exception message
	 */
	public String getExceptionMessage();

}
