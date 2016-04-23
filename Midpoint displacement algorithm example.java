

import java.awt.*;
import java.applet.*;
import java.util.Random;


public class MidPoint01 extends Applet {

 // In this array the drawing will be made and stored
 int[][] map = new int[100][100];
 // use r.nextInt() in the code for random numbers
 Random r = new Random();

 public void init() {
  // When starting up do the midpoint drawing one time
  domidpoint();
 }

 // Here the midpoint code begins.
 public void domidpoint(){
  // Erase the old map array..
  for(int y=0;y<100;y++){
   for(int x=0;x<100;x++){
    map[x][y]=0;
   }
  }
  // Setup points in the 4 corners of the map.
  map[0][0] = 128;
  map[99][0] = 128;
  map[99][99] = 128;
  map[0][99] = 128;
  // Do the midpoint
  midpoint(0,0,99,99);
 }

 // This is the actual mid point displacement code.
 public boolean midpoint(int x1,int y1, int x2, int y2 ){
  //  If this is pointing at just on pixel, Exit because
  //  it doesn't need doing}
   if(x2-x1<2 && y2-y1<2) return false;

  // Find distance between points and
  // use when generating a random number.
    int dist=(x2-x1+y2-y1);
    int hdist=dist / 2;
  // Find Middle Point
    int midx=(x1+x2) / 2;
    int midy=(y1+y2) / 2;
  // Get pixel colors of corners
    int c1=map[x1][y1];
    int c2=map[x2][y1];
    int c3=map[x2][y2];
    int c4=map[x1][y2];

  // If Not already defined, work out the midpoints of the corners of
  // the rectangle by means of an average plus a random number.
    if(map[midx][y1]==0) map[midx][y1]=((c1+c2+r.nextInt(dist)-hdist) / 2);
    if(map[midx][y2]==0) map[midx][y2]=((c4+c3+r.nextInt(dist)-hdist) / 2);
    if(map[x1][midy]==0) map[x1][midy]=((c1+c4+r.nextInt(dist)-hdist) / 2);
    if(map[x2][midy]==0) map[x2][midy]=((c2+c3+r.nextInt(dist)-hdist) / 2);

  // Work out the middle point...
    map[midx][midy] = ((c1+c2+c3+c4+r.nextInt(dist)-hdist) / 4);

  // Now divide this rectangle into 4, And call again For Each smaller
  // rectangle
    midpoint(x1,y1,midx,midy);
    midpoint(midx,y1,x2,midy);
    midpoint(x1,midy,midx,y2);
    midpoint(midx,midy,x2,y2);

  return true;
 }

 public boolean mouseUp( Event evt, int x, int y)
 {
  // redraw the applet with a new midpoint graphic each mouseclick.
  domidpoint();
  repaint();
  return true;
 }



 public void paint(Graphics g) {

  int x1=(getSize().width/2)-100;
  int y1=(getSize().height/2)-100;

  for(int y=0;y<100;y++){
   for(int x=0;x<100;x++){
    g.setColor(new Color(map[x][y],map[x][y],map[x][y]));
    g.fillRect(x*2+x1,y*2+y1,2,2);
   }
  }


 }
}
