


import java.awt.*;
import java.applet.*;
import java.util.Random;

public class Explosion001 extends Applet implements Runnable {
 // Graphics for double buffering.
 Graphics    bufferGraphics;
    Image     offscreen;
 int     startx;
 int     starty;
 int     numparticles =   1024;
 double[]   px =    new double[numparticles];
 double[]   py =    new double[numparticles];
 double[]   incx =     new double[numparticles];
 double[]   incy =    new double[numparticles];
 double[]   addx =     new double[numparticles];
 double[]   addy =    new double[numparticles];
 boolean[]   active =   new boolean[numparticles];

 Random    r =     new Random();

 public void init() {
     setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
     bufferGraphics = offscreen.getGraphics();

  new Thread(this).start();

 }

 public void paint(Graphics g) {
 }
    public void run() {
        for(;;) { // animation loop never ends

   if(r.nextInt(100)<5){
    startx = r.nextInt(getSize().width);
    starty = r.nextInt(getSize().height);
    for(int i = 0 ; i < 64 ; i++){
     newexplosion();
    }
   }
         updateexplosions();
         repaint();
         try {
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }
    }

 public void updateexplosions(){

  for(int i = 0 ; i < numparticles ; i++){
   if(active[i] == true ){
    px[i] += incx[i];
    py[i] += incy[i];
    if(incx[i] > 0){
     incx[i] -= addx[i];
     if(incx[i]<0) active[i] = false;
    }else{
     incx[i] += addx[i];
     if(incx[i]>0) active[i] = false;
    }

    if(incy[i] > 0){
     incy[i] -= addy[i];
     if(incy[i]<0) active[i] = false;
    }else{
     incy[i] += addy[i];
     if(incy[i]>0) active[i] = false;
    }


   }
  }

 }

 public void newexplosion(){

  for (int i = 0 ; i < numparticles ; i++){
   if(active[i] == false){
    px[i] = startx;
    py[i] = starty;
    incx[i] = r.nextDouble()*4-2;
    incy[i] = r.nextDouble()*4-2;
    addx[i] = r.nextDouble()/15;
    addy[i] = r.nextDouble()/15;
    active[i] = true;
    break;
   }
  }

 }

    public void update(Graphics g){
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Explosions Example",10,10);

  for(int i = 0 ; i < numparticles ; i++){
   if(active[i]){
         bufferGraphics.drawOval((int)px[i],(int)py[i],4,4);
   }
        }

       g.drawImage(offscreen,0,0,this);
    }

}
