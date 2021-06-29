package graphic.editor.tools;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;

public class Line {

	Point start;
	Point end;
	Color color;
	int stroke;
	
	public Line(Point start, Point end, Color color, int stroke) {
		this.start = start;
		this.end = end;
		this.color = color;
		this.stroke = stroke;
	}
	
	public Point getStart() {
		return start;
	}
	
	public Point getEnd() {
		return end;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getStroke() {
		return stroke;
	}
	
	public static void drawLine(Graphics2D g, Point start, Point end, Color color, int stroke) {
		
		g.setColor(color);
		g.setStroke(new BasicStroke(stroke));
		g.drawLine(start.x, start.y, end.x, end.y);
		
	}
	
	
	public static void drawPolyline(Graphics g, Point start, Point end) {
		
		g.drawLine(start.x, start.y, end.x, end.y);
		
	}
	
	
}
