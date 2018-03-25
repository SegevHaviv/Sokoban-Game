package controller.commands;

import model.Model;

public class LoadCommand extends SokobanCommand {

	private Model model;
	
	public LoadCommand(Model model) {
		this.model = model;
	}
	
	
	@Override
	public void execute() {

		String filePath = params.remove(0);
		model.loadLevel(filePath);
		
	}

}
