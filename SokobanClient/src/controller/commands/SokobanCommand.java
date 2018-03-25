package controller.commands;

import java.util.List;

public abstract class SokobanCommand implements Command{

	protected List<String> params;
	

	
	public SokobanCommand() {
		params = null;
	}
	public SokobanCommand(List<String> params) {

		this.params = params;
	}
	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}
	
	
	
}
