import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

	/* Static Foreground Layer **/
	private BufferedImage staticForeground;

	/** Boolean State of Main Menu */
	public boolean onMainMenu;

	/** Boolean State of Character Menu */
	public boolean onCharacterMenu;

	/** Directory for Main Menu */
	public static final String MAIN_MENU = "./graphics/menus/main/01.png";

	/** Directory for Character Menu */
	public static final String CHARACTER_MENU = "./graphics/menus/character/";

	private String lastButtonClicked;

	private boolean buttonClicked;

	private boolean isPlayer1;
	
	public double scale;

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
		this.addMouseMotionListener(new mouseHover());
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

	public void characterMenu() {
		try {
			File image = new File(CHARACTER_MENU + "StaticForeground.png");
			staticForeground = ImageIO.read(image);
		}
		catch(IOException e)
		{

		}

		onCharacterMenu = true;
	}

	public void characterMenuButtonHandler(MouseEvent e)
	{
		myGame.stage = 1;
		myGame.player1 = 1;
		myGame.player2 = 1;
		this.setVisible(false);
		myGame.screenSwitcher("Game");
		myGame.myGameScreen.run();
		onCharacterMenu = false;
	}

	public void mainMenuButtonHandler(MouseEvent e) 
	{
		int posY = e.getY();
		int posX = e.getX();
		double scale = myGame.getScale();
		if (posX > scale * 153 && posX < scale * 258) {
			if (posY > scale * 326 && posY < scale * 381) {
				lastButtonClicked = "Play";
				characterMenu();
				onMainMenu = false;
				return;
			}
		}
		if (posX > scale * 153 && posX < scale * 252) {
			if (posY > scale * 470 && posY < scale * 537) {
				lastButtonClicked = "Exit";
				myGame.myApp.setVisible(false);
			}
		}
		if (posX > scale * 153 && posX < scale * 445) {
			if (posY > scale * 406 && posY < scale * 456) {
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
			g2.drawImage(myGame.iR.resizeImage(background), 0, 0, null);
		}

		if (onCharacterMenu)
		{
			g2.setBackground(Color.WHITE);
			//g2.drawImage(myGame.iR.resizeImage(characterPreview), scale * 721, scale * 17, null);
			g2.drawImage(myGame.iR.resizeImage(staticForeground), 0, 0, null);
		}
	}

	private class mouseHandler implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			//System.out.println(e);
			if (onMainMenu) {
				mainMenuButtonHandler(e);
			}
			if (onCharacterMenu)
			{
				characterMenuButtonHandler(e);
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

	private class mouseHover implements MouseMotionListener
	{
		public void mouseMoved(MouseEvent e)
		{
			//TODO: Create Character Select Previews
		}

		public void mouseDragged(MouseEvent e)
		{

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
				scale = myGame.getScale();
				repaint();

				try {
					sleep(10);
				} catch (InterruptedException ie) {

				}
			}
		}
	}

}
