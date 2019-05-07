/**
Class to hold a set of XY Coordinates
@author Nicholas Lorentzen
@version 2019/03/25
*/
public class Coord
{
    /* Int to hold the X position **/
    private int posX;
    /* Int to hold the Y position **/
    private int posY;

    /*
    Constructor for a Coord
    @param x The x coordinate
    @param y The y coordinate
    **/
    public Coord(int x, int y) 
    {
        posX = x;
        posY = y;
    }

    /*
    Returns x position
    @return the integer x postion
    **/
    public int getX() 
    {
        return posX;
    }

    /*
    Returns y position
    @return the integer y postion
    **/
    public int getY()
    {
        return posY;
    }

    /*
    Sets x position
    @param x the integer x postion
    **/
    public void setX(int x) 
    {
        posX = x;
    }

    /*
    Sets y position
    @param y the integer y postion
    **/
    public void setY(int y) 
    {
        posY = y;
    }

    public Coord checkDistance(Coord otherCoord)
    {
        return new Coord(posX - otherCoord.getX(), posY - otherCoord.getY());
    }

    /*
    Returns coord as a string
    @return the string form of a Coord
    **/
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