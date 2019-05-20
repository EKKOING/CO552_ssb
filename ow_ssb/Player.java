import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;
import javax.lang.model.util.ElementScanner6;

import java.util.ArrayList;

/**
 * Class from which all characters inherit properties from
 * 
 * @author Nicholas Lorentzen
 * @version 2019/03/25
 */
public class Player {

    /** Default Height and Width */
    public static final int MY_HEIGHT = 191;
    public static final int MY_WIDTH = 166;

    /** Coord object that holds the postion of the player */
    public Coord myPos;

    /** Velocity vector for physics based movement system */
    public Coord myVector;

    /* Id number to identify which player this represents **/
    public int myId;

    /* Id number of opponent **/
    public int enemyId;

    /* Int to hold the health stat **/
    public int healthAmt;
    /* Int to hold the damage done stat **/
    public int dmgDone;
    /* Int to hold the damage taken stat **/
    public int dmgTaken;

    /* Image Representation **/
    public BufferedImage myImage;

    /* Key Bindings **/
    public int moveLeft;
    public int moveRight;
    public int moveAttack;
    public int moveSpecial;
    public int moveJump;
    public int moveDown;

    /* Base Directory of Images **/
    public final String BASE_DIREC = "./graphics/characters/";

    /** Directory for direction facing */
    public String facingDirec;

    /** Directory for animation */
    public String animationDirec;

    /** Directory of previous animation */
    public String prevDirec;

    /** Animation Frame Number */
    public double myAnimationFrame;

    /* Direction currently facing **/
    public boolean facingRight;

    /** On the ground or not */
    public boolean onGround;

    /** Has the double jump available */
    public boolean doubleJump;

    /* Cooldowns for moves **/
    public boolean canWalk;
    public static final long walkCD = 10;
    public boolean canAttack;
    public static final long attackCD = 500;
    public static final long respawnCD = 3000;

    /* Max walk speed **/
    public static final int MAX_WALKV = 60;

    /* Passthrough of Players class to be able to interact with others **/
    public Players otherPlayers;

    /* Int to hold the default health to start with **/
    public static final int STARTHEALTH = 100;

    /**
     * @param playerNum Id number to assign the player (1 or 2)
     * @param xStart    X location to start player at
     * @param yStart    Y location to start player at
     * @param list      Players class passthrough
     */
    public Player(int playerNum, int xStart, int yStart, Players list) {
        myId = playerNum;
        enemyId = 2;

        if (myId == 2) {
            enemyId = 1;
        }
        otherPlayers = list;

        myPos = new Coord((double) xStart, (double) yStart);
        myVector = new Coord(0.0, 0.0);

        healthAmt = STARTHEALTH;
        canWalk = true;
        canAttack = true;
        onGround = true;
        dmgDone = 0;
        dmgTaken = 0;
        myAnimationFrame = 1;
        prevDirec = "";
        setKeybindings();
        System.out.print("Player of type ");
    }

    /**
     * Sets keybindings based off of ID num
     */
    private void setKeybindings() {
        if (myId == 1) {
            moveLeft = KeyEvent.VK_A;
            moveRight = KeyEvent.VK_D;
            moveJump = KeyEvent.VK_W;
            moveDown = KeyEvent.VK_S;
            moveAttack = KeyEvent.VK_E;
            moveSpecial = KeyEvent.VK_Q;
        }

        if (myId == 2) {
            moveLeft = KeyEvent.VK_J;
            moveRight = KeyEvent.VK_L;
            moveJump = KeyEvent.VK_I;
            moveDown = KeyEvent.VK_K;
            moveAttack = KeyEvent.VK_O;
            moveSpecial = KeyEvent.VK_U;
        }
    }

    /**
     * Gets current health
     * 
     * @return the current health amount
     */
    public int getHealth() {
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
     * Attack method (Just a shell)
     * 
     * @return true if successful attack
     */
    public boolean attack() {
        /* This method will always be overidden **/
        return true;
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
        if (myPos.getX() > MY_WIDTH / 2) {
            if (myPos.getX() < SmashGame.APP_WIDTH - (MY_WIDTH / 2)) {
                if (myVector.getX() > -MAX_WALKV) {
                    myVector.setX(myVector.getX() - 2);
                    canWalk = false;
                    facingRight = false;
                    new CooldownTracker(this, walkCD, "canWalk");
                }
            }
        }
    }

    /**
     * Default walk right method
     */
    public void walkRight() {
        if (myPos.getX() < otherPlayers.myGame.APP_WIDTH - (MY_WIDTH / 2)) {
            if (myVector.getX() < MAX_WALKV) {
                myVector.setX(myVector.getX() + 2);
                canWalk = false;
                facingRight = true;
                new CooldownTracker(this, walkCD, "canWalk");
            }
        }
    }

    public void jump() {
        if (onGround) {
            myVector.setY(-35);
            onGround = false;
            new CooldownTracker(this, (long) 250, "doubleJump");
        } else if (doubleJump) {
            myVector.setY(-20);
            doubleJump = false;
            new CooldownTracker(this, (long) 150, "doubleJump");
        }
    }

    public void checkGround() {
        if (myPos.getY() > 867) {
            myPos.setY(867);
            if (myVector.getY() > 0) {
                myVector.setY(0);
            }
            onGround = true;
            doubleJump = true;
            System.out.println("Hit Ground!");
        }
    }

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
            if (myVector.getY() < 10) {
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
     * Executes moves on the player based off of key input
     * 
     * @param myList Key list generated from Keyput class
     */
    public void move(ArrayList<Key> myList) {
        checkGround();
        resistance();
        if (healthAmt <= 0) {
            new CooldownTracker(this, respawnCD, "respawn");
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

            myPos.setX(myPos.getX() + myVector.getX() / 10);
            myPos.setY(myPos.getY() + myVector.getY() / 10);
        }
    }

    /**
     * Draws Player
     * 
     * @param g2 Graphics object passthrough
     */
    public void drawMe(Graphics2D g2) {
        myAnimationFrame = myAnimationFrame + 0.1;

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
        } 
        else 
        {
            facingDirec = "";
            animationDirec = "/dead";
        }

        if (!(prevDirec.equals(facingDirec + animationDirec))) {
            myAnimationFrame = 1;
        }
        prevDirec = facingDirec + animationDirec;
    }
}