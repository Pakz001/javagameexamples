

import java.awt.*;
import java.applet.*;

public class topdowndoorsandkeysexample001 extends Applet implements Runnable{

 Graphics bufferGraphics;
 Image offscreen;
 boolean ismovingleft;
 boolean ismovingright;
 boolean ismovingup;
 boolean ismovingdown;
 private int map[][] =  new int[][]{
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1},
 {1,0,1,1,1,1,0,1,1,1,0,0,1,1,1,0,1,1,0,1},
 {1,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,0,0,1},
 {1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,0,1,0,1},
 {1,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1},
 {1,0,1,0,2,0,0,1,0,2,0,0,1,0,2,0,0,1,0,1},
 {1,0,1,1,1,0,0,1,1,1,0,1,1,1,1,0,0,0,0,1},
 {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
 {1,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,1},
 {1,0,1,0,1,0,0,1,1,0,0,1,1,0,0,1,0,1,0,1},
 {1,0,0,0,1,0,0,1,0,0,0,0,1,0,0,1,0,0,0,1},
 {1,0,1,1,1,1,0,1,0,0,0,0,1,0,1,1,1,1,0,1},
 {1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1},
 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
 };
 int mapwidth = 20;
 int mapheight = 15;
 int cellwidth = 16;
 int cellheight = 16;
 int pwidth = 16;
 int pheight = 16;
 int playerx = 10*cellwidth;
 int playery = 13*cellheight;
 int numkeys = 4;
 int[][] keys = new int[ numkeys ][ 3 ]; // active, x , y
 int numdoors = 4;
 int[][] doors = new int[ numdoors ][ 3 ]; // active, x , y
 int playerkeys = 0;

 public void init() {
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();
        readkeysanddoors();
  new Thread(this).start();
 }

 public void readkeysanddoors(){
  for ( int y = 0 ; y < mapheight ; y++ ){
  for ( int x = 0 ; x < mapwidth ; x++ ){
   if ( map[ y ][ x ] == 2 ){ // if door
    int n = findfreedoor();
    doors[ n ][ 0 ] = 1;
    doors[ n ][ 1 ] = x;
    doors[ n ][ 2 ] = y;
    map[ y ][ x ] = 0;
   }
   if ( map[ y ][ x ] == 3 ){ // if key
    int n = findfreekey();
    keys[ n ][ 0 ] = 1;
    keys[ n ][ 1 ] = x;
    keys[ n ][ 2 ] = y;
    map[ y ][ x ] = 0;
   }
  }
  }
 }

 public int findfreedoor(){
  for ( int i = 0 ; i < numdoors ; i++ ){
   if ( doors[ i ][ 0 ] == 0 ){
    return i;
   }
  }
  return -1;
 }
 public int findfreekey(){
  for ( int i = 0 ; i < numkeys ; i++ ){
   if ( keys[ i ][ 0 ] == 0 ){
    return i;
   }
  }
  return -1;
 }

