package com.application;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.io.*;
import java.io.IOException;

public class Main {

	public static final int WIDTH = 450;
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
							System.out.println("close");
						} else {
							frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						}
					}
				});
				frame.setResizable(false);
				frame.setContentPane(new MainMenu(WIDTH, HEIGHT));
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
	private static void initScore() {
		File scoreFile = new File("ScoreBoard.dat");
		Writer writeFile = null;
		try {
			if(scoreFile.createNewFile()) {
				writeFile = new FileWriter(scoreFile);
				String defaultScore = new String("Jimmy:10000\n"
						+ "Brook:9000\n"
						+ "Mickey:8000\n"
						+ "Samsul:7000\n"
						+ "Udin:6000\n"
						+ "John:5000\n"
						+ "Jupiter:4000\n"
						+ "Timothy:3000\n"
						+ "Oktovian:2000\n"
						+ "Wiliamp:1000");
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
