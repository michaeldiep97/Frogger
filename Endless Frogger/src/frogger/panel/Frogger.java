package frogger.panel;

import javax.swing.*;

public class Frogger
{
   //-----------------------------------------------------------------
   //  Displays the main frame of the program.
   //-----------------------------------------------------------------
   public static void main (String[] args)
   {
      JFrame frame = new JFrame ("Frogger");
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

      frame.getContentPane().add(new GamePanel());
      frame.pack();
      frame.setVisible(true);
   }
}