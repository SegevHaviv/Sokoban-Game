package controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import controller.commands.BindCommand;
import controller.commands.DeclareSolvedCommand;
import controller.commands.DisplayLeaderboardCommand;
import controller.commands.DisplayLevelCommand;
import controller.commands.DisplayMessageCommand;
import controller.commands.ExitCommand;
import controller.commands.HintCommand;
import controller.commands.LoadCommand;
import controller.commands.MoveCommand;
import controller.commands.SaveCommand;
import controller.commands.SaveToDBCommand;
import controller.commands.SokobanCommand;
import controller.commands.SolveCommand;
import controller.commands.UpdateLeaderboardCommand;
import controller.server.Server;
import model.Model;
import view.View;

public class SokobanController implements Observer {

	private Model model;
	private View view;

	private Map<String, SokobanCommand> commands;
	private Controller controller;

	private boolean ServerRunning;
	Server server;

	public SokobanController(Model m, View v) {
		this.model = m;
		this.view = v;
		controller = new Controller();

		initCommands();

		controller.start();

	}

	public SokobanController(Model m, View v, Server server) {
		this.model = m;
		this.view = v;
		controller = new Controller();
		this.server = server;

		initCommands();

		controller.start();

	}

	private void initCommands() {
		commands = new HashMap<String, SokobanCommand>();
		commands.put("MOVE", new MoveCommand(model));
		commands.put("DISPLAY", new DisplayLevelCommand(model, view));
		commands.put("DISPLAYMESSAGE", new DisplayMessageCommand(view));
		commands.put("SAVE", new SaveCommand(model));
		commands.put("LOAD", new LoadCommand(model));
		commands.put("BIND", new BindCommand(model, view));
		commands.put("DECLARESOLVED", new DeclareSolvedCommand(view));
		commands.put("SAVETODB", new SaveToDBCommand(model));
		commands.put("DISPLAYLEADERBOARD", new DisplayLeaderboardCommand(model, view));
		commands.put("UPDATELEADERBOARD", new UpdateLeaderboardCommand(model, view));
		commands.put("SOLVE", new SolveCommand(model));
		commands.put("HINT", new HintCommand(model));
		commands.put("EXIT", new ExitCommand(model, controller, server, view));

	}

	@Override
	public void update(Observable observable, Object arg) {

		@SuppressWarnings("unchecked")
		LinkedList<String> params = (LinkedList<String>) arg;
		String commandKey = params.removeFirst();
		commandKey = commandKey.toUpperCase();
		SokobanCommand command = commands.get(commandKey);
		if (command != null) {

			command.setParams(params);
			controller.insertCommand(command);

		}
	}

	public void StartServer() {

		try {
			if (server != null)
				server.start();

		} catch (Exception e) {
			System.out.println("Error loading the server, exitting now.");
			System.exit(0);
		}

	}

	public boolean isServerRunning() {
		return ServerRunning;
	}

}
