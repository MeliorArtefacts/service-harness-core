/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.client.core;
import org.melior.service.exception.ApplicationException;
import org.melior.service.exception.ExceptionType;
import org.melior.util.number.Clamp;
import org.springframework.core.io.Resource;

/**
 * Common configuration parameters used by remoting clients.
 * @author Melior
 * @since 2.3
 */
public class ClientConfig {

    private String name;

    private String url;

    private String username;

    private String password;

    private Resource keyStore;

    private String keyStoreType;

    private String keyStorePassword;

    private String keyPassword;

     private Resource trustStore;

    private String trustStoreType;

    private String trustStorePassword;

    private int minimumConnections = 0;

    private int maximumConnections = 1000;

    private int connectionTimeout = 30 * 1000;

    private boolean validateOnBorrow = false;

    private int validationTimeout = 5 * 1000;

    private int requestTimeout = 60 * 1000;

    private int backoffPeriod = 1 * 1000;

    private float backoffMultiplier = 1;

    private int backoffLimit = 0 * 1000;

    private int inactivityTimeout = 300 * 1000;

    private int maximumLifetime = 0 * 1000;

    private int pruneInterval = 5 * 1000;

    /**
     * Constructor.
     */
    protected ClientConfig() {

        super();
    }

    /**
     * Configure client.
     * @param clientConfig The new client configuration parameters
     * @return The client configuration parameters
     */
    public ClientConfig configure(
        final ClientConfig clientConfig) {
        this.name = clientConfig.name;
        this.url = clientConfig.url;
        this.username = clientConfig.username;
        this.password = clientConfig.password;
        this.keyStore = clientConfig.keyStore;
        this.keyStoreType = clientConfig.keyStoreType;
        this.keyStorePassword = clientConfig.keyStorePassword;
        this.keyPassword = clientConfig.keyPassword;
        this.trustStore = clientConfig.trustStore;
        this.trustStoreType = clientConfig.trustStoreType;
        this.trustStorePassword = clientConfig.trustStorePassword;
        this.minimumConnections = clientConfig.minimumConnections;
        this.maximumConnections = clientConfig.maximumConnections;
        this.connectionTimeout = clientConfig.connectionTimeout;
        this.validateOnBorrow = clientConfig.validateOnBorrow;
        this.validationTimeout = clientConfig.validationTimeout;
        this.requestTimeout = clientConfig.requestTimeout;
        this.backoffPeriod = clientConfig.backoffPeriod;
        this.backoffMultiplier = clientConfig.backoffMultiplier;
        this.backoffLimit = clientConfig.backoffLimit;
        this.inactivityTimeout = clientConfig.inactivityTimeout;
        this.maximumLifetime = clientConfig.maximumLifetime;
        this.pruneInterval = clientConfig.pruneInterval;

        return this;
    }

    /**
     * Get name.
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     * @param name The name
     */
    public void setName(
        final String name) {
        this.name = name;
    }

    /**
     * Get URL.
     * @return The URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set URL.
     * @param url The URL
     */
    public void setUrl(
        final String url) {
        this.url = url;
    }

    /**
     * Get user name.
     * @return The user name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set user name.
     * @param username The user name
     */
    public void setUsername(
        final String username) {
        this.username = username;
    }

    /**
     * Get password.
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password.
     * @param password The password
     */
    public void setPassword(
        final String password) {
        this.password = password;
    }
 
    /**
     * Get key store path.
     * @return The key store path
     */
    public Resource getKeyStore() {
        return keyStore;
    }

    /**
     * Set key store path.
     * @param keyStore The key store path
     */
    public void setKeyStore(
        final Resource keyStore) {
        this.keyStore = keyStore;
    }

    /**
     * Get key store type.
     * @return The key store type
     */
    public String getKeyStoreType() {
        return keyStoreType;
    }

    /**
     * Set key store type.
     * @param keyStoreType The key store type
     */
    public void setKeyStoreType(
        final String keyStoreType) {
        this.keyStoreType = keyStoreType;
    }

