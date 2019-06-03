/**
 * Interface for player classes
 * 
 * @author Nicholas Lorentzen
 * @version 20190403
 */
public interface Character
{
    /**
     * Has current character attack
     * @return true if attack successful, else false
     */
    public boolean attack();
    
    /**
     * Has current character block
     * @return true if block successful, else false
     */
    public boolean block();
    
    /**
     * Moves current player based on key
     * @param key Key to check moves for
     */
    public void move(Key key);
}