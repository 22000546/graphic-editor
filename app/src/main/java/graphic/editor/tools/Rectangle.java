package graphic.editor.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import graphic.editor.system.GraphicData;

public class Rectangle {
	
	Point start;
	Point end;
	
	public static void drawRectangle(Graphics2D g, Point start, Point end, Color color, int stroke, Boolean fill) {
		
		g.setColor(color);
		g.setStroke(new BasicStroke(stroke));
		
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
