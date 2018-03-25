package common;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserTableRow {
	
	private StringProperty ip;
	
	private IntegerProperty port;
	
	
	public UserTableRow() {}

	public UserTableRow(String ip, int port) {
		this.ip = new SimpleStringProperty(ip);
		this.port = new SimpleIntegerProperty(port);
	}

	public StringProperty getIp() {
		return ip;
	}

	public void setIp(StringProperty ip) {
		this.ip = ip;
	}

	public IntegerProperty getPort() {
		return port;
	}

	public void setPort(IntegerProperty port) {
		this.port = port;
	}
		
}
