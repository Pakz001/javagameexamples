
import java.awt.*;
import java.applet.*;

public class changepolygonpoints extends Applet {

 public void init() {
 }

 public void paint(Graphics g) {

  g.drawString("Modifying polygons", 50, 60 );

     Polygon p = new Polygon(new int[]{100,100,150},new int[]{100,200,150},3);

       g.drawPolygon(p);

  //loop through the x array of the polygon
       for(int x=0;x < p.npoints ; x++)
        {
         p.xpoints[x] = p.xpoints[x]+100;
       }
       g.drawPolygon(p);

 }
}
