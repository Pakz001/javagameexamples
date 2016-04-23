

import java.awt.*;
import java.applet.*;
import java.util.ArrayList;

public class astarpathfindingexample01 extends Applet {

 int map[][] ={
     {0,0,0,0,0,0,0,0,0,0},
     {0,1,1,1,1,1,1,1,1,0},
     {0,0,0,1,0,1,0,1,0,0},
     {0,0,0,1,0,1,0,1,0,0},
     {0,1,1,1,0,0,0,1,0,0},
     {0,0,0,1,0,1,0,0,0,0},
     {0,0,0,1,0,1,0,1,0,0},
     {0,1,1,1,1,1,0,1,0,0},
     {0,0,0,0,0,1,0,1,1,0},
     {0,0,0,0,0,0,0,0,0,0}
     };
 int mapwidth = 9;
 int mapheight = 9;
 int cellwidth = 32;
 int cellheight = 24;

 int sx,sy,ex,ey;

 // Open list ( x, y, f, g, h, parentx, parenty )
 ArrayList olx = new ArrayList();
 ArrayList oly = new ArrayList();
 ArrayList olf = new ArrayList();
 ArrayList olg = new ArrayList();
 ArrayList olh = new ArrayList();
 ArrayList olpx = new ArrayList();
 ArrayList olpy = new ArrayList();
 // Closed list ( x, y, f, g, h, parentx, parenty )
 ArrayList clx = new ArrayList();
 ArrayList cly = new ArrayList();
 ArrayList clf = new ArrayList();
 ArrayList clg = new ArrayList();
 ArrayList clh = new ArrayList();
 ArrayList clpx = new ArrayList();
 ArrayList clpy = new ArrayList();
 // Path
 ArrayList px = new ArrayList();
 ArrayList py = new ArrayList();

 public void init() {
  setBackground(Color.black);

 }

 public void findpathback(){
  boolean exitloop = false;
  int x = ex;
  int y = ey;
  while ( exitloop == false ){
   for ( int i = 0 ; i < clx.size() ; i++ ){
    if ( clx.get( i ) == x && cly.get( i ) == y ){
     x = clpx.get( i );
     y = clpy.get( i );
     px.add( x );
     py.add( y );
    }
   }
   if ( x == sx && y == sy ) {
    exitloop = true;
   }
  }
 }

 public boolean removefromopenlist( int x , int y ){
  for ( int i = 0 ; i < olx.size() ; i++ ){
   if ( olx.get(i) == x && oly.get(i) == y ){
    olx.remove(i);
    oly.remove(i);
    olf.remove(i);
    olg.remove(i);
    olh.remove(i);
    olpx.remove(i);
    olpy.remove(i);
    return true;
   }
  }
  return false;
 }

 public boolean isonclosedlist( int x , int y ){
  for ( int i = 0 ; i < clx.size() ; i++ ){
   if ( clx.get(i) == x && cly.get(i) == y ) {
    return true;
   }
  }
  return false;
 }

 public boolean isonopenlist( int x , int y ){
  for ( int i = 0 ; i < olx.size() ; i++){
   if ( olx.get(i) == x && oly.get(i) == y ){
    return true;
   }
  }
  return false;
 }

 public boolean openlistisempty(){
  if ( olx.size() > 0 ) {
   return false;
  }
  return true;
 }

 public void setcoordinates(){
  boolean exitloop = false;
  while ( exitloop == false ){
   sx = (int)( Math.random() * mapwidth );
   sy = (int)( Math.random() * mapheight );
   ex = (int)( Math.random() * mapwidth );
   ey = (int)( Math.random() * mapheight );
   if ( map[ sy ][ sx ] == 0 && map[ ey ][ ex ] == 0 ){
    if ( sx != ex && sy != ey ){
     exitloop = true;
    }
   }
  }
 }

