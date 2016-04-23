

import java.awt.*;
import java.applet.*;

public class ShooterShootingExample001 extends Applet implements Runnable {
 Graphics bufferGraphics;
    Image offscreen;
 int px; // player x coordinate.
 int py; // player y coordinate.
 int pw = 16; // player width.
 int ph = 16; // player height.
 boolean pmoveup = false; // player move up.
 boolean pmovedown = false; // player move down.
 int shoottype = 0; // shooting , 0 = no shot, 1 = type 1, 2 = type 2, 3 = type 3
 long lastshottime = 0;
 int numshots = 32;
 double shots[][] = new double[ numshots ][ 5 ]; //active, x, y, mx, my
 int shotw = 3; // shot width.
 int shoth = 3; // shot height.

 public void init() {
  setBackground(Color.black);
     offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
     px = 16;
     py = getSize().height / 2 - ph / 2;
     // Start the runnable thread.
  new Thread(this).start();
 }
    public void run() {
     for(;;) { // animation loop never ends
   moveplayer();
   playershoot();
   updateshots();
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
     bufferGraphics.drawString( "Sidescroller Shooter Shooting Example." , 10 , 10 );
  bufferGraphics.drawString( "use : w and s to move up and down." , 10 , 20 );
  bufferGraphics.drawString( "use : i , o and p to shoot." , 10 , 30 );
  bufferGraphics.setColor( Color.red );
  // draw shots.
  for ( int i = 0 ; i < numshots ; i++ ){
   if ( shots[ i ][ 0 ] == 1 ){
    bufferGraphics.fillOval( (int)shots[ i ][ 1 ] , (int)shots[ i ][ 2 ] , shotw , shoth );
   }
  }
  // draw player.
  bufferGraphics.fillRect( px , py , pw , ph );
     g.drawImage(offscreen,0,0,this);
  }
  public boolean keyUp (Event e, int key){
  if ( key == 119 ){ // w key, up
   pmoveup = false;
  }
  if ( key == 115 ){ // s key, down
   pmovedown = false;
  }
  if ( key == 105 ){ // i key, fire 1
   shoottype = 0;
  }
  if ( key == 111 ){ // o key, fire 2
   shoottype = 0;
  }
  if ( key == 112 ){ // p key, fire 3
   shoottype = 0;
  }
    return true;
  }

   public boolean keyDown (Event e, int key){
  if ( key == 119 ){ // w key, up
   pmoveup = true;
  }
  if ( key == 115 ){ // s key, down
   pmovedown = true;
  }
  if ( key == 105 ){ // i key, fire 1
   shoottype = 1;
  }
  if ( key == 111 ){ // o key, fire 2
   shoottype = 2;
  }
  if ( key == 112 ){ // p key, fire 3
   shoottype = 3;
  }
        System.out.println ("Charakter: " + (char)key + " Integer Value: " + key);
        return true;
  }

 public void moveplayer(){
  if ( pmoveup ){
   if ( py > 30 ){
    py--;
   }
  }
  if ( pmovedown ){
   if ( py < getSize().height - ph ){
    py++;
   }
  }
 }

 public void playershoot(){
  int n = -1;
  if ( lastshottime < System.currentTimeMillis() ){
   if ( shoottype == 1 ){
    n = getemptyshot();
    if ( n > -1 ){
     shots[ n ][ 0 ] = 1;
     shots[ n ][ 1 ] = px + pw;
     shots[ n ][ 2 ] = py + ph / 2;
     shots[ n ][ 3 ] = 3;
     shots[ n ][ 4 ] = 0;
    }
   }
   if ( shoottype == 2 ){
    n = getemptyshot();
    if ( n > -1 ){
     shots[ n ][ 0 ] = 1;
     shots[ n ][ 1 ] = px + pw;
     shots[ n ][ 2 ] = py + ph / 2;
     shots[ n ][ 3 ] = 3;
     shots[ n ][ 4 ] = 0;
    }
    n = getemptyshot();
    if ( n > -1 ){
     shots[ n ][ 0 ] = 1;
     shots[ n ][ 1 ] = px + pw;
     shots[ n ][ 2 ] = py + ph / 2;
     shots[ n ][ 3 ] = 3;
     shots[ n ][ 4 ] = -2;
    }
    n = getemptyshot();
    if ( n > -1 ){
     shots[ n ][ 0 ] = 1;
     shots[ n ][ 1 ] = px + pw;
     shots[ n ][ 2 ] = py + ph / 2;
     shots[ n ][ 3 ] = 3;
     shots[ n ][ 4 ] = 2;
    }
   }
   if ( shoottype == 3 ){
    n = getemptyshot();
    if ( n > -1 ){
     shots[ n ][ 0 ] = 1;
     shots[ n ][ 1 ] = px + pw;
     shots[ n ][ 2 ] = py + ph / 2;
     shots[ n ][ 3 ] = 3;
     shots[ n ][ 4 ] = 0;
    }
    n = getemptyshot();
    if ( n > -1 ){
     shots[ n ][ 0 ] = 1;
     shots[ n ][ 1 ] = px + pw;
     shots[ n ][ 2 ] = py + ph / 2;
     shots[ n ][ 3 ] = 3;
     shots[ n ][ 4 ] = -2;
    }
    n = getemptyshot();
    if ( n > -1 ){
     shots[ n ][ 0 ] = 1;
     shots[ n ][ 1 ] = px + pw;
     shots[ n ][ 2 ] = py + ph / 2;
     shots[ n ][ 3 ] = 3;
     shots[ n ][ 4 ] = 2;
    }
    n = getemptyshot();
    if ( n > -1 ){
     shots[ n ][ 0 ] = 1;
     shots[ n ][ 1 ] = px + pw;
     shots[ n ][ 2 ] = py + ph / 2;
     shots[ n ][ 3 ] = 0;
     shots[ n ][ 4 ] = -3;
    }
    n = getemptyshot();
    if ( n > -1 ){
     shots[ n ][ 0 ] = 1;
     shots[ n ][ 1 ] = px + pw;
     shots[ n ][ 2 ] = py + ph / 2;
     shots[ n ][ 3 ] = 0;
     shots[ n ][ 4 ] = 3;
    }

   }
   lastshottime = System.currentTimeMillis() + 300;
  }

 }

 public int getemptyshot(){
  for ( int i = 0 ; i < numshots ; i++ ){
   if ( shots[ i ][ 0 ] == 0 ){
    return i;
   }
  }
  return -1;
 }

 public void updateshots(){
  for ( int i = 0 ; i < numshots ; i++ ){
   if ( shots[ i ][ 0 ] == 1 ){
    // Update the shots location on the screen.
    shots[ i ][ 1 ] += shots[ i ][ 3 ];
    shots[ i ][ 2 ] += shots[ i ][ 4 ];
    // If the shots get outside of the screen then disable them.
    if ( shots[ i ][ 1 ] > getSize().width ){
     shots[ i ][ 0 ] = 0;
    }
    if ( shots[ i ][ 2 ] < 0 - shoth ){
     shots[ i ][ 0 ] = 0;
    }
    if ( shots[ i ][ 2 ] > getSize().height ){
     shots[ i ][ 0 ] = 0;
    }

   }
  }
 }

}
