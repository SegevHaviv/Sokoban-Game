package Run;

import controller.SokobanController;
import controller.server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.SokobanModel;
import view.Cli;
import view.CommandLineClientHandler;
import view.MainWindowController;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxl = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
			BorderPane root = (BorderPane) fxl.load();

			MainWindowController view = fxl.getController();

			view.setStage(primaryStage);

			SokobanModel model = new SokobanModel(6666,"127.0.0.1");
			SokobanController controller = null;


			boolean serverRunning = false;
			if (!getParameters().getRaw().isEmpty()) {
				if (getParameters().getRaw().get(0).equals("-server")) {
					int port = Integer.parseInt(getParameters().getRaw().get(1));
					Cli cli = new Cli();
					CommandLineClientHandler clientHandler = new CommandLineClientHandler(cli);
					Server server = new Server(port,clientHandler);
					controller = new SokobanController(model, view,server);
					
					cli.addObserver(controller);
					
					
					controller.StartServer();
					serverRunning = true;

				}
				
			

			}
			
			if(!serverRunning){
				controller = new SokobanController(model, view);
				}
				model.addObserver(controller);
				view.addObserver(controller);

				view.start();

				Scene scene = new Scene(root, 1000, 800);
				scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
