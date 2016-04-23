
import java.awt.*;
import java.applet.*;
import java.util.ArrayList;

public class doubleArrayList01 extends Applet {
 ArrayList<Double> doubs = new ArrayList<Double>();

 public void init() {
  doubs.add(0.3);
  System.out.println(doubs.get(0));
 }

 public void paint(Graphics g) {

  g.drawString("Welcome to Java!!", 50, 60 );

 }
}
