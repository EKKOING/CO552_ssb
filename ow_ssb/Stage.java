import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.io.File;
import java.util.ArrayList;

/**
 * @author student
 *
 */
public class Stage {

	public static final double FLOOR_TOP = 722;

	public static final double FLOOR_GAP = 104;

	public static final double FLOOR_BOTTOM = 758;

	public static final double BALCONY_TOP = 432;

	public static final double BALCONY_GAP = 531;

	public static final double BALCONY_BOTTOM = 468;

	/** Stage Path */
	public final String BASE_DIREC = "./graphics/stages/";

	/* Current Image Representation **/
	public BufferedImage myImage;

	/* Animation Counter **/
	public int myAnimationFrame;

	/* String to Fetch Image **/
	public String animationDirec;

	/* ArrayList of Images **/
	public ArrayList<BufferedImage> images;

	/** Screen Created From */
	public GameScreen myScreen;

	/** Scale */
	public double scale;

	/**
	 * 
	 */
	public Stage(String name, GameScreen screen) {
		myAnimationFrame = 1;
		animationDirec = name;
		myScreen = screen;
		scale = myScreen.myGame.scale;

		images = new ArrayList<BufferedImage>();

		boolean fileExists = true;
		while(fileExists)
		{
			try {
				//Create File Directory String
				String fileDirectory = BASE_DIREC + animationDirec + "/";
				if (myAnimationFrame < 10) {fileDirectory = fileDirectory + "0";}
				fileDirectory = fileDirectory + (int) myAnimationFrame + ".png";
	
				//Create Image
				File image = new File(fileDirectory);
				myImage = myScreen.myGame.iR.resizeImage(ImageIO.read(image));
				images.add(myImage);
			} 
			catch (IOException ioe) {
				System.err.println(ioe);
				fileExists = false;
			}
			myAnimationFrame++;
		}
	}

	public void drawMe(Graphics2D g2) {
		if(myAnimationFrame < images.size() - 2)
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