package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
		
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("/presentacion/vista/MenuPrincipal.fxml")); 
	
			primaryStage.setTitle("Menu Principal");
		    primaryStage.setScene(new Scene(root));
		    primaryStage.sizeToScene();
		    primaryStage.show(); 
		    this.stage = primaryStage;

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
