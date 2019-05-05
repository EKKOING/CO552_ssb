import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

/**
 * Panel class for the orb app to handle the frame in which the orbs are
 * displayed
 * 
 * @author Nicholas Lorentzen
 * @version 04/28/2019
 */
public class GamePanel extends JPanel {
    private SmashGame myApp;

    private Players myPlayers;

    /**
     * checks to see if the game is paused
     * 
     * @param app  the App passthrough
     * @param orbs the Orb Collection
     */
    public GamePanel(SmashGame app, Players player) {
        super();
        myPlayers = player;
        myApp = app;

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

        //g2.clearRect(0, 0, getWidth(), getHeight());

        ArrayList<Player> orbs = myPlayers.getPlayers();
        for (int idx = 0; idx < orbs.size(); idx++) {
            Player o = orbs.get(idx);
            // o.updateMax(getWidth(), getHeight());
            o.drawMe(g2);
        }

    }

    /**
     * Thread to repaint screen
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
