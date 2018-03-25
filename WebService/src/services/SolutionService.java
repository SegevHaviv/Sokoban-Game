package services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;


import db.DBManager;
import db.Solution;

@Path("solutions")
public class SolutionService {

	private DBManager dbHandler = new DBManager();

	@QueryParam("levelMap") String Map;
	@GET
	public String getSolution() {
		return dbHandler.searchSolution(Map);
	}

	@POST
	@Consumes("text/plain")
	@Path("add")
	public String addSolution(String actions) {
		dbHandler.addSolution(new Solution(Map, actions));

		return "";
	}
}