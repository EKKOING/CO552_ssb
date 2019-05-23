import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;
/**
 * Class to be able to understand multiple keystrokes and store them in an
 * ArrayList for exporting
 * 
 * @author Nicholas Lorentzen
 * @version 2019/05/21
 */
public class GameScreen extends JPanel {
    /** Keyput class for handling User Input */
    public Keyput myKeyput;
    /** Players class for managing the players */
    public Players myPlayers;
    /** Passthrough of the SmashGame class to allow reference to the gamestate */
    public SmashGame myGame;

    /** Stage */
    public Stage myStage;

    /** Stage Image */
    private BufferedImage myImage;

    /**
     * Constructs a GameScreen
     * @param game Game passthrough
     */
    public GameScreen(SmashGame game) {
        super();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("./graphics/menu/cursor.png");
		Cursor c = toolkit.createCustomCursor(image , new Point(this.getX(), this.getY()), "img");
		this.setCursor (c);

        myGame = game;
    }

    public void run()
    {
        myStage = new Stage("gibraltar");
        myKeyput = new Keyput(myGame);
        myPlayers = new Players(myGame, myKeyput);
        
        this.addKeyListener(myKeyput);
        this.setVisible(true);
        this.setFocusable(true);
        this.requestFocusInWindow();
        FieldUpdater updater = new FieldUpdater();
        updater.start();
    }

    /**
     * Paints screen
     * 
     * @param g Graphics object
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Rectangle2D.Double test = new Rectangle2D.Double(0, 0, 200, 200);
        //g2.fill(test);
        g2.clearRect(0, 0, myGame.APP_WIDTH, myGame.APP_HEIGHT);

        myStage.drawMe(g2);

        ArrayList<Player> paintPlayers = myPlayers.getPlayers();
        for (Player temp : paintPlayers) {temp.drawMe(g2);}

    }

    /**
     * Thread to handle repainting screen
     */
    private class FieldUpdater extends Thread {
        /**
         * Repaints screen on interval while not paused
         */
        public void run() {
            while (true) {
                if (!(myGame.isPaused())) {
                    repaint();
                }

                try {
                    sleep(10);
                } catch (InterruptedException ie) {

                }
            }
        }
    }
}
