package view;

import common.UserTableRow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import viewModel.SolverServerViewModel;

public class MainWindowController implements View {

	@FXML
	private TextField portTextField;
	@FXML
	private TextField userAmountThresholdTextField;
	@FXML
	private Text currentPort;
	@FXML
	private Text currentThreshold;
	@FXML
	private Text NumOfconnectedUsers;

	@FXML
	private TableView<UserTableRow> usersTable = new TableView<>();

	@FXML
	private TableColumn<UserTableRow, String> userIp;

	@FXML
	private TableColumn<UserTableRow, Integer> userPort;

	@FXML
	private void initialize() {

		userIp.setCellValueFactory(u -> u.getValue().getIp());
		userPort.setCellValueFactory(u -> u.getValue().getPort().asObject());
	}

	public TextField getPortTextField() {
		return portTextField;
	}

	public void setPortTextField(TextField textField) {
		this.portTextField = textField;
	}

	public Text getCurrentPort() {
		return currentPort;
	}

	public void setCurrentPort(Text currentPort) {
		this.currentPort = currentPort;
	}

	private SolverServerViewModel viewModel;

	public void setViewModel(SolverServerViewModel viewModel) {
		this.viewModel = viewModel;
		currentPort.textProperty().bind(viewModel.getPort());
		currentThreshold.textProperty().bind(viewModel.getUserAmountThreshold().asString());
		NumOfconnectedUsers.textProperty().bind(viewModel.getNumOfConnectedUsers().asString());
		usersTable.setItems(viewModel.getUsersData());
		
	}

	public MainWindowController() {

	}

	@FXML
	public void stopServer() {
		viewModel.stopServer();
	}

	@FXML
	public void startServer() {

		viewModel.startServer(portTextField.getText());
	}
	
	@FXML
	public void setUserAmountThreshold() {
		viewModel.setUserAmountThreshold(userAmountThresholdTextField.getText());
	}

	@FXML
	public void about() {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("About");
				alert.setHeaderText(null);
				alert.setContentText(
						"Welcome to Sokomon solver server!\nThe server handles requests for sokoban level solutions.");
				alert.showAndWait();
			}
		});

	}

}
