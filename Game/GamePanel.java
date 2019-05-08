import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

/**
 * Panel class for the orb app to handle the frame in which the orbs are
 * displayed
 * 
 * @author Nicholas Lorentzen
 * @version 05/05/2019
 */
public class GamePanel extends JPanel {
    /** Game passthrough */
    private SmashGame myApp;

    /** Players passthrough */
    private Players myPlayers;

    /**
     * Creates new GamePanel
     * 
     * @param app  the App passthrough
     * @param players the Players passthrough
     */
    public GamePanel(SmashGame app, Players players) {
        super();
        myPlayers = players;
        myApp = app;

        this.setVisible(true);
        this.setFocusable(true);

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

        //g2.clearRect(0, 0, getWidth(), getHeight());

        ArrayList<Player> orbs = myPlayers.getPlayers();
        for (int idx = 0; idx < orbs.size(); idx++) {
            Player o = orbs.get(idx);
            // o.updateMax(getWidth(), getHeight());
            o.drawMe(g2);
        }

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
                if (!myApp.isPaused()) {
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
