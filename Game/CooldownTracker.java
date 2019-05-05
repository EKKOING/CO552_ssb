import java.util.Timer;
import java.util.TimerTask;

/**
Creates a cooldown timer that will set the state of a cooldown after a time period
@author Nicholas Lorentzen
@version 04/28/2019
*/

public class CooldownTracker {
    Timer timer;
    String whichCD;
    Player myPlayer;

    public CooldownTracker(Player myPlayer, long ms, String myCD) {
        timer = new Timer();
        whichCD = myCD;
        timer.schedule(new RemindTask(), ms);
	}

    class RemindTask extends TimerTask {
        public void run() {
            if(whichCD == "canWalk")
            {
                myPlayer.canWalk = true;
            }
            if(whichCD == "canAttack")
            {
                //Will update
            }
            timer.cancel();
        }
    }
}