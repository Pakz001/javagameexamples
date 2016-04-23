

import java.awt.*;
import java.applet.*;

public class MouseButton01 extends Applet {
 String info="Press one of the mouse buttons in the applet";
 public void init() {
 }

 public void paint(Graphics g) {

  g.drawString(""+info, 50, 60 );

 }
 public boolean mouseDown (Event e, int x, int y) {
        if (e.modifiers == Event.META_MASK) {
            info=("Right Button Pressed");
        } else if (e.modifiers == Event.ALT_MASK) {
            info=("Middle Button Pressed");
        } else {
            info=("Left Button Pressed");
        }
        repaint();
        return true;
    }

}
