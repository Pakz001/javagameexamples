

import java.awt.*;
import java.applet.*;

public class BlocksShooterExample001 extends Applet implements Runnable {
 Graphics bufferGraphics;
    Image offscreen;

 int[][] blocks = new int[10][10];
 int px = 320/2; // the paddle variables.
 int py = 220;
 int bx = 0; // bullet variables.
 double by = 0;
 int ptx = 0; // paddle target variable.
 boolean isshooting = false; // if bullet is on the screen.

 public void init() {
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();
        // Initialize blocks  1 is block
  for (int y = 0 ; y < 10 ; y++){
   for (int x = 0 ; x < 10 ; x++){
    blocks[x][y] = 1;
   }
  }

  new Thread(this).start();
 }

    public void run() {
        for(;;) { // animation loop never ends
         runpaddle();
         runbullet();
         repaint();
         try {
             Thread.sleep(16);
             }
             catch (InterruptedException e) {
             }
     }
    }

    public void runpaddle(){

     if ( px < ptx ) {
      px++;
     }
     if ( px > ptx ) {
      px--;
     }
     if ( px == ptx && isshooting == false) {
      // shoot
      bx = px + 16;
      by = py;
      isshooting = true;
      // if one in three then shoot at lowest rightest block.
      // else shoot at random block.
      if ( (int)(Math.random() * 3) == 1 ) {
       for ( int zx = 0 ; zx < 10 ; zx++ ) {
        if ( blocks[zx][9] == 1 ) {
         ptx = zx*32;
        }
       }
       }else{
       ptx = (int)(Math.random()* 10) * 32;
      }
     }
     // if lowest blocks line is empty then shift blocks down by 1.
     boolean isempty = true;
     for ( int x = 0 ; x < 10 ; x++){
      if ( blocks[x][9] == 1 ) isempty = false;
     }
     if ( isempty == true ){
      for ( int y = 8 ; y > 0 ; y--){
       for ( int x = 0 ; x < 10 ; x++){
        blocks[x][y+1]=blocks[x][y];
       }
      }
      for (int x = 0 ; x < 10 ; x++){
       blocks[x][0] = 1;
      }
     }

    }

 public void runbullet(){
  // move the bullet up.
  if ( isshooting == true ) {
   by-=1;
  }
  // check collision with blocks.
  if ( isshooting == true ) {
   for ( int y = 0 ; y < 10 ; y++){
    for ( int x = 0 ; x < 10 ; x++){
     if ( blocks[x][y] == 1 ) {
      Rectangle rec1 = new Rectangle(x*32,y*12,32,12);
      Rectangle rec2 = new Rectangle(bx,(int)by,4,4);
      if(rec1.intersects(rec2)){
       isshooting = false;
       blocks[x][y] = 0;
      }
     }
    }
   }
  }
  // if bullet of the screen.
  if ( by < 0 ) {
   isshooting = false;
  }
 }

     public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().height);
     bufferGraphics.setColor(Color.white);
  // Draw Blocks
  for (int y = 0 ; y < 10 ; y++){
   for (int x = 0 ; x < 10 ; x++){
    if ( blocks[x][y] == 1 ){
     bufferGraphics.fillRect(x*32,y*12,30,10);
    }
   }
  }
  // Draw paddle
  bufferGraphics.fillRect(px,py,32,12);
   // Draw Bullet
   if ( isshooting == true ) {
    bufferGraphics.fillRect(bx,(int)by,4,4);
   }
     bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Blocks Shooter Example.",10,10);
        g.drawImage(offscreen,0,0,this);
     }


}
