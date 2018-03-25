package model;

import java.util.List;

import commons.CommonLevel;
import commons.DBScore;
import javafx.beans.property.StringProperty;

public interface Model {

	
	public void loadLevel(String filePath);
	
	public void saveLevel(String filePath);
	
	public void moveHero(int heroIndex, String direction);
	
	public int getElapsedTime();
	
	public int getStepsTaken();
	
	public CommonLevel getCommonLevel();

	public StringProperty getStepsTakenProperty();

	public StringProperty getElapsedTimeProperty();
	
	public StringProperty getLevelNamePropertyProperty();
	
	public void saveScoreToDB(String userName);
	
	public List<DBScore> getLeaderboard(String userName,String levelName, String orderBy, int firstIndex, int maxResults);
	
	public void solve();
	
	public void hint();
	
	public void stop();
	

}
