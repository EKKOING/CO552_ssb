import java.awt.image.BufferedImage;

/**
 * Class for the Pharah character inherits from player
 * 
 * @author Nicholas Lorentzen
 * @version 20190602
 */
public class Pharah extends Player
{
    
    // Cooldowns
    /** Cooldown for Attack */
    public static final long ATTACK_CD = 600;
    /** Cooldown for Walk */
    public static final long walkCD = 10;
    /** Cooldown for Double Jump */
    private static final int DOUBLEJUMP_CD = 150;
    /** Cooldown for Jump */
    private static final int JUMP_CD = 300;
    /** Length of Attack Animation */
    private static final int ATTACK_ANIMATION_LENGTH = 150;
    
    // Velocities
    /** Velocity of Jump */
    private static final int JUMP_V = 30;
    /** Velocity of Double Jump */
    private static final int DOUBLEJUMP_V = 20;
    
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
        getOtherPlayers().getMyPlayers()
            .add(new PharahRocket(getMyId() * PharahRocket.IDENTIFIER, (int) getMyPos().getX() + newX,
                (int) (getMyPos().getY() - 124), getOtherPlayers(), isFacingRight(), getEnemyId()));
        setCanAttack(false);
        new CooldownTracker(this, (long) ATTACK_CD, "canAttack");
        new CooldownTracker(this, (long) ATTACK_ANIMATION_LENGTH, "attacking");
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
            getMyVector().setY(-JUMP_V);
            setOnGround(false);
            setDoubleJump(false);
            new CooldownTracker(this, (long) JUMP_CD, "doubleJump");
        }
        else if (isDoubleJump())
        {
            getMyVector().setY(getMyVector().getY() - DOUBLEJUMP_V);
            setDoubleJump(false);
            new CooldownTracker(this, (long) DOUBLEJUMP_CD, "doubleJump");
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
        return ATTACK_CD;
    }
    
    @Override
    public String getBaseDirectory()
    {
        return BASE_DIREC;
    }
}