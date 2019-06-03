import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Manages the players in a Smash Game
 * 
 * @author Nicholas Lorentzen
 * @version 20190602
 */
public class Players
{
    
    /** State of the game */
    private boolean gameRunning;
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
    private Keyput myKeyput;
    
    /**
     * Creates new collection
     * 
     * @param game   SmashGame passthrough
     * @param keyput Keyput passthrough
     */
    public Players(SmashGame game, Keyput keyput)
    {
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
     * Starts the players
     */
    public void StartPlayers()
    {
        gameRunning = true;
    }
    
    /**
     * Gets the current number of players
     * 
     * @return number of players
     */
    public int getNumPlayers()
    {
        return myNumPlayers;
    }
    
    /**
     * Returns arraylist of players
     * 
     * @return Player arraylist
     */
    public ArrayList<Player> getPlayers()
    {
        return myPlayers;
    }
    
    /**
     * Sets up players
     */
    public void setupPlayers()
    {
        gameRunning = false;
        myPlayers.clear();
        int currentPlayer = 0;
        for (int idx = 1; idx <= NUM_PLAYERS; idx++)
        {
            Player temp = new Player(0, 0, 0, this);
            if (idx == 1)
            {
                currentPlayer = myGame.getPlayer1();
            }
            else
            {
                currentPlayer = myGame.getPlayer2();
            }
            
            switch (currentPlayer)
            {
                case 1:
                    temp = new Pharah(0, 0, 0, this);
                    break;
            }
            
            temp.setMyId(idx);
            temp.setKeybindings();
            
            if (idx == 1)
            {
                temp.setMyPos(new Coord(Stage.FLOOR_GAP + temp.getWidth(), Stage.FLOOR_TOP));
            }
            else
            {
                temp.setMyPos(new Coord(SmashGame.APP_WIDTH - Stage.FLOOR_GAP - temp.getWidth(), Stage.FLOOR_TOP));
            }
            temp.setLives(SmashGame.NUM_LIVES);
            myPlayers.add(temp);
        }
    }
    
    /**
     * Finds a player based on id num
     * 
     * @param id id of the player to search for
     * @return the Player found or null if none
     */
    public Player findPlayer(int id)
    {
        for (Player temp : myPlayers)
        {
            if (temp.getMyId() == id)
            { return temp; }
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
    private class UpdateTask extends TimerTask
    {
        
        /**
         * runs actions on the players
         */
        public void run()
        {
            if (!myGame.isPaused() && gameRunning)
            {
                try
                {
                    for (Player temp : myPlayers)
                    {
                        if (temp.getLives() == 0)
                        {
                            // temp.setLives(-1);
                            myGame.screenSwitcher("End");
                            myGame.getMyWinScreen().run(temp);
                            gameRunning = false;
                            myPlayers.clear();
                            break;
                        }
                        temp.move(myKeyput.getKeys());
                    }
                }
                catch (ConcurrentModificationException e)
                {
                    // Skip Frame
                }
            }
        }
    }
    
    /**
     * Gets myPlayers
     * 
     * @return myPlayers
     */
    public ArrayList<Player> getMyPlayers()
    {
        return myPlayers;
    }
    
    /**
     * Sets myPlayers
     * 
     * @param myPlayers Value to set
     */
    public void setMyPlayers(ArrayList<Player> myPlayers)
    {
        this.myPlayers = myPlayers;
    }
    
    /**
     * Gets myGame
     * 
     * @return myGame
     */
    public SmashGame getMyGame()
    {
        return myGame;
    }
}