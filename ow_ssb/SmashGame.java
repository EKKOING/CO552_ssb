import java.awt.*;
import javax.swing.*;

/**
 * Class to run the Super Smash Bros Game
 * 
 * @author Nicholas Lorentzen
 * @version 2019/05/04
 */
public class SmashGame {
   /** App Width */
   public static final int APP_WIDTH = 1440;
   /** App Height */
   public static final int APP_HEIGHT = 900;

   /** Boolean for what happens at boundaries 
    * If True, Characters Wrap, otherwise Characters bounce
   */
   public static final boolean MOTION_WRAP = false;

   /** Respawns in Random Location to prevent Spawncamping*/
   public static final boolean NO_SPAWNCAMPING = true;

   /** Int for number of lives given to players */
   public static final int NUM_LIVES = 3;

   /** JFrame that holds the entirety of the game */
   public JFrame myApp;

   /** Screen for when the game is running */
   public GameScreen myGameScreen;

   /** TitleScreen */
   public TitleScreen myTitleScreen;

   /** Game End Screen */
   public WinScreen myWinScreen;

   /** App panel */
   private JPanel myAppPanel;

   /** Stage to Play */
   public int stage;

   /** Player 1 Character */
   public int player1;

   /** Player 2 Character */
   public int player2;

   /** Master pause state */
   public boolean isPaused;

   /** Image Resizer */
   public ImageResizer iR;

   /** Scale */
   public double scale;

   /** Monitor Running On */
   public GraphicsDevice gd;

   public static void main(String[] args) {
      SmashGame app = new SmashGame();
      app.run();
   }

   /**
    * Gets and returns the scale modifier
    * @return the scale modifier as a double
    */
   public double getScale()
   {
      if(myApp.getSize().getWidth() / (double) APP_WIDTH > myApp.getSize().getHeight() / (double) APP_HEIGHT)
      {
         return myApp.getSize().getHeight() / ((double) APP_HEIGHT);
      }
      else
      {
         return myApp.getSize().getWidth() / ((double) APP_WIDTH);
      }
   }

   /**
    * Returns the current state of the game
    * 
    * @return The current pause state
    */
   public boolean isPaused() {
      return isPaused;
   }

   /**
    * Controls the state of the app
    * 
    * @param flag State to set the app to
    */
   public void setPaused(boolean flag) {
      isPaused = flag;
   }

   /**
    * Code to run the app
    */
   public void run() {
      isPaused = false;
      setupFrame();
      iR = new ImageResizer(this);
      scale = getScale();
      scale = 0.85; //Manually set Scale
      myTitleScreen.run();
   }

   /**
    * Sets up the JFrame
    */
   private void setupFrame() {
      myApp = new JFrame();

      //Set to Full Screen
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      myApp.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
      myApp.setExtendedState(JFrame.MAXIMIZED_BOTH); 
      myApp.setResizable(false);
      gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    if (gd.isFullScreenSupported()) {
        myApp.setUndecorated(true);
        gd.setFullScreenWindow(myApp);
    } 
    else 
    {
        System.err.println("Full screen not supported");
    }
      
    
      myAppPanel = new JPanel(new CardLayout());

      myTitleScreen = new TitleScreen(this);
      myTitleScreen.setFocusable(true);
      myAppPanel.add("Menu", myTitleScreen);

      myGameScreen = new GameScreen(this);
      myGameScreen.setFocusable(true);
      myAppPanel.add("Game", myGameScreen);

      myWinScreen = new WinScreen(this);
      myWinScreen.setFocusable(true);
      myAppPanel.add("End", myWinScreen);

      myApp.add(myAppPanel);
      myApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      myApp.setVisible(true);
   }

   /**
    * Switches Card Layout Screen
    * @param screenName Screen to switch to
    */
   public void screenSwitcher(String screenName)
   {
      CardLayout layout = (CardLayout) myAppPanel.getLayout();
      layout.show(myAppPanel, screenName);
   }
}