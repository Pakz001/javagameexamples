
import java.applet.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;


public class RocksGameExample01 extends Applet implements Runnable
{
 private boolean  debugmode   = true;

 Random     r     = new Random();

 // Graphics for double buffering.
 Graphics    bufferGraphics;
    Image     offscreen;

    // The Images for the blocks that can be picked up
    // and thrown and jumped on.
    Image     rock1;
    private int   rock1height   = 20;
    private int   rock1width   = 16;
 Image     rock2;
 private int    rock2height   = 8;
 private int   rock2width   = 16;
 private int   numrocks   = 10;
 private boolean[]  rockexists   = new boolean[numrocks];
 private int[]   rockx    = new int[numrocks];
 private int[]   rocky    = new int[numrocks];
 private int[]   rockwidth   = new int[numrocks];
 private int[]   rockheight   = new int[numrocks];
 private double[] rockincy   = new double[numrocks];
 private double[] rockvely   = new double[numrocks];
 private int[]   rocktype   = new int[numrocks];
 private boolean[] rockisfalling  = new boolean[numrocks];
// ArrayList< Integer > rockx   = new ArrayList< Integer >();
// ArrayList< Integer > rocky   = new ArrayList< Integer >();
// ArrayList< Integer > rocktype  = new ArrayList< Integer >();
// ArrayList< Integer > rockincy  = new ArrayList< Integer >();

    // Player Image
    Image player;
    private int   playerwidth   = 16;
    private int   playerheight  = 32;
 Image     playersquat;
    private int   playersquatwidth = playerwidth;
    private int   playersquatheight = playerheight/2;

 private int[][]  debugrect   = new int[10][10];

    // Player position
    private int   xpos    = 0;
    private int   ypos    = 0;
 private int   groundlevel   = 0;
 private boolean  isjumping   = false;
 private boolean  isfalling   = false;
 private double   gravity    = 0.0;
 private double   gravityinc   = 0.08;
 private boolean  playermoveright  = false;
 private boolean  playermoveleft  = false;
 private boolean  playerfacingleft = false;
 private boolean  playerfacingright = true;
 private int   startingjumpspeed = 2;
 private boolean  playerpickuprock = false;
 private boolean  playersquated  = false;
 private boolean  isonrock   = false;
 private boolean  playercaryingrock = false;
 private int   rockbeingcarried = 0;
 private boolean  playerthrowrock  = false;
 private boolean  playerputrockback   = false;
 private int   rockbeingthrowed = 0;
 private boolean  rockisbeingthrowed = false;
 private int   rockthrowheight  = 0;
 private double   rockthrowx   = 0;
 private double   rockthrowy   = 0;
 private double   rockthrowvelx  = 0;
 private double  rockthrowvely  = 0;
 private double  rockthrowincx  = 0;
 private double  rockthrowincy  = 0;
 private boolean  rockgoingup   = false;
 private boolean  rockgoingdown  = false;
 private boolean  rockgoingright  = false;
 private boolean  rockgoingleft  = false;
 private boolean  rockliftingmode  = false;
 private boolean  rockputtingdownmode = false;
 private int   rockdesty   = 0;
 private boolean  startsquat   = false;
 private boolean  endsquat   = false;

 public void init()
    {
     setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
        bufferGraphics = offscreen.getGraphics();
  // create the player image
    player = createImage(playerwidth,playerheight);
    Graphics test1 = player.getGraphics();
    test1.setColor(Color.red);
    test1.fillRect(0,0,playerwidth,playerheight);
  // create the player image
    playersquat = createImage(playersquatwidth,playersquatheight);
    Graphics test4 = playersquat.getGraphics();
    test4.setColor(Color.red);
    test4.fillRect(0,0,playersquatwidth,playersquatheight);

  // create block 1 image
    rock1 = createImage(rock1width,rock1height);
    Graphics test2 = rock1.getGraphics();
    test2.setColor(new Color(100,100,100));
    test2.fillRect(0,0,rock1width,rock1height);
  // create block 2 image
    rock2 = createImage(rock2width,rock2height);
    Graphics test3 = rock2.getGraphics();
    test3.setColor(new Color(100,100,100));
    test3.fillRect(0,0,rock2width,rock2height);

  xpos    = getSize().width / 2;
     ypos    = getSize().height / 2 + 32 - playerheight ;
      groundlevel   = getSize().height / 2 + 32;

  for (int i = 0 ; i < 10 ; i++ ){
   addrock();
  }

  new Thread(this).start();
     }

