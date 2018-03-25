package model.data.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import model.data.levels.SokobanLevel;

public class ObjLevelLoader implements LevelLoader {

	@Override
	public SokobanLevel loadLevel(InputStream istream) throws IOException,ClassNotFoundException{
		SokobanLevel newLevel;
		ObjectInputStream objInputStream = new ObjectInputStream(istream);
		newLevel = (SokobanLevel)objInputStream.readObject();
		
		return newLevel;
	}
	
	

}
