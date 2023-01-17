/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.client.http;
import java.util.ArrayList;
import java.util.List;
import javax.management.Attribute;

/**
 * Utility functions that either produce HTTP headers or manipulate HTTP headers.
 * @author Melior
 * @since 2.3
 */
public class HttpHeader extends Attribute {

    /**
     * Constructor.
     * @param name The header name
     * @param value The header value
     */
    HttpHeader(
        final String name,
        final String value) {

        super(name, value);
    }

    /**
     * Collect HTTP headers into array.
     * @param parts The HTTP header parts
     * @return The array
     */
    public static HttpHeader[] collect(
        final String... parts) {

        int size = parts.length / 2;
        List<HttpHeader> headers;

        headers = new ArrayList<HttpHeader>(size);

        for (int i = 0; i < size; i+=2) {
            headers.add(new HttpHeader(parts[i], parts[i + 1]));
        }

        return headers.toArray(new HttpHeader[headers.size()]);
    }

}
