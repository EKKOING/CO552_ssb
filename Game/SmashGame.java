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
 
    private JFrame myApp;

    private GameScreen myGameScreen;

    /** App panel */
    private JPanel myAppPanel;
    
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
       isPaused = false;
       setupFrame();
    }
 
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