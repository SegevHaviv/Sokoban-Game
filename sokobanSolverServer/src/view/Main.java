package view;
	


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.SolverServerModel;
import viewModel.SolverServerViewModel;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxl = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
			BorderPane root = fxl.load();
			Scene scene = new Scene(root,427,400);
			
			MainWindowController view = fxl.getController();
			SolverServerModel model = new SolverServerModel();
			SolverServerViewModel viewModel = new SolverServerViewModel(model);
			model.addObserver(viewModel);
			view.setViewModel(viewModel);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			
			primaryStage.setMaxHeight(420);
			primaryStage.setMaxWidth(443);
			primaryStage.setMinHeight(420);
			primaryStage.setMinWidth(443);
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent WindowEv) {
					
					view.stopServer();
					
				}
			});
			
			primaryStage.show();


		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
