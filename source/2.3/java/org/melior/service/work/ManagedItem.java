/* __  __      _ _            
  |  \/  |    | (_)           
  | \  / | ___| |_  ___  _ __ 
  | |\/| |/ _ \ | |/ _ \| '__|
  | |  | |  __/ | | (_) | |   
  |_|  |_|\___|_|_|\___/|_|   
        Service Harness
*/
package org.melior.service.work;
import java.io.Serializable;

/**
 * Transforms an object into a managed item with an identifier and a state.
 * Implementations that manage items use the identifier to address the item
 * during persist and restore operations, and maintain the state of the item
 * to provide retries and other lifecycle functions.
 * @author Melior
 * @since 2.3
 */
public class ManagedItem<T> implements Serializable {
    public static final long serialVersionUID = 1620250046171L;

    private String id;

    private T item;

    private String state;

    private String stateMessage;

    /**
     * Constructor.
     * @param item The item
     */
    public ManagedItem(
        final T item) {

        super();

        this.item = item;
    }

    /**
     * Get identifier.
     * @return The identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Set identifier.
     * @param id The identifier
     */
    public void setId(
        final String id) {
        this.id = id;
    }

    /**
     * Get item.
     * @return The item
     */
    public T getItem() {
        return item;
    }

    /**
     * Set item.
     * @param item The item
     */
    public void setItem(
        final T item) {
        this.item = item;
    }

    /**
     * Get state.
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * Set state.
     * @param state The state
     */
    public void setState(
        final String state) {
        this.state = state;
    }

    /**
     * Get state message.
     * @return The state message
     */
    public String getStateMessage() {
        return stateMessage;
    }

    /**
     * Set state message.
     * @param stateMessage The state message
     */
    public void setStateMessage(
        final String stateMessage) {
        this.stateMessage = stateMessage;
    }

}
