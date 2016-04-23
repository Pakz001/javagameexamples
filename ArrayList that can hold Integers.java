
import java.awt.*;
import java.applet.*;
import java.util.ArrayList;


public class intarraylist03 extends Applet {

 public void init() {
  ArrayList< Integer > list=new ArrayList< Integer >();
        int arr1[]={11,12,13,14,15};
        for (int i = 0; i < arr1.length; i++) {
         list.add(new Integer(arr1[i]));
        }
        for (int i = 0; i < arr1.length; i++) {
        System.out.println((i+1)+" : "+(list.get(i)+10));
        }
 }

 public void paint(Graphics g) {

  g.drawString("Welcome to Java!!", 50, 60 );

 }
}
