import java.awt.*;
import java.applet.*;

public class CurrentTimeMilliseconds01 extends Applet {

 public void init() {
 }

 public void paint(Graphics g) {
  g.drawString("Getting the milliseconds.",20,40);
  g.drawString("System.currentTimeMillis() : " + System.currentTimeMillis(), 20, 60 );

 }
}
