package com.application.Swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

public class TetrisPanel extends JPanel {
	private static final int REFRESH_RATE = 30;
	private final int MOVE;
	private final int SIZE;
	private int XMAX;
	private int YMAX;
	private int[][] MESH;
//		private static Pane group = new Pane();
	private Form object;
//		private static Scene scene = new Scene(group, XMAX + 150, YMAX);
	private int score;
	private int top;
	private boolean game;
	private Form nextObj;
	private int linesNo;
	private Controller controller;
	private TetrisArea box;

	public TetrisPanel() {
		MOVE = SIZE = 25;
		XMAX = SIZE * 12;
		YMAX = SIZE * 24;
		this.setPreferredSize(new Dimension(XMAX + 150, YMAX));
		MESH = new int[XMAX / SIZE][YMAX / SIZE];
		score = top = linesNo = 0;
		game = true;
		controller = new Controller(MOVE, SIZE, XMAX, YMAX, MESH);
		nextObj = controller.makeBlock();
		box = new TetrisArea(0, 0, XMAX + 150, YMAX, Color.WHITE, Color.RED, Color.BLACK);

		// untuk mendapatkan ukuran area latar belakang jika frame diresize
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Component c = (Component) e.getSource();
				Dimension dim = c.getSize();
				XMAX = dim.width;
				YMAX = dim.height;
				box.set(0, 0, XMAX, YMAX);
				repaint();
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		box.draw(g);
	}
}
