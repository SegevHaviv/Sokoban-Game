package controller.commands;

import java.util.List;

import commons.DBScore;
import model.Model;
import view.View;

public class UpdateLeaderboardCommand extends SokobanCommand {
	
	Model model;
	View view;
	
	public UpdateLeaderboardCommand(Model model,View view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void execute() {
		String userName = params.get(0);
		String levelName = params.get(1);
		String orderBy = params.get(2);
		int firstIndex = Integer.parseInt(params.get(3));
		int maxResults = Integer.parseInt(params.get(4));
		
		List<DBScore> leaderboard = model.getLeaderboard(userName,levelName,orderBy,firstIndex,maxResults);
		view.updateLeaderboard(leaderboard,userName,levelName,orderBy,firstIndex,maxResults);
		
	}

}
