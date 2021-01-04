package com.application.Swing;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.*;

public class ScoreBoard extends JPanel{

	BufferedReader scoreReader;
	TreeMap<Integer, String> scoreList;
	private int areaWidth;
	private int areaHeight;
	private int wButton;
	private int hButton;
	private JButton okay;
	private JTextField insertName = null;
	private int score = 0; 
	private int smallestScore;
	private String insertToFile;
	private int flagPlay = 0;
	
	public ScoreBoard(int width, int height) {
		this.areaWidth = width;
		this.areaHeight = height;
		wButton = 100;
		hButton = 50;
		flagPlay = 0;
		setLayout(null);
		scoreList = new TreeMap<Integer, String>(Collections.reverseOrder());
		insertName = new JTextField();
		insertName.setColumns(50);
		insertName.setFocusable(true);
		okay = new JButton("Okay");
		okay.setBounds(((areaWidth/2)-(wButton/2)), 540, wButton, hButton);
		this.add(okay);
		ButtonHandler handler = new ButtonHandler();
		okay.addActionListener(handler);
		score = 0;
		readFile();
		this.setVisible(true);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		g.drawString("ScoreBoard", ((areaWidth/2)-87), 45);

		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		int yAdder = 45;
		int y = 105;
		
		Set s = scoreList.entrySet();
		Iterator i = s.iterator();
		
		while(i.hasNext()) {
			Map.Entry m = (Map.Entry) i.next();
			int key = (Integer) m.getKey();
			String value = (String) m.getValue();
			g.drawString(value, 20, y);
			g.drawString(Integer.toString(key), (areaWidth/2)+120, y);
			y += yAdder;
		}
	}
	
	
	private void readFile() {
		FileReader scoreFileReader = null;
		
		try {
			scoreFileReader = new FileReader("src/score/ScoreBoard.dat");
			scoreReader = new BufferedReader(scoreFileReader);
			String line;
			while((line = scoreReader.readLine()) != null) {
				String[] tokens = line.split(":");
				scoreList.put(Integer.parseInt(tokens[1]), tokens[0]);
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
				if(flagPlay == 1) {
					scoreList.put(score, insertName.getText());
					writeFile();
					System.out.println(insertName.getText());
				}
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(okay.getParent());
				frame.setContentPane(new MainMenu(areaWidth, areaHeight));
				frame.invalidate();
				frame.validate();
			}
		}
	}
	
	public void setScore(int score) {
		flagPlay = 1;
		if(scoreList != null)
			smallestScore = scoreList.lastKey();
		if(score > smallestScore) {
			this.score = score;
			scoreList.put(score, "");
			scoreList.pollLastEntry();
			
			int index = 0;
			for(Map.Entry mapElement : scoreList.entrySet()) {
				int key = (int)mapElement.getKey();
				if(key == score)
					break;
				index++;
			}
			insertName.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
			insertName.setBounds(20, (105+index*45)-30, areaWidth/2 - 20, 45);
			this.add(insertName);
		}
	
	}
	
	private void writeFile() {
		insertToFile = "";
		int index = 0;
		for(Map.Entry mapElement : scoreList.entrySet()) {
			int key = (int)mapElement.getKey();
			String value = (String)mapElement.getValue();
			if(index < 9) {
				insertToFile = insertToFile+ value + ":" + Integer.toString(key)+"\n";
			} else {
				insertToFile = insertToFile+ value + ":" + Integer.toString(key);
			}
			index++;
		}
		
		
		File scoreFile = new File("src/score/ScoreBoard.dat");
		Writer writeFile = null;
		try {
			if(!scoreFile.createNewFile()) {
				scoreFile.delete();
				scoreFile.createNewFile();
				writeFile = new FileWriter(scoreFile);
				
				writeFile.write(insertToFile);
				
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
