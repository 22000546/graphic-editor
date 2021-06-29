package graphic.editor.system;

import java.awt.Color;
import java.awt.Point;

import graphic.editor.tools.*;
import graphic.editor.tools.Line;
import java.util.ArrayList;

public class GraphicData {
	
	private Point start;
	private Point end;
	private Point current;
	
	private int MODE;
	int PEN = 1;
	int RECT = 2;
	int OVAL = 3;
	int LINE = 4;
	int POLYLINE = 5;
	int TEXT = 6;
	int PATTERNS = 7;
	int SPRAY = 8;
	int ERASER = 9;
	
	private Color color;
	private int stroke;
	private boolean fill;
	

	public GraphicData(int MODE, Point start, Point end, Color color, int stroke, boolean fill) {
		this.MODE = MODE;
		this.start = start;
		this.end = end;
		this.color = color;
		this.stroke = stroke;
		this.fill = fill;
	}
	
	public void setStart(Point start) {
		this.start = start;
	}
	
	public Point getStart() {
		return start;
	}
	
	public Point getEnd() {
		return end;
	}
	
	public Point getCurrent() {
		return current;
	}
	
	public int getMode() {
		return MODE;
	}

	public Color getColor() {
		return color;
	}
	
	public int getStroke() {
		return stroke;
	}
	
	public Boolean getFill() {
		return fill;
	}
	
}
