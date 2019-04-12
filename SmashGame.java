import java.awt.*;
import javax.swing.*;

/**
Class to run the Super Smash Bros Game
@author Nicholas Lorentzen
@version 2019/03/25
*/
public class SmashGame
{
    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 600;
 
    public static final int XMAX = 400;
    public static final int YMAX = 600;
    
    public static final double SPEED_MIN = 0.0;
    public static final double SPEED_MAX = 20.0;
    
    public static final int ORB_MIN = 5;
    public static final int ORB_MAX = 25;
    
    public static final String TITLE = "Title";
    public static final String ORB = "Orb";
 
    private JFrame myApp;
    private Keyput myKeyput;
    
    //private OrbCollection myCollection;
    
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
       //myCollection = new OrbCollection(this);
       isPaused = true;
       setupFrame();
    }
 
    private void setupFrame()
    {
       myApp = new JFrame();
       myApp.setSize(APP_WIDTH, APP_HEIGHT);
    
       
       myKeyput = new Keyput(this);
       myKeyput.setFocusable(true);
       
       myApp.add(myKeyput);
       myApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       myApp.setVisible(true);
    } 
}