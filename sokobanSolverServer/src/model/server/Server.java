package model.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ForkJoinPool;

import model.clientHandlers.SokobanSolutionClientHandler;

public class Server extends Observable implements Observer{

	private int port;
	private ServerSocket server;
	private volatile boolean stopServer;
	private ForkJoinPool threadPool;
	private int userAmountThreshold = Integer.MAX_VALUE;
	private int usersOnline;

	public Server(int port) {
		this.port = port;
		stopServer = false;

	}
	
	
	public void setUserAmountThreshold(int threshold){
		
		this.userAmountThreshold = threshold;
	}

	public void runServer() throws IOException{
		
		 threadPool = new ForkJoinPool();
		
			while(!stopServer){
				Socket aClient=server.accept();
				
				if(usersOnline>=userAmountThreshold){
					aClient.close();
					continue;
				}
				
				usersOnline++;
				SokobanSolutionClientHandler ch = new SokobanSolutionClientHandler(aClient);
				ch.addObserver(this);	
				
					if(aClient!=null){
						LinkedList<String> notificationToObservers = new LinkedList<>();
						notificationToObservers.add("USERCONNECTED");
						notificationToObservers.add(aClient.getInetAddress().getHostAddress());
						notificationToObservers.add(String.valueOf(aClient.getPort()));
						setChanged();
						notifyObservers(notificationToObservers);
					
						
						threadPool.execute(()->ch.handleClient());
			
					}
					
				}
			

	}

	public void start() throws IOException{
		
		server=new ServerSocket(port);
		
		
		new Thread(new Runnable(){

			@Override
			public void run(){
		
					try {
						runServer();
					} catch (IOException e) {
						System.out.println("Server timed-out.\n");
					
					}
			
			}
		}).start();	
		
		
	}

	public void stop() throws IOException{
		stopServer=true;
		server.close();
		threadPool.shutdown();
	}



	@Override
	public void update(Observable o, Object arg) {
		
		@SuppressWarnings("unchecked")
		LinkedList<String> params = (LinkedList<String>) arg;
		String commandKey = params.get(0);
		commandKey = commandKey.toUpperCase();
		
		switch (commandKey) {

		case "USERDISCONNECTED":
			usersOnline--;
			break;

		}
		setChanged();
		notifyObservers(arg);
	}

}
