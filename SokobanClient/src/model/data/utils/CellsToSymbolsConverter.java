package model.data.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.data.elements.Box;
import model.data.elements.Cell;
import model.data.elements.Floor;
import model.data.elements.Hero;
import model.data.elements.SokobanElement;
import model.data.elements.Target;
import model.data.elements.Wall;

public class CellsToSymbolsConverter {

	Map<SokobanElement, Character> elementToSymbol;

	public CellsToSymbolsConverter() {

		initCellToSymbol();

	}

	private void initCellToSymbol() {

		elementToSymbol = new HashMap<>();

		elementToSymbol.put(new Floor(), ' ');
		elementToSymbol.put(new Target(), 'o');
		elementToSymbol.put(new Wall(), '#');
		elementToSymbol.put(new Floor(new Hero()), 'A');
		elementToSymbol.put(new Floor(new Box()), '@');
		elementToSymbol.put(new Target(new Hero()), 'a');
		elementToSymbol.put(new Target(new Box()), '*');

	}

	public ArrayList<ArrayList<Character>> convert(ArrayList<ArrayList<Cell>> map) {

		ArrayList<ArrayList<Character>> symbolMap = new ArrayList<>();
		int rowNumber = 0;
		for (ArrayList<Cell> row : map) {
			symbolMap.add(new ArrayList<Character>());
			for (Cell cell : row) {
				SokobanElement elementOnCell = cell.getElementOnCell();
				Character symbol = elementToSymbol.get(elementOnCell);
				symbolMap.get(rowNumber).add(symbol);

			}
			rowNumber++;

		}

		return symbolMap;

	}
}
