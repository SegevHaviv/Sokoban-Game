package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import commons.CommonLevel;
import commons.DBScore;
import commons.Direction;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.data.levels.SokobanLevel;
import model.data.loaders.LevelLoader;
import model.data.loaders.LevelLoaderFactory;
import model.data.policies.SokobanPolicy;
import model.data.savers.LevelSaver;
import model.data.savers.LevelSaverFactory;
import model.data.utils.CellsToSymbolsConverter;
import model.db.DBLevel;
import model.db.DBUser;
import model.db.ScoresDBManager;

public class SokobanModel extends Observable implements Model {

	private SokobanLevel level;
	private SokobanPolicy policy;
	private Timer timer;
	private boolean timeRunning = false;
	private boolean declaredSolved = false;

	private StringProperty stepsTakenProperty;
	private StringProperty elapsedTimeProperty;
	private StringProperty levelNameProperty;

	private ScoresDBManager dbManager;

	int solutionServerPort;
	String solutionServerIP;

	ArrayList<String> solution;

	public SokobanModel(int solutionServerPort, String solutionServerIP) {

		this.solutionServerPort = solutionServerPort;
		this.solutionServerIP = solutionServerIP;
		policy = new SokobanPolicy();

		initializeProperties();

	}

	private void initializeProperties() {

		stepsTakenProperty = new SimpleStringProperty();
		elapsedTimeProperty = new SimpleStringProperty();
		levelNameProperty = new SimpleStringProperty();

	}

	@Override
	public void loadLevel(String filePath) {
		stopTimer();
		solution=null;

		String extension = parseExtension(filePath);

		LevelLoaderFactory loaderFactory = new LevelLoaderFactory();

		LevelLoader loader = loaderFactory.CreateLoader(extension);

		try {

			level = loader.loadLevel(new FileInputStream(filePath));

			policy.setLevel(level);
			updateStringProperties();
			declaredSolved = false;
			LinkedList<String> displayParams = new LinkedList<>();
			displayParams.add("DISPLAY");
			setChanged();
			notifyObservers(displayParams);
		} catch (Exception e) {
			LinkedList<String> displayParams = new LinkedList<>();
			displayParams.add("DISPLAYMESSAGE");
			displayParams.add("Error loading file");
			setChanged();
			notifyObservers(displayParams);
		}

	}

	private void updateStringProperties() {

		stepsTakenProperty.set(level.getStepsTaken().toString());
		elapsedTimeProperty.set(level.getElapsedTime().toString());
		levelNameProperty.set(level.getName());

	}

	@Override
	public void saveLevel(String filePath) {
		String extension = parseExtension(filePath);

		LevelSaverFactory saverFactory = new LevelSaverFactory();

		LevelSaver saver = saverFactory.CreateSaver(extension);

		try {
			saver.saveLevel(level, new FileOutputStream(filePath));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String parseExtension(String filePath) {

		int dotIndex = filePath.lastIndexOf('.');
		String extension = filePath.substring(dotIndex + 1);

		return extension;

	}

	private boolean isValidDirection(String direction) {

		for (Direction validDirection : Direction.values()) {

			if (direction.equals(validDirection.name()))
				return true;

		}

		return false;

	}

	@Override
	public void moveHero(int heroIndex, String direction) {
		if (level == null)
			return;

		if (!isValidDirection(direction.toUpperCase()))
			return;

		Direction dir = Direction.valueOf(direction.toUpperCase());

		if (policy.isLegal(heroIndex, dir)) {

			level.moveHero(heroIndex, dir);
			int stepsTaken = level.getStepsTaken();
			level.setStepsTaken(stepsTaken + 1);
			stepsTakenProperty.set(level.getStepsTaken().toString());
		}

		if (!timeRunning && !declaredSolved)
			startTimer();

		if (level.isSolved() && !declaredSolved) {
			stopTimer();
			//User didn't ask for solution
			if(solution==null){
			LinkedList<String> displayParams = new LinkedList<>();
			displayParams.add("DECLARESOLVED");
			setChanged();
			notifyObservers(displayParams);
			declaredSolved = true;
			}

		}

		LinkedList<String> displayParams = new LinkedList<>();
		displayParams.add("DISPLAY");
		setChanged();
		notifyObservers(displayParams);
	}

	private void startTimer() {

		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				level.setElapsedTime(level.getElapsedTime() + 1);
				elapsedTimeProperty.set(level.getElapsedTime().toString());

			};

		}, 0, 1000);

