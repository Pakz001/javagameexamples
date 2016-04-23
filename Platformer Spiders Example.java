

import java.awt.*;
import java.applet.*;

public class PlatformerSpidersExample001 extends Applet implements Runnable {
 Graphics     bufferGraphics;
    Image      offscreen;
 private int map[][] =  new int[][]{
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,1},
 {1,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
 {1,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2,0,0,0,1},
 {1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,1},
 {1,0,0,0,1,1,1,1,0,0,0,0,0,0,0,2,0,0,0,1},
 {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1},
 {1,1,1,1,1,0,0,0,0,0,0,0,2,0,0,0,2,0,0,1},
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

 int[][] spider = new int[32][16]; // active, x, y , direction/down/up, offsety
 long[] spiderdelay = new long[32];

 public void init() {
  setBackground(Color.black);
     offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
     // initiate the spiders
     int spidercount = 0;
     for ( int y = 0 ; y < mapheight ; y++ ) {
      for ( int x = 0 ; x < mapwidth ; x++ ) {
       if ( map[y][x] == 2 ) {
        spider[ spidercount ][ 0 ] = 1;
        spider[ spidercount ][ 1 ] = x * cellwidth;
        spider[ spidercount ][ 2 ] = y * cellheight;
        spider[ spidercount ][ 4 ] = (int)(Math.random() * 24);
        spiderdelay[ spidercount ] = System.currentTimeMillis();
        spidercount++;
       }
      }
     }
  new Thread(this).start();
 }

    public void run() {
     for(;;) { // animation loop never ends
         repaint();
         try {
       updateplayer();
       updatespiders();
       spiderplayercollision();
             Thread.sleep(16);
            }
             catch (InterruptedException e) {
             }
     }
    }

    public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        bufferGraphics.setColor(Color.white);

        // Draw map
        for( int y = 0 ; y < mapheight ; y++ ){
         for ( int x = 0 ; x < mapwidth ; x++){
          if( map[y][x] == 1 ){
           bufferGraphics.fillRect( x * cellwidth , y * cellheight , cellwidth , cellheight );
          }
         }
        }
        // Draw player
        bufferGraphics.fillRect( (int)px , (int)py , pwidth , pheight );

  // Draw spiders
  bufferGraphics.setColor(Color.yellow);
  for (int i = 0 ; i < 32 ; i++){
   if ( spider[i][0] == 1){
    bufferGraphics.fillOval( spider[ i ][ 1 ] , spider[ i ][ 2 ] + spider[ i ][ 4 ] ,
           cellwidth/2 , cellheight/2);
    bufferGraphics.drawLine( spider[ i ][ 1 ] + 4 , spider[ i ][ 2 ] ,
           spider[ i ][ 1 ] + 4 , spider[ i ][ 2 ] + spider[ i ][ 4 ]);
   }
  }

        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Platformer Spiders Example.",10,10);
        bufferGraphics.drawString("a - left, d - right, space to jump.",10,240);

       g.drawImage(offscreen,0,0,this);
    }

    private void spiderplayercollision(){
     for ( int i = 0 ; i < 32 ; i++ ) {
      if ( spider[ i ][ 0 ] == 1 ) {
       Rectangle rec1 = new Rectangle( (int)px , (int)py , pwidth , pheight );
    Rectangle rec2 = new Rectangle( spider[ i ][ 1 ] , spider[ i ][ 2 ] + spider[ i ][ 4 ] ,
            8 , 8 );
    if(rec1.intersects(rec2)){
     px = 132;
     py = 200;
    }
      }
     }
    }

 public void updatespiders(){

  // spidermovement
  for ( int i = 0 ; i < 32 ; i++ ) {
   if ( spider[i][0] == 1 ) {
    if ( spiderdelay[i] < System.currentTimeMillis() ) {
     spiderdelay[i] = System.currentTimeMillis() + 20;
     if ( spider[i][3] == 0 ) {
      spider[i][4]++;
      if ( spider[i][4] > 24 ) {
       spider[i][3] = 1;
       spiderdelay[i] = System.currentTimeMillis() + (int)( Math.random() * 2000 );
      }
     }
     if ( spider[i][3] == 1 ) {
      spider[i][4]--;
      if ( spider[i][4] < 1 ) {
       spider[i][3] = 0;
       spiderdelay[i] = System.currentTimeMillis() + (int)( Math.random() *  2000 );
      }
     }
    }
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
