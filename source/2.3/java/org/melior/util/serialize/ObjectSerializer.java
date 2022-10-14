/* __  __    _ _      
  |  \/  |  | (_)       
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
    Service Harness
*/
package org.melior.util.serialize;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import org.melior.service.exception.ApplicationException;
import org.melior.service.exception.ExceptionType;

/**
 * Implements a POJO serializer and deserializer.
 * @author Melior
 * @since 2.0
 */
public class ObjectSerializer<T>{

  /**
   * Implements a lenient object input stream.
   * @author Melior
   */
  class ObjectInputStream extends java.io.ObjectInputStream{

    /**
     * Constructor.
     * @param inputStream The input stream
     * @throws IOException if an error occurs during the construction
     */
    public ObjectInputStream(
      final InputStream inputStream) throws IOException{
            super(inputStream);
    }

    /**
     * Get serialized class descriptor.
     * @return The serialized class descriptor
     * @throws IOException if an input error occurs
     * @throws ClassNotFoundException if the serialized class cannot be found
     */
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException{
            String className;
      ObjectStreamClass classDescriptor;

            className = super.readClassDescriptor().getName();

            classDescriptor = ObjectStreamClass.lookup(Class.forName(className));

      return classDescriptor;
    }

  }

  /**
   * Constructor.
   */
  private ObjectSerializer(){
        super();
  }

  /**
   * Get instance of object serializer.
   * @param <T> The type
   * @return The object serializer
   */
  public static <T> ObjectSerializer<T> of(){
    return new ObjectSerializer<T>();
  }

  /**
   * Serialize object.
   * @param object The object
   * @return The byte array
   * @throws ApplicationException if unable to serialize the object
   */
  public byte[] serialize(
    final T object) throws ApplicationException{
        ByteArrayOutputStream byteArrayOutputStream;
    ObjectOutputStream objectOutputStream;
    byte[] bytes;

    try{
            byteArrayOutputStream = new ByteArrayOutputStream();
      objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
      objectOutputStream.writeObject(object);
      bytes = byteArrayOutputStream.toByteArray();
    }
    catch (Throwable exception){
      throw new ApplicationException(ExceptionType.LOCAL_APPLICATION, "Failed to serialize object: " + exception.getMessage(), exception);
    }

    return bytes;
  }

  /**
   * Deserialize object.
   * @param bytes The byte array
   * @return The object
   * @throws ApplicationException if unable to deserialize the object
   */
  @SuppressWarnings("unchecked")
  public T deserialize(
    final byte[] bytes) throws ApplicationException{
        ByteArrayInputStream byteArrayInputStream;
    ObjectInputStream objectInputStream;
    T object;

    try{
            byteArrayInputStream = new ByteArrayInputStream(bytes);
      objectInputStream = this.new ObjectInputStream(byteArrayInputStream);
      object = (T) objectInputStream.readObject();
    }
    catch (Throwable exception){
      throw new ApplicationException(ExceptionType.LOCAL_APPLICATION, "Failed to deserialize object: " + exception.getMessage(), exception);
    }

    return object;
  }

}
