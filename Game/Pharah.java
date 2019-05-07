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
@version 2019/03/25
*/
public class Pharah extends Player
{
    public BufferedImage myImage;

    public static final int MY_HEIGHT = 191;

    public static final int MY_WIDTH = 166;
    
    /*
    Constructor for a Pharah player
    **/
    public Pharah(int myId, int xStart, int yStart, Players list)
    {
        super(myId, xStart, yStart, list);
        try
        {
            //InputStream is = getClass().getResourceAsStream("graphics/characters/pharah/pharah.png");
            File image = new File("graphics/characters/pharah/pharah.png");
            myImage = ImageIO.read(image);
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
        System.out.println("Pharah Created \n");
    }
    
    /*
    Basic Melee attack
    @return true if successful attack
    **/
    public boolean attack()
    {
        Player enemy = otherPlayers.findPlayer(enemyId);
        Coord enemyPos = enemy.getPos();
        Coord distToEnemy = myPos.checkDistance(enemy.getPos());
        int damage = 0;
        if(facingRight && distToEnemy.getX() > 0 && distToEnemy.getX() < MY_WIDTH)
        {
            if(myPos.getY() + MY_HEIGHT >= enemyPos.getY() && myPos.getY() <= enemyPos.getY() + enemy.MY_HEIGHT)
            {
                dmgDone += 25;
                enemy.healthAmt -= 25;
                return true;
            }
        }
        else
        {
            if(myPos.getY() + MY_HEIGHT >= enemyPos.getY() && myPos.getY() <= enemyPos.getY() + enemy.MY_HEIGHT)
            {
                dmgDone += 25;
                enemy.healthAmt -= 25;
                return true;
            }
        }
    }

    /*
    Block method
    @return true if successful block
    **/
    public boolean block()
    {
        return true;
    }

    /**
    Draws orb
    @param g2 Graphics object passthrough
    */
    public void drawMe(Graphics2D g2)
    {
        //Ellipse2D.Double spot = new Ellipse2D.Double(topX, topY, SIZE, SIZE);
        //g2.setColor(myColor);
        //g2.fill(spot);

        g2.drawImage(myImage, (int) myPos.getX() - 83, (int) myPos.getY() + 191, null);
    }
    
    public static void main(String[] args) 
    {
        ///Pharah player1 = new Pharah(1,1,1);

        /* Tests all inherited methods**/
        /*
        System.out.println(player1.getHealth() + ": Current Health");
        System.out.println(player1.getDmgDone() + ": Damage Done");
        System.out.println(player1.getDmgTaken() + ": Damage Taken");
        System.out.println(player1.getPos());
        **/

        /* Tests all methods in class**/
        /*
        System.out.println(player1.attack() + ": Attack Successful");
        System.out.println(player1.block() + ": Block Successful");
        **/
    }
}