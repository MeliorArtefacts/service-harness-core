/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.client.core;
import org.melior.client.exception.RemotingException;
import org.melior.client.pool.ConnectionPool;

/**
 * Base class for factories that produce connection objects on behalf of
 * remoting clients.  The connection objects may be pooled for improved
 * performance.
 * @author Melior
 * @since 2.3
 */
public interface ConnectionFactory<C extends ClientConfig, T extends Connection<C, T, P>, P>{

  /**
   * Create a new connection.
   * @param configuration The client configuration
   * @param connectionPool The connection pool
   * @return The new connection
   * @throws RemotingException if unable to create a new connection
   */
  public T createConnection(
    final C configuration,
    final ConnectionPool<C, T, P> connectionPool) throws RemotingException;

  /**
   * Destroy the connection.
   * @param connection The connection
   */
  public void destroyConnection(
    final T connection);

}
