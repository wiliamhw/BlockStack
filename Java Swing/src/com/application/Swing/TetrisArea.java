package com.application.Swing;

import java.awt.Color;
import java.awt.Graphics;

public class TetrisArea {
	private int XMIN;
	private int XMAX;
	private int YMIN;
	private int YMAX;
	private Color colorFilled;
	private Color colorBorder;
	private Color colorBox;
	
	public TetrisArea(int x, int y, int width, int height, 
			Color colorFilled, Color colorBorder, Color colorBox) {
		this.XMIN = x;
		this.YMIN = y;
		this.XMAX = width;
		this.YMAX = height;
		this.colorFilled = colorFilled;
		this.colorBorder = colorBorder;
		this.colorBox = colorBox;
	}
	
	public void set(int x, int y, int width, int height) {
		XMIN = x;
		YMIN = y;
		XMAX = x + width - 1;
		YMAX = y + height - 1;
	}
	
	public void draw(Graphics g) {
		g.setColor(colorFilled);
		g.fillRect(XMIN, YMIN, XMAX - 150, YMAX - YMIN - 1);
		g.drawRect(XMIN, YMIN, XMAX - 150, YMAX - YMIN - 1);
		
		g.setColor(colorBox);
		g.fillRect(XMAX - 150, YMIN, 150, YMAX - YMIN - 1);
		g.drawRect(XMAX - 150, YMIN, 150, YMAX - YMIN - 1);
	}
}
