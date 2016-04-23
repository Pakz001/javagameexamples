
import java.awt.*;
import java.applet.*;
import java.util.Random;

public class MazeGenerator01 extends Applet {

 int SizeX = 32;
 int SizeY = 32;
 private short[][] map = new short[ SizeX ][ SizeY ];
 Random r = new Random();

 public void init() {
  generatemaze(r.nextInt(70)+30);
 }

 public boolean mouseUp( Event evt, int x, int y)
 {
  generatemaze(r.nextInt(70)+30);
  repaint();
  return true;
 }

 public void paint(Graphics g) {

  g.drawString("Press mouse in Applet to create new maze", 0, getSize().height-12 );
  for(int x=0;x<32;x++)
  {
   for(int y=0;y<32;y++)
   {
    if(map[x][y]==4)
    {
     g.fillRect(x*10,y*7,10,7);
    }
   }
  }
 }

 public void generatemaze(int straightness)
 {
  // Maze Generator original by John Chase
  short Wall     = 4;
  short Flr     = 5;
  int NotChecked    = 0;
  int Checked    = 1;
  int Open          = 2;
  int NotOpen       = 3;
  int AllChecked    = SizeX * SizeY;
  int LastDirection   = r.nextInt(4);
  int North         = Open;
  int South         = Open;
  int East          = Open;
  int West          = Open;
  boolean TimeUp     = false;
  boolean Moved   = false;
  int NumFailedMoves   = 0;
  boolean ChangeDirection = false;
  boolean tocheckdirection= false;
  int CurrentX   = 0;
  int CurrentY   = 0;
  int Dir     = 0;
  long LastDrawTimer  = 0;
  boolean Done   = false;
  //make map array fill with walls
  for(int x=0;x < SizeX - 1 ; x++ )
  {
   for(int y=0;y < SizeY - 1 ; y++ )
   {
    map[x][y] = Wall;
   }
  }

  //pick a random cell and mark it as Flr hold in 1 cell from the edge
  CurrentX = r.nextInt( SizeX - 3 ) + 2;
  CurrentY = r.nextInt( SizeY - 3 ) + 2;

  while(TimeUp != true)
  {
   if(tocheckdirection!=true)
   {

    //pick a direction
    Moved = false;
    NumFailedMoves = 0;
    ChangeDirection = true;

    //check strightness factor
    //otherwise random percent chance
    if(r.nextInt(100) < straightness)
    {
     ChangeDirection = false;
     Dir = LastDirection;
    }

    //keep trying till you find a direction open
    while(Moved != true)//Repeat
    {
     //pick a direction to move at random
     if(ChangeDirection == true)
     {
      Dir = r.nextInt(4);//see here for bugs
      LastDirection = Dir;
     }

     ChangeDirection = true;

     switch(Dir)
     {
     //north
     case 0:
      if(North == Open)
      {
       Moved = true;
       CurrentY--;
      }
      break;
     //south
     case 1:
      if(South == Open)
      {
       Moved = true;
       CurrentY++;
      }
      break;
     //east
     case 2:
      if(East == Open)
      {
       Moved = true;
       CurrentX++;
      }
      break;
     //West
     case 3:
      if(West == Open)
      {
       Moved = true;
       CurrentX--;
      }
      break;
     }

    }//Until Moved = True
    //mark the map
    map[CurrentX][CurrentY] = Flr;
    LastDrawTimer = System.currentTimeMillis();
   }

   tocheckdirection = false;
   //step 3 from current cell check N,S,E,W in a random style
   //first set all direction Direction checked
   North = NotOpen;
   South = NotOpen;
   East  = NotOpen;
   West  = NotOpen;

   //check all 4 directions
   //;north
   //;out of bounds?
   if(CurrentY-2 < 0)
   {
    North = NotOpen;
   }
   else if(map[CurrentX][CurrentY-1] == Wall && map[CurrentX][CurrentY-2] == Wall)
   {
    if(map[CurrentX-1][CurrentY-1] == Wall && map[CurrentX+1][CurrentY-1] == Wall)
    {
     North = Open;
    }
    else
    {
    North = NotOpen;
    }
   }

   //south
   //out of bounds?
   if(CurrentY+2 > SizeY - 1 )
   {
    South = NotOpen;
   }
   else if(map[CurrentX][CurrentY+1] == Wall && map[CurrentX][CurrentY+2] == Wall)
   {
    if(map[CurrentX-1][CurrentY+1] == Wall && map[CurrentX+1][CurrentY+1] == Wall)
    {
     South = Open;
    }
    else
    {
     South = NotOpen;
    }
   }
   //;east
   if(CurrentX+2 > SizeX - 1 )
   {
    East = NotOpen;
   }
   else if(map[CurrentX+1][CurrentY] == Wall && map[CurrentX+2][CurrentY] == Wall )
   {
    if(map[CurrentX+1][CurrentY-1] == Wall && map[CurrentX+1][CurrentY+1] == Wall)
    {
     East = Open;
    }
    else
    {
     East = NotOpen;
    }
   }
   //west
   //out of bounds?
   if(CurrentX-2 < 0)
   {
    West = NotOpen;
   }
   else if(map[CurrentX-1][CurrentY] == Wall && map[CurrentX-2][CurrentY] == Wall)
   {
    if(map[CurrentX-1][CurrentY-1] == Wall && map[CurrentX-1][CurrentY+1]== Wall)
    {
     West = Open;
    }
    else
    {
     West = NotOpen;
    }
   }

   //;if time passes without finding anything we are done
   if( System.currentTimeMillis() - LastDrawTimer > 100 ) TimeUp = true;
   //now what happens if all directions are not open
   if(North == NotOpen && South == NotOpen && East == NotOpen && West == NotOpen && TimeUp == false)
   {
    Done = false;
    //pick a random already floored location and try again
    while(Done !=true)
    {
     CurrentX = r.nextInt( SizeX - 2 ) + 1;
     CurrentY = r.nextInt( SizeY - 2 ) + 1;
     if(map[CurrentX][CurrentY] == Flr) Done = true;
    }
    //goto checkdirection
    tocheckdirection=true;
   }

  }

 }


}
