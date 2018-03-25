package view.leaderboard;

import commons.DBScore;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LeaderboardRow {
	
    private  StringProperty userName;
    private  StringProperty levelName;
    private  IntegerProperty stepsTaken;
    private  IntegerProperty finalTime;

    
    public LeaderboardRow() {
    
	}
    
    public LeaderboardRow(DBScore score){
    	
    	
    	this.userName = new SimpleStringProperty(score.getUserName());
    	this.levelName =  new SimpleStringProperty(score.getLevelName());
    	this.stepsTaken =  new SimpleIntegerProperty(score.getStepsTaken());
    	this.finalTime =  new SimpleIntegerProperty(score.getFinalTime());
    	
    	
    }
    public LeaderboardRow(String userName,String levelName, int stepsTaken, int finalTime ) {
		
    	this.userName = new SimpleStringProperty(userName);
    	this.levelName = new SimpleStringProperty(levelName);
    	this.stepsTaken = new SimpleIntegerProperty(stepsTaken);
    	this.finalTime = new SimpleIntegerProperty(finalTime);
	}

	public StringProperty getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName.set(userName);
	}

	public StringProperty getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName.set(levelName);
	}

	public IntegerProperty getStepsTaken() {
		return stepsTaken;
	}

	public void setStepsTaken(Integer stepsTaken) {
		this.stepsTaken.set(stepsTaken);
	}

	public IntegerProperty getFinalTime() {
		return finalTime;
	}

	public void setFinalTime(Integer finalTime) {
		this.finalTime.set(finalTime);
	}
    
    
    

}
