package model.commons;

import java.util.ArrayList;

public class CommonLevel {
	
	private String name;
	private int stepsTaken;
	private int elapsedTime;
	private ArrayList<ArrayList<Character>> map;
	
	public CommonLevel(String name, int stepsTaken, int elapsedTime, ArrayList<ArrayList<Character>> map) {

		this.name = name;
		this.stepsTaken = stepsTaken;
		this.elapsedTime = elapsedTime;
		this.map = map;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStepsTaken() {
		return stepsTaken;
	}

	public void setStepsTaken(int stepsTaken) {
		this.stepsTaken = stepsTaken;
	}

	public int getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public ArrayList<ArrayList<Character>> getMap() {
		return map;
	}

	public void setMap(ArrayList<ArrayList<Character>> map) {
		this.map = map;
	}
	
	
	

}
