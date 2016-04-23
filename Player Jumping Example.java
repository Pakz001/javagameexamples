
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Jumping extends JApplet implements Runnable,KeyListener {
    Graphics bufferGraphics;
 Image offscreen;

    private double xpos;
    private double ypos;
    private int radius;
    private int appletSize_x;
    private int appletSize_y;
    private double time;
    private double height;
    private final int GRAVITY = -10;
    private boolean isFalling = false;
 private boolean isJumping = false;

    public void init() {

  addKeyListener(this);
        radius = 20;
        xpos = getSize().width/2-10;
        ypos = getSize().height-radius*2;
        height = appletSize_y - ypos;
        time = 0;
        appletSize_x = getSize().width;
        appletSize_y = getSize().height;
  setBackground(Color.black);
  offscreen = createImage(getSize().width,getSize().height);
  bufferGraphics = offscreen.getGraphics();
        new Thread(this).start();
    }

    public void run() {

        for(;;) { // animation loop never ends
            if (isJumping) {
                if(height - radius < getSize().height-50) {
              height =  height - (.5 * GRAVITY * (time * time) );
                 ypos = appletSize_y - height;
                 time += .02;
                } else {
                 isJumping = false;
                 isFalling = true;
                 time=0;
                }
            }
            if (isFalling) {
                if(height - radius > radius) {
                    height =  height + (.5 * GRAVITY * (time * time) );
                    ypos = appletSize_y - height;
                    time += .02;
                } else {
                    isFalling = false;
                    ypos = getSize().height-radius*2;
                    time = 0;
                }
            }
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }

    //Overriding the paint method
    public void paint(Graphics g) {
  bufferGraphics.clearRect(0, 0, getSize().width,getSize().height);
        bufferGraphics.setColor (Color.RED);
        bufferGraphics.drawString("Jump Example - Press Spacebar to Jump",10,10);
        int x = (int) xpos;
        int y = (int) ypos;
        bufferGraphics.fillOval (x, y, 2 * radius, 2 * radius);
  g.drawImage(offscreen,0,0,this);
    }

 public void keyPressed(KeyEvent e)
 {
  int keyCode = e.getKeyCode();
  if(keyCode == KeyEvent.VK_SPACE)
  {
   if(isFalling==false && isJumping==false)  {
          height = appletSize_y - ypos;
    isJumping = true;
    time=1;
   }
  }
 }

 public void keyReleased(KeyEvent e)
 {
 // do nothing...
 }

 public void keyTyped(KeyEvent e)
 {
 }

}
