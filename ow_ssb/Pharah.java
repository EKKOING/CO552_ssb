import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
Class for the Pharah character inherits from player
@author Nicholas Lorentzen
@version 2019/05/21
*/
public class Pharah extends Player
{
    /** Image to draw */
    public BufferedImage myImage;

    /* ArrayList of Images **/
	public ArrayList<BufferedImage> images;

    /** Image Directory */
    public final String BASE_DIREC = "./graphics/characters/pharah";

    /** Height of Pharah in pixels */
    public static final int MY_HEIGHT = 162;
    /** Width of Pharah in pixels */
    public static final int MY_WIDTH = 166;
    /** Width of Pharah in pixels */
    public static final int STARTHEALTH = 400;

    /** Cooldowns for Pharah */
    public static final long attackCD = 600;
    public static final long walkCD = 10;

    /** Timer thread */
    public Timer timer;
    

    /**
     * Constructs a player of type pharah
     * @param myId the id of the player (1 or 2)
     * @param xStart the x start location
     * @param yStart the y start location
     * @param list Players class passthrough to be able to access enemies
     */
    public Pharah(int myId, int xStart, int yStart, Players list)
    {
        super(myId, xStart, yStart, list);
        images = new ArrayList<BufferedImage>();
        System.out.println("Pharah Created \n");
    }
    
    /**
    * Rocket Attack
    * @return true if successful attack
    */
    public boolean attack()
    {
        attacking = true;
        int newX = -55;
        if(facingRight){newX = 55;}
        otherPlayers.myPlayers.add(new PharahRocket(myId * 100 + 1, (int) myPos.getX() + newX, (int) (myPos.getY() - 124), otherPlayers, facingRight, enemyId));
        canAttack = false;
        new CooldownTracker(this, (long) attackCD, "canAttack");
        new CooldownTracker(this, (long) 150, "attacking");
        return true;
    }

    /**
    * Block method
    * @return true if successful block
    */
    public boolean block()
    {
        return true;
    }

    public void jump() {
        if (onGround) {
            myVector.setY(-30);
            onGround = false;
            doubleJump = false;
            new CooldownTracker(this, (long) 300, "doubleJump");
        } else if (doubleJump) {
            myVector.setY(myVector.getY() - 20);
            doubleJump = false;
            new CooldownTracker(this, (long) 150, "doubleJump");
        }
    }

    public void renderNewFrames()
    {
        images = new ArrayList<BufferedImage>();
        int renderFrame = 1;
		boolean fileExists = true;
		while(fileExists)
		{
			try {
				//Create File Directory String
				String fileDirectory = BASE_DIREC + facingDirec + animationDirec + "/";
				if (renderFrame < 10) {fileDirectory = fileDirectory + "0";}
				fileDirectory = fileDirectory + (int) renderFrame + ".png";
	
				//Create Image
				File image = new File(fileDirectory);
				myImage = ImageResizer.resizeImage(ImageIO.read(image), scale);
				images.add(myImage);
			} 
			catch (IOException ioe) {
				fileExists = false;
			}
			renderFrame++;
		}
    }

    public void drawMe (Graphics2D g2)
    {
        super.drawMe(g2);
        if(!((facingDirec + animationDirec).equals(prevDirec)))
        {
            //renderNewFrames();
            timer = new Timer();
            timer.schedule(new Renderer(), 0);
        }

        if(myAnimationFrame < images.size() - 2)
		{
            myAnimationFrame = myAnimationFrame + 0.1;
		}
		else
		{
			myAnimationFrame = 0;
		}
        
        try {
            g2.drawImage(images.get((int) myAnimationFrame), (int) (scale * (myPos.getX() - (MY_WIDTH / 2))), (int) (scale * (myPos.getY() - MY_HEIGHT)), null);
        } 
        catch (IndexOutOfBoundsException e) 
        {

        }
        
    }

    public int getHeight()
    {
        return MY_HEIGHT;
    }

    public int getWidth()
    {
        return MY_WIDTH;
    }

    public double getStartHP()
    {
        return STARTHEALTH;
    }

    public int getMaxWalkV()
    {
        return MAX_WALKV;
    }

    public long getWalkCD()
    {
        return walkCD;
    }

    public long getAttackCD()
    {
        return attackCD;
    }


    class Renderer extends TimerTask {
        public void run() {
            ArrayList<BufferedImage> temp = new ArrayList<BufferedImage>();
            int renderFrame = 1;
            boolean fileExists = true;
            while(fileExists)
            {
                try {
                    //Create File Directory String
                    String fileDirectory = BASE_DIREC + facingDirec + animationDirec + "/";
                    if (renderFrame < 10) {fileDirectory = fileDirectory + "0";}
                    fileDirectory = fileDirectory + (int) renderFrame + ".png";
        
                    //Create Image
                    File image = new File(fileDirectory);
                    myImage = otherPlayers.myGame.iR.resizeImage(ImageIO.read(image));
                    temp.add(myImage);
                } 
                catch (IOException ioe) {
                    fileExists = false;
                }
                renderFrame++;
            }
            images = temp;
            timer.cancel();
        }
    }
}