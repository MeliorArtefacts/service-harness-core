/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.util.serialize;
import org.melior.service.exception.ApplicationException;
import org.melior.service.exception.ExceptionType;

/**
 * Implements a POJO serializer and deserializer.
 * @author Melior
 * @since 2.0
 */
public class ObjectSerializer<T>
{

	/**
	 * Implements a lenient object input stream.
	 * @author Melior
	 */
	class ObjectInputStream extends java.io.ObjectInputStream
	{

		/**
		 * Constructor.
		 * @param inputStream The input stream
		 * @throws java.io.IOException when the initialization fails
		 */
		public ObjectInputStream(
			final java.io.InputStream inputStream) throws java.io.IOException
		{
						super(inputStream);
		}

		/**
		 * Get serialized class descriptor.
		 * @return The serialized class descriptor
		 * @throws java.io.IOException when an input error occurs
		 * @throws java.lang.ClassNotFoundException when the serialized class cannot be found
		 */
		protected java.io.ObjectStreamClass readClassDescriptor() throws java.io.IOException, java.lang.ClassNotFoundException
		{
						String className;
			java.io.ObjectStreamClass classDescriptor;

						className = super.readClassDescriptor().getName();

						classDescriptor = java.io.ObjectStreamClass.lookup(Class.forName(className));

			return classDescriptor;
		}

	}

	/**
	 * Constructor.
	 */
	public ObjectSerializer()
	{
				super();
	}

	/**
	 * Serialize object.
	 * @param object The object
	 * @return The byte array
	 * @throws ApplicationException when unable to serialize the object
	 */
	public byte[] serialize(
		final T object) throws ApplicationException
	{
				java.io.ByteArrayOutputStream byteArrayOutputStream;
		java.io.ObjectOutputStream objectOutputStream;
		byte[] bytes;

		try
		{
						byteArrayOutputStream = new java.io.ByteArrayOutputStream();
			objectOutputStream = new java.io.ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
		    bytes = byteArrayOutputStream.toByteArray();
		}
		catch (java.lang.Throwable exception)
		{
			throw new ApplicationException(ExceptionType.LOCAL_APPLICATION, "Failed to serialize object: " + exception.getMessage(), exception);
		}

		return bytes;
	}

	/**
	 * Deserialize object.
	 * @param bytes The byte array
	 * @return The object
	 * @throws ApplicationException when unable to deserialize the object
	 */
	@SuppressWarnings("unchecked")
	public T deserialize(
		final byte[] bytes) throws ApplicationException
	{
				java.io.ByteArrayInputStream byteArrayInputStream;
		java.io.ObjectInputStream objectInputStream;
		T object;

		try
		{
						byteArrayInputStream = new java.io.ByteArrayInputStream(bytes);
			objectInputStream = this.new ObjectInputStream(byteArrayInputStream);
			object = (T) objectInputStream.readObject();
		}
		catch (java.lang.Throwable exception)
		{
			throw new ApplicationException(ExceptionType.LOCAL_APPLICATION, "Failed to deserialize object: " + exception.getMessage(), exception);
		}

		return object;
	}

}
