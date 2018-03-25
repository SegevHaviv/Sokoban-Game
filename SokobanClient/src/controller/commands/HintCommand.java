package controller.commands;

import model.Model;

public class HintCommand extends SokobanCommand {

	private Model model;
	
	public HintCommand(Model model) {
		this.model = model;
	}
	
	
	
	@Override
	public void execute() {
		
		model.hint();
		
		
	}

}
