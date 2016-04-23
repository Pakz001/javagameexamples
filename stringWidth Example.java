
import java.awt.*;
import java.applet.*;

public class stringWidthExample001 extends Applet implements Runnable{
 Graphics bufferGraphics;
    Image offscreen;
 FontMetrics fm;

 public void init() {
  Graphics g;
  g=getGraphics();
  fm = g.getFontMetrics();
  setBackground(Color.black);
     offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
     // Start the runnable thread.
  new Thread(this).start();
 }
    public void run() {
     for(;;) { // animation loop never ends
         repaint();
         try {
             Thread.sleep(16);
            }
             catch (InterruptedException e) {
             }
     }
    }
    public void update(Graphics g){
     String s;
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Stringwidth Example",10,10);
        bufferGraphics.setColor(Color.white);
        s = "This text";
  bufferGraphics.drawString(s,(this.getSize().width-fm.stringWidth(s)) / 2, 80);
        s = "is centered";
  bufferGraphics.drawString(s,(this.getSize().width-fm.stringWidth(s)) / 2, 90);
  s = "on the screen";
  bufferGraphics.drawString(s,(this.getSize().width-fm.stringWidth(s)) / 2, 100);


  // Draw the Graphics buffer
       g.drawImage(offscreen,0,0,this);
    }
}
