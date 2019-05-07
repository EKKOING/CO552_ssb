import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics2D;
import java.awt.geom.*;

/**
 * Manages the players in a Smash Game
 * 
 * @author Nicholas Lorentzen
 * @version 05/06/2019
 */
public class Players {
    /** State of the game */
    public static boolean gameRunning;
    /** Number of players */
    public static final int NUM_PLAYERS = 2;
    /** Time between updates in ms */
    public static final int UPDATE_DELAY = 10;

    /** List of players */
    private ArrayList<Player> myPlayers;
    /** Num of players int */
    private int myNumPlayers;

    /** Timer reference */
    private Timer myTimer;
    /** Orb App */
    private SmashGame myGame;

    /** Keyput passthrough */
    public Keyput myKeyput;

    /**
     * Creates new collection
     * 
     * @param game SmashGame passthrough
     * @param keyput Keyput passthrough
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
     * Gets the current number of players
     * @return number of players
     */
    public int getNumPlayers() {
        return myNumPlayers;
    }

    /**
     * Returns arraylist of players
     * @return Player arraylist
     */
    public ArrayList<Player> getPlayers() {
        return myPlayers;
    }

    /**
     * Sets up players
     */
    public void setupPlayers() {
        myPlayers.clear();
        myPlayers.add(new Pharah(1, 93, 867, this));
        myPlayers.add(new Pharah(2, 1350, 867, this));
    }

    /**
     * Finds a player based on id num
     * @param id id of the player to search for
     * @return the Player found or null if none
     */
    public Player findPlayer(int id)
    {
        for(Player temp : myPlayers)
        {
            if(temp.myId == id)
            {
                return temp;
            }
        }
        return null;
    }

    /**
     * Runs tasks on the players
     */
    private class UpdateTask extends TimerTask {
        /**
         * runs actions on the players
         */
        public void run() {
            if (!myGame.isPaused()) {
                for (int idx = 0; idx < myPlayers.size(); idx++) {
                    Player o = myPlayers.get(idx);
                    o.move(myKeyput.getKeys());
                }
            }
        }
    }
}