 public int getfreerock(){
  for ( int i = 0 ; i < numrocks ; i++ ){
   if( rockexists[i] == false ) return i;
  }
  return -1;
 }

 public boolean addrock(){
  int tp = r.nextInt(2);
  tp++;
  int n = getfreerock();
  //System.out.println("block "+n);
  if( n == -1 ) return false;
  rockexists[n] = true;
  rocktype[n] = tp;
  int x1=0;
  int y1=0;
  boolean exitthis = false;
  while(exitthis == false ){
   x1 = r.nextInt(getSize().width)-74;
   y1 = groundlevel-getrockheight(n);
   if(rockrockcollide(x1,y1,tp) == false){
    rockx[n] = x1;
    rocky[n] = y1;
    rockwidth[n] = getrockwidth(n);
    rockheight[n] = getrockheight(n);
    rocktype[n] = tp;
    rockincy[n] = .1;
    rockvely[n] = 1;
    exitthis = true;
   }
  }
  return true;
 }

 public boolean playeronrock(int x , int y){
  // only the bottom of the player and the
  // top of the rocks need to be checked.
  int x1 = xpos+x;
  int y1 = ypos+y+playerheight;
  int w1 = playerwidth;
  int h1 = 1;
  int x2 = 0;
  int y2 = 0;
  int w2 = 0;
  int h2 = 0;
  for(int n = 0 ; n < numrocks ; n++){
   if( rockexists[n] == true ){
    x2 = rockx[n];
    y2 = rocky[n];
    w2 = rockwidth[n];
    h2 = rockheight[n];
    Rectangle rec1 = new Rectangle(x1,y1+h1-1,w1,1);
    Rectangle rec2 = new Rectangle(x2,y2,w2,1);
    if(rec1.intersects(rec2)) return true;
   }
  }
  return false;
 }

 public int getrockheight(int num){
  if(rocktype[num] == 1){
   return rock1height;
  }else{
   return rock2height;
  }
//  return 0;
 }

 public int getrockwidth(int num){
  if(rocktype[num] == 1){
   return rock1width;
  }else{
   return rock2width;
  }
  //return 0;
 }

 public boolean dorockfall(){
  int x1,y1,w1,h1;
  for( int i = 0 ; i < numrocks ; i++ ){
   if(i != rockbeingcarried ){

    if( rockisfalling[i] == false){
     if( rocky[i] + rockheight[i] < groundlevel && rocktopcollide(rockx[i],rocky[i],rocktype[i]) == false){
      //System.out.println("floater.");
      rockisfalling[i] = true;
      rockvely[i] = 1;
     }
    }
    if( rockisfalling[i] == true ){
     boolean falld = true;
     for( int n = 0 ; n < rockvely[i] ; n++ ){

      if (rocktopcollide(rockx[i],rocky[i],rocktype[i])){
       //System.out.println("rock landed on other rock top");
       rockisfalling[i] = false;
       falld = false;
      }
      if( rocky[i] + rockheight[i]+1 > groundlevel ){
       //System.out.println("Rock landed on ground.");
       rockisfalling[i] = false;
       falld = false;
      }
      if (falld == true){
       rocky[i]++;
       rockvely[i] += rockincy[i];
      }
     }
    }
   }
  }
  return true;
 }

 public boolean rocktopcollide(int x , int y, int tp2){
  // get the rock n variables
  int w1 = 0;
  int h1 = 0;
  int w2 = 0;
  int h2 = 0;
  int x2 = x;
  int y2 = y;
  if(tp2 == 1){
   w2 = rock1width;
   h2 = rock1height;
  }else{
   w2 = rock2width;
   h2 = rock2height;
  }
  // loop through all rocks
  for(int i = 0 ; i < numrocks ; i++ ){
   if(rockexists[i] == true ){
    int x1 = rockx[i];
    int y1 = rocky[i];
    int tp1 = rocktype[i];
    if((x1 != x2) && (y1 != y2)){
     if(tp1 == 1){
      w1 = rock1width;
      h1 = rock1height;
     }else{
      w1 = rock2width;
      h1 = rock2height;
     }
     //System.out.println(""+w1+","+h1+":"+w2+","+h2);
     //debugrect[0][0] = 1;
     //debugrect[0][1] = x1;
     //debugrect[0][2] = y1;
     //debugrect[0][3] = w1;
     //debugrect[0][4] = h1;
     //debugrect[1][0] = 1;
     //debugrect[1][1] = x2;
     //debugrect[1][2] = y2;
     //debugrect[1][3] = w2;
     //debugrect[1][4] = h2;
     Rectangle rec1 = new Rectangle(x1,y1-1,w1,1);
     Rectangle rec2 = new Rectangle(x2,y2+h2-1,w2,1);
     if(rec1.intersects(rec2)) return true;
    }
   }
  }
  return false;
 }

