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
public class WinScreen extends JPanel
{
	
	/** Animation Path */
	public final String BASE_DIREC = "./graphics/endgame/";
	
	/** Iteration Amount for Animatioon */
	private static final double FRAME_GAP = 0.2;
	
	/** Covert from source scale to 900x1400 */
	private static final double BASE_SCALE = 0.75;
	
	/** Directory of the highlights */
	private static final String HIGHLIGHT_DIREC = "./graphics/endgame/highlights/";
	
	private static final double NUM_HIGHLIGHTS = 2;
	
	private static final int MIN_NUM_FRAMES = 119;
	
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
	public boolean rendered;
	
	/** True when Playing Should Start */
	public boolean shouldPlay;
	
	/* ArrayList of Images **/
	public ArrayList<BufferedImage> images;
	
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
	
	/** Runs The Screen */
	public void run(Player winner)
	{
		rendered = false;
		characterName = winner.getClass().getSimpleName().toLowerCase();
		myAnimationFrame = 0;
		// System.out.println(characterName);
		scale = myGame.scale;
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
			File image = new File(BASE_DIREC + winner.enemyId + ".png");
			playerNum = ImageResizer.resizeImage(ImageIO.read(image), scale * BASE_SCALE);
		}
		catch (IOException ioe)
		{
			// System.err.println(ioe);
			// Oh well
		}
		renderUI();
	}
	
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
			g2.clearRect(0, 0, 1920, 1080);
			g2.setBackground(Color.WHITE);
			if (myAnimationFrame < images.size() - 1)
			{
				myAnimationFrame = myAnimationFrame + FRAME_GAP;
				g2.drawImage(images.get((int) myAnimationFrame), 0, 0, null);
				
				g2.drawImage(playerNum, (int) (scale * (192 * BASE_SCALE)), (int) (scale * (218 * BASE_SCALE)), null);
				g2.drawImage(foreground, (int) (scale * (192 * BASE_SCALE)), (int) (scale * (108 * BASE_SCALE)), null);
				// System.out.println("Playing Frame Number " + myAnimationFrame);
			}
			else
			{
				// System.out.println("Animation Ended");
				g2.drawImage(images.get(images.size() - 1), 0, 0, null);
				g2.drawImage(playerNum, (int) (scale * (192 * BASE_SCALE)), (int) (scale * (218 * BASE_SCALE)), null);
				g2.drawImage(foreground, (int) (scale * (192 * BASE_SCALE)), (int) (scale * (108 * BASE_SCALE)), null);
				g2.drawImage(continueImage, (int) (scale * (1158 * BASE_SCALE)), (int) (scale * (859 * BASE_SCALE)),
					null);
				animationDone = true;
			}
		}
		else
		{
			g2.clearRect(0, 0, 1920, 1080);
			g2.setBackground(Color.BLACK);
			
			// Draw Loading Indicators
			double rectangleHeight = (double) images.size() / (double) MIN_NUM_FRAMES;
			if (rectangleHeight > MIN_NUM_FRAMES)
			{ rectangleHeight = MIN_NUM_FRAMES; }
			rectangleHeight = rectangleHeight * (double) SmashGame.APP_HEIGHT;
			
			int rectangleNum = 0;
			for (int xLoc = 0; xLoc < SmashGame.APP_WIDTH; xLoc += 100 * scale)
			{
				Rectangle2D bars = new Rectangle2D.Double(scale * xLoc, scale * 0, 70, rectangleHeight * scale);
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
				g2.drawImage(loadingScreen, (int) (619 * scale * BASE_SCALE), (int) (108 * scale * BASE_SCALE), null);
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
				myGame.myTitleScreen.run();
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
