

import java.awt.*;
import java.applet.*;

public class manhattandistanceexample001 extends Applet implements Runnable {
 // Graphics for double buffering.
 Graphics      bufferGraphics;
    Image       offscreen;
 int mousex =     0;
 int mousey =    0;

 public void init() {
     setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
     new Thread(this).start();

 }

    public void run() {
        for(;;) { // animation loop never ends
         repaint();
         try {
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }

   public boolean mouseMove(Event e, int x, int y){
        mousex = x;
        mousey = y;
  return true;
 }

    public void update(Graphics g){
     bufferGraphics.clearRect( 0 , 0 , getSize().width , getSize().width );
        bufferGraphics.setColor( Color.red );
        bufferGraphics.drawString( "Manhattan Distance " , 10 , 10 );

  for ( int y = 0 ; y < 240/32 ; y++ ){
  for ( int x = 0 ; x < 320/32 ; x++ ){
   bufferGraphics.drawRect( x * 32 , y * 32 , 32 , 32 );
   int dist = mdist( mousex / 32 , mousey / 32 , x , y );
   bufferGraphics.drawString( "" + dist , x * 32 , y * 32 + 16 );
  }
  }

       g.drawImage(offscreen,0,0,this);
    }

 public int mdist( int x1 , int y1 , int x2 , int y2 ){
  return Math.abs( x2 - x1 ) + Math.abs( y2 - y1 );
 }

}
