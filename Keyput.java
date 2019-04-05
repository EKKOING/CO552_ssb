import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
Class for the Pharah character inherits from player
@author Nicholas Lorentzen
@version 2019/04/05
*/
public class Keyput
{
    public Keyput()
    {
        KeyHandler kh = new KeyHandler();
        //this.addKeyListener(kh);
        //this.setFocusable(true);
        //this.requestFocusInWindow();
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
        Keyput test = new Keyput();
    }
}