package model.data.loaders;

import java.util.HashMap;

/**
 * Produces an appropriate level loader for specific type of files.
 * @param hmap hash map to generate loader creators given a file extension.
 */

public class LevelLoaderFactory {

		
		private HashMap<String,Creator> extensionToCreator;
		
		/**
		 * Class constructor.
		 */
		public LevelLoaderFactory(){
			extensionToCreator=new HashMap<String,Creator>();
			extensionToCreator.put("txt", new TextLoaderCreator());
			extensionToCreator.put("xml", new XMLLoaderCreator());
			extensionToCreator.put("obj", new ObjLoaderCreator());	
		}
		
		/**
		 * Defines the behavior of a creator.
		 */
		private interface Creator {

			public LevelLoader create();
		}
		
		/**Creator of Text level loaders.
		 *
		 */
		private class TextLoaderCreator implements Creator {
			/**
			Creates Text level loader.
			@return New Text Level Loader.
			 */
			@Override
			public TextLevelLoader create() {
				return new TextLevelLoader();
			}
		}

		/**Creator of XML level loaders.
		 *
		 */
		private class XMLLoaderCreator implements Creator {

			/**
			Creates	XML level loader.
			 */
			@Override
			public XmlLevelLoader create() {
				return new XmlLevelLoader();
			}
		}
		
		/**Creator of object level loaders.
		 *
		 */
		private class ObjLoaderCreator implements Creator {

			/**
			Creates	object level loader.
			 */
			@Override
			public ObjLevelLoader create() {
				return new ObjLevelLoader();
			}
		}
		
		/**
		Given a file extension, creates an appropriate level loader for specific type of files.
		@param extension File extension 
		@return Level Loader for the specific type of files
		 */
		public LevelLoader CreateLoader(String extension){
			Creator loaderCreator = extensionToCreator.get(extension);	
			if(loaderCreator!=null) return loaderCreator.create();
			return null;
		}
	}