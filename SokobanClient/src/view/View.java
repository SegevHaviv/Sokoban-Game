package view;

import java.util.List;

import commons.CommonLevel;
import commons.DBScore;


public interface View{
	
	public void start();
	
	public void displayLevel(CommonLevel level);
	
	public void displayMessage(String message);
	
	public void DisplayLeaderboard(List<DBScore> leaderboard,String userName, String levelName, String orderBy, int firstIndex,int amountOfRows);
	
	public void updateLeaderboard(List<DBScore> leaderboard,String userName, String levelName, String orderBy, int firstIndex,int amountOfRows);
	
	public void declareSolved();
	
	public void stop();
	

}
