
import java.awt.*;
import java.applet.*;
import java.util.Random;


public class MapClustering01 extends Applet {

 Random r = new Random();
 int mapwidth = 320;
 int mapheight = 200;
 int[][] map = new int[mapwidth][mapheight];
 String info = "";

 public void init() {
  generateclustermap(r.nextInt(5),r.nextInt(2));
 }

 public void generateclustermap(int passes, int mode){
  passes++;
  System.out.println("Mode : " + mode + " Passes :"+passes);
  info = "Mode : " + mode + " Passes :"+passes;
  //
  // I modified the formula, i do not add 0 to the
  // map. where I read about the forumula it said
  // to fill the map with 0 1 and 3 but the map
  // looks neater when only using 1 and 2.

  // fill map with random 1's and 2's.
  for (int y = 0 ; y < mapheight ; y++ ){
   for (int x = 0 ; x < mapwidth ; x++ ){
    map[x][y] = r.nextInt(2)+1;
    if (mode == 1 && r.nextInt(15) < 1 ) map[x][y]=1;

   }
  }

  // cluster
  for(int i = 0 ; i < passes ; i++){
   int bcolor = 0;
   int scount = 0;
   for (int y = 0 ; y < mapheight ; y++ ){
    for (int x = 0 ; x < mapwidth ; x++ ){
     scount = 0;
     bcolor = map[x][y];
     for (int y1 = -1 ; y1 < 2 ; y1++ ){
      for (int x1 = -1 ; x1 < 2 ; x1++ ){
       if ( x + x1 >= 0 && x + x1 < mapwidth && y + y1 >= 0 && y + y1 < mapheight ){
        if (map[x+x1][y+y1] == bcolor ) scount++;
       }
      }
     }
     if( scount < 5 && bcolor == 1 ) map[x][y] = 2;
     if( scount < 5 && bcolor == 2 ) map[x][y] = 1;
    }
   }
  }

 }


 public boolean mouseUp( Event evt, int x, int y){
  generateclustermap(r.nextInt(5),r.nextInt(2));
  repaint();
  return true;
 }

 public void paint(Graphics g) {

  g.drawString("Click to create new image..", 10, getSize().height-5 );
  g.drawString(info, 10, getSize().height-25 );
  int sx = getSize().width / mapwidth;
  int sy = getSize().height / mapheight;
  for(int y = 0 ; y < mapheight ; y++ ){
   for( int x = 0 ; x < mapwidth ; x++){
    if ( map[x][y] == 1 ) g.setColor(Color.black);
    if ( map[x][y] == 2 ) g.setColor(Color.white);
    g.fillRect(x*sx,y*sy,sx,sy);
   }
  }
 }
}
