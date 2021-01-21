package com.blockstack.utils;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

	// Tetris99 Nintendo Switch Assets
	public enum Sfx {
		ingame(Asset.getAudio("ingame.wav")),
		gameover(Asset.getAudio("gameover.wav")),
		harddrop(Asset.getAudio("harddrop.wav")),
		hold(Asset.getAudio("hold.wav")),
		move(Asset.getAudio("move.wav")),
		pause(Asset.getAudio("pause.wav")),
		rotate(Asset.getAudio("rotate.wav")),
		softdrop(Asset.getAudio("softdrop.wav")),
		_single(Asset.getAudio("single.wav")),
		_double(Asset.getAudio("double.wav")),
		_triple(Asset.getAudio("triple.wav")),
		_tetris(Asset.getAudio("tetris.wav")),
		ok(Asset.getAudio("cursor2.wav")),
		cursor(Asset.getAudio("cursor.wav")),
		fix(Asset.getAudio("fix.wav")),
		cancel(Asset.getAudio("cancel.wav")),
		landing(Asset.getAudio("landing.wav"));

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

	public void replayAudio(boolean isFast) {
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
