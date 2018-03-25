package model.data.policies;

import commons.Direction;
import model.data.levels.SokobanLevel;


public abstract class Policy {
	
	SokobanLevel level;
	
	public Policy() {
		level = null;
	}
	
	
	public Policy(SokobanLevel level) {
		this.level = level;
	}
	public abstract boolean isLegal(int heroIndex, Direction direction);

	
	
	public SokobanLevel getLevel() {
		return level;
	}

	public void setLevel(SokobanLevel level) {
		this.level = level;
	}
	
	
	

}
