package model.data.levels;

import java.util.ArrayList;

import commons.Direction;
import model.data.elements.Box;
import model.data.elements.Cell;
import model.data.elements.CoverableElement;
import model.data.elements.Hero;
import model.data.elements.Position;
import model.data.elements.SokobanElement;
import model.data.elements.Target;
import model.data.elements.Wall;

public class SokobanLevel extends Level {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer stepsTaken;
	private Integer elapsedTime;
	private ArrayList<ArrayList<Cell>> map;
	private ArrayList<Position> heroes;
	private ArrayList<Position> targets;

	public SokobanLevel() {
		super(null);
		this.stepsTaken = 0;
		this.elapsedTime = 0;
		this.map = null;
		this.heroes = null;
		this.targets = null;
	}

	public SokobanLevel(String name, int stepsTaken, int elapsedTime, ArrayList<ArrayList<Cell>> map) {
		super(name);
		this.stepsTaken = stepsTaken;
		this.elapsedTime = elapsedTime;
		this.map = map;
		heroes = findHeroes(map);
		targets = findTargets(map);

	}

	
	public boolean isBox(Position pos) {

		SokobanElement element = getElementInPosition(pos);
		if (element instanceof CoverableElement)
			element = ((CoverableElement) element).getOnTop();

		if (element instanceof Box)
			return true;

		else
			return false;

	}

	public boolean isHero(Position pos) {

		SokobanElement element = getElementInPosition(pos);
		if (element instanceof CoverableElement)
			element = ((CoverableElement) element).getOnTop();

		if (element instanceof Hero)
			return true;

		else
			return false;

	}
	


	public boolean isWall(Position pos) {

		SokobanElement element = getElementInPosition(pos);

		if (element instanceof Wall)
			return true;

		else
			return false;

	}
	

	public boolean isTarget(Position pos) {

		SokobanElement element = getElementInPosition(pos);

		if (element instanceof Target)
			return true;

		else
			return false;

	}

	public SokobanElement getElementInPosition(Position pos) {
		int row = pos.getRow();
		int col = pos.getCol();

		Cell cell = map.get(row).get(col);

		SokobanElement element = cell.getElementOnCell();

		return element;

	}

	private ArrayList<Position> findTargets(ArrayList<ArrayList<Cell>> map){
		
		ArrayList<Position> targetsFound = new ArrayList<>();
		for (int row = 0; row < map.size(); row++) {

			for (int col = 0; col < map.get(row).size(); col++) {
				
				Position CurrentPos = new Position(row, col);
				if(isTarget(CurrentPos)){
					targetsFound.add(CurrentPos);
				}

			}
		}

		return targetsFound;
	}
	
	
	private ArrayList<Position> findHeroes(ArrayList<ArrayList<Cell>> map) {

		ArrayList<Position> heroesFound = new ArrayList<>();
		for (int row = 0; row < map.size(); row++) {

			for (int col = 0; col < map.get(row).size(); col++) {
				
				Position CurrentPos = new Position(row, col);
				if(isHero(CurrentPos)){
					heroesFound.add(CurrentPos);
				}

			}
		}

		return heroesFound;

	}
	
	

	public void moveHero(int HeroIndex, Direction direction) {

		Position heroPos = heroes.get(HeroIndex);

		CoverableElement element = (CoverableElement) getElementInPosition(heroPos);

		Position nextPos = Position.generateNextPosition(heroPos, direction);
		CoverableElement nearElement = (CoverableElement) getElementInPosition(nextPos);

		if (nearElement.getOnTop() instanceof Box) {
			Position nextBoxPos = Position.generateNextPosition(nextPos, direction);
			CoverableElement nearBoxElement = (CoverableElement) getElementInPosition(nextBoxPos);
			nearBoxElement.setOnTop(nearElement.getOnTop());
			nearElement.setOnTop(element.getOnTop());
			element.setOnTop(null);
			
		} else {
			nearElement.setOnTop(element.getOnTop());
			element.setOnTop(null);
		}

		heroes.get(HeroIndex).setPosition(nextPos);

	}

	
	public boolean isSolved(){
		
		
		for(Position targetPos : targets){
			
			if(!isBox(targetPos)){
				return false;
			}
			
		}
		
		return true;
		
		
		
	}
	
	
	
	/* /GETTERS AND SETTERS/ */
	public ArrayList<ArrayList<Cell>> getMap() {
		return map;
	}

	public void setMap(ArrayList<ArrayList<Cell>> map) {
		this.map = map;
	}

	public ArrayList<Position> getHeroes() {
		return heroes;
	}

	public void setHeroes(ArrayList<Position> heroes) {
		this.heroes = heroes;
	}

	public Integer getStepsTaken() {
		return stepsTaken;
	}

	public void setStepsTaken(Integer stepsTaken) {
		this.stepsTaken = stepsTaken;
	}

	public Integer getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Integer elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public ArrayList<Position> getTargets() {
		return targets;
	}

	public void setTargets(ArrayList<Position> targets) {
		this.targets = targets;
	}
	
	

	/**/

}
