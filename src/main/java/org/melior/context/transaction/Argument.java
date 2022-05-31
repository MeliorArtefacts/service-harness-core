/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.context.transaction;

/**
 * An argument that may be added to the transaction context of a thread
 * during the processing of a transaction.  The argument is recorded in
 * the transaction log.
 * @author Melior
 * @since 2.1
 */
public class Argument
{
		private String name;

		private String value;

	/**
	 * Constructor.
	 */
	public Argument()
	{
				super();
	}

	/**
	 * Get name.
	 * @return The name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Set name.
	 * @param name The name
	 */
	public void setName(
		final String name)
	{
		this.name = name;
	}

	/**
	 * Get value.
	 * @return The value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Set value.
	 * @param value The value
	 */
	public void setValue(
		final String value)
	{
		this.value = value;
	}

}
