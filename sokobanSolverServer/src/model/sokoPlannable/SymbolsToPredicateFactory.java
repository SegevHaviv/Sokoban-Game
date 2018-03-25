package model.sokoPlannable;

import java.util.HashMap;
import java.util.Map;

import model.predicats.SokoPredicate;
import predicates.Clause;
import predicates.Predicate;

public class SymbolsToPredicateFactory {
	
	int boxCounter = 0;
	int targetCounter = 0;
	
	Map<Character,Creator> predicatesCreator;
	
	public SymbolsToPredicateFactory() {

		predicatesCreator = new HashMap<Character, Creator>();
		predicatesCreator.put('@', new BoxAtCreator());
		predicatesCreator.put('#', new WallAtCreator());
		predicatesCreator.put('o', new TargetAtCreator());
		predicatesCreator.put('*', new BoxOnTargetCreator());
		predicatesCreator.put('a', new PlayerOnTargetCreator());
		predicatesCreator.put('A', new PlayerAtCreator());

	}
	
	private interface Creator {
		public Predicate create(int i, int j);
	}
	
	private class BoxAtCreator implements Creator{

		@Override
		public Predicate create(int i, int j) {
			
			 Predicate p = new SokoPredicate("BoxAt","b"+boxCounter,""+i+","+j);
			 boxCounter++;
			 return p;
		}
		
	}
	private class WallAtCreator implements Creator{

		@Override
		public Predicate create(int i, int j) {
			return new SokoPredicate("WallAt","",""+i+","+j);
		}
		
	}
	
	private class TargetAtCreator implements Creator{

		@Override
		public Predicate create(int i, int j) {
			Predicate p = new SokoPredicate("TargetAt","t"+targetCounter,""+i+","+j);
			targetCounter++;
			return p;
		}
		
	}
	private class BoxOnTargetCreator implements Creator{

		@Override
		public Predicate create(int i, int j) {
			Clause c = new Clause(new SokoPredicate("BoxAt","b"+boxCounter,i+","+j),new SokoPredicate("TargetAt","t"+targetCounter,i+","+j));
			boxCounter++;
			targetCounter++;
			
			return c;
		}
	}
	
	private class PlayerOnTargetCreator implements Creator{

		@Override
		public Predicate create(int i, int j) {
			Clause c = new Clause(new SokoPredicate("PlayerAt","",i+","+j),new SokoPredicate("TargetAt","t"+targetCounter,i+","+j));
			targetCounter++;
			
			return c;
		}
	}
	
	private class PlayerAtCreator implements Creator{

		@Override
		public Predicate create(int i, int j) {
			return new SokoPredicate("PlayerAt","",""+i+","+j);
		}
		
		
		
	}
	
	
	
	public Predicate CreatePredicate(Character key, int i, int j) {
		Creator preCreator = predicatesCreator.get(key);
		if (preCreator != null)
			return preCreator.create(i, j);
		return null;
	}

	

}
