import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Win Screen To Manage Post-Game Events
 * @author Nicholas Lorentzen
 * @version 20190602
 */
public class WinScreen extends JPanel
{
	
	/** Game Over Text Y Position */
	private static final int GAMEOVER_TEXT_Y = 108;
	/** Game Over Text X Position */
	private static final int GAMEOVER_TEXT_X = 619;
	/** Continue Text Y Position */
	private static final int CONTINUE_TEXT_Y = 859;
	/** Continue Text X Position */
	private static final int CONTINUE_TEXT_X = 1158;
	/** Spacing For Loading Bars */
	private static final int LOADING_BAR_SPACING = 30;
	/** Winner Text Y Position */
	private static final int FOREGROUND_Y = 108;
	/** Player Text Y Position */
	private static final int PLAYERNUM_Y = 218;
	/** X Location for Left Aligned Text */
	private static final int TEXT_ALIGNED_LEFT_X = 192;
	
	/** Animation Path */
	private final String BASE_DIREC = "./graphics/endgame/";
	
	/** Iteration Amount for Animatioon */
	private static final double FRAME_GAP = 0.2;
	
	/** Covert from source scale to 900x1400 */
	private static final double BASE_SCALE = 0.75;
	
	/** Directory of the highlights */
	private static final String HIGHLIGHT_DIREC = "./graphics/endgame/highlights/";
	
	/** Number of Highlights */
	private static final double NUM_HIGHLIGHTS = 2;
	/** Least Possible Frames Used By Animation */
	private static final int MIN_NUM_FRAMES = 119;
	/** Maximum Screen Height */
	private static final int MAX_SCREEN_SIZE_HEIGHT = 1080;
	/** Maximum Screen Width */
	private static final int MAX_SCREEN_SIZE_WIDTH = 1920;
	
	/** Winning Character Name */
	private String characterName;
	
	/** Highlight Number (1 or 2) */
	private int highlightNum;
	
	/** Main class passthrough */
	private SmashGame myGame;
	
	/** True when animation done */
	private boolean animationDone;
	
	/** Scale */
	private double scale;
	
	/** False if currently rendering */
	private boolean rendered;
	
	/** True when Playing Should Start */
	private boolean shouldPlay;
	
	/* ArrayList of Images **/
	private ArrayList<BufferedImage> images;
	
	/** Loading Screen */
	private BufferedImage loadingScreen;
	
	/** Shows Player Number */
	private BufferedImage playerNum;
	
	/** Foreground image */
	private BufferedImage foreground;
	
	/** Foreground image */
	private BufferedImage continueImage;
	
	/** Timer */
	private Timer timer;
	
	/** Current Frame */
	private double myAnimationFrame;
	
	/**
	 * Constructs A Win Screen
	 * 
	 * @param game Game Passthrough
	 */
	public WinScreen(SmashGame game)
	{
		super();
		myGame = game;
		animationDone = false;
		this.addMouseListener(new mouseHandler());
		this.setVisible(true);
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		images = new ArrayList<BufferedImage>();
	}
	
	/**
	 * Runs The Screen
	 * 
	 * @param winner Winning Player
	 */
	public void run(Player winner)
	{
		rendered = false;
		characterName = winner.getClass().getSimpleName().toLowerCase();
		myAnimationFrame = 0;
		// System.out.println(characterName);
		scale = myGame.getScale();
		this.requestFocusInWindow(true);
		highlightNum = (int) (Math.random() * NUM_HIGHLIGHTS) + 1;
		try
		{
			File image = new File(BASE_DIREC + "loadingscreen.png");
			loadingScreen = ImageResizer.resizeImage(ImageIO.read(image), scale * BASE_SCALE);
		}
		catch (IOException ioe)
		{
			// System.err.println(ioe);
			// Oh well
		}
		this.setVisible(true);
		this.requestFocusInWindow(true);
		FieldUpdater updater = new FieldUpdater();
		updater.start();
		images.clear();
		timer = new Timer();
		timer.schedule(new Renderer(), 0);
		
		try
		{
			File image = new File(BASE_DIREC + winner.getEnemyId() + ".png");
			playerNum = ImageResizer.resizeImage(ImageIO.read(image), scale * BASE_SCALE);
		}
		catch (IOException ioe)
		{
			// System.err.println(ioe);
			// Oh well
		}
		renderUI();
	}
	
	/**
	 * Renders The UI Files
	 */
	private void renderUI()
	{
		try
		{
			File image = new File(BASE_DIREC + "foreground.png");
			foreground = ImageResizer.resizeImage(ImageIO.read(image), scale * BASE_SCALE);
		}
		catch (IOException ioe)
		{
			// System.err.println(ioe);
			// Oh well
		}
		
		try
		{
			File image = new File(BASE_DIREC + "continue.png");
			continueImage = ImageResizer.resizeImage(ImageIO.read(image), scale * BASE_SCALE);
		}
		catch (IOException ioe)
		{
			// System.err.println(ioe);
			// Oh well
		}
	}
	
