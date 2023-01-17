/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.service.exception;

/**
 * Provides a list of standard exception types.
 * @author Melior
 * @since 2.0
 */
public enum ExceptionType {
    SECURITY,
    SERVICE_UNAVAILABLE,
    SERVICE_OVERLOAD,
    NO_DATA,
    LOCAL_SYSTEM,
    LOCAL_APPLICATION,
    DATAACCESS_COMMUNICATION,
    DATAACCESS_SYSTEM,
    DATAACCESS_APPLICATION,
    REMOTING_COMMUNICATION,
    REMOTING_SYSTEM,
    REMOTING_APPLICATION,
    TIMEOUT,
    UNEXPECTED
}
