 
import java.awt.*;
import java.applet.*;

public class RectangleCollision extends Applet {
 public void init() {
 }
 public void paint(Graphics g) {
  Rectangle rec1 = new Rectangle(10,10,20,20);
  Rectangle rec2 = new Rectangle(15,15,20,20);
  g.fillRect(rec1.x,rec1.y,rec1.width,rec1.height);
  g.fillRect(rec2.x,rec2.y,rec2.width,rec2.height);
  if(rec1.intersects(rec2))
  g.drawString("Filled Rectangle Collision", 50, 60 );
 }
}
