import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

/**
 * Class for the Pharah character inherits from player
 * 
 * @author Nicholas Lorentzen
 * @version 20190602
 */
public class Pharah extends Player
{
    
    /** Image to draw */
    private BufferedImage myImage;
    
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
    
    /**
     * Constructs a player of type pharah
     * 
     * @param myId   the id of the player (1 or 2)
     * @param xStart the x start location
     * @param yStart the y start location
     * @param list   Players class passthrough to be able to access enemies
     */
    public Pharah(int myId, int xStart, int yStart, Players list)
    {
        super(myId, xStart, yStart, list);
        System.out.println("Pharah Created \n");
    }
    
    /**
     * Rocket Attack
     * 
     * @return true if successful attack
     */
    @Override
    public boolean attack()
    {
        setAttacking(true);
        
        if (isOnGround())
        { getMyVector().setX(0); }
        
        int newX = -PharahRocket.PHARAH_ARM_OFFSET;
        if (isFacingRight())
        { newX = -newX; }
        getOtherPlayers().getMyPlayers().add(new PharahRocket(getMyId() * 100 + 1, (int) getMyPos().getX() + newX,
            (int) (getMyPos().getY() - 124), getOtherPlayers(), isFacingRight(), getEnemyId()));
        setCanAttack(false);
        new CooldownTracker(this, (long) attackCD, "canAttack");
        new CooldownTracker(this, (long) 150, "attacking");
        return true;
    }
    
    @Override
    public void createDirectoryString()
    {
        super.createDirectoryString();
        if (isOnGround() && isAttacking())
        { setAnimationDirec("/standingshooting"); }
    }
    
    /**
     * Block method
     * 
     * @return true if successful block
     */
    @Override
    public boolean block()
    {
        return true;
    }
    
    @Override
    public void jump()
    {
        if (isOnGround())
        {
            getMyVector().setY(-30);
            setOnGround(false);
            setDoubleJump(false);
            new CooldownTracker(this, (long) 300, "doubleJump");
        }
        else if (isDoubleJump())
        {
            getMyVector().setY(getMyVector().getY() - 20);
            setDoubleJump(false);
            new CooldownTracker(this, (long) 150, "doubleJump");
        }
    }
    
    @Override
    public int getHeight()
    {
        return MY_HEIGHT;
    }
    
    @Override
    public int getWidth()
    {
        return MY_WIDTH;
    }
    
    @Override
    public double getStartHP()
    {
        return STARTHEALTH;
    }
    
    @Override
    public int getMaxWalkV()
    {
        return MAX_WALKV;
    }
    
    @Override
    public long getWalkCD()
    {
        return walkCD;
    }
    
    @Override
    public long getAttackCD()
    {
        return attackCD;
    }
    
    @Override
    public String getBaseDirectory()
    {
        return BASE_DIREC;
    }
}