import java.awt.*;
import javax.swing.*;

/**
 * Class to run the Super Smash Bros Game
 * 
 * @author Nicholas Lorentzen
 * @version 20190602
 */
public class SmashGame
{
   
   /** App Width */
   public static final int APP_WIDTH = 1440;
   /** App Height */
   public static final int APP_HEIGHT = 900;
   
   /**
    * Boolean for what happens at boundaries If True, Characters Wrap, otherwise
    * Characters bounce
    */
   public static final boolean MOTION_WRAP = false;
   
   /** Respawns in Random Location to prevent Spawncamping */
   public static final boolean NO_SPAWNCAMPING = true;
   
   /** Int for number of lives given to players */
   public static final int NUM_LIVES = 3;
   
   /** JFrame that holds the entirety of the game */
   private JFrame myApp;
   
   /** Screen for when the game is running */
   private GameScreen myGameScreen;
   
   /** TitleScreen */
   private TitleScreen myTitleScreen;
   
   /** Game End Screen */
   private WinScreen myWinScreen;
   
   /** App panel */
   private JPanel myAppPanel;
   
   /** Stage to Play */
   private int stage;
   
   /** Player 1 Character */
   private int player1;
   
   /** Player 2 Character */
   private int player2;
   
   /** Master pause state */
   private boolean isPaused;
   
   /** Scale */
   private double scale;
   
   /** Monitor Running On */
   private GraphicsDevice gd;
   
   public static void main(String[] args)
   {
      SmashGame app = new SmashGame();
      app.run();
   }
   
   /**
    * Gets and returns the scale modifier
    * 
    * @return the scale modifier as a double
    */
   public double getScale()
   {
      if (myApp.getSize().getWidth() / (double) APP_WIDTH > myApp.getSize().getHeight() / (double) APP_HEIGHT)
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
   public boolean isPaused()
   {
      return isPaused;
   }
   
   /**
    * Controls the state of the app
    * 
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
      scale = getScale();
      //scale = 0.85; // Manually set Scale
      myTitleScreen.run();
   }
   
   /**
    * Sets up the JFrame
    */
   private void setupFrame()
   {
      myApp = new JFrame();
      
      // Set to Full Screen
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      myApp.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
      myApp.setExtendedState(JFrame.MAXIMIZED_BOTH);
      myApp.setResizable(false);
      gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
      if (gd.isFullScreenSupported())
      {
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
    * 
    * @param screenName Screen to switch to
    */
   public void screenSwitcher(String screenName)
   {
      CardLayout layout = (CardLayout) myAppPanel.getLayout();
      layout.show(myAppPanel, screenName);
   }
   
   public GameScreen getMyGameScreen()
   {
      return myGameScreen;
   }
   
   public void setMyGameScreen(GameScreen myGameScreen)
   {
      this.myGameScreen = myGameScreen;
   }
   
   public TitleScreen getMyTitleScreen()
   {
      return myTitleScreen;
   }
   
   public void setMyTitleScreen(TitleScreen myTitleScreen)
   {
      this.myTitleScreen = myTitleScreen;
   }
   
   public WinScreen getMyWinScreen()
   {
      return myWinScreen;
   }
   
   public void setMyWinScreen(WinScreen myWinScreen)
   {
      this.myWinScreen = myWinScreen;
   }
   
   public int getStage()
   {
      return stage;
   }
   
   public void setStage(int stage)
   {
      this.stage = stage;
   }
   
   public int getPlayer1()
   {
      return player1;
   }
   
   public void setPlayer1(int player1)
   {
      this.player1 = player1;
   }
   
   public int getPlayer2()
   {
      return player2;
   }
   
   public void setPlayer2(int player2)
   {
      this.player2 = player2;
   }
   
   public void setScale(double scale)
   {
      this.scale = scale;
   }
}