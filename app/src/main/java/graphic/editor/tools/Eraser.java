package graphic.editor.tools;

import java.awt.*;

public class Eraser {

	public static void erase(Graphics2D g, Point start, Point end, int stroke) {
		
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(stroke));
		g.drawLine(start.x, start.y, end.x, end.y);
		
	}
	
}
