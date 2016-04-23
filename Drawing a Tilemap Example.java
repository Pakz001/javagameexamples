
import java.applet.*;
import java.awt.event.*;
import java.awt.*;

public class TileMap extends Applet
{
    Graphics bufferGraphics;
 Image offscreen;
 Image image2;
 Dimension dim;
 // This is the drawn map; 1 = redblock
 final short map[] ={
      1,1,1,1,1,1,1,1,1,1,
      1,0,0,0,0,0,0,0,0,1,
      1,0,0,0,0,0,0,0,0,1,
      1,0,0,0,0,0,0,0,0,1,
      1,0,0,0,0,0,0,0,0,1,
      1,0,0,0,0,0,0,0,0,1,
      1,0,0,0,0,0,0,0,0,1,
      1,0,0,0,0,0,0,0,0,1,
      1,0,0,0,0,0,0,0,0,1,
      1,1,1,1,1,1,1,1,1,1
      };

     public void init()
     {
        dim = getSize();
        setBackground(Color.black);
        offscreen = createImage(dim.width,dim.height);
       bufferGraphics = offscreen.getGraphics();

    image2 = createImage(32,32);
    Graphics test = image2.getGraphics();
    test.setColor(Color.red);
    test.fillRect(0,0,32,32);
    test.setColor(Color.black);
    test.drawRect(0,0,32,32);
     }

 public void paint(Graphics g)
 {
        bufferGraphics.clearRect(0,0,dim.width,dim.width);
  // Here the map is drawn
  int n=0;
  for(int x=0;x<=9;x++){
  for(int y=0;y<=9;y++){
   if(map[n]==1)           bufferGraphics.drawImage(image2,x*32,y*32,this);
   n++;
  }
  }
        bufferGraphics.setColor(Color.white);
        bufferGraphics.drawString("DrawTileMap Example",10,10);
        g.drawImage(offscreen,0,0,this);
 }



     public void update(Graphics g)
     {
          paint(g);
     }



 }
