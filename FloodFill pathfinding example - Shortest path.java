
import java.awt.*;
import java.applet.*;

public class pathfind07 extends Applet {

 // stores the map ; it is rotated, weird
 int map[][] ={
     {0,0,0,0,0,1,0,1,0,0},
     {0,1,1,1,0,1,0,1,0,1},
     {0,0,0,0,0,0,0,0,0,0},
     {1,1,1,1,1,1,1,0,1,1},
     {1,1,0,0,0,0,1,0,1,0},
     {0,1,1,1,0,0,1,0,0,0},
     {1,0,0,1,0,1,1,0,1,0},
     {1,1,0,1,0,0,0,0,1,0},
     {0,0,1,1,1,1,1,1,1,0},
     {0,0,1,0,0,0,0,0,0,0}
     };
 int[][][] map2 = new int[20][20][20];
 int mystartx=0;
 int mystarty=0;
 int myendx=9;
 int myendy=9;
 // Maximum length of path here is 100
 int[] pathx = new int[100];
 int[] pathy = new int[100];
 int cw=22;//draw cell width
 int ch=22;//draw cell height
 public void init() {

 }

 public void paint(Graphics g) {

  //draw map
  g.setColor(Color.black);
  for(int x=0;x<10;x++){
  for(int y=0;y<10;y++){
   // draw the distance map
   g.drawString(""+map2[x][y][2],x*cw+cw/2,y*ch+ch/2);
   // draw the tile map
   if(map[x][y]==1){
    g.fillRect(x*cw,y*ch,cw,ch);
   }
  }}

  // draw the start and end locations
  g.setColor(Color.red);
  g.drawRect(mystartx*cw+cw/4,mystarty*ch+ch/4,cw/2,ch/2);
  g.setColor(Color.green);
  g.drawRect(myendx*cw+cw/4,myendy*ch+ch/4,cw/2,cw/2);
  // draw path
  g.setColor(Color.black);
  for(int cnt3=1;cnt3<100;cnt3++)
  {
   if(pathx[cnt3-1]!=-1 && pathx[cnt3]!=-1)
   {
   g.drawLine(pathx[cnt3-1]*cw+cw/2,pathy[cnt3-1]*ch+ch/2,pathx[cnt3]*cw+cw/2,pathy[cnt3]*ch+ch/2);
   }
  }
  g.drawString("Press on the map to create path",0,230);
 }

