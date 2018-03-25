package controller.commands;

import model.Model;

public class SaveCommand extends SokobanCommand {

	private Model model;
	
	public SaveCommand(Model model) {
		this.model = model;
	}

	@Override
	public void execute() {
		
		String filePath = params.remove(0);
		model.saveLevel(filePath);
		
	}
}
