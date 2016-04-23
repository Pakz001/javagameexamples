

import java.awt.*;
import java.applet.*;

public class platformerladdersexample001 extends Applet implements Runnable {
 Graphics bufferGraphics;
    Image offscreen;
 private int map[][] =  new int[][]{
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,1,2,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,1,2,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
 {1,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,2,1,1,1,1,1,1,1,2,1,0,0,1},
 {1,0,0,0,0,0,0,2,0,0,0,0,1,1,1,2,1,0,0,1},
 {1,0,0,0,1,1,1,2,1,0,0,0,0,0,0,2,0,0,0,1},
 {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,2,1,1,1},
 {1,1,1,2,1,0,0,0,0,0,0,0,0,0,0,0,2,0,0,1},
 {1,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,1},
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 };
 int     mapwidth =    20;
 int     mapheight =   15;
 int     cellwidth =   16;
 int     cellheight =   16;
 double     px =     132;
 double    py =    200;
 int     pwidth =    cellwidth/2;
 int     pheight =    cellheight;
 boolean    isjumping =   false;
 boolean    isfalling =   false;
 double    gravity =    0;
 boolean    ismovingright =  false;
 boolean    ismovingleft =   false;
 boolean    ismovingup =  false;
 boolean    ismovingdown =   false;
 double    jumpforce =   3;
 boolean    onladder =    false;

 public void init() {
  setBackground(Color.black);
     offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
  new Thread(this).start();
 }
    public void run() {
     for(;;) { // animation loop never ends
     updateplayer();
         repaint();
         try {
             Thread.sleep(16);
            }
             catch (InterruptedException e) {
            }
     }
    }

 public void update (Graphics g) {
  bufferGraphics.clearRect( 0 , 0 , getSize().width , getSize().height );
        // Draw map
        for( int y = 0 ; y < mapheight ; y++ ){
         for ( int x = 0 ; x < mapwidth ; x++){
          if( map[ y ][ x ] == 1 ){
        bufferGraphics.setColor ( Color.white );
           bufferGraphics.fillRect( x * cellwidth , y * cellheight , cellwidth , cellheight );
          }
          if( map[ y ][ x ] == 2 ){ // Draw ladder
           bufferGraphics.setColor ( new Color( 170 , 130 , 0 ) );
           bufferGraphics.fillRect( x * cellwidth , y * cellheight , cellwidth , 3 );
           bufferGraphics.fillRect( x * cellwidth , y * cellheight +6 , cellwidth , 3 );
           bufferGraphics.fillRect( x * cellwidth , y * cellheight + 12 , cellwidth , 3 );

          }
         }
        }
        // Draw player
        bufferGraphics.fillRect( (int)px , (int)py , pwidth , pheight );

     bufferGraphics.setColor ( Color.green );
     bufferGraphics.drawString( "Platformer Ladders Eample." , 10 , 10 );
       g.drawImage(offscreen,0,0,this);
  }
    public void updateplayer(){
  boolean ontheladder = laddercollision( (int)px , (int)py , pwidth , pheight );
  if ( ontheladder ) {
   if ( ismovingup ) {
    py--;
   }
  }
  if (  laddercollision( (int)px , (int)py+pheight , pwidth , 1 ) ||
     mapcollision( (int)px , (int)py+pheight , pwidth , pheight ) == false ) {
   if ( ismovingdown ) {
    py++;
   }
  }

  if ( isjumping == false && isfalling == false && ontheladder == false ){
   if( mapcollision( (int)px , (int)py+1 , pwidth , pheight ) == false ){
    isfalling = true;
    gravity = 0;
   }
  }
  if (ismovingright){
   if ( mapcollision( (int)(px + 1) , (int)py , pwidth , pheight ) == false ){
    px += 1;
   }
  }
  if (ismovingleft){
   if ( mapcollision( (int)(px - 1) , (int)py , pwidth , pheight ) == false ){
    px -= 1;
   }
  }

  if ( isfalling == true && isjumping == false ){
   for ( int i = 0 ; i < gravity ; i++ ){
    if ( mapcollision ( (int)px , (int)(py + 1) , pwidth , pheight ) == false &&
      laddercollision( (int)px , (int)py+1 , pwidth , pheight ) == false ){
     py += 1;
    }else{
     gravity = 0;
     isfalling = false;
    }
   }
   gravity += .1;
  }

  if ( isjumping == true && isfalling == false ){
   for ( int i = 0 ; i < gravity ; i++){
    if ( mapcollision ( (int)px , (int)(py - 1) , pwidth , pheight ) == false ){
     py -= 1;
     //System.out.print("still jumping : " + gravity);
    }else{
     gravity = 0;
     isfalling = true;
     isjumping = false;
    }
   }
   if( gravity < 1 ) {
    gravity = 0;
    isfalling = true;
    isjumping = false;
   }
   gravity -= .1;
  }
    }

 public boolean laddercollision( int x , int y , int width , int height ){
   int mapx = x / cellwidth;
   int mapy = y / cellheight;
   for ( int y1 = mapy - 1 ; y1 < mapy + 2 ; y1++ ){
    for ( int x1 = mapx - 1 ; x1 < mapx + 2 ; x1++ ){
     if ( x1 >= 0 && x1 < mapwidth && y1 >= 0 && y1 < mapheight ){
      if ( map[y1][x1] == 2 ){
       Rectangle rec1 = new Rectangle( x , y , width , height );
      Rectangle rec2 = new Rectangle( x1 * cellwidth,
              y1 * cellheight,
              cellwidth,
              cellheight);
      if( rec1.intersects( rec2 )) return true;
      }
     }
    }
   }
  return false;
 }

  public boolean mapcollision( int x , int y , int width , int height ){
   int mapx = x / cellwidth;
   int mapy = y / cellheight;
   for ( int y1 = mapy - 1 ; y1 < mapy + 2 ; y1++ ){
    for ( int x1 = mapx - 1 ; x1 < mapx + 2 ; x1++ ){
     if ( x1 >= 0 && x1 < mapwidth && y1 >= 0 && y1 < mapheight ){
      if ( map[y1][x1] == 1 ){
       Rectangle rec1 = new Rectangle( x , y , width , height );
      Rectangle rec2 = new Rectangle( x1 * cellwidth,
              y1 * cellheight,
              cellwidth,
              cellheight);
      if( rec1.intersects( rec2 )) return true;
      }
     }
    }
   }
  return false;
  }
  public boolean keyDown (Event e, int key){
    if( key == 97 ) // a key
        {
         ismovingleft = true;
        }
        if(key== 100) // d key
        {
          ismovingright = true;
        }
    if( key == 119 ) // w key
        {
         ismovingup = true;
        }
        if(key== 115) // s key
        {
          ismovingdown = true;
        }

      if( key == 32 ) // space bar for jump
      {
        if( isfalling == false && isjumping == false )
        {
            isjumping = true;
            gravity = jumpforce;
        }
      }

        System.out.println (" Integer Value: " + key);

   return true;
  }

 public boolean keyUp (Event e, int key){
    if( key == 97 ) // a key
        {
          ismovingleft = false;
        }
        if( key == 100 ) // d key
        {
          ismovingright = false;
        }
    if( key == 119 ) // w key
        {
          ismovingup = false;
        }
        if( key == 115 ) // s key
        {
          ismovingdown = false;
        }

  return true;
 }

}
