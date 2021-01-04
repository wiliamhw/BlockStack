package com.application.Swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.io.*;
import java.io.IOException;

public class Main {

	public static final int WIDTH = 550;

	public static final int HEIGHT = 600;
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Tetris");
				initScore();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						int choose = JOptionPane.showConfirmDialog(null, "Do you really want to exit the application?",
								"Confirm Close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
						if(choose == JOptionPane.YES_OPTION) {
							e.getWindow().dispose();
							
							// check if board class is already made
							try {
							   Board panel = (Board) frame.getContentPane();
							   panel.stopMusic();
							} 
							catch (Exception ex) {} // do nothing
						} else {
							frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						}
					}
				});
				frame.setResizable(false);
				frame.setContentPane(new MainMenu(WIDTH, HEIGHT));
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
	
	private static void initScore() {
		File scoreFile = new File("src/score/ScoreBoard.dat");
		Writer writeFile = null;
		try {
			if(scoreFile.createNewFile()) {
				writeFile = new FileWriter(scoreFile);
				String defaultScore = new String("Kosimp:9000\n"
						+ "Wiliamp:8000\n"
						+ "Mickey:7000\n"
						+ "Samsul:6000\n"
						+ "Udin:5000\n"
						+ "John:4000\n"
						+ "Jupiter:3000\n"
						+ "Timothy:2000\n"
						+ "Oktovian:1000\n"
						+ "Noob:0");
				writeFile.write(defaultScore);
				
			}
			
		} catch (Exception e) {}
		
		finally {
			try {
				if(writeFile != null)
					writeFile.close();
			} catch (IOException e) {}
		}
		
		
	}
}