    /**
     * Get key store password.
     * @return The key store password
     */
    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    /**
     * Set key store password.
     * @param keyStorePassword The key store password
     */
    public void setKeyStorePassword(
        final String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    /**
     * Get key password.
     * @return The key password
     */
    public String getKeyPassword() {
        return keyPassword;
    }

    /**
     * Set key password.
     * @param keyPassword The key password
     */
    public void setKeyPassword(
        final String keyPassword) {
        this.keyPassword = keyPassword;
    }

    /**
     * Get trust store path.
     * @return The trust store path
     */
    public Resource getTrustStore() {
        return trustStore;
    }

    /**
     * Set trust store path.
     * @param trustStore The trust store path
     */
    public void setTrustStore(
        final Resource trustStore) {
        this.trustStore = trustStore;
    }

    /**
     * Get trust store type.
     * @return The trust store type
     */
    public String getTrustStoreType() {
        return trustStoreType;
    }

    /**
     * Set trust store type.
     * @param trustStoreType The trust store type
     */
    public void setTrustStoreType(
        final String trustStoreType) {
        this.trustStoreType = trustStoreType;
    }

    /**
     * Get trust store password.
     * @return The trust store password
     */
    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    /**
     * Set trust store password.
     * @param trustStorePassword The trust store password
     */
    public void setTrustStorePassword(
        final String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    /**
     * Get minimum number of connections.
     * @return The minimum number of connections
     */
    public int getMinimumConnections() {
        return minimumConnections;
    }

    /**
     * Set minimum number of connections.
     * @param minimumConnections The minimum number of connections
     * @throws ApplicationException if the minimum number of connections is invalid
     */
    public void setMinimumConnections(
        final int minimumConnections) throws ApplicationException {

        if ((this.maximumConnections > 0)
            && (minimumConnections > this.maximumConnections)) {
            throw new ApplicationException(ExceptionType.LOCAL_APPLICATION, "Minimum number of connections may not be more than maximum.");
        }

        this.minimumConnections = Clamp.clampInt(minimumConnections, 0, Integer.MAX_VALUE);
    }

    /**
     * Get maximum number of connections.
     * @return The maximum number of connections
     */
    public int getMaximumConnections() {
        return maximumConnections;
    }

    /**
     * Set maximum number of connections.
     * @param maximumConnections The maximum number of connections
     * @throws ApplicationException if the maximum number of connections is invalid
     */
    public void setMaximumConnections(
        final int maximumConnections) throws ApplicationException {

        if ((maximumConnections > 0)
            && (maximumConnections < this.minimumConnections)) {
            throw new ApplicationException(ExceptionType.LOCAL_APPLICATION, "Maximum number of connections may not be less than minimum.");
        }

        this.maximumConnections = Clamp.clampInt(maximumConnections, 0, Integer.MAX_VALUE);
    }

    /**
     * Get connection timeout.
     * @return The connection timeout
     */
    public int getConnectionTimeout() {
        return (connectionTimeout == 0) ? getRequestTimeout() : connectionTimeout;
    }

    /**
     * Set connection timeout.
     * @param connectionTimeout The connection timeout, specified in seconds
     */
    public void setConnectionTimeout(
        final int connectionTimeout) {
        this.connectionTimeout = Clamp.clampInt(connectionTimeout * 1000, 0, Integer.MAX_VALUE);
    }

    /**
     * Get validate on borrow indicator.
     * @return The validate on borrow indicator
     */
    public boolean isValidateOnBorrow() {
        return validateOnBorrow;
    }

    /**
     * Set validate on borrow indicator.
     * @param validateOnBorrow The validate on borrow indicator
     */
    public void setValidateOnBorrow(
        final boolean validateOnBorrow) {
        this.validateOnBorrow = validateOnBorrow;
    }

    /**
     * Get validation timeout.
     * @return The validation timeout
     */
    public int getValidationTimeout() {
        return (validationTimeout == 0) ? getConnectionTimeout() : validationTimeout;
    }

    /**
     * Set validation timeout.
     * @param validationTimeout The validation timeout, specified in seconds
     */
    public void setValidationTimeout(
        final int validationTimeout) {
        this.validationTimeout = Clamp.clampInt(validationTimeout, 0, Integer.MAX_VALUE);
    }

    /**
     * Get request timeout.
     * @return The request timeout
     */
    public int getRequestTimeout() {
        return requestTimeout;
    }

    /**
     * Set request timeout.
     * @param requestTimeout The request timeout, specified in seconds
     */
    public void setRequestTimeout(
        final int requestTimeout) {
        this.requestTimeout = Clamp.clampInt(requestTimeout * 1000, 0, Integer.MAX_VALUE);
    }

    /**
     * Get backoff period.
     * @return The backoff period
     */
    public int getBackoffPeriod() {
        return backoffPeriod;
    }

    /**
     * Set backoff period.
     * @param backoffPeriod The backoff period, specified in seconds
     */
    public final void setBackoffPeriod(
        final int backoffPeriod) {
        this.backoffPeriod = Clamp.clampInt(backoffPeriod * 1000, 1000, Integer.MAX_VALUE);
    }

    /**
     * Get backoff multiplier.
     * @return The backoff multiplier
     */
    public float getBackoffMultiplier() {
        return backoffMultiplier;
    }

    /**
     * Set backoff multiplier.
     * @param backoffMultiplier The backoff multiplier
     */
    public void setBackoffMultiplier(
        final float backoffMultiplier) {
        this.backoffMultiplier = Clamp.clampFloat(backoffMultiplier, 0, Float.MAX_VALUE);
    }

    /**
     * Get backoff limit.
     * @return The backoff limit
     */
    public int getBackoffLimit() {
        return backoffLimit;
    }

    /**
     * Set backoff limit.
     * @param backoffLimit The backoff limit, specified in seconds
     */
    public void setBackoffLimit(
        final int backoffLimit) {
        this.backoffLimit = Clamp.clampInt(backoffLimit * 1000, 0, Integer.MAX_VALUE);
    }

    /**
     * Get connection inactivity timeout.
     * @return The connection inactivity timeout
     */
    public int getInactivityTimeout() {
        return inactivityTimeout;
    }

    /**
     * Set connection inactivity timeout.
     * @param inactivityTimeout The connection inactivity timeout, specified in seconds
     */
    public void setInactivityTimeout(
        final int inactivityTimeout) {
        this.inactivityTimeout = Clamp.clampInt(inactivityTimeout * 1000, 0, Integer.MAX_VALUE);
    }

    /**
     * Get maximum connection lifetime.
     * @return The maximum connection lifetime
     */
    public int getMaximumLifetime() {
        return maximumLifetime;
    }

    /**
     * Set maximum connection lifetime.
     * @param maximumLifetime The maximum connection lifetime, specified in seconds
     */
    public void setMaximumLifetime(
        final int maximumLifetime) {
        this.maximumLifetime = Clamp.clampInt(maximumLifetime * 1000, 0, Integer.MAX_VALUE);
    }

    /**
     * Get connection prune interval.
     * @return The connection prune interval
     */
    public int getPruneInterval() {
        return pruneInterval;
    }

    /**
     * Set connection prune interval.
     * @param pruneInterval The connection prune interval, specified in seconds
     */
    public void setPruneInterval(
        final int pruneInterval) {
        this.pruneInterval = Clamp.clampInt(pruneInterval * 1000, 0, Integer.MAX_VALUE);
    }

}
