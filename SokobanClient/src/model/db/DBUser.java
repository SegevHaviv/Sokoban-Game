package model.db;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity (name="Users")
public class DBUser {
	
	@Id
	private String userName;

	public DBUser() {
		userName = null;
	}
	
	public DBUser(String userName) {
		this.userName=userName; 
	}
	
	public String getUsername() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		DBUser other = (DBUser) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [username=" + userName + "]";
	}


	

}
