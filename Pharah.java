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

        System.out.println("New Pharah created");

    }

    public boolean attack()
    {
        return true;
    }
    
    public static void main(String[] args) 
    {
        Pharah player1 = new Pharah();

        System.out.println(player1.attack());

    }
}