import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

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
    public ArrayList<Player> myPlayers;
    /** Num of players int */
    private int myNumPlayers;

    /** Timer reference */
    private Timer myTimer;
    /** Orb App */
    public SmashGame myGame;

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
        int currentPlayer = 0;
        for(int idx = 1; idx <= 2; idx++)
        {
            Player temp = new Player(0, 0, 0, this);
            if(idx == 1)
            {   
                currentPlayer = myGame.player1;
            }
            else{
                currentPlayer = myGame.player2;
            }
            
            switch(currentPlayer)
            {
                case 1:
                temp = new Pharah(0, 0, 0, this);
                break;
            }
            temp.myId = idx;
            temp.setKeybindings();
            if(idx == 1)
            {
                temp.myPos = new Coord(Stage.FLOOR_GAP + 20, Stage.FLOOR_TOP);
            }
            else
            {
                temp.myPos = new Coord(SmashGame.APP_WIDTH - Stage.FLOOR_GAP - 20, Stage.FLOOR_TOP);
            }
            myPlayers.add(temp);
        }
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

    public void removeObject(Player remove)
    {
        myPlayers.remove(remove);
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
                try {
                    for (Player temp: myPlayers) {
                        temp.move(myKeyput.getKeys());
                    }
                } catch (ConcurrentModificationException e) {
                    //Skip Frame
                }
            }
        }
    }
}