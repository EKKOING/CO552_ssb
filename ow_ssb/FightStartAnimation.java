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
public class FightStartAnimation implements Animator
{
    
    /** Animation Path */
    public final String BASE_DIREC = "./graphics/ingame/animations/fightstart/Layer ";
    
    /* Current Image Representation **/
    public BufferedImage loadingScreen;
    
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
    private static final int TOP_L_X = -80;
    
    /** Y Location of the animation */
    private static final int TOP_L_Y = 225;
    
    /** Iteration Amount for Animatioon */
    private static final double FRAME_GAP = 0.2;
    
    /** Covert from source scale to 900x1400 */
    private static final double BASE_SCALE = 0.75;
    
    /**
     * Construct a fight start animation
     * 
     * @param myGameScreen Screen stored in
     * @param scaler       Scale to run at
     */
    public FightStartAnimation(GameScreen myGameScreen, double scaler)
    {
        myAnimationFrame = 0;
        shouldPlay = false;
        myScreen = myGameScreen;
        scale = scaler;
        try
        {
            File image = new File(BASE_DIREC + "loadingscreen.png");
            loadingScreen = ImageResizer.resizeImage(ImageIO.read(image), scale * BASE_SCALE);
        }
        catch (IOException ioe)
        {
            // Oh well
        }
        images = new ArrayList<BufferedImage>();
        timer = new Timer();
        timer.schedule(new Renderer(), 0);
    }
    
    /** Plays the animation */
    @Override
    public void play()
    {
        shouldPlay = true;
    }
    
    public boolean isRendered()
    {
        return rendered;
    }
    
    @Override
    public void endAnimation()
    {
        myScreen.removeAnimation(this);
        myScreen.startGame();
    }
    
    @Override
    public void drawMe(Graphics2D g2)
    {
        if (rendered && shouldPlay)
        {
            if (myAnimationFrame < images.size() - 1)
            {
                myAnimationFrame = myAnimationFrame + FRAME_GAP;
                g2.drawImage(images.get((int) myAnimationFrame), (int) (TOP_L_X), (int) (TOP_L_Y), null);
                // System.out.println("Playing Frame Number " + myAnimationFrame);
            }
            else
            {
                // System.out.println("Animation Ended");
                endAnimation();
            }
        }
        else
        {
            try
            {
                g2.drawImage(loadingScreen, 0, 0, null);
            }
            catch (NullPointerException e)
            {
                // This will happen a couple of times depending on processing time
            }
        }
    }
    
    
    class Renderer extends TimerTask
    {
        
        /**
         *
         */
        public void run()
        {
            ArrayList<BufferedImage> temp = new ArrayList<BufferedImage>();
            int renderFrame = 1;
            boolean fileExists = true;
            while (fileExists)
            {
                try
                {
                    // Create File Directory String
                    String fileDirectory = BASE_DIREC;
                    fileDirectory = fileDirectory + (int) renderFrame + ".png";
                    
                    // System.out.println(fileDirectory);
                    
                    // Create Image
                    File image = new File(fileDirectory);
                    BufferedImage renderedFrame = ImageResizer.resizeImage(ImageIO.read(image), scale * BASE_SCALE);
                    temp.add(renderedFrame);
                }
                catch (IOException ioe)
                {
                    fileExists = false;
                }
                renderFrame++;
            }
            images = temp;
            rendered = true;
            // System.out.println("Game Start Rendered " + images.size() + " Images");
            timer.cancel();
        }
    }
}