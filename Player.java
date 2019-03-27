/**
Class from which all characters inherit properties from
@author Nicholas Lorentzen
@version 2019/03/25
*/
public class Player
{
    private Coord myPos;
    private int healthAmt;
    private int dmgDone;
    private int dmgTaken;

    public static final int STARTHEALTH = 100;
    
    public void Player() 
    {
        Coord myPos = new Coord(0,0);
        healthAmt = STARTHEALTH;
        dmgDone = 0;
        dmgTaken = 0;
        System.out.println("Player Created");
    }
    
    public int getHealth() 
    {
        return healthAmt;
    }
    public static void main(String[] args) 
    {
        Player player1 = new Player();

        System.out.println(player1.getHealth());
        
    }
}