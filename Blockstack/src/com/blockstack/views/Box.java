package com.application.Swing;

import java.awt.Color;
import java.awt.Graphics;

public class Box {
	private final int baseX;
	private final int baseY;
	private final int width;
	private final int height;
	private final int borderX;
	private final int borderY;
	protected int curX; // current drawer x-coordinate
	protected int curY;
	
	public Box(int baseX, int baseY, int width, int height, int borderX, int borderY) {
		this.baseX = baseX;
		this.baseY = baseY;
		this.width = width;
		this.height = height;
		this.borderX = borderX;
		this.borderY = borderY;
	}
	
	// make box
	public void make(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(baseX - borderX, baseY - borderY, width + 2*borderX, height + 2*borderY);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(baseX, baseY, width, height);
	}

	protected int getBaseX() {
		return baseX;
	}

	protected int getBaseY() {
		return baseY;
	}

	protected int getWidth() {
		return width;
	}

	protected int getHeight() {
		return height;
	}
}
