

import java.awt.*;
import java.applet.*;
import java.awt.geom.Area;

public class SlopesPlatformer extends Applet implements Runnable{

  // This is the drawn map; 1 = redblock
 private short map[][]={
      {1,1,1,1,1,1,1,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,2,1,1,1,1,1},
      {1,0,0,0,0,0,0,0,2,1,1,1,1},
      {1,0,0,1,0,0,0,0,0,1,1,1,1},
      {1,0,0,1,0,0,0,0,0,1,1,1,1},
      {1,0,0,1,0,0,0,0,3,1,1,1,1},
      {1,0,0,0,0,0,0,3,1,1,1,1,1},
      {1,0,0,0,0,0,0,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,2,1,1,1,1,1},
      {1,0,0,0,0,0,0,0,2,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,2,1,1,1},
      {1,0,0,0,0,0,0,0,0,0,2,1,1},
      {1,0,0,0,0,0,0,0,0,0,0,2,1},
      {1,0,0,0,0,0,0,1,0,0,0,0,1},
      {1,0,0,0,0,0,0,1,0,0,0,0,1},
      {1,0,0,0,0,0,0,1,0,0,0,0,1},
      {1,0,0,0,0,0,0,1,0,0,0,0,1},
      {1,1,1,1,1,1,1,1,1,1,1,1,1}
      };

 private short cellwidth=16;
 private short cellheight=16;
 private short mapheight = 13;
 private short mapwidth = 20;
   Graphics bufferGraphics;
   Image bufferimage;
 Graphics debugGraphics;
 Image debugimage;
  private double mouseX;
  private double mouseY;
  private boolean playerMoveRight = false;
  private boolean playerMoveLeft = false;
    private double xPos=20;
    private double yPos=96;
    private boolean isFalling = false;
    private boolean isJumping = false;
 private double gravity;
 private double groundLevel = 100;
 private boolean playerFacingLeft;
 private boolean playerFacingRight=true;
 private boolean moverightup = false;
 private boolean moveleftup = false;
 private int mousecol=0;

 public void init() {
     setBackground(Color.black);
     bufferimage = createImage(getSize().width,getSize().height);
     bufferGraphics = bufferimage.getGraphics();
    debugimage = createImage(getSize().width,getSize().height);
    debugGraphics = debugimage.getGraphics();

    new Thread(this).start();
 }

