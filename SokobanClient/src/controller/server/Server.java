package controller.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import commons.ClientHandler;

public class Server {

	private int port;
	private ServerSocket server;
	private ClientHandler ch;
	private volatile boolean stopServer;

	public Server(int port, ClientHandler ch) {

		this.port = port;
		this.ch = ch;
		stopServer = false;

	}

	public void runServer() throws IOException{
		
			while(!stopServer){
			
			Socket aClient=server.accept();
			InputStream inFromClient=aClient.getInputStream();
			OutputStream outToClient=aClient.getOutputStream();
			ch.setInput(inFromClient);
			ch.setOutput(outToClient);
			if(aClient!=null)
			ch.handleClient();
			
			aClient.close();
			
			}
		
		
	}

	public void start() throws IOException{
		
		server=new ServerSocket(port);
		server.setSoTimeout(5000);
		
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
	}

}
