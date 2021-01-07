package com.application.Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.*;
import javax.imageio.ImageIO;

public class MainMenu extends JPanel {

	private BufferedImage image;
	private BufferedImage home;
	private int areaWidth;
	private int areaHeight;
	private JButton play;
	private JButton score;
	private JButton credits;
	private JButton exit;
	private final int wButton;
	private final int hButton;
	private ImageIcon iconStart = null;
	private ImageIcon iconScore = null;
	private ImageIcon iconCredits = null;
	private ImageIcon iconExit = null;
	
	public MainMenu(int width, int height) {
		this.areaWidth = width;
		this.areaHeight = height;
		wButton = 110;
		hButton = 60;
		this.setPreferredSize(new Dimension(areaWidth, areaHeight));
		setLayout(null);
		try {
			image = ImageIO.read(new File("src/images/MenuLogo.png"));
//			home = ImageIO.read(new File("src/images/mainmenu.jpg"));
			iconStart = new ImageIcon("src/images/StartGameButton.png");
			iconScore = new ImageIcon("src/images/ScoreButton.png");
			iconCredits = new ImageIcon("src/images/CreditsButton.png");
			iconExit = new ImageIcon("src/images/ExitButton.png");
		} catch(IOException e) {
			System.out.println(e);
			
		}
		
		setButton();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(home, 0, 0, null);
		g.drawImage(image, ((areaWidth/2) - image.getWidth()/2), 20, null);
	}
	
	public class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
//			Main.sfx.ok.playbackMusic();
			if(e.getActionCommand().equals("") || e.getActionCommand().equals("Start Game")) {			
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(play.getParent());
				frame.setContentPane(new Board(frame));
				frame.setFocusable(true);
				frame.revalidate();
				frame.getContentPane().requestFocus();
				frame.getContentPane().setFocusable(true);
			}
				
			else if(e.getActionCommand().equals(" ") || e.getActionCommand().equals("Score")) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(score.getParent());
				
				frame.setContentPane(new ScoreBoard(areaWidth, areaHeight));
				frame.revalidate();
			}
				
			else if(e.getActionCommand().equals("  ") || e.getActionCommand().equals("Credits")) {
				//show Credits
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(credits.getParent());
				JOptionPane.showMessageDialog(frame, "Kosim\nWilliam");
			}
			else if(e.getActionCommand().equals("   ") || e.getActionCommand().equals("Exit")) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(exit.getParent());
				int choose = JOptionPane.showConfirmDialog(frame, "Do you really want to exit the application?",
						"Confirm Close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(choose == JOptionPane.YES_OPTION) {
					frame.dispose();
				} else {
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		}
	}
	
	private void setHover(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
//				Main.sfx.cursor.playbackMusic();
				button.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
			}
			public void mouseExited(MouseEvent e) {
				button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5));
			}
		});
	}
	
	private void setButton() {
		if(iconStart == null || iconScore == null || iconCredits == null || iconExit == null) {			
			play = new JButton("Start Game");
			score = new JButton("Score");
			credits = new JButton("Credits");
			exit = new JButton("Exit");
		}
		else {
			play = new JButton("", iconStart);
			score = new JButton(" ", iconScore);
			credits = new JButton("  ", iconCredits);
			exit = new JButton("   ", iconExit);
		}
		
		play.setHorizontalTextPosition(JButton.CENTER);
		score.setHorizontalTextPosition(JButton.CENTER);
		credits.setHorizontalTextPosition(JButton.CENTER);
		exit.setHorizontalTextPosition(JButton.CENTER);

		play.setBounds(((areaWidth/2) - (wButton/2)), 273, wButton, hButton);
		score.setBounds(((areaWidth/2) - (wButton/2)), 343, wButton, hButton);
		credits.setBounds(((areaWidth/2) - (wButton/2)), 413, wButton, hButton);
		exit.setBounds(((areaWidth/2) - (wButton/2)), 483, wButton, hButton);
		
		play.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5));
		score.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5));
		credits.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5));
		exit.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5));
		
		ButtonHandler handler = new ButtonHandler();
		play.addActionListener(handler);
		score.addActionListener(handler);
		credits.addActionListener(handler);
		exit.addActionListener(handler);
		
		setHover(play);
		setHover(score);
		setHover(credits);
		setHover(exit);
		
		this.add(play);
		this.add(score);
		this.add(credits);
		this.add(exit);
	}
}
