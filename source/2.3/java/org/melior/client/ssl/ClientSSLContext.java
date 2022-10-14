/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.client.ssl;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedTrustManager;
import org.melior.client.core.ClientConfig;
import org.melior.util.object.ObjectUtil;
import org.melior.util.string.StringUtil;

/**
 * Instantiates an {@code SSLContext} for use in remoting clients that establish secure
 * connections to remote services.  The {@code SSLContext} can either be fully trusting
 * or will require a concrete key store and/or a concrete trust store that is referenced
 * in the remoting client configuration.
 * @author Melior
 * @since 2.3
 */
public interface ClientSSLContext{

  /**
   * Create lenient SSL context.
   * @param protocol The protocol to support
   * @return The SSL context
   */
  public static SSLContext ofLenient(
    final String protocol){
        SSLContext sslContext;

    try{
            sslContext = SSLContext.getInstance(protocol);
      sslContext.init(null, new TrustManager[] {new X509ExtendedTrustManager(){
        public X509Certificate[] getAcceptedIssuers() {return null;}
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {}
        public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {}
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {}
        public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {}
      }}, new SecureRandom());
    }
    catch (Exception exception){
      throw new RuntimeException("Failed to create SSL context: " + exception.getMessage(), exception);
    }

    return sslContext;
  }

  /**
   * Create SSL context that uses configured key store and trust store.
   * @param protocol The protocol to support
   * @param clientConfig The configuration
   * @return The SSL context
   */
  public static SSLContext ofKeyStore(
    final String protocol,
    final ClientConfig clientConfig){
        KeyStore keyStore = null;
    KeyManagerFactory keyManagerFactory = null;
    KeyStore trustStore = null;
    TrustManagerFactory trustManagerFactory = null;
    SSLContext sslContext;

    try{

            if (clientConfig.getKeyStore() != null){
                keyStore = KeyStore.getInstance(ObjectUtil.coalesce(clientConfig.getKeyStoreType(), KeyStore.getDefaultType()));

                try (InputStream inputStream = clientConfig.getKeyStore().getInputStream()){
          keyStore.load(inputStream, StringUtil.toCharArray(clientConfig.getKeyStorePassword()));
        }

                keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, StringUtil.toCharArray(ObjectUtil.coalesce(
          clientConfig.getKeyPassword(), clientConfig.getKeyStorePassword(), "")));
      }

            if (clientConfig.getTrustStore() != null){
                trustStore = KeyStore.getInstance(ObjectUtil.coalesce(clientConfig.getTrustStoreType(), KeyStore.getDefaultType()));

                try (InputStream inputStream = clientConfig.getTrustStore().getInputStream()){
          trustStore.load(inputStream, StringUtil.toCharArray(clientConfig.getTrustStorePassword()));
        }

                trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
      }

            sslContext = SSLContext.getInstance(protocol);
      sslContext.init((keyManagerFactory == null) ? null : keyManagerFactory.getKeyManagers(),
        (trustManagerFactory == null) ? null : trustManagerFactory.getTrustManagers(), new SecureRandom());
    }
    catch (Exception exception){
      throw new RuntimeException("Failed to create SSL context: " + exception.getMessage(), exception);
    }

    return sslContext;
  }

}
