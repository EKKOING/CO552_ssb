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
	
	public static final String BASE_DIREC = "./graphics/menus/character";
	public static final String ICON_DIREC = "/icons/";
	public static final String PREVIEW_DIREC = "/previews/";
	public static final double HEIGHT = 140;
	public static final double WIDTH = 109;
	
	public static final double YGAP = 40;
	public static final double XGAP = 20;
	
	private BufferedImage myIcon;
	
	private BufferedImage myPreview;
	
	private double xPos;
	
	private double yPos;
	
	private double scale;
	
	private String myName;
	
	/**
	 * 
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
	
	public void drawMe(Graphics2D g2)
	{
		g2.drawImage(myIcon, (int) (xPos * scale), (int) (yPos * scale), null);
	}
	
	public BufferedImage getPreview()
	{
		return myPreview;
	}
	
	public String getMyName()
	{
		return myName;
	}
	
	public void setMyName(String myName)
	{
		this.myName = myName;
	}
}
