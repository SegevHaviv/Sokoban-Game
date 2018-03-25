package model.position;

public class Position {


	int row;
	int col;

	public Position() {
		this.row = 0;
		this.col = 0;

	}

	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public void setPosition(Position pos) {

		this.row = pos.getRow();
		this.col = pos.getCol();

	}

	public static Position generateNextPosition(Position currentPos, Direction direction) {

		Position nextPos = null;

		int currentRow = currentPos.getRow();
		int currentCol = currentPos.getCol();

		switch (direction) {

		case UP: {
			nextPos = new Position(currentRow - 1, currentCol);
			break;
		}

		case DOWN: {
			nextPos = new Position(currentRow + 1, currentCol);
			break;
		}

		case LEFT: {
			nextPos = new Position(currentRow, currentCol - 1);
			break;
		}

		case RIGHT: {
			nextPos = new Position(currentRow, currentCol + 1);
			break;
		}

		}

		return nextPos;

	}
	
	
	@Override
	public String toString() {
		return this.row + "," + this.col;
	}
}
