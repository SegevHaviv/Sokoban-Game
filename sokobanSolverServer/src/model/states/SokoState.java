package model.states;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.position.Direction;
import model.position.Position;

public class SokoState implements Serializable {

	private static final long serialVersionUID = 1L;
	private char[][] map;

	public SokoState() {
	}

	public SokoState(char[][] map) {

		this.setMap(map);
	}

	public char[][] getMap() {
		return map;
	}

	public void setMap(char[][] map) {
		this.map = map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(map);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SokoState other = (SokoState) obj;

		char[][] otherMap = other.map;

		for (int i = 0; i < this.map.length; i++) {

			for (int j = 0; j < this.map[i].length; j++) {

				if (map[i][j] != otherMap[i][j])
					return false;

			}

		}

		return true;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < map.length; i++) {

			builder.append(map[i]);
			builder.append('\n');

		}

		return builder.toString();

	}
	
	public void movePlayer(Direction direction){
		
		Position playerPos = getPlayerPos(this.map);
		int playerRow = playerPos.getRow();
		int playerCol = playerPos.getCol();
		
		if(this.map[playerRow][playerCol] == 'a')
			this.map[playerRow][playerCol] = 'o';
		else this.map[playerRow][playerCol] = ' ';
		
	
		
		Position nextPlayerPos = Position.generateNextPosition(playerPos, direction);
		int nextPlayerRow = nextPlayerPos.getRow();
		int nextPlayerCol = nextPlayerPos.getCol();
		
			
			if(this.map[nextPlayerRow][nextPlayerCol] == '@' || this.map[nextPlayerRow][nextPlayerCol] == '*'){
			Position nextBoxPos = Position.generateNextPosition(nextPlayerPos, direction);
			int nextBoxRow = nextBoxPos.getRow();
			int nextBoxCol = nextBoxPos.getCol();
			if(this.map[nextBoxRow][nextBoxCol] == 'o')
				this.map[nextBoxRow][nextBoxCol] = '*';
			else this.map[nextBoxRow][nextBoxCol] = '@';
				
			}
			
			if(this.map[nextPlayerRow][nextPlayerCol] == '*' || this.map[nextPlayerRow][nextPlayerCol] == 'o')
				this.map[nextPlayerRow][nextPlayerCol] = 'a';
			else this.map[nextPlayerRow][nextPlayerCol] = 'A';
			
		
		
	}

	public static char[][] cloneMap(char[][] map) {

		char[][] newMap = new char[map.length][map[0].length];
		for (int i = 0; i < map.length; i++) {

			for (int j = 0; j < map[i].length; j++) {

				newMap[i][j] = map[i][j];

			}

		}
		return newMap;

	}

	public static List<Position> getBoxesPos(char[][] map) {

		ArrayList<Position> boxesPos = new ArrayList<>();

		for (int i = 0; i < map.length; i++) {

			for (int j = 0; j < map[i].length; j++) {

				if (map[i][j] == '@' || map[i][j] == '*') {

					boxesPos.add(new Position(i, j));

				}
			}
		}

		return boxesPos;

	}
	
	public static Position getPlayerPos(char[][] map) {


		for (int i = 0; i < map.length; i++) {

			for (int j = 0; j < map[i].length; j++) {

				if (map[i][j] == 'A' || map[i][j] == 'a') {

					return new Position(i, j);

				}
			}
		}
		
		return null;


	}


	
	public static char[][] moveBox(char[][] map, Position boxPos, Direction direction) {

		char[][] mapAfterMovement = cloneMap(map);

		int boxRow = boxPos.getRow();
		int boxCol = boxPos.getCol();

		if (mapAfterMovement[boxRow][boxCol] == '*') {

			mapAfterMovement[boxRow][boxCol] = 'o';

		} else
			mapAfterMovement[boxRow][boxCol] = ' ';

		Position nextBoxPos = Position.generateNextPosition(boxPos, direction);

		int nextBoxRow = nextBoxPos.getRow();
		int nextBoxCol = nextBoxPos.getCol();

		if (mapAfterMovement[nextBoxRow][nextBoxCol] == 'o') {

			mapAfterMovement[nextBoxRow][nextBoxCol] = '*';

		} else
			mapAfterMovement[nextBoxRow][nextBoxCol] = '@';

		return mapAfterMovement;
	}
	
	
		
		
	
}
