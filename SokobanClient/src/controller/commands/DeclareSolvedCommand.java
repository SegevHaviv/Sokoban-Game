package controller.commands;

import view.View;

public class DeclareSolvedCommand extends SokobanCommand {

	private View view;

	
	public DeclareSolvedCommand(View view) {
		this.view = view;
	}
	
	public void execute() {
		
			view.declareSolved();
		
		
	
	}
}
