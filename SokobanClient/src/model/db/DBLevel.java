package model.db;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name="Levels")
public class DBLevel {
	
	@Id
	private String levelName;
	
	
	
	public DBLevel() {
		levelName=null;
	}
	
	public DBLevel(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((levelName == null) ? 0 : levelName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBLevel other = (DBLevel) obj;
		if (levelName == null) {
			if (other.levelName != null)
				return false;
		} else if (!levelName.equals(other.levelName))
			return false;
		return true;
	}
	
	
	

}
