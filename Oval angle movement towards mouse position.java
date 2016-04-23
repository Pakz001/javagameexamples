

import java.awt.*;
import java.applet.*;

public class AngleMovement01 extends Applet implements Runnable {
 // Graphics for double buffering.
 Graphics    bufferGraphics;
    Image     offscreen;

 double    playerx    = 100;
 double    playery    = 100;
 double    playermx   = 0;
 double    playermy   = 0;
 int     playerwidth   = 16;
 int     playerheight  = 16;
 double    targetx    = 0;
 double    targety    = 0;

 public void init() {
     double angle = 0;
     setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();

     targetx = getSize().width/2;
     targety = getSize().height/2;
     angle = getangle(playerx,playery,targetx,targety);
     playermx = Math.sin(Math.toRadians(angle));
     playermy = Math.cos(Math.toRadians(angle));
  new Thread(this).start();

 }

 public void paint(Graphics g) {
 }
    public void run() {
        for(;;) { // animation loop never ends
         playerx += playermx;
         playery += playermy;

   if(playerx < 0){
    playerx = getSize().width/2;
    setdirection();
   }
   if(playery < 0){
    playery = getSize().height/2;
    setdirection();
   }
   if(playerx > getSize().width){
    playerx = getSize().width/2;
    setdirection();
   }
   if(playery > getSize().height){
    playery = getSize().height/2;
    setdirection();
   }
         repaint();
         try {
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }

 public void setdirection(){
  double angle;
  angle = getangle(playerx,playery,targetx,targety);
     playermx = 1*Math.sin(Math.toRadians(angle));
     playermy = 1*Math.cos(Math.toRadians(angle));
 }

   public boolean mouseMove(Event e, int x, int y){
  double angle;
  targetx = x;
  targety = y;
  setdirection();
  return true;
 }
    public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Angle Movement Example",10,10);
        bufferGraphics.drawString("Move mouse on Applet",10,20);
  bufferGraphics.fillOval((int)playerx,(int)playery,playerwidth,playerheight);
       g.drawImage(offscreen,0,0,this);
    }
  public boolean keyDown (Event e, int key){
   return true;
  }
 public boolean keyUp (Event e, int key){
  return true;
 }
 public double getangle( double x1, double y1, double x2 , double y2 ){
     double at = Math.toDegrees(Math.atan2( x1 - x2 , y1 - y2 ));
     at = at - 180;
     if (at > 360 ){
      at = at - 360;
     }else if ( at < 0) {
      at = at + 360;
     }
     return at;
  }
}
