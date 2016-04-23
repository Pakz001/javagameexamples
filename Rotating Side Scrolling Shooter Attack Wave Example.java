

import java.awt.*;
import java.applet.*;

public class SideScrollingAttackWave001 extends Applet implements Runnable {
 Graphics bufferGraphics;
    Image offscreen;
 int numenemies = 16;
 double enemies[][] = new double[ numenemies ][ 8 ]; // active, x , y , mx , angle , radius , locx,locy

 int enemyw = 16;
 int enemyh = 16;

 public void init() {
  setBackground(Color.black);
     offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
  initiateattackwave();
     // Start the runnable thread.
  new Thread(this).start();
 }
 public void initiateattackwave(){
  // initiate the attack wave
     for ( int i = 0 ; i < 7 ; i++ ){
      enemies[ i ][ 0 ] = 1;
      enemies[ i ][ 1 ] = 0;
      enemies[ i ][ 2 ] = 0;
      enemies[ i ][ 3 ] = -.2;
      enemies[ i ][ 4 ] = i * 16;
      enemies[ i ][ 5 ] = 100;
      enemies[ i ][ 6 ] = getSize().width;
      enemies[ i ][ 7 ] = getSize().height / 2 - 8;
     }
 }
    public void run() {
     boolean noleft;
     for(;;) { // animation loop never ends

   // update the enemies
   for ( int i = 0 ; i < numenemies ; i++ ){
    if ( enemies[ i ][ 0 ] == 1 ){
     enemies[ i ][ 6 ] += enemies[ i ][ 3 ];
     enemies[ i ][ 4 ] += .5;
     if ( enemies[ i ][ 4 ] > 360 ){
      enemies[ i ][ 4 ] = 0;
     }
     enemies[ i ][ 1 ] = Math.sin(  Math.toRadians(
             enemies[ i ][ 4 ] ) )
             * enemies[ i ][ 5 ]
             + enemies[ i ][ 6 ];
     enemies[ i ][ 2 ] = Math.cos(  Math.toRadians(
             enemies[ i ][ 4 ] ) )
             * enemies[ i ][ 5 ]
             + enemies[ i ][ 7 ];

     if ( enemies[ i ][ 6 ] < -70 ){
      enemies[ i ][ 0 ] = 0;
     }
    }
   }

   // if there are no more active enemies then initiate new wave.
   noleft = true;
   for ( int i = 0 ; i < numenemies ; i++ ){
    if ( enemies[ i ][ 0 ] == 1 ){
     noleft = false;
    }
   }
         if ( noleft ){
          initiateattackwave();
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
     bufferGraphics.drawString( "Sidescroller Attack Wave Example." , 10 , 10 );
  bufferGraphics.setColor( Color.red );
  // Draw the enemies
  for ( int i = 0 ; i < numenemies ; i++ ){
   if ( enemies[ i ][ 0 ] == 1 ){
    bufferGraphics.fillRect(  (int)enemies[ i ][ 1 ] ,
           (int)enemies[ i ][ 2 ] ,
           enemyw ,
           enemyh );
   }
  }
     g.drawImage(offscreen,0,0,this);

 }


}
