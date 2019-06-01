import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author student
 *
 */
public class FightStartAnimation implements Animator {

    /** Animation Path */
    public final String BASE_DIREC = "./graphics/ingame/animations/fightstart/Layer ";

    /* Current Image Representation **/
    public BufferedImage myImage;

    /* Animation Counter **/
    public double myAnimationFrame;

    /** Timer Thread */
    public Timer timer;

    /* ArrayList of Images **/
    public ArrayList<BufferedImage> images;

    /** Screen Created From */
    public GameScreen myScreen;

    /** Scale */
    public double scale;

    /** False if currently rendering */
    public boolean rendered;

    /** True when Playing Should Start */
    public boolean shouldPlay;

    /** X Location of the animation */
    private static final int TOP_L_X = 0;

    /** Y Location of the animation */
    private static final int TOP_L_Y = 225;

    /** Iteration Amount for Animatioon */
    private static final double FRAME_GAP = 0.2;

    /**
     * 
     */
    public FightStartAnimation(GameScreen myGameScreen, double scaler) {
        myAnimationFrame = 0;
        shouldPlay = false;
        myScreen = myGameScreen;
        scale = scaler;
        images = new ArrayList<BufferedImage>();
        timer = new Timer();
        timer.schedule(new Renderer(), 0);
    }

    public void play() {
        shouldPlay = true;
    }

    public void endAnimation() {
        myScreen.removeAnimation(this);
        myScreen.myPlayers.StartPlayers();
    }

    public void drawMe(Graphics2D g2) {
        if (rendered && shouldPlay) {
            if (myAnimationFrame < images.size() - 1) {
                myAnimationFrame = myAnimationFrame + FRAME_GAP;
                g2.drawImage(images.get((int) myAnimationFrame), (int) (TOP_L_X), (int) (TOP_L_Y), null);
                //System.out.println("Playing Frame Number " + myAnimationFrame);
            } else {
                //System.out.println("Animation Ended");
                endAnimation();
            }
        }
    }

    class Renderer extends TimerTask {
        public void run() {
            ArrayList<BufferedImage> temp = new ArrayList<BufferedImage>();
            int renderFrame = 1;
            boolean fileExists = true;
            while (fileExists) {
                try {
                    // Create File Directory String
                    String fileDirectory = BASE_DIREC;
                    fileDirectory = fileDirectory + (int) renderFrame + ".png";

                    //System.out.println(fileDirectory);

                    // Create Image
                    File image = new File(fileDirectory);
                    myImage = ImageResizer.resizeImage(ImageIO.read(image), scale * 0.75);
                    temp.add(myImage);
                } catch (IOException ioe) {
                    fileExists = false;
                }
                renderFrame++;
            }
            images = temp;
            rendered = true;
            //System.out.println("Game Start Rendered " + images.size() + " Images");
            timer.cancel();
        }
    }
}