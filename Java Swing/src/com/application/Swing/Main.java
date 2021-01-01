package com.application.Swing;

import javax.swing.JFrame;

public class Main {
	
	private JFrame frame;
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Tetris");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(new TetrisPanel()); //450, 600
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
}