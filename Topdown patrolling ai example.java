

import java.awt.*;
import java.applet.*;

public class topdownpatrollingaiexample001 extends Applet implements Runnable{

 Graphics bufferGraphics;
 Image offscreen;
 boolean ismovingleft;
 boolean ismovingright;
 boolean ismovingup;
 boolean ismovingdown;
 int mapwidth = 20;
 int mapheight = 15;
 int cellwidth = 16;
 int cellheight = 16;
 private int map[][] =  new int[][]{
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,0,1,1,1,1,0,1,0,0,0,0,1,0,1,1,1,1,0,1},
 {1,0,0,0,1,0,0,1,0,0,0,0,1,0,0,1,0,0,0,1},
 {1,0,1,0,1,0,0,1,1,0,0,1,1,0,0,1,0,1,0,1},
 {1,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,1},
 {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
 {1,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,0,0,0,1},
 {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
 {1,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,1},
 {1,0,1,0,1,0,0,1,1,0,0,1,1,0,0,1,0,1,0,1},
 {1,0,0,0,1,0,0,1,0,0,0,0,1,0,0,1,0,0,0,1},
 {1,0,1,1,1,1,0,1,0,2,0,0,1,0,1,1,1,1,0,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 };
 int playerstartpositionx = playerx = 10*cellwidth;
 int playerstartpositiony = playery = 13*cellheight;
 int playerx = playerstartpositionx;
 int playery = playerstartpositiony;
 // For the pattern movement ai
 int numai = 10;
 int ai[][] = new int[ numai ][ 10 ]; // ,active,x,y,direction[0-up,1-right,2-down,3-left]
 public void init() {
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();
        // initiate ai
        ai[ 0 ][ 0 ] = 1;
        ai[ 0 ][ 1 ] = 1 * cellwidth;
        ai[ 0 ][ 2 ] = 1 * cellheight;
        ai[ 0 ][ 3 ] = 1;
  //6 2
  ai[ 1 ][ 0 ] = 1;
  ai[ 1 ][ 1 ] = 6 * cellwidth;
  ai[ 1 ][ 2 ] = 2 * cellheight;
  ai[ 1 ][ 3 ] = 2;
  // 13 10
  ai[ 2 ][ 0 ] = 1;
  ai[ 2 ][ 1 ] = 13 * cellwidth;
  ai[ 2 ][ 2 ] = 10 * cellheight;
  ai[ 2 ][ 3 ] = 2;
  new Thread(this).start();
 }
    public void run() {
        for(;;) { // animation loop never ends
   moveplayer();
   updateai();
         repaint();
         try {
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }

 public void updateai(){
  for ( int i = 0 ; i < numai ; i++ ){
   if ( ai[ i ][ 0 ] == 1 ){
    // Movement up ( 0 )
    if ( ai[ i ][ 3 ] == 0 ){
     if ( ismapcollision( ai[ i ][ 1 ] , ai[ i ][ 2 ] - 1 ) ){
      ai[ i ][ 3 ] = 2;
     }else{
      ai[ i ][ 2 ]--;
     }
    }
    // Movement right ( 1 )
    if ( ai[ i ][ 3 ] == 1 ){
     if ( ismapcollision( ai[ i ][ 1 ] + 1 , ai[ i ][ 2 ] ) ){
      ai[ i ][ 3 ] = 3;
     }else{
      ai[ i ][ 1 ]++;
     }
    }
    // Movement down ( 2 )
    if ( ai[ i ][ 3 ] == 2 ){
     if ( ismapcollision( ai[ i ][ 1 ] , ai[ i ][ 2 ] + 1 ) ){
      ai[ i ][ 3 ] = 0;
     }else{
      ai[ i ][ 2 ]++;
     }
    }
    // Movement left ( 3 )
    if ( ai[ i ][ 3 ] == 3 ){
     if ( ismapcollision( ai[ i ][ 1 ] - 1 , ai[ i ][ 2 ] ) ){
      ai[ i ][ 3 ] = 1;
     }else{
      ai[ i ][ 1 ]--;
     }
    }
   // Collision with the ai
   Rectangle rec1 = new Rectangle(  playerx ,
             playery ,
             cellwidth ,
             cellheight );
   Rectangle rec2 = new Rectangle(  ai[ i ][ 1 ],
            ai[ i ][ 2 ],
            cellwidth,
            cellheight);
   if( rec1.intersects( rec2 ) ){
     playerx = playerstartpositionx;
     playery = playerstartpositiony;
   }

   }
  }
 }

    public boolean ismapcollision(int x, int y){

  int pcx = x / cellwidth;
  int pcy = y / cellheight;
  for (int y1 = pcy - 1 ; y1 < pcy + 2 ; y1++){
   for (int x1 = pcx - 1 ; x1 < pcx + 2 ; x1++){
    if( x1 >= 0 && x1 < mapwidth && y1 >= 0 && y1 < mapheight ){
     if ( map[y1][x1] == 1 ){
      Rectangle rec1 = new Rectangle(  x1 * cellwidth ,
                y1 * cellheight ,
                cellwidth ,
                cellheight );
      Rectangle rec2 = new Rectangle(  x,
               y,
               cellwidth,
               cellheight);
      if(rec1.intersects(rec2)) return true;
     }
    }
   }
  }
  return false;
    }

    public void moveplayer(){
     if (ismovingright == true && ismapcollision(playerx + 1,playery) == false){
      playerx++;
     }
     if (ismovingup == true && ismapcollision(playerx,playery-1) == false){
      playery--;
     }
     if (ismovingdown == true && ismapcollision(playerx,playery+1) == false){
      playery++;
     }
     if (ismovingleft == true && ismapcollision(playerx-1,playery) == false){
      playerx--;
     }

    }

     public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().height);
  // Draw map
        for( int y = 0 ; y < mapheight ; y++ ){
         for ( int x = 0 ; x < mapwidth ; x++){
          // walls
          if( map[y][x] == 1 ){
     bufferGraphics.setColor( Color.white );
           bufferGraphics.fillRect( x * cellwidth , y * cellheight , cellwidth , cellheight );
          }
         }
        }
  // Draw ai
  for ( int i = 0 ; i < numai ; i++ ){
   if ( ai[ i ][ 0 ] == 1 ){
    bufferGraphics.setColor( Color.blue );
    bufferGraphics.fillOval(  ai[ i ][ 1 ] ,
           ai[ i ][ 2 ] ,
           cellwidth ,
           cellheight );
   }
  }

     bufferGraphics.setColor(Color.red);

  bufferGraphics.fillOval(playerx,playery,cellwidth,cellheight);

        bufferGraphics.drawString("2D Topdown Patrolling ai.",10,10);
  bufferGraphics.drawString("w/s/a/d to move player.",10,237);

        g.drawImage(offscreen,0,0,this);
     }
  public boolean keyDown (Event e, int key){
   if(key==97)
        {
         ismovingleft = true;
        }
        if(key==100)
        {
         ismovingright = true;
        }
        if(key==119)
        {
         ismovingup = true;
        }
        if(key==115)
        {
         ismovingdown = true;
        }

   return true;
  }
 public boolean keyUp (Event e, int key){

   if(key==97)
        {
         ismovingleft = false;
        }
        if(key==100)
        {
         ismovingright = false;
        }
        if(key==119)
        {
         ismovingup = false;
        }
        if(key==115)
        {
         ismovingdown = false;
        }
//  System.out.println(""+key);
  return true;
 }


}

