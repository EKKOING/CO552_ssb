import java.util.Timer;
import java.util.TimerTask;

/**
Creates a cooldown timer that will set the state of a cooldown after a time period
@author Nicholas Lorentzen
@version 05/07/2019
*/
public class CooldownTracker {
    /** Timer thread */
    Timer timer;
    /** Name of CD being tracked */
    String whichCD;
    /** Player whom the CD belongs to */
    Player myPlayer;

    /**
     * Constructs a new CD activator
     * @param myPlayer Player to act upon
     * @param ms Ms of CD time
     * @param myCD CD to act upon
     */
    public CooldownTracker(Player myPlayer, long ms, String myCD) {
        timer = new Timer();
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
            timer.cancel();
        }
    }
}