    public void run() {
        for(;;) { // animation loop never ends
   moveplayer();
    playerdoors();
   playerkeys();
         repaint();
         try {
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }

 public int isdoorcollision( int x , int y ){
  for ( int i = 0 ; i < numdoors ; i++ ){
   if ( doors[ i ][ 0 ] == 1 ){
    Rectangle rec1 = new Rectangle(  doors[ i ][ 1 ] * cellwidth ,
              doors[ i ][ 2 ] * cellheight ,
              cellwidth ,
              cellheight );
    Rectangle rec2 = new Rectangle(  x,
             y,
             cellwidth,
             cellheight);
    if(rec1.intersects(rec2)) return i;
   }
  }
  return -1; }

 public int iskeycollision( int x , int y ){
  for ( int i = 0 ; i < numkeys ; i++ ){
   if ( keys[ i ][ 0 ] == 1 ){
    Rectangle rec1 = new Rectangle(  keys[ i ][ 1 ] * cellwidth ,
              keys[ i ][ 2 ] * cellheight ,
              cellwidth ,
              cellheight );
    Rectangle rec2 = new Rectangle(  x,
             y,
             cellwidth,
             cellheight);
    if(rec1.intersects(rec2)) return i;
   }
  }
  return -1;
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

 public void playerkeys(){
  if ( ismovingleft ){
   int thekey = iskeycollision( playerx - 1 , playery );
   if ( thekey > -1 ){
    playerkeys++;
    keys[ thekey ][ 0 ] = 0;
   }
  }
  if ( ismovingright ){
   int thekey = iskeycollision( playerx + 1 , playery );
   if ( thekey > -1 ){
    playerkeys++;
    keys[ thekey ][ 0 ] = 0;
   }
  }
  if ( ismovingup ){
   int thekey = iskeycollision( playerx , playery - 1);
   if ( thekey > -1 ){
    playerkeys++;
    keys[ thekey ][ 0 ] = 0;
   }
  }
  if ( ismovingdown ){
   int thekey = iskeycollision( playerx , playery + 1 );
   if ( thekey > -1 ){
    playerkeys++;
    keys[ thekey ][ 0 ] = 0;
   }
  }

 }

 public void playerdoors(){
    if ( ismovingleft == true ){
   if ( playerkeys > 0 ){
    int thedoor = isdoorcollision( playerx - 1 , playery );
    if ( thedoor > -1 ){
     playerkeys--;
     doors[ thedoor ][ 0 ] = 0;
    }
   }
  }
  if ( ismovingright == true ){
   if ( playerkeys > 0 ){
    int thedoor = isdoorcollision( playerx + 1 , playery );
    if ( thedoor > -1 ){
     playerkeys--;
     doors[ thedoor ][ 0 ] = 0;
    }
   }
  }
  if ( ismovingup == true ){
   if ( playerkeys > 0 ){
    int thedoor = isdoorcollision( playerx , playery - 1 );
    if ( thedoor > -1 ){
     playerkeys--;
     doors[ thedoor ][ 0 ] = 0;
    }
   }
  }
  if ( ismovingdown == true ){
   if ( playerkeys > 0 ){
    int thedoor = isdoorcollision( playerx , playery + 1 );
    if ( thedoor > -1 ){
     playerkeys--;
     doors[ thedoor ][ 0 ] = 0;
    }
   }
  }

 }

    public void moveplayer(){
     if (  ismovingright == true &&
       ismapcollision( playerx + 1 , playery ) == false &&
       isdoorcollision( playerx + 1 , playery ) < 0 ){
      playerx++;
     }
     if ( ismovingup == true &&
       ismapcollision( playerx , playery - 1 ) == false &&
       isdoorcollision( playerx , playery - 1 ) < 0 ){
      playery--;
     }
     if ( ismovingdown == true &&
       ismapcollision( playerx , playery + 1 ) == false &&
       isdoorcollision( playerx , playery + 1 ) < 0 ){
      playery++;
     }
     if ( ismovingleft == true &&
       ismapcollision( playerx - 1 , playery ) == false &&
       isdoorcollision( playerx - 1 , playery ) < 0 ){
      playerx--;
     }


    }

     public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().height);

  // Draw map
  bufferGraphics.setColor(Color.white);
        for( int y = 0 ; y < mapheight ; y++ ){
         for ( int x = 0 ; x < mapwidth ; x++){
          if( map[y][x] == 1 ){
           bufferGraphics.fillRect( x * cellwidth , y * cellheight , cellwidth , cellheight );
          }
         }
        }

  // Draw doors
  bufferGraphics.setColor( Color.blue );
  for ( int i = 0 ; i < numdoors ; i++ ){
   if ( doors[ i ][ 0 ] == 1 ){
    bufferGraphics.fillRect(  doors[ i ][ 1 ] * cellwidth ,
           doors[ i ][ 2 ] * cellheight ,
           cellwidth ,
           cellheight );
   }
  }
  // Draw keys
  bufferGraphics.setColor( Color.yellow );
  for ( int i = 0 ; i < numkeys ; i++ ){
   if ( keys[ i ][ 0 ] == 1 ){
    bufferGraphics.fillOval( keys[ i ][ 1 ] * cellwidth ,
           keys[ i ][ 2 ] * cellheight ,
           cellwidth ,
           cellheight );
   }
  }

  // Draw player
     bufferGraphics.setColor(Color.red);
  bufferGraphics.fillOval(playerx,playery,cellwidth,cellheight);

        bufferGraphics.drawString("2D Topdown Doors and keys.",10,10);
        bufferGraphics.drawString("Keys : " + playerkeys , 200 , 10 );
  bufferGraphics.drawString("w/s/a/d = movement, yellow = key, blue = door.",10,237);

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
