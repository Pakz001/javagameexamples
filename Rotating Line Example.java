

import java.awt.*;
import java.applet.*;

public class RotatingLineExample extends Applet implements Runnable{

 Graphics bufferGraphics;
    Image offscreen;
 double x1;
 double y1;
 double x2;
 double y2;
 int angle;

 public void init() {
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();
  x1 = getSize().width / 2;
  y1 = getSize().height / 2;
  new Thread(this).start();

 }

 public void rotateline(){
  x2 = Math.cos(Math.toRadians(angle)) * 100 + x1;
  y2 = Math.sin(Math.toRadians(angle)) * 100 + y1;
  angle++;
  if ( angle > 360 ) angle = 0;
 }

    public void run() {
        for(;;) { // animation loop never ends
   rotateline();
         repaint();
         try {
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }

     public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().height);
     bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Rotating Line Example.",10,10);
  bufferGraphics.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
        g.drawImage(offscreen,0,0,this);
     }

}
