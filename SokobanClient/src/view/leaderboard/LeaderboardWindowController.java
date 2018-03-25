package view.leaderboard;

import java.util.LinkedList;
import java.util.Observable;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LeaderboardWindowController extends Observable {
	@FXML
	private TableView<LeaderboardRow> leaderboard = new TableView<>();

	@FXML
	private TableColumn<LeaderboardRow, Integer> rankColumn;

	@FXML
	private TableColumn<LeaderboardRow, String> userNameColumn;

	@FXML
	private TableColumn<LeaderboardRow, String> levelNameColumn;

	@FXML
	private TableColumn<LeaderboardRow, Integer> stepsTakenColumn;

	@FXML
	private TableColumn<LeaderboardRow, Integer> finalTimeColumn;

	@FXML
	private TextField userFilterTextField;
	@FXML
	private TextField levelFilterTextField;
	
	@FXML
	private CheckMenuItem orderBySteps;
	
	@FXML
	private CheckMenuItem orderByTime;
	

	private int beginIndex;
	private int amountOfRows;
	private String userName = "";
	private String levelName = "";
	private String orderBy;
	private int windowNumber;

	public LeaderboardWindowController() {

	}

	@FXML
	private void initialize() {
		rankColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Integer>(
				leaderboard.getItems().indexOf(column.getValue()) + beginIndex + 1));
		userNameColumn.setCellValueFactory(cellData -> cellData.getValue().getUserName());
		levelNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLevelName());
		stepsTakenColumn.setCellValueFactory(cellData -> cellData.getValue().getStepsTaken().asObject());
		finalTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getFinalTime().asObject());

		userFilterTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {

					LinkedList<String> command = new LinkedList<String>();
					command.add("" + windowNumber);
					command.add("DISPLAYLEADERBOARD");
					if (!userFilterTextField.getText().isEmpty())
					command.add(userFilterTextField.getText());
					else
						command.add(null);
					if (!levelFilterTextField.getText().isEmpty())
						command.add(levelFilterTextField.getText());
					else
						command.add("");

					command.add(orderBy);
					command.add("" + 0);
					command.add("" + amountOfRows);
					setChanged();
					notifyObservers(command);
				}
			}

		});
		levelFilterTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {

					LinkedList<String> command = new LinkedList<String>();
					command.add("" + windowNumber);
					command.add("DISPLAYLEADERBOARD");
					if (!userFilterTextField.getText().isEmpty())
						command.add(userFilterTextField.getText());
					else
						command.add(null);
					
					if(!levelFilterTextField.getText().isEmpty())
					command.add(levelFilterTextField.getText());
					else
						command.add("");
					command.add(orderBy);
					command.add("" + 0);
					command.add("" + amountOfRows);

					setChanged();
					notifyObservers(command);

				}
			}
		});

		leaderboard.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					LeaderboardRow score = leaderboard.getSelectionModel().getSelectedItem();

					LinkedList<String> command = new LinkedList<String>();
					command.add("" + windowNumber);
					command.add("DISPLAYLEADERBOARD");
					command.add(score.getUserName().get());
					command.add("");
					command.add(orderBy);
					command.add("" + 0);
					command.add("" + amountOfRows);

					setChanged();
					notifyObservers(command);

				}

			}
		});

	}

	public void next() {

		LinkedList<String> command = new LinkedList<String>();
		command.add("" + windowNumber);
		command.add("UPDATELEADERBOARD");
		command.add(userName);
		command.add(levelName);
		command.add(orderBy);
		command.add("" + (beginIndex + amountOfRows));
		command.add("" + (amountOfRows));

		setChanged();
		notifyObservers(command);

	}

	public void back() {

		LinkedList<String> command = new LinkedList<String>();
		command.add("" + windowNumber);
		command.add("UPDATELEADERBOARD");
		command.add(userName);
		command.add(levelName);
		command.add(orderBy);
		if (beginIndex - 15 > 0) {
			command.add("" + (beginIndex - 15));
			command.add("" + (amountOfRows));
		} else {

			command.add("" + 0);
			command.add("" + amountOfRows);

		}

		setChanged();
		notifyObservers(command);

	}

	public void firstPage() {

		LinkedList<String> command = new LinkedList<String>();
		command.add("" + windowNumber);
		command.add("UPDATELEADERBOARD");
		command.add(userName);
		command.add(levelName);
		command.add(orderBy);
		command.add("" + 0);
		command.add("" + amountOfRows);

		setChanged();
		notifyObservers(command);

	}

	public void orderByTime() {
		
		orderBySteps.setSelected(false);
		LinkedList<String> command = new LinkedList<String>();
		command.add("" + windowNumber);
		command.add("UPDATELEADERBOARD");
		command.add(userName);
		command.add(levelName);
		command.add("finalTime");
		command.add("" + 0);
		command.add("" + amountOfRows);

		setChanged();
		notifyObservers(command);

	}

	public void orderBySteps() {

		orderByTime.setSelected(false);
		LinkedList<String> command = new LinkedList<String>();
		command.add("" + windowNumber);
		command.add("UPDATELEADERBOARD");
		command.add(userName);
		command.add(levelName);
		command.add("stepsTaken");
		command.add("" + 0);
		command.add("" + amountOfRows);

		setChanged();
		notifyObservers(command);

	}
	

	public void setTableData(ObservableList<LeaderboardRow> scoreData) {

		leaderboard.setItems(scoreData);

	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public int getAmountOfRows() {
		return amountOfRows;
	}

	public void setAmountOfRows(int amountOfRows) {
		this.amountOfRows = amountOfRows;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getWindowNumber() {
		return windowNumber;
	}

	public void setWindowNumber(int windowNumber) {
		this.windowNumber = windowNumber;
	}

	public void setDetails(String userName, String levelName, int beginIndex, int amountOfRows, String orderBy) {

		setUserName(userName);
		setLevelName(levelName);
		setBeginIndex(beginIndex);
		setAmountOfRows(amountOfRows);
		setOrderBy(orderBy);

	}

}
