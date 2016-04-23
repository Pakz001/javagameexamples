
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class PlayerTileMapCollision extends Applet implements Runnable {
    Graphics bufferGraphics;
  Image offscreen;
  Graphics bufferLevel;
  Image levelgraphic;
 Image image2;
 private double mouseX;
 private double mouseY;
 private boolean playerMoveRight = false;
 private boolean playerMoveLeft = false;
    private double xpos=100;
    private double ypos=100;
    private int radius;
    private int appletSize_x;
    private int appletSize_y;
    private double time;
    private double height;
    private final int GRAVITY = -10;
    private boolean isFalling = true;
  private boolean isJumping = false;


  final short map[] ={
        1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
        1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
        1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
       1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
        1,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,1,
        1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
        1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,
        1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
        1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
        1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
        1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
        1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
        };

    public void init() {


        radius = 10;
        height = appletSize_y - ypos;
        time = 0;
        appletSize_x = getSize().width;
        appletSize_y = getSize().height;
        height=appletSize_y - ypos;
    setBackground(Color.black);
    offscreen = createImage(getSize().width,getSize().height);
    bufferGraphics = offscreen.getGraphics();
        levelgraphic = createImage(getSize().width,getSize().height);
        bufferLevel = levelgraphic.getGraphics();

     image2 = createImage(16,16);
     Graphics test = image2.getGraphics();
     test.setColor(Color.red);
     test.fillRect(0,0,16,16);
     test.setColor(Color.black);
     test.drawRect(0,0,16,16);

        new Thread(this).start();

   // Here the map is drawn
    int n=0;
    for(int y=0;y<=11;y++){
    for(int x=0;x<=19;x++){
      if(map[n]==1) bufferLevel.drawImage(image2,x*16,y*16,this);
      n++;
    }
    }

    }

    public void run() {
  int pos;
  short ch;
  int val;
        for(;;) { // animation loop never ends
      pos = (int)((xpos+8)/16)+20*(int)((ypos+16)/16);
         ch = map[pos];
         if(ch==0 && isJumping==false)isFalling=true;
            if (isJumping) {
       pos = (int)((xpos+8)/16)+20*(int)((ypos-1)/16);
          ch = map[pos];
                if((height - radius < getSize().height) && (ch==0) && (time<1.7)) {
                height =  height - (.2 * GRAVITY * (time * time) );
                  ypos = appletSize_y - height;
                  time += .02;
                } else {
                  isJumping = false;
                  isFalling = true;
                  time=0;
                }
            }

            if (isFalling) {
        pos = (int)(xpos/16)+20*(int)((ypos+16)/16);
          ch = map[pos];
                if(ch==0){
                    height =  height + (.5 * GRAVITY * (time * time) );
                    ypos = appletSize_y - height;
                    time += .02;
                } else {
                    isFalling = false;
                    val=(int)ypos/16;
                    ypos=val*16;
                    time = 0;
             }
            }

   if(playerMoveLeft){
        pos = (int)((xpos-1)/16)+20*(int)((ypos)/16);
          ch = map[pos];
    if(ch==0)
    {
      xpos--;
    }
   }
   if(playerMoveRight){
        pos = (int)((xpos+16)/16)+20*(int)((ypos)/16);
          ch = map[pos];
          if(ch==0) xpos++;
   }

            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }

 public void update (Graphics g)
 {
    bufferGraphics.clearRect(0, 0, getSize().width,getSize().height);
     bufferGraphics.drawImage(levelgraphic,0,0,this);
        bufferGraphics.setColor (Color.RED);
        bufferGraphics.drawString("Jump Example - Press Spacebar to Jump",10,10);
        int x = (int) xpos;
        int y = (int) ypos;
        bufferGraphics.fillOval (x, y, 2 * radius, 2 * radius);
    g.drawImage(offscreen,0,0,this);

 }

    //Overriding the paint method
    public void paint(Graphics g) {
    }

 public boolean mouseMove(Event e, int x, int y)
 {
  mouseX = x;
  mouseY = y;

  return true;
 }

 public boolean keyUp (Event e, int key){
  if(key==Event.LEFT)
        {
         playerMoveLeft = false;
        }
        if(key==Event.RIGHT)
        {
         playerMoveRight = false;
        }
  return true;
 }

 public boolean keyDown (Event e, int key){

  if(key==Event.LEFT)
        {
         playerMoveLeft = true;
   //System.out.println (movingLeft);
        }
        if(key==Event.RIGHT)
        {
         playerMoveRight = true;
        }

     if(key==32)
     {
      if(isFalling==false && isJumping==false)  {
          height = appletSize_y - ypos;
       isJumping = true;
       time=1;
      }
     }
        //System.out.println ("Charakter: " + (char)key + " Integer Value: " + key);
        return true;
 }

}
