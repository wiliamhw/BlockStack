package com.application.Swing;

import java.awt.Color;

// Class for a block that is made from 4 rectangles
public class Form {
	Rectangle a;
	Rectangle b;
	Rectangle c;
	Rectangle d;
	Color color;
	private String name;
	int form = 1;

	public Form(Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public Form(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.name = name;

		switch (name) {
		case "j":
			color = new Color(112, 128, 144);
			break;
		case "l":
			color = new Color(184, 134, 11);
			break;
		case "o":
			color = new Color(205, 92, 92);
			break;
		case "s":
			color = new Color(34, 139, 34);
			break;
		case "t":
			color = new Color(95, 158, 160);
			break;
		case "z":
			color = new Color(255, 105, 180);
			break;
		case "i":
			color = new Color(244, 164, 96);
			break;
		}

		this.a._setColor(color);
		this.b._setColor(color);
		this.c._setColor(color);
		this.d._setColor(color);
	}

	public String getName() {
		return name;
	}

	public void changeForm() {
		if (form != 4) {
			form++;
		} else {
			form = 1;
		}
	}
}