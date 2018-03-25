package model.data.savers;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import model.data.levels.SokobanLevel;

public class ObjLevelSaver implements LevelSaver {

	@Override
	public void saveLevel(SokobanLevel level, OutputStream ostream) throws IOException {
		if (ostream != null && level != null) {
			ObjectOutputStream objOutputStream = new ObjectOutputStream(new BufferedOutputStream(ostream));
			objOutputStream.writeObject(level);
			objOutputStream.close();
		}

	}

}
