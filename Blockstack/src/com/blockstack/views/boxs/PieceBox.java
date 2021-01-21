package com.blockstack.views.boxs;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.blockstack.utils.Shape;
import com.blockstack.utils.Shape.Tetrominoes;

public class PieceBox extends Box {
	private final int squareSide;
	private final int titleBarHeight;
	private final String title;
	private Shape piece;
	
	public PieceBox(int baseX, int baseY, int border, int squareSide, String title) {
		super(baseX, baseY, 4 * squareSide, 11*squareSide/2, border, border);
		this.squareSide = squareSide;
		this.title = title;
		this.titleBarHeight = 3*squareSide/2;
	}
	
	public void make(Graphics g, Shape piece) {
		super.make(g); // make box
		
		// set variable
		this.piece = piece;
		curX = getBaseX();
		curY = getBaseY();
		
		// make black box for piece
		g.setColor(Color.BLACK);
		g.fillRect(curX, curY + titleBarHeight, getWidth(), getWidth());
		
		// set title font
		g.setColor(Color.WHITE);
		g.setFont(new Font("Tahoma", Font.BOLD, 18));
		FontMetrics font = g.getFontMetrics();
		
		// draw title in middle
		curX += (getWidth() - font.stringWidth(title))/2;
		curY += (titleBarHeight + font.getHeight() - 5)/2;
		g.drawString(title, curX, curY);
		
		if (this.piece.getShape() != Tetrominoes.NoShape) {
			centerPadder(); // add curX and curY with some value to center text
			
			for (int i = 0; i < 4; ++i) {
				int x = squareSide * this.piece.getX(i);
				int y = squareSide * this.piece.getY(i);
				drawSquare(g, curX + x, curY + y, this.piece.getShape());
			}
		}
	}
	
	private void centerPadder() {
		curX = getBaseX();
		curY = getBaseY() + titleBarHeight;
		if (this.piece.getShape() == Tetrominoes.LineShape) {
			// center == middle
			curX += getWidth()/4;
			curY += (getbBoxHeight() - squareSide)/2;
		} 
		else if (this.piece.getShape() == Tetrominoes.SquareShape) {
			// center == upper-left
			curX += (getWidth() - 2 * squareSide)/2;
			curY += (getbBoxHeight() - 2 * squareSide)/2;
		}
		else {
			// center == lower-middle
			curX += (getWidth() - squareSide)/2;
			curY += getbBoxHeight()/2;
		}
	}
	
	
	protected int getbBoxHeight() {
		return super.getHeight() - this.titleBarHeight;
	}
	
	private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
		Color color = shape.color;
		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareSide - 2, squareSide - 2);
		g.setColor(color.brighter());
		g.drawLine(x, y + squareSide - 1, x, y);
		g.drawLine(x, y, x + squareSide - 1, y);
		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareSide - 1, x + squareSide - 1, y + squareSide - 1);
		g.drawLine(x + squareSide - 1, y + squareSide - 1, x + squareSide - 1, y + 1);
	}
}
