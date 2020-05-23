package presentacion.controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

public class ControladorImportar implements Initializable {
	

//	@FXML private Controller controller;
	
//	@FXML private ControladorMenuPrincipal menuPrincipal;
	@FXML Button btn1;
	@FXML ListView lstview;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


	}
	
	@FXML
	public void btn1() {
		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);
		if(selectedFile != null) {
			lstview.getItems().add(selectedFile.getAbsolutePath());
			System.out.println(lstview.getItems().size());
			System.out.println(selectedFile.length());
			try {
				Scanner scanner = new Scanner(selectedFile);
				while(scanner.hasNext()) {
					String data = scanner.nextLine();
					String[] valores = data.split(",");
					System.out.println(valores[0]);
					//System.out.println(scanner.next());
					lstview.getItems().add(data);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else {
			System.out.println("file is not valid");
		}
	}
}
