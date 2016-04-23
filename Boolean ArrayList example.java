
import java.awt.*;
import java.applet.*;
import java.util.ArrayList;

public class BooleanArrayList01 extends Applet {

 ArrayList<Boolean> bools = new ArrayList<Boolean>();

 public void init() {

 bools.add(true);
 System.out.println(""+bools.get(0));

 }

 public void paint(Graphics g) {

  g.drawString("Welcome to Java!!", 50, 60 );

 }
} 