 public void findpath(){
  // Clear all the pathfinding data
  olx.clear();
  oly.clear();
  olf.clear();
  olg.clear();
  olh.clear();
  olpx.clear();
  olpy.clear();
  //
  clx.clear();
  cly.clear();
  clf.clear();
  clg.clear();
  clh.clear();
  clpx.clear();
  clpy.clear();
  //
  px.clear();
  py.clear();
  //
  // Move the start position onto the open list
  olx.add( sx );
  oly.add( sy );
  olf.add( 0 );
  olg.add( 0 );
  olh.add( 0 );
  olpx.add( 0 );
  olpy.add( 0 );
  //
  boolean exitloop = false;
  int tx = 0;
  int ty = 0;
  int tf = 0;
  int tg = 0;
  int th = 0;
  int tpx = 0;
  int tpy = 0;
  int newx = 0;
  int newy = 0;
  int lowestf = 0;
  while ( exitloop == false ){
   // If the open list is empty then exit loop
   if ( openlistisempty() == true ){
    exitloop = true;
   }
   // Get the lowest f value position from the
   // open list and use that.
   lowestf = 100000;
   for ( int i = 0 ; i < olx.size() ; i++ ){
    if ( olf.get( i ) < lowestf ) {
     lowestf = olf.get( i );
     tx = olx.get( i );
     ty = oly.get( i );
     tf = olf.get( i );
     tg = olg.get( i );
     th = olh.get( i );
     tpx = olpx.get( i );
     tpy = olpy.get( i );
    }
   }
   // if the current position is the end position then
   // path was found.
   if ( tx == ex && ty == ey ){
    exitloop = true;
    clx.add( tx );
    cly.add( ty );
    clf.add( tf );
    clg.add( tg );
    clh.add( th );
    clpx.add( tpx );
    clpy.add( tpy );
    findpathback();
   }else{
    // Move the current position onto the closed list
    clx.add( tx );
    cly.add( ty );
    clf.add( tf );
    clg.add( tg );
    clh.add( th );
    clpx.add( tpx );
    clpy.add( tpy );
    // Remove the current position from the open is
    removefromopenlist( tx , ty );
    // Get the eight positions from around the current
    // position and move them onto the open list.
    //
    newx = tx - 1;
    newy = ty - 1;
    if ( newx > -1 && newy > -1 && newx < mapwidth + 1 && newy < mapheight + 1 ){
    if ( isonopenlist( newx , newy ) == false ){
    if ( isonclosedlist( newx , newy ) == false ){
    if ( map[ newy ][ newx ] == 0 ){
     olx.add( newx );
     oly.add( newy );
     olg.add( tg + 1 );
     olh.add( distance( newx , newy , ex , ey ));
     olf.add( ( tg + 1 ) + distance( newx , newy , ex , ey ) );
     olpx.add( tx );
     olpy.add( ty );
    }
    }
    }
    }
    //
    newx = tx;
    newy = ty - 1;
    if ( newx > -1 && newy > -1 && newx < mapwidth + 1 && newy < mapheight + 1 ){
    if ( isonopenlist( newx , newy ) == false ){
    if ( isonclosedlist( newx , newy ) == false ){
    if ( map[ newy ][ newx ] == 0 ){
     olx.add( newx );
     oly.add( newy );
     olg.add( tg + 1 );
     olh.add( distance( newx , newy , ex , ey ));
     olf.add( ( tg + 1 ) + distance( newx , newy , ex , ey ) );
     olpx.add( tx );
     olpy.add( ty );
    }
    }
    }
    }
    //
    newx = tx + 1;
    newy = ty - 1;
    if ( newx > -1 && newy > -1 && newx < mapwidth + 1 && newy < mapheight + 1 ){
    if ( isonopenlist( newx , newy ) == false ){
    if ( isonclosedlist( newx , newy ) == false ){
    if ( map[ newy ][ newx ] == 0 ){
     olx.add( newx );
     oly.add( newy );
     olg.add( tg + 1 );
     olh.add( distance( newx , newy , ex , ey ));
     olf.add( ( tg + 1 ) + distance( newx , newy , ex , ey ) );
     olpx.add( tx );
     olpy.add( ty );
    }
    }
    }
    }
    //
    newx = tx - 1;
    newy = ty;
    if ( newx > -1 && newy > -1 && newx < mapwidth + 1 && newy < mapheight + 1 ){
    if ( isonopenlist( newx , newy ) == false ){
    if ( isonclosedlist( newx , newy ) == false ){
    if ( map[ newy ][ newx ] == 0 ){
     olx.add( newx );
     oly.add( newy );
     olg.add( tg + 1 );
     olh.add( distance( newx , newy , ex , ey ));
     olf.add( ( tg + 1 ) + distance( newx , newy , ex , ey ) );
     olpx.add( tx );
     olpy.add( ty );
    }
    }
    }
    }
    //
    newx = tx + 1;
    newy = ty;
    if ( newx > -1 && newy > -1 && newx < mapwidth + 1 && newy < mapheight + 1 ){
    if ( isonopenlist( newx , newy ) == false ){
    if ( isonclosedlist( newx , newy ) == false ){
    if ( map[ newy ][ newx ] == 0 ){
     olx.add( newx );
     oly.add( newy );
     olg.add( tg + 1 );
     olh.add( distance( newx , newy , ex , ey ));
     olf.add( ( tg + 1 ) + distance( newx , newy , ex , ey ) );
     olpx.add( tx );
     olpy.add( ty );
    }
    }
    }
    }
    //
    newx = tx - 1;
    newy = ty + 1;
    if ( newx > -1 && newy > -1 && newx < mapwidth + 1 && newy < mapheight + 1 ){
    if ( isonopenlist( newx , newy ) == false ){
    if ( isonclosedlist( newx , newy ) == false ){
    if ( map[ newy ][ newx ] == 0 ){
     olx.add( newx );
     oly.add( newy );
     olg.add( tg + 1 );
     olh.add( distance( newx , newy , ex , ey ));
     olf.add( ( tg + 1 ) + distance( newx , newy , ex , ey ) );
     olpx.add( tx );
     olpy.add( ty );
    }
    }
    }
    }
    //
    newx = tx;
    newy = ty + 1;
    if ( newx > -1 && newy > -1 && newx < mapwidth + 1 && newy < mapheight + 1 ){
    if ( isonopenlist( newx , newy ) == false ){
    if ( isonclosedlist( newx , newy ) == false ){
    if ( map[ newy ][ newx ] == 0 ){
     olx.add( newx );
     oly.add( newy );
     olg.add( tg + 1 );
     olh.add( distance( newx , newy , ex , ey ));
     olf.add( ( tg + 1 ) + distance( newx , newy , ex , ey ) );
     olpx.add( tx );
     olpy.add( ty );
    }
    }
    }
    }
    //
    newx = tx + 1;
    newy = ty + 1;
    if ( newx > -1 && newy > -1 && newx < mapwidth + 1 && newy < mapheight + 1 ){
    if ( isonopenlist( newx , newy ) == false ){
    if ( isonclosedlist( newx , newy ) == false ){
    if ( map[ newy ][ newx ] == 0 ){
     olx.add( newx );
     oly.add( newy );
     olg.add( tg + 1 );
     olh.add( distance( newx , newy , ex , ey ));
     olf.add( ( tg + 1 ) + distance( newx , newy , ex , ey ) );
     olpx.add( tx );
     olpy.add( ty );
    }
    }
    }
    }

   }

  }
 }

