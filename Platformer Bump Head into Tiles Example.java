

import java.awt.*;
import java.applet.*;

public class PlatformerHitTiles01 extends Applet implements Runnable {
 // Graphics for double buffering.
 Graphics    bufferGraphics;
    Image     offscreen;
 private short map[][]={
      {1,1,1,1,1,1,1,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,2,0,0,1},
      {1,0,0,0,0,0,0,0,0,2,0,0,1},
      {1,0,0,0,0,0,0,0,0,2,0,0,1},
      {1,0,0,0,0,0,0,0,0,2,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,2,0,0,1},
      {1,0,0,0,0,0,0,0,0,2,0,0,1},
      {1,0,0,0,0,0,0,0,0,2,0,0,1},
      {1,0,0,0,0,0,0,0,0,2,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
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
 short    numhitblocks =  32;
 double[][]   hitblocks =   new double[numhitblocks][9];    // 0 - active , 1 - x
                   // 2 - y , 3 - type
                   // 4 - numhits(how many times to jump into for present)
                   // 5 - jump , 6 - jumpgravity , 7 - falling
                   // 8 - incy;
 int     currenthitblock =  0;
 int     numpresents =   32;
 double[][]   presents =    new double[numpresents][9];  // 0 - active , 1 - x
                   // 2 - y

 public void init() {
     setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
  initmap();
  new Thread(this).start();

 }

 public void initmap(){
  // initialize the hit tiles (the blocks you jump into)
  int counter = 0;
  for ( int y = 0 ; y < mapheight ; y++ ){
   for ( int x = 0 ; x < mapwidth ; x++ ){
    if ( map[x][y] == 2 ){
     hitblocks[counter][0] = 1;
     hitblocks[counter][1] = x * cellwidth;
     hitblocks[counter][2] = y * cellheight;
     hitblocks[counter][3] = 0;
     hitblocks[counter][4] = 3;
     counter++;
    }
   }
  }
 }

 public void paint(Graphics g) {
 }

    public void run() {
        for(;;) { // animation loop never ends
   updateplayer();
   updatepresents();
   updatehitblocks();
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
   }
   gravity += .1;
  }

  if ( isjumping == true && isfalling == false ){
   for ( int i = 0 ; i < gravity ; i++){
    if ( mapcollision ( (int)px , (int)(py - 1) , pwidth , pheight ) == false ){
     if ( hitblockcollision ( (int)px , (int)(py - 1) , pwidth , pheight ) == false ){
      py -= 1;
     }else{
      gravity = 0;
      isfalling = true;
      isjumping = false;
      hitblocks[currenthitblock][4] -= 1;
      hitblocks[currenthitblock][5] = 1;
      if( hitblocks[currenthitblock][4] < 0 ){
       hitblocks[currenthitblock][0] = 0;
       map[ (int)hitblocks[currenthitblock][1] / cellwidth ][ (int)hitblocks[currenthitblock][2] / cellheight ] = 1;
       newpresent((int)hitblocks[currenthitblock][1] / cellwidth , ((int)hitblocks[currenthitblock][2] / cellheight ) - 1);
      }
     }
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

 public void updatepresents(){
  for( int i = 0 ; i < numpresents ; i++ ){
   if( presents[i][0] == 1 ){
    Rectangle rec1 = new Rectangle( (int)px,
            (int)py,
            pwidth,
            pheight);
    Rectangle rec2 = new Rectangle( (int)presents[i][1],
            (int)presents[i][2],
            cellwidth,
            cellheight);
    if (rec1.intersects( rec2 )){
     presents[i][0] = 0;
    }
   }
  }
 }

 public boolean hitblockcollision( int x , int y , int width , int height ){
  for ( int i = 0 ; i < numhitblocks ; i++ ){
   if( hitblocks[i][0] == 1 ){
    Rectangle rec1 = new Rectangle( x , y , width , height );
    Rectangle rec2 = new Rectangle( (int)hitblocks[i][1],
            (int)hitblocks[i][2],
            cellwidth,
            cellheight);
    if( rec1.intersects( rec2 )){
     currenthitblock = i;
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

 public boolean newpresent(int x, int y){
  for( int i = 0 ; i < numpresents ; i++){
   if ( presents[i][0] == 0 ) {
    presents[i][1] = x * cellwidth;
    presents[i][2] = y * cellheight;
    presents[i][0] = 1;
    return true;
   }
  }
  return false;
 }

   public boolean mouseMove(Event e, int x, int y){
  return true;
 }

 public void updatehitblocks(){
  for ( int i = 0 ; i < numhitblocks ; i++ ){
   if( hitblocks[i][0] == 1 ){
    if( hitblocks[i][5] == 1 ){
     hitblocks[i][6] -= hitblocks[i][8];
     hitblocks[i][8] += .1;
     if( hitblocks[i][6] < -6 ) {
      hitblocks[i][5] = 0;
      hitblocks[i][7] = 1;
      hitblocks[i][8] = 0;
     }
    }
    if( hitblocks[i][7] == 1 ){
     hitblocks[i][6] += hitblocks[i][8];
     hitblocks[i][8] += .1;
     if( hitblocks[i][6] > 0 ){
      hitblocks[i][6] = 0;
      hitblocks[i][7] = 0;
      hitblocks[i][8] = 0;
     }
    }
   }
  }
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

  // Draw hitblocks
  bufferGraphics.setColor( new Color(255,255,0));
  for ( int i = 0 ; i < numhitblocks ; i++ ){
   if( hitblocks[i][0] == 1 ) {
    bufferGraphics.fillRect(  (int)hitblocks[i][1],
           (int)hitblocks[i][2] + (int)hitblocks[i][6] ,
           cellwidth,
           cellheight);
   }
  }

  // Draw presents
  bufferGraphics.setColor( new Color(0,255,0));
  for ( int i = 0 ; i < numpresents ; i++){
   if ( presents[i][0] == 1 ){
    bufferGraphics.fillOval( (int)presents[i][1],
           (int)presents[i][2],
           cellwidth,
           cellheight);
   }
  }
        // Draw player
        bufferGraphics.setColor(Color.red);
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
