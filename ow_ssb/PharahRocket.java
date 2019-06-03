import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Player of Type Pharah Rocket As A Projectile Attack
 * @author Nicholas Lorentzen
 * @version 20190529
 */
public class PharahRocket extends Player
{
	
	/** Width */
	private static final int MY_WIDTH = 20;
	/** Height */
	private static final int MY_HEIGHT = 10;
	
	/** Identifier to Prevent Hash Collisions */
	public static final int IDENTIFIER = 311;
	
	/** Scale Grid to Screen */
	private static final int MOVEMENT_SCALE = 10;
	
	/** Amount of Damage to be Randomized */
	private static final int RANDOMIZED_DAMAGE = 20;
	/** Base Damage Amount */
	private static final int BASE_DAMAGE = 20;
	
	/** Base Knockback */
	private static final int STANDARD_KNOCKBACK = 30;
	/** Amount of Knockback to be Randomized */
	private static final int RANDOMIZED_KNOCKBACK = 7;
	
	/** Speed of the rocket */
	public static final double SPEED = 80;
	
	/** Distance from Pharah Center Line to Start Rocket */
	public static final int PHARAH_ARM_OFFSET = 55;
	
	/** True if a target has been hit */
	private boolean hitSomething;
	
	/**
	 * Constructs a new Pharah Rocket with Properties
	 * 
	 * @param myId    Id of Rocket
	 * @param xStart  Starting X Location
	 * @param yStart  Starting Y Location
	 * @param list    Passthrough for Access to List
	 * @param facingR Directinal Boolean True if Right else False
	 * @param enemy   Target of Rocket
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
					enemy.knockBackX(STANDARD_KNOCKBACK + Math.random() * RANDOMIZED_KNOCKBACK);
				}
				else
				{
					enemy.knockBackX(-STANDARD_KNOCKBACK - Math.random() * RANDOMIZED_KNOCKBACK);
				}
				
				// Damage
				enemy.getDamaged(BASE_DAMAGE + (Math.random() * RANDOMIZED_DAMAGE - (RANDOMIZED_DAMAGE / 2)));
				
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
			getMyPos().setX(getMyPos().getX() + getMyVector().getX() / MOVEMENT_SCALE);
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
			Rectangle2D me = new Rectangle2D.Double(
				getOtherPlayers().getMyGame().getScale() * (getMyPos().getX() - MY_WIDTH),
				getOtherPlayers().getMyGame().getScale() * (getMyPos().getY() - (MY_HEIGHT / 2)),
				getOtherPlayers().getMyGame().getScale() * MY_WIDTH,
				getOtherPlayers().getMyGame().getScale() * MY_HEIGHT);
			g2.setColor(Color.YELLOW);
			g2.fill(me);
		}
	}
	
}
