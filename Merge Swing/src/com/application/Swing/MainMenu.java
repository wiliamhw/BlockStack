package com.application.Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.*;
import javax.imageio.ImageIO;

public class MainMenu extends JPanel{

	private BufferedImage image;
	private int areaWidth;
	private int areaHeight;
	private JButton play = new JButton("Start Game");
//	private JButton tutorial = new JButton("Tutorial");
	private JButton score = new JButton("Score");
	private JButton credits = new JButton("Credits");
	private JButton exit = new JButton("Exit");
	private int wButton;
	private int hButton;
	
	public MainMenu(int width, int height) {
		this.areaWidth = width;
		this.areaHeight = height;
		wButton = 100;
		hButton = 50;
		this.setPreferredSize(new Dimension(areaWidth, areaHeight));
		setLayout(null);
		try {
			image = ImageIO.read(new File("MenuLogo.png"));
		} catch(IOException e) { }

		play.setBounds(((areaWidth/2) - (wButton/2)), 273, wButton, hButton);
		score.setBounds(((areaWidth/2) - (wButton/2)), 343, wButton, hButton);
		credits.setBounds(((areaWidth/2) - (wButton/2)), 413, wButton, hButton);
		exit.setBounds(((areaWidth/2) - (wButton/2)), 483, wButton, hButton);
		
		this.add(play);
//		this.add(tutorial);
		this.add(score);
		this.add(credits);
		this.add(exit);
		
		ButtonHandler handler = new ButtonHandler();
		play.addActionListener(handler);
		score.addActionListener(handler);
		credits.addActionListener(handler);
		exit.addActionListener(handler);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("C");
		g.drawImage(image, ((areaWidth/2) - image.getWidth()/2), 20, null);
	}
	
	public class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("Start Game")) {
				//run game
			}
				
			else if(e.getActionCommand().equals("Score")) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(score.getParent());
				
				frame.setContentPane(new ScoreBoard(areaWidth, areaHeight));
				frame.invalidate();
				frame.validate();
			}
				
			else if(e.getActionCommand().equals("Credits")) {
				//show Credits
			}
			else if(e.getActionCommand().equals("Exit")) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(exit.getParent());
				int choose = JOptionPane.showConfirmDialog(null, "Do you really want to exit the application?",
						"Confirm Close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(choose == JOptionPane.YES_OPTION) {
					frame.dispose();
					System.out.println("close");
				} else {
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		}
		
	}
}