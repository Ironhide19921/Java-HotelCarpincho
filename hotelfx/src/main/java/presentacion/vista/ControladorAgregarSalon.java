package presentacion.vista;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import dto.CategoriaCuartoDTO;
import dto.SalonDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Salon;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorAgregarSalon implements Initializable{
	@FXML private TextField txtCapacidad;
	@FXML private TextField txtSenia;
	@FXML private TextField txtEstilo;
	@FXML private TextField txtMonto;
	@FXML private Button btnAgregarSalon;
	@FXML private Button btnEditarSalon;
		  private Salon salon;
		  private Integer id;
		  
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.salon = new Salon(new DAOSQLFactory());
	}
	
	@FXML
	public void guardarSalon() throws IOException {
	 
		int capacidad = Integer.parseInt(txtCapacidad.getText());
		BigDecimal senia = new BigDecimal(txtSenia.getText());
		String estilo = txtEstilo.getText();
		BigDecimal monto = new BigDecimal(txtMonto.getText());
		Boolean estado = true;
		
		SalonDTO salon = new SalonDTO(0, capacidad, senia, estilo, monto, estado);
		this.salon.agregarSalon(salon);
		cerrarVentana();	
	}
	
	@FXML
	public void modificarSalon() throws IOException {
	
		Integer id = this.id;
		int capacidad = Integer.parseInt(txtCapacidad.getText());
		BigDecimal senia = new BigDecimal(txtSenia.getText());
		String estilo = txtEstilo.getText();
		BigDecimal monto = new BigDecimal(txtMonto.getText());
		Boolean estado = true;
	
		SalonDTO salon = new SalonDTO(id , capacidad, senia, estilo, monto, estado);
		this.salon.modificarSalon(salon);
		cerrarVentana();	
	}
	
	@FXML
	 private void cerrarVentana() {
		Stage stage = (Stage) txtCapacidad.getScene().getWindow();
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
}