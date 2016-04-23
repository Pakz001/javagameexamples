

import java.awt.*;
import java.applet.*;
import java.awt.geom.Area;
import java.util.Random;

public class ScrollingTileMap03 extends Applet implements Runnable{
 private boolean debugmode    = false;
 Random r = new Random();
 private int mapvisiblex;
 private int mapvisibley;
 private short mapwidth     = 250;
 private short mapheight     = 250;
 private short map[][]      = new short[ mapwidth ][ mapheight ];
 private int cellwidth     = 16;
 private int cellheight     = 16;
   Graphics bufferGraphics;
   Image bufferimage;
  private double mouseX;
  private double mouseY;
  private boolean playerMoveRight   = false;
  private boolean playerMoveLeft    = false;
    private double xPos      = 0;
    private double yPos      = 0;
    private boolean isFalling     = false;
    private boolean isJumping     = false;
 private double gravity;
 private boolean playerFacingLeft;
 private boolean playerFacingRight  = true;
 private int tileposx     = 0;
 private int tileposy     = 0;
 private int screenposx     = 0;
 private int screenposy     = 0;
 int[][] debug = new int[100][5];


 public void init() {
     setBackground(Color.black);
     bufferimage = createImage( getSize().width , getSize().height );
     bufferGraphics = bufferimage.getGraphics();
  generatemap();
  // find starting spot for the player
  findplayerstartposition();
  xPos = getSize().width / 2;
  yPos = getSize().height / 2;
    new Thread(this).start();
 }

 public void generatemap(){

  tileposx     = 0;
  tileposy     = 0;
  screenposx     = 0;
  screenposy     = 0;
  int size      = r.nextInt(12)+4;
  cellwidth     = size;
  cellheight     = size;
  mapvisiblex     = (int)getSize().width / cellwidth;
  mapvisibley     = (int)(getSize().height / cellheight);
  if(mapvisiblex > mapwidth ) mapvisiblex = mapwidth;
  if(mapvisibley > mapheight ) mapvisibley = mapheight;


  // erase old map
  for(int y = 0 ; y < mapheight ; y++){
   for(int x = 0 ; x < mapwidth ; x++){
    map[x][y] = 0;
   }
  }

  // create drunken lines
  for(int lne = 0; lne < mapheight-6 ; lne += 6 )
  {
   int turtley=5;
   int yoffset = lne;
   for (int i = 0 ; i < mapwidth ; i++){
    if(turtley + yoffset < mapheight - 1 && r.nextInt(10) > 8 ) turtley++;
    if(turtley + yoffset > 0 && r.nextInt(10) > 8 ) turtley--;

    map[i][turtley + yoffset] = 1;
    if(r.nextInt(10) > 8 && i < ( mapwidth - 2 ) ) {
     map[i][turtley+yoffset] = 0;
     map[i+1][turtley+yoffset] = 0;
     i += 1;
    }
   }

  }

  // put slopes in
  int mx1=0;
  int my1=0;
  int mx2=0;
  int my2=0;
  for( int y = 0 ; y < mapheight ; y++){
   for (int x = 0 ; x < mapwidth ; x++){
    mx1 = x+1;
    my1 = y-1;
    mx2 = x;
    my2 = y-1;
    if(mx1 >= 0 && mx1 < mapwidth && my1 >= 0 && my1 < mapheight ){
     if(mx2 >= 0 && mx2 < mapwidth && my2 >= 0 && my2 < mapheight ){
      if(map[x][y] == 1 && map[mx1][my1] == 1 && map[mx2][my2] == 0){
       map[mx2][my2] = 3;
      }
     }
    }
    mx1 = x-1;
    my1 = y-1;
    mx2 = x;
    my2 = y-1;
    if(mx1 >= 0 && mx1 < mapwidth && my1 >= 0 && my1 < mapheight ){
     if(mx2 >= 0 && mx2 < mapwidth && my2 >= 0 && my2 < mapheight ){
      if(map[x][y] == 1 && map[mx1][my1] == 1 && map[mx2][my2] == 0){
       map[mx2][my2] = 2;
      }
     }
    }

   }
  }

  // Put a wall around the map..
  for(int y = 0 ; y < mapheight ; y++){
   map[0][y]=1;
   map[ mapwidth - 1 ][y]=1;
  }
  for(int x = 0 ; x < mapwidth ; x++){
   map[x][0]=1;
   map[x][ mapheight - 1 ]=1;
  }


 }

