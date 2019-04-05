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

    public void setX(int x) 
    {
        posX = x;
    }

    public void setY(int y) 
    {
        posY = y;
    }

    public String toString(){
        return "Current Position (" + posX + "," + posY + ")";
    }
    public static void main(String[] args) 
    {
        Coord where = new Coord(1,1);

        /* Testing Code **/
        System.out.println(where.getX());
        System.out.println(where.getY());

        where.setX(2);
        where.setY(3);

        System.out.println(where.getX());
        System.out.println(where.getY());

        System.out.println(where);
    }
}