package presentacion.controlador;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

import dto.ClienteDTO;
import dto.PerfilDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Cliente;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorAgregarCliente implements Initializable {
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellido;
	@FXML
	private TextField txtNumDocumento;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtTelefono;
	@FXML
	private RadioButton radioEstado;
	@FXML
	private DatePicker txtFecha;
	@FXML
	private Button btnAgregarCliente;
	@FXML
	private ComboBox<String> comboTipoDoc;
	private ObservableList<String> listaTipoDocExistentes;

	@FXML
	private Button btnModificarCliente;
	@FXML
	private Button btnReservas;
	@FXML
	private Button btnCerrar;
	
	private Integer id;
	private Cliente hotel;
	
	private Validador validador;
	

	
	@FXML private ControladorMenuPrincipal menuPrincipal;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 this.hotel = new Cliente(new DAOSQLFactory());
		 this.listaTipoDocExistentes = FXCollections.observableArrayList();
		 this.listaTipoDocExistentes.add("DNI");
		 this.listaTipoDocExistentes.add("Segundo tipo");
		 this.listaTipoDocExistentes.add("Tercer tipo");
		 
		 comboTipoDoc.setItems(listaTipoDocExistentes);
	}
	
	 @FXML
		public void guardarCliente() throws IOException {
		 
		 	if(!Validador.validarCliente(this)) {
		 		return;
		 	}
		 
			String nombre = txtNombre.getText();
			String apellido = txtApellido.getText();
			String tipoDoc = this.comboTipoDoc.getValue();
			String documento = txtNumDocumento.getText();
			String email = txtEmail.getText();
			String tel = txtTelefono.getText();
			java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(txtFecha.getValue());
			ClienteDTO nuevoCliente = new ClienteDTO(0, nombre, apellido, tipoDoc, documento, email, tel, true,gettedDatePickerDate);
			this.hotel.agregarCliente(nuevoCliente);
	
			//tomo el controlador
			//ControladorABMCliente scene2Controller = fxmlLoader.getController();
			//scene2Controller.refrescarTabla();
			cerrarVentanaAgregar();	

	}
	 

		public void setearCamposPantalla(ClienteDTO clienteSeleccionado) throws IOException {
		
			   //ClienteDTO clienteSeleccionado = controlador.getTablaPersonas().getSelectionModel().getSelectedItem();
			   txtNombre.setText(clienteSeleccionado.getNombre());
			   txtApellido.setText(clienteSeleccionado.getApellido());
			   this.comboTipoDoc.setValue(clienteSeleccionado.getTipoDocumento());
			   txtNumDocumento.setText(clienteSeleccionado.getNumeroDocumento());
			   txtEmail.setText(clienteSeleccionado.getEmail());
			   txtTelefono.setText(clienteSeleccionado.getTelefono());
			   
			   this.txtFecha.setValue(clienteSeleccionado.getFechaNacimiento().toLocalDate());
			   id = clienteSeleccionado.getIdCliente();
			   	    
		}

		@FXML
			public void modificarCliente() throws IOException {
			
				if(!Validador.validarCliente(this)) {
			 		return;
			 	}
			
				String nombre = txtNombre.getText();
				String apellido = txtApellido.getText();
				String tipoDoc = this.comboTipoDoc.getValue();
				String documento =txtNumDocumento.getText();
				String email = txtEmail.getText();
				String tel = txtTelefono.getText();
				java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(txtFecha.getValue());
				
				ClienteDTO nuevoCliente = new ClienteDTO(id, nombre, apellido, tipoDoc, documento, email, tel, true,gettedDatePickerDate);
				this.hotel.modificarCliente(nuevoCliente);
			
				cerrarVentanaModificar();	
		}
	 
	 
	 @FXML
	 private void cerrarVentanaAgregar() {
		Stage stage = (Stage) btnAgregarCliente.getScene().getWindow();
		stage.close();
	}
	 
	 @FXML
	 private void cerrarVentanaModificar() {
		 Stage stage = (Stage) btnModificarCliente.getScene().getWindow();
			stage.close();
	}
	 
	 @FXML
	 private void cerrarVentana() {
		 Stage stage = (Stage) btnCerrar.getScene().getWindow();
			stage.close();
	}
	 
	public Button getBtnAgregarCliente() {
			return btnAgregarCliente;
	}

	public void setBtnAgregarCliente(Button btnAgregarCliente) {
			this.btnAgregarCliente = btnAgregarCliente;
	}

	public void setVisibilityBtnAgregarCliente(Boolean value) {
		this.btnAgregarCliente.setVisible(value);
}
	public void setDisableBtnAgregarCliente(Boolean value) {

		this.btnAgregarCliente.setDisable(value);
}
	public void setVisibilityBtnModificarCliente(Boolean value) {
		this.btnModificarCliente.setVisible(value);
}
	public void setDisableBtnModificarCliente(Boolean value) {
		this.btnModificarCliente.setDisable(value);
}

	public Button getBtnModificarCliente() {
			return btnModificarCliente;
	}

	public void setBtnModificarCliente(Button btnModificarCliente) {
			this.btnModificarCliente = btnModificarCliente;
	}

	public TextField getTxtNombre() {
		return txtNombre;
	}

	public TextField getTxtApellido() {
		return txtApellido;
	}

	public TextField getTxtNumDocumento() {
		return txtNumDocumento;
	}

	public TextField getTxtEmail() {
		return txtEmail;
	}

	public TextField getTxtTelefono() {
		return txtTelefono;
	}

	public DatePicker getTxtFecha() {
		return txtFecha;
	}

	public ComboBox<String> getComboTipoDoc() {
		return comboTipoDoc;
	}
	

	

	
}
