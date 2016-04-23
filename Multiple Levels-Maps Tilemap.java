

import java.awt.*;
import java.applet.*;

public class Multiple_Levels_Tilemap extends Applet implements Runnable {
 // Graphics for double buffering.
 Graphics    bufferGraphics;
    Image     offscreen;
 private short map[][][]={
      // Level 1
      {
      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
      },
      // Level 2
      {
      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,1},

      {1,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
      },
      // Level 3
      {
      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},

      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
      },
 };

 int     currentlevel =   0;
 int     mapwidth =    20;
 int      mapheight =   15;
 int      cellwidth =   16;
 int      cellheight =   16;

 public void init() {
     setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
  new Thread(this).start();

 }

 public void paint(Graphics g) {
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


    public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Multiple Levels Tilemap.",20,30);
        bufferGraphics.drawString("Cursors Left and Right for levels.",20,50);

        // Draw map
        for( int y = 0 ; y < mapheight ; y++ ){
         for ( int x = 0 ; x < mapwidth ; x++){
          if( map[currentlevel][y][x] == 1 ){
           bufferGraphics.fillRect( x * cellwidth , y * cellheight , cellwidth , cellheight );
          }
         }
        }

       g.drawImage(offscreen,0,0,this);
    }

  public boolean keyDown (Event e, int key){

        System.out.println (" Integer Value: " + key);

   return true;
  }

 public boolean keyUp (Event e, int key){
    if( key == Event.LEFT )
        {
   if (currentlevel > 0) currentlevel--;
        }
        if(key==Event.RIGHT)
        {
   if (currentlevel < 2) currentlevel++;
        }

  return true;
 }

}
