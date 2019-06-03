import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Nicholas Lorentzen
 * @version 20190602
 */
public class CharacterMenuIcon
{
	//Directories
	/** Base Directory for Images */
	public static final String BASE_DIREC = "./graphics/menus/character";
	/** Additional Directory for Icons */
	public static final String ICON_DIREC = "/icons/";
	/** Additional Directory for Previews */
	public static final String PREVIEW_DIREC = "/previews/";

	//Icon Size Settings
	/** Height of Icon */
	public static final double HEIGHT = 140;
	/** Width of Icon */
	public static final double WIDTH = 109;
	
	//Layout Settings
	/** Gap Between Icons Y*/
	public static final double YGAP = 40;
	/** Gap Between Icons X */
	public static final double XGAP = 20;
	
	//Images
	/** Icon Image */
	private BufferedImage myIcon;
	/** Preview Image */
	private BufferedImage myPreview;
	
	//Position
	/** X Position */
	private double xPos;
	/** Y Position */
	private double yPos;
	
	/** Scale */
	private double scale;
	
	/** Name */
	private String myName;
	
	/**
	 * Constructor for Icon
	 * @param name Name of Character to Represent
	 * @param s Scale
	 * @param x X Location
	 * @param y Y Location
	 */
	public CharacterMenuIcon(String name, double s, double x, double y)
	{
		myName = name;
		scale = s;
		xPos = x;
		yPos = y;
		
		try
		{
			// Create File Directory String
			String fileDirectory = BASE_DIREC + ICON_DIREC + myName + ".png";
			
			// Create Image
			File image = new File(fileDirectory);
			myIcon = ImageResizer.resizeImage(ImageIO.read(image), scale);
		}
		catch (IOException e)
		{
			System.err.println(BASE_DIREC + ICON_DIREC + myName + ".png :Does not exist");
		}
		
		try
		{
			// Create File Directory String
			String fileDirectory = BASE_DIREC + PREVIEW_DIREC + myName + ".png";
			
			// Create Image
			File image = new File(fileDirectory);
			myPreview = ImageResizer.resizeImage(ImageIO.read(image), scale);
		}
		catch (IOException e)
		{
			System.err.println(BASE_DIREC + PREVIEW_DIREC + myName + ".png :Does not exist");
		}
	}
	
	/**
	 * Checks if MouseEvent is within the icon
	 * @param e MouseEvent to check
	 * @return True If Icon Contains e, Else False
	 */
	public boolean contains(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		
		if (xPos * scale < x && x < scale * (xPos + WIDTH))
		{
			if (scale * yPos < y && y < scale * (yPos + HEIGHT))
			{ return true; }
		}
		
		return false;
	}
	
	/**
	 * Draws Icon
	 * @param g2 Graphics Object passthrough
	 */
	public void drawMe(Graphics2D g2)
	{
		g2.drawImage(myIcon, (int) (xPos * scale), (int) (yPos * scale), null);
	}
	
	/**
	 * Gets the Preview
	 * @return myPreview
	 */
	public BufferedImage getPreview()
	{
		return myPreview;
	}
	
	/**
	 * Gets Name
	 * @return myName
	 */
	public String getMyName()
	{
		return myName;
	}
}
