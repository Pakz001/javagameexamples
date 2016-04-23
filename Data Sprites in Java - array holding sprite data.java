

import java.applet.*;
import java.awt.event.*;
import java.awt.*;

public class DataSpritesInJava01 extends Applet {
    Graphics bufferGraphics;
    Image offscreen;
    Image image2;
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

     public void init(){
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();
   image2 = createImage(16,16);
    Graphics test = image2.getGraphics();
      for( int y = 0 ; y < 16 ; y++ ){
       for ( int x = 0 ; x < 16 ; x++ ){
    test.setColor(Color.black);
        if (tree1[x][y] == 1 ){
          test.setColor(Color.green);
        }
        if (tree1[x][y] == 2 ){
         test.setColor(new Color(200,100,0));
        }
    // Here we draw the pixel.
        test.fillRect(y,x,1,1);
       }
      }

     }
      public void paint(Graphics g){
        bufferGraphics.clearRect(0,0,getSize().width,getSize().height);
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Data [][] Sprites - Tiny trees.",10,10);
        g.drawImage(offscreen,0,0,this);
        for( int y = 0 ; y < 6 ; y++){
         for( int x = 0 ; x < 6 ; x++){
      g.drawImage(image2,30+x*16,30+y*16,this);
         }
        }
     }
     public void update(Graphics g){
          paint(g);
     }
 }

