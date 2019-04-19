import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
Class for the Pharah character inherits from player
@author Nicholas Lorentzen
@version 2019/04/05
*/
public class Keyput extends JPanel
{
   public ArrayList<Key> dictionary;
   public Key keyTemp;
   private SmashGame myGame;

    public Keyput(SmashGame game)
    {
      myGame = game;
      createList();
      addKeyListener(new KeyHandler());
      requestFocusInWindow();
    }

    public void createList()
    {
      for(int idx = 0; idx < 525; idx++)
      {
         Key tempKey = new Key(idx, false);
         dictionary.add(tempKey);
      }
    }

    public Key getKey(int num)
    {
       for(Key temp : dictionary)
       {
          if(temp.keyNumber == num)
          {
             return temp;
          }
       }
       return null;
    }

    private class KeyHandler implements KeyListener
   {
      public void keyPressed(KeyEvent e)
      {         
         int code = e.getKeyCode();
         keyTemp = getKey(code);
         
      }
      
      public void keyReleased(KeyEvent e)
      {
         //nothing
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

    public static void main(String[] args) {
        
    }
}