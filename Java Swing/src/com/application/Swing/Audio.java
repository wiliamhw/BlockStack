package com.application.Swing;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

	private final String musicLocation;
	private final File musicPath;
	private Clip clip;
	private long clipTimePosition;

	public Audio(String musicLocation) {
		this.musicLocation = musicLocation;
		musicPath = new File(this.musicLocation);

		if (musicPath.exists())
			addAudio();
		else
			System.out.println("Can't find " + musicLocation);
	}

	private void addAudio() {
		try {
			AudioInputStream audioinput = AudioSystem.getAudioInputStream(musicPath);
			clip = AudioSystem.getClip();
			clip.open(audioinput);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void playAudio(boolean isLooped) {
		try {
			clip.start();
			if (isLooped)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void playbackAudio(boolean isFast) {
		try {
			if (isFast) {
				// reset AudioInputStream and Clip
				addAudio();
			} else {
				clipTimePosition = 0;
				clip.setMicrosecondPosition(clipTimePosition);
			}
			playAudio(false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void pauseAudio() {
		try {
			clipTimePosition = clip.getMicrosecondPosition();
			clip.setMicrosecondPosition(clipTimePosition);
			clip.stop();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void stopAudio() {
		try {
			clip.stop();
			clipTimePosition = 0;
			clip.setMicrosecondPosition(clipTimePosition);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