 public boolean findpath(int startx,int starty,int endx,int endy)
 {
  System.out.println("Looking for path at :"+startx+":"+starty+":"+endx+":"+endy);
  if(startx<0) return false;
  if(startx>9) return false;
  if(starty<0) return false;
  if(starty>9) return false;
  if(map[startx][starty]==1)return false;
  if(map[endx][endy]==1)return false;

  //erase the old path
  for(int i=0;i<100;i++)
  {
   pathx[i]=-1;
   pathy[i]=-1;
  }
  //erase the path finding buffer map. [][][1]=donewith[][][2]=distance
  for(int y=0;y<20;y++){
  for(int x=0;x<20;x++){
   map2[x][y][1]=0;
   map2[x][y][2]=0;
  }
  }
  //Make arrays to store closeby cells in (open list and closed list)
  int[] x1 = new int[500];
  int[] y1 = new int[500];
  int[] x2 = new int[500];
  int[] y2 = new int[500];
  int cnt,x,y;
  int dist=0;
  int numitems = 0;
  int numitems2 = 0;
  // set all cell values to unused
  for(int i=0;i<500;i++){
   x1[i]=-1;
   y1[i]=-1;
   x2[i]=-1;
   y2[i]=-1;
  }
  // start location
  x1[0]=startx;
  y1[0]=starty;
  numitems = 1;
  // loop 120 times to fill the map
  for(int bbb=0;bbb<120;bbb++)
  {
   // increase the distance of the flood
   dist++;

   // fill done map
   for(int i=0;i < numitems ;i++){
     map2[x1[i]][y1[i]][1]=1;
     map2[x1[i]][y1[i]][2]=dist;
   }
   // Find neighboords for list 2
   cnt=0;
   x=0;
   y=0;
   for(int i=0; i < numitems ;i++){
    x=x1[i];
    y=y1[i];
    if(x-1>=0 && y-1>=0 && map[x-1][y-1]!=1 && map2[x-1][y-1][1]!=1)
    {
     x2[cnt]=x-1;
     y2[cnt]=y-1;
     map2[x-1][y-1][1]=1;
     cnt++;
    }
    if(x>=0 && y-1>=0 && map[x][y-1]!=1 && map2[x][y-1][1]!=1)
    {
     x2[cnt]=x;
     y2[cnt]=y-1;
     map2[x][y-1][1]=1;
     cnt++;
    }
    if(x+1<10 && y-1>=0 && map[x+1][y-1]!=1 && map2[x+1][y-1][1]!=1)
    {
     x2[cnt]=x+1;
     y2[cnt]=y-1;
     map2[x+1][y-1][1]=1;
     cnt++;
    }
    if(x-1>=0 && y>=0 && map[x-1][y]!=1 && map2[x-1][y][1]!=1)
    {
     x2[cnt]=x-1;
     y2[cnt]=y;
     map2[x-1][y][1]=1;
     cnt++;
    }
    if(x+1<10 && y>=0 && map[x+1][y]!=1 && map2[x+1][y][1]!=1)
    {
     x2[cnt]=x+1;
     y2[cnt]=y;
     map2[x+1][y][1]=1;
     cnt++;
    }
    if(x-1>=0 && y+1<10 && map[x-1][y+1]!=1 && map2[x-1][y+1][1]!=1)
    {
     x2[cnt]=x-1;
     y2[cnt]=y+1;
     map2[x-1][y+1][1]=1;
     cnt++;
    }
    if(x>=0 && y+1<10 && map[x][y+1]!=1 && map2[x][y+1][1]!=1)
    {
     x2[cnt]=x;
     y2[cnt]=y+1;
     map2[x][y+1][1]=1;
     cnt++;
    }
    if(x+1<10 && y+1<10 && map[x+1][y+1]!=1 && map2[x+1][y+1][1]!=1)
    {
     x2[cnt]=x+1;
     y2[cnt]=y+1;
     map2[x+1][y+1][1]=1;
     cnt++;
    }

   }
   numitems2 = cnt;
   //erase list 1
   for(int i=0; i < numitems ;i++){
    x1[i]=-1;
    y1[i]=-1;
   }
   //copy list 2 to list 1
   for(int i=0; i < numitems2 ;i++)
   {
    x1[i]=x2[i];
    y1[i]=y2[i];
    x2[i]=-1;
    y2[i]=-1;
   }
   numitems = numitems2;

  }

  // Find path Count back / trace back
  System.out.println("Tracing back");
  int lx=endx;//store the end location here to count back to start
  int ly=endy;
  //int[] nums = new int[10];
  int nums[] ={1000,1000,1000,1000,1000,1000,1000,1000,1000,1000};
  int cnt2=1;//for numbering the surrounding blocks
  int cnt3=0;//path index
  boolean exitloop=false;
  while(exitloop==false)
  {
   if(lx==startx && ly==starty) break;
   // erase the nums array (nums stores the distance value in the buffer map2[][][2]
   for(int cc=0;cc<10;cc++)
   {
    nums[cc]=1000;
   }
   cnt2=1;
   // Read the surrounding map cells into the nums array.
   if(lx-1>=0 && ly-1>=0 && map[lx-1][ly-1]!=1)
   {
    nums[cnt2]=map2[lx-1][ly-1][2];
   }
   cnt2++;
   if(lx>=0 && ly-1>=0 && map[lx][ly-1]!=1)
   {
    nums[cnt2]=map2[lx][ly-1][2];
   }
   cnt2++;
   if(lx+1<10 && ly-1>=0 && map[lx+1][ly-1]!=1)
   {
    nums[cnt2]=map2[lx+1][ly-1][2];
   }
   cnt2++;
   if(lx-1>=0 && ly>=0 && map[lx-1][ly]!=1)
   {
    nums[cnt2]=map2[lx-1][ly][2];
   }
   cnt2++;
   if(lx+1<10 && ly>=0 && map[lx+1][ly]!=1)
   {
    nums[cnt2]=map2[lx+1][ly][2];
   }
   cnt2++;
   if(lx-1>=0 && ly+1<10 && map[lx-1][ly+1]!=1)
   {
    nums[cnt2]=map2[lx-1][ly+1][2];
   }
   cnt2++;
   if(lx<10 && ly+1<10 && map[lx][ly+1]!=1)
   {
    nums[cnt2]=map2[lx][ly+1][2];
   }
   cnt2++;
   if(lx+1<10 && ly+1<10 && map[lx+1][ly+1]!=1)
   {
    nums[cnt2]=map2[lx+1][ly+1][2];
   }
   cnt2++;

   int lowest = 1000;//used to find lowest value
   int thenextloc=-1;
   // loop through all surrounding cells and find lowest value
   for(int i=1;i<10;i++)
   {
    System.out.print(""+nums[i]+",");
    if(nums[i] < lowest )
    {
     lowest = nums[i];
     thenextloc = i;//this is the next direction we travel in
    }
   }
   // if no next direction is found then clear path and exit method
   if(lowest == 1000 || lowest == 0)
   {
    System.out.println("quited 0 or 1000");
    for(int i=0;i<100;i++)
    {
     pathx[i]=-1;
     pathy[i]=-1;
    }
    return false;
   }
   System.out.println(" lowest value " + lowest + " thenextloc : "+thenextloc);
   // The thenextloc variable has the next direction we travel in
   // 1=leftabove 2=above 3=rightabove
   // 4=left 5 = right 6 = left below
   // 7=below 8 = right below
   if(thenextloc==1){
    lx--;
    ly--;
   }
   if(thenextloc==2){
    ly--;
   }
   if(thenextloc==3){
    lx++;
    ly--;
   }
   if(thenextloc==4){
    lx--;
   }
   if(thenextloc==5){
    lx++;
   }
   if(thenextloc==6){
    lx--;
    ly++;
   }
   if(thenextloc==7){
    ly++;
   }
   if(thenextloc==8){
    lx++;
    ly++;
   }
   //store the next coordinates in the path arrays
   pathx[cnt3]=lx;
   pathy[cnt3]=ly;
   // next slot in the path array if the path gets longer
   cnt3++;
  }
  // if the path was found then stop
  System.out.println("path found");
  return true;
 }


 public boolean mouseUp (Event e, int x, int y)
 {
  // divide the mouse coordinates with the width and
  // height of the screen map blocks
  int thex=x/cw;
  int they=y/ch;
  mystartx=thex;
  mystarty=they;
  //try to find the path
  findpath(thex,they,myendx,myendy);
  //repaint the screen
  repaint();

  return true;
 }


}

