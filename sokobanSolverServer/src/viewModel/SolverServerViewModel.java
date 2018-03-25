package viewModel;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import common.UserTableRow;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;

public class SolverServerViewModel implements Observer{

	private StringProperty port;
	private Model model;
	private IntegerProperty numOfConnectedUsers;
	private IntegerProperty userAmountThreshold;
	private ObservableList<UserTableRow> usersData;
	
	
	public SolverServerViewModel(Model model) {
		usersData = FXCollections.observableArrayList();
		numOfConnectedUsers = new SimpleIntegerProperty(0);
		userAmountThreshold = new SimpleIntegerProperty(Integer.MAX_VALUE);
		port = new SimpleStringProperty("");
		this.model = model;
	}
	
	public void stopServer(){
		
		model.stopServer();
		port.set("");
		userAmountThreshold.set(Integer.MAX_VALUE);
		
	}
	
	public void startServer(String port){

		
		int portNum;
		
		try {
			portNum = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			return;
		}
		
		if(portNum<0 || portNum>65535)
			return;
		
		this.port.set(port);
		model.startServer(portNum);
		
		
		userAmountThreshold.set(Integer.MAX_VALUE);
		
	}
	

	public StringProperty getPort() {
		return port;
	}


	
	public IntegerProperty getUserAmountThreshold() {
		return userAmountThreshold;
	}

	public ObservableList<UserTableRow> getUsersData() {
		return usersData;
	}

	@Override
	public void update(Observable o, Object arg) {
		
		@SuppressWarnings("unchecked")
		LinkedList<String> params = (LinkedList<String>) arg;
		String commandKey = params.removeFirst();
		
		if(commandKey == "USERCONNECTED"){
			numOfConnectedUsers.set(model.getNumOfUsersHandled());
			String ip = params.removeFirst();
			int port = Integer.parseInt(params.removeFirst());
			usersData.add(new UserTableRow(ip, port));
				
		}
		
	}
	
	public void setUserAmountThreshold(String threshold){
		
		if(port.get().isEmpty())
			return;
		
		int thresholdNum;
		
		try {
			thresholdNum = Integer.parseInt(threshold);
		} catch (NumberFormatException e) {
			return;
		}
		
		if(thresholdNum<0)
			return;
		
		this.userAmountThreshold.set(thresholdNum);
		model.setUserAmountThreshold(thresholdNum);
		
	}

	public IntegerProperty getNumOfConnectedUsers() {
		return numOfConnectedUsers;
	}
	
	


}
