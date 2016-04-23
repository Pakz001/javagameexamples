

import java.awt.*;
import java.applet.*;

public class exclamationbooleanvalueExample001 extends Applet {

 // create a boolean and set its value to false
 boolean the = false;

 public void init() {
 }

 public void paint(Graphics g) {
  g.drawString( "! Example" , 10, 10 );
  g.drawString( "boolean the = false;" , 10 , 30 );
  g.drawString( "if ( !the ) { " , 10 , 40 );

  // if the is false then draw a message on the screen.
  if ( !the ) {
   g.drawString( "the value is false." , 10 , 70 );
  }

 }


}