  public boolean scrollmap(int x, int y){
   if(x > 0){
    for(int z = 0 ; z < x ; z++){
    if(tileposx == 0){
    }else{

      if(moveplayer(1,0,11) == true) screenposx++;
      if(screenposx > cellwidth){
       tileposx--;
       screenposx = 0;
      }
    }
    }
   }
   if(x < 0){
    for(int z = 0 ; z > x ; z--){
     if(tileposx + mapvisiblex == mapwidth ){
     }else{

      if(moveplayer(-1,0,12)==true) screenposx--;;
      if(screenposx < 0) {
       tileposx++;
       screenposx = cellwidth;
      }
     }
    }
   }
   if(y > 0){
    for( int z = 0 ; z < y ; z++){
     if (tileposy == 0){
      }else{
      screenposy++;
      moveplayer(0,1,13);

      if(screenposy > cellheight){
       tileposy--;
       screenposy = 0;
      }
     }
    }
   }
   if(y < 0){
    for(int z = 0 ; z > y ; z--){
     if(tileposy + mapvisibley == mapheight ){
     }else{
      screenposy--;
      moveplayer(0,-1,14);
      if(screenposy < 0 ) {
       tileposy++;
       screenposy = cellheight;
      }
     }
    }
   }
  return true;
  }

 public boolean moveplayer(int x,int y,int orig){
  if( rectcollision(xPos+x,yPos+y)!= 0 ) {
   return false;
  }
  xPos += x;
  yPos += y;
  return true;
 }

