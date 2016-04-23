
import java.applet.*;
import java.awt.*;
import java.util.Random;
import java.awt.image.BufferedImage;


public class FogOfWarExample extends Applet implements Runnable {

 Random r = new Random();
 Graphics bufferGraphics;
    Image offscreen;
    Image image2;
    Image image3;
    Image image4;

 private short fogbrush[][]={
 {0,0,1,0,0},
 {0,1,1,1,0},
 {1,1,1,1,1},
 {0,1,1,1,0},
 {0,0,1,0,0},
 };

 private short map[][]={
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1},
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,2,0,1,1,1,1,1,1},
 {1,0,0,0,0,0,1,1,1,0,0,0,0,0,0,1,1,0,0,0,2,2,2,0,0,0,0,1,1,1},
 {1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,0,0,0,0,2,2,2,0,0,0,1,1,1},
 {1,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,1,1},
 {1,1,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,1},
 {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,1,1,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,1},
 {1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,1,1},
 {1,1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
 {1,1,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
 {1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
 {1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
 {1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
 {1,1,1,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,1,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
 {1,1,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,1,1},
 {1,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,2,0,0,0,1,1},
 {2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
 {2,2,2,2,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
 {1,1,1,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,1},
 {1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,1,1,1,1,0,0,1,1,1,1,0,1,1},
 {1,1,1,1,1,1,1,0,0,0,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,1,1},
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 };
    // Here I have stored a sprite that will be drawn onto the screen.
 // Note : you have to switch the x and y in the create sprite part to get
 // the right sprite view since the layout of array data has switched x and y view.
 private short tree1[][]={
       {0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0},
       {0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0},
       {0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0},
       {0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
       {0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
       {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
       {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
       {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
       {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
       {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
       {0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
       {0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0},
       {0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0}
       };
 private short mountain1[][]={
       {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,3,3,3,3,0,0,0,0,0,0},
       {0,0,0,0,0,0,3,3,3,3,0,0,0,0,0,0},
       {0,0,0,0,0,3,3,3,3,3,3,0,0,0,0,0},
       {0,0,0,0,0,3,3,3,3,3,3,0,0,0,0,0},
       {0,0,0,0,3,3,3,3,3,3,3,3,0,0,0,0},
       {0,0,0,0,3,3,3,3,3,3,3,3,0,0,0,0},
       {0,0,0,3,3,3,3,3,3,3,3,3,3,0,0,0},
       {0,0,0,3,3,3,3,3,3,3,3,3,3,0,0,0},
       {0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,0},
       {0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,0},
       {0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0},
       {0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0},
       {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
       };

 private short ground1[][]={
       {4,4,4,4,5,5,4,4,4,4,4,4,5,4,4,4},
       {4,5,4,4,4,4,4,4,4,4,4,4,5,5,4,4},
       {4,4,5,4,4,4,4,4,4,4,4,4,5,5,4,4},
       {4,4,5,5,5,5,4,4,4,4,4,4,4,5,5,4},
       {4,4,5,5,5,5,4,4,4,5,4,4,4,4,5,4},
       {5,4,4,4,5,5,4,4,4,5,4,4,4,4,4,5},
       {5,4,4,4,4,4,4,4,4,5,4,4,4,4,4,4},
       {4,4,4,4,5,4,4,4,4,5,5,4,4,4,4,4},
       {4,4,4,4,4,5,4,4,4,5,5,5,4,4,4,4},
       {4,4,4,4,4,4,4,4,4,5,5,5,5,4,4,4},
       {4,4,5,4,4,4,4,4,4,4,5,5,5,4,4,4},
       {4,4,5,5,4,4,4,4,4,4,5,5,5,5,4,4},
       {4,5,5,5,4,4,4,4,5,4,4,5,5,5,4,4},
       {4,5,5,5,5,5,4,4,5,5,4,4,5,5,4,4},
       {4,4,4,4,5,5,5,4,4,4,4,4,4,5,4,4},
       {4,4,4,4,4,5,5,5,4,4,4,4,5,4,4,4},
       };

 int playerx;
 int playery;
 short[][] fogmap = new short[16][10];

     public void init(){
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();

   image2 = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    Graphics test1 = image2.getGraphics();
      for( int y = 0 ; y < 16 ; y++ ){
       for ( int x = 0 ; x < 16 ; x++ ){
    test1.setColor(getcolor(tree1[x][y]));
        test1.fillRect(y,x,1,1);
       }
      }
   image3 = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    Graphics test2 = image3.getGraphics();
      for( int y = 0 ; y < 16 ; y++ ){
       for ( int x = 0 ; x < 16 ; x++ ){
    test2.setColor(getcolor(mountain1[x][y]));
        test2.fillRect(y,x,1,1);
       }
      }

   image4 = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    Graphics test3 = image4.getGraphics();
      for( int y = 0 ; y < 16 ; y++ ){
       for ( int x = 0 ; x < 16 ; x++ ){
    test3.setColor(getcolor(ground1[x][y]));
        test3.fillRect(y,x,1,1);
       }
      }

  playerx = 16 / 2;
  playery = 10 / 2;
  unfog();
  new Thread(this).start();

     }

    public void run() {
        for(;;) { // animation loop never ends
         repaint();
         try {
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }

 public Color getcolor(int c){
  if (c ==  0 ) return new Color(0,0,0,0);
  if (c ==  1 ) return new Color(0,240,0,255);
  if (c ==  2 ) return new Color(200,100,0,255);
  if (c ==  3 ) return new Color(200,200,200,255);
  if (c ==  4 ) return new Color(30,200,10,255);
  if (c ==  5 ) return new Color(50,220,20,255);
  return new Color(0,0,0,0);
 }

     public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().height);
     bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Fog of War Example",10,10);
        bufferGraphics.drawString("Use w/s/a/d to move - r to reset",10,230);
        for( int y = 0 ; y < 10 ; y++){
         for( int x = 0 ; x < 16 ; x++){
          if (fogmap[x][y] == 1 ) {
           bufferGraphics.drawImage(image4,32+x*16,32+y*16,this);
          }
         }
        }

    int r1 = 0;
        for( int y = 0 ; y < 10 ; y++){
         for( int x = 0 ; x < 16 ; x++){
    if (map[y][x] == 1 && fogmap[x][y] == 1 ) {
       bufferGraphics.drawImage(image2,32+x*16,32+y*16,this);
      }
      if (map[y][x] == 2 && fogmap[x][y] == 1 ){
       bufferGraphics.drawImage(image3,32+x*16,32+y*16,this);
      }
         }
        }
      bufferGraphics.setColor(Color.white);
        bufferGraphics.fillRect(playerx * 16 , playery * 16 , 16 , 16);

        g.drawImage(offscreen,0,0,this);
     }

  public boolean keyDown (Event e, int key){

    if( key == 119 ) // w key
        {
        }
        if(key == 97) // a key
        {
        }
        if( key == 100 ) // d key
        {
        }
        if( key == 115 ) // s key
        {
        }


        System.out.println (" Integer Value: " + key);

   return true;
  }

 public void unfog(){
  for ( int y = 0 ; y < 5 ; y++){
  for ( int x = 0 ; x < 5 ; x++){
  if (fogbrush[x][y] == 1){
   if ( playerx - x > -1 && playerx - x < 16 ){
   if ( playery - y > -1 && playery - y < 10 ){
    fogmap[playerx - x][playery - y] = 1;
   }
   }
  }
  }
  }
 }

 public boolean keyUp (Event e, int key){
    if( key == 119 ) // w key
        {
         if( playery > 2 ) playery--;
         unfog();
        }
        if( key == 97 ) // a key
        {
         if( playerx > 2 ) playerx--;
         unfog();
        }
        if( key == 100 ) // d key
        {
         if( playerx < 17 ) playerx++;
         unfog();
        }
        if( key == 115 ) // s key
        {
         if( playery < 11 ) playery++;
   unfog();
        }
  if ( key == 114 ) // r key
  {
   // reset the fogmap
   for(int y = 0 ; y < 10 ; y++)
   {
    for ( int x = 0 ; x < 16 ; x++)
    {
     fogmap[x][y] = 0;
    }
   }
   unfog();
  }

  return true;
 }


 }
