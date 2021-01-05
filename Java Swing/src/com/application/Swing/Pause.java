package com.application.Swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Pause extends JDialog {

	private int areaWidth = 272;
	private int areaHeight = 320;
	private JButton resume = new JButton("Resume");
	private JButton restart = new JButton("Restart");
	private JButton quit = new JButton("Quit");
	private JFrame frame;
	private Board board;
	private int wButton;
	private int hButton;

	public Pause(JFrame owner, Board board) {
		super(owner, true);
		this.frame = owner;
		this.board = board;
		wButton = 100;
		hButton = 50;
		this.setPreferredSize(new Dimension(areaWidth, areaHeight));
		this.setLayout(null);
		this.setUndecorated(true);
		this.getContentPane().setBackground(new Color(244, 179, 80));

		resume.setBounds(82, 50, wButton, hButton);
		restart.setBounds(82, 125, wButton, hButton);
		quit.setBounds(82, 200, wButton, hButton);

		this.add(resume);
		this.add(restart);
		this.add(quit);

		ButtonHandler handler1 = new ButtonHandler();
		resume.addActionListener(handler1);
		restart.addActionListener(handler1);
		quit.addActionListener(handler1);
	}
	
	public void showDialog() {
		this.pack();
		this.setLocationRelativeTo(this.board);
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
				
			} else if (e.getActionCommand().equals("Quit")) {
				int choose = JOptionPane.showConfirmDialog(frame, "Do you really want to go back to main menu?",
						"Confirm Back", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(choose == JOptionPane.YES_OPTION) {
					hideDialog();
					board.gotoScoreboard();
				}
			}
		}

	}
}
