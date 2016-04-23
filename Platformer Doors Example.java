

import java.awt.*;
import java.applet.*;

public class platformerdoorsexample001 extends Applet implements Runnable {
 Graphics     bufferGraphics;
    Image      offscreen;
 private int map[][] =  new int[][]{
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,1},
 {1,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1},
 {1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 };
 int mapwidth = 20;
 int mapheight = 15;
 int cellwidth = 16;
 int cellheight = 16;
 double     px =     132;
 double    py =    200;
 int     pwidth =    cellwidth/2;
 int     pheight =    cellheight;
 boolean    isjumping =   false;
 boolean    isfalling =   false;
 double    gravity =    0;
 boolean    ismovingright =  false;
 boolean    ismovingleft =   false;
 double    jumpforce =   3;
 int     numdoors =   10;
 private int[][] door = new int[ numdoors ][ 4 ]; // door ] active , x , y , teleport_to

 public void init() {
  setBackground(Color.black);
     offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
     // Door 1
     door[ 0 ][ 0 ] = 1;
     door[ 0 ][ 1 ] = 14;
     door[ 0 ][ 2 ] = 13;
     door[ 0 ][ 3 ] = 1;
     // Door 2
     door[ 1 ][ 0 ] = 1;
     door[ 1 ][ 1 ] = 15;
     door[ 1 ][ 2 ] = 10;
     door[ 1 ][ 3 ] = 0;
     // Door 3
     door[ 2 ][ 0 ] = 1;
     door[ 2 ][ 1 ] = 10;
     door[ 2 ][ 2 ] = 10;
     door[ 2 ][ 3 ] = 3;
     // Door 4
     door[ 3 ][ 0 ] = 1;
     door[ 3 ][ 1 ] = 14;
     door[ 3 ][ 2 ] = 3;
     door[ 3 ][ 3 ] = 2;
     //
  new Thread(this).start();
 }
    public void run() {
     for(;;) { // animation loop never ends
         repaint();
         try {
       updateplayer();
             Thread.sleep(16);
            }
             catch (InterruptedException e) {
             }
     }
    }
    public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        // Draw map
        for( int y = 0 ; y < mapheight ; y++ ){
         for ( int x = 0 ; x < mapwidth ; x++){
          if( map[y][x] == 1 ){
           bufferGraphics.setColor(Color.white);
           bufferGraphics.fillRect( x * cellwidth , y * cellheight , cellwidth , cellheight );
          }
          if( map[y][x] == 2 ){
           bufferGraphics.setColor(Color.yellow);
           bufferGraphics.fillRect( x * cellwidth , y * cellheight , cellwidth , cellheight );
          }

         }
        }
        // Draw doors
        bufferGraphics.setColor(Color.yellow);
        for ( int i = 0 ; i < numdoors ; i++ ){
         if ( door[ i ][ 0 ] == 1 ){
          bufferGraphics.fillRect(  door[ i ][ 1 ] * cellwidth ,
                 door[ i ][ 2 ] * cellheight ,
                 cellwidth ,
                 cellheight );
         }
        }
        // Draw player
        bufferGraphics.setColor(Color.red);
        bufferGraphics.fillRect( (int)px , (int)py , pwidth , pheight );

        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Platformer Doors Example.",10,10);
        bufferGraphics.drawString("a - left, d - right, space - jump, w - enter door.",10,240);

       g.drawImage(offscreen,0,0,this);
    }
    public void updateplayer(){

  if ( isjumping == false && isfalling == false ){
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
    if ( mapcollision ( (int)px , (int)(py + 1) , pwidth , pheight ) == false ){
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

 public Integer doorcollision( int x , int y , int w , int h ){
  int returnvalue = -1;
  for ( int i = 0 ; i < numdoors ; i++ ){
   if ( door[ i ][ 0 ] == 1 ){
    Rectangle rec1 = new Rectangle( x , y , w , h );
    Rectangle rec2 = new Rectangle( door[ i ][ 1 ]  * cellwidth,
            door[ i ][ 2 ]  * cellheight,
            cellwidth,
            cellheight);
    if( rec1.intersects( rec2 )) return i;
   }
  }
  return returnvalue;
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
   int thedoor = -1;
   thedoor = doorcollision( (int)px , (int)py , pwidth , pheight );
   if ( thedoor > -1 ){
    int tdoor = door[ thedoor ][ 3 ];
    px = door[ tdoor ][ 1 ] * cellwidth + cellwidth / 4;
    py = door[ tdoor ][ 2 ] * cellheight;
   }
  }
  return true;
 }


}
