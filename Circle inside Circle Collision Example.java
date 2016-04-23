 
import java.awt.*;
import java.applet.*;

public class CircleCircleCollision extends Applet
{
        public void init()
        {
        }

        public void paint(Graphics g){
         int x1=150;
         int y1=150;
         int x2=120;
         int y2=160;
        int dist;
            Point p1=new Point(x1,y1);
            Point p2=new Point(x2,y2);
            g.drawOval(p1.x,p1.y,40,40);
            g.drawOval(p2.x,p2.y,40,40);
   dist=(int)Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
            if(dist<=40)
            g.drawString("Collision!", 10, 20);
        }
}
