package com.application.Swing;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

	// Tetris99 Nintendo Switch Assets
	enum Sfx {
		ingame(new Audio("src/music/ingame.wav")),
		gameover(new Audio("src/music/gameover.wav")),
		harddrop(new Audio("src/music/harddrop.wav")),
		hold(new Audio("src/music/hold.wav")),
		move(new Audio("src/music/move.wav")),
		pause(new Audio("src/music/pause.wav")),
		rotate(new Audio("src/music/rotate.wav")),
		softdrop(new Audio("src/music/softdrop.wav")),
		_single(new Audio("src/music/single.wav")),
		_double(new Audio("src/music/double.wav")),
		_triple(new Audio("src/music/triple.wav")),
		_tetris(new Audio("src/music/tetris.wav")),
		ok(new Audio("src/music/cursor2.wav")),
		cursor(new Audio("src/music/cursor.wav")),
		fix(new Audio("src/music/fix.wav")),
		cancel(new Audio("src/music/cancel.wav")),
		landing(new Audio("src/music/landing.wav"));

		public Audio audio;
		private Sfx(Audio audio) {
			this.audio = audio;
		}
	}

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
