package model.sokoPlannable;

import java.util.List;
import java.util.Set;

import model.predicats.SokoPredicate;
import model.sokoSearchable.BoxToTargetSearchable;
import model.sokoSearchable.PlayerToTargetSearchable;
import model.sokoSearchable.SokoSearchable;
import model.states.SokoState;
import predicates.Action;
import predicates.Clause;
import predicates.Predicate;
import searchLib.BestFirstSearch;
import searchLib.State;
import strips.Plannable;

public class SokoPlannable implements Plannable {

	private Clause knowledgeBase;

	private Clause goal;

	private State<SokoState> initialState;

	private int boxCount = 0;

	private SymbolsToPredicateFactory predicateFactory;
	

	public SokoPlannable(State<SokoState> initialState) {

		this.goal = new Clause();

		this.initialState = initialState;

		char[][] map = initialState.getState().getMap();

		

		this.knowledgeBase = generateKnowledgeBase(map);
		this.goal = generateGoal(map);
	}

	@Override
	public Clause getGoal() {
		return goal;
	}

	@Override
	public Clause getKnowledgebase() {
		return knowledgeBase;
	}

	private Clause generateKnowledgeBase(char[][] map) {
		
		predicateFactory = new SymbolsToPredicateFactory();
		
		Clause kb = new Clause();

		for (int i = 0; i < map.length; i++) {

			for (int j = 0; j < map[i].length; j++) {

				Predicate p = predicateFactory.CreatePredicate(map[i][j], i, j);
				if (p != null) {

					kb.add(p);
				}

			}

		}
		
		this.boxCount = predicateFactory.boxCounter;
		
		return kb;

	}

	private Clause generateGoal(char[][] map) {

		Clause goal = new Clause();

		for (int i = 0; i < map.length; i++) {

			for (int j = 0; j < map[i].length; j++) {

				switch (map[i][j]) {
				case 'o':
				case '*':
				case 'a':
					goal.add(new SokoPredicate("BoxAt", "?", i + "," + j));
					break;

				default:
					break;

				}

			}
		}
		
		return goal;

	}

	@Override
	public List<Action> getsatisfyingActions(Predicate top) {

		if (top.getType().equals("BoxAt")) {

			for (int i = 0; i < boxCount; i++) {
				SokoPredicate task = new SokoPredicate("BoxAt", "b" + i, top.getValue());
				boolean boxAlreadyOnTarget = false;

				for(Predicate p : goal.getPredicates()){
					if (knowledgeBase.satisfies(new Predicate("BoxAt", "b" + i, p.getValue())))
						boxAlreadyOnTarget = true;
				}
			
				if (!boxAlreadyOnTarget) {
					SokoSearchable searchable = generateSearchableForBoxMovement(task);
					//BFS<SokoState> bfs = new BFS<>();
					BestFirstSearch<SokoState> bfs = new BestFirstSearch<>();
					//Dijkstra<SokoState> bfs = new Dijkstra<>();
					List<Action> actions = bfs.search(searchable);

					if (actions != null) {
						for(Action action : actions){
							
							for(Predicate p : action.getEffects().getPredicates()){
								if(p.getType().equals("BoxAt"))
									p.setId("b"+i);
							}
							
						}
				
						return actions;
					}
				}
			}

		} else if (top.getType().equals("PlayerAt")) {

			SokoSearchable searchable = generateSearchableForPlayerMovement((SokoPredicate) top);

			//BFS<SokoState> bfs = new BFS<>();
			BestFirstSearch<SokoState> bfs = new BestFirstSearch<>();
			//Dijkstra<SokoState> bfs = new Dijkstra<>();
			List<Action> actions = bfs.search(searchable);
			if (actions != null) {
				actions.get(actions.size() - 1).addEffect(top);
				return actions;
			}

		}

		return null;
	}

