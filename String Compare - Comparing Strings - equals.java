


/**
 * @(#)StringCompare01.java
 *
 * StringCompare01 Applet application
 *
 * @author
 * @version 1.00 2011/7/21
 */

import java.awt.*;
import java.applet.*;

public class StringCompare01 extends Applet {

	public void init() {
	}

	public void paint(Graphics g) {

		String banana = "";

		for ( int i = 0 ; i < 9 ; i++){
			banana = banana + "0";
		}
		System.out.println(banana);
		if ( banana.equals("000000000")) {
			g.drawString("String equals 000000000", 50, 90 );
		}

		g.drawString("Welcome to Java!!", 50, 60 );

	}
}
