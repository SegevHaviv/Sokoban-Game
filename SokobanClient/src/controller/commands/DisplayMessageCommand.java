package controller.commands;

import view.View;

public class DisplayMessageCommand extends SokobanCommand{

	View view;
	
	public DisplayMessageCommand(View view) {
		this.view = view;
	}
	
	@Override
	public void execute() {
		String message = String.join(" ", params);
		view.displayMessage(message);
		
	}

}
