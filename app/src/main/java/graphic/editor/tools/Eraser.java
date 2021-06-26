package graphic.editor.tools;

import java.awt.*;

public class Eraser {

	public static void erase(Graphics g, Point current) {
		
		g.setColor(Color.WHITE);
		g.drawLine(current.x, current.y, current.x, current.y);
		
	}
	
}
