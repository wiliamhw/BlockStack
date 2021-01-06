package com.application.Swing;

import java.awt.BasicStroke;
import java.awt.Color;
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
	private Graphics g;
	private Shape piece;
	
	public PieceBox(int squareWidth, int squareHeight) {
		this.squareWidth = squareWidth;
		this.squareHeight = squareHeight;
		this.padder = 5;
		this.margin = 50;
	}
	
	public void make(Graphics g, Shape piece, int leftBorder) {
		this.piece = piece;
		this.width = 4 * squareWidth;
		this.height = this.width;
		this.x = leftBorder + margin;
		this.y = margin;
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, width, height);

		// border
		Graphics2D g2 = (Graphics2D) g;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(10));
		g2.setColor(Color.LIGHT_GRAY);
		g2.drawRect(x - padder, y - padder, width + 2 * padder, height + 2 * padder);
		g2.setStroke(oldStroke);
		
		int temp[] = new int[2];
		if (piece.getShape() != Tetrominoes.SquareShape && piece.getShape() != Tetrominoes.LineShape) {
			if (!canMove(piece, width, height, x - squareWidth, y)) {
				temp[0] = squareWidth / 2;
			}
			if (!canMove(piece, width, height, x, y - squareHeight)) {
				temp[1] = squareHeight / 2;
			}
		}
		x += temp[0] + width / 4;
		y += temp[1] + height / 4;

		if (piece.getShape() != Tetrominoes.NoShape) {
			for (int i = 0; i < 4; ++i) {
				int q_x = squareWidth * piece.getX(i);
				int q_y = squareHeight * piece.getY(i);
				drawSquare(g, x + q_x, y + q_y, piece.getShape());
			}
		}
	}
	
	private boolean canMove(Shape currpiece, int width, int height, int newX, int newY) {
		for (int i = 0; i < 4; ++i) {
			int x = newX + currpiece.getX(i);
			int y = newY - currpiece.getY(i);

			if (x < 0 || x >= width || y < 0 || y >= height)
				return false;
		}
		return true;
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
