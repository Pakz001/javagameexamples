

import java.applet.Applet;
import java.awt.*;
public class RotatePolygonaroundcenter001 extends Applet  implements Runnable{
 Graphics bufferGraphics;
 Image offscreen;
    int[] XArray = {0, -15, 15};
    int[] YArray = {-15, 15, 15};
 double polangle;
 double polx = 320/2-15;
 double poly = 240/2-15;
 int[] xcoord = new int[3];
   int[] ycoord = new int[3];
 boolean rotleft = false;
 boolean rotright = false;
 public void init() {
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();
     makecoords(polx,poly,polangle);
  new Thread(this).start();
 }
    public void run() {
        for(;;) { // animation loop never ends
         repaint();
         try {
    if ( rotleft == true ) {
        polangle -= 3;
     if ( polangle < 0 ) {
      polangle = 360;
     }
    }
    if ( rotright == true ) {
     polangle += 3;
     if ( polangle > 360 ) {
      polangle = 0;
     }
    }
          makecoords(polx,poly,polangle);
             Thread.sleep(16);
             }
             catch (InterruptedException e) {
             }
     }
    }

 public void makecoords(double x, double y, double angle)
   {
     int  i;
  angle = Math.toRadians(angle);
     for (i=0; i<3; i++)
     {
        xcoord[i] = (int)(x+(XArray[i]*Math.sin(angle)-YArray[i]*Math.cos(angle)));
        ycoord[i] = (int)(y+(XArray[i]*Math.cos(angle)+YArray[i]*Math.sin(angle)));
     }
  }

     public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().height);
     bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Rotate polygon around center.",10,10);
  bufferGraphics.drawString("a/d to rotate.",10,237);
     bufferGraphics.fillPolygon (xcoord, ycoord, 3);

        g.drawImage(offscreen,0,0,this);
     }
  public boolean keyDown (Event e, int key){
   if(key==97) // a key
        {
         rotleft = true;
        }
        if(key==100) // d key
        {
   rotright = true;
        }

   return true;
  }

 public boolean keyUp (Event e, int key){
    if( key == 97 ) // a key
        {
          rotleft = false;
        }
        if( key == 100 ) // d key
        {
          rotright = false;
        }

  return true;
 }

}
