package model.data.policies;

import commons.Direction;
import model.data.elements.Position;
import model.data.levels.SokobanLevel;

public class SokobanPolicy extends Policy {


	public SokobanPolicy() {}
	
	public SokobanPolicy(SokobanLevel level) {
		super(level);
	}

	
	
	@Override
	public boolean isLegal(int heroIndex, Direction direction) {

		Position heroPos = level.getHeroes().get(heroIndex);
		
		Position nextPos = Position.generateNextPosition(heroPos, direction);
		
		if(level.isWall(nextPos) || level.isHero(nextPos))
			return false;
		
		if(level.isBox(nextPos)){
			
			Position nextBoxPos = Position.generateNextPosition(nextPos, direction);
			
			if(level.isWall(nextBoxPos) || level.isBox(nextBoxPos) || level.isHero(nextBoxPos))
				return false;
				
		}
		
		return true;
	}
	
	


}
