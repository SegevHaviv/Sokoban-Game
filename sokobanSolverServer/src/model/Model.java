package model;

public interface Model {
	
	public void startServer(int port);
	
	public void stopServer();
	
	public int getNumOfUsersHandled();
	
	public void setUserAmountThreshold(int threshold);
}
