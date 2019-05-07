import java.awt.*;
import javax.swing.*;

/**
Class to run the Super Smash Bros Game
@author Nicholas Lorentzen
@version 2019/05/04
*/
public class SmashGame
{
    /** App Width */
    public static final int APP_WIDTH = 1440;
    /** App Height */
    public static final int APP_HEIGHT = 900;
 
    /** JFrame that holds the entirety of the game */
    private JFrame myApp;

    /** Screen for when the game is running */
    private GameScreen myGameScreen;

    /** App panel */
    private JPanel myAppPanel;
    
    /** Master pause state */
    private boolean isPaused;
    
    public static void main(String[] args)
    {
       SmashGame app = new SmashGame();
       app.run();
    }
    
    /**
     * Returns the current state of the game
     * @return The current pause state
     */
    public boolean isPaused()
    {
       return isPaused;
    }
    
    /**
     * Controls the state of the app
     * @param flag State to set the app to
     */
    public void setPaused(boolean flag)
    {
       isPaused = flag;
    }
    
    /**
     * Code to run the app
     */
    public void run()
    {
       isPaused = false;
       setupFrame();
    }
 
    /**
     * Sets up the JFrame
     */
    private void setupFrame()
    {
       myApp = new JFrame();
       myApp.setSize(APP_WIDTH, APP_HEIGHT);
      
       myGameScreen = new GameScreen(this);
       myGameScreen.setFocusable(true);
      
       myAppPanel = new JPanel(new CardLayout());
       //myAppPanel.add(myTitlePanel, TITLE);
       myAppPanel.add("Game", myGameScreen);
      
       myApp.add(myAppPanel);
       //myApp.add(myKeyput);
       myApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       myApp.setVisible(true);
    } 
}