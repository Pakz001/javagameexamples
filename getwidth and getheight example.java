package test001;
import java.awt.*;
import java.applet.*;

public class testing001 extends Applet implements Runnable {
 // Graphics for double buffering.
 Graphics      bufferGraphics;
 Image       offscreen;
 
 public void init() {
     setBackground(Color.black);
     offscreen = createImage(getWidth(),getHeight());
     bufferGraphics = offscreen.getGraphics();
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
     bufferGraphics.clearRect( 0 , 0 , getWidth() , getHeight() );
     bufferGraphics.setColor( Color.red );
     bufferGraphics.drawString( "getWidth() and getHeight()" , 10 , 10 );

     bufferGraphics.drawRect( 20 , 20 , getWidth()-40 , getHeight()-40 );
  
       g.drawImage(offscreen,0,0,this);
    }

}
