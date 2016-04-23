

import java.awt.*;
import java.applet.*;

public class MissileCommanderExample001 extends Applet implements Runnable{
 Graphics bufferGraphics;
    Image offscreen;
    boolean missilefired;
 int missileangle;
 double mx1; // missile variables.
 double my1;
 double mx2;
 double my2;
 long mld; // missile line point 2 delay.
 int mtx; // missile target variables.
 int mty;
 boolean explosionactive;
 int ex; // Explosion variables.
 int ey;
 int er; // explosion radius.
 boolean explosiongrowing;
 long edelay;
 long turnoffmissiletime;
 boolean turnoffmissile;
 public void init() {
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();
  new Thread(this).start();
 }
    public void run() {
        for(;;) { // animation loop never ends
         runmissile();
         repaint();
         try {
             Thread.sleep(16);
             }
             catch (InterruptedException e) {
             }
     }
    }
    public void runmissile(){
     if ( missilefired == true ){
      if ( explosionactive == false ) {
        Rectangle rec1 = new Rectangle((int)mx1-2,(int)my1-2,4,4);
    Rectangle rec2 = new Rectangle(mtx-2,mty-2,4,4);
    if(rec1.intersects(rec2)){ // if missile reached target coordinates
     explosionactive = true;
     explosiongrowing = true;
     ex = mtx;
     ey = mty;
     er = 0;
     edelay = System.currentTimeMillis() + 10;
     turnoffmissile = true;
     turnoffmissiletime = System.currentTimeMillis() + 1000;
    }
      }
      if ( explosionactive == false ) {
       mx1 += 1 * Math.sin(Math.toRadians(missileangle));
       my1 += 1 * Math.cos(Math.toRadians(missileangle));
      }
      if ( mld < System.currentTimeMillis() ) {
       mx2 += 1 * Math.sin(Math.toRadians(missileangle));
       my2 += 1 * Math.cos(Math.toRadians(missileangle));
      }
     }
     // code for the explosion.
     if ( explosionactive == true ) {
      if ( edelay < System.currentTimeMillis() ) {
       edelay = System.currentTimeMillis() + 10;
       if ( explosiongrowing == true ) {
        er++;
        if ( er > 48 ) {
         explosiongrowing = false;
        }
       }else{
        er--;
        if ( er < 1 ) {
         explosionactive = false;
        }
       }
      }
     }

     // code for turning off missile
     if ( turnoffmissile == true ) {
      if ( turnoffmissiletime < System.currentTimeMillis() ) {
       missilefired = false;
       turnoffmissile = false;
      }
     }
    }
 public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().height);
     bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Missile Commander Example.",10,10);
        bufferGraphics.drawString("Press mouse to launch missile.",10,220);
        bufferGraphics.setColor(Color.white);
        bufferGraphics.drawLine(0,200,320,200);
      // Draw the missile
      if ( missilefired == true ) {
       bufferGraphics.drawLine((int)mx1,(int)my1,(int)mx2,(int)my2);
      }
      if ( explosionactive == true ) {
       bufferGraphics.fillOval(ex-er/2,ey-er/2,er,er);
      }
        g.drawImage(offscreen,0,0,this);
     }
   public boolean mouseMove(Event e, int x, int y){
  return true;
 }
  public boolean mouseUp (Event e, int x, int y) {
        if (e.modifiers == Event.META_MASK) {
            //info=("Right Button Pressed");
        } else if (e.modifiers == Event.ALT_MASK) {
            //info=("Middle Button Pressed");
        } else {
            //info=("Left Button Pressed");
            if ( missilefired == false && y < 190 ) {
    mtx = x;
    mty = y;
          missileangle = (int)Math.toDegrees(Math.atan2( 160 - x , 200 - y ));
     missileangle = missileangle - 180;
     if (missileangle > 360 ){
     missileangle = missileangle - 360;
     }else if ( missileangle < 0) {
      missileangle = missileangle + 360;
     }

             missilefired = true;
    mld = System.currentTimeMillis() + 1000;
    mx1 = 320/2;
    my1 = 200;
    mx2 = 320/2;
    my2 = 200;
            }
        }
        return true;
    }


}
