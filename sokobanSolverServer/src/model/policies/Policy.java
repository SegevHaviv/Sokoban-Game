package model.policies;

import model.position.Direction;
import model.position.Position;

public interface Policy {
	
	public boolean isLegal(char[][] map,Position heroPos, Direction direction);

}
