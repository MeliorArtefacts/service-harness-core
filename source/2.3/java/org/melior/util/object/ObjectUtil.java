/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.object;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import org.melior.service.exception.ApplicationException;
import org.melior.service.exception.ExceptionType;

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

  /**
   * Convert from one type of array to another.
   * @param array The input array
   * @param clazz The output element type
   * @param converter The element converter
   * @return The output array
   */
  @SuppressWarnings("unchecked")
  public static <T, U> U[] convertArray(
    final T[] array,
    final Class<U> clazz,
    final Converter<T, U> converter){
        U[] result;

        if (array == null){
      return null;
    }

        result = (U[]) java.lang.reflect.Array.newInstance(clazz, array.length);

    for (int i = 0; i < array.length; i++){
      result[i] = converter.convert(array[i]);
    }

    return result;
  }

  /**
   * Convert from one type of list to another.
   * @param list The input list
   * @param clazz The output element type
   * @param converter The element converter
   * @return The output list
   */
  public static <T, U> List<U> convertList(
    final List<T> list,
    final Class<U> clazz,
    final Converter<T, U> converter){
        List<U> result;

        if (list == null){
      return null;
    }

        result = new ArrayList<U>(list.size());

    for (T element : list){
      result.add(converter.convert(element));
    }

    return result;
  }

  /**
   * Require object to not be {@code null}.  If the object is
   * {@code null} then an exception is raised with the message.
   * @param obj The object
   * @param message The message
   */
  public static <T> void requireNonNull(
    final T obj,
    final String message) throws ApplicationException{

        if (obj == null){
            throw new ApplicationException(ExceptionType.LOCAL_APPLICATION, message);
    }

  }

  /**
   * Ensures that the returned list is never {@code null}.  If the input list
   * is {@code null} then a new list is created.
   * @param list The input list
   * @return The input list, or a new list if the input list is null
   */
  public static <T> List<T> ensureNonNull(
    final List<T> list){
    return (list == null) ? new ArrayList<T>() : list;
  }

  /**
   * Translate object.  If no option matches the object then the following applies:
   * if the last option is unary then it is returned as a default translation,
   * otherwise {@code null} is returned.
   * @param obj The object
   * @param options The available options
   * @return The translation
   */
  @SafeVarargs
  public static <T> T translate(
    final T obj,
    final T... options){
        int length;

        length = (options.length >> 1) << 1;

        for (int i = 0; i < length; i+=2){

            if (options[i].equals(obj) == true){
                return options[i + 1];
      }

    }

        return ((options.length % 2) == 1) ? options[options.length - 1] : null;
  }

}
