
import java.awt.*;
import java.applet.*;

public class MathRandom extends Applet {

 public void init() {
 }

 public void paint(Graphics g) {

  g.drawString("Math random example",50,40);
  g.drawString("(int)(Math.Random() * 100) = "+(int)(Math.random()*100), 50, 60 );

 }
}

