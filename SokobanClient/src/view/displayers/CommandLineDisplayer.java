package view.displayers;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import commons.CommonLevel;

public class CommandLineDisplayer implements Displayer {

	PrintWriter writer;

	public CommandLineDisplayer(OutputStream ostream) {
		writer = new PrintWriter(ostream);
	}

	
	public void setOutput(OutputStream ostream){
		writer = new PrintWriter(ostream);
		
	}
	@Override
	public void displayLevel(CommonLevel level) {
		ArrayList<ArrayList<Character>> map = level.getMap();

		for (ArrayList<Character> row : map) {

			for (Character symbol : row) {

				writer.print(symbol);
			}

			writer.print('\n');

		}

		writer.flush();

	}

	@Override
	public void displayMessage(String message) {
		
		writer.println(message);
		writer.flush();


	}
	
	@Override
	public void printInstructions(){
	
		writer.println("   _____       _  __     ____              ");
		writer.println("  / ____|     | |/ /    |  _ \\             ");
		writer.println(" | (___   ___ | ' / ___ | |_) | __ _ _ __  ");
		writer.println("  \\___ \\ / _ \\|  < / _ \\|  _ < / _` | '_ \\ ");
		writer.println("   ___) | (_) | . \\ (_) | |_) | (_| | | | |");
		writer.println(" |_____/ \\___/|_|\\_\\___/|____/ \\__,_|_| |_|");
		writer.println("\t*Chen Shmilovich and Segev Haviv.");
		writer.println("");

		writer.println("1. Load (file path here) to load level from file.");
		writer.println("2. Display to display the level.");
		writer.println("3. Move {up,down,left,right} to move character.");
		writer.println("4. Save (file path here) to save your progress.");
		writer.println("5. Exit to exit the program.");
		writer.flush();
		
	}
	
}
