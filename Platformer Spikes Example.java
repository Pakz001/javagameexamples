

import java.awt.*;
import java.applet.*;
import java.util.Random;
import java.awt.geom.Area;

public class PlatformerSpikes01 extends Applet implements Runnable {
 Random    r =     new Random();
 // Graphics for double buffering.
 Graphics    bufferGraphics;
    Image     offscreen;
 Image     spikeimage;
 private short map[][]={
      {1,1,1,1,1,1,1,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,2,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,2,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,2,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,1,1,1,1,1,1,1,1,1,1,1,1}
      };
 int     mapwidth =    20;
 int      mapheight =   13;
 int      cellwidth =   16;
 int      cellheight =   16;
 int     pstartx =    32;
 int     pstarty =    128;
 double     px =     pstartx;
 double    py =    pstarty;
 int     pwidth =    cellwidth/2;
 int     pheight =    cellheight;
 boolean    isjumping =   false;
 boolean    isfalling =   false;
 double    gravity =    0;
 boolean    ismovingright =  false;
 boolean    ismovingleft =   false;
 double    jumpforce =   3;
 int     sx1 =    3;
 int     sy1 =    0;
 int     sx2 =    7;
 int     sy2 =    32;
 int     sx3 =     0;
 int     sy3 =    32;
 short    maxnumspikes =  32;
 short     spikewidth =   8;
 short    spikeheight =   32;
 double     spikes[][] =   new double[maxnumspikes][9];  // 0 - active , 1 = x
                   // 2 = y , 3 - incx , 4 - incy
                   // 5 - addx , 6 = addy
                   // 7 - spikewait , 8 - up(1)/down(2)

 public void init() {
     setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();

  spikeimage = createImage(8,32);
  Graphics test1 = spikeimage.getGraphics();
  int[] XArray = {sx1,sx2,sx3};
     int[] YArray = {sy1,sy2,sy3};
     test1.setColor(Color.red);
     test1.fillPolygon (XArray, YArray, 3);

  initmap();
  new Thread(this).start();

 }

 public void initmap(){
  int cnt = 0;
  for ( int y = 0 ; y < mapheight ; y++){
   for ( int x = 0 ; x < mapwidth ; x++){
    if( map[x][y] == 2 ){ // if map tile is a spike (2)
     spikes[cnt][0] = 1;
     spikes[cnt][1] = x * cellwidth;
     spikes[cnt][2] = y * cellheight;
     spikes[cnt][4] = spikeheight;
     spikes[cnt][7] = r.nextInt(200);
     spikes[cnt][8] = 2;
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
         updatespikes();
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

  public void updatespikes(){

   if ( pspikecollision() ) {
    px = pstartx;
    py = pstarty;
   };

   for ( int i = 0 ; i < maxnumspikes ; i++ ){
    if ( spikes[i][0] == 1 ){ // if spike is active
     if(spikes[i][7] < 0 ){
      if ( spikes[i][8] == 2 ){
       spikes[i][4] -= spikes[i][6];
       if ( spikes[i][4] < 0 ){
        spikes[i][4] = 0; // lowest value
        spikes[i][8] = 1; // go up again
        spikes[i][6] = 0;
        spikes[i][7] = 100;
       }
       spikes[i][6] += .01;
      }
      if ( spikes[i][8] == 1 ){
       spikes[i][4] += spikes[i][6];
       if ( spikes[i][4] > spikeheight ){
        spikes[i][4] = spikeheight; // highest value
        spikes[i][8] = 2; // go down again
        spikes[i][7] = 100;
       }
       spikes[i][6] += .01;
      }
     }
     spikes[i][7]--;
    }
   }
  }

 public boolean pspikecollision(){
  for ( int i = 0 ; i < maxnumspikes ; i++ ){
   if ( spikes[i][0] == 1 ){

    int[] XArray =  {(int)px, (int)px+pwidth, (int)px+pwidth,  (int)px};
       int[] YArray =  {(int)py, (int)py,  (int)py+pheight, (int)py+pheight};
       int[] XArray2 = {(int)spikes[i][1]+sx1, (int)spikes[i][1]+sx2,  (int)spikes[i][1]+sx3};
       int[] YArray2 = {(int)spikes[i][2]+(spikeheight - cellheight) - (int)spikes[i][4]+sy1, (int)spikes[i][2]+sy2, (int)spikes[i][2]+sy3};

       Polygon abc = new Polygon(XArray, YArray, 4);
       Polygon abc2= new Polygon(XArray2,YArray2,3);
       //g.drawPolygon(abc);
       //g.drawPolygon(abc2);

      Area a1 = new Area(abc);
      Area a2 = new Area(abc2);

    a1.intersect(a2);
    if (!a1.isEmpty())
     //g.drawString("Two Polygons collided", 20, 20 );
     return true;



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
  // Draw Spikes
  for ( int i = 0 ; i < maxnumspikes ; i++ ){
   if ( spikes[i][0] == 1 ){
    bufferGraphics.drawImage( spikeimage,
           (int)spikes[i][1],
           (int)spikes[i][2] + (spikeheight - cellheight) - (int)spikes[i][4],
           spikewidth,
           (int)spikes[i][4],
           this);
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

        //System.out.println (" Integer Value: " + key);

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
