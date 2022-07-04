/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.slf4j.impl;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.spi.MDCAdapter;

/**
 * Binds the SLF4J MDC adapter to a NOOP handler.
 * @author Melior
 * @since 2.1
 */
public class StaticMDCBinder{
    private static StaticMDCBinder instance = new StaticMDCBinder();

    private MDCAdapter mdcAdapter;

  /**
   * Constructor.
   */
  private StaticMDCBinder(){
        super();

        mdcAdapter = new NOPMDCAdapter();
  }

  /**
   * Get singleton instance.
   * @return The singleton instance
   */
  public static final StaticMDCBinder getSingleton(){
    return instance;
  }

  /**
   * Get MDC adapter.
   * @return The MDC adapter
   */
  public MDCAdapter getMDCA(){
    return mdcAdapter;
  }

  /**
   * Get MDC adapter class name.
   * @return The MDC adapter class name
   */
  public String getMDCAdapterClassStr(){
    return mdcAdapter.getClass().getName();
  }

}
