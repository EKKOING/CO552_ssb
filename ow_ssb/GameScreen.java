import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import javax.imageio.*;

/**
 * Class to be able to understand multiple keystrokes and store them in an
 * ArrayList for exporting
 * 
 * @author Nicholas Lorentzen
 * @version 2019/05/21
 */
public class GameScreen extends JPanel {

    private static final long serialVersionUID = 3412334407377724982L;

    /** Keyput class for handling User Input */
    public Keyput myKeyput;
    /** Players class for managing the players */
    public Players myPlayers;
    /** Passthrough of the SmashGame class to allow reference to the gamestate */
    public SmashGame myGame;

    /** Stage */
    public Stage myStage;

    /** Base Directory for Images */
    public static final String BASE_DIRECTORY = "./graphics/ingame/topIcon/";

    /** Health Bar Overlay */
    private BufferedImage healthBar;

    /** Background UI */
    private BufferedImage background;

    /** Animations List */
    public ArrayList<Animator> myAnimations;

    /** Boolean State of Game */
    public boolean gameStarted;

    /** Scale */
    public double scale;

    /**
     * Constructs a GameScreen
     * 
     * @param game Game passthrough
     */
    public GameScreen(SmashGame game) {
        super();
        myGame = game;
    }

    /** Run the class */
    public void run() {
        gameStarted = false;
        myAnimations = new ArrayList<Animator>();
        scale = myGame.scale;
        FightStartAnimation startGameAnimation = new FightStartAnimation(this, scale);
        myAnimations.add(startGameAnimation);
        myStage = new Stage("gibraltar", this);
        myKeyput = new Keyput(myGame);
        myPlayers = new Players(myGame, myKeyput);

        String directoryArray[] = { "bg.png", "healthbar.png" };
        for (String fileDirectory : directoryArray) {
            try {
                // Create Image
                File image = new File(BASE_DIRECTORY + fileDirectory);
                BufferedImage tempImage = myGame.iR.resizeImage(ImageIO.read(image));
                switch (fileDirectory) {
                case "bg.png":
                    background = tempImage;
                    break;
                case "healthbar.png":
                    healthBar = tempImage;
                    break;
                default:
                    break;
                }
            } catch (IOException e) {
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
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setBackground(Color.BLACK);
        g2.clearRect(0, 0, 1920, 1080);


        myStage.drawMe(g2);

        try {
            ArrayList<Player> paintPlayers = myPlayers.getPlayers();
            for (Player temp : paintPlayers) {
                temp.drawMe(g2);
            }
        } catch (ConcurrentModificationException e) {
            // Skip frame
        }

        if (myAnimations.size() > 0) {
            try {
                for (Animator temp : myAnimations) {
                    temp.drawMe(g2);
                }
            } catch (ConcurrentModificationException e) {
                // Skip frame
            }
        }

        if(gameStarted)
        {
            drawUI(g2);
        }

        g2.clearRect((int) (SmashGame.APP_WIDTH * scale), 0, (int) (scale * 500), (int) (scale * SmashGame.APP_HEIGHT));
        g2.clearRect(0, (int) (SmashGame.APP_HEIGHT * scale), (int) (scale * (SmashGame.APP_WIDTH + 500)), (int) (scale * 500));
    }

    /**
     * Removes animation
     * @param animation animation to remove
     */
    public void removeAnimation(Animator animation)
    {
        myAnimations.remove(animation);
    }

    /**
     * Draws the UI seen in the corners
     * @param g2 Graphics Object
     */
    public void drawUI(Graphics2D g2) {
        // Player 1
        g2.drawImage(background, (int) (scale * 0), (int) (scale * 0), null);

        // Create Health Bar Inners
        for (int idx = 1; idx <= 2; idx++) {
            Player temp = myPlayers.findPlayer(idx);
            int rectangleLength = (int) (((scale * 200) / temp.getStartHP()) * temp.getHealth());
            if (idx == 1) {
                Rectangle2D healthInner = new Rectangle2D.Double(scale * 11, scale * 73, rectangleLength, scale * 19);
                g2.setColor(Color.WHITE);
                g2.fill(healthInner);
            } else {
                Rectangle2D healthInner = new Rectangle2D.Double(scale * 1232, scale * 73, rectangleLength, scale * 19);
                g2.setColor(Color.WHITE);
                g2.fill(healthInner);
            }
        }

        // Player 1
        g2.drawImage(healthBar, (int) (scale * 7), (int) (scale * 73), null);

        // Player 2
        g2.drawImage(healthBar, (int) (scale * 1228), (int) (scale * 73), null);
    }

    /**
     * Thread to handle repainting screen
     */
    private class FieldUpdater extends Thread {
        /**
         * Repaints screen on interval while not paused
         */
        public void run() {
            while (true) {
                if (!(myGame.isPaused())) {
                    repaint();
                }

                try {
                    sleep(10);
                } catch (InterruptedException ie) {

                }
            }
        }
    }
}
