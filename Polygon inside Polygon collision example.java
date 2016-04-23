
import java.applet.Applet;
import java.awt.geom.Area;
import java.awt.*;

public class PolygonPolygonCollision extends Applet {

  public void paint (Graphics g) {
   int movex=100;
   int movey=100;
    int[] XArray = {20, 160, 120, 160, 20, 60};
    int[] YArray = {20, 20, 90, 160, 160, 90};
    int[] XArray2 = {20+movex, 160+movex, 120+movex, 160+movex, 20+movex, 60+movex};
    int[] YArray2 = {20+movey, 20+movey, 90+movey, 160+movey, 160+movey, 90+movey};

    Polygon abc = new Polygon(XArray, YArray, 6);
    Polygon abc2= new Polygon(XArray2,YArray2,6);
    g.drawPolygon(abc);
    g.drawPolygon(abc2);

   Area a1 = new Area(abc);
   Area a2 = new Area(abc2);

 a1.intersect(a2);
 if (!a1.isEmpty())
  g.drawString("Two Polygons collided", 20, 20 );
  }

}
