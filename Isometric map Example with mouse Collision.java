

import java.awt.*;
import java.applet.*;

public class IsometricCollision02 extends Applet implements Runnable {
 // Graphics for double buffering.
 Graphics bufferGraphics;
 Image offscreen;
 int tilewidth = 64;
 int tileheight =32;
 int mtilex,mtiley;

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

   public boolean mouseMove(Event e, int x, int y){
    int x0 = 130 + tilewidth/2, y0 = 0;
    mtilex = (int)Math.floor( (y - y0)/(double)tileheight - (x - x0)/(double)tilewidth );
       mtiley = (int)Math.floor( (y - y0)/(double)tileheight + (x - x0)/(double)tilewidth );
   if(mtilex<0)mtilex=mtiley=-1;
   if(mtilex>4)mtilex=mtiley=-1;
   if(mtiley<0)mtiley=mtilex=-1;
   if(mtiley>4)mtiley=mtilex=-1;
   return true;
 }
    public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Isometric Example",10,10);

  int x,y;
     for(int i=0;i<5;i++){
         x = 130 - (i*(tilewidth/2));
         y = 0 + (i*(tileheight/2));
         for(int j=0;j<5;j++){
             drawiso(x,y,bufferGraphics);
             bufferGraphics.drawString(""+i+","+j,x+tilewidth/2,y+tileheight/2);
             x = x + tilewidth/2;
             y = y + tileheight/2;
         }
     }

     bufferGraphics.drawString("Mouse Tile Position : "+mtilex+","+mtiley,10,getSize().height-45);

       g.drawImage(offscreen,0,0,this);
    }
  public boolean keyDown (Event e, int key){
   return true;
  }
 public boolean keyUp (Event e, int key){
  return true;
 }

 public void drawiso(int x1, int y1, Graphics bufferGraphics){
 //Rect x1,y1,64,32,0
  bufferGraphics.drawLine( x1+31,y1,x1+61,y1+16);
  bufferGraphics.drawLine( x1+31,y1,x1,y1+16);
  bufferGraphics.drawLine( x1+31,y1+31,x1+63,y1+16);
  bufferGraphics.drawLine( x1+31,y1+31,x1,y1+16);
 }

}
