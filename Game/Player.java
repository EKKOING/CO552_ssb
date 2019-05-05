import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;

/**
Class from which all characters inherit properties from
@author Nicholas Lorentzen
@version 2019/03/25
*/
public class Player
{
    /* Coord object that holds the postion of the player **/
    public Coord myPos;

    /*Id number to identify which player this represents **/
    public int myId;

    /* Int to hold the health stat **/
    private int healthAmt;
    /* Int to hold the damage done stat **/
    private int dmgDone;
    /* Int to hold the damage taken stat **/
    private int dmgTaken;

    /* Image Representation **/
    public BufferedImage myImage;

    /*Key Bindings - Will be updated to become variable later **/
    public int moveLeft;
    public int moveRight;
    public int moveAttack;

    /* Cooldowns for moves (Currently not implemented) **/
    public boolean canWalk;
    public static final long walkCD = 50;

    /* Int to hold the default health to start with **/
    public static final int STARTHEALTH = 100;
    
    public Player(int playerNum, int xStart, int yStart) 
    {
        myId = playerNum;
        myPos = new Coord(xStart,yStart);
        healthAmt = STARTHEALTH;
        dmgDone = 0;
        dmgTaken = 0;
        setKeybindings();
        System.out.println("Player Created");
    }

    private void setKeybindings()
    {
        if (myId == 1)
        {
            moveLeft = KeyEvent.VK_A;
            moveRight = KeyEvent.VK_D;
            moveAttack = KeyEvent.VK_E;
        }

        if (myId == 2)
        {
            moveLeft = KeyEvent.VK_J;
            moveRight = KeyEvent.VK_L;
            moveAttack = KeyEvent.VK_O;
        }
    }
    
    public int getHealth() 
    {
        return healthAmt;
    }

    public Coord getPos(){
        return myPos;
    }

    public int getDmgTaken(){
        return dmgTaken;
    }

    public int getDmgDone(){
        return dmgDone;
    }

    /*
    Attack method
    @return true if successful attack
    **/
    public boolean attack()
    {
        /* This method will always be overidden **/
        return true;
    }

    /*
    Block method
    @return true if successful block
    **/
    public boolean block()
    {
        /* This method will always be overidden **/
        return true;
    }

    public void walkLeft()
    {
        if(myPos.getX() > 10)
        {
            myPos.setX(myPos.getX() - 1);
        }
    }

    public void walkRight()
    {
        if(myPos.getX() < 1430)
        {
            myPos.setX(myPos.getX() + 1);
        }
    }

    public void move(Key currentKey)
    {
        if(!(currentKey.keyState))
        {
            if(currentKey.keyNumber == moveLeft && canWalk)
            {
                walkLeft();
            }

            if(currentKey.keyNumber == moveRight && canWalk)
            {
                walkRight();
            }
        }
    }

    /**
    Draws orb
    @param g2 Graphics object passthrough
    */
    public void drawMe(Graphics2D g2)
    {
        //Always Overidden
    }

    public static void main(String[] args) 
    {
        Player player1 = new Player(1, 1, 1);

        /* Test all methods **/
        System.out.println(player1.getHealth() + ": Current Health");
        System.out.println(player1.getDmgDone() + ": Damage Done");
        System.out.println(player1.getDmgTaken() + ": Damage Taken");
        System.out.println(player1.getPos());
        System.out.println(player1.attack() + ": Attack Successful");
        System.out.println(player1.block() + ": Block Successful");
    }
}