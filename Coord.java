/**
Class to hold a set of XY Coordinates
@author Nicholas Lorentzen
@version 2019/03/25
*/
public class Coord
{
    private int posX;
    private int posY;

    public Coord(int x, int y) 
    {
        posX = x;
        posY = y;
    }

    public int getX() 
    {
        return posX;
    }

    public int getY()
    {
        return posY;
    }

    public boolean setX(int x) 
    {
        posX = x;
    }

    public boolean setY(int y) 
    {
        posY = y;
    }

    public static void main(String[] args) 
    {
        System.out.println("java");
    }
}