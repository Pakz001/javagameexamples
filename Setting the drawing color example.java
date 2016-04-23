import java.awt.*;
import java.applet.*;

public class testproject extends Applet implements Runnable {
    // Graphics for double buffering.
    Graphics    bufferGraphics;
    Image        offscreen;
    
    public void init() {
        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();
        new Thread(this).start();
    }

    public void run(){
        for(;;){
            repaint();
            try{
                Thread.sleep(10);
            }
            catch (InterruptedException e){    
            }    
        }
    }
    
    public void update(Graphics g){
        bufferGraphics.clearRect(0,0,getSize().width,getSize().height);
        // Here the color is set using new Color followed by
        // the rgb values. In this case 200,0,0
        bufferGraphics.setColor(new Color(200,0,0));
        bufferGraphics.drawString("Test",10,10);
        
        g.drawImage(offscreen,0,0,this);
        
    }

}
