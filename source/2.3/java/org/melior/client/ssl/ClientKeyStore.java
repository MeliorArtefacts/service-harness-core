/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.client.ssl;
import java.io.InputStream;
import java.security.KeyStore;
import org.melior.client.core.ClientConfig;
import org.melior.util.object.ObjectUtil;
import org.melior.util.string.StringUtil;

/**
 * Instantiates a concrete key store or a concrete trust store from the remoting client
 * configuration, for use in remoting clients that establish secure connections to remote
 * services.
 * @author Melior
 * @since 2.3
 */
public interface ClientKeyStore {

    /**
     * Create key store from configuration.
     * @param clientConfig The configuration
     * @return The key store
     */
    public static KeyStore ofKey(
        final ClientConfig clientConfig) {

        KeyStore keyStore = null;

        try {

            if (clientConfig.getKeyStore() != null) {

                keyStore = KeyStore.getInstance(ObjectUtil.coalesce(clientConfig.getKeyStoreType(), KeyStore.getDefaultType()));

                try (InputStream inputStream = clientConfig.getKeyStore().getInputStream()) {
                    keyStore.load(inputStream, StringUtil.toCharArray(clientConfig.getKeyStorePassword()));
                }

            }

        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to create key store: " + exception.getMessage(), exception);
        }

        return keyStore;
    }

    /**
     * Create trust store from configuration.
     * @param clientConfig The configuration
     * @return The trust store
     */
    public static KeyStore ofTrust(
        final ClientConfig clientConfig) {

        KeyStore trustStore = null;

        try {

            if (clientConfig.getTrustStore() != null) {

                trustStore = KeyStore.getInstance(ObjectUtil.coalesce(clientConfig.getTrustStoreType(), KeyStore.getDefaultType()));

                try (InputStream inputStream = clientConfig.getTrustStore().getInputStream()) {
                    trustStore.load(inputStream, StringUtil.toCharArray(clientConfig.getTrustStorePassword()));
                }

            }

        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to create trust store: " + exception.getMessage(), exception);
        }

        return trustStore;
    }

}
