package graphic.editor.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Spray {
	
	public static void spray(Graphics g, Point current) {
		
		Random r = new Random();

		g.fillOval(current.x+r.nextInt(), current.y+r.nextInt(), 3, 3);
		
	}

}
