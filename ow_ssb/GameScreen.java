import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.geom.*;
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
    /** Keyput class for handling User Input */
    public Keyput myKeyput;
    /** Players class for managing the players */
    public Players myPlayers;
    /** Passthrough of the SmashGame class to allow reference to the gamestate */
    public SmashGame myGame;

    /** Stage */
    public Stage myStage;

    public static final String BASE_DIRECTORY = "./graphics/ingame/topIcon/";

    /** Health Bar Overlay */
    private BufferedImage healthBar;

    /** Background UI */
    private BufferedImage background;

    /**
     * Constructs a GameScreen
     * @param game Game passthrough
     */
    public GameScreen(SmashGame game) {
        super();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("./graphics/menu/cursor.png");
		Cursor c = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), "img");
		this.setCursor (c);

        myGame = game;
    }

    public void run()
    {
        myStage = new Stage("gibraltar");
        myKeyput = new Keyput(myGame);
        myPlayers = new Players(myGame, myKeyput);

        String directoryArray[] = {"bg.png", "healthbar.png"};
        for(String fileDirectory : directoryArray)
        {
            try {
            //Create Image
            File image = new File(BASE_DIRECTORY + fileDirectory);
            BufferedImage tempImage = ImageIO.read(image);
            switch(fileDirectory)
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
    }

    /**
     * Paints screen
     * 
     * @param g Graphics object
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Rectangle2D.Double test = new Rectangle2D.Double(0, 0, 200, 200);
        //g2.fill(test);
        g2.clearRect(0, 0, myGame.APP_WIDTH, myGame.APP_HEIGHT);

        myStage.drawMe(g2);

        try {
            ArrayList<Player> paintPlayers = myPlayers.getPlayers();
            for (Player temp : paintPlayers) {temp.drawMe(g2);}
            drawUI(g2);
        } catch (ConcurrentModificationException e) {
            //Skip frame
        }
        
    }

    public void drawUI(Graphics2D g2)
    {
        //Player 1
        g2.drawImage(background, 0, 0, null);

        //Create Health Bar Inners
        for(int idx = 1; idx <= 2; idx++)
        {
            Player temp = myPlayers.findPlayer(idx);
            int rectangleLength = (int) ((200 / temp.STARTHEALTH) * temp.healthAmt);
            if(idx == 1)
            {
                Rectangle2D healthInner = new Rectangle2D.Double(11, 73, rectangleLength, 19);
                g2.setColor(Color.WHITE);
                g2.fill(healthInner);
            }
            else
            {
                Rectangle2D healthInner = new Rectangle2D.Double(1232, 73, rectangleLength, 19);
                g2.setColor(Color.WHITE);
                g2.fill(healthInner);
            }
        }

        //Player 1
        g2.drawImage(healthBar, 7, 73, null);

        //Player 2
        g2.drawImage(healthBar, 1228, 73, null);
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
