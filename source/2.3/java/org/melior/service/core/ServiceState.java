/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.service.core;

/**
 * A list of service states.
 * @author Melior
 * @since 2.0
 */
public enum ServiceState {
    RUNNING,
    SUSPENDED,
    STOPPED;

    private static ServiceState serviceState;

    /**
     * Set service state.
     * @param serviceState The service state
     */
    public static void set(
        final ServiceState serviceState) {
        ServiceState.serviceState = serviceState;
    }

    /**
     * Indicate whether service is active.
     * @return true if the service is active, false otherwise
     */
    public static boolean isActive() {
        return (serviceState == ServiceState.RUNNING) || (serviceState == ServiceState.SUSPENDED);
    }

    /**
     * Indicate whether service is suspended.
     * @return true if the service is suspended, false otherwise
     */
    public static boolean isSuspended() {
        return (serviceState == ServiceState.SUSPENDED);
    }

}
