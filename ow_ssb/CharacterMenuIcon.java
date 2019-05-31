import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.io.File;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * @author student
 *
 */
public class CharacterMenuIcon {

	public static final String BASE_DIREC = "./graphics/menus/character";
	public static final String ICON_DIREC = "/icons/";
	public static final String PREVIEW_DIREC = "/previews/";
	public static final double HEIGHT = 140;
	public static final double WIDTH = 109;

	public BufferedImage myIcon;

	public BufferedImage myPreview;

	public double xPos;

	public double yPos;

	public double scale;

	public String myName;

	/**
	 * 
	 */
	public CharacterMenuIcon(String name, double s, double x, double y) {
		myName = name;
		scale = s;
		xPos = x;
		yPos = y;

		try {
			// Create File Directory String
			String fileDirectory = BASE_DIREC + ICON_DIREC + myName + ".png";

			// Create Image
			File image = new File(fileDirectory);
			myIcon = ImageResizer.resizeImage(ImageIO.read(image), scale);
		} catch (IOException e) {
			System.err.println(BASE_DIREC + ICON_DIREC + myName + ".png :Does not exist");
		}

		try {
			// Create File Directory String
			String fileDirectory = BASE_DIREC + PREVIEW_DIREC + myName + ".png";

			// Create Image
			File image = new File(fileDirectory);
			myPreview = ImageResizer.resizeImage(ImageIO.read(image), scale);
		} catch (IOException e) {
			System.err.println(BASE_DIREC + PREVIEW_DIREC + myName + ".png :Does not exist");
		}
	}

	public boolean contains(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (xPos * scale < x && x < scale * (xPos + WIDTH)) {
			if (scale * yPos < y && y < scale * (yPos + HEIGHT)) {
				return true;
			}
		}

		return false;
	}

	public void drawMe(Graphics2D g2) {
		g2.drawImage(myIcon, (int) (xPos * scale), (int) (yPos * scale), null);
	}

	public BufferedImage getPreview() {
		return myPreview;
	}

}
