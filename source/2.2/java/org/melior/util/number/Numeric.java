/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.number;

/**
 * Clamps a value between the minimum and maximum values provided.
 * @author Melior
 * @since 2.1
 */
public class Numeric{

  /**
   * Constructor.
   */
  private Numeric(){
        super();
  }

  /**
   * Determine whether string is a valid {@code int}.
   * @param string The string
   * @return true if the string is a valid {@code int}, false otherwise
   */
  public static boolean isInt(
    final String string){

    try{
      Integer.parseInt(string);
    }
    catch (Exception exception){
      return false;
    }

    return true;
  }

  /**
   * Determine whether string is a valid {@code float}.
   * @param string The string
   * @return true if the string is a valid {@code float}, false otherwise
   */
  public static boolean isFloat(
    final String string){

    try{
      Float.parseFloat(string);
    }
    catch (Exception exception){
      return false;
    }

    return true;
  }

  /**
   * Determine whether string is a valid {@code long}.
   * @param string The string
   * @return true if the string is a valid {@code long}, false otherwise
   */
  public static boolean isLong(
    final String string){

    try{
      Long.parseLong(string);
    }
    catch (Exception exception){
      return false;
    }

    return true;
  }

  /**
   * Determine whether string is a valid {@code double}.
   * @param string The string
   * @return true if the string is a valid {@code double}, false otherwise
   */
  public static boolean isDouble(
    final String string){

    try{
      Double.parseDouble(string);
    }
    catch (Exception exception){
      return false;
    }

    return true;
  }

}
