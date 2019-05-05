import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics2D;
import java.awt.geom.*;

/**
 * Manages the players in a Smash Game
 * 
 * @author Nicholas Lorentzen
 * @version 04/28/2019
 */
public class Players {
    /** Start speed */
    public static boolean gameRunning;
    /** Num orbs */
    public static final int NUM_PLAYERS = 2;
    /** Time between updates in ms */
    public static final int UPDATE_DELAY = 10;
    /** Amount to bounce */
    // public static final double BOUNCE_AMT = (Orb.SIZE / 2);

    /** List of players */
    private ArrayList<Player> myPlayers;
    /** Num of players int */
    private int myNumPlayers;

    /** Timer reference */
    private Timer myTimer;
    /** Orb App */
    private SmashGame myGame;

    public Keyput myKeyput;

    /**
     * Creates new collection
     * 
     * @param game SmashGame passthrough
     */
    public Players(SmashGame game, Keyput keyput) {
        myGame = game;
        myKeyput = keyput;
        myPlayers = new ArrayList<Player>();
        myNumPlayers = NUM_PLAYERS;
        // mySpeed = SPEED;
        setupPlayers();

        myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new UpdateTask(), 0, UPDATE_DELAY);
    }

    /**
     * Gets num orbs
     * 
     * @return number of orbs
     */
    public int getNumPlayers() {
        return myNumPlayers;
    }

    /**
     * Returns arraylist of orbs
     * 
     * @return Player arraylist
     */
    public ArrayList<Player> getPlayers() {
        return myPlayers;
    }

    /**
     * Sets the number of orbs
     * 
     * @param num number to set
     */
    public void setNumOrbs(int num) {
        myNumPlayers = num;
        setupPlayers();
    }

    /**
     * Sets up orbs
     */
    public void setupPlayers() {
        myPlayers.clear();
        myPlayers.add(new Pharah(1, 10, 676));
        // myPlayers.add(new Pharah(2, 1430, 10));
    }

    /**
     * Runs tasks on the players
     */
    private class UpdateTask extends TimerTask {
        /**
         * runs actions on the orbs
         */
        public void run() {
            if (!myGame.isPaused()) {
                for (int idx = 0; idx < myPlayers.size(); idx++) {
                    Player o = myPlayers.get(idx);
                    // checkForCollision(o);
                    // o.checkForBounce();
                    for (Key temp : myKeyput.getKeys()) {
                        o.move(temp);
                    }
                    //o.drawMe(Graphics2D);
                }
            }
        }
    }
}