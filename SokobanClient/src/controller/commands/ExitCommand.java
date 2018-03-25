package controller.commands;

import java.io.IOException;

import controller.Controller;
import controller.server.Server;
import model.Model;
import view.View;

public class ExitCommand extends SokobanCommand {

	private Model model;
	private Controller controller;
	private Server server;
	private View view;

	public ExitCommand(Model model, Controller controller, Server server, View view) {
		this.model = model;
		this.controller = controller;
		this.server = server;
		this.view = view;

	}

	@Override
	public void execute() {
		view.stop();
		model.stop();
		controller.stop();
		
		try {
			if (server != null)
				server.stop();

		} catch (IOException e) {
			System.out.println("Error stopping the server, exitting now.");
			System.exit(0);
		}

	

	}

}
