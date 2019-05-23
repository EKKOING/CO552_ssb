import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
//import java.time.chrono.ThaiBuddhistDate; This keeps adding itself idk y

import javax.imageio.*;
import javax.lang.model.util.ElementScanner6;

/**
 * @author student
 *
 */
public class TitleScreen extends JPanel {

	/** Game Passthrough */
	public SmashGame myGame;

	/* Background Layer **/
	private BufferedImage background;

	/** Boolean State of Main Menu */
	public boolean onMainMenu;

	/** Directory for Main Menu */
	public static final String MAIN_MENU = "./graphics/menus/main/01.png";

	private String lastButtonClicked;

	private boolean buttonClicked;

	/**
	 * name of file with character list (NOTE: Any character not there will not be
	 * selectable)
	 */
	public static final String CHARACTER_LIST = "./data/characters.txt";

	/** Graphics Object */
	private Graphics2D g2;

	/**
	 * 
	 */
	public TitleScreen(SmashGame game) {
		super();
		myGame = game;
		this.addMouseListener(new mouseHandler());
		this.setVisible(true);
		this.setFocusable(true);
		this.requestFocusInWindow();

		FieldUpdater updater = new FieldUpdater();
		updater.start();
	}

	public void run() {
		this.requestFocusInWindow(true);
		mainMenu();
	}

	public void mainMenu() {
		lastButtonClicked = "";
		buttonClicked = false;
		try {
			File image = new File(MAIN_MENU);
			background = ImageIO.read(image);
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
		onMainMenu = true;
	}

	public void mainMenuButtonHandler(MouseEvent e) 
	{
		int posY = e.getY();
		int posX = e.getX();
		if (posX > 153 && posX < 258) {
			if (posY > 326 && posY < 381) {
				lastButtonClicked = "Play";
				myGame.stage = 1;
				myGame.player1 = 1;
				myGame.player2 = 1;
				this.setVisible(false);
				myGame.screenSwitcher("Game");
				myGame.myGameScreen.run();
				return;
			}
		}
		if (posX > 153 && posX < 252) {
			if (posY > 470 && posY < 537) {
				lastButtonClicked = "Exit";
				myGame.myApp.setVisible(false);
			}
		}
		if (posX > 153 && posX < 445) {
			if (posY > 406 && posY < 456) {
				lastButtonClicked = "Instructions";
			}
		}
		System.out.println(lastButtonClicked);
		lastButtonClicked = "";
	}

	/**
	 * Paints screen
	 * 
	 * @param g Graphics object
	 */
	public void paintComponent(Graphics g) {
		g2 = (Graphics2D) g;

		if (onMainMenu) {
			g2.drawImage(background, 0, 0, null);
		}

	}

	private class mouseHandler implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			//System.out.println(e);
			if (onMainMenu) {
				mainMenuButtonHandler(e);
			}

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}
	}

	/**
	 * Thread to handle repainting screen
	 */
	private class FieldUpdater extends Thread {
		/**
		 * Repaints screen on interval while not paused
		 */
		public void run() {
			while (true) {

				repaint();

				try {
					sleep(10);
				} catch (InterruptedException ie) {

				}
			}
		}
	}

}
