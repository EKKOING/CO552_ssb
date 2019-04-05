/**
Class from which all characters inherit properties from
@author Nicholas Lorentzen
@version 2019/03/25
*/
public class Player
{
    /* Coord object that holds the postion of the player **/
    private Coord myPos;

    /* Int to hold the health stat **/
    private int healthAmt;
    /* Int to hold the damage done stat **/
    private int dmgDone;
    /* Int to hold the damage taken stat **/
    private int dmgTaken;

    /* Int to hold the default health to start with **/
    public static final int STARTHEALTH = 100;
    
    public Player() 
    {
        myPos = new Coord(0,0);
        healthAmt = STARTHEALTH;
        dmgDone = 0;
        dmgTaken = 0;
        System.out.println("Player Created");
    }
    
    public int getHealth() 
    {
        return healthAmt;
    }

    public Coord getPos(){
        return myPos;
    }

    public int getDmgTaken(){
        return dmgTaken;
    }

    public int getDmgDone(){
        return dmgDone;
    }

    public static void main(String[] args) 
    {
        Player player1 = new Player();

        /* Test all methods **/
        System.out.println(player1.getHealth() + ": Current Health");
        System.out.println(player1.getDmgDone() + ": Damage Done");
        System.out.println(player1.getDmgTaken() + ": Damage Taken");
        System.out.println(player1.getPos());
    }
}