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
 * @author Nicholas Lorentzen
 * @version 20190602
 */
public class TitleScreen extends JPanel
{
	
	/** Game Passthrough */
	private SmashGame myGame;
	
	/** Background Layer */
	private BufferedImage background;
	
	/** Static Foreground Layer */
	private BufferedImage staticForeground;
	
	/** Dynamic Character Preview */
	private BufferedImage characterPreview;
	
	/** Character Preview Placeholder */
	private BufferedImage noCharacterPreviewed;
	
	/** Character Preview Placeholder */
	private BufferedImage currentPicker;
	
	/** Boolean State of Main Menu */
	private boolean onMainMenu;
	
	/** Boolean State of Character Menu */
	private boolean onCharacterMenu;
	
	/** Boolean State of Instruction Menu */
	private boolean onInstructionMenu;
	
	/** Directory for Main Menu */
	public static final String MAIN_MENU = "./graphics/menus/main/01.png";
	
	/** Directory for Character Menu */
	public static final String CHARACTER_MENU = "./graphics/menus/character/";
	
	/** Directory for Instruction Menu */
	public static final String INSTRUCTION_MENU = "./graphics/menus/instruction/01.png";
	
	private String lastButtonClicked;
	
	private boolean buttonClicked;
	
	private boolean isPlayer1;
	
	private boolean isPlayer2;
	
	private double scale;
	
	private Timer myTimer;
	
	private boolean canClick;
	
