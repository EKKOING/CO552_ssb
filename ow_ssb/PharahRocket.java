import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import javax.imageio.*;
import java.util.ArrayList;

/**
 * @author student
 *
 */
public class PharahRocket extends Player {

	public static final double SPEED = 80;
	private boolean hitSomething;

	/**
	 * 
	 */
	public PharahRocket(int myId, int xStart, int yStart, Players list, boolean facingR, int enemy) {
		super(myId, xStart, yStart, list);
		enemyId = enemy;
		facingRight = facingR; //This will be used later don't worry
		if(facingRight)
		{
			myVector.setX(SPEED);
		}
		else
		{
			myVector.setX(-SPEED);
		}
		hitSomething = false;
	}

    /**
     * Attack method (Just a shell)
     * 
     * @return true if successful attack
     */
    public boolean attack() {
        Player enemy = otherPlayers.findPlayer(enemyId);
        Coord enemyPos = enemy.getPos();
		Coord distToEnemy = myPos.checkDistance(enemyPos);
		if (distToEnemy.getX() < (enemy.MY_WIDTH / 2) && distToEnemy.getX() > (-enemy.MY_WIDTH / 2))
		{
			if (distToEnemy.getY() < enemy.MY_HEIGHT && distToEnemy.getY() > 0 && myPos.getY() < enemyPos.getY())
			{
				enemy.healthAmt -=  10;
				canAttack = false;
				hitSomething = true;
				explode();
				return true;
			}
		}
		return false;
    }

    public void checkGround() {
		if(myPos.getX() < 0 || myPos.getX() > SmashGame.APP_WIDTH)
		{
			kill();
		}
    }

    public void respawn()
    {
		//None
    }

    public void kill()
    {
		otherPlayers.myPlayers.remove(this);
	}

	public void explode()
	{
		kill();
	}

    /**
     * Executes moves on the player based off of key input
     * 
     * @param myList Key list generated from Keyput class
     */
    public void move(ArrayList<Key> myList) {
		if(!(hitSomething))
		{
			checkGround();
			if(canAttack)
			{
				attack();
			}
			myPos.setX(myPos.getX() + myVector.getX() / 10);
		}
    }

    /**
     * Draws Player
     * 
     * @param g2 Graphics object passthrough
     */
    public void drawMe(Graphics2D g2) {
		if(!(hitSomething))
		{
			//TODO: Place Images
			Rectangle2D me = new Rectangle2D.Double(myPos.getX() - 10, myPos.getY() - 2.5, 10, 5);
			g2.setColor(Color.YELLOW);
			g2.fill(me);
		}
    }

}
