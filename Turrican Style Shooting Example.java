

import java.awt.*;
import java.applet.*;

public class TurricanShootingExample001 extends Applet implements Runnable{
 Graphics bufferGraphics;
    Image offscreen;
  boolean playerMoveRight = false;
  boolean playerMoveLeft = false;
   double playershootangle = 90-45;
   boolean shootlaser = false;
   long shootingdelay;
    double xPos=100;
    double yPos=100;
    boolean isFalling = false;
    boolean isJumping = false;
 double gravity;
 double groundLevel = 100;
 boolean isShooting;
 boolean playerFacingLeft;
 boolean playerFacingRight=true;
 int numlasers = 50;
 double lasers[][] = new double[ numlasers ][ 4 ]; // 0-active,1-x,2-y,3-angle
 long lasertimeout[] = new long[ numlasers ]; // when does the laser end

 public void init() {
  setBackground(Color.black);
     offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();

     // Start the runnable thread.
  new Thread(this).start();
 }
    public void run() {
     for(;;) { // animation loop never ends
         updateplayer();
         updatelasers();
         repaint();
         try {
             Thread.sleep(16);
            }
             catch (InterruptedException e) {
             }
     }
    }
    public void updatelasers(){
     if ( shootlaser == true ) {
      if ( shootingdelay < System.currentTimeMillis() ) {
       shootingdelay = System.currentTimeMillis() + 60;
       newlaser();
      }
     }
  for ( int i = 0 ; i < numlasers ; i++ ) {
   if ( lasers[ i ][ 0 ] == 1 ) {
    lasers[ i ][ 1 ] += Math.sin( Math.toRadians( 2 * lasers[ i ][ 3 ] ));
    lasers[ i ][ 2 ] += Math.cos( Math.toRadians( 2 * lasers[ i ][ 3 ] ));
    if ( lasertimeout[ i ] < System.currentTimeMillis() ) {
     lasers[ i ][ 0 ] = 0;
    }
    if ( lasers[ i ] [ 2 ] - 16 > groundLevel ) {
     lasers[ i ][ 0 ] = 0;
    }
   }
  }
    }
    public void newlaser(){
     for ( int i = 0 ; i < numlasers ; i++ ) {
      if ( lasers[ i ][ 0 ] == 0 ) {
       lasers[ i ][ 3 ] = playershootangle;
       lasers[ i ][ 0 ] = 1;
       lasers[ i ][ 1 ] =  xPos + 8
            + 10 * Math.sin( Math.toRadians( 2 * lasers[ i ][ 3 ] )) ;
       lasers[ i ][ 2 ] = yPos + 8
            + 10 * Math.cos( Math.toRadians( 2 * lasers[ i ][ 3 ] )) ;
       lasertimeout[ i ] = System.currentTimeMillis() + 1300;
    break;
      }
     }
    }
 public void updateplayer(){

   if( playerMoveRight && xPos < getSize().width - 16 && !shootlaser )
   {
    xPos++;
   }
   if( playerMoveLeft && xPos  > 0 && !shootlaser )
   {
    xPos--;
   }
   if ( playerMoveRight && shootlaser ) {
    playershootangle -= 2;
    if ( playershootangle < 0 ) {
     playershootangle = 360;
    }
   }
   if ( playerMoveLeft && shootlaser ) {
    playershootangle += 2;
    if ( playershootangle > 360 ) {
     playershootangle = 0;
    }
   }
   if( isJumping )
   {
    yPos = (int)yPos - gravity;
    gravity = gravity - .1;
    if( gravity < 0 )
    {
     isJumping = false;
     isFalling = true;
    }
   }
   if( isFalling )
   {
    yPos = (int)yPos + gravity;
    gravity = gravity + .1;
    if( yPos  >groundLevel )
    {
     isFalling = false;
     yPos = groundLevel;
     gravity  =0;
    }
   }
 }

  public boolean keyUp (Event e, int key){
    if ( key == 97 )
        {
          playerMoveLeft = false;
        }
        if ( key == 100 )
        {
          playerMoveRight = false;
        }
  if ( key == 109 ) { // m key
   shootlaser = false;
   // when the player releases the fire key then reset the shooting angle.
   if ( playerFacingLeft ) {
    playershootangle = 90+45;
   }
   if ( playerFacingRight ) {
    playershootangle = 45;
   }

  }
    return true;
  }
  public boolean keyDown (Event e, int key){

    if ( key == 97 )
        {
         playerMoveLeft = true;
         playerFacingLeft = true;
         playerFacingRight = false;
        }
        if ( key == 100 )
        {
          playerMoveRight = true;
          playerFacingRight = true;
          playerFacingLeft = false;
        }

      if ( key == 120 ) // x character jump
      {
        if( isFalling == false && isJumping == false )
        {
            isJumping = true;
            gravity = 3;
        }
      }

  if ( key == 109 ){ // m shoot
   shootlaser = true;
  }

        System.out.println ("Charakter: " + (char)key + " Integer Value: " + key);
        return true;
  }

 public void update (Graphics g)
  {
  bufferGraphics.clearRect( 0 , 0 , getSize().width , getSize().height );
     bufferGraphics.setColor ( Color.white );
     bufferGraphics.drawString( "Turrican Shooting Example" , 10 , 10 );
     bufferGraphics.drawString( "a - left , d - right , x jump , m - shoot" , 10 , 20 );

  // draw lasers
  for ( int i = 0 ; i < numlasers ; i++ ) {
   if ( lasers[ i ][ 0 ] == 1 ) {
    bufferGraphics.fillOval( (int)lasers[ i ][ 1 ] , (int)lasers[ i ][ 2 ] , 4 , 4 );
   }
  }


     int x = (int) xPos;
     int y = (int) yPos;
     bufferGraphics.fillRect (x, y, 16, 16);



     g.drawImage(offscreen,0,0,this);

 }


}
