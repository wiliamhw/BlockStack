package com.application.Swing;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tetris extends JFrame {

	private JLabel statusBar;

	public Tetris() {
		statusBar = new JLabel();
		add(statusBar, BorderLayout.SOUTH);
		Board board = new Board(this);
		add(board);
		board.start();
		setSize(400, 800);
		setTitle("My Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public JLabel getStatusBar() {
		return statusBar;
	}

	public static void main(String[] args) {
		Tetris myTetris = new Tetris();
		myTetris.setLocationRelativeTo(null);
		myTetris.setVisible(true);
		
		// music
		String filepath = "Tetris99.wav";
		
		Music musicObject = new Music();
		musicObject.playMusic(filepath);
	}

}