 public int distance( int x1 , int y1 , int x2 , int y2 ){
  int distance=(int)Math.sqrt( ( x1 - x2 ) * ( x1 - x2 ) + ( y1 - y2 ) * ( y1 - y2 ) ) ;
  return distance;
 }

 public void paint(Graphics g) {

  setcoordinates();
  findpath();
  // Draw the map on the applet window
  g.setColor( Color.blue );
  for ( int y = 0 ; y < mapheight ; y++ ){
  for ( int x = 0 ; x < mapwidth ; x++ ){
   if ( map[ y ][ x ] == 1 ){
    g.fillRect( x * cellwidth , y * cellheight , cellwidth , cellheight );
   }
  }
  }
  // Draw the start position on the applet window
  g.setColor( Color.green );
  g.fillOval( sx * cellwidth + 4 , sy * cellheight + 4 , 8 , 8 );
  g.setColor( Color.red );
  g.fillOval( ex * cellwidth + 4 , ey * cellheight + 4 , 8 , 8 );

  // Draw the path
  g.setColor( Color.yellow );
  for ( int i = 0 ; i < px.size() ; i++ ){
   g.fillOval( px.get( i ) * cellwidth + 8 , py.get( i ) * cellheight + 8 , 8 , 8 );
  }

 }
}