	private SokoSearchable generateSearchableForBoxMovement(SokoPredicate goal) {

		char[][] currentMap = new char[initialState.getState().getMap().length][initialState.getState().getMap()[0].length];
		
		for(int i=0;i<currentMap.length-1;i++)
			for(int j=0;j<currentMap[i].length;j++)
				currentMap[i][j]=' ';
		
		String[] goalcoordinates = goal.getValue().split(",");
		int goalrowCoordinate = Integer.parseInt(goalcoordinates[0]);
		int goalcolCoordinate = Integer.parseInt(goalcoordinates[1]);

		currentMap[goalrowCoordinate][goalcolCoordinate] = 'o';

		Set<Predicate> predicates = knowledgeBase.getPredicates();
		for (Predicate predicate : predicates) {

			int rowCoordinate = 0;
			int colCoordinate = 0;
			String[] coordinates = predicate.getValue().split(",");
			if (!(predicate instanceof Clause)) {
				rowCoordinate = Integer.parseInt(coordinates[0]);
				colCoordinate = Integer.parseInt(coordinates[1]);
			}

			switch (predicate.getType()) {
			case ("WallAt"):
				currentMap[rowCoordinate][colCoordinate] = '#';
				break;

			case ("TargetAt"):
				if(currentMap[rowCoordinate][colCoordinate]!='@'  && currentMap[rowCoordinate][colCoordinate]!='#')
				if (predicate.getValue().equals(goal.getValue()))
					currentMap[rowCoordinate][colCoordinate] = 'o';
				else
					if(currentMap[rowCoordinate][colCoordinate]!='@' || currentMap[rowCoordinate][colCoordinate]!='#')
					currentMap[rowCoordinate][colCoordinate] = ' ';
				break;
			case ("PlayerAt"):
				currentMap[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])] = ' ';
				break;
			case ("BoxAt"):
				if (predicate.getId().equals(goal.getId()))
					currentMap[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])] = '@';
				else
					currentMap[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])] = '#';
				break;
			case ("And"):
				Clause c = null;
				if (predicate instanceof Clause)
					c = (Clause) predicate;
				Set<Predicate> innerPredicates = c.getPredicates();
				for (Predicate inner : innerPredicates) {

					String[] innerCoordinates = inner.getValue().split(",");

					if (inner.getType().equals("BoxAt")) {
						if (inner.getId().equals(goal.getId()))
							currentMap[Integer.parseInt(innerCoordinates[0])][Integer
									.parseInt(innerCoordinates[1])] = '*';
						else
							currentMap[Integer.parseInt(innerCoordinates[0])][Integer
									.parseInt(innerCoordinates[1])] = '#';
					} else if (inner.getType().equals("PlayerAt")) {
						if (inner.getValue().equals(goal.getValue()))
							if(currentMap[rowCoordinate][colCoordinate]!='@' || currentMap[rowCoordinate][colCoordinate]!='#')
							currentMap[Integer.parseInt(innerCoordinates[0])][Integer
									.parseInt(innerCoordinates[1])] = 'o';
						else
							if(currentMap[rowCoordinate][colCoordinate]!='@' || currentMap[rowCoordinate][colCoordinate]!='#')
							currentMap[Integer.parseInt(innerCoordinates[0])][Integer
									.parseInt(innerCoordinates[1])] = ' ';

					}

				}
				break;

			}

		}
		
		return new BoxToTargetSearchable(new State<SokoState>(new SokoState(currentMap)));
	}

	// There are some bugs here for sure
	private SokoSearchable generateSearchableForPlayerMovement(SokoPredicate goal) {

		char[][] currentMap = new char[initialState.getState().getMap().length][initialState.getState().getMap()[0].length];
		
		for(int i=0;i<currentMap.length-1;i++)
			for(int j=0;j<currentMap[i].length;j++)
				currentMap[i][j]=' ';

	
		
		String[] goalcoordinates = goal.getValue().split(",");
		int goalrowCoordinate = Integer.parseInt(goalcoordinates[0]);
		int goalcolCoordinate = Integer.parseInt(goalcoordinates[1]);

		currentMap[goalrowCoordinate][goalcolCoordinate] = 'o';
		
		Set<Predicate> predicates = knowledgeBase.getPredicates();
		for (Predicate predicate : predicates) {

			int rowCoordinate = 0;
			int colCoordinate = 0;
			String[] coordinates = predicate.getValue().split(",");
			if(!(predicate instanceof Clause)){
			rowCoordinate = Integer.parseInt(coordinates[0]);
			colCoordinate = Integer.parseInt(coordinates[1]);
			}

			switch (predicate.getType()) {
			case ("WallAt"):
				currentMap[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])] = '#';
				break;
			case ("TargetAt"):
				if(currentMap[rowCoordinate][colCoordinate]!='@' && currentMap[rowCoordinate][colCoordinate]!='#')
				if (!predicate.getValue().equals(goal.getValue()))
					currentMap[rowCoordinate][colCoordinate] = ' ';
				break;
			case ("PlayerAt"):
				if(currentMap[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]!='#')
				currentMap[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])] = '@';
				break;
			case ("BoxAt"):
				currentMap[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])] = '#';
				break;
			case ("And"):
				Clause c = (Clause) predicate;
				Set<Predicate> innerPredicates = c.getPredicates();
				for (Predicate inner : innerPredicates) {

					String[] innerCoordinates = inner.getValue().split(",");

					if (inner.getType().equals("BoxAt")) {
						currentMap[Integer.parseInt(innerCoordinates[0])][Integer.parseInt(innerCoordinates[1])] = '#';
					} else if (inner.getType().equals("PlayerAt")) {
						if(currentMap[Integer.parseInt(innerCoordinates[0])][Integer.parseInt(innerCoordinates[1])]!='#')
						currentMap[Integer.parseInt(innerCoordinates[0])][Integer.parseInt(innerCoordinates[1])] = '@';
					}

				}
				break;

			}

		}

		return new PlayerToTargetSearchable(new State<SokoState>(new SokoState(currentMap)));
	}
}
