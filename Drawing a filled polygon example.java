
 
import java.applet.Applet;
import java.awt.*;
public class Polygon1 extends Applet {
  public void paint (Graphics g) {
    int[] XArray = {20, 160, 120, 160, 20, 60};
    int[] YArray = {20, 20, 90, 160, 160, 90};
    g.fillPolygon (XArray, YArray, 6);
  }
}
