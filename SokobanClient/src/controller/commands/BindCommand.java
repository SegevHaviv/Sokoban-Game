package controller.commands;

import model.Model;
import view.BindableGui;
import view.View;

public class BindCommand extends SokobanCommand{

	private Model model;
	private View view;
	
	public BindCommand(Model model, View view) {
		
		this.model = model;
		this.view = view;
	}
	@Override
	public void execute() {
		
		BindableGui bindable = (BindableGui)view;
		
		bindable.bindElapsedTimeProperty(model.getElapsedTimeProperty());
		bindable.bindStepsTakenProperty(model.getStepsTakenProperty());
		bindable.bindNamePropety(model.getLevelNamePropertyProperty());
		
	}

}
