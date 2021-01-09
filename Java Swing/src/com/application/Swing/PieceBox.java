package com.application.Swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.application.Swing.Shape.Tetrominoes;

public class PieceBox {
	private final int squareWidth;
	private final int squareHeight;
	private int width;
	private int height;
	private int x;
	private int y;
	private final int padder;
	private final int margin;
	private Shape piece;
	
	public PieceBox(int squareWidth, int squareHeight) {
		this.squareWidth = squareWidth;
		this.squareHeight = squareHeight;
		this.padder = 5;
		this.margin = 50;
	}
	
	public void make(Graphics g, Shape piece, int leftBorder, String title) {
		this.piece = piece;
		this.width = 4 * squareWidth;
		this.height = this.width;
		this.x = leftBorder + margin;
		this.y = margin;
		int titleBarHeight = 3 * squareHeight / 2;
		int temp[] = new int[2];
		
		// box
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, width, titleBarHeight);
		g.setColor(Color.BLACK);
		g.fillRect(x, y + titleBarHeight, width, height);
		g.setColor(Color.WHITE);

		// border
		Graphics2D g2 = (Graphics2D) g;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(10));
		g2.setColor(Color.LIGHT_GRAY);
		g2.drawRect(x - padder, y - padder, width + 2 * padder, height + 2 * padder + titleBarHeight);
		g2.setStroke(oldStroke);
		
		// title
		g.setColor(Color.WHITE);
		g.setFont(new Font("Tahoma", Font.BOLD, 18));
		FontMetrics font = g.getFontMetrics();
		temp[0] = x + (width - font.stringWidth(title))/2;
		temp[1] = y + (titleBarHeight + font.getHeight() - padder)/2;
		g.drawString(title, temp[0], temp[1]);
		
		// piece
		temp = centerPadding(titleBarHeight);

		if (this.piece.getShape() != Tetrominoes.NoShape) {
			for (int i = 0; i < 4; ++i) {
				int q_x = squareWidth * this.piece.getX(i);
				int q_y = squareHeight * this.piece.getY(i);
				drawSquare(g, temp[0] + q_x, temp[1] + q_y, this.piece.getShape());
			}
		}
	}

	private int[] centerPadding(int titleBarHeight) {
		int temp[] = new int[2];
		if (this.piece.getShape() == Tetrominoes.NoShape) return temp;
		
		temp[0] = x;
		temp[1] = y + titleBarHeight;
		if (this.piece.getShape() == Tetrominoes.LineShape) {
			// center == middle
			temp[0] += this.width/4;
			temp[1] += (this.height - squareHeight)/2;
		} 
		else if (this.piece.getShape() == Tetrominoes.SquareShape) {
			// center == upper-left
			temp[0] += (this.width - 2 * squareWidth)/2;
			temp[1] += (this.height - 2 * squareHeight)/2;
		}
		else {
			// center == lower-middle
			temp[0] += (this.width - squareWidth)/2;
			temp[1] += this.height/2;
		}
		return temp;
	}

	private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
		Color color = shape.color;
		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth - 2, squareHeight - 2);
		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight - 1, x, y);
		g.drawLine(x, y, x + squareWidth - 1, y);
		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight - 1, x + squareWidth - 1, y + squareHeight - 1);
		g.drawLine(x + squareWidth - 1, y + squareHeight - 1, x + squareWidth - 1, y + 1);
	}
}
