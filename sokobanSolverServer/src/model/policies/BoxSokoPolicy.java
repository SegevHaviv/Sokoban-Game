package model.policies;

import model.position.Direction;
import model.position.Position;

public class BoxSokoPolicy implements Policy{
	
	
	@Override
	public boolean isLegal(char[][] map,Position boxPos, Direction direction) {
		
		Position nextboxPos = Position.generateNextPosition(boxPos, direction);
		Position playerPos = null;
		
		switch(direction){
		
		case UP: playerPos = Position.generateNextPosition(boxPos, Direction.DOWN);break;
		case DOWN: playerPos = Position.generateNextPosition(boxPos, Direction.UP);break;
		case LEFT: playerPos = Position.generateNextPosition(boxPos, Direction.RIGHT);break;
		case RIGHT: playerPos = Position.generateNextPosition(boxPos, Direction.LEFT);break;
		
		}
		

		
		if ((map[nextboxPos.getRow()][nextboxPos.getCol()]==' ' || map[nextboxPos.getRow()][nextboxPos.getCol()]=='o')&&
			(map[playerPos.getRow()][playerPos.getCol()]==' ' ||  map[playerPos.getRow()][playerPos.getCol()]=='o')){
			return true;
			
		}

		return false;
	}

}
