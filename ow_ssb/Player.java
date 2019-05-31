import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class from which all characters inherit properties from
 * 
 * @author Nicholas Lorentzen
 * @version 2019/05/21
 *
 */
public class Player {

    /** Coord object that holds the postion of the player */
    public Coord myPos;

    /** Velocity vector for physics based movement system */
    public Coord myVector;

    /* Id number to identify which player this represents **/
    public int myId;

    /* Id number of opponent **/
    public int enemyId;

    /* Int to hold the health stat **/
    public double healthAmt;
    /* Int to hold the damage done stat **/
    public int dmgDone;
    /* Int to hold the damage taken stat **/
    public int dmgTaken;

    /* Image Representation **/
    public BufferedImage myImage;

    /** Thread Manager for Rendering */
    public Timer timer;

    /* Key Bindings **/
    public int moveLeft;
    public int moveRight;
    public int moveAttack;
    public int moveSpecial;
    public int moveJump;
    public int moveDown;

    /* Base Directory of Images **/
    public final String BASE_DIREC = "./graphics/characters/";

    /* Base Directory of Images **/
    public final String BANNER_DIREC = "./graphics/ingame/banner/";

    /* ArrayList of Images **/
    public ArrayList<BufferedImage> images;

    /** Directory for direction facing */
    public String facingDirec;

    /** Directory for animation */
    public String animationDirec;

    /** Directory of previous animation */
    public String prevDirec;

    /** Animation Frame Number */
    public double myAnimationFrame;

    /** Direction currently facing */
    public boolean facingRight;

    /** On the ground or not */
    public boolean onGround;

    /** Has the double jump available */
    public boolean doubleJump;

    // All Variable Below Should be Overridden by any Child Classes
    /** Default Height and Width */
    public static final int MY_HEIGHT = 191;
    public static final int MY_WIDTH = 166;

    /* Cooldowns for moves **/
    public boolean canWalk;
    public static final long walkCD = 10;
    public boolean canAttack;
    public boolean attacking;
    public static final long attackCD = 500;
    public static final long respawnCD = 5000;

    /* Max walk speed **/
    public static final int MAX_WALKV = 80;

    /** Boolean Dead State */
    public boolean dead;

    /** Boolean Invincibilty */
    public boolean invincible;

    /* Passthrough of Players class to be able to interact with others **/
    public Players otherPlayers;

    /* Int to hold the default health to start with **/
    public static final double STARTHEALTH = 100;

    /** Lives Left */
    public int livesLeft;

    /* Buffered Image of Banner **/
    private BufferedImage myBanner;

    private RingOutExplosion ringOutLeft;
    private RingOutExplosion ringOutRight;
    private RingOutExplosion ringOutDown;

    /* Image Resizer **/
    public double scale;

    /**
     * @param playerNum Id number to assign the player (1 or 2)
     * @param xStart    X location to start player at
     * @param yStart    Y location to start player at
     * @param list      Players class passthrough
     */
    public Player(int playerNum, int xStart, int yStart, Players list) {
        myId = playerNum;
        otherPlayers = list;

        images = new ArrayList<BufferedImage>();

        myPos = new Coord((double) xStart, (double) yStart);
        myVector = new Coord(0.0, 0.0);

        healthAmt = getStartHP();
        canWalk = true;
        canAttack = true;
        onGround = true;
        dmgDone = 0;
        dmgTaken = 0;
        myAnimationFrame = 0;
        facingDirec = "";
        animationDirec = "";
        prevDirec = "";
        setKeybindings();
        // System.out.print("Player of type ");
        scale = otherPlayers.myGame.scale;
        livesLeft = SmashGame.NUM_LIVES;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Player compPlayer = (Player) o;
        return myId == compPlayer.myId;
    }

    @Override
    public int hashCode() {
        return myId;
    }

