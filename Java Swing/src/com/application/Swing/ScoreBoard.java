package com.application.Swing;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class ScoreBoard extends JPanel{

	BufferedReader scoreReader;
	List<String> name;
	List<String> score;
	private int areaWidth;
	private int areaHeight;
	private int wButton;
	private int hButton;
	private JButton okay;
	
	public ScoreBoard(int width, int height) {
		System.out.println("A");
		this.areaWidth = width;
		this.areaHeight = height;
		wButton = 100;
		hButton = 50;
		
		this.setPreferredSize(new Dimension(areaWidth, areaHeight));
		setLayout(null);
		
		okay = new JButton("Okay");
		okay.setBounds(((areaWidth/2)-(wButton/2)), 540, wButton, hButton);
		this.add(okay);
		ButtonHandler handler = new ButtonHandler();
		okay.addActionListener(handler);
		
		this.setVisible(true);
		
		readFile();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		g.drawString("ScoreBoard", ((areaWidth/2)-87), 45);

		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		int yAdder = 45;
		int y = 105;
		for(int i=0; i<name.size(); i++) {
			g.drawString(name.get(i), 20, y);
			g.drawString(score.get(i), (areaHeight/2)+20, y);
			y += yAdder;
		}
	}
	
	
	private void readFile() {
		FileReader scoreFileReader = null;
		name = new ArrayList<String>();
		score = new ArrayList<String>();
		
		try {
			scoreFileReader = new FileReader("ScoreBoard.dat");
			scoreReader = new BufferedReader(scoreFileReader);
			String line;
			while((line = scoreReader.readLine()) != null) {
				String[] tokens = line.split(":");
				name.add(tokens[0]);
				score.add(tokens[1]);
			}
		} catch(Exception e) { }
		
		finally {
			try {
				if(scoreFileReader != null)
					scoreFileReader.close();
			} catch (IOException e) {}
		}
	}
	
	public class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Okay")) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(okay.getParent());
				frame.setContentPane(new MainMenu(areaWidth, areaHeight));
				frame.invalidate();
				frame.validate();
			}
		}
	}
}
