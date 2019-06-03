import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import javax.imageio.*;
import java.util.ArrayList;

/**
 * @author Nicholas Lorentzen
 * @version 20190529
 */
public class PharahRocket extends Player
{
	
	/** Speed of the rocket */
	public static final double SPEED = 80;
	
	/** Distance from Pharah Center Line to Start Rocket */
	public static final int PHARAH_ARM_OFFSET = 55;
	
	/** True if a target has been hit */
	private boolean hitSomething;
	
	/**
	 * 
	 * @param myId
	 * @param xStart
	 * @param yStart
	 * @param list
	 * @param facingR
	 * @param enemy
	 */
	public PharahRocket(int myId, int xStart, int yStart, Players list, boolean facingR, int enemy)
	{
		super(myId, xStart, yStart, list);
		setEnemyId(enemy);
		setFacingRight(facingR);
		if (isFacingRight())
		{
			getMyVector().setX(SPEED);
		}
		else
		{
			getMyVector().setX(-SPEED);
		}
		hitSomething = false;
		// System.out.print("Pharah Rocket Created");
	}
	
	/**
	 * Attack method for Rocket
	 * 
	 * @return true if successful attack
	 */
	@Override
	public boolean attack()
	{
		Player enemy = getOtherPlayers().findPlayer(getEnemyId());
		Coord enemyPos = enemy.getPos();
		Coord distToEnemy = getMyPos().checkDistance(enemyPos);
		if (distToEnemy.getX() < (enemy.getWidth() / 2) && distToEnemy.getX() > (-enemy.getWidth() / 2))
		{
			if (distToEnemy.getY() < enemy.getHeight() && distToEnemy.getY() > 0 && getMyPos().getY() < enemyPos.getY())
			{
				
				// Knockback
				if (isFacingRight())
				{
					enemy.knockBackX(30 + Math.random() * 7);
				}
				else
				{
					enemy.knockBackX(-30 - Math.random() * 7);
				}
				
				// Damage
				enemy.getDamaged(20 + (Math.random() * 20 - 10));
				
				// Stop Duplication of Attacks
				setCanAttack(false);
				hitSomething = true;
				explode();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void checkBoundaries()
	{
		if (getMyPos().getX() < 0 || getMyPos().getX() > SmashGame.APP_WIDTH)
		{ kill("outOfBoundaries"); }
	}
	
	@Override
	public void kill(String type)
	{
		getOtherPlayers().removeObject(this);
	}
	
	public void explode()
	{
		kill("hit");
	}
	
	/**
	 * Executes moves on the rocket
	 * 
	 * @param myList Key list generated from Keyput class (Not Used)
	 */
	@Override
	public void move(ArrayList<Key> myList)
	{
		if (!(hitSomething))
		{
			checkBoundaries();
			if (isCanAttack())
			{ attack(); }
			getMyPos().setX(getMyPos().getX() + getMyVector().getX() / 10);
		}
	}
	
	/**
	 * Draws Player
	 * 
	 * @param g2 Graphics object passthrough
	 */
	@Override
	public void drawMe(Graphics2D g2)
	{
		if (!(hitSomething))
		{
			// TODO: Place Images
			Rectangle2D me = new Rectangle2D.Double(getOtherPlayers().getMyGame().getScale() * (getMyPos().getX() - 10),
				getOtherPlayers().getMyGame().getScale() * (getMyPos().getY() - 2.5), getOtherPlayers().getMyGame().getScale() * 20,
				getOtherPlayers().getMyGame().getScale() * 10);
			g2.setColor(Color.YELLOW);
			g2.fill(me);
		}
	}
	
}
