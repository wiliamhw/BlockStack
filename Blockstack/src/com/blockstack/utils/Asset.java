package com.blockstack.utils;
import java.io.File;

import javax.swing.ImageIcon;

public class Asset {
	public static File getFile(String type, String name) {
		return new File("src/com/blockstack/assets/" + type + "/" + name);
	}
	
	public static ImageIcon getImageIcon(String name) {
		return new ImageIcon("src/com/blockstack/assets/images" + "/" + name);
	}
	
	public static Audio getAudio(String name) {
		return new Audio("src/com/blockstack/assets/music" + "/" + name);
	}
}
