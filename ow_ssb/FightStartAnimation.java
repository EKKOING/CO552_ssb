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

    /** Stage Path */
    public final String BASE_DIREC = "./graphics/ingame/animations/fightstart";

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

    private static final int TOP_L_X = 0;

    private static final int TOP_L_Y = 0;

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
        myScreen.myAnimations.remove(this);
        myScreen.myPlayers.StartPlayers();
    }

    public void drawMe(Graphics2D g2) {
        if (rendered && shouldPlay) {
            if (myAnimationFrame < images.size() - 1) {
                myAnimationFrame = myAnimationFrame + 0.01;
                g2.drawImage(images.get((int) myAnimationFrame), (int) (TOP_L_X * scale), (int) (TOP_L_Y * scale), null);
            } else {
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
                    String fileDirectory = BASE_DIREC + "/";
                    if (renderFrame < 10) {
                        fileDirectory = fileDirectory + "0";
                    }
                    fileDirectory = fileDirectory + (int) renderFrame + ".png";

                    // System.out.println(fileDirectory);

                    // Create Image
                    File image = new File(fileDirectory);
                    myImage = ImageResizer.resizeImage(ImageIO.read(image), scale * 0.35);
                    temp.add(myImage);
                } catch (IOException ioe) {
                    fileExists = false;
                }
                renderFrame++;
            }
            images = temp;
            rendered = true;
            timer.cancel();
        }
    }
}