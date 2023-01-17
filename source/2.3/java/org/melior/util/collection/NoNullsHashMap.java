/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.util.collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Implements a {@code HashMap} that only accepts a new entry if
 * the provided value is not {@code null}.
 * @author Melior
 * @since 2.3
 */
public class NoNullsHashMap<K, V> extends HashMap<K, V> {
    private static final long serialVersionUID = 3624374282301265L;

    /**
     * Constructor.
     */
    public NoNullsHashMap() {

        super();
    }

    /**
     * Constructor.
     * @param initialCapacity The initial capacity
     * @param loadFactor The load factor
     */
    public NoNullsHashMap(
        final int initialCapacity,
        final float loadFactor) {

        super(initialCapacity, loadFactor);
    }

    /**
     * Constructor.
     * @param initialCapacity The initial capacity
     */
    public NoNullsHashMap(
        final int initialCapacity) {

        super(initialCapacity);
    }

    /**
     * Constructor.
     * @param map The source map
     */
    public NoNullsHashMap(
        final Map<? extends K, ? extends V> map) {
        super(map);
    }

    /**
     * Add new entry to map if provided value is not {@code null}.
     * @return The previous value, or null if the provided value is {@code null}
     */
    public V put(
        final K key,
        final V value) {
        return (value == null) ? null : super.put(key, value);
    }

}
