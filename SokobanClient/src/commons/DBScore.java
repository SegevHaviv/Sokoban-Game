package commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="Scores")
public class DBScore {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="userName")
	private String userName;
	@Column(name="levelName")
	private String levelName;
	
	@Column(name="stepsTaken")
	private int stepsTaken;
	@Column(name="finalTime")
	private int finalTime;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public DBScore() {
	
	}
	
	public DBScore(String userName,String levelName,int stepsTaken,int finalTime) {
		
		this.userName=userName;
		this.levelName=levelName;
		this.stepsTaken=stepsTaken;
		this.finalTime=finalTime;
	}
	
	public String getUserName(){
		
		return this.userName;
		
	}
	
	public String getLevelName(){
		
		return this.levelName;
		
	}
	
	public int getStepsTaken() {
		return stepsTaken;
	}
	public void setStepsTaken(int stepsTaken) {
		this.stepsTaken = stepsTaken;
	}
	public int getFinalTime() {
		return finalTime;
	}
	public void setFinalTime(int finalTime) {
		this.finalTime = finalTime;
	}

}
