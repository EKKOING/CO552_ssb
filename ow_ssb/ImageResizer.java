import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.*;

/**
 * @author student
 *
 */
public class ImageResizer {

	private SmashGame myGame;

	/**
	 * 
	 */
	public ImageResizer(SmashGame frame) {
		myGame = frame;
	}

	/**
	 * 
	 */
	public ImageResizer() {
	}

	public BufferedImage resizeImage(BufferedImage bi) 
	{
		double scale = myGame.scale;
		AffineTransform resize = AffineTransform.getScaleInstance(scale, scale);
		AffineTransformOp op = new AffineTransformOp(resize, AffineTransformOp.TYPE_BICUBIC);
		return op.filter(bi, null);
	}

	public static BufferedImage resizeImage(BufferedImage bi, double scale) 
	{
		if(scale >= 1.0 && scale <= 1.1)
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
