 
//
// This is maybe the third time that I made a platformer thing with a moving platform.
// I got the moving platform part working quickly. I had a little trouble with
// the gravity, jumping and falling.
//
// The green rectangles I made for viewing the collision cells.
//
// Feel free to modify and use the example for your own use.
//

import java.awt.*;
import java.applet.*;

public class MovingPLatform001 extends Applet implements Runnable {
 // Graphics for double buffering.
 Graphics    bufferGraphics;
 Image     offscreen;
 int     mapwidth =    20;
 int     mapheight =   13;
 short    cellwidth =   16;
 short    cellheight =   16;
 short    maxnummplats =   16;
 private double[][] mplat =    new double[maxnummplats][8]; // 0 - active 0/1 ; 1 - x ; 2 - y ; 3 - incx ;
                   // 4 - incy
 int     mplatwidth =   cellwidth*2;
 int     mplatheight =  cellheight/2;
 int     mplatmspeed =   1;
 int     currentmplat =   0;
 boolean    isonmplat =   false;
 private short map[][]={
      {1,1,1,1,1,1,1,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,0,0,1,1},
      {1,0,0,0,0,0,0,0,0,0,0,1,1},
      {1,0,0,0,0,0,0,0,0,0,1,1,1},
      {1,0,0,0,1,0,0,0,0,1,1,1,1},
      {1,0,0,0,1,0,0,0,0,1,1,1,1},
      {1,0,0,0,1,0,0,0,0,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,1,1,1,1},
      {1,0,0,0,2,0,0,0,0,0,1,1,1},
      {1,0,0,0,0,0,0,0,0,0,0,1,1},
      {1,0,0,0,0,0,0,1,0,0,0,0,1},
      {1,0,0,0,0,0,0,1,0,0,0,0,1},
      {1,0,0,0,0,0,0,1,0,0,0,0,1},
      {1,0,0,0,0,0,0,1,0,0,0,0,1},
      {1,1,1,1,1,1,1,1,1,1,1,1,1}
      };
 double    px =     100;
 double    py =    100;
 int     pwidth =   cellwidth/2;
 int     pheight =    cellheight;
 int     pmspeed =    1;
 int[][]    coldebug =    new int[9][12];
 boolean    isjumping =   false;
 boolean    isfalling =   false;
 boolean    ismovingright =  false;
 boolean    ismovingleft =   false;
 double    gravity =   0;
 double    jumpforce =   3;

