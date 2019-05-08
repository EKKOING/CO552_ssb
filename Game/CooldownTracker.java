import java.util.Timer;
import java.util.TimerTask;

/**
Creates a cooldown timer that will set the state of a cooldown after a time period
@author Nicholas Lorentzen
@version 05/07/2019
*/
public class CooldownTracker {
    /** Timer thread */
    public Timer timer;
    /** Name of CD being tracked */
    public String whichCD;
    /** Player whom the CD belongs to */
    public Player myPlayer;

    /**
     * Constructs a new CD activator
     * @param tempPlayer Player to act upon
     * @param ms Ms of CD time
     * @param myCD CD to act upon
     */
    public CooldownTracker(Player tempPlayer, long ms, String myCD) {

        timer = new Timer();
        myPlayer = tempPlayer;
        whichCD = myCD;
        timer.schedule(new RemindTask(), ms);
	}

    /** Thread to manage cooldown */
    class RemindTask extends TimerTask {
        public void run() {
            if(whichCD == "canWalk")
            {
                myPlayer.canWalk = true;
            }
            if(whichCD == "canAttack")
            {
                myPlayer.canAttack = true;
            }
            if(whichCD == "respawn")
            {
                myPlayer.healthAmt = myPlayer.STARTHEALTH;
            }
            timer.cancel();
        }
    }
}