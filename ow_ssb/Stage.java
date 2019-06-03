import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * @author Nicholas Lorentzen
 * @version 20190602
 */
public class Stage
{
	
	/** Y Location of the Top of the Floor */
	public static final double FLOOR_TOP = 722;
	/** Gap Length on Sides of Floor */
	public static final double FLOOR_GAP = 104;
	/** Y Location on the Bottom of the Floor */
	public static final double FLOOR_BOTTOM = 758;
	/** Y Location of the Top of the Balcony */
	public static final double BALCONY_TOP = 432;
	/** Gap Length on Sides of Balcony */
	public static final double BALCONY_GAP = 531;
	/** Y Location of the Bottom of the Balcony */
	public static final double BALCONY_BOTTOM = 468;
	
	/** Distance Off Screen to Kill Players */
	public static final double KILL_BARRIER_SIDE = 120;
	
	/** Stage Path */
	public final String BASE_DIREC = "./graphics/stages/";
	
	/* Current Image Representation **/
	private BufferedImage myImage;
	
	/* Animation Counter **/
	private int myAnimationFrame;
	
	/* String to Fetch Image **/
	private String animationDirec;
	
	/* ArrayList of Images **/
	private ArrayList<BufferedImage> images;
	
	/** Screen Created From */
	private GameScreen myScreen;
	
	/** Scale */
	private double scale;
	
	/**
	 * Constructs A Stage
	 * 
	 * @param name   Name of Map
	 * @param screen Screen to Add to
	 */
	public Stage(String name, GameScreen screen)
	{
		myAnimationFrame = 1;
		animationDirec = name;
		myScreen = screen;
		scale = myScreen.getMyGame().getScale();
		
		images = new ArrayList<BufferedImage>();
		
		boolean fileExists = true;
		while (fileExists)
		{
			try
			{
				// Create File Directory String
				String fileDirectory = BASE_DIREC + animationDirec + "/";
				if (myAnimationFrame < 10)
				{ fileDirectory = fileDirectory + "0"; }
				fileDirectory = fileDirectory + (int) myAnimationFrame + ".png";
				
				// Create Image
				File image = new File(fileDirectory);
				myImage = ImageResizer.resizeImage(ImageIO.read(image), scale);
				images.add(myImage);
			}
			catch (IOException ioe)
			{
				System.err.println(ioe);
				fileExists = false;
			}
			myAnimationFrame++;
		}
	}
	
	/**
	 * Draws Stage
	 * 
	 * @param g2 Graphics Object Passthrough
	 */
	public void drawMe(Graphics2D g2)
	{
		if (myAnimationFrame < images.size() - 2)
		{
			myAnimationFrame++;
		}
		else
		{
			myAnimationFrame = 0;
		}
		g2.drawImage(images.get(myAnimationFrame), 0, 0, null);
	}
	
}