 public void init() {
     setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
  initmplat();
  new Thread(this).start();

 }
 public void paint(Graphics g) {
 }
    public void run() {
        for(;;) { // animation loop never ends
   movemplat();
   updateplayer();
         repaint();
         try {
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }
 public void initmplat(){
  int curmplat = 0;
  for ( int y = 0 ; y < mapheight ; y++ ){
   for ( int x = 0 ; x < mapwidth ; x++ ){
    if ( map[x][y] == 2 ){      // if 2=mplat
     mplat[curmplat][0] = 1;    // activate platform
     mplat[curmplat][1] = x * cellwidth; // x location
     mplat[curmplat][2] = y * cellheight;// y location
     mplat[curmplat][3] = mplatmspeed; // movement speed
     curmplat++;
    }
   }
  }
 }
 public void movemplat(){
  // here the moving platforms are moved
  for ( int i = 0 ; i < maxnummplats ; i++ ) {
   if ( mplat[i][0] == 1 ){ // if moving platform exists
    if( mplat[i][3] > 0 ){ // if movement speed is positive
     if ( map[
        (int)( ( mplat[i][1] + mplat[i][3] + mplatwidth ) /cellwidth)][
        (int)(mplat[i][2]/cellheight)]
        != 1 ){
      mplat[i][1] += mplat[i][3];
     }else{
      mplat[i][3] = -mplat[i][3];
     }
    }else{ // if movement speed is negative
     if ( map[
        (int)( ( mplat[i][1] + mplat[i][3]) /cellwidth)][
        (int)(mplat[i][2]/cellheight)]
        != 1 ){
      mplat[i][1] += mplat[i][3];
     }else{
      mplat[i][3] = -mplat[i][3];
     }
    }
   }
  }
 }
 public boolean mplatcollision( int x , int y , int width , int height ){
  for ( int i = 0 ; i < maxnummplats ; i++ ){
   if ( mplat[i][0] == 1 ){
    Rectangle rec1 = new Rectangle(x,y,width,height);
    Rectangle rec2 = new Rectangle( (int)mplat[i][1] ,
            (int)mplat[i][2] ,
            mplatwidth  ,
            mplatheight );
    if(rec1.intersects(rec2)){
     currentmplat = i;
     return true;
    }
   }
  }
  return false;
 }
 public void updateplayer(){
  if (ismovingright){
   if ( mapcollision( (int)(px + pmspeed) , (int)py , pwidth , pheight ) == false ){
    px += pmspeed;
   }
  }
  if (ismovingleft){
   if ( mapcollision( (int)(px - pmspeed) , (int)py , pwidth , pheight ) == false ){
    px -= pmspeed;
   }
  }
  if (isonmplat == true){
   px += mplat[currentmplat][3];
   if (mplatcollision( (int)px , (int)(py+1) , pwidth , pheight ) == false){
    isonmplat = false;

   }
  }
  if( isfalling == false && isjumping == false && isonmplat == false ){
   if( mapcollision( (int)px , (int)( py + 1 ) , pwidth , pheight ) == false )
    isfalling = true;
  }
  if ( isfalling = true && isjumping == false && isonmplat == false){

   for ( int i = 0 ; i < gravity ; i++ ){
    if ( mapcollision ( (int)px , (int)(py + 1) , pwidth , pheight ) == false ){
     py += 1;
    }else{
     gravity = 0;
     isfalling = false;
    }
    if ( mplatcollision ((int)px , (int)(py + 1) , pwidth , pheight ) == true ){
     isfalling = false;
     gravity = 0;
     isonmplat = true;
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
 public boolean mapcollision( int x , int y , int width, int height ){
  // get the map coordinates from the player coordinates
  int mapx = x / cellwidth;
  int mapy = y / cellheight;
  int cnt = 0; // counter for coldebug
  for ( int y1 = mapy - 1 ; y1 < mapy + 2 ; y1++ ){
   for ( int x1 = mapx - 1 ; x1 < mapx + 2 ; x1++){
    //coldebug[cnt][0] = x1 * cellwidth;
    //coldebug[cnt][1] = y1 * cellheight;
    //coldebug[cnt][2] = cellwidth;
    //coldebug[cnt][3] = cellheight;
    //cnt++;
    if( x1 >= 0 && x1 < mapwidth && y1 >= 0 && y1 < mapheight ){
     coldebug[cnt][0] = x1 * cellwidth;
     coldebug[cnt][1] = y1 * cellheight;
     coldebug[cnt][2] = cellwidth;
     coldebug[cnt][3] = cellheight;
     cnt++;
     if ( map[x1][y1] == 1 ){
      Rectangle rec1 = new Rectangle(  x1 * cellwidth ,
                y1 * cellheight ,
                cellwidth ,
                cellheight );
      Rectangle rec2 = new Rectangle(  x,
               y,
               width,
               height);
      if(rec1.intersects(rec2)) return true;
     }
    }
   }
  }
  return false;
 }
 public boolean mouseMove(Event e, int x, int y){
  //System.out.println( "" + mapcollision( x , y , pwidth , pheight ));
  return true;
 }
    public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Moving Platformer",10,10);
  // Draw the map
  for ( int y = 0 ; y < mapheight ; y++ ){
   for ( int x = 0 ; x < mapwidth ; x++ ){
    if ( map[x][y] == 1 ){
     bufferGraphics.fillRect( x * cellwidth , y * cellheight , cellwidth , cellheight );
    }
   }
  }
  // Draw the moving platforms
  for( int i = 0 ; i < maxnummplats ; i++ ){
   if ( mplat[i][0] == 1 ){ // if moving platformer is active/used/exists
    bufferGraphics.fillRect(  (int)mplat[i][1] ,
           (int)mplat[i][2] ,
           (int)mplatwidth ,
           (int)mplatheight );
   }
  }
  // Draw the player
  bufferGraphics.fillRect((int)px,(int)py,pwidth,pheight);

  // draw the collision debug info
  bufferGraphics.setColor(Color.green);
  for ( int i = 0 ; i < 9 ; i++ ){
   bufferGraphics.drawRect(  coldebug[i][0],
          coldebug[i][1],
          coldebug[i][2],
          coldebug[i][3]);
  }
  bufferGraphics.drawString("isjumping :"+isjumping+" , isfalling :"+isfalling,10,250);

  // Draw the Graphics buffer
       g.drawImage(offscreen,0,0,this);
    }
  public boolean keyDown (Event e, int key){
   if(key==Event.LEFT)
        {
         ismovingleft = true;
        }
        if(key==Event.RIGHT)
        {
         ismovingright = true;
        }

     if(key==32)
   {
   if(isfalling==false && isjumping==false)  {
       isjumping = true;
     gravity = jumpforce;
     System.out.println("Player is in jump mode.");
      }
     }


   return true;
  }
 public boolean keyUp (Event e, int key){

  if(key==Event.LEFT)
        {
         ismovingleft = false;
        }
        if(key==Event.RIGHT)
        {
         ismovingright = false;
        }
  //System.out.println(""+key);
  return true;
 }

}
