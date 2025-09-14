package sk.mkrajcovic.mp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		PlayerUI ui = new PlayerUI();
		Scene scene = new Scene(ui.createUI(), 400, 250);

		stage.setTitle("Java Music Player");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}