

import java.awt.*;
import java.applet.*;

public class CopperBarExample001 extends Applet {

 public void init() {
  // Set the applet background color to black.
  setBackground(Color.black);
 }

 public void paint(Graphics g) {
  g.setColor( Color.white );
  g.drawString( "Copperbar Example" , 10, 10 );
  int y = 50;
  double col = 0;
  while ( y < 100 ){ // draw the first 50 lines of the copperbar , dark to light
   g.setColor( new Color( 0 , 0 , (int)col ) );
   col += 255 / 50;
   g.drawLine( 0 , y , getSize().width , y );
   y++;
  }
  while ( y < 150 ){ // draw the last 50 lines of the copperbar, light to dark.
   g.setColor( new Color( 0 , 0 , (int)col ) );
   col -= 255 / 50;
   g.drawLine( 0 , y , getSize().width , y );
   y++;
  }

 }


}

