package com.application.Swing;

public class Speed {
	private final int lowestSpeed; // in ms
	private final int highestSpeed;
	private final int adder;
	private final int trigger; // raise level every <trigger> lines
	private int currSpeed;
	private int counter;
	public int tempLines;
	
	public Speed(int lowestSpeed, int highestSpeed, int adder, int trigger) {
		tempLines = 0;
		this.counter = 0;
		this.lowestSpeed = lowestSpeed;
		this.highestSpeed = highestSpeed;
		this.currSpeed = lowestSpeed;
		this.adder = adder;
		this.trigger = trigger;
	}
	
	public int raiseSpeed() {
		int add = 0;
		while (currSpeed > highestSpeed && tempLines >= trigger) {
			currSpeed -= adder;
			tempLines -= trigger;
//			System.out.println(currSpeed);
			add++;
		}
		return add;
	}
	
	public void resetSpeed() {
		currSpeed = lowestSpeed;
	}
	
	public int raisetempLines() {
		tempLines++;
		return raiseSpeed();
	}
	
	public void resettempLines() {
		tempLines = 0;
	}
	
	public boolean doAction() {
		counter += adder;
		
		if (counter >= currSpeed) {
			counter = 0;
			return true;
		}
		
		return false;
	}
}
