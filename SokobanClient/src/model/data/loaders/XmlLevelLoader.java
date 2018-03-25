package model.data.loaders;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.InputStream;

import model.data.levels.SokobanLevel;

public class XmlLevelLoader implements LevelLoader {

	@Override
	public SokobanLevel loadLevel(InputStream istream) {
		
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(istream));
		SokobanLevel newLevel = (SokobanLevel)decoder.readObject();
		decoder.close();
		return newLevel;
	}

}
