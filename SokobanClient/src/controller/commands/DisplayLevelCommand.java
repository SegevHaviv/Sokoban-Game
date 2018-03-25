package controller.commands;

import model.Model;
import view.View;

public class DisplayLevelCommand extends SokobanCommand {

	private Model model;
	private View view;
	
	public DisplayLevelCommand(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	
	@Override
	public void execute() {

		view.displayLevel(model.getCommonLevel());

		
	}
	
	

}