 public boolean rockrockcollide(int x , int y, int tp2){
  // get the rock n variables
  int w1 = 0;
  int h1 = 0;
  int w2 = 0;
  int h2 = 0;
  int x2 = x;
  int y2 = y;
  if(tp2 == 1){
   w2 = rock1width;
   h2 = rock1height;
  }else{
   w2 = rock2width;
   h2 = rock2height;
  }
  // loop through all rocks
  for(int i = 0 ; i < numrocks ; i++ ){
   int x1 = rockx[i];
   int y1 = rocky[i];
   int tp1 = rocktype[i];
   if(tp1 == 1){
    w1 = rock1width;
    h1 = rock1height;
   }else{
    w1 = rock2width;
    h1 = rock2height;
   }
   Rectangle rec1 = new Rectangle(x1,y1,w1,h1);
   Rectangle rec2 = new Rectangle(x2,y2,w2,h2);
   if(rec1.intersects(rec2)) return true;
  }

 return false;
 }

 public int moveplayer(int x, int y, int callloc){
  if(iscollision(xpos + x , ypos + y ) == true ) return callloc;
  xpos += x;
  ypos += y;
  return callloc;
 }

 public boolean iscollision(int x, int y){
  if(x < 0 || x > getSize().width - playerwidth ) return true;
  if(y < 0 || y > groundlevel-playerheight ) return true;
  return false;
 }

 public int playerinfrontofrock(){
  int x1 = xpos;
  int y1 = ypos;
  int w1 = playersquatwidth;
  int h1 = playersquatheight;
  int x2 = 0;
  int y2 = 0;
  int w2 = 0;
  int h2 = 0;
  int[] rc = new int[numrocks];
  int rccnt = 0;
  for(int n = 0 ; n < numrocks ; n++){
   if( rockexists[n] == true ){
    x2 = rockx[n];
    y2 = rocky[n];
    w2 = rockwidth[n];
    h2 = rockheight[n];
    Rectangle rec1 = new Rectangle(x1,y1,w1,h1);
    Rectangle rec2 = new Rectangle(x2,y2,w2,h2);
    if( rec1.intersects(rec2) == true ) {
     rc[rccnt] = n;
     rccnt++;
     //System.out.println("Number of rocks :"+rccnt+" rock:"+n);
    }
   }
  }
  if(rccnt > 0){
   int lowest = 0;
   int rockval = 0;
   for ( int i = 0 ; i < rccnt ; i++ ){
    //System.out.println("looping :"+i);
    if( rocky[rc[i]] > lowest ){
      //System.out.println("checking:"+rc[i]);
      lowest = rocky[rc[i]];
      rockval = rc[i];
    }
   }
   return rockval;
  }
  return -1;
 }

 public boolean pickuprock(){
  //System.out.println("pickup rock method..");
  int z = playerinfrontofrock();
  if(z == -1) return false;
  //playercaryingrock = true;
  rockbeingcarried = z;
  rockdesty = ypos-rockheight[rockbeingcarried];
  //System.out.println("rockdesty : " + rockdesty + " rocky " + rocky[rockbeingcarried]);
  rockliftingmode = true;
  rockx[rockbeingcarried] = xpos;
  //rocky[rockbeingcarried] = ypos-rockheight[rockbeingcarried];
  //System.out.println("Picked up " + z);
  return true;
 }

 public boolean putrockback(){
  rockx[rockbeingcarried] = xpos;
  //rocky[rockbeingcarried] = groundlevel-rockheight[rockbeingcarried];
  rockdesty = groundlevel-rockheight[rockbeingcarried];
  rockputtingdownmode = true;
  //playercaryingrock = false;
  //rockbeingcarried = -1;
  return true;
 }

