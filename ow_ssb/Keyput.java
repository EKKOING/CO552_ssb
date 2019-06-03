import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class to be able to understand multiple keystrokes and store them in an
 * ArrayList for exporting
 * 
 * @author Nicholas Lorentzen
 * @version 20190521
 */
public class Keyput implements KeyListener
{
   
   /** List of keys pressed */
   private ArrayList<Key> dictionary;
   /** Temporary key used in multimethod operations */
   private Key keyTemp;
   /** SmashGame passthrough for gamestate */
   private SmashGame myGame;
   /** Index of the last key used */
   private int lastKeyIdx;
   
   /** Delay between adding time to each keys press */
   private static final long INCREMENT = 10;
   
   /** Thread to update keypress lengths */
   private Timer t;
   
   /**
    * Constructor
    * 
    * @param game SmashGame passthrough for pausing
    */
   public Keyput(SmashGame game)
   {
      dictionary = new ArrayList<Key>();
      myGame = game;
      t = new Timer();
      t.scheduleAtFixedRate(new Clock(), (long) 0, INCREMENT);
   }
   
   /** Initilasizes list (Not In Use) */
   public void createList()
   {
      for (int idx = 0; idx < 600; idx++)
      {
         Key tempKey = new Key(idx, false);
         dictionary.add(tempKey);
      }
   }
   
   /**
    * Looks for a key in the dictionary and returns it If the key isnt found it
    * creates a new key with the number specified
    * 
    * @param num the keynumber of the key to look for
    * @return the Key found
    */
   public Key getKey(int num)
   {
      
      for (int idx = 0; idx < dictionary.size(); idx++)
      {
         if (dictionary.get(idx).keyNumber == num)
         {
            lastKeyIdx = idx;
            return dictionary.get(idx);
         }
      }
      Key tempKey = new Key(num, false);
      dictionary.add(tempKey);
      return getKey(num);
   }
   
   /**
    * Runs on key pressed
    * 
    * @param e KeyEvent to process
    */
   public void keyPressed(KeyEvent e)
   {
      // System.out.println(e);
      int code = e.getKeyCode();
      
      if (code == KeyEvent.VK_ESCAPE)
      {
         myGame.screenSwitcher("Menu");
         myGame.getMyTitleScreen().run();
      }
      
      Key temp = getKey(code);
      
      if (!(temp.keyState))
      {
         temp.updatekey(true);
         
         // Debug Code
         // System.out.println("Key #" + temp.keyNumber + " has been pressed");
      }
      
      dictionary.set(lastKeyIdx, temp);
   }
   
   /**
    * Runs on key release
    * 
    * @param e KeyEvent to process
    */
   public void keyReleased(KeyEvent e)
   {
      // System.out.println(e);
      int code = e.getKeyCode();
      Key temp = getKey(code);
      temp.updatekey(false);
      dictionary.set(lastKeyIdx, temp);
      
      // Debug Code
      // System.out.println("Key #" + temp.keyNumber + " was held down for " +
      // temp.pressLength + " seconds");
   }
   
   /**
    * Doesn't do anything
    * 
    * @param e KeyEvent to process
    */
   public void keyTyped(KeyEvent e)
   {
      // nothing
   }
   
   /**
    * Returns the dictionary for external processing
    * 
    * @return the dictionary of keys as an ArrayList
    */
   public ArrayList<Key> getKeys()
   {
      return dictionary;
   }
   
   
   /** Thread to handle updating Keys */
   private class Clock extends TimerTask
   {
      
      public void run()
      {
         for (Key myKey : dictionary)
         {
            myKey.updatekey(myKey.keyState);
            
            // Debug Code
            if (myKey.keyState)
            {
               // System.out.println("Key #" + myKey.keyNumber + " is still being held down");
            }
         }
      }
   }
}