

import java.awt.*;
import java.applet.*;

public class ThreeAttackWavesExample001 extends Applet implements Runnable {
 Graphics bufferGraphics;
    Image offscreen;
    int numenemies = 64;
    int enemyw = 16; // enemy width
    int enemyh = 16; // enemy height
 double enemies[][] = new double[ numenemies ][ 4 ]; // active,x,y,mx

 public void init() {
  setBackground(Color.black);
     offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
     createattackwave3();
     // Start the runnable thread.
  new Thread(this).start();
 }
    public void run() {
     for(;;) { // animation loop never ends


   if ( noenemiesareleft() == true ){
    int n = (int)( Math.random() * 4 );
    if ( n == 1 ){
     createattackwave1();
    }
    if ( n == 2 ){
     createattackwave2();
    }
    if ( n == 3 ){
     createattackwave3();
    }

   }

      moveenemies();
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
     bufferGraphics.drawString( "Sidescroller Shooter 3 attack waves Example." , 10 , 10 );

  // Draw enemies
     bufferGraphics.setColor ( Color.red );
  for ( int i = 0 ; i < numenemies ; i++ ) {
   if ( enemies[ i ][ 0 ] == 1 ){
    bufferGraphics.fillRect(  (int)enemies[ i ][ 1 ],
            (int)enemies[ i ][ 2 ],
            enemyw,
            enemyh );
   }
  }

     g.drawImage(offscreen,0,0,this);

 }

 public boolean noenemiesareleft(){
  boolean noleft = true;
  for ( int i = 0 ; i < numenemies ; i++ ){
   if ( enemies[ i ][ 0 ] == 1 ){
    noleft = false;
   }
  }
  return noleft;
 }

 public void createattackwave1(){
  int n;
  for ( int i = 0 ; i < 4 ; i++ ){
   n = findemptyenemy();
         //System.out.println ("slot : " + n );
   if ( n > -1 ){
    enemies[ n ][ 0 ] = 1;
    enemies[ n ][ 1 ] = getSize().width + 32 + 4 * enemyw - ( i * enemyw ) + ( enemyw * 3 );
    enemies[ n ][ 2 ] = i * ( enemyh * 1.5 ) + 32;
    enemies[ n ][ 3 ] = .5;
   }
  }
 }

 public void createattackwave2(){
  int n;
  for ( int i = 0 ; i < 4 ; i++ ){
   n = findemptyenemy();
         //System.out.println ("slot : " + n );
   if ( n > -1 ){
    enemies[ n ][ 0 ] = 1;
    enemies[ n ][ 1 ] = getSize().width + 32 + ( i * enemyw ) + ( enemyw * 3 );
    enemies[ n ][ 2 ] = i * ( enemyh * 1.5 ) + 132;
    enemies[ n ][ 3 ] = .5;
   }
  }
 }

 public void createattackwave3(){
  int n;
  for ( int i = 0 ; i < 4 ; i++ ){
   n = findemptyenemy();
         //System.out.println ("slot : " + n );
   if ( n > -1 ){
    enemies[ n ][ 0 ] = 1;
    enemies[ n ][ 1 ] = getSize().width + (int)(Math.random() * getSize().width / 2);
    enemies[ n ][ 2 ] = (int)(Math.random() * (getSize().height - 100 ) + 50);
    enemies[ n ][ 3 ] = .5;
   }
  }
 }


 public int findemptyenemy(){
  for ( int i = 0 ; i < numenemies ; i++ ){
   if ( enemies[ i ][ 0 ] == 0 ){
    return i;
   }
  }
  return -1;
 }

 public void moveenemies(){
  for ( int i = 0 ; i < numenemies ; i++ ){
   if ( enemies[ i ][ 0 ] == 1 ){
    enemies[ i ][ 1 ] -= enemies[ i ][ 3 ];
    if ( enemies[ i ][ 1 ] < 0 - enemyw ){
     enemies[ i ][ 0 ] = 0;
    }
   }
  }
 }


}
