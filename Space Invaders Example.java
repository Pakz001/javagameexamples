/**
 * @(#)SpaceInvadersExample003.java
 *
 * SpaceInvadersExample003 Applet application
 *
 * @author Rudy van Etten
 * @version 1.00 2013/10/28
 */

import java.awt.*;
import java.applet.*;

public class SpaceInvadersExample003 extends Applet  implements Runnable{
    Graphics bufferGraphics;
    Image offscreen;

    double px = 160-8;
    double py = 200;
    int pwidth = 16;
    int pheight = 16;
    boolean ismovingright = false;
    boolean ismovingleft = false;
    int wavedirection = 1; // 1 = right 0 = left
    long firedelay = 0;
    double bullet[][] = new double[ 32 ][ 5 ]; //active,x,y,my
    double alien[][] = new double[10*5][5];//active,x,y
    public void init() {
           setBackground(Color.black);
          offscreen = createImage(getSize().width,getSize().height);
           bufferGraphics = offscreen.getGraphics();
        int x=0;
        int y=0;
        for(int i=0;i<10*5;i++){
            alien[i][0]=1;
            alien[i][1]=x;
            alien[i][2]=y;
            x+=32;
            if(x>getSize().width-32){
                x=0;
                y+=32;
            }

           }
        new Thread(this).start();

    }
    public void run() {
        for(;;) { // animation loop never ends
            if (ismovingright==true){
                if (px<getSize().width-pwidth)
                    px+=1;
            }
            if (ismovingleft==true){
                if(px>0)
                    px-=1;
            }
            // move the bullets and remove them if they get above the screen
            for(int i=0 ;i<32;i++){
                if(bullet[i][0]==1){
                    bullet[i][2]+=bullet[i][3];
                    if(bullet[i][2]<0){
                        bullet[i][0]=0;
                    }
                }
            }
            // Collision with the bullets and the aliens.
            for(int i=0; i<32;i++){
                if(bullet[i][0]==1){
                for (int ii=0;ii<10*5;ii++){
                if (alien[ii][0]==1){
                    Rectangle rec1 = new Rectangle((int)bullet[i][1],(int)bullet[i][2],16,16);
                    Rectangle rec2 = new Rectangle((int)alien[ii][1],(int)alien[ii][2],16,16);
                    if(rec1.intersects(rec2)){
                        //g.drawString("Filled Rectangle Collision", 50, 60 );
                        bullet[i][0]=0;
                        alien[ii][0]=0;
                    }
                }
                }
                }
            }
            // Move the alien wave
            for(int i=0;i<10*5;i++){
                if(alien[i][0]==1){
                    if(wavedirection==0){
                        alien[i][1]-=0.1;
                    }else{
                        alien[i][1]+=0.1;
                    }
                    if(alien[i][1]>getSize().width-16){
                        wavedirection=0;
                    }
                    if(alien[i][1]<0){
                        wavedirection=1;
                    }
                }
            }
            repaint();
            try {
                Thread.sleep(16);
                }
                catch (InterruptedException e) {
                }
        }
    }

    public void update(Graphics g){
        bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Space Invaders Example.",20,30);
        // draw player ship
         bufferGraphics.setColor(Color.white);
        bufferGraphics.fillRect((int)px,(int)py,pwidth,pheight);
        //draw aliens
        for(int i=0;i<10*5;i++){
            if(alien[i][0]==1){
                bufferGraphics.fillRect((int)alien[i][1],(int)alien[i][2],16,16);
            }
        }
        // Draw bullets
        for(int i=0;i<32;i++){
            if(bullet[i][0]==1){
                bufferGraphics.fillRect((int)bullet[i][1],(int)bullet[i][2],3,3);
            }
        }
         g.drawImage(offscreen,0,0,this);
    }

        public void createBullet(){
            for(int i=0;i<32;i++){
                if(bullet[i][0]==0){
                    bullet[i][0]=1;
                    bullet[i][1]=px+8;
                    bullet[i][2]=py-16;
                    bullet[i][3]=-2;
                    return;
                }
            }
        }

         public boolean keyDown (Event e, int key){
          if( key == Event.LEFT )
        {
            ismovingleft = true;
        }
        if(key==Event.RIGHT)
        {
             ismovingright = true;
        }

         if( key == 32 ) // space bar for jump
         {
            if(firedelay<System.currentTimeMillis()){
                   firedelay = System.currentTimeMillis() + 200;
                   createBullet();
            }
         }

        System.out.println (" Integer Value: " + key);

         return true;
     }

    public boolean keyUp (Event e, int key){
          if( key == Event.LEFT )
        {
             ismovingleft = false;
        }
        if( key == Event.RIGHT )
        {
             ismovingright = false;
        }

        return true;
    }


}
