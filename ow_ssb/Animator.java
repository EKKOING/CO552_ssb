import java.awt.Graphics2D;

/**
 * @author student
 *
 */
public interface Animator {

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
     * @param g2
     */
    public void drawMe(Graphics2D g2);
}