package view.displayers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import commons.CommonLevel;
import commons.Direction;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class GraphicDisplayer extends Canvas implements Displayer {

	@FXML
	private StringProperty wallImgPath;
	@FXML
	private StringProperty boxImgPath;

	@FXML
	private StringProperty heroUpImgPath;
	@FXML
	private StringProperty heroDownImgPath;
	@FXML
	private StringProperty heroLeftImgPath;
	@FXML
	private StringProperty heroRightImgPath;

	
	@FXML
	private StringProperty targetImgPath;
	@FXML
	private StringProperty backgroundImgPath;
	
	private Image backgroundImg;
	
	
	private Map<Direction,Image> heroSprites; 
	private Map<Character,Image> imgMap;
	
	boolean imgInitialized = false;


	private CommonLevel level;
	public GraphicDisplayer() {
		initProperties();
		imgMap = new HashMap<Character, Image>();
		heroSprites = new HashMap<Direction,Image>();
	

	}

	private void initProperties(){
		backgroundImgPath = new SimpleStringProperty();
		wallImgPath = new SimpleStringProperty();
		boxImgPath = new SimpleStringProperty();
		heroUpImgPath = new SimpleStringProperty();
		heroDownImgPath = new SimpleStringProperty();
		heroLeftImgPath = new SimpleStringProperty();
		heroRightImgPath = new SimpleStringProperty();
		targetImgPath = new SimpleStringProperty();
		
	}
	private void initImages() {
		
		try {
		backgroundImg = new Image(new FileInputStream(this.backgroundImgPath.get()));
		
		Image wallImg = new Image(new FileInputStream(wallImgPath.get()));
		Image boxImg = new Image(new FileInputStream(boxImgPath.get()));
		Image heroImg = new Image(new FileInputStream(heroDownImgPath.get()));
		Image targetImg = new Image(new FileInputStream(this.targetImgPath.get()));
		
		imgMap.put('#', wallImg);
		imgMap.put('@', boxImg);
		imgMap.put('*', boxImg);
		imgMap.put('A', heroImg);
		imgMap.put('a', heroImg);
		imgMap.put('o', targetImg);
		
		heroSprites.put(Direction.UP, new Image(new FileInputStream(heroUpImgPath.get())));
		heroSprites.put(Direction.DOWN, new Image(new FileInputStream(heroDownImgPath.get())));
		heroSprites.put(Direction.LEFT, new Image(new FileInputStream(heroLeftImgPath.get())));
		heroSprites.put(Direction.RIGHT, new Image(new FileInputStream(heroRightImgPath.get())));
		imgInitialized = true;
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void changeHeroDirection(Direction direction){
		
		Image heroImg = heroSprites.get(direction);
		imgMap.put('A', heroImg);
		imgMap.put('a', heroImg);
		
		
		
	}
	/* GETTERS */
	
	public String getBackgroundImgPath() {
		return backgroundImgPath.get();
	}
	public String getWallImgPath() {
		return wallImgPath.get();
	}

	public String getBoxImgPath() {
		return boxImgPath.get();
	}



	public String getTargetImgPath() {
		return targetImgPath.get();
	}
	
	public String getHeroUpImgPath() {
		return heroUpImgPath.get();
	}
	public String getHeroDownImgPath() {
		return heroDownImgPath.get();
	}

	public String getHeroLeftImgPath() {
		return heroLeftImgPath.get();
	}
	public String getHeroRightImgPath() {
		return heroRightImgPath.get();
	}
	/* SETTERS */
	
	public void setBackgroundImgPath(String backgroundImgPath) {
		this.backgroundImgPath.set(backgroundImgPath);
	}



	public void setHeroUpImgPath(String heroUpImgPath) {
		this.heroUpImgPath.set(heroUpImgPath);
	}

	public void setHeroDownImgPath(String heroDownImgPath) {
		this.heroDownImgPath.set(heroDownImgPath);
	}

	public void setHeroLeftImgPath(String heroLeftImgPath) {
		this.heroLeftImgPath.set(heroLeftImgPath);
	}

	public void setHeroRightImgPath(String heroRightImgPath) {
		this.heroRightImgPath.set(heroRightImgPath);
	}

	public void setWallImgPath(String wallImgPath) {
		this.wallImgPath.set(wallImgPath);
	}

	public void setBoxImgPath(String boxImgPath) {
		this.boxImgPath.set(boxImgPath);
	}


	public void setTargetImgPath(String targetImgPath) {
		this.targetImgPath.set(targetImgPath);

	}
	
	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public void displayLevel(CommonLevel level) {
	
		this.level = level;
		redraw();
	

	}
	
	public void redraw(){
		
		if(level==null)
			return;
		
		if(!imgInitialized)
			initImages();
		
		
		ArrayList<ArrayList<Character>> map = level.getMap();
		double canvasWidth = getWidth();
		double canvasHeight = getHeight();
		double cellWidth, cellHeight;

		
		cellWidth = canvasWidth / (getMaxRowSize(map));
		cellHeight = canvasHeight / (map.size());

		GraphicsContext gc = getGraphicsContext2D();

		gc.clearRect(0, 0, canvasWidth, canvasHeight);

		gc.drawImage(backgroundImg,0,0,canvasWidth,canvasHeight);

		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).size(); j++) {
				Character currentSymbol = map.get(i).get(j);
				Image elementImg = imgMap.get(currentSymbol);
				if(elementImg!=null)
				gc.drawImage(elementImg, j * cellWidth, i * cellHeight, cellWidth, cellHeight);

			}
		}
		
		
		
	}

	
	private int getMaxRowSize(ArrayList<ArrayList<Character>> map){
		
		int max = 0;
		for(ArrayList<Character> row : map){
			
			if(row.size()>max)
				max = row.size();
			
		}
		
		
		return max;
		
	}
	@Override
	public void displayMessage(String message) {
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Message");
				alert.setHeaderText(null);
				alert.setContentText(message);
				alert.showAndWait();
			}
		});
		

	}

	@Override
	public void printInstructions() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("About");
				alert.setHeaderText(null);
				alert.setContentText(
						"Welcome to Sokomon!\n" + "The goal of the game is to place all to balls on the targets.\n"
								+ "Notice that you can only push one ball at a time.\n" + "Good luck!");
				alert.showAndWait();
			}
		});

	}



}
