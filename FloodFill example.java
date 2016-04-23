

import java.awt.*;
import java.applet.*;
import java.util.ArrayList;

public class FloodFill01 extends Applet {

 int mapwidth   = 100;
 int mapheight   = 100;
 int cellwidth   = 0;
 int cellheight   = 0;
 int[][] map   = new int[ mapwidth ][ mapheight ];

 public void init() {
  // I tried placing these lines in the main class but it
  // did not work...
  cellwidth   = getSize().width/100;
  cellheight   = getSize().height/100;
  // make a line on the map
  for(int x=0;x<50;x++){
   map[x][50]=1;
  }
  // make another line on the map
  for(int y=50;y<100;y++){
   map[50][y]=1;
  }
  // floodfill the map
  FloodFill(10,10);
 }

 public void paint(Graphics g) {
  g.drawString("FloodFill example..", 50, getSize().height-5 );
  for(int y = 0 ; y < mapheight ; y++){
   for(int x = 0 ; x < mapwidth ; x++){
    if(map[x][y]==1) g.fillRect(x*cellwidth,y*cellheight,cellwidth,cellheight);
   }
  }

 }

 public boolean FloodFill(int x, int y){
  // with these arraylists we store and process the unflooded
  ArrayList< Integer > x1 = new ArrayList< Integer >();
  ArrayList< Integer > y1 = new ArrayList< Integer >();
  ArrayList< Integer > x2 = new ArrayList< Integer >();
  ArrayList< Integer > y2 = new ArrayList< Integer >();
  // add start location to x1 list 1
  x1.add(x);
  y1.add(y);
  boolean doloop=true;
  // Here we flood the map
  while (doloop){
   // find new fill spaces..
   for(int i=0 ; i < x1.size() ;i++){
   for(int y3 = -1 ; y3 < 2 ; y3++){
   for(int x3 = -1 ; x3 < 2 ; x3++){
    int xtemp = x1.get(i);
    int ytemp = y1.get(i);
    if(xtemp + x3 >= 0 && xtemp + x3 < mapwidth){
    if(ytemp + y3 >=0 && ytemp + y3 < mapheight){
    if(map[ xtemp + x3 ][ ytemp + y3 ] == 0 ){
     x2.add( xtemp + x3 );
     y2.add(ytemp+y3);
     map[ xtemp + x3 ][ ytemp + y3 ]=1;
    }}}
   }}}
   // If there are no 0'ss left then stop loop by modyfing doloop variable
   if( x2.size() == 0 ) doloop=false;
   // erase old list
   x1.clear();
   y1.clear();
   // copy list 2 into list 1
   for (int ii = 0 ; ii < x2.size() ; ii++){
    x1.add(x2.get(ii));
    y1.add(y2.get(ii));
   }
   // erase list 2
   x2.clear();
   y2.clear();

  }

  return true;
 }



}

