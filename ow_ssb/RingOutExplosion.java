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
public class RingOutExplosion implements Animator
{
    
    /** Number of Ringout Animations */
    public static final double NUM_RINGOUTS = 4;
    
    /** Stage Path */
    public final String BASE_DIREC = "./graphics/ingame/animations/ringout";
    
    /* Current Image Representation **/
    public BufferedImage myImage;
    
    /* Animation Counter **/
    public double myAnimationFrame;
    
    /* String to Fetch Image **/
    public int animationDirec;
    
    /** Direction Facing String */
    public String direcFacing;
    
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
    
    /** Location */
    private Coord myLocation;
    
    /**
     * 
     */
    public RingOutExplosion(GameScreen myGameScreen, double scaler, String direction)
    {
        myAnimationFrame = 0;
        direcFacing = direction;
        myScreen = myGameScreen;
        scale = scaler;
        animationDirec = (int) (Math.random() * NUM_RINGOUTS) + 1;
        images = new ArrayList<BufferedImage>();
        timer = new Timer();
        timer.schedule(new Renderer(), 0);
    }
    
    @Override
    public void play()
    {
        
    }
    
    public void play(Coord myPos)
    {
        myLocation = new Coord(myPos.getX(), myPos.getY());
        if (direcFacing.equals("left"))
        {
            myLocation.setX(0 - 280 * .35);
            myLocation.setY(myLocation.getY() - 560 * .35);
        }
        else if (direcFacing.equals("right"))
        {
            myLocation.setX(SmashGame.APP_WIDTH - 1576 * .35);
            myLocation.setY(myLocation.getY() - 560 * .35);
        }
        else
        {
            myLocation.setY(SmashGame.APP_HEIGHT - 1576 * .35);
            myLocation.setX(myLocation.getX() - 560 * .35);
        }
        myScreen.myAnimations.add(this);
    }
    
    @Override
    public void endAnimation()
    {
        myScreen.myAnimations.remove(this);
        myAnimationFrame = 1;
        rendered = false;
        animationDirec = (int) (Math.random() * NUM_RINGOUTS) + 1;
        timer = new Timer();
        timer.schedule(new Renderer(), 0);
    }
    
    @Override
    public void drawMe(Graphics2D g2)
    {
        if (rendered)
        {
            if (myAnimationFrame < images.size() - 1)
            {
                myAnimationFrame = myAnimationFrame + 0.2;
                g2.drawImage(images.get((int) myAnimationFrame), (int) (myLocation.getX() * scale),
                    (int) (myLocation.getY() * scale), null);
            }
            else
            {
                endAnimation();
            }
        }
    }
    
    
    class Renderer extends TimerTask
    {
        
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
                    String fileDirectory = BASE_DIREC + "/" + direcFacing + "/" + animationDirec + "/";
                    if (renderFrame < 10)
                    { fileDirectory = fileDirectory + "0"; }
                    fileDirectory = fileDirectory + (int) renderFrame + ".png";
                    
                    // System.out.println(fileDirectory);
                    
                    // Create Image
                    File image = new File(fileDirectory);
                    myImage = ImageResizer.resizeImage(ImageIO.read(image), scale * 0.35);
                    temp.add(myImage);
                }
                catch (IOException ioe)
                {
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