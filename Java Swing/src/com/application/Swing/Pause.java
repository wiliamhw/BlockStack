package com.application.Swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Pause extends JDialog{

	private int areaWidth = 272;
	private int areaHeight = 320;
	private JButton resume = new JButton("Resume");
	private JButton restart = new JButton("Restart");
	private JButton quit = new JButton("Main Menu");
	private JFrame frame;
	private Board board;
	private int wButton;
	private int hButton;
	private JTextField pauseHeader;

	public Pause(JFrame owner, Board board) {
		super(owner, true);
		this.frame = owner;
		this.board = board;
		wButton = 100;
		hButton = 50;
		this.setPreferredSize(new Dimension(areaWidth, areaHeight));
		this.setLayout(null);
		this.setUndecorated(true);
		this.getContentPane().setBackground(Color.DARK_GRAY);
		getRootPane().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10));

		resume.setBounds(((areaWidth/2)-(wButton/2)), 80, wButton, hButton);
		restart.setBounds(((areaWidth/2)-(wButton/2)), 160, wButton, hButton);
		quit.setBounds(((areaWidth/2)-(wButton/2)), 240, wButton, hButton);
		
		pauseHeader = new JTextField();
		pauseHeader.setBackground(Color.DARK_GRAY);
		pauseHeader.setBorder(null);
		pauseHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		pauseHeader.setText("Paused");
		pauseHeader.setHorizontalAlignment(JTextField.CENTER);
		pauseHeader.setEditable(false);
		pauseHeader.setForeground(Color.WHITE);
		pauseHeader.setBounds(0, 10, areaWidth, 40);

		this.add(resume);
		this.add(restart);
		this.add(quit);
		this.add(pauseHeader);

		ButtonHandler handler1 = new ButtonHandler();
		resume.addActionListener(handler1);
		restart.addActionListener(handler1);
		quit.addActionListener(handler1);
		
		setHover(resume);
		setHover(restart);
		setHover(quit);
	}
	
	public void showDialog() {
		this.pack();
		this.setLocationRelativeTo(this.board);
		board.pauseButton.setBackground(UIManager.getColor("control"));
		board.setEnabled(false);
		this.setVisible(true);
	}
	
	public void hideDialog() {
		board.setEnabled(true);
		this.setVisible(false);
	}
	
	private void unpause() {
		board.requestFocusInWindow();
		board.pause();
	}

	public class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("Resume")) {
				try {
					Pause dialog = (Pause) SwingUtilities.getWindowAncestor(resume.getParent());
					board.pauseButton.setText("Pause");
					
					dialog.unpause();
				}
				catch (Exception ex) {
					System.out.println(ex);
				}
			} else if (e.getActionCommand().equals("Restart")) {
				board.stopMusic();
				unpause();
				board.start();
				board.repaint();
				
			} else if (e.getActionCommand().equals("Main Menu")) {
				int choose = JOptionPane.showConfirmDialog(frame, "Do you really want to go back to main menu?",
						"Confirm Back", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(choose == JOptionPane.YES_OPTION) {
					hideDialog();
					board.gotoScoreboard();
				}
			}
		}

	}
	
	private void setHover(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				button.setBackground(new Color(244, 179, 80));
			}
			public void mouseClicked(MouseEvent e) {
				button.setBackground(new Color(244, 179, 80));
			}
			public void mousePressed(MouseEvent e) {
				button.setBackground(new Color(244, 179, 80));
			}
			public void mouseExited(MouseEvent e) {
				button.setBackground(UIManager.getColor("control"));
			}
		});
	}
}
