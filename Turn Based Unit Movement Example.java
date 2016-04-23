

import java.awt.*;
import java.applet.*;

public class turnbased_example extends Applet  implements Runnable{

 Graphics bufferGraphics;
    Image offscreen;
    int numunits = 5;
    int unitwidth = 16;
    int unitheight = 16;
    int blinkingunit = 0;
 long blinktime;
 int gameturn = 0;
 boolean endofturn = false;
 int[][] unit = new int[numunits][10]; //unit , exists x y visible moves

 public void init() {
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();

        // create units
        for (int i = 0 ; i < numunits ; i++){
         unit[i][0] = 1;
         findgoodunitpos(i);
   unit[i][3] = 1;
   unit[i][4] = 2;
        }
  new Thread(this).start();
 }

 public void findgoodunitpos(int i){

     boolean exitloop = false;
     boolean unitontop = false;
     int newx = 0;
     int newy = 0;
     while ( exitloop == false ) {
      newx = (int)(Math.random()* 15 );
         newy = (int)((Math.random()* 12) + 1);
   // is unit not ontop of other unit then exit loop
   unitontop = false;
       for (int ii = 0 ; ii < numunits ; ii++){
        if (newx == unit[ii][1] && newy == unit[ii][2]){
         unitontop = true;
        }
       }
       if (unitontop == false ) exitloop = true;
     }
     unit[i][1] = newx;
     unit[i][2] = newy;
 }

 public void dounitblinking(){
  if ( System.currentTimeMillis() > blinktime ){
   if ( unit[blinkingunit][3] == 1 ) {
    unit[blinkingunit][3] = 0 ;
   } else {
    unit[blinkingunit][3] = 1;
   }
   blinktime = System.currentTimeMillis() + 500;
  }
 }
 public void resetblink(){
   blinktime = System.currentTimeMillis() + 500;
   unit[blinkingunit][3] = 1;
 }

    public void run() {
        for(;;) { // animation loop never ends
         repaint();
         try {
          if ( endofturn == false ) {
              dounitblinking();
          }
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }
     public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().height);
     bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Turn based movement Example.",10,10);
        bufferGraphics.drawString("w/s/a/d to move unit. click on unit to activate.",10,237);
        bufferGraphics.drawString("Unit : " + blinkingunit , getSize().width - 64 , 54);
        bufferGraphics.drawString("X : " + unit[blinkingunit][1] , getSize().width - 64 , 64);
        bufferGraphics.drawString("Y : " + unit[blinkingunit][2] , getSize().width - 64 , 74);
  bufferGraphics.drawString("Moves : " + unit[blinkingunit][4] , getSize().width - 64 , 84);
  bufferGraphics.drawString("Turn : " + gameturn , getSize().width - 64 , 94);
        bufferGraphics.drawRect(0,16,getSize().width-64,getSize().height-32);


  // if end of turn
  if ( endofturn == true ) {
   bufferGraphics.drawString("End of Turn" , getSize().width - 64 , 124);
   bufferGraphics.drawString("Press Enter" , getSize().width - 64 , 134);
  }

        // Draw units
        for (int i = 0 ; i < numunits ; i++){
         if ( unit[i][0] == 1 && unit[i][3] == 1){
          bufferGraphics.fillRect(unit[i][1] * unitwidth ,
                unit[i][2] * unitheight ,
                unitwidth ,
                unitheight);
         }
        }

        g.drawImage(offscreen,0,0,this);
     }
    public boolean occupied(int x, int y){
     for (int i = 0 ; i < numunits ; i++){
      if ( unit[i][1] == x ){
      if ( unit[i][2] == y ){
       return true;
      }
      }
     }
     return false;
    }


    public boolean nextmovableunit(){
     for ( int i = 0 ; i < numunits ; i++ ) {
      if ( unit[i][4] > 0 ) {
       resetblink();
       blinkingunit = i;
       resetblink();
       return true;
      }
     }
     return false;
    }


 public void nextturn(){
  boolean allmovesgone = true;
  for ( int i = 0 ; i < numunits ; i++ ) {
   if ( unit[i][4] > 0 ) {
    allmovesgone = false;
   }
  }
  if ( allmovesgone == true ) {
   endofturn = true;
  }
 }

 public boolean keyUp (Event e, int key){
        //System.out.println (" Integer Value: " + key);

  if ( endofturn == true ) {
   if( key == 10 ) // Return/Enter key / end of turn
         {
          gameturn++;
    for ( int i = 0 ; i < numunits ; i++ ) {
     unit[i][4] = 2;
    }
    nextmovableunit();
          endofturn = false;
         }
  }
  if ( endofturn == false ) {

     if( key == 119 ) // w key , up
         {
          if (unit[blinkingunit][2] > 1 &&
           occupied(unit[blinkingunit][1],unit[blinkingunit][2]-1) == false &&
           unit[blinkingunit][4] > 0 ) {
           unit[blinkingunit][2]--;
           unit[blinkingunit][4]--;
           resetblink();
     if (unit[blinkingunit][4] == 0 ){
      nextmovableunit();
     }
     nextturn();
           }
         }
         if( key == 97 ) // a key , left
         {
          if (unit[blinkingunit][1] > 0 &&
            occupied(unit[blinkingunit][1]-1,unit[blinkingunit][2]) == false &&
           unit[blinkingunit][4] > 0 ) {
           unit[blinkingunit][1]--;
           unit[blinkingunit][4]--;
           resetblink();
     if (unit[blinkingunit][4] == 0 ){
      nextmovableunit();
     }
     nextturn();
           }
          }
          if( key == 100 ) // d key , right
          {
           if (unit[blinkingunit][1] < 15 &&
           occupied(unit[blinkingunit][1]+1,unit[blinkingunit][2]) == false &&
            unit[blinkingunit][4] > 0 ) {
           unit[blinkingunit][1]++;
           unit[blinkingunit][4]--;
           resetblink();
     if (unit[blinkingunit][4] == 0 ){
      nextmovableunit();
     }
     nextturn();
          }
         }
         if( key == 115 ) // s key , down
          {
           if ( unit[blinkingunit][2] < 13 &&
           occupied(unit[blinkingunit][1],unit[blinkingunit][2]+1) == false &&
           unit[blinkingunit][4] > 0 ) {
           unit[blinkingunit][2]++;
           unit[blinkingunit][4]--;
           resetblink();
     if (unit[blinkingunit][4] == 0 ){
      nextmovableunit();
     }
     nextturn();
          }
         }
  } // end of endofturn boolean
        return true;
 }

  public boolean mouseUp (Event e, int x, int y) {
        if (e.modifiers == Event.META_MASK) {
            //info=("Right Button Pressed");
        } else if (e.modifiers == Event.ALT_MASK) {
            //info=("Middle Button Pressed");
        } else {
            //info=("Left Button Pressed");
   int mx = x / unitwidth;
   int my = y / unitheight;
   for ( int i = 0 ; i < numunits ; i++ ) {
    if (unit[i][1] == mx && unit[i][2] == my ) {
     resetblink();
     blinkingunit = i;
     resetblink();
    }
   }
        }
        return true;
    }


}
