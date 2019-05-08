import javax.swing.*;
/**
 * Class to be able to understand multiple keystrokes and store them in an
 * ArrayList for exporting
 * 
 * @author Nicholas Lorentzen
 * @version 2019/05/05
 */
public class GameScreen extends JPanel {
    /** Keyput class for handling User Input */
    public Keyput myKeyput;
    /** Players class for managing the players */
    public Players myPlayers;
    /** Passthrough of the SmashGame class to allow reference to the gamestate */
    public SmashGame myGame;

    /** JPanel to use */
    private GamePanel myGamePanel;
    //private Keyput myKeyPanel;

    /**
     * Constructs a GameScreen
     * @param game Game passthrough
     */
    public GameScreen(SmashGame game) {
        System.out.println("GameScreen.GameScreen called");

        myGame = game;
        myKeyput = new Keyput(myGame);
        myPlayers = new Players(myGame, myKeyput);

        myGamePanel = new GamePanel(myGame, myPlayers);

        //myGamePanel.add()

        this.add(myGamePanel);
        //this.add(myKeyput);
        this.setVisible(true);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
}