    public void run() {
        for(;;) { // animation loop never ends

   if(xPos < getSize().width / 2 ) scrollmap(1,0);
   if(xPos > getSize().width / 2 ) scrollmap(-1,0);
   if(yPos < getSize().height / 2 ) scrollmap(0,1);
   if(yPos > getSize().height / 2 ) scrollmap(0,-1);

   if(rectcollision(xPos,yPos+1)==0)
   {
    if(isFalling==false && isJumping==false)
    {
     isFalling=true;
     gravity=0;
    }
   }
   if(playerMoveRight)
   {
    if(rectcollision(xPos+1,yPos)==0){
     scrollmap(-1,0);
     moveplayer(1,0,2);;
    }

    if(rectcollision(xPos+1,yPos)==3)
    {
     moveplayer(1,-1,2);
     scrollmap(1,-1);
    }
   }
   if(playerMoveLeft)
   {
    if(rectcollision(xPos-1,yPos)==0){
     scrollmap(1,0);
     moveplayer(-1,0,3);
    }

    if(rectcollision(xPos-1,yPos)==2)
    {
     moveplayer(-1,-1,3);
     scrollmap(-1,-1);
    }
   }

   if(isJumping)
   {
    gravity = gravity - .1;
    if(gravity < 0){
     isJumping=false;
     isFalling=true;

    }
    for(int z = 0 ; z < Math.abs(gravity) ; z++){
     if(rectcollision(xPos,yPos-1)==0){
      moveplayer(0,-1,4);
     }else{
      isJumping=false;
      isFalling=true;
      break;
     }
    }
   }
   if(isFalling)
   {

    gravity = gravity + .1;
    for(int z = 0 ; z < gravity ; z++){
     if(rectcollision(xPos,yPos+1)==0){
      moveplayer(0,1,5);
      scrollmap(0,-1);
     }else{
      isFalling = false;
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

 public short rectcollision(double x,double y)
 {
  for(int i = 0 ; i<100 ; i++ ){
   debug[i][0]=0;
  }
  int chkx=(int)x/cellwidth+tileposx;
  int chky=(int)y/cellheight+tileposy;
  int x1=(int)(x);
  int y1=(int)(y);
  int cnt=0;
  for(int y2=-1;y2<3;y2++)
  {
   for(int x2=-1;x2<3;x2++)
   {
    if (chkx+x2>=0 && chkx + x2 < mapwidth && chky+y2>=0 && chky+y2 < mapheight)
    {


     if(map[chkx+x2][chky+y2]==2)
     {
      int x3=((chkx-tileposx)+x2)*cellwidth+screenposx;
      int y3=((chky-tileposy)+y2)*cellheight+screenposy;
       Rectangle rec1 = new Rectangle(x1,y1,cellwidth,cellheight);
        Rectangle rec2 = new Rectangle(x3,y3,cellwidth,cellheight);
      if(rec1.intersects(rec2))
      {
       int[] XArray = {x3,x3,x3+cellwidth};
       int[] YArray = {y3,y3+cellheight,y3+cellheight};
          int[] XArray2= {x1,x1+cellwidth,x1+cellwidth,x1};
          int[] YArray2= {y1,y1,y1+cellheight,y1+cellheight};
          Polygon abc = new Polygon(XArray, YArray, 3);
          Polygon abc2= new Polygon(XArray2,YArray2,4);
          Area a1 = new Area(abc);
          Area a2 = new Area(abc2);
       if(debugmode){
        debug[cnt][0]=1;
        debug[cnt][1]=x3;
        debug[cnt][2]=y3;
        debug[cnt][3]=cellwidth;
        debug[cnt][4]=cellheight;
        cnt++;
        debug[cnt][0]=1;
        debug[cnt][1]=x1;
        debug[cnt][2]=y1;
        debug[cnt][3]=cellwidth;
        debug[cnt][4]=cellheight;
        cnt++;
       }
        a1.intersect(a2);
        if (!a1.isEmpty())
        {
         return 2;
         }

      }
     }


     if(map[chkx+x2][chky+y2]==3)
     {
      int x3=((chkx-tileposx)+x2)*cellwidth+screenposx;
      int y3=((chky-tileposy)+y2)*cellheight+screenposy;

       Rectangle rec1 = new Rectangle(x1,y1,cellwidth,cellheight);
        Rectangle rec2 = new Rectangle(x3,y3,cellwidth,cellheight);
      if(rec1.intersects(rec2))
      {
       int[] XArray = {x3,x3+cellwidth,x3+cellwidth};
       int[] YArray = {y3+cellheight,y3+cellheight,y3};
          int[] XArray2= {x1,x1+cellwidth,x1+cellwidth,x1};
          int[] YArray2= {y1,y1,y1+cellheight,y1+cellheight};
          Polygon abc = new Polygon(XArray, YArray, 3);
          Polygon abc2= new Polygon(XArray2,YArray2,4);
          Area a1 = new Area(abc);
          Area a2 = new Area(abc2);
       if(debugmode){
         debug[cnt][0]=1;
        debug[cnt][1]=x3;
        debug[cnt][2]=y3;
        debug[cnt][3]=cellwidth;
        debug[cnt][4]=cellheight;
        cnt++;
        debug[cnt][0]=1;
        debug[cnt][1]=x1;
        debug[cnt][2]=y1;
        debug[cnt][3]=cellwidth;
        debug[cnt][4]=cellheight;
        cnt++;
       }
       a1.intersect(a2);
        if (!a1.isEmpty())
        {
         return 3;
         }

      }
     }


     if (map[chkx+x2][chky+y2]==1)
     {
      int x3=((chkx-tileposx)+x2)*cellwidth+screenposx;
      int y3=((chky-tileposy)+y2)*cellheight+screenposy;
       Rectangle rec1 = new Rectangle(x1,y1,cellwidth,cellheight);
        Rectangle rec2 = new Rectangle(x3,y3,cellwidth,cellheight);
      if(debugmode){
       debug[cnt][0]=1;
       debug[cnt][1]=x3;
       debug[cnt][2]=y3;
       debug[cnt][3]=cellwidth;
       debug[cnt][4]=cellheight;
       cnt++;
       debug[cnt][0]=1;
       debug[cnt][1]=x1;
       debug[cnt][2]=y1;
       debug[cnt][3]=cellwidth;
       debug[cnt][4]=cellheight;
       cnt++;
      }
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

 public void update (Graphics g)
  {
   bufferGraphics.clearRect(0, 0, getSize().width , getSize().height );
  bufferGraphics.setColor (Color.white);
  int drawx = 0;
  int drawy = 0;
  int mx = 0;
  int my = 0;

  for(int y=-1;y < mapvisibley + 1 ;y++){
  for(int x=-1;x < mapvisiblex + 1;x++){
   drawx = x*cellwidth+screenposx;
   drawy = y*cellheight+screenposy;
   mx = x + tileposx;
   my = y + tileposy;
   if(mx >= 0 && mx < mapwidth && my >= 0 && my < mapheight ){
    if(map[mx][my]==1)
    {
     bufferGraphics.fillRect(drawx,drawy,cellwidth,cellheight);
    }
    if(map[mx][my]==2)
    {
     int[] XArray = {drawx,drawx,drawx+cellwidth};
     int[] YArray = {drawy,drawy+cellheight,drawy+cellheight};
         Polygon abc = new Polygon(XArray, YArray, 3);

     bufferGraphics.fillPolygon(abc);
    }
    if(map[mx][my]==3)
    {
     int[] XArray = {drawx,drawx+cellwidth,drawx+cellwidth};
     int[] YArray = {drawy+cellheight,drawy+cellheight,drawy};
        Polygon abc = new Polygon(XArray, YArray, 3);

     bufferGraphics.fillPolygon(abc);
    }
   }
  }
  }
  bufferGraphics.setColor(Color.black);
  bufferGraphics.fillRect(3,getSize().height - 30 , getSize().width , 12);
  bufferGraphics.setColor(Color.white);
  bufferGraphics.drawString("Scrolling platformer example - z = jump - Cursors l and r",3,getSize().height - 20 );
  int x = (int) xPos;
  int y = (int) yPos;

  // This is the player graphic
  bufferGraphics.fillOval (x, y, cellwidth, cellheight);

  if(debugmode){
   bufferGraphics.setColor(Color.blue);
   for(int i=0; i < 100 ; i++){
    if(debug[i][0] == 1){
     bufferGraphics.drawRect(debug[i][1],debug[i][2],debug[i][3],debug[i][4]);
    }
   }
  }

  g.drawImage(bufferimage,0,0,this);
  }

 public void paint(Graphics g) {
 }

 public boolean mouseMove(Event e, int x, int y)
  {
    mouseX = x;
    mouseY = y;
    return true;
  }

  public boolean keyUp (Event e, int key){
    if(key==114)  // r key
        {
   xPos = 150;
   yPos = 150;
        }
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

      if(key==122) // z key
      {
        if(isFalling==false && isJumping==false)
        {
    if(rectcollision(xPos,yPos-3)==0){
             isJumping = true;
             gravity = 3;
    }
        }
      }


      if(key==100 && debugmode){ // d key
       generatemap();
      }
        return true;
  }

 public void findplayerstartposition()
 {
  while(rectcollision(xPos,yPos)!= 0 ){
   xPos = r.nextInt( getSize().width - 50 ) + 50;
   yPos = r.nextInt( getSize().height - 50 ) + 50;
  }

 }
 public boolean mouseUp (Event e, int x, int y)
 {
  generatemap();
  findplayerstartposition();
  return true;
 }

}
