package model.sokoSearchable;

import java.util.HashMap;

import model.policies.BoxSokoPolicy;
import model.policies.Policy;
import model.position.Direction;
import model.position.Position;
import model.predicats.SokoPredicate;
import model.states.SokoState;
import predicates.Action;
import predicates.Clause;
import searchLib.Searchable;
import searchLib.State;

public abstract class SokoSearchable implements Searchable<SokoState> {

	private State<SokoState> initialState;

	private Policy policy;

	private State<SokoState> goal;

	public SokoSearchable(State<SokoState> initialState, Policy policy) {
		this.policy = policy;
		this.initialState = initialState;

		generateGoal();

	}

	private void generateGoal() {

		SokoState state = initialState.getState();
		char[][] map = state.getMap();
		char[][] solvedMap = generateSolvedMap(map);
		goal = new State<SokoState>(new SokoState(solvedMap));

	}

	private char[][] generateSolvedMap(char[][] map) {

		char[][] solvedMap = SokoState.cloneMap(map);

		for (int i = 0; i < solvedMap.length; i++) {

			for (int j = 0; j < solvedMap[i].length; j++) {

				if (solvedMap[i][j] == 'a')
					solvedMap[i][j] = 'o'; 

				else if (solvedMap[i][j] == 'A' || solvedMap[i][j] == '@')
					solvedMap[i][j] = ' ';

				if (solvedMap[i][j] == 'o') {
					solvedMap[i][j] = '*';

				}
			}
		}
		return solvedMap;

	}

	private char[][] generateMapAfterMovementIfLegal(char[][] currentMap, Position currentBoxPos, Direction direction) {

		if (policy.isLegal(currentMap, currentBoxPos, direction)) {

			char[][] mapAfterMovement = SokoState.moveBox(currentMap, currentBoxPos, direction);

			return mapAfterMovement;
		}

		return null;

	}

	@Override
	public HashMap<Action, State<SokoState>> getPossibleMoves(State<SokoState> current) {
		char[][] currentMap = current.getState().getMap();

		Position currentBoxPos = SokoState.getBoxesPos(currentMap).get(0);

		HashMap<Action, State<SokoState>> possibleMoves = new HashMap<>();

		for (Direction direction : Direction.values()) {

			char[][] adjacentMap = generateMapAfterMovementIfLegal(currentMap, currentBoxPos, direction);
			if (adjacentMap != null) {
				Action action = new Action("Move" + " " + direction.toString().toLowerCase(), "", "");
				Clause preconditions = generateActionPreconditions(currentBoxPos, direction);
				action.setPreconditions(preconditions);
				if (policy instanceof BoxSokoPolicy) {
					action.addEffect(
							new SokoPredicate("PlayerAt", "", currentBoxPos.getRow() + "," + currentBoxPos.getCol()));
					Position nextBoxPos = Position.generateNextPosition(currentBoxPos, direction);
					action.addEffect(new SokoPredicate("BoxAt", "", nextBoxPos.getRow() + "," + nextBoxPos.getCol()));
				}

				State<SokoState> adjacent = new State<>(new SokoState(adjacentMap));
				possibleMoves.put(action, adjacent);
			}
		}

		return possibleMoves;

	}

	abstract Clause generateActionPreconditions(Position currentBoxPos, Direction direction);

	@Override
	public State<SokoState> getInitialState() {
		return initialState;
	}

	@Override
	public State<SokoState> getGoal() {
		return goal;
	}

}
