package model.data.savers;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import model.data.levels.SokobanLevel;

public class XmlLevelSaver implements LevelSaver {
	

	@Override
	public void saveLevel( SokobanLevel level,OutputStream ostream) throws IOException {
		if (ostream != null && level != null) {
			XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(ostream));
			xmlEncoder.writeObject(level);
			xmlEncoder.close();
		}

	}

}
