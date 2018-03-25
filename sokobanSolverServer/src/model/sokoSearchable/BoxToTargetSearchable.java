package model.sokoSearchable;

import model.policies.BoxSokoPolicy;
import model.position.Direction;
import model.position.Position;
import model.predicats.SokoPredicate;
import model.states.SokoState;
import predicates.Clause;
import predicates.Predicate;
import searchLib.State;

public class BoxToTargetSearchable extends SokoSearchable {

	
	public BoxToTargetSearchable(State<SokoState> initialState) {
		super(initialState, new BoxSokoPolicy());
	}

	@Override
	Clause generateActionPreconditions(Position currentBoxPos, Direction direction) {
		
		int currentBoxRow = currentBoxPos.getRow();
		int currentBoxCol = currentBoxPos.getCol();

		Predicate precondition = null;

		switch (direction) {

		case UP:
			precondition = new SokoPredicate("PlayerAt", "", (currentBoxRow + 1) + "," + currentBoxCol);
			break;

		case DOWN:
			precondition = new SokoPredicate("PlayerAt", "", (currentBoxRow - 1) + "," + currentBoxCol);
			break;

		case LEFT:
			precondition = new SokoPredicate("PlayerAt", "", (currentBoxRow) + "," + (currentBoxCol + 1));
			break;

		case RIGHT:
			precondition = new SokoPredicate("PlayerAt", "", (currentBoxRow) + "," + (currentBoxCol - 1));
			break;

		}

		return new Clause(precondition);
	}




}
