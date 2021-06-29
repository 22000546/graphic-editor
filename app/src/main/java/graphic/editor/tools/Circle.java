package graphic.editor.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Circle {
	
	Point start;
	Point end;
	
	public static void drawCircle(Graphics2D g, Point start, Point end, Color color, int stroke, boolean fill) {
		
		g.setColor(color);
		g.setStroke(new BasicStroke(stroke));
		
		if(fill == true) {
			if((start.x < end.x)&&(start.y < end.y))
				 g.fillOval(start.x, start.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
			 else if((start.x < end.x)&&(start.y > end.y))
				 g.fillOval(start.x, end.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
			 else if((start.x > end.x)&&(start.y < end.y))
				 g.fillOval(end.x, start.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
			 else if((start.x > end.x)&&(start.y > end.y))
				 g.fillOval(end.x, end.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
		} else {
			if((start.x < end.x)&&(start.y < end.y))
				 g.drawOval(start.x, start.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
			 else if((start.x < end.x)&&(start.y > end.y))
				 g.drawOval(start.x, end.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
			 else if((start.x > end.x)&&(start.y < end.y))
				 g.drawOval(end.x, start.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
			 else if((start.x > end.x)&&(start.y > end.y))
				 g.drawOval(end.x, end.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
		}
		
	}
	

}