    /**
     * Sets keybindings based off of ID num
     */
    public void setKeybindings() {
        if (myId == 1) {
            moveLeft = KeyEvent.VK_A;
            moveRight = KeyEvent.VK_D;
            moveJump = KeyEvent.VK_W;
            moveDown = KeyEvent.VK_S;
            moveAttack = KeyEvent.VK_E;
            moveSpecial = KeyEvent.VK_Q;
            facingRight = true;
            enemyId = 2;
            try {
                File image = new File(BANNER_DIREC + "1.png");
                myBanner = ImageResizer.resizeImage(ImageIO.read(image), scale);
            } catch (IOException ioe2) {

            }
        }

        if (myId == 2) {
            moveLeft = KeyEvent.VK_J;
            moveRight = KeyEvent.VK_L;
            moveJump = KeyEvent.VK_I;
            moveDown = KeyEvent.VK_K;
            moveAttack = KeyEvent.VK_O;
            moveSpecial = KeyEvent.VK_U;
            facingRight = false;
            enemyId = 1;
            try {
                File image = new File(BANNER_DIREC + "2.png");
                myBanner = ImageResizer.resizeImage(ImageIO.read(image), scale);
            } catch (IOException ioe2) {

            }
        }

        if (myId == 1 || myId == 2) {
            ringOutLeft = new RingOutExplosion(otherPlayers.myGame.myGameScreen, scale, "left");
            ringOutRight = new RingOutExplosion(otherPlayers.myGame.myGameScreen, scale, "right");
            ringOutDown = new RingOutExplosion(otherPlayers.myGame.myGameScreen, scale, "down");
        }
    }

    /**
     * Gets current health
     * 
     * @return the current health amount
     */
    public double getHealth() {
        return healthAmt;
    }

    /**
     * Gets current postion
     * 
     * @return the current position as a Coord object
     */
    public Coord getPos() {
        return myPos;
    }

    /**
     * Gets the damage taken
     * 
     * @return the damage taken as an int
     */
    public int getDmgTaken() {
        return dmgTaken;
    }

    /**
     * Gets the damage done
     * 
     * @return the damage done as an int
     */
    public int getDmgDone() {
        return dmgDone;
    }

    /**
     * Damages the player
     * 
     * @param dmgDo Amount of damage to attempt to do
     * @return Amount of damage done to player
     */
    public double getDamaged(double dmgDo) {
        if (invincible) {
            return 0.0;
        } else {
            // Critical Hit Randomizer
            if (Math.random() > 0.99) {
                healthAmt -= -dmgDo * 2;
            } else {
                healthAmt -= dmgDo;
            }
        }

        return 0;
    }

    /**
     * Knocks the player back an amount based on current health
     * 
     * @param amt Amount to knockback
     */
    public void knockBackX(double amt) {
        double inverseMass = ((double) getStartHP()) / (healthAmt + 50);
        double knockback = inverseMass * amt;
        myVector.setX(myVector.getX() + knockback);
    }

    /**
     * Knocks the player back an amount based on current health
     * 
     * @param amt Amount to knockback
     */
    public void knockBackY(double amt) {
        double inverseMass = ((double) getStartHP()) / (healthAmt + 50);
        double knockback = inverseMass * amt;
        myVector.setY(myVector.getY() + knockback);
    }

    /**
     * Manages collisions with the main floor and balcony
     */
    public void checkGround() {
        if (myPos.getY() >= Stage.FLOOR_TOP && myPos.getY() <= Stage.FLOOR_TOP + 10 && myPos.getX() > Stage.FLOOR_GAP
                && myPos.getX() < SmashGame.APP_WIDTH - Stage.FLOOR_GAP) {

            myPos.setY(Stage.FLOOR_TOP);
            if (myVector.getY() >= 0) {
                myVector.setY(0);
            }

            if (myVector.getY() > -5) {
                onGround = true;
                doubleJump = true;
            }
        } else if (myPos.getY() >= Stage.BALCONY_TOP && myPos.getY() <= Stage.BALCONY_TOP + 10
                && myPos.getX() > Stage.BALCONY_GAP && myPos.getX() < SmashGame.APP_WIDTH - Stage.BALCONY_GAP) {

            myPos.setY(Stage.BALCONY_TOP);
            if (myVector.getY() >= 0) {
                myVector.setY(0);
            }
            if (myVector.getY() > -5) {
                onGround = true;
                doubleJump = true;
            }
        } else {
            onGround = false;
        }
    }

