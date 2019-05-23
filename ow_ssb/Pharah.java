import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;
import java.io.File;

/**
Class for the Pharah character inherits from player
@author Nicholas Lorentzen
@version 2019/05/21
*/
public class Pharah extends Player
{
    /** Image to draw */
    public BufferedImage myImage;

    /** Image Directory */
    public final String BASE_DIREC = "./graphics/characters/pharah";

    /** Height of Pharah in pixels */
    public static final int MY_HEIGHT = 162;
    /** Width of Pharah in pixels */
    public static final int MY_WIDTH = 166;

    /** Cooldowns for Pharah */
    public static final long attackCD = 500;
    

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
        System.out.println("Pharah Created \n");
    }
    
    /**
    * Basic Melee attack
    * @return true if successful attack
    */
    public boolean attack()
    {
        otherPlayers.myPlayers.add(new PharahRocket(myId, (int) myPos.getX(), (int) myPos.getY() - 100, otherPlayers, facingRight, enemyId));
        canAttack = false;
        new CooldownTracker(this, (long) attackCD, "canAttack");
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

    public void drawMe (Graphics2D g2)
    {
        super.drawMe(g2);

        try {
            //Create File Directory String
            String fileDirectory = BASE_DIREC + facingDirec + animationDirec + "/";
            if (myAnimationFrame < 10) {fileDirectory = fileDirectory + "0";}
            fileDirectory = fileDirectory + (int) myAnimationFrame + ".png";

            //Create Image
            File image = new File(fileDirectory);
            myImage = ImageIO.read(image);
        } 
        catch (IOException ioe) {
            myAnimationFrame = 1;
            try {
                String fileDirectory = BASE_DIREC + facingDirec + animationDirec + "/";
                if (myAnimationFrame < 10) {fileDirectory = fileDirectory + "0";}
                fileDirectory = fileDirectory + (int) myAnimationFrame + ".png";
                //Create Image
                File image = new File(fileDirectory);
                myImage = ImageIO.read(image);
            } catch (IOException ioe2) {
                System.err.println(ioe2);
                String fileDirectory = BASE_DIREC + facingDirec + animationDirec + "/";
                if (myAnimationFrame < 10) {fileDirectory = fileDirectory + "0";}
                fileDirectory = fileDirectory + (int) myAnimationFrame + ".png";
                System.err.println("FILE: " + fileDirectory);
            }
        }
        g2.drawImage(myImage, (int) myPos.getX() - (MY_WIDTH / 2), (int) myPos.getY() - MY_HEIGHT, null);
    }
}