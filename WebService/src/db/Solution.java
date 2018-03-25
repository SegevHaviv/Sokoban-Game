package db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity (name="Solution")
public class Solution implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LevelMap")
	private String levelMap;
	
	@Column(name = "Actions")
	private String actions;
	
	public Solution() {}

	public Solution(String map, String actions) {
		this.levelMap = map;
		this.actions = actions;
	}

	public String getLevelMap() {
		return levelMap;
	}

	public void setLevelMap(String levelMap) {
		this.levelMap = levelMap;
	}

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}
}
