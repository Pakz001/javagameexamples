

import java.awt.*;
import java.applet.*;

public class ScrollTextExample extends Applet implements Runnable{

 Graphics bufferGraphics;
    Graphics scrolltextGraphics;
    Image offscreen;
 Image scrolltextimage;
 String message = "This is a scroll text message made in java. There is not much to report...      ";
 int scrolltextloc =0;
 int scrolltextoffset = 0;


 public void init() {
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        scrolltextimage = createImage(getSize().width+12, 12);
        scrolltextGraphics = scrolltextimage.getGraphics();
        bufferGraphics = offscreen.getGraphics();
  new Thread(this).start();

 }
    public void run() {
        for(;;) { // animation loop never ends
         repaint();
         try {
    scrolltext();
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }

    public void scrolltext(){
  scrolltextoffset--;
  if ( scrolltextoffset < 0 ) {
   scrolltextoffset = 12;
   scrolltextloc++;
   if ( scrolltextloc > message.length()-1) scrolltextloc = 0;
  }
     scrolltextGraphics.clearRect(0,0,getSize().width+12,12);
  scrolltextGraphics.setColor(Color.white);
  int stloc = scrolltextloc;
  String temp;
  for ( int i = 0 ; i < 30 ; i++)
  {
   temp = message.substring(stloc,stloc+1);
   scrolltextGraphics.drawString(temp,i*12+scrolltextoffset,10);
   stloc++;
   if(stloc > message.length()-1) stloc = 0;
  }

    }

     public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().height);
     bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("ScrollText Example",10,10);

  //scrolltextGraphics.clearRect(0,0,getSize().width+12,12);
  //scrolltextGraphics.setColor(Color.white);
  //scrolltextGraphics.drawString(message,0,10);




  bufferGraphics.drawImage(scrolltextimage,0,100,this);

        g.drawImage(offscreen,0,0,this);
     }

}
