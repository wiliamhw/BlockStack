package com.blockstack.utils;

import com.blockstack.utils.Audio.Sfx;

public class Speed {
	private final int slowestSpeed; // in ms
	private final int fastestSpeed;
	private final int subtractor;
	private final int trigger; // raise level every <trigger> lines
	private int currSpeed;
	private int counter;
	public int tempLines;
	
	public Speed(int slowestSpeed, int fastestSpeed, int subtractor, int trigger) {
		tempLines = 0;
		this.counter = 0;
		this.slowestSpeed = slowestSpeed;
		this.fastestSpeed = fastestSpeed;
		this.currSpeed = slowestSpeed;
		this.subtractor = subtractor;
		this.trigger = trigger;
	}
	
	public int raiseSpeed() {
		int add = 0;
		while (currSpeed > fastestSpeed && tempLines >= trigger) {
			currSpeed -= subtractor;
			tempLines -= trigger;
//			System.out.println(currSpeed);
			Sfx.levelUp.audio.replayAudio(false);
			add++;
		}
		return add;
	}
	
	public void resetSpeed() {
		currSpeed = slowestSpeed;
	}
	
	public int raisetempLines() {
		tempLines++;
		return raiseSpeed();
	}
	
	public void resettempLines() {
		tempLines = 0;
	}
	
	public boolean doAction() {
		counter += subtractor;
		
		if (counter >= currSpeed) {
			counter = 0;
			return true;
		}
		return false;
	}
}
