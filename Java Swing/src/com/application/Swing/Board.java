package com.application.Swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.application.Swing.Shape.Tetrominoes;

public class Board extends JPanel implements ActionListener {

	private static final int BOARD_WIDTH = 10;
	private static final int BOARD_HEIGHT = 20;
	private final Timer timer;
	private boolean isFallingFinished = false;
	private boolean isStarted = false;
	private boolean isPaused = false;
	private int curX = 0;
	private int curY = 0;
	private Shape curPiece;
	private final ImageIcon icon = new ImageIcon("src/images/null.png");
	private final Pause pauseDialog;
	private final Music musicObject;
	private final Tetrominoes[] board;
	private int score;
	private int totalLines;

	public Board(JFrame frame) {
		// pause menu
		pauseDialog = new Pause(frame, this);
		
		// music
		String filepath = "src/music/Tetris99.wav";
		musicObject = new Music(filepath);
		musicObject.playMusic();
		
		// board
		curPiece = new Shape();
		timer = new Timer(400, this); // timer for lines down
		board = new Tetrominoes[BOARD_WIDTH * BOARD_HEIGHT];
		
		addKeyListener(new MyTetrisAdapter());
		start();
	}
	
	public void start() {
		if (isPaused)
			return;
				
		isStarted = true;
		isFallingFinished = false;
		score = 0;
		totalLines = 0;
		clearBoard();
		newPiece();
		timer.start();
	}
	
	public void pause() {
		if (!isStarted)
			return;

		isPaused = !isPaused;

		if (isPaused) {
			timer.stop();
			musicObject.pauseMusic();
			pauseDialog.showDialog();
		} else {
			timer.start();
			musicObject.playMusic();
			pauseDialog.hideDialog();
		}

		repaint();
	}
	
	private void clearBoard() {
		for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {
			board[i] = Tetrominoes.NoShape;
		}
	}

	private void newPiece() {
		curPiece.setRandomShape();
		curX = BOARD_WIDTH / 2;
		curY = BOARD_HEIGHT + curPiece.minY();

		if (!tryMove(curPiece, curX, curY - 1)) {
			curPiece.setShape(Tetrominoes.NoShape);
			timer.stop();
			isStarted = false;
			this.stopMusic();
			
			// game over
			int input = JOptionPane.showConfirmDialog(null, "Your Score : " + score, "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
			if(input == 0) {
				gotoScoreboard();
			}
		}
	}
	
	public void gotoScoreboard() {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this.getParent());
		
		ScoreBoard scoreBoard = new ScoreBoard(getSize().width, getSize().height);
		frame.setContentPane(scoreBoard);
		frame.setFocusable(true);
		scoreBoard.setScore(score);
		frame.revalidate();
		frame.getContentPane().requestFocus();
		frame.getContentPane().setFocusable(true);
	}
	
	private boolean tryMove(Shape newPiece, int newX, int newY) {
		for (int i = 0; i < 4; ++i) {
			int x = newX + newPiece.getX(i);
			int y = newY - newPiece.getY(i);

			if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT)
				return false;

			if (shapeAt(x, y) != Tetrominoes.NoShape)
				return false;
		}
		curPiece = newPiece;
		curX = newX;
		curY = newY;
		repaint();

