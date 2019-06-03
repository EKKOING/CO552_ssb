import java.util.Timer;
import java.util.TimerTask;

/**
 * Creates a cooldown timer that will set the state of a cooldown after a time
 * period
 * 
 * @author Nicholas Lorentzen
 * @version 20190507
 */
public class CooldownTracker
{
    
    /** Timer thread */
    private Timer timer;
    /** Name of CD being tracked */
    private String whichCD;
    /** Player whom the CD belongs to */
    private Player myPlayer;
    
    /**
     * Constructs a new CD activator
     * 
     * @param tempPlayer Player to act upon
     * @param ms         Ms of CD time
     * @param myCD       CD to act upon
     */
    public CooldownTracker(Player tempPlayer, long ms, String myCD)
    {
        
        timer = new Timer();
        myPlayer = tempPlayer;
        whichCD = myCD;
        timer.schedule(new RemindTask(), ms);
    }
    
    
    /** Thread to manage cooldown */
    class RemindTask extends TimerTask
    {
        
        public void run()
        {
            switch (whichCD)
            {
                case "canWalk":
                    myPlayer.setCanWalk(true);
                    break;
                
                case "canAttack":
                    myPlayer.setCanAttack(true);
                    break;
                
                case "doubleJump":
                    myPlayer.setDoubleJump(true);
                    break;
                
                case "respawn":
                    myPlayer.respawn();
                    break;
                
                case "attacking":
                    myPlayer.setAttacking(false);
                    break;
                
                default:
                    System.err.println("No Such Cooldown Found for Input: \"" + whichCD + "\"");
                    break;
            }
            
            timer.cancel();
        }
    }
}