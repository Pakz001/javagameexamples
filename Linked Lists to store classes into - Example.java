
import java.awt.*;
import java.applet.*;
import java.util.LinkedList;
import java.util.Random;

public class ListTest02 extends Applet {

 LinkedList enemies = new LinkedList();
 Random r1 = new Random();

 public void init()
 {
  // Test adding items to linked list
  for(int i = 0 ; i <= 30 ; i++){
   enemies.add(new theEnemies(r1.nextInt(200),r1.nextInt(200)));
  }
  // Test removing of item from linked list.
  enemies.remove(0);
 }

 public void paint(Graphics g)
 {

  g.drawString("Welcome to Java!!", 50, 60 );

  theEnemies theenemies;
  for (int i = 0 ; i < enemies.size();i++) {
   theenemies = (theEnemies)enemies.get(i);
   g.drawRect(theenemies.getx(),theenemies.gety(),32,32);
        }

 }

 class theEnemies
 {
  private int x;
  private int y;
  private int w;
  private int h;
  public theEnemies(int x1,int y1)
  {
   x=x1;
   y=y1;
  }
  public int getx()
  {
   return x;
  }
  public int gety()
  {
   return y;
  }
 }

}
