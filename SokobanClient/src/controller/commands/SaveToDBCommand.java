package controller.commands;

import model.Model;

public class SaveToDBCommand extends SokobanCommand {

	Model model;
	
	public SaveToDBCommand(Model model) {
		this.model = model;
	}
	
	@Override
	public void execute() {
		
		String userName = params.remove(0);
		model.saveScoreToDB(userName);
		
		
	}

}
