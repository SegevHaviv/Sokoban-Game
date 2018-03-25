package model.clientHandlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Observable;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.solver.sokobanSolver;

public class SokobanSolutionClientHandler extends Observable implements ClientHandler {

	Socket client;

	public SokobanSolutionClientHandler(Socket client) {
		this.client = client;
	}

	@Override
	public void handleClient() {
		try {
			PrintWriter writer = new PrintWriter(client.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

			String compressedMap = reader.readLine();
			String parsedMapForService = compressedMap.replace(' ', 'f').replace('#', 'w').replace('@', 'b');
			String solution = getSolutionFromService(parsedMapForService);
			if(solution.equals("NONE")){
				solution = ""; 
			}	
			else if(solution.isEmpty()){
						sokobanSolver solver = new sokobanSolver();
						solution = solver.solve(compressedMap);	
						
						if(solution.isEmpty())
							saveSolutionToService(parsedMapForService,"NONE");
						
						else
							saveSolutionToService(parsedMapForService,solution);
					} 
					
		    writer.println(solution); 
			writer.flush();
			
			client.close();
		

			LinkedList<String> notificationToObservers = new LinkedList<>();
			notificationToObservers.add("USERDISCONNECTED");//
			setChanged();
			notifyObservers(notificationToObservers);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getSolutionFromService(String map) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/WebService/solutions?levelMap="+map);
		Response response = webTarget.request(MediaType.TEXT_PLAIN).get(Response.class);
		if (response.getStatus() == 200) {
			String solution = response.readEntity(new GenericType<String>() {});
			return solution;
		} else {
			System.out.println(response.getHeaderString("errorResponse"));
		}
		return "";
	} 
	
	



	public void saveSolutionToService(String map, String actions){
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client
				.target("http://localhost:8080/WebService/solutions/add?levelMap="+map);
		Entity<String> entity = Entity.text(actions);
		Response response = webTarget.request(MediaType.TEXT_PLAIN).post(entity);
		String value = response.readEntity(String.class);
		System.out.println(value);
		response.close();
		
	}
}
