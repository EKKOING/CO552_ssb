import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
Class for the Pharah character inherits from player
@author Nicholas Lorentzen
@version 2019/04/05
*/
public class Keyput extends JPanel
{
   private SmashGame myGame;

    public Keyput(SmashGame game)
    {
      myGame = game;

      addKeyListener(new KeyHandler());
      requestFocusInWindow();

    }

    private class KeyHandler implements KeyListener
   {
      public void keyPressed(KeyEvent e)
      {         
         int code = e.getKeyCode();
         
         System.out.println(code);
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

    public static void main(String[] args) {
        
    }
}