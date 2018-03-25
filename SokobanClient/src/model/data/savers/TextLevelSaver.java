package model.data.savers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import model.data.levels.SokobanLevel;
import model.data.utils.CellsToSymbolsConverter;

public class TextLevelSaver implements LevelSaver {

	PrintWriter writer;
	Map<SokobanElement, Character> elementToSymbol;

	public TextLevelSaver() {
		this.writer = null;
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

	@Override
	public void saveLevel(SokobanLevel level, OutputStream ostream) throws IOException {

		if (level != null) {

			writer = new PrintWriter(ostream);

			saveHeader(level);

			saveLevelMap(level);

			writer.flush();
		}

	}

	private void saveLevelMap(SokobanLevel level) {

		ArrayList<ArrayList<Cell>> map = level.getMap();

		CellsToSymbolsConverter converter = new CellsToSymbolsConverter();

		ArrayList<ArrayList<Character>> symbolMap = converter.convert(map);

		for (ArrayList<Character> row : symbolMap) {

			for (Character symbol : row) {

				writer.print(symbol.charValue());
			}

			writer.println();

		}

	}

	private void saveHeader(SokobanLevel level) {

		String name = level.getName();
		int stepsTaken = level.getStepsTaken();
		int elapsedTime = level.getElapsedTime();

		writer.println("Name:");
		writer.println(name);
		writer.println("Steps Taken:");
		writer.println(stepsTaken);
		writer.println("Elapsed Time:");
		writer.println(elapsedTime);

	}

}