 public boolean rockthrow(){
  if( rockisbeingthrowed == false) return false;
  if(rockgoingright){
   rockthrowx += rockthrowvelx;
   rockthrowvelx -= rockthrowincx;
  }else{
   rockthrowx -= rockthrowvelx;
   rockthrowvelx -= rockthrowincx;
  }
  if(rockgoingup){
   rockthrowy -= rockthrowvely;
   rockthrowvely -= rockthrowincy;
  }else{ // rockgoingdown

   for(int t = 0 ; t < (int)rockthrowvely ; t++){
    if( rocky[rockbeingthrowed] + rockthrowheight > groundlevel){
     //System.out.println("Rock landed on gourndlevel");
     rockgoingdown = false;
     rocky[rockbeingthrowed] = groundlevel-rockthrowheight;
     rockisbeingthrowed = false;
     return true;
    }
    if(rocktopcollide( rockx[rockbeingthrowed] , rocky[rockbeingthrowed]+t , rocktype[rockbeingthrowed] ) == true    ) {
     //System.out.println("collided rock vs rock");
     rockgoingdown = false;
     rockisbeingthrowed = false;
     rocky[rockbeingthrowed] = rocky[rockbeingthrowed]+t;
     return true;
    }
   }
   rockthrowy += rockthrowvely;
   rockthrowvely += rockthrowincy;
  }
  if(rockthrowvelx < 0){
   rockthrowvelx = 0;
   rockthrowincx = 0;
   rockgoingright = false;
   rockgoingleft = false;
  }
  if(rockthrowvely < 0 && rockgoingup){
   rockthrowvely = 0;
   rockgoingup = false;
   rockgoingdown = true;
  }

  //System.out.println("velx:"+rockvelx+"vely:"+rockvely);
  rockx[rockbeingthrowed] = (int)rockthrowx;
  rocky[rockbeingthrowed] = (int)rockthrowy;
  return true;
 }

 public void playercode(){

  if(startsquat){
   startsquat = false;
   playersquated = true;
   ypos += playerheight/2;
   playerheight = playerheight/2;
  }
  if(endsquat && rockputtingdownmode == false && rockliftingmode == false){
   endsquat = false;
       playersquated = false;
        ypos -= playerheight;
        playerheight = playerheight * 2;
  }

  if(rockliftingmode){
   rocky[rockbeingcarried]--;
   if( rocky[rockbeingcarried] == rockdesty){
    rockliftingmode = false;
    //System.out.println("rock has been lifted up");
    playercaryingrock = true;
   }
  }

  if(rockputtingdownmode){
   rocky[rockbeingcarried]++;
   //System.out.println(""+rocky[rockbeingcarried]);
   if(rocky[rockbeingcarried] == rockdesty){
    rockputtingdownmode = false;
    playercaryingrock = false;
    //System.out.println("Rock has been put down.");
    rockbeingcarried = -1;
   }
  }

  if( playerputrockback ){
   playerputrockback = false;
   putrockback();
  }

  if( playerthrowrock ){
   //System.out.println("In setup pf throw rock..");
   playerthrowrock = false;
   rockgoingdown = false;
   rockgoingleft = false;
   rockgoingright = false;
   rockgoingup = true;
   if( playerfacingright ){
    rockgoingright = true;
   }else{
    rockgoingleft = true;
   }
   rockthrowvelx = 3;
   rockthrowvely = 2;
   rockthrowincx = .05;
   rockthrowincy = .1;
   rockthrowx = rockx[rockbeingcarried];
   rockthrowy = rocky[rockbeingcarried];
   if(rocktype[rockbeingcarried]==1){
    rockthrowheight = rock1height;
   }else{
    rockthrowheight = rock2height;
   }
   rockisbeingthrowed = true;
   rockbeingthrowed = rockbeingcarried;
   playercaryingrock = false;
  }

  if(playercaryingrock && rockputtingdownmode == false){
   rockx[rockbeingcarried] = xpos;
   rocky[rockbeingcarried] = ypos-rockheight[rockbeingcarried];
  }

  if(playerpickuprock){
   pickuprock();
   playerpickuprock = false;
   //System.out.println("player picked up rock done.");
  }

  if( isonrock && isjumping == false && isfalling == false){
   if(playeronrock(0,1) == false ){
    //System.out.println("Falling down - is not on rock");
    isfalling = true;
    gravity = 0;
    isonrock = false;
   }
  }

  if(playermoveright && rockputtingdownmode == false && rockliftingmode == false){
   moveplayer(1,0,1);
  }
  if(playermoveleft && rockputtingdownmode == false && rockliftingmode == false){
   moveplayer(-1,0,2);
  }
  if(isjumping){
   gravity -= gravityinc;
   for(int z = 0 ; z < gravity ; z++ ){
    moveplayer(0,-1,3);
   }
   if( gravity <= 0 ){
    isjumping = false;
    isfalling = true;
    gravity = 0;
    //System.out.println("End of jumping");
   }
  }
  if(isfalling){
   int zz=0;
   gravity += gravityinc;
   for( int z = 0 ; z < gravity ; z++){
    moveplayer(0,1,3);
    if(iscollision(xpos,ypos+1) == true) zz=1;
    if(playeronrock(0,1) == true) zz=2;
    if(zz > 0){
     isfalling = false;
     if(zz == 2) isonrock = true;
     if(zz == 1) isonrock = false;
     //System.out.println("End of falling " + zz);
     break;
    }
   }
  }

 }

