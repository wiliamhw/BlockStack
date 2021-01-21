package com.application.Swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.application.Swing.Audio.Sfx;

public class PauseMenu extends JDialog {

	private final int areaWidth = 272;
	private final int areaHeight = 340;
	private final int border = 10;
	private final int wButton = 100;
	private final int hButton = 50;
	private JButton resume;
	private JButton restart;
	private JButton quit;
	private final JTextField pauseHeader = new JTextField("Paused");
	private final JButton pauseButton;
	private final JFrame frame;
	private final Board board;
	private ImageIcon iconResume = null;
	private ImageIcon iconRestart = null;
	private ImageIcon iconMenu = null;

	public PauseMenu(JFrame owner, Board board, JButton pauseButton) {
		super(owner, true);
		this.frame = owner;
		this.board = board;
		this.pauseButton = pauseButton;
		this.setPreferredSize(new Dimension(areaWidth, areaHeight));
		this.setLayout(null);
		this.setUndecorated(true);
		this.getContentPane().setBackground(new Color(46, 49, 49));
		getRootPane().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10));

		
		try {
			iconResume = new ImageIcon("src/images/ResumeButton.png");
			iconRestart = new ImageIcon("src/images/RestartButton.png");
			iconMenu = new ImageIcon("src/images/MainMenuButton.png");
		} catch(Exception e) {
			System.out.println(e);
		}
		
		setButton();

		pauseHeader.setBackground(new Color(46, 49, 49));
		pauseHeader.setBorder(null);
		pauseHeader.setFont(new Font("Tahoma", Font.BOLD, 30));
		pauseHeader.setHorizontalAlignment(JTextField.CENTER);
		pauseHeader.setEditable(false);
		pauseHeader.setForeground(Color.WHITE);
		pauseHeader.setBounds(-border / 2, border, areaWidth, hButton - border);

		this.add(pauseHeader);

	}

	public void showDialog() {
		this.pack();
		this.setLocationRelativeTo(this.board);
		pauseButton.setBackground(UIManager.getColor("control"));
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
			Sfx.ok.audio.playbackAudio(true);
			if (e.getActionCommand().equals("Resume") || e.getActionCommand().equals("")) {
				try {
					PauseMenu dialog = (PauseMenu) SwingUtilities.getWindowAncestor(resume.getParent());

					dialog.unpause();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			} else if (e.getActionCommand().equals("Restart") || e.getActionCommand().equals(" ")) {
				Sfx.ingame.audio.stopAudio();
				unpause();
				board.start();
				board.repaint();

			} else if (e.getActionCommand().equals("Main Menu") || e.getActionCommand().equals("  ")) {
				int choose = JOptionPane.showConfirmDialog(frame, "Do you really want to go back to main menu?",
						"Confirm Back", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (choose == JOptionPane.YES_OPTION) {
					Sfx.ingame.audio.stopAudio();
					hideDialog();
					board.gotoScoreboard();
				}
			}
		}

	}

	private void setHover(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				Sfx.cursor.audio.playbackAudio(true);
				button.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
			}
			public void mouseExited(MouseEvent e) {
				button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5));
			}
		});
	}
	
	private void setButton() {
		ButtonHandler handler1 = new ButtonHandler();
		
		if(iconMenu == null || iconRestart == null || iconResume == null) {
			resume = new JButton("Resume");
			restart = new JButton("Restart");
			quit = new JButton("Main Menu");			
		}
		else {
			resume = new JButton("", iconResume);
			restart = new JButton(" ", iconRestart);
			quit = new JButton("  ", iconMenu);
		}
		
		resume.setHorizontalTextPosition(JButton.CENTER);
		restart.setHorizontalTextPosition(JButton.CENTER);
		quit.setHorizontalTextPosition(JButton.CENTER);
		
		resume.setBounds((areaWidth - wButton - border) / 2, 80, wButton, hButton);
		restart.setBounds((areaWidth - wButton - border) / 2, 160, wButton, hButton);
		quit.setBounds((areaWidth - wButton - border) / 2, 240, wButton, hButton);
		
		resume.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5));
		restart.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5));
		quit.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5));
		
		resume.addActionListener(handler1);
		restart.addActionListener(handler1);
		quit.addActionListener(handler1);
		
		setHover(resume);
		setHover(restart);
		setHover(quit);
		
		this.add(resume);
		this.add(restart);
		this.add(quit);
		this.add(pauseHeader);
	}
}
