import java.util.ArrayList;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.util.Timer;
import javax.imageio.*;


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

	/* Dynamic Character Preview **/
	private BufferedImage characterPreview;

	/** Character Preview Placeholder */
	private BufferedImage noCharacterPreviewed;

	/** Character Preview Placeholder */
	private BufferedImage currentPicker;

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

	private boolean isPlayer2;

	public double scale;

	public Timer myTimer;

	public boolean canClick;

	public ArrayList<CharacterMenuIcon> myIcons;

	public static final double ICON_X = 56.00;

	public static final double ICON_Y = 220.0;

	/**
	 * Array with character list (NOTE: Any character not there will not be
	 * selectable)
	 */
	public static final String charactersArray[] = { "random", "pharah", "empty", "empty", "empty", "empty", "empty",
			"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty" };

	public int numCharacters;

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
		scale = myGame.scale;
		this.requestFocusInWindow(true);
		mainMenu();
		canClick = true;
	}

	public void getNumCharacters() {
		numCharacters = 0;
		for (String currentCharacter : charactersArray) {
			if (!(currentCharacter.equals("random") || currentCharacter.equals("empty"))) {
				numCharacters++;
			}
		}
	}

	public void mainMenu() {
		lastButtonClicked = "";
		buttonClicked = false;
		try {
			File image = new File(MAIN_MENU);
			background = myGame.iR.resizeImage(ImageIO.read(image));
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
		onMainMenu = true;
	}

	public void characterMenu() {
		try {
			File image = new File(CHARACTER_MENU + "StaticForeground.png");
			staticForeground = myGame.iR.resizeImage(ImageIO.read(image));
		} catch (IOException e) {

		}

		try {
			File image = new File(CHARACTER_MENU + "noCharacterPreviewed.png");
			noCharacterPreviewed = myGame.iR.resizeImage(ImageIO.read(image));
		} catch (IOException e) {

		}

		if (isPlayer1) {
			try {
				File image = new File(CHARACTER_MENU + "1.png");
				currentPicker = myGame.iR.resizeImage(ImageIO.read(image));
			} catch (IOException e) {

			}
		} else {
			try {
				File image = new File(CHARACTER_MENU + "2.png");
				currentPicker = myGame.iR.resizeImage(ImageIO.read(image));
			} catch (IOException e) {

			}
		}

		// Auto Layout Icons From Set Array
		double positionX = ICON_X;
		double positionY = ICON_Y;
		myIcons = new ArrayList<CharacterMenuIcon>();

		for (String character : charactersArray) {
			myIcons.add(new CharacterMenuIcon(character, scale, positionX, positionY));
			positionX = positionX + CharacterMenuIcon.WIDTH + 20;
			if (myIcons.size() == 4 || myIcons.size() == 9) {
				positionX = ICON_X;
				positionY = positionY + CharacterMenuIcon.HEIGHT + 40;
			}
		}
		onCharacterMenu = true;
	}

	public void characterMenuButtonHandler(MouseEvent e) {
		if (canClick) {
			for (CharacterMenuIcon currentIcon : myIcons) {
				if (currentIcon.contains(e)) {
					int currentCharacter = 0;
					switch (currentIcon.myName) {
					case "pharah":
						currentCharacter = 1;
						break;
					case "random":
						currentCharacter = (int) (Math.random() * numCharacters) + 1;
						break;
					}
					if (isPlayer2 && currentCharacter != 0) {
						myGame.player2 = currentCharacter;
						isPlayer2 = false;
						this.setVisible(false);
						myGame.screenSwitcher("Game");
						myGame.myGameScreen.run();
						onCharacterMenu = false;
					}

					if (isPlayer1 && currentCharacter != 0) {
						myGame.player1 = currentCharacter;
						isPlayer1 = false;
						isPlayer2 = true;
						canClick = false;
						myTimer = new Timer();
						myTimer.schedule(new RemindTask(), 2000);
						characterMenu();
					}
				}
			}
		}
	}

	public void mainMenuButtonHandler(MouseEvent e) {
		int posY = e.getY();
		int posX = e.getX();
		double scale = myGame.scale;
		if (posX > scale * 153 && posX < scale * 258) {
			if (posY > scale * 326 && posY < scale * 381) {
				lastButtonClicked = "Play";
				canClick = false;
				myTimer = new Timer();
				myTimer.schedule(new RemindTask(), 1000);
				isPlayer1 = true;
				characterMenu();
				onMainMenu = false;
				return;
			}
		}
		if (posX > scale * 153 && posX < scale * 252) {
			if (posY > scale * 470 && posY < scale * 537) {
				lastButtonClicked = "Exit";
				System.exit(0);
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
			g2.clearRect(0, 0, 1920, 1080);
			g2.setBackground(Color.BLACK);
			g2.drawImage(background, 0, 0, null);
		}

		if (onCharacterMenu) {
			g2.clearRect(0, 0, 1920, 1080);
			g2.setBackground(Color.WHITE);
			g2.drawImage(characterPreview, (int) (scale * 721), (int) (scale * 17), null);
			g2.drawImage(staticForeground, 0, 0, null);
			g2.drawImage(currentPicker, (int) (scale * 60), (int) (scale * 109.94), null);
			for (CharacterMenuIcon currentIcon : myIcons) {
				currentIcon.drawMe(g2);
			}
		}
	}

	private class mouseHandler implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			// System.out.println(e);

			if (onMainMenu) {
				mainMenuButtonHandler(e);
			}
			if (onCharacterMenu) {
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

	private class mouseHover implements MouseMotionListener {
		public void mouseMoved(MouseEvent e) {
			if (onCharacterMenu) {
				characterPreview = noCharacterPreviewed;
				for (CharacterMenuIcon currentIcon : myIcons) {
					if (currentIcon.contains(e)) {
						characterPreview = currentIcon.getPreview();
					}
				}
			}
		}

		public void mouseDragged(MouseEvent e) {

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
					sleep(20);
				} catch (InterruptedException ie) {

				}
			}
		}
	}

	class RemindTask extends TimerTask {
		public void run() {
			canClick = true;
			myTimer.cancel();
		}
	}

}
