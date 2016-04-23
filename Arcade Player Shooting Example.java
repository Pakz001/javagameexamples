

import java.awt.*;
import java.applet.*;

public class PlayerShooting extends Applet implements Runnable {
    Graphics bufferGraphics;
   Image offscreen;
  private double mouseX;
  private double mouseY;
  private boolean playerMoveRight = false;
  private boolean playerMoveLeft = false;
    private double xPos=100;
    private double yPos=100;
    private boolean isFalling = false;
    private boolean isJumping = false;
 private double gravity;
 private double groundLevel = 100;
 private boolean isShooting;
 private boolean playerFacingLeft;
 private boolean playerFacingRight=true;
 private Shot [] shots;


    public void init() {

     setBackground(Color.black);
     offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();

  shots = new Shot[99];

        new Thread(this).start();
    }

    public void run() {
        for(;;) { // animation loop never ends

   if(playerMoveRight && xPos0)
   {
    xPos--;
   }
   if(isJumping)
   {
    yPos = (int)yPos - gravity;
    gravity = gravity - .1;
    if(gravity<0)
    {
    isJumping=false;
    isFalling=true;
    }
   }
   if(isFalling)
   {
    yPos = (int)yPos + gravity;
    gravity = gravity + .1;
    if(yPos>groundLevel)
    {
     isFalling = false;
     yPos = groundLevel;
     gravity=0;
    }
   }



   for(int i=0; i < shots.length; i++)
   {
    if(shots[i] != null)
    {
     // move shot
     shots[i].moveShot();
     // test if shot has left the game area
     // if true, delete from array
     if(shots[i].getYPos() < 0)
     {
      shots[i] = null;
      break;
     }
     if(shots[i].getYPos() > getSize().height)
     {
      shots[i] = null;
      break;
     }
     if(shots[i].getXPos() < 0)
     {
      shots[i] = null;
      break;
     }
     if(shots[i].getXPos() > getSize().width)
     {
      shots[i] = null;
      break;
     }

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

 public void update (Graphics g)
 {
 bufferGraphics.clearRect(0, 0, getSize().width,getSize().height);
    bufferGraphics.setColor (Color.white);
    bufferGraphics.drawString("Player Shooting Example - Press z to shoot. x - jump",10,10);
    int x = (int) xPos;
    int y = (int) yPos;
    bufferGraphics.fillOval (x, y, 16, 16);



    // draw shots
 for(int i=0; i < shots.length; i++)
 {
  if(shots[i] != null)
  {
   shots[i].drawShot(bufferGraphics);
  }
 }


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
      if(key==122)//z key
      {
       isShooting = true;

   // generate new shot and try to store it in shots array
   for(int i=0; i < shots.length; i++)
   {
    // only store shot if there is a place left in the array
    if(shots[i] == null)
    {
     if(playerFacingLeft)
     {
     shots[i] = new Shot(xPos,yPos,-2,0);
     }else
     {
     shots[i] = new Shot(xPos+16,yPos,2,0);

     }
     break;
    }
   }


      }
        System.out.println ("Charakter: " + (char)key + " Integer Value: " + key);
        return true;
  }



}


Shot.class

 

import java.awt.Graphics;
import java.awt.Color;


 public class Shot
 {
  private double xLoc;
  private double yLoc;
  private double velX;
  private double velY;

  public Shot(double x, double y,double vx, double vy)
  {
   xLoc = x;
   yLoc = y;
   velX = vx;
   velY = vy;
  }

  public int getYPos()
  {
   return (int)yLoc;
  }
  public int getXPos()
  {
   return (int)xLoc;
  }
  public void moveShot()
  {
   xLoc = (xLoc + velX);
   yLoc = (yLoc + velY);
  }
  public void drawShot(Graphics bufferGraphics)
  {
   bufferGraphics.setColor(Color.green);
   bufferGraphics.fillOval((int)xLoc,(int)yLoc,8,8);
  }
 }
