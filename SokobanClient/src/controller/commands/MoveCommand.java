package controller.commands;

import model.Model;

public class MoveCommand extends SokobanCommand {

	private Model model;
	
	public MoveCommand(Model model) {
		this.model = model;
	}
	
	
	@Override
	public void execute() {
		int heroIndex = 0;
		if (params.size() > 1)
			heroIndex = Integer.parseInt(params.remove(0));
		
		String direction = params.remove(0);
		model.moveHero(heroIndex, direction);
		
	}

}
