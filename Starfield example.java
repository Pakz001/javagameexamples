

import java.awt.*;
import java.applet.*;

public class StarField01 extends Applet implements Runnable{

 int         numStars;
 Point[]     stars;
 Graphics     bufferGraphics;
    Image      offscreen;


 public void init() {
  setBackground(Color.black);
     numStars = 100;
     stars = new Point[numStars];
     for (int i = 0; i < numStars; i++)
       stars[i] = new Point((int) (Math.random() * getSize().width), (int) (Math.random() * getSize().height));
    offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
  new Thread(this).start();

 }

    public void run() {
     for(;;) { // animation loop never ends
         repaint();
         try {
                for (int i = 0; i < numStars; i++)
                {
                 stars[i].x -= 1;
                 if (stars[i].x < 0) stars[i].x = getSize().width;
                }

             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }

    public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Starfield Example.",20,30);
      bufferGraphics.setColor(Color.white);
      for (int i = 0; i < numStars; i++)
        bufferGraphics.drawLine(stars[i].x, stars[i].y, stars[i].x, stars[i].y);

      g.drawImage(offscreen,0,0,this);
    }


}
