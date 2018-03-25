package model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import model.server.Server;

public class SolverServerModel extends Observable implements Model, Observer {

	private Server server;
	private int numOfUsersHandled;

	public SolverServerModel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void startServer(int port) {
		try {
			if (server != null)
				server.stop();

			server = new Server(port);
			server.addObserver(this);

			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void stopServer() {

		try {

			if (server != null)

				server.stop();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void update(Observable o, Object arg) {

		@SuppressWarnings("unchecked")
		LinkedList<String> params = (LinkedList<String>) arg;
		String commandKey = params.get(0);
		commandKey = commandKey.toUpperCase();
		
		switch (commandKey) {

		case "USERCONNECTED":
			numOfUsersHandled++;
			break;

		}



		setChanged();
		notifyObservers(params);

	}

	@Override
	public void setUserAmountThreshold(int threshold){
		if(server!=null)
		server.setUserAmountThreshold(threshold);
		
	}

	@Override
	public int getNumOfUsersHandled() {
	
		return numOfUsersHandled;
		
	}

}
