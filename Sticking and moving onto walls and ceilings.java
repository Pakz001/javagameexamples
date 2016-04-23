

import java.awt.*;
import java.applet.*;

public class Stickingtowallsandceilings extends Applet implements Runnable {
 // Graphics for double buffering.
 Graphics    bufferGraphics;
    Image     offscreen;
 private short map[][]={
      {1,1,1,1,1,1,1,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,0,1,0,0,0,1},
      {1,0,0,0,0,0,0,0,1,0,0,0,1},
      {1,0,0,0,0,0,0,0,1,0,0,0,1},
      {1,0,0,0,0,0,0,0,1,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,1,0,0,0,0,0,1},
      {1,0,0,0,0,0,1,0,0,0,0,0,1},
      {1,0,0,0,0,0,1,0,0,0,0,0,1},
      {1,0,0,1,0,0,1,0,0,0,0,0,1},
      {1,0,0,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,1,0,0,0,0,0,1},
      {1,0,0,0,0,0,1,0,0,0,0,0,1},
      {1,0,0,0,0,0,1,0,0,0,0,0,1},
      {1,1,1,1,1,1,1,1,1,1,1,1,1}
      };
 int     mapwidth =    20;
 int      mapheight =   13;
 int      cellwidth =   16;
 int      cellheight =   16;
 double     px =     200;
 double    py =    100;
 int     pwidth =    cellwidth/2;
 int     pheight =    cellheight;
 boolean    isjumping =   false;
 boolean    isfalling =   false;
 double    gravity =    0;
 boolean    ismovingright =  false;
 boolean    ismovingleft =   false;
 boolean    ismovingup =   false;
 boolean    ismovingdown =   false;
 boolean    issticking =   false;
 boolean    isstickingceiling = false;
 boolean    isstickingwall = false;
 double    jumpforce =   3;

 public void init() {
     setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
  initmap();
  new Thread(this).start();

 }

 public void initmap(){
 }

 public void paint(Graphics g) {
 }

    public void run() {
        for(;;) { // animation loop never ends
   updateplayer();
         repaint();
         try {
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }

    public void updateplayer(){

  if ( issticking == true ) {
   //
   // Here you stick on a wall or a ceiling
   //

   if ( ismovingup == true ) {
    if ( mapcollision ( (int)px , (int)(py - 1) , pwidth , pheight ) == false ){
     py -= 1;
    }
   }
   if ( ismovingdown == true ) {
    if ( mapcollision ( (int)px , (int)(py + 1) , pwidth , pheight ) == false ){
     py += 1;
    }
    if ( mapcollision ( (int)px , (int)(py - 1) , pwidth , pheight ) == false ){
    if ( mapcollision ( (int)( px - 1 ) , (int)py , pwidth , pheight ) == false ){
    if ( mapcollision ( (int)( px + 1 ) , (int)py , pwidth , pheight ) == false ){
     issticking = false;
    }}}
   }
   if ( ismovingleft == true ) {
    if ( mapcollision ( (int)( px - 1 ), (int)py , pwidth , pheight ) == false ){
    if ( mapcollision ( (int)px, (int)( py - 1 ) , pwidth , pheight ) == false ){
     px -= 1;
     issticking = false;
    }}
    if ( mapcollision ( (int)px, (int)( py - 1 ) , pwidth , pheight ) == true ){
    if ( mapcollision ( (int)( px - 1 ), (int)py , pwidth , pheight ) == false ){
     px -= 1;
    }}
   }
   if ( ismovingright == true ) {
    if ( mapcollision ( (int)( px + 1 ) , (int)py , pwidth , pheight ) == false ){
    if ( mapcollision ( (int)px, (int)( py - 1 ) , pwidth , pheight ) == false ){
     px += 1;
     issticking = false;
    }}
    if ( mapcollision ( (int)px, (int)( py - 1 ) , pwidth , pheight ) == true ){
    if ( mapcollision ( (int)( px + 1 ), (int)py , pwidth , pheight ) == false ){
     px += 1;
    }}
   }

  }

  if ( issticking == false ) {
   //
   // Here you do not stick on a wall or ceiling
   //
   if ( isjumping == false && isfalling == false ){
    if( mapcollision( (int)px , (int)py+1 , pwidth , pheight ) == false ){
     isfalling = true;
     gravity = 0;
    }
   }
   if (ismovingright){
    if ( mapcollision( (int)(px + 1) , (int)py , pwidth , pheight ) == false ){
     px += 1;
    }else{
     issticking = true;
     isjumping = false;
     isfalling = false;
    }
   }
   if (ismovingleft){
    if ( mapcollision( (int)(px - 1) , (int)py , pwidth , pheight ) == false ){
     px -= 1;
    }else{
     issticking = true;
     isjumping = false;
     isfalling = false;
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
      //gravity = 0;
      issticking = true;
      isfalling = false;
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


    }

  public boolean mapcollision( int x , int y , int width , int height ){
   int mapx = x / cellwidth;
   int mapy = y / cellheight;
   for ( int y1 = mapy - 1 ; y1 < mapy + 2 ; y1++ ){
    for ( int x1 = mapx - 1 ; x1 < mapx + 2 ; x1++ ){
     if ( x1 >= 0 && x1 < mapwidth && y1 >= 0 && y1 < mapheight ){
      if ( map[x1][y1] == 1 ){
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

   public boolean mouseMove(Event e, int x, int y){
  return true;
 }

    public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        // Draw map
        bufferGraphics.setColor(Color.red);
        for( int y = 0 ; y < mapheight ; y++ ){
         for ( int x = 0 ; x < mapwidth ; x++){
          if( map[x][y] == 1 ){
           bufferGraphics.fillRect( x * cellwidth , y * cellheight , cellwidth , cellheight );
          }
         }
        }
        bufferGraphics.setColor(Color.white);
        bufferGraphics.drawString("Platformer sticky walls.",10,10);

        // Draw player
        bufferGraphics.fillRect( (int)px , (int)py , pwidth , pheight );
        bufferGraphics.setColor(Color.white);
  bufferGraphics.drawString("W, S , A , D = movement. Space = jump." , 10 , 220 );
       g.drawImage(offscreen,0,0,this);
    }

  public boolean keyDown (Event e, int key){
    if ( key == 97 ) // a key
        {
         ismovingleft = true;
        }
        if ( key == 100 ) // d key
        {
          ismovingright = true;
        }

  if ( key == 119 ) // w key
  {
   ismovingup = true;
  }
  if ( key == 115) // s key
  {
   ismovingdown = true;
  }

      if( key == 32 ) //  space for jump
      {
        if( isfalling == false && isjumping == false && issticking == false )
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
