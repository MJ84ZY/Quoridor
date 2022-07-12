package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

//stage - scene
public class Main extends Application {

	private Parent root;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			root = FXMLLoader.load(getClass().getResource("/test.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Quoridor");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
//		System.out.println("ok");
	}

	public static void main(String[] args) {
		launch(args);
	}

}