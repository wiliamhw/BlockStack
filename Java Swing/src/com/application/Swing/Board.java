package com.application.Swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.application.Swing.Audio.Sfx;
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
	private Shape currPiece;
	private Shape nextPiece;
	private Shape holdPiece;
	private final ImageIcon icon = new ImageIcon("src/images/null.png");
	protected JButton pauseButton;
	private final ScoreBox scorebox = new ScoreBox();
	private final Tetrominoes[] board = new Tetrominoes[BOARD_WIDTH * BOARD_HEIGHT];
	private final PauseMenu pauseDialog;
	private PieceBox nextPieceBox = null;
	private PieceBox holdPieceBox = null;
	private Ghost ghost = null;
	private boolean isHold; // flag for checking if a block have been hold in this turn
	private boolean enableGhost;
	private int score;
	private int totalLines;
	private ImageIcon iconPause = null;
		

	public Board(JFrame frame) {
		setLayout(null);

		// pause Button
		try {
			iconPause = new ImageIcon("src/images/PauseButton.png");
		} catch(Exception e) {
			System.out.println(e);
		}
		setPauseButton();

		// pause menu
		pauseDialog = new PauseMenu(frame, this, pauseButton);

		// board
		currPiece = new Shape();
		nextPiece = new Shape();
		holdPiece = new Shape();
		timer = new Timer(400, this); // timer for lines down

		addKeyListener(new MyTetrisAdapter());
		start();
	}

	public void start() {
		if (isPaused)
			return;

		// music
		Sfx.ingame.audio.playAudio(true);
		
		isStarted = true;
		isFallingFinished = false;
		isHold = false;
		enableGhost = true;
		score = 0;
		totalLines = 0;
		clearBoard();
		
		nextPiece.setRandomShape();
		holdPiece.setShape(Tetrominoes.NoShape);
		newPiece();
		timer.start();
	}

	public void pause() {
		if (!isStarted)
			return;

		isPaused = !isPaused;

		if (isPaused) {
			timer.stop();
			Sfx.ingame.audio.pauseAudio();
			pauseButton.setVisible(false);
			pauseDialog.showDialog();
		} else {
			timer.start();
			Sfx.ingame.audio.playAudio(true);
			if(iconPause != null) {
				pauseButton.setIcon(iconPause);
				pauseButton.setText("");						
			} else {
				pauseButton.setText("Pause");
			}
			pauseButton.setVisible(true);
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
		if (!isHold) {
			currPiece.setShape(nextPiece.getShape());
			nextPiece.setRandomShape();
		}

		curX = BOARD_WIDTH / 2;
		curY = BOARD_HEIGHT + currPiece.minY();

		if (!tryMove(currPiece, curX, curY - 1)) {
			currPiece.setShape(Tetrominoes.NoShape);
			timer.stop();
			isStarted = false;
			Sfx.ingame.audio.stopAudio();

			// game over
			Sfx.gameover.audio.playAudio(false);
			int input = JOptionPane.showConfirmDialog(this, "Your Score : " + score, "Game Over",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
			try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			if (input == 0) {
				gotoScoreboard();
			}
		}
	}

	private void hold() {
		if (isHold) {
			Sfx.cancel.audio.playbackAudio(true);
			return;
		}
		
		if (holdPiece.getShape() == Tetrominoes.NoShape) {
			holdPiece.setShape(currPiece.getShape());
		} else {
			Tetrominoes temp = holdPiece.getShape();
			holdPiece.setShape(currPiece.getShape());
			currPiece.setShape(temp);
		}
		isHold = true;
		newPiece();
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

			if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {
				Sfx.fix.audio.playbackAudio(false);
				return false;
			}

			if (shapeAt(x, y) != Tetrominoes.NoShape)
				return false;
		}
		currPiece = newPiece;
		curX = newX;
		curY = newY;
		repaint();

		return true;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(0, 0, 0, 30));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		try {
			g.drawImage(Main.background, 0, 0, null);
		} catch (Exception e) {
			System.out.println(e);
		}
		Dimension size = getSize();
		g.setColor(new Color(36, 37, 32));
		int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();
		int boardLeft = (Main.WIDTH - BOARD_WIDTH * squareWidth()) / 2;
		g.fillRect(boardLeft, boardTop, squareWidth() * BOARD_WIDTH, (int) size.getHeight());

		// ghost
		if (ghost == null) ghost = new Ghost(squareWidth(), squareHeight(), 
										BOARD_WIDTH, BOARD_HEIGHT, boardLeft, boardTop);
		if (enableGhost && currPiece.getShape() != Tetrominoes.NoShape)  {
			ghost.make(g, board, currPiece, curX, curY);
		}
		
		// pieces
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; ++j) {

				// Grid
				g.setColor(new Color(1f, 1f, 1f, .25f));
				g.drawRect(boardLeft + j * squareWidth(), boardTop + i * squareHeight(), squareWidth(), squareHeight());

				// rendering placed shape
				Tetrominoes shape = shapeAt(j, BOARD_HEIGHT - i - 1);

				if (shape != Tetrominoes.NoShape) {
					drawSquare(g, boardLeft + j * squareWidth(), boardTop + i * squareHeight(), shape);
				}
			}
		}
		if (currPiece.getShape() != Tetrominoes.NoShape) {
			for (int i = 0; i < 4; ++i) {
				int x = curX + currPiece.getX(i);
				int y = curY - currPiece.getY(i);
				drawSquare(g, boardLeft + x * squareWidth(), boardTop + (BOARD_HEIGHT - y - 1) * squareHeight(),
						currPiece.getShape());
			}
		}
		// nextPieceBox
		if (nextPieceBox == null) nextPieceBox = new PieceBox(squareWidth(), squareHeight());
		nextPieceBox.make(g, nextPiece, boardLeft + BOARD_WIDTH * squareWidth(), "Next");
		
		// holdPieceBox
		if (holdPieceBox == null) holdPieceBox = new PieceBox(squareWidth(), squareHeight());
		holdPieceBox.make(g, holdPiece, 0, "Hold");
		
		scorebox.make(g, score, totalLines);
		pauseButton.repaint();
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

	private Tetrominoes shapeAt(int x, int y) {
		return board[y * BOARD_WIDTH + x];
	}

	private int squareWidth() {
		return (int) getSize().getHeight() / BOARD_HEIGHT;
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
		if (!tryMove(currPiece, curX, curY - 1))
			pieceDropped();
	}

	private void pieceDropped() {
		for (int i = 0; i < 4; i++) {
			int x = curX + currPiece.getX(i);
			int y = curY - currPiece.getY(i);
			board[y * BOARD_WIDTH + x] = currPiece.getShape();
		}

		removeFullLines();

		isHold = false;
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
				score += 100 + 200 * (numFullLines - 1);
				isFallingFinished = true;
				repaint();
			}
		}
		totalLines += numFullLines;
		switch (numFullLines) {
		case 0:
			break;
		case 1:
			Sfx._single.audio.playbackAudio(false);
			break;
		case 2:
			Sfx._double.audio.playbackAudio(false);
			break;
		case 3:
			Sfx._triple.audio.playbackAudio(false);
			break;
		default:
			Sfx._tetris.audio.playbackAudio(false);
			break;
		}
	}

	private void dropDown() {
		int newY = curY;
		int _curY = curY;

		while (newY > 0) {
			if (!tryMove(currPiece, curX, newY - 1))
				break;

			--newY;
		}
		score += 2 * (_curY - newY);
		pieceDropped();
	}

	class MyTetrisAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent ke) {
			if (!isStarted || currPiece.getShape() == Tetrominoes.NoShape)
				return;

			int keyCode = ke.getKeyCode();

			switch (keyCode) {
			case KeyEvent.VK_ESCAPE:
				Sfx.pause.audio.playbackAudio(false);
				pause();
				break;
			case KeyEvent.VK_LEFT:
				Sfx.move.audio.playbackAudio(false);
				tryMove(currPiece, curX - 1, curY);
				break;
			case KeyEvent.VK_RIGHT:
				Sfx.move.audio.playbackAudio(false);
				tryMove(currPiece, curX + 1, curY);
				break;
			case KeyEvent.VK_UP:
				Sfx.rotate.audio.playbackAudio(false);
				tryMove(currPiece.rotateRight(), curX, curY);
				break;
			case 'z':
			case 'Z':
				Sfx.rotate.audio.playbackAudio(false);
				tryMove(currPiece.rotateLeft(), curX, curY);
				break;
			case KeyEvent.VK_SPACE:
				Sfx.harddrop.audio.playbackAudio(false);
				dropDown();
				break;
			case KeyEvent.VK_DOWN:
				Sfx.softdrop.audio.playbackAudio(false);
				++score;
				oneLineDown();
				break;
			case 'c':
			case 'C':
				if (!isHold) Sfx.hold.audio.playbackAudio(false);
				hold();
				break;
			case 'g':
			case 'G':
				Sfx.ok.audio.playbackAudio(false);
				enableGhost = !enableGhost;
				break;
			}
		}
	}

	public void setPauseAction(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				Sfx.cursor.audio.playbackAudio(true);
				button.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
			}

			public void mouseClicked(MouseEvent e) {
				Sfx.ok.audio.playbackAudio(true);
				pause();
			}

			public void mouseExited(MouseEvent e) {
				button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5));
			}
		});
	}
	
	private void setPauseButton() {
		if(iconPause == null) {
			pauseButton = new JButton("Pause");
		}
		else {
			pauseButton = new JButton("", iconPause);
		}
		pauseButton.setHorizontalTextPosition(JButton.CENTER);
		pauseButton.setBounds(640, 550, 110, 50);
		pauseButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5));
		setPauseAction(pauseButton);
		this.add(pauseButton);
	}
}