
import java.applet.*;
import java.awt.*;
import java.util.Random;
import java.awt.image.BufferedImage;


public class SimCityRoadSystem01 extends Applet {
	Random	r = new Random();
	Graphics bufferGraphics;
    Image offscreen;
	short[][] map = new short[20][15];
	short[][] roadmap = new short[20][15];
	BufferedImage roads[] = new BufferedImage[17];


	private short road[][][]={
							// road 0 - connects to all
							{
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0}
							},

							// road 1 - connects to all
							{
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,1,1,0,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,0,1,1,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0}
							},
							// road 2 - connects top to center
							{
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,0,1,1,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0}
							},
							// road 3 - connects right to center
							{
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,1,1,1,1,1},
							{0,0,1,1,1,1,1,1},
							{0,0,1,1,1,1,1,1},
							{0,0,0,1,1,1,1,1},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0}
							},
							// road 4 - connects bottom to center
							{
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,1,1,0,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0}
							},
							// road 5 - connects left to center
							{
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{1,1,1,1,1,0,0,0},
							{1,1,1,1,1,1,0,0},
							{1,1,1,1,1,1,0,0},
							{1,1,1,1,1,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0}
							},
							// road 6 - connects top and right to center
							{
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,1,1},
							{0,0,1,1,1,1,1,1},
							{0,0,1,1,1,1,1,1},
							{0,0,0,1,1,1,1,1},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0}
							},
							// road 7 - connects top and bottom to center
							{
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0}
							},
							// road 8 - connects top and left to center
							{
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{1,1,1,1,1,1,0,0},
							{1,1,1,1,1,1,0,0},
							{1,1,1,1,1,1,0,0},
							{1,1,1,1,1,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0}
							},
							// road 9 - connects top and bottom and right to center
							{
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,1,1},
							{0,0,1,1,1,1,1,1},
							{0,0,1,1,1,1,1,1},
							{0,0,1,1,1,1,1,1},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0}
							},
							// road 10- connects top and bottom and left to center
							{
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{1,1,1,1,1,1,0,0},
							{1,1,1,1,1,1,0,0},
							{1,1,1,1,1,1,0,0},
							{1,1,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0}
							},
							// road 11 - connects right and bottom to center
							{
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{0,0,0,1,1,1,1,1},
							{0,0,1,1,1,1,1,1},
							{0,0,1,1,1,1,1,1},
							{0,0,1,1,1,1,1,1},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0}
							},
							// road 12 - connects left and right to center
							{
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0}
							},
							// road 13 - connects left and bottom to center
							{
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{1,1,1,1,1,0,0,0},
							{1,1,1,1,1,1,0,0},
							{1,1,1,1,1,1,0,0},
							{1,1,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0}
							},
							// road 14 - connects left and bottom to center
							{
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0}
							},
							// road 15 - connects left and bottom to center
							{
							{0,0,0,0,0,0,0,0},
							{0,0,0,0,0,0,0,0},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0}
							},
							// road 16 - connects top and left and bottom to center
							{
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{0,0,1,1,1,1,0,0},
							{0,0,1,1,1,1,0,0}
							},

	};

    public void init(){
        Graphics2D g;

        setBackground(Color.black);
        offscreen = createImage(getSize().width,getSize().height);
       	bufferGraphics = offscreen.getGraphics();

		for ( int i = 0 ; i < 17 ; i++){
			roads[i] = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
 			g = roads[i].createGraphics();
			for ( int y = 0 ; y < 8 ; y++){
				for ( int x = 0 ; x < 8 ; x++){
					if (road[i][x][y] == 1) {
						g.setColor(new Color(150,150,150,255));
					}
					if (road[i][x][y] == 0) {
							g.setColor(new Color(0,0,0,0));
					}
					g.fillRect(y*2,x*2,2,2);
				}
			}
		}

     }

	public boolean doroadmap( int x , int y ){
		if ( x < 0 ) return false;
		if ( x > 19 ) return false;
		if ( y < 0 ) return false;
		if ( y > 14 ) return false;
		String roadsit;

		roadsit = "";

		if ( roadmap[x][y] == 1 ) roadsit=roadsit + "1";

		if ( y - 1 < 0 ) {
			roadsit = roadsit + "0";
		}else{
			if ( roadmap[x][y-1] == 1 ) {
				roadsit=roadsit + "1";
			}else{
				roadsit=roadsit + "0";
			}
		}
		if ( x - 1 < 0 ) {
			roadsit = roadsit + "0";
		}else{
			if ( roadmap[x - 1][y] == 1 ){
				roadsit = roadsit + "1";
			}else{
				roadsit = roadsit + "0";
			}
		}
		if ( x + 1 > 19 ) {
			roadsit = roadsit + "0";
		}else{
			if ( roadmap[ x + 1 ][ y ] == 1 ){
				roadsit = roadsit + "1";
			}else{
				roadsit = roadsit + "0";
				}
		}
		if ( y + 1 > 14 ){
			roadsit = roadsit + "0";
		}else{
			if ( roadmap[x][y+1] == 1 ){
				roadsit = roadsit + "1";
			}else{
				roadsit = roadsit + "0";
			}
		}

		if ( roadsit.equals("10000") ) map[x][y] = 1;
		if ( roadsit.equals("11000") ) map[x][y] = 2;
		if ( roadsit.equals("10010") ) map[x][y] = 3;
		if ( roadsit.equals("10001") ) map[x][y] = 4;
		if ( roadsit.equals("10100") ) map[x][y] = 5;
		if ( roadsit.equals("11010") ) map[x][y] = 6;
		if ( roadsit.equals("11001") ) map[x][y] = 7;
		if ( roadsit.equals("11100") ) map[x][y] = 8;
		if ( roadsit.equals("11011") ) map[x][y] = 9;
		if ( roadsit.equals("11101") ) map[x][y] = 10;
		if ( roadsit.equals("10011") ) map[x][y] = 11;
		if ( roadsit.equals("10110") ) map[x][y] = 12;
		if ( roadsit.equals("10101") ) map[x][y] = 13;
		if ( roadsit.equals("11110") ) map[x][y] = 14;
		if ( roadsit.equals("10111") ) map[x][y] = 15;
		if ( roadsit.equals("11111") ) map[x][y] = 16;



		return true;

	}

 	public boolean mouseDown (Event e, int x, int y) {
        if (e.modifiers == Event.META_MASK) {
        //    info=("Right Button Pressed");
        } else if (e.modifiers == Event.ALT_MASK) {
        //    info=("Middle Button Pressed");
        } else {
        //   info=("Left Button Pressed");
//        	System.out.println( "left mouse pressed ");
        	roadmap[ x / 16 ][ y / 16 ] = 1;
        	for ( int y1 = -1 ; y1 <=1 ; y1++ ){
        		for ( int x1 = -1 ; x1 <=1 ; x1++ ) {
            		doroadmap ( ( x / 16 ) + x1  ,  ( y / 16 ) + y1  );
        		}
        	}
        }
        repaint();
        return true;
    }
	public void paint(Graphics g){
    	bufferGraphics.clearRect(0,0,getSize().width,getSize().height);
    	bufferGraphics.setColor(Color.red);
        bufferGraphics.drawString("Sim City Road System.",10,10);
		bufferGraphics.drawString("Press the mouse on the applet ",10,20);
     	bufferGraphics.drawString("to lay roads.",10,30);


		for ( int x = 0 ; x < 20 ; x++ ) {
			for ( int y = 0 ; y < 15 ; y++ ) {
				bufferGraphics.drawImage( roads[map[ x ][ y ]] , x * 16 , y * 16 , this );
			}
		}


      g.drawImage(offscreen,0,0,this);

     }
    public void update(Graphics g){
          paint(g);
     }
 }


