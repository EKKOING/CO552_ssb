import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;
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

    /* Coord object that holds the postion of the player **/
    public Coord myPos;

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

    /* Direction currently facing **/
    public boolean facingRight;

    /* Cooldowns for moves **/
    public boolean canWalk;
    public static final long walkCD = 10;
    public boolean canAttack;
    public static final long attackCD = 500;
    public static final long respawnCD = 3000;

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

        myPos = new Coord(xStart, yStart);

        healthAmt = STARTHEALTH;
        canWalk = true;
        canAttack = true;
        dmgDone = 0;
        dmgTaken = 0;
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
            moveAttack = KeyEvent.VK_E;
            moveSpecial = KeyEvent.VK_Q;
        }

        if (myId == 2) {
            moveLeft = KeyEvent.VK_J;
            moveRight = KeyEvent.VK_L;
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
        if (myPos.getX() > MY_WIDTH) {
            myPos.setX(myPos.getX() - 1);
            canWalk = false;
            facingRight = false;
            new CooldownTracker(this, walkCD, "canWalk");
        }
    }

    /**
     * Default walk right method
     */
    public void walkRight() {
        if (myPos.getX() < otherPlayers.myGame.APP_WIDTH - MY_WIDTH) {
            myPos.setX(myPos.getX() + 1);
            canWalk = false;
            facingRight = true;
            new CooldownTracker(this, walkCD, "canWalk");
        }
    }

    /**
     * Executes moves on the player based off of key input
     * 
     * @param myList Key list generated from Keyput class
     */
    public void move(ArrayList<Key> myList) {
        if (healthAmt <= 0) {
            new CooldownTracker(this, respawnCD, "respawn");
        } 
        else {

            for (Key currentKey : myList) {
                if (currentKey.keyState) {
                    if (currentKey.keyNumber == moveLeft && canWalk) {
                        walkLeft();
                    } else if (currentKey.keyNumber == moveRight && canWalk) {
                        walkRight();
                    }

                    if (currentKey.keyNumber == moveAttack && canAttack) {
                        attack();
                    }
                }
            }
        }
    }

    /**
     * Draws Player
     * 
     * @param g2 Graphics object passthrough
     */
    public void drawMe(Graphics2D g2) {
        // Always Overidden
    }

    public static void main(String[] args) {
        // Player player1 = new Player(1, 1, 1);

        /* Test all methods **/
        /*
         * System.out.println(player1.getHealth() + ": Current Health");
         * System.out.println(player1.getDmgDone() + ": Damage Done");
         * System.out.println(player1.getDmgTaken() + ": Damage Taken");
         * System.out.println(player1.getPos()); System.out.println(player1.attack() +
         * ": Attack Successful"); System.out.println(player1.block() +
         * ": Block Successful");
         **/
    }
}