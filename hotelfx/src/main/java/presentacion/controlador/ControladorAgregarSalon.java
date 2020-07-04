package presentacion.controlador;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.CategoriaCuartoDTO;
import dto.SalonDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.Salon;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorAgregarSalon implements Initializable{
	@FXML private TextField txtCapacidad;
	@FXML private TextField txtSenia;
	@FXML private TextField txtEstilo;
	@FXML private TextField txtMonto;
	@FXML private Button btnAgregarSalon;
	@FXML private Button btnEditarSalon;
	@FXML private Button btnCerrar;
		  private Salon salon;
		  private Integer id;
		  private Alert alert;
		  private ControladorABMSalon controladorABMSalon;
		  
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarIconos();
		this.salon = new Salon(new DAOSQLFactory());
		this.alert = new Alert(AlertType.INFORMATION);
	}
	
	@FXML
	public void guardarSalon() throws IOException {
		
		if(!Validador.formatoNumero(txtSenia.getText()) || !Validador.formatoNumero(txtCapacidad.getText())) {
			Validador.mostrarMensaje("Formatos incorrectos");
			return;
		}
		int senia = Integer.parseInt(txtSenia.getText());
		if(senia <= 100 && senia>=0) {
			int capacidad = Integer.parseInt(txtCapacidad.getText());
			String estilo = txtEstilo.getText();
			BigDecimal monto = new BigDecimal(txtMonto.getText());
			Boolean estado = true;
			
			SalonDTO salon = new SalonDTO(0, capacidad, senia, estilo, monto, estado);
			if(!Validador.consultarRepetidos(salon, this.salon.obtenerSalones())) {
				this.salon.agregarSalon(salon);
				this.controladorABMSalon.refrescarTabla();
				Validador.mostrarMensaje("Salon agregado.");
				cerrarVentana();	
			}
			else {
				Validador.mostrarMensaje("El salon ya existe.");
			}
		}
		else {
			Validador.mostrarMensaje("La seña debe ser un porcentaje de 0 a 100.");
		}
	}
	
	@FXML
	public void modificarSalon() throws IOException {
	
		Integer id = this.id;
		if(!Validador.formatoNumero(txtSenia.getText()) || !Validador.formatoNumero(txtCapacidad.getText())) {
			Validador.mostrarMensaje("Formatos incorrectos");
			return;
		}
		int capacidad = Integer.parseInt(txtCapacidad.getText());
		int senia = Integer.parseInt(txtSenia.getText());
		String estilo = txtEstilo.getText();
		BigDecimal monto = new BigDecimal(txtMonto.getText());
		Boolean estado = true;
		SalonDTO salonActual = null;
		SalonDTO salon = new SalonDTO(id , capacidad, senia, estilo, monto, estado);
		List<SalonDTO> salonesSinActual = this.salon.obtenerSalones();
		for(SalonDTO s : salonesSinActual) {
			if(s.getId() == id) {
				salonActual = s;
			}
		}
		salonesSinActual.remove(salonActual);
		
		if(!Validador.consultarRepetidos(salon, salonesSinActual)) {
			this.salon.modificarSalon(salon);
			this.controladorABMSalon.refrescarTabla();
			cerrarVentana();
			Validador.mostrarMensaje("Salon modificado.");
		}
		else {
			Validador.mostrarMensaje("Nombre del salon ya existe.");
		}
		
		
	}
	
	@FXML
	 private void cerrarVentana() {
		Stage stage = (Stage) btnCerrar.getScene().getWindow();
		stage.close();
	}
	
	public void setearCamposPantalla(SalonDTO salon) throws IOException {
		
		txtCapacidad.setText(String.valueOf(salon.getCapacidad()));
		txtSenia.setText(String.valueOf(salon.getSenia()));
		txtEstilo.setText(salon.getEstilo());
		txtMonto.setText(String.valueOf(salon.getMonto()));
		id = salon.getId();
		    
	}
	
	public Button getBtnAgregarSalon() {
		return btnAgregarSalon;
	}

	public void setBtnAgregarSalon(Button btnAgregarSalon) {
		this.btnAgregarSalon = btnAgregarSalon;
	}

	public Button getBtnEditarSalon() {
		return btnEditarSalon;
	}

	public void setBtnEditarSalon(Button btnEditarSalon) {
		this.btnEditarSalon = btnEditarSalon;
	}
	
	public void setVisibilityBtnAgregarSalon(Boolean value) {
		this.btnAgregarSalon.setVisible(value);
	}
	
	public void setDisableBtnAgregarSalon(Boolean value) {
		this.btnAgregarSalon.setDisable(value);
	}
	
	public void setVisibilityBtnModificarSalon(Boolean value) {
		this.btnEditarSalon.setVisible(value);
	}	
	
	public void setDisableBtnModificarSalon(Boolean value) {
		this.btnEditarSalon.setDisable(value);
	}

	public void enviarControlador(ControladorABMSalon controladorABMSalon) {
		// TODO Auto-generated method stub
		this.controladorABMSalon = controladorABMSalon;
	}
	
	private void cargarIconos() {
		
		URL linkEditar = getClass().getResource("/img/editar.png");
		URL linkConfirmar = getClass().getResource("/img/aceptar.png");		
		URL linkCerrar = getClass().getResource("/img/cancelar.png");	
		
		Image imageAgregar = new Image(linkConfirmar.toString(),24,24,false,true) ;
		Image imageEliminar = new Image(linkEditar.toString(),24,24,false,true) ;
		Image imageCerrar = new Image(linkCerrar.toString(),24,24,false,true) ;
	
		this.btnAgregarSalon.setGraphic(new ImageView(imageAgregar));
		this.btnEditarSalon.setGraphic(new ImageView(imageEliminar));
		this.btnCerrar.setGraphic(new ImageView(imageCerrar));
	}
}