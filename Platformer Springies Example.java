

import java.awt.*;
import java.applet.*;

public class PlatformerSpringies001 extends Applet implements Runnable {
 // Graphics for double buffering.
 Graphics    bufferGraphics;
    Image     offscreen;
 private short map[][]={
      {1,1,1,1,1,1,1,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,2,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,2,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,1,1,1,1,1,1,1,1,1,1,1,1}
      };
 int     mapwidth =    20;
 int      mapheight =   13;
 int      cellwidth =   16;
 int      cellheight =   16;
 int      maxnumspringies = 32;
 int      swidth =    cellwidth;
 int      sheight =   cellheight/2;
 int      springyjumpforce =  5;
 int     currentspringy =    0;
 double[][]   springy =   new double[maxnumspringies][8];  // 0 - active ; 1 = x ; 2 = y
                    // 3 - springy width
                    // 4 - springy height
                    // 5 - springy jumpforce
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

 public void init() {
     setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
  initmap();
  new Thread(this).start();

 }

 public void initmap(){
  int cnt = 0;
  for ( int y = 0 ; y < mapheight ; y++ ){
   for ( int x = 0 ; x < mapwidth ; x++ ){
    if ( map[x][y] == 2 ){
     springy[cnt][0] = 1;
     springy[cnt][1] = x*cellwidth;
     springy[cnt][2] = y*cellheight+cellheight/2;
     springy[cnt][3] = cellwidth;
     springy[cnt][4] = cellheight/2;
     springy[cnt][5] = springyjumpforce;
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
    if( springycollision( (int)px , (int)(py + 1) , pwidth , pheight ) == true ){
     gravity = springy[currentspringy][5];
     isfalling = false;
     isjumping = true;
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

 public boolean springycollision( int x , int y , int width , int height){
  for ( int i = 0 ; i < maxnumspringies ; i++ ){
   if( springy[i][0] == 1 ){
     Rectangle rec1 = new Rectangle( x , y , width , height );
    Rectangle rec2 = new Rectangle( (int)springy[i][1],
            (int)springy[i][2],
            (int)springy[i][3],
            (int)springy[i][4]);
    if( rec1.intersects( rec2 )){
     currentspringy = i;
     return true;
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
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Platformer Springies Example.",10,10);

        // Draw map
        for( int y = 0 ; y < mapheight ; y++ ){
         for ( int x = 0 ; x < mapwidth ; x++){
          if( map[x][y] == 1 ){
           bufferGraphics.fillRect( x * cellwidth , y * cellheight , cellwidth , cellheight );
          }
         }
        }
        // Draw Springies
        for ( int i = 0 ; i < maxnumspringies ; i++ ){
         if ( springy[i][0] == 1 ){
          bufferGraphics.fillRect(  (int)springy[i][1],
                 (int)springy[i][2],
                 (int)springy[i][3],
                 (int)springy[i][4] );
         }
        }

        // Draw player
        bufferGraphics.fillRect( (int)px , (int)py , pwidth , pheight );

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

        System.out.println (" Integer Value: " + key);

   return true;
  }

 public boolean keyUp (Event e, int key){
    if( key == Event.LEFT )
        {
          ismovingleft = false;
        }
        if( key == Event.RIGHT )
        {
          ismovingright = false;
        }

  return true;
 }

}