    public void run() {

        for(;;) { // animation loop never ends
   dorockfall();
   playercode();
   rockthrow();
      repaint();
         try {
             Thread.sleep(10);
             }
             catch (InterruptedException e) {
             }
     }


    }

   public void paint(Graphics g)
   {

    }

 public boolean mouseMove(Event e, int x, int y)
  {
    //mouseX = x;
    //mouseY = y;
    //System.out.println( rocktopcollide(x,y,1) );
    return true;
  }

    public void update(Graphics g)
    {
     bufferGraphics.clearRect(0,0,getSize().width,getSize().width);
        bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Rocks Game Example",10,10);
        bufferGraphics.drawString("Use cursor left and right and z to jump and x to crouch.",10,getSize().height-12);
        bufferGraphics.drawString("Use a to pick up while crouched rock and throw.",10,getSize().height-3);

  if(playersquated == false ){
     bufferGraphics.drawImage(player,xpos,ypos,this);
  }else{
   bufferGraphics.drawImage(playersquat,xpos,ypos,this);
  }
  bufferGraphics.drawLine(0,groundlevel,getSize().width,groundlevel);

  // draw rocks;
  for(int n = 0 ; n < numrocks ; n++){
   //System.out.println("locy in draw : " + rocky.get(n));
   if( rocktype[n] == 1 ){
    bufferGraphics.drawImage( rock1 , rockx[n] , rocky[n] , this);
   }else{
    bufferGraphics.drawImage( rock2 , rockx[n] , rocky[n] , this);
   }
  }
  bufferGraphics.setColor(Color.white);
  for(int i = 0; i<10 ; i++){
   if (debugrect[i][0] == 1){
    bufferGraphics.drawRect(debugrect[i][1],debugrect[i][2],debugrect[i][3],debugrect[i][4]);
   }
  }

       g.drawImage(offscreen,0,0,this);

    }

 public boolean keyUp (Event e, int key){
    if(key == Event.LEFT){
          playermoveleft = false;
        }
        if(key == Event.RIGHT){
          playermoveright = false;
        }
        if(key == 120 && isjumping == false && isfalling == false){ // x key
   endsquat = true;
        }
  // pick up rock..
  if(key == 97 && playersquated && playercaryingrock == false){ //97 . a key
   playerpickuprock = true;
  }
  // throw rock
  if(key == 97 && playersquated == false && playercaryingrock){ // a key
   playerthrowrock = true;
  }
  // put rock back on the ground
  if( key == 97 && playersquated && playercaryingrock && playerpickuprock == false){ // a key
   playerputrockback = true;
   //System.out.println("Put rock back on the ground..");
  }


    return true;
  }

  public boolean keyDown (Event e, int key){

  // move left
    if(key==Event.LEFT && playersquated == false ){
         playermoveleft = true;
         playermoveright = false;
   playerfacingleft = true;
   playerfacingright = false;
        }
        // move right
        if(key==Event.RIGHT && playersquated == false ){
          playermoveright = true;
   playermoveleft = false;
          playerfacingright = true;
          playerfacingleft = false;

        }
        // squat
  if(key == 120 && isjumping == false && isfalling == false && playersquated == false){ // x key // squat
   startsquat = true;
  }

      // Jump
      if(key == 122 && isjumping == false && isfalling == false){//z key / jump
   if(playersquated == false ){
    //System.out.println("Jump key pressed");
    isjumping = true;
    gravity = startingjumpspeed;
   }
      }
  //if(debugmode) System.out.println("Key touched : " + key);
  return true;
  }


 }
