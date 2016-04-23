

import java.awt.*;
import java.applet.*;

public class gamemenuexample001 extends Applet implements Runnable{

 Graphics bufferGraphics;
 Image offscreen;
 FontMetrics fm;
 int nummenuitems = 4;
 String gamemenu[] = new String[ nummenuitems ];
 int menuchoice = 0;

 public void init() {
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();
  Graphics g;
  g = getGraphics();
  fm = g.getFontMetrics();
        gamemenu[ 0 ] = "New Game";
        gamemenu[ 1 ] = "Load Game";
        gamemenu[ 2 ] = "Options";
        gamemenu[ 3 ] = "Credits";
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



     public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().height);
     bufferGraphics.setColor(Color.red);

  String s = "";
  for ( int i = 0 ; i < nummenuitems ; i++ ){
   if ( i == menuchoice ){
          s = "-> " + gamemenu[ i ];
    bufferGraphics.drawString(s,(this.getSize().width-fm.stringWidth(s)) / 2, 80 + i * 20 );
   }else{
    s = gamemenu[ i ];
    bufferGraphics.drawString(s,(this.getSize().width-fm.stringWidth(s)) / 2, 80 + i * 20 );
   }
  }

        bufferGraphics.drawString("Game Menu Screen.",10,10);
  bufferGraphics.drawString("w/s to move cursor.",10,237);

        g.drawImage(offscreen,0,0,this);
     }
  public boolean keyDown (Event e, int key){

   return true;
  }
 public boolean keyUp (Event e, int key){

  // key w
   if ( key == 119 ){
       if ( menuchoice > 0 ){
        menuchoice--;
       }
        }
        // key s
        if ( key == 115 ){
         if ( menuchoice < nummenuitems - 1 ){
          menuchoice++;
         }
        }
  //System.out.println(""+key);
  return true;
 }


}

