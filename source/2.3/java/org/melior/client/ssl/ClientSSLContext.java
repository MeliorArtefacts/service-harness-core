/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.client.ssl;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import org.melior.client.core.ClientConfig;

/**
 * TODO
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
        return null;
  }

}
