package com.blockstack.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.blockstack.utils.Shape;
import com.blockstack.utils.Shape.Tetrominoes;

public class Ghost {
	private final int squareWidth;
	private final int squareHeight;
	private final int BOARD_WIDTH;
	private final int BOARD_HEIGHT;
	private final int boardLeft;
	private final int boardTop;
	private Tetrominoes[] board;
	private int x;
	private int y;
	private Shape piece;
	
	public Ghost(int squareWidth, int squareHeight, int BOARD_WIDTH, int BOARD_HEIGHT, int boardLeft, int boardTop) {
		this.squareWidth = squareWidth;
		this.squareHeight = squareHeight;
		this.BOARD_WIDTH = BOARD_WIDTH;
		this.BOARD_HEIGHT = BOARD_HEIGHT;
		this.boardLeft = boardLeft;
		this.boardTop = boardTop;
	}
	
	public void make(Graphics g, Tetrominoes[] board, Shape piece, int curX, int curY) {
		this.board = board;
		this.piece = piece;
		this.x = curX;
		this.y = curY;
		moddropDown();
		
		for (int i = 0; i < 4; ++i) {
			int _x = this.x + this.piece.getX(i);
			int _y = this.y - this.piece.getY(i);
			drawShadow(g, boardLeft + _x * squareWidth, boardTop + (BOARD_HEIGHT - _y - 1) * squareHeight);
		}
	}
	
	private void drawShadow(Graphics g, int x, int y) {
		Color color = this.piece.getShape().color;
		Graphics2D g2 = (Graphics2D) g;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(3));
		g2.setColor(color);
		g2.drawLine(x, y + squareHeight - 1, x, y);
		g2.drawLine(x, y, x + squareWidth - 1, y);
		g2.drawLine(x + 1, y + squareHeight - 1, x + squareWidth - 1, y + squareHeight - 1);
		g2.drawLine(x + squareWidth - 1, y + squareHeight - 1, x + squareWidth - 1, y + 1);
		g2.setStroke(oldStroke);
	}
	
	private void moddropDown() {
		int newY = this.y;
		while (newY > 0) {
			if (!modtryMove(x, newY - 1))
				break;

			--newY;
		}
	}
	
	private boolean modtryMove(int newX, int newY) {
		for (int i = 0; i < 4; ++i) {
			int _x = newX + this.piece.getX(i);
			int _y = newY - this.piece.getY(i);

			if (_y < 0 || _y >= BOARD_HEIGHT) {
				return false;
			}

			if (shapeAt(_x, _y) != Tetrominoes.NoShape)
				return false;
		}
		this.x = newX;
		this.y = newY;
		return true;
	}
	
	private Tetrominoes shapeAt(int _x, int _y) {
		return board[_y * BOARD_WIDTH + _x];
	}
}
