package presentacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dto.CategoriaCuartoDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.CategoriaCuarto;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorAgregarCategoriaCuarto implements Initializable{
	@FXML private TextField txtNombre;
	@FXML private TextField txtDetalle;
	@FXML private Button btnAgregarCategoriaCuarto;
	@FXML private Button btnEditarCategoriaCuarto;
	@FXML private Button btnCerrar;
		  private CategoriaCuarto categoriaCuarto;
		  private Integer id;
		  
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.categoriaCuarto = new CategoriaCuarto(new DAOSQLFactory());
	}
	
	 @FXML
		public void guardarCliente() throws IOException {
		 
			String nombre = txtNombre.getText();
			String detalle = txtDetalle.getText();
			CategoriaCuartoDTO categoriaCuarto = new CategoriaCuartoDTO(0, nombre, detalle);
			this.categoriaCuarto.agregarCategoriaCuarto(categoriaCuarto);
			cerrarVentanaAgregar();	

	}
	 

		public void setearCamposPantalla(CategoriaCuartoDTO categoriaCuarto) throws IOException {
	
			   //ClienteDTO clienteSeleccionado = controlador.getTablaPersonas().getSelectionModel().getSelectedItem();
			   txtNombre.setText(categoriaCuarto.getNombre());
			   txtDetalle.setText(categoriaCuarto.getDetalle());
			   id = categoriaCuarto.getIdCategoriaCuarto();
			    
		 }

		@FXML
			public void modificarCliente() throws IOException {
			
			String nombre = txtNombre.getText();
			String detalle = txtDetalle.getText();
		
			CategoriaCuartoDTO categoriaCuarto = new CategoriaCuartoDTO(id, nombre, detalle);
				this.categoriaCuarto.modificarCategoriaCuarto(categoriaCuarto);
				cerrarVentanaModificar();	
		}
	 
	 
	 @FXML
	 private void cerrarVentanaAgregar() {
		Stage stage = (Stage) btnAgregarCategoriaCuarto.getScene().getWindow();
		stage.close();
	}
	 
	 @FXML
	 private void cerrarVentanaModificar() {
		 Stage stage = (Stage) btnEditarCategoriaCuarto.getScene().getWindow();
			stage.close();
	}
	 
	
	 @FXML
	 private void cerrarVentana() {
		 Stage stage = (Stage) btnCerrar.getScene().getWindow();
			stage.close();
	}
	 
	 
	 
		public Button getBtnAgregarCategoriaCuarto() {
				return btnAgregarCategoriaCuarto;
		}

		public void setBtnAgregarCategoriaCuarto(Button btnAgregarCategoriaCuarto) {
				this.btnAgregarCategoriaCuarto = btnAgregarCategoriaCuarto;
		}

		public void setVisibilityBtnAgregarCategoriaCuarto(Boolean value) {
			this.btnAgregarCategoriaCuarto.setVisible(value);
	}
		public void setDisableBtnAgregarCategoriaCuarto(Boolean value) {

			this.btnAgregarCategoriaCuarto.setDisable(value);
	}
		public void setVisibilityBtnModificarCategoriaCuarto(Boolean value) {
			this.btnEditarCategoriaCuarto.setVisible(value);
	}
		public void setDisableBtnModificarCategoriaCuarto(Boolean value) {
			this.btnEditarCategoriaCuarto.setDisable(value);
	}

		public Button getBtnModificarCategoriaCuarto() {
				return btnEditarCategoriaCuarto;
		}

		public void setBtnModificarCategoriaCuarto(Button btnModificarCliente) {
				this.btnEditarCategoriaCuarto = btnModificarCliente;
		}

	 
	 
}
