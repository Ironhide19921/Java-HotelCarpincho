package presentacion.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import main.Main;
import java.net.URL;

public class FxmlLoader {
private Pane view;

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


}