	private ArrayList<CharacterMenuIcon> myIcons;
	
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
	public TitleScreen(SmashGame game)
	{
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
	
	public void run()
	{
		scale = myGame.getScale();
		this.requestFocusInWindow(true);
		mainMenu();
		canClick = true;
	}
	
	public void getNumCharacters()
	{
		numCharacters = 0;
		for (String currentCharacter : charactersArray)
		{
			if (!(currentCharacter.equals("random") || currentCharacter.equals("empty")))
			{ numCharacters++; }
		}
	}
	
	public void mainMenu()
	{
		lastButtonClicked = "";
		buttonClicked = false;
		try
		{
			File image = new File(MAIN_MENU);
			background = ImageResizer.resizeImage(ImageIO.read(image), scale);
		}
		catch (IOException ioe)
		{
			System.err.println(ioe);
		}
		onMainMenu = true;
	}
	
	public void characterMenu()
	{
		try
		{
			File image = new File(CHARACTER_MENU + "StaticForeground.png");
			staticForeground = ImageResizer.resizeImage(ImageIO.read(image), scale);
		}
		catch (IOException e)
		{
			
		}
		
		try
		{
			File image = new File(CHARACTER_MENU + "noCharacterPreviewed.png");
			noCharacterPreviewed = ImageResizer.resizeImage(ImageIO.read(image), scale);
		}
		catch (IOException e)
		{
			
		}
		
		if (isPlayer1)
		{
			try
			{
				File image = new File(CHARACTER_MENU + "1.png");
				currentPicker = ImageResizer.resizeImage(ImageIO.read(image), scale);
			}
			catch (IOException e)
			{
				
			}
		}
		else
		{
			try
			{
				File image = new File(CHARACTER_MENU + "2.png");
				currentPicker = ImageResizer.resizeImage(ImageIO.read(image), scale);
			}
			catch (IOException e)
			{
				
			}
		}
		
		// Auto Layout Icons From Set Array
		double positionX = ICON_X;
		double positionY = ICON_Y;
		myIcons = new ArrayList<CharacterMenuIcon>();
		
		for (String character : charactersArray)
		{
			myIcons.add(new CharacterMenuIcon(character, scale, positionX, positionY));
			positionX = positionX + CharacterMenuIcon.WIDTH + CharacterMenuIcon.XGAP;
			if (myIcons.size() == 4 || myIcons.size() == 9)
			{
				positionX = ICON_X;
				positionY = positionY + CharacterMenuIcon.HEIGHT + CharacterMenuIcon.YGAP;
			}
		}
		onCharacterMenu = true;
	}
	
	public void instructionMenu()
	{
		lastButtonClicked = "";
		buttonClicked = false;
		try
		{
			File image = new File(INSTRUCTION_MENU);
			background = ImageResizer.resizeImage(ImageIO.read(image), scale);
		}
		catch (IOException ioe)
		{
			System.err.println(ioe);
		}
		onInstructionMenu = true;
	}
	
	public void characterMenuButtonHandler(MouseEvent e)
	{
		if (canClick)
		{
			for (CharacterMenuIcon currentIcon : myIcons)
			{
				if (currentIcon.contains(e))
				{
					int currentCharacter = 0;
					switch (currentIcon.getMyName())
					{
						case "pharah":
							currentCharacter = 1;
							break;
						case "random":
							currentCharacter = (int) (Math.random() * numCharacters) + 1;
							break;
					}
					if (isPlayer2 && currentCharacter != 0)
					{
						myGame.setPlayer2(currentCharacter);
						isPlayer2 = false;
						this.setVisible(false);
						myGame.screenSwitcher("Game");
						myGame.getMyGameScreen().run();
						onCharacterMenu = false;
					}
					
					if (isPlayer1 && currentCharacter != 0)
					{
						myGame.setPlayer1(currentCharacter);
						isPlayer1 = false;
						isPlayer2 = true;
						canClick = false;
						myTimer = new Timer();
						myTimer.schedule(new ClickDelay(), 2000);
						characterMenu();
					}
				}
			}
		}
	}
	
	public void mainMenuButtonHandler(MouseEvent e)
	{
		int posY = e.getY();
		int posX = e.getX();
		double scale = myGame.getScale();
		if (posX > scale * 153 && posX < scale * 258)
		{
			if (posY > scale * 326 && posY < scale * 381)
			{
				lastButtonClicked = "Play";
				canClick = false;
				myTimer = new Timer();
				myTimer.schedule(new ClickDelay(), 1000);
				isPlayer1 = true;
				characterMenu();
				onMainMenu = false;
				return;
			}
		}
		if (posX > scale * 153 && posX < scale * 252)
		{
			if (posY > scale * 470 && posY < scale * 537)
			{
				lastButtonClicked = "Exit";
				System.exit(0);
			}
		}
		if (posX > scale * 153 && posX < scale * 445)
		{
			if (posY > scale * 406 && posY < scale * 456)
			{
				lastButtonClicked = "Instructions";
				canClick = false;
				myTimer = new Timer();
				myTimer.schedule(new ClickDelay(), 500);
				instructionMenu();
				onMainMenu = false;
				return;
			}
		}
		System.out.println(lastButtonClicked);
		lastButtonClicked = "";
	}
	
	/**
	 * Executes instruction on Instructions Menu based on clicks
	 * 
	 * @param e Mouse Event
	 */
	public void instructionMenuButtonHandler(MouseEvent e)
	{
		if (canClick)
		{
			lastButtonClicked = "Return To Main Menu";
			canClick = false;
			myTimer = new Timer();
			myTimer.schedule(new ClickDelay(), 500);
			mainMenu();
			onInstructionMenu = false;
		}
	}
	
	/**
	 * Paints screen
	 * 
	 * @param g Graphics object
	 */
	public void paintComponent(Graphics g)
	{
		g2 = (Graphics2D) g;
		
		if (onMainMenu || onInstructionMenu)
		{
			g2.clearRect(0, 0, 1920, 1080);
			g2.setBackground(Color.BLACK);
			g2.drawImage(background, 0, 0, null);
		}
		
		if (onCharacterMenu)
		{
			g2.clearRect(0, 0, 1920, 1080);
			g2.setBackground(Color.WHITE);
			g2.drawImage(characterPreview, (int) (scale * 721), (int) (scale * 17), null);
			g2.drawImage(staticForeground, 0, 0, null);
			g2.drawImage(currentPicker, (int) (scale * 60), (int) (scale * 109.94), null);
			for (CharacterMenuIcon currentIcon : myIcons)
			{ currentIcon.drawMe(g2); }
		}
	}
	
	
	private class mouseHandler implements MouseListener
	{
		
		@Override
		public void mouseClicked(MouseEvent e)
		{
			// System.out.println(e);
			
			if (onMainMenu)
			{ mainMenuButtonHandler(e); }
			
			if (onCharacterMenu)
			{ characterMenuButtonHandler(e); }
			
			if (onInstructionMenu)
			{ instructionMenuButtonHandler(e); }
		}
		
		@Override
		public void mouseEntered(MouseEvent e)
		{
			
		}
		
		@Override
		public void mouseExited(MouseEvent e)
		{
			
		}
		
		@Override
		public void mousePressed(MouseEvent e)
		{
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e)
		{
			
		}
	}
	
	
	private class mouseHover implements MouseMotionListener
	{
		
		public void mouseMoved(MouseEvent e)
		{
			if (onCharacterMenu)
			{
				characterPreview = noCharacterPreviewed;
				for (CharacterMenuIcon currentIcon : myIcons)
				{
					if (currentIcon.contains(e))
					{ characterPreview = currentIcon.getPreview(); }
				}
			}
		}
		
		public void mouseDragged(MouseEvent e)
		{
			
		}
	}
	
	
	/**
	 * Thread to handle repainting screen
	 */
	private class FieldUpdater extends Thread
	{
		
		/**
		 * Repaints screen on interval while not paused
		 */
		public void run()
		{
			while (true)
			{
				repaint();
				
				try
				{
					sleep(20);
				}
				catch (InterruptedException ie)
				{
					
				}
			}
		}
	}
	
	
	class ClickDelay extends TimerTask
	{
		
		public void run()
		{
			canClick = true;
			myTimer.cancel();
		}
	}
	
}
