package presentacion.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import dto.ConfiguracionDTO;
import dto.EmailDTO;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.Configuracion;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorConfiguracion implements Initializable{
	@FXML private TextField txtUsername;
	@FXML private TextField txtPassword;
	@FXML private Button btnTest;
	@FXML private Button btnGuardar;
	@FXML private ComboBox<String> comboProvs;
	private ObservableList<String> listaProvs;
	private Configuracion configuracion;
	private ObservableList<ConfiguracionDTO> listaConfig;
	@FXML private EmailDTO mail;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cargarIconos();
		this.listaProvs = FXCollections.observableArrayList();
		this.listaProvs.add("outlook");
		this.listaProvs.add("gmail");
		this.comboProvs.setItems(listaProvs);	
		this.comboProvs.setValue("outlook");
		this.listaConfig = FXCollections.observableArrayList();
		this.configuracion = new Configuracion(new DAOSQLFactory());
		this.listaConfig = getAllConfigs();
		mail = new EmailDTO();
		System.out.println(mail.getEmisor());
		setearCampos(listaConfig);
		// Validar mail
		txtUsername.focusedProperty().addListener((obs, oldText, newText) -> {
			validarCorreo(newText);
		});
		
	}

	private void cargarIconos() {
	
			
			URL linkConfirmar = getClass().getResource("/img/aceptar.png");		
			URL linkTest = getClass().getResource("/img/buscar.png");	
			
			Image imageAgregar = new Image(linkConfirmar.toString(),24,24,false,true) ;
			Image imageTest = new Image(linkTest.toString(),24,24,false,true) ;
			
			this.btnTest.setGraphic(new ImageView(imageTest));
			this.btnGuardar.setGraphic(new ImageView(imageAgregar));
	
	}

	private void validarCorreo(Boolean newText) {
		if(newText==false) {
			if(!Validador.formatoMail(txtUsername.getText())) {
				Validador.mostrarMensaje("Formato de correo inválido");
				System.out.println("no cumple");
				txtUsername.requestFocus();
			}else {
				txtPassword.requestFocus();
			}
		}		
	}


	private void setearCampos(ObservableList<ConfiguracionDTO> listaDeUno) {
		txtUsername.setText(listaDeUno.get(0).getUsername());
		txtPassword.setText(listaDeUno.get(0).getPassword());
		switch(listaDeUno.get(0).getProvSMTP()) {
		case("smtp.gmail.com"):
			comboProvs.setValue("gmail");
		break;
		case("smtp.live.com"):
			comboProvs.setValue("outlook");
		break;
		}
	}


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

			Validador.mostrarMensaje("Configuración de correo guardada con éxito!");
		}
	}

	private ObservableList<ConfiguracionDTO> getAllConfigs() {
		List<ConfiguracionDTO> configs = this.configuracion.obtenerConfiguraciones();
		//		listaConfig.clear();
		for(ConfiguracionDTO c : configs) {
			listaConfig.add(c);
		}
		return listaConfig;
	}
	
	@FXML
	private void testMail() {
		this.mail = new EmailDTO();
		mail.enviarMsjTest();
		
	}

}
