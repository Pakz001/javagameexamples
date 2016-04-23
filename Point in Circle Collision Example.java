
import java.awt.*;
import java.applet.*;

public class PointInCircleCollision001 extends Applet {

 int pointx = 50;
 int pointy = 50;
 int circlex = 50;
 int circley = 50;
 int circlew = 150;

 public void init() {
 }
 // x point , y point, circlex, circley, circle size
 public boolean pointincircle( int x , int y , int cx , int cy , int cw ){
  int x1 = x;
  int y1 = y;
  int x2 = cx + cw/2;
  int y2 = cy + cw/2;
  boolean retval = false;
  int distance=(int)Math.sqrt( ( x1 - x2 ) * ( x1 - x2 ) + ( y1 - y2 ) * ( y1 - y2 ) ) ;
  if ( distance < cw/2 ) {
   retval = true;
  }
  return retval;
 }

 public void paint(Graphics g) {
        g.setColor(Color.red);
  g.drawString("Point in Circle Collision example.", 10, 10 );
        g.setColor(Color.black);
  g.fillOval(circlex,circley,circlew,circlew);
        g.setColor(Color.yellow);
        g.fillRect(pointx-5,pointy-5,10,10);
        g.setColor(Color.black);
  if ( pointincircle( pointx , pointy , circlex , circley , circlew ) == true ) {
   g.drawString("Collision.", 10, 210 );
  }else{
   g.drawString("No Collision.", 10, 210 );
  }
 }
}
