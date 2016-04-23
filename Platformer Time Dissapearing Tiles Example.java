


import java.awt.*;
import java.applet.*;

public class tdtilesexample001 extends Applet implements Runnable {
 Graphics     bufferGraphics;
    Image      offscreen;
 private int map[][] =  new int[][]{
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,1,1,1,2,2,2,2,2,2,2,1,1,1},
 {1,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,2,2,1,1,1,1,1,1,1,1,1,1,1},
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
 int     numtdt =   32;
 int[][]    tdt =    new int[ numtdt ][ 3 ]; // active , x , y , timeout
 long[]    tdttimeout =  new long[ numtdt ];

 public void init() {
  setBackground(Color.black);
     offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
     // read time dissapearing tiles
     for ( int y = 0 ; y < mapheight ; y++ ){
     for ( int x = 0 ; x < mapwidth ; x++ ){
      if ( map[ y ][ x ] == 2 ){
       int n = freetdt();
       tdt[ n ][ 0 ] = 1;
       tdt[ n ][ 1 ] = x;
       tdt[ n ][ 2 ] = y;
       tdttimeout[ n ] = -1;
       map[ y ][ x ] = 0;
      }
     }
     }
  new Thread(this).start();
 }

 public int freetdt(){
  for ( int i = 0 ; i < numtdt ; i++ ){
   if ( tdt[ i ][ 0 ] == 0 ){
    return i;
   }
  }
  return -1;
 }

    public void run() {
     for(;;) { // animation loop never ends
         repaint();
         try {
       updateplayer();
    updatetdt();
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

         }
        }
        // Draw time dissapearing tiles
        bufferGraphics.setColor(Color.yellow);
        for ( int i = 0 ; i < numtdt ; i++ ){
         if ( tdt[ i ][ 0 ] == 1 ){
          bufferGraphics.fillRect(  tdt[ i ][ 1 ] * cellwidth ,
                 tdt[ i ][ 2 ] * cellheight ,
                 cellwidth ,
                 cellheight );
         }
        }
        // Draw player
        bufferGraphics.setColor(Color.red);
        bufferGraphics.fillRect( (int)px , (int)py , pwidth , pheight );

        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Platformer Time Dissapearing tiles Example.",10,10);
        bufferGraphics.drawString("a - left, d - right, space - jump.",10,240);

       g.drawImage(offscreen,0,0,this);
    }

    public void updatetdt(){
     for ( int i = 0 ; i < numtdt ; i++ ){
      if ( tdt[ i ][ 0 ] == 1 ){
       if ( tdtcollision( (int)px , (int)py + 1 ) == i ){
        tdttimeout[ i ] = System.currentTimeMillis() + 2000;
       }
      }
     }
     for ( int i = 0 ; i < numtdt ; i++ ){
      if ( tdttimeout[ i ] > -1 ){
       if ( tdttimeout[ i ] < System.currentTimeMillis() ){
        tdttimeout[ i ] = -1;
        tdt[ i ][ 0 ] = -1;
       }
      }
     }
    }

 public void updateplayer(){

  if ( isjumping == false && isfalling == false ){
   if(  mapcollision( (int)px , (int)py+1 , pwidth , pheight ) == false &&
     tdtcollision( (int)px , (int)py + 1 ) == -1 ){
    isfalling = true;
    gravity = 0;
   }
  }
  if (ismovingright){
   if (  mapcollision( (int)(px + 1) , (int)py , pwidth , pheight ) == false &&
     tdtcollision( (int)px + 1 , (int)py ) == -1 ){
    px += 1;
   }
  }
  if (ismovingleft){
   if (  mapcollision( (int)(px - 1) , (int)py , pwidth , pheight ) == false &&
     tdtcollision( (int)px - 1 , (int)py ) == -1 ){
    px -= 1;
   }
  }

  if ( isfalling == true && isjumping == false ){
   for ( int i = 0 ; i < gravity ; i++ ){
    if (  mapcollision ( (int)px , (int)(py + 1) , pwidth , pheight ) == false &&
      tdtcollision( (int)px , (int)py + 1) == -1 ){
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
    if (  mapcollision ( (int)px , (int)(py - 1) , pwidth , pheight ) == false &&
      tdtcollision( (int)px , (int)py - 1 ) == -1 ){
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

    public int tdtcollision( int x , int y ){
     for ( int i = 0 ; i < numtdt ; i++ ){
      if ( tdt[ i ][ 0 ] == 1 ){
     Rectangle rec1 = new Rectangle( x , y , pwidth , pheight );
    Rectangle rec2 = new Rectangle( tdt[ i ][ 1 ] * cellwidth,
            tdt[ i ][ 2 ] * cellheight,
            cellwidth,
            cellheight);
    if( rec1.intersects( rec2 )) return i;

      }
     }
     return -1;
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
  return true;
 }



}
