/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.client.core;

/**
 * Base class for {@code String}-based client payloads that wish to make their
 * raw content visible in addition to there processed POJO content.
 * @author Melior
 * @since 2.0
 */
public abstract class RawAwarePayload
{
		private transient String raw;

	/**
	 * Constructor.
	 */
	public RawAwarePayload()
	{
				super();
	}

	/**
	 * Get raw payload.
	 * @return The raw payload
	 */
	public String getRaw()
	{
		return raw;
	}

	/**
	 * Set raw payload.
	 * @param raw The raw payload
	 */
	public void setRaw(
		final String raw)
	{
				this.raw = raw;
	}

}
