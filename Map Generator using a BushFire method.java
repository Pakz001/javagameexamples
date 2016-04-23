
import java.awt.*;
import java.applet.*;
import java.util.Random;


public class BushFireMap02 extends Applet {

 int[][] map = new int[100][100];
 Random r = new Random();

 public void init() {
  makemap(0,0,r.nextInt(10)+15,r.nextInt(20)+60,80);
 }

 public void makemap(int x1,int y1, int numfires, int burncycles, int chanceofburning)
 {
  //System.out.println("" + numfires + "," + burncycles + ","+chanceofburning);
  //add trees to the map
  for(int y=0;y<100;y++){
  for(int x=0;x<100;x++){
   map[x][y]=1;
  }
  }
  // number of fires
  for(int an=0;an < numfires ;an++){
   map[r.nextInt(90)+5][r.nextInt(90)+5]=2;
  }


  // do the burn loop
  for(int burned=0;burned < burncycles ; burned++){
   for(int y=0;y<100;y++){
    for(int x=0;x<100;x++){
     // if fire on map location
     if(map[x][y]==2){
      //ignite around if tree there and random met
      if(x-1 >= 0){
       if(map[x-1][y]==1 && r.nextInt(100) > chanceofburning){
        map[x-1][y]=2;
        map[x][y]=0; // turn off fire
       }
      }
      if(y-1 >= 0){
       if(map[x][y-1]==1 && r.nextInt(100) > chanceofburning){
        map[x][y-1]=2;
        map[x][y]=0;
       }
      }
      if(x+1 < 100){
       if(map[x+1][y]==1 && r.nextInt(100) > chanceofburning){
        map[x+1][y]=2;
        map[x][y]=0;
       }
      }
      if(y+1 < 100){
       if(map[x][y+1]==1 && r.nextInt(100) > chanceofburning){
        map[x][y+1]=2;
        map[x][y]=0;
       }
      }

     }
    }
   }

  }
 }

 public boolean mouseUp( Event evt, int x, int y)
 {
  makemap(0,0,r.nextInt(10)+15,r.nextInt(20)+60,80);
  repaint();
  return true;
 }


 public void paint(Graphics g) {

  //g.drawString("Welcome to Java!!", 50, 60 );
  int w,h;
  w=getSize().width/100;
  h=getSize().height/100;
  for (int y=0;y<100;y++){
   for (int x=0;x<100;x++){
    if(map[x][y]==1){
     g.fillRect(x*w,y*h,w,h);
    }
   }
  }
 }
}
