/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.object;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Utility functions that apply to all objects.
 * @author Melior
 * @since 2.2
 */
public interface ObjectUtil{

  /**
   * Return first object if it is non-{@code null},
   * otherwise return second object.
   * @param obj1 The first object
   * @param obj2 The second object
   * @return
   */
  public static <T> T coalesce(
    final T obj1,
    final T obj2){
    return (obj1 == null) ? obj2 : obj1;
  }

  /**
   * Return first non-{@code null} object in list.
   * @param objects The list of objects
   * @return The first non-{@code null} object
   */
  @SafeVarargs
  public static <T> T coalesce(
    final T... objects){

        for (T obj : objects){

            if (obj != null){
        return obj;
      }

    }

    return null;
  }

  /**
   * Determine whether object is contained within array.
   * @param array The array of objects
   * @param obj The object
   * @return true if the object is contained within the array, false otherwise
   */
  public static <T> boolean contains(
    final T[] objects,
    final T obj){

        for (int i = 0; i < objects.length; i++){

            if (objects[i].equals(obj) == true){
        return true;
      }

    }

    return false;
  }

  /**
   * Collect objects into array.
   * @param objects The objects
   * @return The array
   */
  @SafeVarargs
  public static <T> Object[] collect(
    final T... objects){
    return objects;
  }

  /**
   * Get all interfaces of given class.
   * @param clazz The class
   * @return The array of interfaces
   */
  public static Class<?>[] getAllInterfaces(
    final Class<?> clazz){
        HashSet<Class<?>> interfaceSet;

        interfaceSet = new LinkedHashSet<Class<?>>();

        getAllInterfaces(interfaceSet, clazz);

        return interfaceSet.toArray(new Class[interfaceSet.size()]);
  }

  /**
   * Get all interfaces of given class.
   * @param interfaceSet The interface set
   * @param clazz The class
   */
  public static void getAllInterfaces(
    final HashSet<Class<?>> interfaceSet,
    final Class<?> clazz){
        Class<?> cls = clazz;
    Class<?>[] interfaces;

        while (cls != null){
            interfaces = cls.getInterfaces();

            for (Class<?> i : interfaces){

                if (interfaceSet.add(i) == true){
                    getAllInterfaces(interfaceSet, i);
        }

      }

            cls = cls.getSuperclass();
    }

  }

}
