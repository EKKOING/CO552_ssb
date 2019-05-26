import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Control.Type;
import javax.sound.sampled.Line.Info;


/**
 * https://stackoverflow.com/questions/20354508/sound-effects-in-java
 * 
 * @author https://stackoverflow.com/users/1172265/batuhan-bardak
 */
public class JavaAudio
{
	public static synchronized void playSound(final String url) {
		  new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
						File myFile = new File(url);
						//AudioInputStream sound = new AudioInputStream(myFile);
						Clip myClip = AudioSystem.getClip();
						//myClip.open(sound);

		        /**Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream( .getResourceAsStream(url));
		        clip.open(inputStream);
		        clip.start();*/
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}
}