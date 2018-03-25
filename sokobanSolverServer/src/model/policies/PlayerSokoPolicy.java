package model.policies;

import model.position.Direction;
import model.position.Position;

public class PlayerSokoPolicy implements Policy{
	

	@Override
	public boolean isLegal(char[][] map,Position heroPos, Direction direction) {

		
		Position nextPos = Position.generateNextPosition(heroPos, direction);
		

		if (map[nextPos.getRow()][nextPos.getCol()]=='#' ||  map[nextPos.getRow()][nextPos.getCol()]=='*'){
			
			return false;
			
		}

		return true;
	}
	

}
