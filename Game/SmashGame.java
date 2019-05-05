import java.awt.*;
import javax.swing.*;

/**
Class to run the Super Smash Bros Game
@author Nicholas Lorentzen
@version 2019/05/04
*/
public class SmashGame
{
    public static final int APP_WIDTH = 1440;
    public static final int APP_HEIGHT = 900;
    
    public static final double SPEED_MIN = 0.0;
    public static final double SPEED_MAX = 20.0;
    
    public static final int ORB_MIN = 5;
    public static final int ORB_MAX = 25;
    
    public static final String TITLE = "Title";
    public static final String ORB = "Orb";
 
    private JFrame myApp;
    private Keyput myKeyput;

    /** App panel */
   private JPanel myAppPanel;
    
   private Players myPlayers;
    
    private boolean isPaused;
    
    public static void main(String[] args)
    {
       SmashGame app = new SmashGame();
       app.run();
    }
    
    public boolean isPaused()
    {
       return isPaused;
    }
    
    public void setPaused(boolean flag)
    {
       isPaused = flag;
    }
    
    public void run()
    {
       myPlayers = new Players(this);
       isPaused = true;
       setupFrame();
    }
 
    private void setupFrame()
    {
       myApp = new JFrame();
       myApp.setSize(APP_WIDTH, APP_HEIGHT);
    
       
       myKeyput = new Keyput(this);
       myKeyput.setFocusable(true);
       

       //myApp = new JFrame();
      
       //myTitlePanel = new OrbTitleScreen(this);
      
       //myGameScreen = new GameScreen(this, myPlayers, myKeyput);
       //myGameScreen.setFocusable(true);
      
       //myAppPanel = new JPanel(new CardLayout());
       //myAppPanel.add(myTitlePanel, TITLE);
       //myAppPanel.add(myOrbPanel, ORB);
      
       //myApp.add(myAppPanel);
       //myApp.add(myKeyput);
       myApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       myApp.setVisible(true);
    } 
}