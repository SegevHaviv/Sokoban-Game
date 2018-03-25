package view;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

import commons.CommonLevel;
import commons.DBScore;
import commons.Direction;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.displayers.GraphicDisplayer;
import view.leaderboard.LeaderboardRow;
import view.leaderboard.LeaderboardWindowController;
import view.musicPlayer.MediaPlayer;
import view.musicPlayer.MusicPlayer;

public class MainWindowController extends Observable implements View, Initializable, BindableGui, Observer {

	private Stage stage;

	private MediaPlayer player;
	@FXML
	private GraphicDisplayer displayer;

	@FXML
	private Text stepsTakenText;
	@FXML
	private Text elapsedTimeText;
	@FXML
	private Text levelNameText;

	@FXML
	private Button leaderboardButton;

	private HashMap<KeyCode, Direction> keyMapping;
	
	private LeaderboardWindowController leaderboardController;


	private ArrayList<LeaderboardWindowController> leaderboardControllerList;

	public MainWindowController() {

		readKeyMapping();
		player = new MusicPlayer();
		leaderboardControllerList = new ArrayList<>();

	}

	@SuppressWarnings("unchecked")
	private void readKeyMapping() {

		try {
			XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("KeyMap.xml")));
			keyMapping = (HashMap<KeyCode, Direction>) xmlDecoder.readObject();
			xmlDecoder.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void setStageProperties() {

		leaderboardButton.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (true) {
				displayer.requestFocus(); // Delegate the focus to container

			}
		});

		stage.setMinHeight(800);
		stage.setMinWidth(800);

		stage.widthProperty().addListener((obs, oldVal, newVal) -> {
			displayer.setWidth(newVal.intValue()/1.05);

			displayer.redraw();

		});
		stage.heightProperty().addListener((obs, oldVal, newVal) -> {
			displayer.setHeight(newVal.intValue()/1.2);

			displayer.redraw();

		});

		stage.setTitle("SoKoMon");
		try {
			Image ico = new Image(new FileInputStream(displayer.getHeroDownImgPath()));
			stage.getIcons().add(ico);

		} catch (FileNotFoundException e) {
			System.out.println("Setting the file title has failed.");
		}

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent WindowEv) {
				close();

			}
		});

	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		setStageProperties();
	}

	@Override
	public void start() {

		bindProperties();

		player.play("./Resources/SokobanSong.mp3");
		LinkedList<String> params = new LinkedList<>();
		params.add("LOAD");
		params.add("levels/level1.txt");
		setChanged();
		notifyObservers(params);

	}

	private void bindProperties() {
		LinkedList<String> params = new LinkedList<>();
		params.add("BIND");
		setChanged();
		notifyObservers(params);

	}

	@Override
	public void displayLevel(CommonLevel level) {
		displayer.displayLevel(level);

	}

	@Override
	public void displayMessage(String message) {
		displayer.displayMessage(message);

	}

	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open Sokoban level");
		fc.setInitialDirectory(new File("./levels/"));
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text", "*.txt"),
				new FileChooser.ExtensionFilter("XML", "*.xml"), new FileChooser.ExtensionFilter("OBJ", "*.obj"));

		File chosen = fc.showOpenDialog(new Stage());
		if (chosen != null) {
			displayer.changeHeroDirection(Direction.DOWN);
			LinkedList<String> command = new LinkedList<String>();
			command.add("LOAD");
			command.add(chosen.getPath());
			setChanged();
			notifyObservers(command);
		}
	}

	public void saveFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Save Sokoban level");
		fc.setInitialDirectory(new File("./levels/"));
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text", "*.txt"),
				new FileChooser.ExtensionFilter("XML", "*.xml"), new FileChooser.ExtensionFilter("OBJ", "*.obj"));
		File chosen = fc.showSaveDialog(new Stage());
		if (chosen != null) {
			LinkedList<String> command = new LinkedList<String>();
			command.add("SAVE");
			command.add(chosen.getPath());
			setChanged();
			notifyObservers(command);
		}
	}

	public void about() {

		displayer.printInstructions();
	}

	public void close() {

		LinkedList<String> command = new LinkedList<String>();
		command.add("EXIT");
		setChanged();
		notifyObservers(command);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		displayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> displayer.requestFocus());

		displayer.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				LinkedList<String> command = new LinkedList<String>();
				command.add("MOVE");
				Direction direction = keyMapping.get(event.getCode());

				if (direction != null) {
					displayer.changeHeroDirection(direction);
					command.add(direction.name());
					setChanged();
					notifyObservers(command);

				}

			}
		});

	}

	@Override
	public void bindStepsTakenProperty(StringProperty stepsTaken) {
		stepsTakenText.textProperty().bind(stepsTaken);

	}

	@Override
	public void bindElapsedTimeProperty(StringProperty elapsedTime) {
		elapsedTimeText.textProperty().bind(elapsedTime);

	}

	@Override
	public void bindNamePropety(StringProperty name) {
		this.levelNameText.textProperty().bind(name);

	}

	public Text getStepsTakenText() {
		return stepsTakenText;
	}

	public void setStepsTakenText(Text stepsTakenText) {
		this.stepsTakenText = stepsTakenText;
	}

	public Text getElapsedTimeText() {
		return elapsedTimeText;
	}

	public void setElapsedTimeText(Text elapsedTimeText) {
		this.elapsedTimeText = elapsedTimeText;
	}

	@Override
	public void stop() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				player.stop();
				stage.close();

			}

		});

	}

	@Override
	public void declareSolved() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Save your result");
				dialog.setHeaderText("Congratulations! Level solved!\n");
				dialog.setContentText("Enter your name to save your result:");

				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					String username = result.get();
					if (username.length() > 19)
						username = username.substring(0, 19);

					LinkedList<String> params = new LinkedList<String>();

					params.add("SAVETODB");
					params.add(username);

					setChanged();
					notifyObservers(params);
				}

			}
		});
	}

	public void requestLeaderboard() {

		LinkedList<String> params = new LinkedList<String>();

		params.add("DISPLAYLEADERBOARD");
		params.add(null);
		params.add("CurrentLevel");
		params.add("finalTime");
		params.add("0");
		params.add("15");

		setChanged();
		notifyObservers(params);

	}
	
	private void setLeaderboardStageProperties(Stage stage){
		
		stage.setTitle("Leaderboard");
		try {
			Image ico = new Image(new FileInputStream("Resources/leaderboard.png"));
			stage.getIcons().add(ico);

		} catch (FileNotFoundException e) {
			System.out.println("Setting the stage icon failed.");
		}
		
		stage.minWidthProperty().set(415);
		stage.minHeightProperty().set(500);
		stage.maxHeightProperty().set(555);
		
		
	}

	@Override
	public void DisplayLeaderboard(List<DBScore> leaderboard,String userName, String levelName, String orderBy, int firstIndex,int amountOfRows) {

		ObservableList<LeaderboardRow> scoreData = FXCollections.observableArrayList();

		scoreData.clear();
		for (DBScore score : leaderboard) {

			scoreData.add(new LeaderboardRow(score));
		}

		MainWindowController mainwindow = this;
		

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
					LeaderboardWindowController leaderboardController= null;
					FXMLLoader fxl = new FXMLLoader(getClass().getResource("/view/leaderboard/LeaderboardWindow.fxml"));
					AnchorPane root = null;
					try {
						root = (AnchorPane) fxl.load();

						leaderboardController = fxl.getController();

						leaderboardController.addObserver(mainwindow);
						leaderboardControllerList.add(leaderboardController);
						leaderboardController.setWindowNumber(leaderboardControllerList.size()-1);
						
						

						Stage leaderboardStage = new Stage();
						Scene scene = new Scene(root, 415, 555);
						scene.getStylesheets()
								.add(getClass().getResource("/view/leaderboard/leaderboard.css").toExternalForm());
						leaderboardStage.setScene(scene);
						setLeaderboardStageProperties(leaderboardStage);
						leaderboardStage.show();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					


				leaderboardController.setDetails(userName, levelName, firstIndex, amountOfRows, orderBy);
				leaderboardController.setTableData(scoreData);

			}

		});

	}

	@Override
	public void update(Observable observable, Object arg) {
		
		@SuppressWarnings("unchecked")
		LinkedList<String> params =(LinkedList<String>) arg;
		int leaderboardWindowIndex = Integer.parseInt(params.removeFirst());
		leaderboardController = leaderboardControllerList.get(leaderboardWindowIndex);
		
		setChanged();
		notifyObservers(params);

	}
	

	@Override
	public void updateLeaderboard(List<DBScore> leaderboard,String userName, String levelName, String orderBy, int firstIndex,int amountOfRows) {
		
		ObservableList<LeaderboardRow> scoreData = FXCollections.observableArrayList();

		scoreData.clear();
		for (DBScore score : leaderboard) {

			scoreData.add(new LeaderboardRow(score));
		}
		

		leaderboardController.setDetails(userName, levelName, firstIndex, amountOfRows, orderBy);
		leaderboardController.setTableData(scoreData);
		
	}
	
	public void solve(){
		
		LinkedList<String> params = new LinkedList<String>();

		params.add("SOLVE");

		setChanged();
		notifyObservers(params);
		
	}
	
	public void hint(){
		
		LinkedList<String> params = new LinkedList<String>();

		params.add("HINT");

		setChanged();
		notifyObservers(params);
		
	}

}
