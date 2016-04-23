

import java.awt.*;
import java.applet.*;
public class RandomObstacleAvoidanceExample001 extends Applet implements Runnable{
 Graphics bufferGraphics;
    Image offscreen;
 int sx , sy , ex , ey; // start and end position
 int px , py; // player position
 long delay;
 private int map[][] =  new int[][]{
 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 {0,0,1,0,0,1,0,0,0,0,0,0,1,0,0,0,1,0,0,0},
 {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
 {0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
 {0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0},
 {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
 {0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0},
 {0,0,0,1,0,0,0,0,0,0,0,1,0,1,0,0,0,1,0,0},
 {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
 {0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
 {0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,1,0,0},
 {0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
 };
 public void init(){
  setBackground( Color.black );
     offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
  setstartposition();
  setendposition();
  px = sx;
  py = sy;
  new Thread(this).start();
 }
    public void run() {
     for(;;) { // animation loop never ends
         moveplayer();
         repaint();
         try {
             Thread.sleep(16);
            }
             catch (InterruptedException e) {
             }
     }
    }
 public void update (Graphics g)
  {
  bufferGraphics.clearRect( 0 , 0 , getSize().width , getSize().height );
     bufferGraphics.setColor ( Color.white );
     bufferGraphics.drawString( "Random Obstacle Avoidance." , 10 , 10 );
  bufferGraphics.setColor( Color.green );
  // draw map
  for ( int y = 0 ; y < 15 ; y++ ){
   for ( int x = 0 ; x < 20 ; x++ ){
    if ( map[ y ][ x ] == 1 ){
     bufferGraphics.fillOval( x * 16 , y * 16 , 16 , 16 );
    }
   }
  }
  // draw end and start position
  bufferGraphics.setColor( Color.red );
  bufferGraphics.fillRect( sx * 16 , sy * 16 , 16 , 16 );
  bufferGraphics.setColor( Color.yellow );
  bufferGraphics.fillRect( ex * 16 , ey * 16 , 16 , 16 );
  // draw player
  bufferGraphics.setColor( Color.white );
  bufferGraphics.fillRect( px * 16 , py * 16 , 16 , 16 );

     g.drawImage(offscreen,0,0,this);

  }
  public void moveplayer(){
   if ( delay < System.currentTimeMillis() ){
    int newx = px;
    int newy = py;
    if ( px < ex ){
     newx++;
    }
    if ( px > ex ){
     newx--;
    }
    if ( py < ey ){
     newy++;
    }
    if ( py > ey ){
     newy--;
    }
    if ( map[ newy ][ newx ] == 1 ){
     boolean newposfound = false;
     newx = px;
     newy = py;
     while ( newposfound == false ){
      if ( (int)(Math.random() * 2 ) == 0 ){
       newx--;
      }else{
       newx++;
      }
      if ( (int)(Math.random() * 2 ) == 0 ){
       newy--;
      }else{
       newy++;
      }
      if ( map[ newy ][ newx ] == 0 ){
       newposfound = true;
      }
     }
    }
    px = newx;
    py = newy;
    if ( px == ex && py == ey ){
    setstartposition();
    setendposition();
    px = sx;
    py = sy;
    }
    delay = System.currentTimeMillis() + 200;
   }
  }
 public void setstartposition(){
  if (  (int)( Math.random() * 2 ) == 0 ){
   sx = ( int )( Math.random() * 6 );
   sy = 0;
  }else{
   sx = 0;
   sy = ( int )( Math.random() * 6 );
  }
 }
 public void setendposition(){
  if (  (int)( Math.random() * 2 ) == 0 ){
   ex = 20 - ( int )( Math.random() * 6 );
   ey = 14;
  }else{
   ex = 19;
   ey = 14 - ( int )( Math.random() * 6 );
  }

 }
}
