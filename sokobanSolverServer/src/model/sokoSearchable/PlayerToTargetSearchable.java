package model.sokoSearchable;

import model.policies.PlayerSokoPolicy;
import model.position.Direction;
import model.position.Position;
import model.states.SokoState;
import predicates.Clause;
import searchLib.State;

public class PlayerToTargetSearchable extends SokoSearchable {

	
	public PlayerToTargetSearchable(State<SokoState> initialState) {
		super(initialState, new PlayerSokoPolicy());
	}

	@Override
	Clause generateActionPreconditions(Position currentBoxPos, Direction direction) {	
		return new Clause();
	}
}
