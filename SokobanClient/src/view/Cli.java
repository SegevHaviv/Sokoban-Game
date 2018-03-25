package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import commons.CommonLevel;
import commons.DBScore;
import view.displayers.CommandLineDisplayer;

public class Cli extends Observable implements View {

	BufferedReader reader;

	String exitString = "EXIT";

	CommandLineDisplayer displayer;

	private volatile boolean stop = false;
	public Cli(InputStream istream, OutputStream ostream) {

		reader = new BufferedReader(new InputStreamReader(istream));
		displayer = new CommandLineDisplayer(ostream);

	}

	public Cli() {
		reader = new BufferedReader(new InputStreamReader(System.in));
		displayer = new CommandLineDisplayer(System.out);
	}

	public void setInput(InputStream istream) {

		reader = new BufferedReader(new InputStreamReader(istream));

	}

	public void setOutput(OutputStream ostream) {

		displayer.setOutput(ostream);

	}

	public String getExitString() {
		return exitString;
	}

	public void setExitString(String exitString) {
		this.exitString = exitString;
	}

	@Override
	public void displayLevel(CommonLevel level) {

		displayer.displayLevel(level);

	}

	@Override
	public void displayMessage(String message) {

		displayer.displayMessage(message);
	}

	private void ReadCommands() {

		Thread read = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					String commandLine = "";

					do {

						commandLine = reader.readLine();

						String[] commandArray = commandLine.split(" ");

						LinkedList<String> params = new LinkedList<String>();

						for (String word : commandArray) {
							params.add(word);
						}

						setChanged();
						notifyObservers(params);

					} while (!commandLine.toUpperCase().equals(exitString) && !stop);
					
						
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});
		read.start();

		try {
			read.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void start() {

		displayer.printInstructions();

		ReadCommands();
		

		displayer.displayMessage(exitString);

	}

	@Override
	public void stop() {
		stop = true;

	}

	@Override
	public void declareSolved() {
		

		
		displayer.displayMessage("Congratulations!Level solved!");
		
	}

	@Override
	public void DisplayLeaderboard(List<DBScore> leaderboard, String userName, String levelName, String orderBy,
			int firstIndex, int amountOfRows) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLeaderboard(List<DBScore> leaderboard, String userName, String levelName, String orderBy,
			int firstIndex, int amountOfRows) {
		// TODO Auto-generated method stub
		
	}





}
