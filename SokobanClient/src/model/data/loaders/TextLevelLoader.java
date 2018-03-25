package model.data.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.data.elements.Cell;
import model.data.levels.SokobanLevel;

public class TextLevelLoader implements LevelLoader {
	
	BufferedReader reader;

	@Override
	public SokobanLevel loadLevel(InputStream istream) throws IOException{
		
		reader = new BufferedReader(new InputStreamReader(istream));
		
		//Name:
		reader.readLine();
		String name = reader.readLine();
		//Steps Taken:
		reader.readLine();
		int stepsTaken = Integer.parseInt(reader.readLine());
		//Elapsed Time:
		reader.readLine();
		int elapsedTime = Integer.parseInt(reader.readLine());
		
		ArrayList<ArrayList<Cell>> map = readLevelMap();
		
		return new SokobanLevel(name,stepsTaken,elapsedTime,map);
		
		
	}
	
	
	private ArrayList<ArrayList<Cell>> readLevelMap() throws IOException{
		
		ArrayList<ArrayList<Cell>> map = new ArrayList<>();
		String line = null;
		TextToCellFactory textToCellFactory = new TextToCellFactory();
		
		int row = 0;
		
		while((line = reader.readLine()) != null){
			map.add(new ArrayList<Cell>());
			for(int i=0;i<line.length();i++){
				
				char textSymbol = line.charAt(i);
				Cell newCell = textToCellFactory.CreateCell(textSymbol);
				
				map.get(row).add(newCell);
				
				
			}
			row++;
		}
		
		
		return map;
		
	}

}
