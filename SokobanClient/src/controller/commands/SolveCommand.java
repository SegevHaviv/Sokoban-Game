package controller.commands;

import model.Model;

public class SolveCommand extends SokobanCommand {

	private Model model;
	
	public SolveCommand(Model model) {
		this.model = model;
	}
	
	
	@Override
	public void execute() {
		
		model.solve();
		
	}
	

}
