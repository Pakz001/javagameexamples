

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BallDrop extends JApplet implements MouseListener, MouseMotionListener, Runnable {
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

    public void init() {

        xpos = getSize().width/2-10;
        ypos = 0;
        height = appletSize_y - ypos;
        time = 0;
        radius = 20;
        appletSize_x = getSize().width;
        appletSize_y = getSize().height;
        addMouseListener(this);
        addMouseMotionListener(this);
  setBackground(Color.black);
      offscreen = createImage(getSize().width,getSize().height);
       bufferGraphics = offscreen.getGraphics();
        new Thread(this).start();
    }

    public void run() {

        for(;;) { // animation loop never ends
            if (isFalling) {
                if(height - radius > 0) {
                    height =  height + (.5 * GRAVITY * (time * time) );
                    ypos = appletSize_y - height;
                    time += .02;
                } else {
                    isFalling = false;
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
        bufferGraphics.drawString("Balldrop - press mouse to drop/redrop",10,10);
        int x = (int) xpos;
        int y = (int) ypos;
        bufferGraphics.fillOval (x, y, 2 * radius, 2 * radius);
   g.drawImage(offscreen,0,0,this);
    }
    //Listeners
    public void mousePressed (MouseEvent e) {}
    public void mouseDragged (MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {
        ypos = 0;
        height = appletSize_y - ypos;
        if (height > 0) { isFalling = true; }
    }
    public void mouseExited(MouseEvent e)  {}
    public void mouseMoved   (MouseEvent e) {}
    public void mouseEntered (MouseEvent e) {}
    public void mouseClicked (MouseEvent e) {}
}
