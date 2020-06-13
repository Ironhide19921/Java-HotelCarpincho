package presentacion.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.SendHttp;

public class ControladorDivisas implements Initializable {
	@FXML private TextField txtValorUsd;
	@FXML private TextField txtValorEur;
	@FXML private Button btnRefrescar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			txtValorUsd.setText(SendHttp.getConversion("ARS", "USD").toString());
			txtValorEur.setText(SendHttp.getConversion("ARS", "EUR").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void refrescar() throws Exception {
		txtValorUsd.setText(SendHttp.getConversion("ARS", "USD").toString());
		txtValorEur.setText(SendHttp.getConversion("ARS", "EUR").toString());
	}

}
