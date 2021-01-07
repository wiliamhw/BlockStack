package com.application.Swing;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;


public class Music {
	
	private final String musicLocation;
	private File musicPath;
	private Clip clip;
	private long clipTimePosition;
	
	public Music(String musicLocation) {
		this.musicLocation = musicLocation;
		addMusic();
	}
	
	private void addMusic() {
		try {
			musicPath = new File(musicLocation);
			
			if (musicPath.exists()) {
				AudioInputStream audioinput = AudioSystem.getAudioInputStream(musicPath);
				clip = AudioSystem.getClip();
				clip.open(audioinput);
			} else {
				System.out.println("Can't find " + musicLocation);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void playMusic(boolean _loop) {
		try {
			clip.start();
			if (_loop) clip.loop(Clip.LOOP_CONTINUOUSLY );
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void playbackMusic() {
		try {
			clipTimePosition = 0;
			clip.setMicrosecondPosition(clipTimePosition);
			playMusic(false);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void pauseMusic() {
		try {
			clipTimePosition = clip.getMicrosecondPosition();
			clip.setMicrosecondPosition(clipTimePosition);
			clip.stop();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void stopMusic() {
		try {
			clip.stop();
			clipTimePosition = 0;
			clip.setMicrosecondPosition(clipTimePosition);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
