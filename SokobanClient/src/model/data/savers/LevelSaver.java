package model.data.savers;

import java.io.OutputStream;

import model.data.levels.SokobanLevel;

public interface LevelSaver {
	
	public void saveLevel(SokobanLevel level,OutputStream ostream) throws Exception;
	
}
