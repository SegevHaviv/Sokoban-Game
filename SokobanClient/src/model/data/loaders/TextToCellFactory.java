package model.data.loaders;

import java.util.HashMap;

import model.data.elements.Box;
import model.data.elements.Cell;
import model.data.elements.Floor;
import model.data.elements.Hero;
import model.data.elements.Target;
import model.data.elements.Wall;

public class TextToCellFactory {

	private HashMap<Character, Creator> symbolToCreator;
	
	
	public TextToCellFactory() {
		symbolToCreator = new HashMap<>();
		
		symbolToCreator.put(' ', new FloorOnCellCreator());
		symbolToCreator.put('o', new TargetOnCellCreator());
		symbolToCreator.put('#', new WallOnCellCreator());
		symbolToCreator.put('@', new BoxOnFloorCellCreator());
		symbolToCreator.put('A', new HeroOnFloorCellCreator());
		symbolToCreator.put('*', new BoxOnTargetCellCreator());
		symbolToCreator.put('a', new HeroOnTargetCellCreator());
	
	}

	private interface Creator {

		public Cell create();
	}

	private class FloorOnCellCreator implements Creator {

		@Override
		public Cell create() {
			return new Cell(new Floor(null));
		}

	}

	private class TargetOnCellCreator implements Creator {

		@Override
		public Cell create() {
			return new Cell(new Target(null));
		}
	}
	
	private class WallOnCellCreator implements Creator {

		@Override
		public Cell create() {
			return new Cell(new Wall());
		}

	}

	private class BoxOnFloorCellCreator implements Creator {

		@Override
		public Cell create() {
			return new Cell(new Floor(new Box()));
		}

	}

	private class HeroOnFloorCellCreator implements Creator {

		@Override
		public Cell create() {
			return new Cell(new Floor(new Hero()));
		}

	}
	
	private class BoxOnTargetCellCreator implements Creator {

		@Override
		public Cell create() {
			return new Cell(new Target(new Box()));
		}

	}
	
	private class HeroOnTargetCellCreator implements Creator {

		@Override
		public Cell create() {
			return new Cell(new Target(new Hero()));
		}

	}
	
	public Cell CreateCell(Character symbol){
		
		Creator creator = symbolToCreator.get(symbol);
		
		return creator.create();
		
		
	}

}
