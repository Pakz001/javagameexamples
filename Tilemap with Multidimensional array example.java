

import java.awt.*;
import java.applet.*;

public class Pathfinding01 extends Applet {

 short map[][] ={{0,0,0,0,1,0,0,0,0,0},
     {0,0,0,0,1,0,1,1,1,0},
     {0,1,1,1,1,0,1,0,1,0},
     {0,0,0,0,1,0,1,0,1,0},
     {0,1,1,0,1,0,1,0,1,0},
     {0,0,1,0,1,0,1,0,1,0},
     {0,0,1,0,0,0,1,0,1,0},
     {0,0,1,1,1,1,1,0,1,0},
     {0,0,0,0,0,0,0,0,0,0},
     {0,0,0,0,0,0,0,0,0,0}};

 public void init() {
 }

 public void paint(Graphics g) {

  //g.drawString("Welcome to Java!!", 50, 60 );
  for(int x = 0; x < map.length; x++){
            for(int y = 0; y < map[0].length; y++){
             if(map[x][y]==1){
              g.fillRect(x*32,y*32,32,32);
             }
            }}
 }
}
