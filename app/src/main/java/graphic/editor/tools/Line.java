package graphic.editor.tools;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;

public class Line {

	public static void drawLine(Graphics g, Point start, Point end) {
		
		g.drawLine(start.x, start.y, end.x, end.y);
		
	}
	
	
	public static void drawPolyline(Graphics g, Point start, Point end) {
		
		g.drawLine(start.x, start.y, end.x, end.y);
		
	}
	
}
