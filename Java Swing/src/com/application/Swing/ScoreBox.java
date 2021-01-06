package com.application.Swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class ScoreBox {
	private final int widthBase = 137;
	private final int heightBase = 211;
	private final int xBase = 42;
	private final int yBase = 310;
	private final int padX = 10;
	private final int padY = 23;
	
	public ScoreBox() {}
	
	public void make(Graphics g, int score, int totalLines) {
		int x = xBase;
		int y = yBase;
		int width = widthBase;
		int height = heightBase;
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x - padX, y - padY, width + 2*padX, height + 2*padY);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, width, height);
		
		int newWidth = 95;
		x += (width - newWidth)/2;
		y = 441;
		width = newWidth;
		height = 22;
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y - 67, width, height);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		int tmpX = x;
		FontMetrics font = g.getFontMetrics();
		String str = "Score";
		x = tmpX + (width - font.stringWidth(str))/2;
		y += (height - font.getHeight())/2 - 67 - 5;
		
		g.drawString("Score", x, y);
		g.drawString("Lines", x, y + 67);
		
		g.setFont(new Font("Tahoma", Font.PLAIN, 15));
		font = g.getFontMetrics();
		
		str = Integer.toString(score);
		x = tmpX + (width - font.stringWidth(str))/2;
		y += height;
		g.drawString(str, x, y);
		
		str = Integer.toString(totalLines);
		x = tmpX + (width - font.stringWidth(str))/2;
		g.drawString(str, x, y + 67);
	}
}