    /**
     * Handles resistance physics and absolute maximums for velocities
     */
    public void resistance() {
        if (onGround) {
            if (myVector.getX() >= 2) {
                myVector.setX(myVector.getX() * 0.9);
            } else if (myVector.getX() <= -2) {
                myVector.setX(myVector.getX() * 0.9);
            } else {
                myVector.setX(0);
            }
        } else {
            if (myVector.getY() < 20) {
                myVector.setY(myVector.getY() + 1);
            }

            if (myVector.getX() >= 0.5) {
                myVector.setX(myVector.getX() * 0.99);
            } else if (myVector.getX() <= 0.5) {
                myVector.setX(myVector.getX() * 0.99);
            } else {
                myVector.setX(0);
            }
        }
    }

    /**
     * Checks Boundaries of the game and calls appropriate response to violations
     */
    public void checkBoundaries() {

        if (SmashGame.MOTION_WRAP) {
            if (myPos.getX() > SmashGame.APP_WIDTH + 10) {
                myPos.setX(0);
            }

            if (myPos.getX() < -10) {
                myPos.setX(SmashGame.APP_WIDTH);
            }
        } else {
            if (myPos.getX() > SmashGame.APP_WIDTH - 10) {
                myVector.setX(myVector.getX() - 1);
            }

            if (myPos.getX() < 10) {
                myVector.setX(myVector.getX() + 1);
            }

            if (myPos.getX() > SmashGame.APP_WIDTH + 120 && healthAmt > 0) {
                kill("right");
            }

            if (myPos.getX() < -120 && healthAmt > 0) {
                kill("left");
            }
        }

        if (myPos.getY() < 10) {
            myPos.setY(10);
        }

        if (myPos.getY() > SmashGame.APP_HEIGHT + 200 && healthAmt > 0) {
            kill("down");
        }
    }

    /**
     * Respawns Player
     */
    public void respawn() {
        myPos = new Coord(SmashGame.APP_WIDTH / 2, -50);
        if (SmashGame.NO_SPAWNCAMPING) {
            myPos.setX(Stage.FLOOR_GAP + (Math.random() * (SmashGame.APP_WIDTH - (2 * Stage.FLOOR_GAP))));
        }
        healthAmt = getStartHP();
        onGround = false;
        myVector.setY(60);
        dead = false;
    }

    /**
     * Kills the player and executes response based on cause of death
     * 
     * @param type The reason for death
     */
    public void kill(String type) {
        if (!dead) {
            if (type.equals("left")) {
                ringOutLeft.play(myPos);
                myPos.setY(SmashGame.APP_HEIGHT + 200);
            }
            if (type.equals("right")) {
                ringOutRight.play(myPos);
                myPos.setY(SmashGame.APP_HEIGHT + 200);
            }
            if (type.equals("down")) {
                ringOutDown.play(myPos);
                myPos.setY(SmashGame.APP_HEIGHT + 200);
            }

            healthAmt = 0;
            dead = true;

            new CooldownTracker(this, respawnCD, "respawn");
        }
    }

    /**
     * Executes moves on the player based off of key input Sometimes overidden
     * 
     * @param myList Key list generated from Keyput class
     */
    public void move(ArrayList<Key> myList) {
        resistance();
        checkGround();
        checkBoundaries();
        if (healthAmt <= 0) {
            kill("health");
        } else {

            for (Key currentKey : myList) {
                if (currentKey.keyState) {
                    if (currentKey.keyNumber == moveLeft && canWalk) {
                        walkLeft();
                    } else if (currentKey.keyNumber == moveRight && canWalk) {
                        walkRight();
                    }

                    if (currentKey.keyNumber == moveJump) {
                        jump();
                    }

                    if (currentKey.keyNumber == moveAttack && canAttack) {
                        attack();
                    }
                }
            }
        }
        myPos.setX(myPos.getX() + myVector.getX() / 10);
        myPos.setY(myPos.getY() + myVector.getY() / 10);
    }

