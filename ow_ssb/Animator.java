import java.awt.Graphics2D;

/**
 * @author student
 *
 */
public interface Animator {

    public void play();

    public void endAnimation();

    public void drawMe(Graphics2D g2);
}