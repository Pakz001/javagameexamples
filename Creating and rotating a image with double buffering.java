
import java.awt.geom.AffineTransform;
import java.applet.*;
import java.awt.event.*;
import java.awt.*;

public class rotateimage01 extends Applet implements Runnable
{
 Graphics bufferGraphics;
    Image offscreen;
    Image image2;
 int im2angle=0;

     public void init()
     {
      setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();

    image2 = createImage(32,32);
    Graphics test = image2.getGraphics();
    test.setColor(Color.red);
    test.fillRect(0,0,32,32);
  new Thread(this).start();
     }

    public void run() {

        for(;;) { // animation loop never ends
            im2angle++;
            if(im2angle>360) im2angle=0;
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }

   public void paint(Graphics g)
   {
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Rotate Image Example",10,10);
  // I have no idea if recreating this section slows down
  // the program. maybe it should be done different.
  Graphics2D  graphics = (Graphics2D) offscreen.getGraphics();
       graphics.rotate (Math.toRadians(im2angle), 60+32/2, 60+32/2);
       graphics.drawImage(image2, 60, 60, this);
       graphics.dispose();

    bufferGraphics.drawImage(image2,10,20,this);
       g.drawImage(offscreen,0,0,this);

     }



    public void update(Graphics g)
    {
          paint(g);
    }



 }