		timeRunning = true;
	}

	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timeRunning = false;
		}

	}

	@Override
	public int getElapsedTime() {
		return level.getElapsedTime();
	}

	@Override
	public int getStepsTaken() {
		return level.getStepsTaken();
	}

	@Override
	public CommonLevel getCommonLevel() {

		String name = level.getName();
		int stepsTaken = level.getStepsTaken();
		int elapsedTime = level.getElapsedTime();
		ArrayList<ArrayList<Character>> map;

		CellsToSymbolsConverter converter = new CellsToSymbolsConverter();

		map = converter.convert(level.getMap());

		return new CommonLevel(name, stepsTaken, elapsedTime, map);
	}

	@Override
	public StringProperty getStepsTakenProperty() {
		return stepsTakenProperty;
	}

	@Override
	public StringProperty getElapsedTimeProperty() {
		return elapsedTimeProperty;
	}

	@Override
	public StringProperty getLevelNamePropertyProperty() {
		return levelNameProperty;
	}

	@Override
	public void saveScoreToDB(String userName) {

		if (dbManager == null)
			dbManager = new ScoresDBManager();

		DBUser userToSave = new DBUser(userName);
		dbManager.addUser(userToSave);

		DBLevel levelToSave = new DBLevel(level.getName());

		dbManager.addLevel(levelToSave);

		dbManager.addScore(userToSave, levelToSave, level.getStepsTaken(), level.getElapsedTime());

	}

	@Override
	public void stop() {
		stopTimer();

		if (dbManager != null)
			dbManager.getFactory().close();

	}

	@Override
	public List<DBScore> getLeaderboard(String userName, String levelName, String orderBy, int firstIndex,
			int maxResults) {
		if (dbManager == null)
			dbManager = new ScoresDBManager();

		if (this.level != null && levelName.equals("CurrentLevel"))
			levelName = level.getName();

		else if (levelName.isEmpty())
			levelName = null;

		List<DBScore> leaderboard = dbManager.getScores(levelName, userName, orderBy, firstIndex, maxResults);

		return leaderboard;
	}

	
	private void requestForSolution() throws UnknownHostException, IOException {
		if(level.isSolved()){
			notifyAlreadySolved();
			return;
		}
			
		Socket theServer = new Socket(solutionServerIP, solutionServerPort);
		PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
		outToServer.println(compressLevelToString());
		outToServer.flush();
		
		
		String result = inFromServer.readLine();
		if(result.isEmpty() || result.equals("NONE")){
			LinkedList<String> displayParams = new LinkedList<>();
			displayParams.add("DISPLAYMESSAGE");
			displayParams.add("Could not find solution for this level");
			setChanged();
			notifyObservers(displayParams);
		}
			
		
		solution = parseResultString(result);
		
		theServer.close();
		outToServer.close();
		inFromServer.close();

	}

	private void notifyAlreadySolved(){
		LinkedList<String> displayParams = new LinkedList<>();
		displayParams.add("DISPLAYMESSAGE");
		if(level.isSolved())
		displayParams.add("Level is already solved");
		setChanged();
		notifyObservers(displayParams);
		
		
	}
	private void notifyServerError() {
		LinkedList<String> displayParams = new LinkedList<>();
		displayParams.add("DISPLAYMESSAGE");
		displayParams.add("Error occurred while connecting to server");
		setChanged();
		notifyObservers(displayParams);

	}
	
	private void notifyError() {
		LinkedList<String> displayParams = new LinkedList<>();
		displayParams.add("DISPLAYMESSAGE");
		displayParams.add("Error occurred");
		setChanged();
		notifyObservers(displayParams);

	}

	private String compressLevelToString() {

		CellsToSymbolsConverter converter = new CellsToSymbolsConverter();

		ArrayList<ArrayList<Character>> map = converter.convert(level.getMap());

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < map.size(); i++) {

			for (int j = 0; j < map.get(i).size(); j++) {

				builder.append(map.get(i).get(j));

			}

			builder.append(',');

		}

		return builder.toString();

	}


	@Override
	public void solve() {
		if(solution == null || solution.isEmpty()){
			try {
				requestForSolution();
			}catch(Exception e){
				notifyServerError();
				return;
			}
		}
		
		executeSolution();

	}
	
	private void executeSolution(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!solution.isEmpty()){
					try {
						Thread.sleep(500);
						moveHero(0, solution.remove(0));
						
					} catch (InterruptedException e) {
						
						notifyError();
					}
				
			}
		}}).start();
		
	}

	@Override
	public void hint() {
		if(solution == null || solution.isEmpty()){
			try {
				requestForSolution();
			}catch(Exception e){
				notifyServerError();
				return;
			}	
		}
		if(!solution.isEmpty())
		moveHero(0, solution.remove(0));
	
	}
	
	public ArrayList<String> parseResultString(String solution){
		

		ArrayList<String> result = new ArrayList<>();
		String[] parsed = solution.replaceAll(" ","").trim().split("Move");
		

		for(int i=1;i<parsed.length;i++){
			result.add(parsed[i]);
		}

		
		return result;
		
	}

}