		return true;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		try {
			g.drawImage(Main.background, 0, 0, null);
		} catch (Exception e) {
			System.out.println("Can't draw background.");
		}
		Dimension size = getSize();
		g.setColor(Color.BLACK);
		int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();
		int boardLeft = (Main.WIDTH - BOARD_WIDTH * squareWidth())/2;
		g.fillRect(boardLeft, boardTop, squareWidth() * BOARD_WIDTH, (int) size.getHeight());
		
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; ++j) {
				
				g.setColor(new Color(1f, 1f, 1f, .25f));
				g.drawRect(boardLeft + j*squareWidth(), boardTop + i*squareHeight(), squareWidth(), squareHeight());
				
				Tetrominoes shape = shapeAt(j, BOARD_HEIGHT - i - 1);

				if (shape != Tetrominoes.NoShape) {
					drawSquare(g, boardLeft + j*squareWidth(), boardTop + i*squareHeight(), shape);
				}
			}
		}
		if (curPiece.getShape() != Tetrominoes.NoShape) {
			for (int i = 0; i < 4; ++i) {
				int x = curX + curPiece.getX(i);
				int y = curY - curPiece.getY(i);
				drawSquare(g, boardLeft + x*squareWidth(), boardTop + (BOARD_HEIGHT - y - 1)*squareHeight(),
						curPiece.getShape());
			}
		}
		makeScoreBox(g);
	}
	
	private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
		Color color = shape.color;
		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, y, x + squareWidth() - 1, y);
		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
	}
	
	private void makeScoreBox(Graphics g) {
		int width = 137;
		int height = 211;
		int x = 42;
		int y = 310;
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x - 10, y - 23, width + 20, height + 47);
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
	
	private Tetrominoes shapeAt(int x, int y) {
		return board[y * BOARD_WIDTH + x];
	}
	
	private int squareWidth() {
		return (int)  getSize().getHeight() / BOARD_HEIGHT;
	}

	private int squareHeight() {
		return (int) getSize().getHeight() / BOARD_HEIGHT;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();
		} else {
			oneLineDown();
		}
	}

	private void oneLineDown() {
		if (!tryMove(curPiece, curX, curY - 1))
			pieceDropped();
	}
	
	private void pieceDropped() {
		for (int i = 0; i < 4; i++) {
			int x = curX + curPiece.getX(i);
			int y = curY - curPiece.getY(i);
			board[y * BOARD_WIDTH + x] = curPiece.getShape();
		}

		removeFullLines();

		if (!isFallingFinished) {
			newPiece();
		}
	}
	
	private void removeFullLines() {
		int numFullLines = 0;

		for (int i = BOARD_HEIGHT - 1; i >= 0; --i) {
			boolean lineIsFull = true;

			for (int j = 0; j < BOARD_WIDTH; ++j) {
				if (shapeAt(j, i) == Tetrominoes.NoShape) {
					lineIsFull = false;
					break;
				}
			}
			if (lineIsFull) {
				++numFullLines;

				for (int k = i; k < BOARD_HEIGHT - 1; ++k) {
					for (int j = 0; j < BOARD_WIDTH; ++j) {
						board[k * BOARD_WIDTH + j] = shapeAt(j, k + 1);
					}
				}
			}
			if (numFullLines > 0) {
				score += 100 + 200*(numFullLines - 1);
				isFallingFinished = true;
				repaint();
			}
		}
		totalLines += numFullLines;
	}

	private void dropDown() {
		int newY = curY;
		int _curY = curY;

		while (newY > 0) {
			if (!tryMove(curPiece, curX, newY - 1))
				break;

			--newY;
		}
		score += 2 * (_curY - newY);
		pieceDropped();
	}

	class MyTetrisAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent ke) {
			if (!isStarted || curPiece.getShape() == Tetrominoes.NoShape)
				return;

			int keyCode = ke.getKeyCode();

			switch (keyCode) {
			case KeyEvent.VK_ESCAPE:
				pause();
				break;
			case KeyEvent.VK_LEFT:
				tryMove(curPiece, curX - 1, curY);
				break;
			case KeyEvent.VK_RIGHT:
				tryMove(curPiece, curX + 1, curY);
				break;
			case KeyEvent.VK_UP:
				tryMove(curPiece.rotateLeft(), curX, curY);
				break;
			case 'z':
			case 'Z':
				tryMove(curPiece.rotateRight(), curX, curY);
				break;
			case KeyEvent.VK_SPACE:
				dropDown();
				break;
			case KeyEvent.VK_DOWN:
				++score;
				oneLineDown();
				break;
			}

		}
	}
	
	public void stopMusic() {
		musicObject.stopMusic();
	}
}