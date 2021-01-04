package com.application.Swing;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
//import javax.swing.JOptionPane;

public class Music {
	
	void playMusic(String musicLocation) {
		try {
			File musicPath = new File(musicLocation);
			
			if (musicPath.exists()) {
				AudioInputStream audioinput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioinput);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY );
				
//				JOptionPane.showMessageDialog(null, "Hit ok to pause");
//				long clipTimePosition = clip.getMicrosecondPosition();
//				clip.stop();
//				
//				JOptionPane.showMessageDialog(null, "Hit ok to resume");
//				clip.setMicrosecondPosition(clipTimePosition);
//				clip.start();
//				
//				JOptionPane.showMessageDialog(null, "Press OK to stop playing");
			} else {
				System.out.println("Can't find file");
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
