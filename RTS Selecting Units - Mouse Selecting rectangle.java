
import java.awt.*;
import java.applet.*;

public class RTSSelectingUnits01 extends Applet implements Runnable {
 // Graphics for double buffering.
 Graphics      bufferGraphics;
    Image       offscreen;
 int mousebutton =    0;
 int selx1 =     0;
 int sely1 =     0;
 int selx2 =     0;
 int sely2 =     0;
 boolean drawselectrect =  false;

 public void init() {
     setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();
     new Thread(this).start();

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
        selx2 = x;
        sely2 = y;
  return true;
 }

    public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Selecting Units RTS.",10,10);

  if(drawselectrect == true ){
   bufferGraphics.setColor(new Color(200,200,200));
   int tx1 = selx1;
   int ty1 = sely1;
   int tx2 = selx2;
   int ty2 = sely2;
   if (selx1 > selx2){
    tx1 = selx2;
    tx2 = selx1;
   }
   if (sely1 > sely2){
    ty1 = sely2;
    ty2 = sely1;
   }
   bufferGraphics.drawRect(tx1,ty1,tx2-tx1,ty2-ty1);
  }
  bufferGraphics.drawString(""+selx2+","+sely2,10,20);
       g.drawImage(offscreen,0,0,this);
    }

  public boolean mouseDown (Event e, int x, int y) {
        if (e.modifiers == Event.META_MASK) {
            //info=("Right Button Pressed");
         mousebutton = 2;
        } else if (e.modifiers == Event.ALT_MASK) {
            //info=("Middle Button Pressed");
        } else {
            //info=("Left Button Pressed");
            mousebutton = 1;
            selx1 = x;
            sely1 = y;
       drawselectrect = true;
        }

        return true;
    }

  public boolean mouseUp (Event e, int x, int y) {
        if (e.modifiers == Event.META_MASK) {
            //info=("Right Button Pressed");
         mousebutton = 0;
        } else if (e.modifiers == Event.ALT_MASK) {
            //info=("Middle Button Pressed");
        } else {
            //info=("Left Button Pressed");
            mousebutton = 0;
            drawselectrect = false;

       }
        return true;
    }
  public boolean mouseDrag(Event e, int x, int y){
   selx2 = x;
   sely2 = y;
   return true;
  }


}
