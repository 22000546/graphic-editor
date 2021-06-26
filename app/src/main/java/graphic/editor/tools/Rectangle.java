package graphic.editor.tools;

import java.awt.Graphics;
import java.awt.Point;

public class Rectangle {
	
	Point start;
	Point end;
	
	public static void drawRectangle(Graphics g, Point start, Point end, Boolean fill) {
		
		if(fill == true) {
			if((start.x < end.x)&&(start.y < end.y))
				 g.fillRect(start.x, start.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
			 else if((start.x < end.x)&&(start.y > end.y))
				 g.fillRect(start.x, end.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
			 else if((start.x > end.x)&&(start.y < end.y))
				 g.fillRect(end.x, start.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
			 else if((start.x > end.x)&&(start.y > end.y))
				 g.fillRect(end.x, end.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
		} else {
			if((start.x < end.x)&&(start.y < end.y))
				 g.drawRect(start.x, start.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
			 else if((start.x < end.x)&&(start.y > end.y))
				 g.drawRect(start.x, end.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
			 else if((start.x > end.x)&&(start.y < end.y))
				 g.drawRect(end.x, start.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
			 else if((start.x > end.x)&&(start.y > end.y))
				 g.drawRect(end.x, end.y, Math.abs(end.x - start.x), Math.abs(end.y - start.y));
		}
	}
	
	
}