    public void run() {
        for(;;) { // animation loop never ends
   mousecol=0;
   if(rectcollision(xPos,yPos+1)==0)
   {
    if(isFalling==false && isJumping==false)
    {
     System.out.print("Off the ground,");
     isFalling=true;
     gravity=0;
    }
   }
   if(playerMoveRight)
   {
    if(rectcollision(xPos+1,yPos)==0)xPos++;
    if(rectcollision(xPos+1,yPos)==3)
    {
    xPos++;
    yPos--;
    }
   }
   if(playerMoveLeft)
   {
    if(rectcollision(xPos-1,yPos)==0)xPos--;
    if(rectcollision(xPos-1,yPos)==2)
    {
     yPos--;
     xPos--;
    }
   }

   if(isJumping)
   {
    yPos = (int)yPos - gravity;
    gravity = gravity - .1;
    if(gravity<0 || rectcollision(xPos,yPos-1)!=0)
    {
    isJumping=false;
    isFalling=true;
    }
   }
   if(isFalling)
   {
    yPos = (int)yPos + gravity;
    gravity = gravity + .1;
    if(rectcollision(xPos,yPos)!=0)
    {
     System.out.print("collided. ending gravity");
     isFalling = false;
     //yPos = (yPos/32)*32;
     while(rectcollision(xPos,yPos)!=0)
     {
      yPos--;
     }
     gravity=0;
    }
   }

         repaint();
         try {
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
 }

 public short rectcollision(double x,double y)
 {
  int chkx=(int)x/cellwidth;
  int chky=(int)y/cellheight;

  for(int y2=-1;y2<3;y2++)
  {
   for(int x2=-1;x2<3;x2++)
   {
    if (chkx+x2>=0 && chkx+x2 < mapwidth && chky+y2>=0 && chky+y2 < mapheight)
    {


     if(map[chkx+x2][chky+y2]==2)
     {
      int x1=(int)x;
      int y1=(int)y;
      int x3=(chkx+x2)*cellwidth;
      int y3=(chky+y2)*cellheight;
       Rectangle rec1 = new Rectangle(x1,y1,cellwidth,cellheight);
        Rectangle rec2 = new Rectangle(x3,y3,cellwidth,cellheight);
      if(rec1.intersects(rec2))
      {
       mousecol=2;
       int[] XArray = {x3,x3,x3+cellwidth};
       int[] YArray = {y3,y3+cellheight,y3+cellheight};
          int[] XArray2= {x1,x1+cellwidth,x1+cellwidth,x1};
          int[] YArray2= {y1,y1,y1+cellheight,y1+cellheight};
          Polygon abc = new Polygon(XArray, YArray, 3);
          Polygon abc2= new Polygon(XArray2,YArray2,4);
          Area a1 = new Area(abc);
          Area a2 = new Area(abc2);

        a1.intersect(a2);
        if (!a1.isEmpty())
        {
        //System.out.print("collided with tile 3");
         return 2;
          //g.drawString("Two Polygons collided", 20, 20 );
         }

      }
     }


     if(map[chkx+x2][chky+y2]==3)
     {
      int x1=(int)x;
      int y1=(int)y;
      int x3=(chkx+x2)*cellwidth;
      int y3=(chky+y2)*cellheight;
       Rectangle rec1 = new Rectangle(x1,y1,cellwidth,cellheight);
        Rectangle rec2 = new Rectangle(x3,y3,cellwidth,cellheight);
      if(rec1.intersects(rec2))
      {
       mousecol=3;
       int[] XArray = {x3,x3+cellwidth,x3+cellwidth};
       int[] YArray = {y3+cellheight,y3+cellheight,y3};
          int[] XArray2= {x1,x1+cellwidth,x1+cellwidth,x1};
          int[] YArray2= {y1,y1,y1+cellheight,y1+cellheight};
          Polygon abc = new Polygon(XArray, YArray, 3);
          Polygon abc2= new Polygon(XArray2,YArray2,4);
          Area a1 = new Area(abc);
          Area a2 = new Area(abc2);

        a1.intersect(a2);
        if (!a1.isEmpty())
        {
        //System.out.print("collided with tile 3");
         return 3;
          //g.drawString("Two Polygons collided", 20, 20 );
         }

      }
     }

     if(map[chkx+x2][chky+y2]==2)
     {
      int x1=(int)x;
      int y1=(int)y;
      int x3=(chkx+x2)*cellwidth;
      int y3=(chky+y2)*cellheight;
       Rectangle rec1 = new Rectangle(x1,y1,cellwidth,cellheight);
        Rectangle rec2 = new Rectangle(x3,y3,cellwidth,cellheight);
      if(rec1.intersects(rec2))
      {
       //System.out.print("collided with tile 2");



      }
     }
     if (map[chkx+x2][chky+y2]==1)
     {
      int x1=(int)x;
      int y1=(int)y;
      int x3=(chkx+x2)*cellwidth;
      int y3=(chky+y2)*cellheight;
       Rectangle rec1 = new Rectangle(x1,y1,cellwidth,cellheight);
        Rectangle rec2 = new Rectangle(x3,y3,cellwidth,cellheight);
      if(rec1.intersects(rec2))
      {
       return 1;
      }
     }
    }
   }
  }
  return 0;
 }

 public short mapcollision(double x,double y)
 {
  int[] XArray = {20, 160, 120, 160, 20, 60};
     int[] YArray = {20, 20, 90, 160, 160, 90};
     int[] XArray2 = {20, 160, 120, 160, 20, 60};
     int[] YArray2 = {20, 20, 90, 160, 160, 90};

     Polygon abc = new Polygon(XArray, YArray, 6);
     Polygon abc2= new Polygon(XArray2,YArray2,6);
     //g.drawPolygon(abc);
     //g.drawPolygon(abc2);

     Area a1 = new Area(abc);
     Area a2 = new Area(abc2);

   a1.intersect(a2);
   if (!a1.isEmpty())
   {
     //g.drawString("Two Polygons collided", 20, 20 );
    }
  return 0;
 }

 public void update (Graphics g)
  {
  bufferGraphics.clearRect(0, 0, getSize().width,getSize().height);
  bufferGraphics.setColor (Color.white);
  for(int y=0; y < mapheight ;y++){
  for(int x=0; x < mapwidth ;x++){
   if(map[x][y]==1)
   {
    bufferGraphics.fillRect(x*cellwidth,y*cellheight,cellwidth,cellheight);
   }
   if(map[x][y]==2)
   {
    int[] XArray = {x*cellwidth,x*cellwidth,x*cellwidth+cellwidth};
    int[] YArray = {y*cellheight,y*cellheight+cellheight,y*cellheight+cellheight};
       Polygon abc = new Polygon(XArray, YArray, 3);

    bufferGraphics.fillPolygon(abc);
   }
   if(map[x][y]==3)
   {
    int[] XArray = {x*cellwidth,x*cellwidth+cellwidth,x*cellwidth+cellwidth};
    int[] YArray = {y*cellheight+cellheight,y*cellheight+cellheight,y*cellheight};
       Polygon abc = new Polygon(XArray, YArray, 3);

    bufferGraphics.fillPolygon(abc);
   }

  }
 }

 bufferGraphics.drawString("Slopes platformer example - x - jump - cursors l and r",3,getSize().height-20);
 bufferGraphics.drawString("moveleftup:"+moveleftup+" moverightup:"+moverightup,3,getSize().height-10);
 bufferGraphics.drawString("mousecol :"+mousecol,3,getSize().height-5);

 int x = (int) xPos;
 int y = (int) yPos;
 bufferGraphics.fillOval (x, y, cellwidth, cellheight);
 //if(mouseY<100)
 //{









  x=(int)xPos;
  y=(int)yPos;
  int chkx=(int)x/cellwidth;
  int chky=(int)y/cellheight;

  for(int y2=-1;y2<3;y2++)
  {
   for(int x2=-1;x2<3;x2++)
   {
    if (chkx+x2>=0 && chkx+x2 < mapwidth && chky+y2>=0 && chky + y2 < mapheight )
    {
     if(map[chkx+x2][chky+y2]==1)
     {
      int x1=(int)x;
      int y1=(int)y;
      int x3=(chkx+x2)*cellwidth;
      int y3=(chky+y2)*cellheight;
       Rectangle rec1 = new Rectangle(x1,y1,cellwidth,cellheight);
        Rectangle rec2 = new Rectangle(x3,y3,cellwidth,cellheight);
        bufferGraphics.setColor(Color.yellow);
      bufferGraphics.drawRect(rec1.x,rec1.y,rec1.width,rec1.height);
      bufferGraphics.drawRect(rec2.x,rec2.y,rec2.width,rec2.height);
      if(rec1.intersects(rec2))
      {
      // return 1;
      }
     }
     if(map[chkx+x2][chky+y2]==3)
     {

      int x1=(int)x;
      int y1=(int)y;
      int x3=(chkx+x2)*cellwidth;
      int y3=(chky+y2)*cellheight;
       Rectangle rec1 = new Rectangle(x1,y1,cellwidth,cellheight);
        Rectangle rec2 = new Rectangle(x3,y3,cellwidth,cellheight);
      if(rec1.intersects(rec2))
      {
       //stem.out.print("yeah");
       mousecol=3;
       int[] XArray= {x3,x3+cellwidth,x3+cellwidth};
       int[] YArray= {y3+cellheight,y3+cellheight,y3};
          int[] XArray2= {x1,x1+cellwidth,x1+cellwidth,x1};
          int[] YArray2= {y1,y1,y1+cellheight,y1+cellheight};
          Polygon abc = new Polygon(XArray, YArray, 3);
          Polygon abc2= new Polygon(XArray2,YArray2,4);
          bufferGraphics.setColor(Color.yellow);
       bufferGraphics.fillPolygon(abc);
          bufferGraphics.fillPolygon(abc2);
          Area a1 = new Area(abc);
          Area a2 = new Area(abc2);

        a1.intersect(a2);
        if (!a1.isEmpty())
        {
        //System.out.print("collided with tile 3");
         //return 3;
          //g.drawString("Two Polygons collided", 20, 20 );
         }

      }
     }
    }
   }
  }















 g.drawImage(bufferimage,0,0,this);
 //}else
 //{
 //g.drawImage(debugimage,0,0,this);
 //}
 }

 // Not used becourse update is being used for screen drawing
 public void paint(Graphics g) {
  //g.drawString("Welcome to Java!!", 50, 60 );
 }

 public boolean mouseMove(Event e, int x, int y)
  {
    mouseX = x;
    mouseY = y;
  mousecol = rectcollision(x,y);
  //xPos=x;
  //yPos=y;
  //System.out.println("On the "+map[(int)mouseX/cellwidth][(int)mouseY/cellheight]);

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
         playerFacingLeft = true;
         playerFacingRight = false;
        }
        if(key==Event.RIGHT)
        {
          playerMoveRight = true;
          playerFacingRight = true;
          playerFacingLeft = false;
        }

      if(key==120)
      {
        if(isFalling==false && isJumping==false)
        {
            isJumping = true;
            gravity = 3;
        }
      }

        //System.out.println ("Charakter: " + (char)key + " Integer Value: " + key);
        return true;
  }


}

