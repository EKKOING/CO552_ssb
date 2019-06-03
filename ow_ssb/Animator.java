import java.awt.Graphics2D;

/**
 * @author Nicholas Lorentzen
 * @version 20190602
 */
public interface Animator
{
    
    /**
     * Plays the animation
     */
    public void play();
    
    /**
     * Ends the animation
     */
    public void endAnimation();
    
    /**
     * Draws the animation
     * 
     * @param g2 Graphics Passthrough
     */
    public void drawMe(Graphics2D g2);
}