
import java.awt.*;
import java.applet.*;

public class SinWavyMovementExample001 extends Applet implements Runnable {
 Graphics bufferGraphics;
    Image offscreen;

 int angle = 0;
 double blockx;
 double blocky;
 double offsety = 0;

 public void init() {
  setBackground(Color.black);
     offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
     blockx = getSize().width;
     blocky = getSize().height / 2 - 16;
     // Start the runnable thread.
  new Thread(this).start();
 }
    public void run() {
     for(;;) { // animation loop never ends
         angle+= 3;
         if ( angle > 360 ) {
          angle = 0;
         }
         offsety = Math.sin(Math.toRadians(angle)) * 12;
   blockx -= 0.5;
   if ( blockx < -32 ){
    blockx = getSize().width;
   }
         repaint();
         try {
             Thread.sleep(16);
            }
             catch (InterruptedException e) {
             }
     }
    }
 public void update (Graphics g)
  {
  bufferGraphics.clearRect( 0 , 0 , getSize().width , getSize().height );
     bufferGraphics.setColor ( Color.white );
     bufferGraphics.drawString( "Sin Wavy Movement Example" , 10 , 10 );

  bufferGraphics.fillRect ( (int)blockx , (int)blocky + (int)offsety , 32 , 32 );

     g.drawImage(offscreen,0,0,this);

 }


}
