

import java.awt.*;
import java.applet.*;

public class TopdownMovementExample001 extends Applet implements Runnable{

 Graphics bufferGraphics;
 Image offscreen;
 boolean ismovingleft;
 boolean ismovingright;
 boolean ismovingup;
 boolean ismovingdown;
 private int map[][] =  new int[][]{
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,1,1,1,1,0,1,0,0,0,0,1,0,1,1,1,1,0,1},
 {1,0,0,0,1,0,0,1,0,0,0,0,1,0,0,1,0,0,0,1},
 {1,0,1,0,1,0,0,1,1,0,0,1,1,0,0,1,0,1,0,1},
 {1,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,1},
 {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
 {1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1},
 {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
 {1,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,1},
 {1,0,1,0,1,0,0,1,1,0,0,1,1,0,0,1,0,1,0,1},
 {1,0,0,0,1,0,0,1,0,0,0,0,1,0,0,1,0,0,0,1},
 {1,0,1,1,1,1,0,1,0,0,0,0,1,0,1,1,1,1,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 };
 int mapwidth = 20;
 int mapheight = 15;
 int cellwidth = 16;
 int cellheight = 16;
 int playerx = 10*cellwidth;
 int playery = 13*cellheight;
 public void init() {
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();
  new Thread(this).start();
 }
    public void run() {
        for(;;) { // animation loop never ends
   moveplayer();
         repaint();
         try {
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }

    public boolean ismapcollision(int x, int y){

  int pcx = x / cellwidth;
  int pcy = y / cellheight;
  for (int y1 = pcy - 1 ; y1 < pcy + 2 ; y1++){
   for (int x1 = pcx - 1 ; x1 < pcx + 2 ; x1++){
    if( x1 >= 0 && x1 < mapwidth && y1 >= 0 && y1 < mapheight ){
     if ( map[y1][x1] == 1 ){
      Rectangle rec1 = new Rectangle(  x1 * cellwidth ,
                y1 * cellheight ,
                cellwidth ,
                cellheight );
      Rectangle rec2 = new Rectangle(  x,
               y,
               cellwidth,
               cellheight);
      if(rec1.intersects(rec2)) return true;
     }
    }
   }
  }
  return false;
    }

    public void moveplayer(){
     if (ismovingright == true && ismapcollision(playerx + 1,playery) == false){
      playerx++;
     }
     if (ismovingup == true && ismapcollision(playerx,playery-1) == false){
      playery--;
     }
     if (ismovingdown == true && ismapcollision(playerx,playery+1) == false){
      playery++;
     }
     if (ismovingleft == true && ismapcollision(playerx-1,playery) == false){
      playerx--;
     }

    }

     public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().height);

  bufferGraphics.setColor(Color.white);
        for( int y = 0 ; y < mapheight ; y++ ){
         for ( int x = 0 ; x < mapwidth ; x++){
          if( map[y][x] == 1 ){
           bufferGraphics.fillRect( x * cellwidth , y * cellheight , cellwidth , cellheight );
          }
         }
        }

     bufferGraphics.setColor(Color.red);

  bufferGraphics.fillOval(playerx,playery,cellwidth,cellheight);

        bufferGraphics.drawString("2D Topdown Movement.",10,10);
  bufferGraphics.drawString("w/s/a/d to move player.",10,237);

        g.drawImage(offscreen,0,0,this);
     }
  public boolean keyDown (Event e, int key){
   if(key==97)
        {
         ismovingleft = true;
        }
        if(key==100)
        {
         ismovingright = true;
        }
        if(key==119)
        {
         ismovingup = true;
        }
        if(key==115)
        {
         ismovingdown = true;
        }

   return true;
  }
 public boolean keyUp (Event e, int key){

   if(key==97)
        {
         ismovingleft = false;
        }
        if(key==100)
        {
         ismovingright = false;
        }
        if(key==119)
        {
         ismovingup = false;
        }
        if(key==115)
        {
         ismovingdown = false;
        }
//  System.out.println(""+key);
  return true;
 }


}
