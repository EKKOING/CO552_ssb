/**
Class for the Pharah character inherits from player
@author Nicholas Lorentzen
@version 2019/03/25
*/
public class Pharah extends Player
{
    public Pharah()
    {
        super();

        System.out.println("Pharah Player Created");

    }

    public boolean attack()
    {
        /* Shell Method (Obviously) - Will get more later **/
        return true;
    }
    
    public static void main(String[] args) 
    {
        Pharah player1 = new Pharah();

        /* Tests all inherited methods**/
        System.out.println(player1.getHealth() + ": Current Health");
        System.out.println(player1.getDmgDone() + ": Damage Done");
        System.out.println(player1.getDmgTaken() + ": Damage Taken");
        System.out.println(player1.getPos());

        /* Tests all methods in class**/
        System.out.println(player1.attack() + ": Attack Successful");
    }
}