import javax.swing.*;
/**
 * Class to be able to understand multiple keystrokes and store them in an
 * ArrayList for exporting
 * 
 * @author Nicholas Lorentzen
 * @version 2019/05/05
 */
public class GameScreen extends JPanel {
    public Keyput myKeyput;
    public Players myPlayers;
    public SmashGame myGame;

    private GamePanel myGamePanel;
    //private Keyput myKeyPanel;

    public GameScreen(SmashGame game) {
        myGame = game;
        myKeyput = new Keyput(myGame);
        myPlayers = new Players(myGame, myKeyput);

        myGamePanel = new GamePanel(myGame, myPlayers);

        //myGamePanel.add()

        this.add(myGamePanel);
        this.add(myKeyput);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
}