import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Game Screen Class for when the game is actually running
 * 
 * @author Nicholas Lorentzen
 * @version 20190602
 */
public class GameScreen extends JPanel
{
    
    /** Amount to Offset Inner Part of the Healthbar by */
    private static final double HEALTHBAR_OUTER_OFFSET = 5;
    /** Healthbar Height */
    private static final int HEALTHBAR_HEIGHT = 19;
    /** X Location of Player 2 Healthbar */
    private static final int HEALTHBAR_PLAYER_2_X = 1232;
    /** X Location of Player 1 Healthbar */
    private static final int HEALTHBAR_PLAYER_1_X = 11;
    /** Y Location of Healthbars */
    private static final int HEALTHBAR_Y_LOCATION = 73;
    /** Length of Healthbars */
    private static final int HEALTHBAR_LENGTH = 200;
    
    /** Area to Clean Up on Edges of Screen */
    private static final int AREA_TO_CLEAR = 500;
    
    /** Base Directory for Images */
    public static final String BASE_DIRECTORY = "./graphics/ingame/topIcon/";
    
    /** Keyput class for handling User Input */
    private Keyput myKeyput;
    /** Players class for managing the players */
    private Players myPlayers;
    /** Current Stage */
    private Stage myStage;
    
    /** Passthrough of the SmashGame class to allow reference to the gamestate */
    private SmashGame myGame;
    
    /** Health Bar Overlay */
    private BufferedImage healthBar;
    
    /** Background UI */
    private BufferedImage background;
    
    /** Animations List */
    private ArrayList<Animator> myAnimations;
    
    /** Boolean State of Game */
    private boolean gameStarted;
    
    /** Scale */
    private double scale;
    
    /**
     * Constructs a GameScreen
     * 
     * @param game Game passthrough
     */
    public GameScreen(SmashGame game)
    {
        super();
        myGame = game;
    }
    
    /** Run the class */
    public void run()
    {
        gameStarted = false;
        myAnimations = new ArrayList<Animator>();
        myAnimations.clear();
        scale = myGame.getScale();
        FightStartAnimation startGameAnimation = new FightStartAnimation(this, scale);
        myAnimations.add(startGameAnimation);
        myStage = new Stage("gibraltar", this);
        myKeyput = new Keyput(myGame);
        myPlayers = new Players(myGame, myKeyput);
        
        String directoryArray[] = { "bg.png", "healthbar.png" };
        for (String fileDirectory : directoryArray)
        {
            try
            {
                // Create Image
                File image = new File(BASE_DIRECTORY + fileDirectory);
                BufferedImage tempImage = ImageResizer.resizeImage(ImageIO.read(image), scale);
                switch (fileDirectory)
                {
                    case "bg.png":
                        background = tempImage;
                        break;
                    case "healthbar.png":
                        healthBar = tempImage;
                        break;
                    default:
                        break;
                }
            }
            catch (IOException e)
            {
                System.err.println("Directory: " + fileDirectory + " - Does Not Exist");
            }
        }
        
        this.addKeyListener(myKeyput);
        this.setVisible(true);
        this.setFocusable(true);
        this.requestFocusInWindow();
        FieldUpdater updater = new FieldUpdater();
        updater.start();
        startGameAnimation.play();
    }
    
    /**
     * Starts game
     */
    public void startGame()
    {
        myPlayers.StartPlayers();
        gameStarted = true;
    }
    
    /**
     * Paints screen
     * 
     * @param g Graphics object
     */
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setBackground(Color.BLACK);
        g2.clearRect(0, 0, 1920, 1080);
        
        myStage.drawMe(g2);
        
        try
        {
            ArrayList<Player> paintPlayers = myPlayers.getPlayers();
            for (Player temp : paintPlayers)
            { temp.drawMe(g2); }
        }
        catch (ConcurrentModificationException e)
        {
            // Skip frame
        }
        
        if (myAnimations.size() > 0)
        {
            try
            {
                for (Animator temp : myAnimations)
                { temp.drawMe(g2); }
            }
            catch (ConcurrentModificationException e)
            {
                // Skip frame
            }
        }
        
        if (gameStarted)
        { drawUI(g2); }
        
        g2.clearRect((int) (SmashGame.APP_WIDTH * scale), 0, (int) (scale * AREA_TO_CLEAR),
            (int) (scale * SmashGame.APP_HEIGHT));
        g2.clearRect(0, (int) (SmashGame.APP_HEIGHT * scale), (int) (scale * (SmashGame.APP_WIDTH + AREA_TO_CLEAR)),
            (int) (scale * AREA_TO_CLEAR));
    }
    
    /**
     * Removes animation
     * 
     * @param animation animation to remove
     */
    public void removeAnimation(Animator animation)
    {
        myAnimations.remove(animation);
    }
    
    /**
     * Draws the UI seen in the corners
     * 
     * @param g2 Graphics Object
     */
    public void drawUI(Graphics2D g2)
    {
        // Player 1
        g2.drawImage(background, (int) (scale * 0), (int) (scale * 0), null);
        
        // Create Health Bar Inners
        for (int idx = 1; idx <= 2; idx++)
        {
            Player temp = myPlayers.findPlayer(idx);
            int rectangleLength = (int) (((scale * HEALTHBAR_LENGTH) / temp.getStartHP()) * temp.getHealth());
            if (idx == 1)
            {
                Rectangle2D healthInner = new Rectangle2D.Double(
                    scale * (HEALTHBAR_PLAYER_1_X + HEALTHBAR_OUTER_OFFSET), scale * HEALTHBAR_Y_LOCATION,
                    rectangleLength, scale * HEALTHBAR_HEIGHT);
                g2.setColor(Color.WHITE);
                g2.fill(healthInner);
            }
            else
            {
                Rectangle2D healthInner = new Rectangle2D.Double(
                    scale * (HEALTHBAR_PLAYER_2_X + HEALTHBAR_OUTER_OFFSET), scale * HEALTHBAR_Y_LOCATION,
                    rectangleLength, scale * HEALTHBAR_HEIGHT);
                g2.setColor(Color.WHITE);
                g2.fill(healthInner);
            }
        }
        
        // Player 1
        g2.drawImage(healthBar, (int) (scale * HEALTHBAR_PLAYER_1_X), (int) (scale * HEALTHBAR_Y_LOCATION), null);
        
        // Player 2
        g2.drawImage(healthBar, (int) (scale * HEALTHBAR_PLAYER_2_X), (int) (scale * HEALTHBAR_Y_LOCATION), null);
    }
    
    
    /**
     * Thread to handle repainting screen
     */
    private class FieldUpdater extends Thread
    {
        
        /**
         * Repaints screen on interval while not paused
         */
        public void run()
        {
            while (true)
            {
                if (!(myGame.isPaused()))
                { repaint(); }
                
                try
                {
                    sleep(10);
                }
                catch (InterruptedException ie)
                {
                    
                }
            }
        }
    }
    
    public SmashGame getMyGame()
    {
        return myGame;
    }
    
    public void setMyGame(SmashGame myGame)
    {
        this.myGame = myGame;
    }
    
    public ArrayList<Animator> getMyAnimations()
    {
        return myAnimations;
    }
    
    public void setMyAnimations(ArrayList<Animator> myAnimations)
    {
        this.myAnimations = myAnimations;
    }
}
