
//
// I have placed the moving blocks in a regular array instead of placing them in a ArrayList.
// This because I had strange bugs with ArrayLists that I could solve. Better save then sorry.
//
//

import java.awt.*;
import java.applet.*;

public class PlatformerMovingBlocks01 extends Applet implements Runnable {
 // Graphics for double buffering.
 Graphics    bufferGraphics;
    Image     offscreen;
 private short map[][]={
      {1,1,1,1,1,1,1,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,2,2,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,2,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,2,2,2,1},
      {1,0,0,0,0,0,0,0,0,0,0,2,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,2,1},
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
 double    jumpforce =   3;
 short    maxnummblocks =  32; // Maximum number of moving blocks
 int     mblockwidth =  16;
 int     mblockheight =   16;
 short    currentmblock =  0;
 boolean    dragblock =   false;
 short    bbdragged =   0;
 double[][]   mblocks =   new double[maxnummblocks][8]; // 0 - active , 1 - x , 2 - y
                   // 3 - falling (1) , 4 - gravx
                   // 5 - gravy
 public void init() {
     setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
  initmap();
  new Thread(this).start();

 }

 public void initmap(){
  int cnt = 0;
  for( int y = 0 ; y < mapheight ; y++ ){
   for ( int x = 0 ; x < mapwidth ; x++ ){
    if( map[x][y] == 2 ){
     mblocks[cnt][0] = 1;
     mblocks[cnt][1] = x * cellwidth;
     mblocks[cnt][2] = y * cellheight;
     cnt++;
    }
   }
  }
 }

 public void paint(Graphics g) {
 }

    public void run() {
        for(;;) { // animation loop never ends
   updateplayer();
         updatemblocks();
         repaint();
         try {
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }

    public void updateplayer(){

  if ( isjumping == false && isfalling == false ){
   if( mapcollision( (int)px , (int)py+1 , pwidth , pheight ) == false &&
    mblockcollision ( (int)px , (int)py+1 , pwidth , pheight ) == false ){
    isfalling = true;
    gravity = 0;
   }
  }
  if (ismovingright){
   if ( mapcollision( (int)(px + 1) , (int)py , pwidth , pheight ) == false ){
    if( mblockcollision( (int)px + 1 , (int)py , pwidth , pheight ) == false ){
     px += 1;
     if (dragblock == true ){
      if(mblockcollision ( (int)px-2 , (int)py , pwidth, pheight) == true){
       mblocks[currentmblock][1] += 1;
      }
     }

    }
   }
   if ( mblockcollision( (int)px + 1 , (int)py , pwidth , pheight )){
    // touching a moveable block
    // check if it is not touching another block or map tile.
    if ( mblockblockwallc( 1 , 0 ) == false ){
     px += 1;
     mblocks[currentmblock][1] += 1;
    }
   }
  }
  if (ismovingleft){
   if ( mapcollision( (int)(px - 1) , (int)py , pwidth , pheight ) == false ){
    if( mblockcollision( (int)px - 1 , (int)py , pwidth , pheight ) == false ){
     px -= 1;
     if (dragblock == true ){
      if(mblockcollision ( (int)px+2 , (int)py , pwidth, pheight) == true){
       mblocks[currentmblock][1] -= 1;
      }
     }
    }
   }
   if ( mblockcollision( (int)px - 1 , (int)py , pwidth , pheight )){
    // touching a moveable block
    // check if it is not touching another block or map tile.
    if ( mblockblockwallc( -1 , 0 ) == false ){
     px -= 1;
     mblocks[currentmblock][1] -= 1;
    }
   }
  }

  if ( isfalling == true && isjumping == false ){
   for ( int i = 0 ; i < gravity ; i++ ){
    if ( mapcollision ( (int)px , (int)py + 1 , pwidth , pheight ) == false &&
      mblockcollision ((int)px , (int)py + 1 , pwidth , pheight ) == false){
     py += 1;
    }else{
     System.out.print("Stopped falling..");
     gravity = 0;
     isfalling = false;
    }
   }
   gravity += .1;
  }

  if ( isjumping == true && isfalling == false ){
   for ( int i = 0 ; i < gravity ; i++){
    if ( mapcollision ( (int)px , (int)(py - 1) , pwidth , pheight ) == false &&
     mblockcollision ( (int)px , (int)py - 1 , pwidth , pheight ) == false ) {
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

 public void updatemblocks(){
  // if block is floating then set to falling
  for ( int i = 0 ; i < maxnummblocks ; i++ ){
   if ( mblocks[i][0] == 1 ){
    if ( mblockblocktilec(i,0,1) == false ){
     mblocks[i][3] = 1;
    }
   }
  }
  // do falling blocks
  for ( int i = 0 ; i < maxnummblocks ; i++ ){
   if ( mblocks[i][0] == 1 ){
    for (int j = 0 ; j < mblocks[i][5] ; j++ ){
     if (mblockblocktilec(i,0,1) == false ){
      mblocks[i][2] += 1;
     }else{
      mblocks[i][3] = 0;
      mblocks[i][5] = 0;
     }
    }
    mblocks[i][5] += .1;
   }
  }

 }

 public boolean mblockblocktilec( int block , int x, int y ){
  for ( int i = 0 ; i < maxnummblocks ; i++ ){
   if ( block != i && mblocks[i][0] == 1 ){
    Rectangle rec1 = new Rectangle( (int)mblocks[block][1] + x ,
            (int)mblocks[block][2] + y ,
            mblockwidth ,
            mblockheight );
    Rectangle rec2 = new Rectangle( (int)mblocks[i][1] ,
            (int)mblocks[i][2] ,
            mblockwidth ,
            mblockheight );
    if ( rec1.intersects(rec2) ) {
     return true;
    }
   }
  }
  // check if block touches tile
  int x1 = (int)(mblocks[block][1] / cellwidth);
  int y1 = (int)(mblocks[block][2] / cellheight);
  for ( int y2 = y1 - 1 ; y2 < y1 + 2 ; y2++ ){
   for ( int x2 = x1 - 1 ; x2 < x1 + 2 ; x2++ ){
    if( x2 >= 0 && x2 < mapwidth && y2 >= 0 && y2 < mapheight ){
     if(map[x2][y2] == 1){
       Rectangle rec1 = new Rectangle( (int)mblocks[block][1] + x ,
               (int)mblocks[block][2] + y ,
               mblockwidth ,
               mblockheight);
       Rectangle rec2 = new Rectangle( x2 * cellwidth ,
               y2 * cellheight ,
               cellwidth ,
               cellheight);
       if ( rec1.intersects(rec2) ) {
        return true;
       }
     }
    }
   }
  }
  // check if on player
  Rectangle rec1 = new Rectangle( (int)px ,
          (int)py ,
          pwidth ,
          pheight);
  Rectangle rec2 = new Rectangle( (int)mblocks[block][1]+x ,
          (int)mblocks[block][2]+y ,
          mblockwidth ,
          mblockheight);
  if ( rec1.intersects(rec2) ) {
   return true;
  }


  return false;
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

 public boolean mblockblockwallc( int dirx, int diry ){
  // check for collision with other blocks.
  for ( int i = 0 ; i < maxnummblocks ; i++ ){
   if ( i != currentmblock ){
    if ( mblocks[i][0] == 1 ){
     Rectangle rec1 = new Rectangle( (int)mblocks[currentmblock][1] + dirx ,
              (int)mblocks[currentmblock][2] + diry ,
              mblockwidth ,
              mblockheight );
     Rectangle rec2 = new Rectangle( (int)mblocks[i][1] ,
              (int)mblocks[i][2] ,
              mblockwidth ,
              mblockheight );
     if (rec1.intersects(rec2)) {
      return true;
     }
    }
   }
  }
  // check for collision with map tile (1)
  int x1 = (int)mblocks[currentmblock][1] / cellwidth;
  int y1 = (int)mblocks[currentmblock][2] / cellheight;
  for ( int y = y1 - 1 ; y < y1 + 2 ; y++ ){
   for ( int x = x1 - 1 ; x < x1 + 2 ; x++ ){
    if( x >= 0 && x < mapwidth && y >= 0 && y < mapheight ){
     if( map[x][y] == 1 ){
      Rectangle rec1 = new Rectangle( x * cellwidth ,
              y * cellheight ,
              cellwidth ,
              cellheight);
      Rectangle rec2 = new Rectangle( (int)mblocks[currentmblock][1] ,
              (int)mblocks[currentmblock][2] ,
              mblockwidth ,
              mblockheight);
      if (rec1.intersects(rec2)){
       return true;
      }
     }
    }
   }
  }
  return false;
 }

  public boolean mblockcollision( int x , int y , int width , int height ){
   for ( int i = 0 ; i < maxnummblocks ; i++ ){
    if( mblocks[i][0] == 1){
     Rectangle rec1 = new Rectangle( x , y , width , height );
     Rectangle rec2 = new Rectangle(  (int)mblocks[i][1],
              (int)mblocks[i][2],
              mblockwidth,
              mblockheight);
     if(rec1.intersects(rec2)){
      currentmblock = (short)i;
      return true;
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
        bufferGraphics.setColor(Color.red);

        // Draw map
        for( int y = 0 ; y < mapheight ; y++ ){
         for ( int x = 0 ; x < mapwidth ; x++){
          if( map[x][y] == 1 ){
           bufferGraphics.fillRect( x * cellwidth , y * cellheight , cellwidth , cellheight );
          }
         }
        }

  // Draw the moveable blocks
  for ( int i = 0 ; i < maxnummblocks ; i++ ){
   if ( mblocks[i][0] == 1 ){
    bufferGraphics.fillRect(  (int)mblocks[i][1] ,
           (int)mblocks[i][2] ,
           cellwidth ,
           cellheight );
   }
  }

        // Draw player
        bufferGraphics.fillRect( (int)px , (int)py , pwidth , pheight );
  // Draw some info
        bufferGraphics.setColor(Color.white);
        bufferGraphics.drawString("Platformer Moveable blocks Example.",10,10);
        bufferGraphics.drawString("cursor l/r, space , x to drag blocks.",10,20);
       g.drawImage(offscreen,0,0,this);
    }

  public boolean keyDown (Event e, int key){
    if( key == Event.LEFT )
        {
         ismovingleft = true;
        }
        if(key==Event.RIGHT)
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

  if ( key == 120 ){ // x button block dragging mode
   dragblock = true;
  }
        //System.out.println (" Integer Value: " + key);

   return true;
  }

 public boolean keyUp (Event e, int key){
    if( key == Event.LEFT ){
          ismovingleft = false;
        }
        if( key == Event.RIGHT ){
          ismovingright = false;
        }
  if ( key == 120 ){ // block being dragged mode (x key)
   dragblock = false;
  }

  return true;
 }

}
