

import java.awt.*;
import java.applet.*;

public class BlinkingUnitExample extends Applet implements Runnable{
 Graphics bufferGraphics;
 Image offscreen;

 int unitx;
 int unity;
 int unitsize = 16;
 boolean unitvisible = false;
 long blinktime;

 public void init() {
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();
  unitx = getSize().width / 2 / unitsize - 1;
  unity = getSize().height / 2 / unitsize - 1;
  new Thread(this).start();
 }

    public void run() {
        for(;;) { // animation loop never ends
         repaint();
         try {
          doblinktime();
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }

 public void doblinktime(){
  if ( System.currentTimeMillis() > blinktime ){
   if ( unitvisible == true ) {
    unitvisible=false;
   } else {
    unitvisible=true;
   }
   blinktime = System.currentTimeMillis() + 1000;
  }
 }

     public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().height);
     bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Blinking Unit Example.",10,10);
        bufferGraphics.drawString("w/s/a/d to move unit.",10,220);
  if ( unitvisible == true ){
   bufferGraphics.fillRect(unitx * unitsize , unity * unitsize , unitsize , unitsize);
  }
        g.drawImage(offscreen,0,0,this);
     }

 public void resetblink(){
   blinktime = System.currentTimeMillis() + 1000;
   unitvisible = true;
 }


 public boolean keyUp (Event e, int key){
    if( key == 119 ) // w key
        {
         if( unity > 1 ) unity--;
         resetblink();
        }
        if( key == 97 ) // a key
        {
         if( unitx > 1 ) unitx--;
         resetblink();
        }
        if( key == 100 ) // d key
        {
         if( unitx < 18 ) unitx++;
         resetblink();
        }
        if( key == 115 ) // s key
        {
         if( unity < 12 ) unity++;
         resetblink();
        }

  return true;
 }

}
