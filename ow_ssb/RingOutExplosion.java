import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

/**
 * @author Nicholas Lorentzen
 * @version 20190602
 */
public class RingOutExplosion implements Animator
{

    /** Convert Source to 1440*900 */
    private static final double SOURCE_SCALE = .35;
    
    /** Number of Ringout Animations */
    public static final double NUM_RINGOUTS = 4;

    //Image Controls
    /** Amount to Step Frames By */
    private static final double FRAME_STEPPER = 0.2;
    /** Normal Edge Offset */
    private static final int EDGE_OFFSET = 1576;
    /** Offset From Center */
    private static final int CENTER_OFFSET = 560;
    /** Alternate Offset */
    private static final int ALT_OFFSET = 280;
    
    /** Stage Path */
    public final String BASE_DIREC = "./graphics/ingame/animations/ringout";
    
    /* Current Image Representation **/
    private BufferedImage myImage;
    
    /* Animation Counter **/
    private double myAnimationFrame;
    
    /* String to Fetch Image **/
    private int animationDirec;
    
    /** Direction Facing String */
    private String direcFacing;
    
    /** Timer Thread */
    private Timer timer;
    
    /* ArrayList of Images **/
    private ArrayList<BufferedImage> images;
    
    /** Screen Created From */
    private GameScreen myScreen;
    
    /** Scale */
    private double scale;
    
    /** False if currently rendering */
    private boolean rendered;
    
    /** Location */
    private Coord myLocation;
    
    /**
     * Constructs A New Ring Out Explosion
     * 
     * @param myGameScreen GameScreen Passthrough
     * @param scaler       Scaler for Game
     * @param direction    Direction of Explosion
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
    
    /**
     * Plays Animation
     * @param myPos Position to Play At
     */
    public void play(Coord myPos)
    {
        myLocation = new Coord(myPos.getX(), myPos.getY());
        if (direcFacing.equals("left"))
        {
            myLocation.setX(0 - ALT_OFFSET * SOURCE_SCALE);
            myLocation.setY(myLocation.getY() - CENTER_OFFSET * SOURCE_SCALE);
        }
        else if (direcFacing.equals("right"))
        {
            myLocation.setX(SmashGame.APP_WIDTH - EDGE_OFFSET * SOURCE_SCALE);
            myLocation.setY(myLocation.getY() - CENTER_OFFSET * SOURCE_SCALE);
        }
        else
        {
            myLocation.setY(SmashGame.APP_HEIGHT - EDGE_OFFSET * SOURCE_SCALE);
            myLocation.setX(myLocation.getX() - CENTER_OFFSET * SOURCE_SCALE);
        }
        myScreen.getMyAnimations().add(this);
    }
    
    @Override
    public void endAnimation()
    {
        myScreen.getMyAnimations().remove(this);
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
                myAnimationFrame = myAnimationFrame + FRAME_STEPPER;
                g2.drawImage(images.get((int) myAnimationFrame), (int) (myLocation.getX() * scale),
                    (int) (myLocation.getY() * scale), null);
            }
            else
            {
                endAnimation();
            }
        }
    }
    
    /**
     * Render Class
     */
    class Renderer extends TimerTask
    {
        /**
         * Render Images
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
                    String fileDirectory = BASE_DIREC + "/" + direcFacing + "/" + animationDirec + "/";
                    if (renderFrame < 10)
                    { fileDirectory = fileDirectory + "0"; }
                    fileDirectory = fileDirectory + (int) renderFrame + ".png";
                    
                    // System.out.println(fileDirectory);
                    
                    // Create Image
                    File image = new File(fileDirectory);
                    myImage = ImageResizer.resizeImage(ImageIO.read(image), scale * SOURCE_SCALE);
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