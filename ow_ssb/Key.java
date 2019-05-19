/**
Key Class to hold pressed down keys and their lengths
@author Nicholas Lorentzen
@version 2019/05/04
*/
public class Key
{
    private static final double INCREMENT = 0.01;
    public int keyNumber;
    public double pressLength;
    public boolean keyState;

    public Key (int keyNum, boolean keyPressed) 
    {
        keyNumber = keyNum;
        keyState = keyPressed;
    }

    public double updatekey(boolean keyPressed) 
    {
        if (keyPressed && keyState)
        {
            pressLength = pressLength + INCREMENT;
        }
        else if (!(keyState)&& keyPressed) 
        {
            keyState = true;
            pressLength = 0;
            pressLength = pressLength + INCREMENT;
        }
        else if (keyState && !(keyPressed)) 
        {
            keyState = false;
        }
        return pressLength;
    }

    public double getLength()
    {
        return pressLength;
    }

    public int getNum()
    {
        return keyNumber;
    }

    public boolean getState()
    {
        return keyState;
    }

    public static void main(String[] args) {
        Key test = new Key(1, false);

        System.out.println(test.getNum());
        System.out.println(test.getState());
        System.out.println(test.getLength());
        System.out.println(test.updatekey(true));
        System.out.println(test.updatekey(false));
        System.out.println(test.updatekey(false));
        System.out.println(test.updatekey(true));
    }

}