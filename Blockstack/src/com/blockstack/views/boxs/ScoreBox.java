package com.blockstack.views.boxs;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class ScoreBox extends Box {
	private final int columnWidth; // textfield width
	private final int columnHeight;
	private final int paddingY; // vertical padding for each textfield column
	
	public ScoreBox(int baseX, int baseY, int width, int height, int borderX, int borderY,
					int columnWidth, int columnHeight, int paddingY) {
		super(baseX, baseY, width, height, borderX, borderY);
		this.columnWidth = columnWidth;
		this.columnHeight = columnHeight;
		this.paddingY = paddingY;
	}
	
	// make components inside the box
	public void make(Graphics g, int score, int level, int totalLines) {
		super.make(g); // make box
		
		// set variable 
		curX = getBaseX() + (getWidth() - columnWidth)/2;
		curY = getBaseY() + paddingY;
		int _baseX = curX;
		int _baseY = curY;
		
		// draw score, level, and lines column
		g.setColor(Color.BLACK);
		for (int i = 0; i < 3; i++) {
			g.fillRect(curX, curY, columnWidth, columnHeight);
			curY += paddingY;
		}
		
		// set textfield label font
		g.setColor(Color.WHITE);
		g.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		// variable for aligning text to middle
		FontMetrics font = g.getFontMetrics();
		
		// draw textfield label in middle
		curY = _baseY - (columnHeight - font.getHeight())/2 - 5;
		String label[] = {"Score", "Level", "Lines"};
		for (String s : label) {
			curX = _baseX + (columnWidth - font.stringWidth(s))/2;
			g.drawString(s, curX, curY);
			curY += paddingY;
		}
		
		// set textfield value font
		g.setFont(new Font("Tahoma", Font.PLAIN, 15));
		font = g.getFontMetrics();
		
		// align textfiled value to middle
		curY = _baseY + (columnHeight + font.getHeight())/2 - 3;
		String value[] = {Integer.toString(score), 
							Integer.toString(level), 
							Integer.toString(totalLines)};
		for (String s : value) {
			curX = _baseX + (columnWidth - font.stringWidth(s))/2;
			g.drawString(s, curX, curY);
			curY += paddingY;
		}
	}
}
