

import java.awt.*;
import java.applet.*;

public class hiscorescreenexample001 extends Applet  implements Runnable{
 Graphics bufferGraphics;
    Image offscreen;
 String[] hiscore_s = new String[10];
 int[] hiscore_i = new int[10];

 public void init() {
  setBackground(Color.black);
     offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
     // create the hiscore names.
     hiscore_s[ 0 ] = "Joe";
     hiscore_s[ 1 ] = "Bill";
     hiscore_s[ 2 ] = "Jenny";
     hiscore_s[ 3 ] = "Steve";
     hiscore_s[ 4 ] = "Jan";
     hiscore_s[ 5 ] = "Penny";
     hiscore_s[ 6 ] = "Gary";
     hiscore_s[ 7 ] = "William";
     hiscore_s[ 8 ] = "John";
     hiscore_s[ 9 ] = "Carol";
     // create the hiscore scores.
     hiscore_i[ 0 ] = 100000;
     hiscore_i[ 1 ] =  90000;
     hiscore_i[ 2 ] =  80000;
     hiscore_i[ 3 ] =  70000;
     hiscore_i[ 4 ] =  60000;
     hiscore_i[ 5 ] =  50000;
     hiscore_i[ 6 ] =  40000;
     hiscore_i[ 7 ] =  30000;
     hiscore_i[ 8 ] =  20000;
     hiscore_i[ 9 ] =  10000;
     // Start the runnable thread.
  new Thread(this).start();
 }
    public void run() {
     for(;;) { // animation loop never ends
         repaint();
         try {
             Thread.sleep(16);
            }
             catch (InterruptedException e) {
             }
     }
    }
    public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("HiScore Screen Example",10,10);
        bufferGraphics.setColor(Color.white);
  // Draw the hiscore text to screen.
  for ( int i = 0 ; i < 10 ; i++ ) {
   bufferGraphics.drawString(hiscore_s[i],90,i*12+70);
   bufferGraphics.drawString(""+hiscore_i[i],190,i*12+70);
  }
  // Draw the Graphics buffer
       g.drawImage(offscreen,0,0,this);
    }


}
