package com.blockstack.views;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

<<<<<<< HEAD:Blockstack/src/com/blockstack/views/Main.java
import com.blockstack.utils.Audio.Sfx;
import com.blockstack.utils.Asset;
import com.blockstack.views.menus.MainMenu;
=======
import com.application.Swing.Audio.Sfx;
>>>>>>> 438f0d7a5a7b86bd21c93b5d1de218d2db6b37d7:Java Swing/src/com/application/Swing/Main.java

import java.io.*;

public class Main {

	public static BufferedImage background;
	public static final int WIDTH = 750;
	public static final int HEIGHT = 600;
	
	public static void main(String[] args) {
		// ingame background
		try {
			background = ImageIO.read(Asset.getFile("images", "ingame.jpg"));
		} catch(IOException e) {
			System.out.println(e);
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Tetris");
				initScore();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						int choose = JOptionPane.showConfirmDialog(frame, "Do you really want to exit the application?",
								"Confirm Close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
						if(choose == JOptionPane.YES_OPTION) {
							e.getWindow().dispose();
							
							// check if board class is already made
							try {
							   Board panel = (Board) frame.getContentPane();
							   Sfx.ingame.audio.stopAudio();
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
		File scoreFile = Asset.getFile("score", "ScoreBoard.dat");
		Writer writeFile = null;
		try {
			if(scoreFile.createNewFile()) {
				writeFile = new FileWriter(scoreFile);
				String defaultScore = new String(
						  "Kosimp:20000\n"
						+ "Wiliamp:19000\n"
						+ "Samsul:6000\n"
						+ "Udin:5000\n"
						+ "John:4000\n"
						+ "Jupiter:3000\n"
						+ "Timothy:2000\n"
						+ "Oktovian:1000\n"
						+ "Noob:0\n");
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
