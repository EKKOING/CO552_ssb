/**
Interface for player classes
@author Nicholas Lorentzen
@version 2019/04/03
*/
public interface Character
{
    public boolean attack();
    public boolean block();

    public void move(Key key);

}