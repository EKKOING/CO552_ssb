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
	public PharahRocket(int myId, int xStart, int yStart, Players list, boolean facingR, int enemy) {
		super(myId, xStart, yStart, list);
		enemyId = enemy;
		facingRight = facingR; // This will be used later don't worry
		if (facingRight) {
			myVector.setX(SPEED);
		} else {
			myVector.setX(-SPEED);
		}
		hitSomething = false;
		// System.out.print("Pharah Rocket Created");
	}

	/**
	 * Attack method (Just a shell)
	 * 
	 * @return true if successful attack
	 */
	@Override
	public boolean attack() {
		Player enemy = otherPlayers.findPlayer(enemyId);
		Coord enemyPos = enemy.getPos();
		Coord distToEnemy = myPos.checkDistance(enemyPos);
		if (distToEnemy.getX() < (enemy.getWidth() / 2) && distToEnemy.getX() > (-enemy.getWidth() / 2)) {
			if (distToEnemy.getY() < enemy.getHeight() && distToEnemy.getY() > 0 && myPos.getY() < enemyPos.getY()) {

				// Knockback
				if (facingRight) {
					enemy.knockBackX(30 + Math.random() * 7);
				} else {
					enemy.knockBackX(-30 - Math.random() * 7);
				}

				// Damage
				enemy.getDamaged(20 + (Math.random() * 20 - 10));

				// Stop Duplication
				canAttack = false;
				hitSomething = true;
				explode();
				return true;
			}
		}
		return false;
	}

	@Override
	public void checkBoundaries() {
		if (myPos.getX() < 0 || myPos.getX() > SmashGame.APP_WIDTH) {
			kill("outOfBoundaries");
		}
	}

	@Override
	public void kill(String type) {
		otherPlayers.removeObject(this);
	}

	public void explode() {
		kill("hit");
	}

	/**
	 * Executes moves on the rocket
	 * 
	 * @param myList Key list generated from Keyput class (Not Used)
	 */
	@Override
	public void move(ArrayList<Key> myList) {
		if (!(hitSomething)) {
			checkBoundaries();
			if (canAttack) {
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
	@Override
	public void drawMe(Graphics2D g2) {
		if (!(hitSomething)) {
			// TODO: Place Images
			Rectangle2D me = new Rectangle2D.Double(otherPlayers.myGame.scale * (myPos.getX() - 10),
					otherPlayers.myGame.scale * (myPos.getY() - 2.5), otherPlayers.myGame.scale * 20,
					otherPlayers.myGame.scale * 10);
			g2.setColor(Color.YELLOW);
			g2.fill(me);
		}
	}

}
