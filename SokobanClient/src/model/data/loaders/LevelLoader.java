package model.data.loaders;

import java.io.InputStream;

import model.data.levels.SokobanLevel;

public interface LevelLoader {
	
	public SokobanLevel loadLevel(InputStream stream) throws Exception;

}
