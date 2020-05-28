package presentacion.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import dto.ConfiguracionDTO;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import modelo.Configuracion;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;


public class ControladorConfiguracion implements Initializable{
	@FXML private TextField txtUsername;
	@FXML private TextField txtPassword;
	@FXML private Button btnGuardar;
	@FXML private ComboBox<String> comboProvs;
	private ObservableList<String> listaProvs;
	private Configuracion configuracion;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.listaProvs = FXCollections.observableArrayList();
		this.listaProvs.add("outlook");
		this.listaProvs.add("gmail");
		this.comboProvs.setItems(listaProvs);	
		this.comboProvs.setValue("outlook");
		
		this.configuracion = new Configuracion(new DAOSQLFactory());
	}

	
//	@FXML
//	public void guardarConfig() throws IOException {
//		String mail = txtUsername.getText();
//		String password = txtPassword.getText();
//		
//		
//		ConfiguracionDTO config = new ConfiguracionDTO(0, mail, password);
//		this.configuracion.agregarConfiguracion(config);
//	}
	
	@FXML
	public void modificarConfig() throws IOException {
		if(txtUsername.getText().equals("") || txtPassword.getText().equals("")) {
			Validador.mostrarMensaje("Mail o Password incompletas");
			return ;
		}else {

		String mail = txtUsername.getText();
		String password = txtPassword.getText();
		String provSMTP = comboProvs.getValue().toString();
		String provAdevolver ="smtp.live.com";
		if(provSMTP.equals("outlook"))
			provAdevolver = "smtp.live.com";
		else if(provSMTP.equals("gmail"))
			provAdevolver = "smtp.gmail.com"; 
		
		ConfiguracionDTO config = new ConfiguracionDTO(1, mail, password, provAdevolver);
		this.configuracion.modificarConfiguracion(config);
		
		}
	}
}
