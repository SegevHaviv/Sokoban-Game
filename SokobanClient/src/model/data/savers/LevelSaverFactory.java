package model.data.savers;

import java.util.HashMap;

public class LevelSaverFactory {


	public LevelSaverFactory(){
		extensionToCreator=new HashMap<String,Creator>();
		extensionToCreator.put("txt", new TextSaverCreator());
		extensionToCreator.put("xml", new XMLSaverCreator());
		extensionToCreator.put("obj", new ObjSaverCreator());
	}
	
	private HashMap<String,Creator> extensionToCreator;

	private interface Creator {
		public LevelSaver create();
	}

	private class TextSaverCreator implements Creator {
		@Override
		public TextLevelSaver create() {	return new TextLevelSaver();	}
	}
	

	private class XMLSaverCreator implements Creator {
		@Override
		public XmlLevelSaver create() { return new XmlLevelSaver();	}
	}
	
	private class ObjSaverCreator implements Creator {
		@Override
		public ObjLevelSaver create() { return new ObjLevelSaver(); }
	}
	
	public LevelSaver CreateSaver(String extension){
		Creator saverCreator = extensionToCreator.get(extension);
		if(saverCreator!=null) return saverCreator.create();
		return null;
	}
}
