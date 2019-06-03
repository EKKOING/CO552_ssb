import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * @author Nicholas Lorentzen
 * @version 20190602
 */
public class ImageResizer
{
	
	/** Smash Game Passthrough */
	private SmashGame myGame;
	
	/**
	 * Constructor with SmashGame param
	 * 
	 * @param frame SmashGame that controls scale
	 */
	@Deprecated
	public ImageResizer(SmashGame frame)
	{
		myGame = frame;
	}
	
	/**
	 * Resizes An Image Based off of the SmashGame that it was given
	 * 
	 * @param bi Image to resize
	 * @return The resized image
	 */
	@Deprecated
	public BufferedImage resizeImage(BufferedImage bi)
	{
		double scale = myGame.getScale();
		AffineTransform resize = AffineTransform.getScaleInstance(scale, scale);
		AffineTransformOp op = new AffineTransformOp(resize, AffineTransformOp.TYPE_BICUBIC);
		return op.filter(bi, null);
	}
	
	public static BufferedImage resizeImage(BufferedImage bi, double scale)
	{
		if (scale >= 1.0 && scale <= 1.1)
		{
			return bi;
		}
		else
		{
			AffineTransform resize = AffineTransform.getScaleInstance(scale, scale);
			AffineTransformOp op = new AffineTransformOp(resize, AffineTransformOp.TYPE_BICUBIC);
			return op.filter(bi, null);
		}
	}
}
