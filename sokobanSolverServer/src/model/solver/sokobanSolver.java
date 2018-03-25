package model.solver;

import java.io.IOException;
import java.util.List;

import predicates.Action;
import searchLib.State;
import model.sokoPlannable.SokoPlannable;
import model.states.SokoState;
import strips.Strips;

public class sokobanSolver {

	public String solve(String levelToSolve) throws IOException, InterruptedException {

		String[] temp = levelToSolve.split(",");

		char[][] map = new char[temp.length][];

		for (int i = 0; i < temp.length; i++) {
			map[i] = temp[i].toCharArray();
		}
		
		int rowSize = map[0].length;
		for(char[] row : map){
			if(row.length != rowSize)
				return "";
		}
		SokoState state = new SokoState(map);
		State<SokoState> initialGameState = new State<>(state);

		SokoPlannable plannable = new SokoPlannable(initialGameState);

		Strips strips = new Strips();

		StringBuilder solution = new StringBuilder();
		List<Action> actions = strips.plan(plannable);

		if(actions==null)
			return "";
		
		for (Action action : actions) {
			solution.append(action.getType() + " ");
		}

		return solution.toString();
	}
}