/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.slf4j.impl;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MarkerFactoryBinder;

/**
 * Binds the SLF4J marker factory to a basic handler.
 * @author Melior
 * @since 2.1
 */
public class StaticMarkerBinder implements MarkerFactoryBinder{
    private static StaticMarkerBinder instance = new StaticMarkerBinder();

    private IMarkerFactory markerFactory;

  /**
   * Constructor.
   */
  private StaticMarkerBinder(){
        super();

        markerFactory = new BasicMarkerFactory();
  }

  /**
   * Get singleton instance.
   * @return The singleton instance
   */
  public static StaticMarkerBinder getSingleton(){
    return instance;
  }

  /**
   * Get marker factory.
   * @return The marker factory
   */
  public IMarkerFactory getMarkerFactory(){
    return markerFactory;
  }

  /**
   * Get marker factory class name.
   * @return The marker factory class name
   */
  public String getMarkerFactoryClassStr(){
    return markerFactory.getClass().getName();
  }

}
