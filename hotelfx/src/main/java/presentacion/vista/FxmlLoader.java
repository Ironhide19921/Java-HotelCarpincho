package presentacion.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import java.net.URL;

public class FxmlLoader {
private Pane view;
private Scene scene;
private FXMLLoader fxmlLoader;

public Pane getPage(String fileName) {
	
	try {
		System.out.println("presentacion/vista/" + fileName +".fxml");
		URL fileUrl = getClass().getClassLoader().getResource("presentacion/vista/" + fileName +".fxml");
		System.out.println(fileUrl.getFile()+" nombre file URL");

	  
		new FXMLLoader();
		view = FXMLLoader.load(fileUrl);
	
	} catch (Exception e) {
		System.out.println("No page " + fileName +" please check FXMLLoader");
	}
	return view;
	
	}


public Scene getScene(String fileName) {
	
	try {
		System.out.println("presentacion/vista/" + fileName +".fxml");
		URL fileUrl = getClass().getClassLoader().getResource("presentacion/vista/" + fileName +".fxml");
		System.out.println(fileUrl.getFile()+" nombre file URL");
		fxmlLoader = new FXMLLoader(fileUrl);
		//cargo el objeto completo que incluye toda la escena y el controlador
		Parent root = (Parent) fxmlLoader.load();
	
		return new Scene(root);
	
	} catch (Exception e) {
		System.out.println("No page " + fileName +" please check FXMLLoader");
	}
	return null;
	}

	public void mostrarStage(Stage primaryStage, String titulo) {
		primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
		primaryStage.setTitle(titulo);
		primaryStage.sizeToScene();
		primaryStage.show(); 
	}

	public FXMLLoader getFXMLLoader() {
		return fxmlLoader;
	}
}



