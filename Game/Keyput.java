import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
Class to be able to understand multiple keystrokes and store them in an ArrayList for exporting
@author Nicholas Lorentzen
@version 2019/05/04
*/
public class Keyput extends JPanel
{
   public ArrayList<Key> dictionary;
   public Key keyTemp;
   private SmashGame myGame;
   public int lastKeyIdx;

   private static final long INCREMENT = 10;

   //private Clock keyUpdater;
   private Timer t;

    public Keyput(SmashGame game)
    {
      dictionary = new ArrayList<Key>();
      myGame = game;

      
      t = new Timer();
      t.scheduleAtFixedRate(new Clock(), (long) 0, INCREMENT);

      //createList();
      addKeyListener(new KeyHandler());
      requestFocusInWindow();
    }

    public void createList()
    {
      for(int idx = 0; idx < 600; idx++)
      {
         Key tempKey = new Key(idx, false);
         dictionary.add(tempKey);
      }
    }

    public Key getKey(int num)
    {
      
      for (int idx = 0; idx < dictionary.size(); idx++)
       {
          if(dictionary.get(idx).keyNumber == num)
          {
             lastKeyIdx = idx;
             return dictionary.get(idx);
          }
       }
       Key tempKey = new Key(num, false);
       dictionary.add(tempKey);
       return getKey(num);
    }

    private class KeyHandler implements KeyListener
   {
      public void keyPressed(KeyEvent e)
      {
         //System.out.println(e);
         int code = e.getKeyCode();
         Key temp = getKey(code);

         if(!(temp.keyState))
         {
            temp.updatekey(true);

            //Debug Code
            System.out.println("Key #" + temp.keyNumber + " has been pressed");
         }

         dictionary.set(lastKeyIdx, temp);
      }
      
      public void keyReleased(KeyEvent e)
      {
         //System.out.println(e);
         int code = e.getKeyCode();
         Key temp = getKey(code);
         temp.updatekey(false);
         dictionary.set(lastKeyIdx, temp);

         //Debug Code
         System.out.println("Key #" + temp.keyNumber + " was held down for " + temp.pressLength + " seconds");
      }
      
      public void keyTyped(KeyEvent e)
      {
         //nothing
      }
   }

   public ArrayList<Key> getKeys()
   {
      return dictionary;
   }

   private class Clock extends TimerTask
   {
      public void run()
      {
         for(Key myKey : dictionary)
         {
            myKey.updatekey(myKey.keyState);

            //Debug Code
            if(myKey.keyState) {System.out.println("Key #" + myKey.keyNumber + " is still being held down");}
         }
      }
   }

    public static void main(String[] args) {
       /** I have determined that it would take longer to write out test code than to have to set breakpoints 
        * for the debugger each time I open the program, hence there is no code to test this method here.
        * This is due to the fact that attempting to instantiate the keyput class requires creating the game class
        * which creates its own Keyput class. You also wouldn't be able to imitate keystrokes here which renders 
        * everything useless that this class has.
       */
    }
}