import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;
import java.io.File;
import java.util.ArrayList;

/**
 * @author student
 *
 */
public class Stage {

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

	/**
	 * 
	 */
	public Stage(String name) {
		myAnimationFrame = 1;
		animationDirec = name;

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
				myImage = ImageIO.read(image);
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