	/**
	 * Paints screen
	 * 
	 * @param g Graphics object
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		if (rendered)
		{
			g2.clearRect(0, 0, MAX_SCREEN_SIZE_WIDTH, MAX_SCREEN_SIZE_HEIGHT);
			g2.setBackground(Color.WHITE);
			if (myAnimationFrame < images.size() - 1)
			{
				myAnimationFrame = myAnimationFrame + FRAME_GAP;
				g2.drawImage(images.get((int) myAnimationFrame), 0, 0, null);
				
				g2.drawImage(playerNum, (int) (scale * (TEXT_ALIGNED_LEFT_X * BASE_SCALE)),
					(int) (scale * (PLAYERNUM_Y * BASE_SCALE)), null);
				g2.drawImage(foreground, (int) (scale * (TEXT_ALIGNED_LEFT_X * BASE_SCALE)),
					(int) (scale * (FOREGROUND_Y * BASE_SCALE)), null);
				// System.out.println("Playing Frame Number " + myAnimationFrame);
			}
			else
			{
				// System.out.println("Animation Ended");
				g2.drawImage(images.get(images.size() - 1), 0, 0, null);
				g2.drawImage(playerNum, (int) (scale * (TEXT_ALIGNED_LEFT_X * BASE_SCALE)),
					(int) (scale * (PLAYERNUM_Y * BASE_SCALE)), null);
				g2.drawImage(foreground, (int) (scale * (TEXT_ALIGNED_LEFT_X * BASE_SCALE)),
					(int) (scale * (FOREGROUND_Y * BASE_SCALE)), null);
				g2.drawImage(continueImage, (int) (scale * (CONTINUE_TEXT_X * BASE_SCALE)),
					(int) (scale * (CONTINUE_TEXT_Y * BASE_SCALE)), null);
				animationDone = true;
			}
		}
		else
		{
			g2.clearRect(0, 0, MAX_SCREEN_SIZE_WIDTH, MAX_SCREEN_SIZE_HEIGHT);
			g2.setBackground(Color.BLACK);
			
			// Draw Loading Indicators
			double rectangleHeight = (double) images.size() / (double) MIN_NUM_FRAMES;
			if (rectangleHeight > MIN_NUM_FRAMES)
			{ rectangleHeight = MIN_NUM_FRAMES; }
			rectangleHeight = rectangleHeight * (double) SmashGame.APP_HEIGHT;
			
			int rectangleNum = 0;
			for (int xLoc = 0; xLoc < SmashGame.APP_WIDTH; xLoc += LOADING_BAR_SPACING * scale)
			{
				Rectangle2D bars = new Rectangle2D.Double(scale * xLoc, scale * 0, LOADING_BAR_SPACING,
					rectangleHeight * scale);
				if (rectangleNum % 2 == 0)
				{
					g2.setColor(Color.LIGHT_GRAY);
				}
				else
				{
					g2.setColor(Color.DARK_GRAY);
				}
				g2.fill(bars);
				rectangleNum++;
			}
			
			// Draw Game Over Image
			try
			{
				g2.drawImage(loadingScreen, (int) (GAMEOVER_TEXT_X * scale * BASE_SCALE),
					(int) (GAMEOVER_TEXT_Y * scale * BASE_SCALE), null);
			}
			catch (NullPointerException e)
			{
				// System.err.println(e);
				// This will happen a couple of times depending on processing time
			}
			// System.out.println("Loading: Size = " + images.size() + " Height = " +
			// rectangleHeight);
		}
	}
	
	
	private class mouseHandler implements MouseListener
	{
		
		@Override
		public void mouseClicked(MouseEvent e)
		{
			if (animationDone)
			{
				myGame.screenSwitcher("Menu");
				myGame.getMyTitleScreen().run();
			}
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
	
	
	/**
	 * Thread to handle repainting screen
	 */
	private class FieldUpdater extends Thread
	{
		
		/**
		 * Repaints screen on interval while not paused
		 */
		@Override
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
	
	
	/**
	 * Renderer Class
	 */
	class Renderer extends TimerTask
	{
		
		/**
		 * Renders Frames
		 */
		@Override
		public void run()
		{
			// ArrayList<BufferedImage> temp = new ArrayList<BufferedImage>();
			int renderFrame = 1;
			boolean fileExists = true;
			while (fileExists)
			{
				try
				{
					// Create File Directory String
					String fileDirectory = HIGHLIGHT_DIREC + characterName + "/" + highlightNum + "/Layer ";
					fileDirectory = fileDirectory + (int) renderFrame + ".png";
					
					// System.out.println(fileDirectory);
					
					// Create Image
					File image = new File(fileDirectory);
					BufferedImage renderedFrame = ImageResizer.resizeImage(ImageIO.read(image), scale * BASE_SCALE);
					images.add(renderedFrame);
				}
				catch (IOException ioe)
				{
					fileExists = false;
				}
				renderFrame++;
			}
			// images = temp;
			rendered = true;
			// System.out.println("Win Screen Rendered " + images.size() + " Images");
			timer.cancel();
		}
	}
}