    /**
     * Draws Player
     * 
     * @param g2 Graphics object passthrough
     */
    public void drawMe(Graphics2D g2) {
        prevDirec = facingDirec + animationDirec;
        g2.drawImage(myBanner, (int) (scale * (myPos.getX() - 48)), (int) (scale * (myPos.getY() - getHeight() - 80)),
                null);

        createDirectoryString();

        if (!(prevDirec.equals(facingDirec + animationDirec))) {
            myAnimationFrame = 0;
            timer = new Timer();
            timer.schedule(new Renderer(), 0);
        }

        if (myAnimationFrame < images.size() - 2) {
            myAnimationFrame = myAnimationFrame + 0.1;
        } else {
            myAnimationFrame = 0;
        }

        try {
            g2.drawImage(images.get((int) myAnimationFrame), (int) (scale * (myPos.getX() - (getWidth() / 2))),
                    (int) (scale * (myPos.getY() - getHeight())), null);
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Creates Image Directory String
     */
    public void createDirectoryString() {
        if (healthAmt > 0) {
            if (facingRight) {
                facingDirec = "/right";
            } else {
                facingDirec = "/left";
            }

            if (myVector.getX() > 0 || myVector.getX() < 0) {
                animationDirec = "/walking";
            } else {
                animationDirec = "/standing";
            }

            if (!onGround) {
                animationDirec = "/flying";
            }
        } else {
            facingDirec = "";
            animationDirec = "/dead";
        }

        if (attacking) {
            animationDirec = animationDirec + "shooting";
        }
    }

    // All Methods Below Should Be Copied Into New Player Classes

    public int getHeight() {
        return MY_HEIGHT;
    }

    public int getWidth() {
        return MY_WIDTH;
    }

    public int getMaxWalkV() {
        return MAX_WALKV;
    }

    public long getWalkCD() {
        return walkCD;
    }

    public long getAttackCD() {
        return attackCD;
    }

    public double getStartHP() {
        return STARTHEALTH;
    }

    public String getBaseDirectory() {
        return BASE_DIREC;
    }

    /**
     * Attack method (Just a shell)
     * 
     * @return true if successful attack
     */
    public boolean attack() {
        /* This method will always be overidden **/
        Player enemy = otherPlayers.findPlayer(enemyId);
        Coord enemyPos = enemy.getPos();
        Coord distToEnemy = myPos.checkDistance(enemy.getPos());
        // int damage = 0;
        if (facingRight && distToEnemy.getX() > 0 && distToEnemy.getX() < getWidth()) {
            if (myPos.getY() + getHeight() >= enemyPos.getY() && myPos.getY() <= enemyPos.getY() + enemy.getHeight()) {
                dmgDone += 25;
                enemy.healthAmt -= 25;
                canAttack = false;
                new CooldownTracker(this, attackCD, "canAttack");
                return true;
            }
        } else {
            if (myPos.getY() + getHeight() >= enemyPos.getY() && myPos.getY() <= enemyPos.getY() + enemy.getHeight()) {
                dmgDone += 25;
                enemy.healthAmt -= 25;
                canAttack = false;
                new CooldownTracker(this, attackCD, "canAttack");
                return true;
            }
        }
        return false;
    }

    /**
     * Block method (Just a shell)
     * 
     * @return true if successful block
     */
    public boolean block() {
        /* This method will always be overidden **/
        return true;
    }

    /**
     * Default walk left method
     */
    public void walkLeft() {
        if (myPos.getX() < SmashGame.APP_WIDTH) {
            if (myVector.getX() > -MAX_WALKV) {
                myVector.setX(myVector.getX() - 2);
                canWalk = false;
                facingRight = false;
                new CooldownTracker(this, walkCD, "canWalk");
            }
        }
    }

    /**
     * Default walk right method
     */
    public void walkRight() {
        if (myVector.getX() < MAX_WALKV) {
            myVector.setX(myVector.getX() + 2);
            canWalk = false;
            facingRight = true;
            new CooldownTracker(this, walkCD, "canWalk");
        }
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

    class Renderer extends TimerTask {
        public void run() {
            ArrayList<BufferedImage> temp = new ArrayList<BufferedImage>();
            int renderFrame = 1;
            boolean fileExists = true;
            while (fileExists) {
                try {
                    // Create File Directory String
                    String fileDirectory = getBaseDirectory() + facingDirec + animationDirec + "/";
                    if (renderFrame < 10) {
                        fileDirectory = fileDirectory + "0";
                    }
                    fileDirectory = fileDirectory + (int) renderFrame + ".png";

                    // Create Image
                    File image = new File(fileDirectory);
                    myImage = ImageResizer.resizeImage(ImageIO.read(image), scale);
                    temp.add(myImage);
                } catch (IOException ioe) {
                    fileExists = false;
                }
                renderFrame++;
            }
            images = temp;
            timer.cancel();
        }
    }
}