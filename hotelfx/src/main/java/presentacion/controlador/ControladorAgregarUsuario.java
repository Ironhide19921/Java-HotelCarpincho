package presentacion.controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dto.EmailDTO;
import dto.PerfilDTO;
import dto.UsuarioDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Perfil;
import modelo.Usuario;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorAgregarUsuario implements Initializable {
	@FXML private TextField txtNombre;
	@FXML private TextField txtApellido;
	@FXML private TextField txtPassword;
	@FXML private TextField txtNumDocumento;
	@FXML private TextField txtEmail;
	@FXML private ComboBox<PerfilDTO> comboPerfil;
	@FXML private ComboBox<String> comboTipoDoc;
		  private ObservableList<String> listaTipoDocExistentes;
		  private List<UsuarioDTO> usuariosRegistrados;

	@FXML private Button btnAgregarUsuario;
	@FXML private Button btnModificarUsuario;
	@FXML private Button btnCerrar;
		  private Integer idUsuario;
		  private Integer idPerfil;
		  private Usuario usuarios;
		  private Perfil perfil;
		  
		  private ObservableList<PerfilDTO> listaPerfiles;
		  
		  private Validador validador;
		private ControladorABMUsuarios controladorABMUsuarios;
		  
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			this.perfil = new Perfil(new DAOSQLFactory());
			this.usuarios = new Usuario(new DAOSQLFactory());
//			this.usuariosRegistrados = new ArrayList<UsuarioDTO>();
			this.usuariosRegistrados = usuarios.obtenerUsuarios();
			this.listaTipoDocExistentes = FXCollections.observableArrayList();
			this.listaTipoDocExistentes.add("DNI");
			this.listaTipoDocExistentes.add("LIBRETA CIVICA");
			this.listaTipoDocExistentes.add("PASAPORTE");
			this.listaTipoDocExistentes.add("DOCUMENTO EXTRANJERO");
			this.listaPerfiles = FXCollections.observableList(perfil.obtenerPerfil());
			
			this.comboTipoDoc.setItems(listaTipoDocExistentes);
			this.comboPerfil.setItems(listaPerfiles);
				
		}
		
		 @FXML
			public void guardarUsuario() throws IOException {
			 
			 	if(!Validador.validarUsuario(this)) {
			 		return;
			 	}
			 	
			    String nombre = txtNombre.getText();
				String apellido = txtApellido.getText();
				String tipoDoc = this.comboTipoDoc.getValue();
				String documento =txtNumDocumento.getText();
				String email = txtEmail.getText();
				String password = txtPassword.getText();
					
				idUsuario = 0;
				idPerfil = this.comboPerfil.getValue().getIdPerfil();
				
				UsuarioDTO nuevoUsuario = new UsuarioDTO(idUsuario, idPerfil,nombre, apellido, tipoDoc, documento,email, password, true);
				
				if(!(Validador.consultarRepetidos(nuevoUsuario, (this.usuariosRegistrados)))) {
					this.usuarios.agregarUsuario(nuevoUsuario);
					this.controladorABMUsuarios.refrescarTabla();
					cerrarVentanaAgregar();
				}else {
					Validador.mostrarMensaje("Email ya registrado");
				}
				
				
				

		 }
		 

			public void setearCamposPantalla(UsuarioDTO usuarioSeleccionado) throws IOException {
				txtNombre.setText(usuarioSeleccionado.getNombre());
				txtApellido.setText(usuarioSeleccionado.getApellido());
				this.comboTipoDoc.setValue(usuarioSeleccionado.getTipoDocumento());
				txtNumDocumento.setText(usuarioSeleccionado.getNumeroDocumento());
				txtEmail.setText(usuarioSeleccionado.getEmail());
				txtPassword.setText(usuarioSeleccionado.getPassword());
				idUsuario = usuarioSeleccionado.getIdUsuario();

				for (PerfilDTO perfil : listaPerfiles) {
					if (perfil.getIdPerfil() == usuarioSeleccionado.getIdPerfil()) {
						this.comboPerfil.setValue(perfil);
					}

				}
			}

			@FXML
			public void modificarUsuario() throws IOException {
				if (!Validador.validarUsuario(this)) {
					return;
				}

				String nombre = txtNombre.getText();
				String apellido = txtApellido.getText();
				String tipoDoc = this.comboTipoDoc.getValue();
				String documento = txtNumDocumento.getText();
				String email = txtEmail.getText();
				String password = txtPassword.getText();

				idPerfil = this.comboPerfil.getValue().getIdPerfil();

				UsuarioDTO nuevoUsuario = new UsuarioDTO(idUsuario, idPerfil, nombre, apellido, tipoDoc, documento,
						email, password, true);
				
				if(!(Validador.consultarRepetidos(nuevoUsuario, (this.usuariosRegistrados)))) {
					this.usuarios.modificarUsuario(nuevoUsuario);
					this.controladorABMUsuarios.refrescarTabla();
					cerrarVentanaModificar();
				}else {
					Validador.mostrarMensaje("Email ya registrado");
				}
				
			}
		 
		 
		 @FXML
		 private void cerrarVentanaAgregar() {
			Stage stage = (Stage) btnAgregarUsuario.getScene().getWindow();
			stage.close();
		}
		 
		 @FXML
		 private void cerrarVentanaModificar() {
			 Stage stage = (Stage) btnModificarUsuario.getScene().getWindow();
				stage.close();
		}
		 
		 @FXML
		 private void cerrarVentana() {
			 Stage stage = (Stage) btnCerrar.getScene().getWindow();
				stage.close();
		}
		 
		public Button getBtnAgregarUsuario() {
				return btnAgregarUsuario;
		}

		public void setBtnAgregarUsuario(Button btnAgregarCliente) {
				this.btnAgregarUsuario = btnAgregarCliente;
		}

		public void setVisibilityBtnAgregarUsuario(Boolean value) {
			this.btnAgregarUsuario.setVisible(value);
		}
		public void setDisableBtnAgregarUsuario(Boolean value) {

			this.btnAgregarUsuario.setDisable(value);
		}
		public void setVisibilityBtnModificarUsuario(Boolean value) {
			this.btnModificarUsuario.setVisible(value);
		}
		public void setDisableBtnModificarUsuario(Boolean value) {
			this.btnModificarUsuario.setDisable(value);
		}

		public Button getBtnModificarUsuario() {
				return btnModificarUsuario;
		}

		public void setBtnModificarUsuario(Button btnModificarCliente) {
				this.btnModificarUsuario = btnModificarCliente;
		}
		
		public TextField getTxtNombre() {
			return txtNombre;
		}

		public void setTxtNombre(TextField txtNombre) {
			this.txtNombre = txtNombre;
		}

		public TextField getTxtApellido() {
			return txtApellido;
		}

		public void setTxtApellido(TextField txtApellido) {
			this.txtApellido = txtApellido;
		}

		public TextField getTxtPassword() {
			return txtPassword;
		}

		public void setTxtPassword(TextField txtPassword) {
			this.txtPassword = txtPassword;
		}

		public TextField getTxtNumDocumento() {
			return txtNumDocumento;
		}

		public void setTxtNumDocumento(TextField txtNumDocumento) {
			this.txtNumDocumento = txtNumDocumento;
		}

		public TextField getTxtEmail() {
			return txtEmail;
		}

		public void setTxtEmail(TextField txtEmail) {
			this.txtEmail = txtEmail;
		}

		public ComboBox<PerfilDTO> getComboPerfil() {
			return comboPerfil;
		}

		public ComboBox<String> getComboTipoDoc() {
			return comboTipoDoc;
		}

		public void enviarControlador(ControladorABMUsuarios controladorABMUsuarios) {
			// TODO Auto-generated method stub
			this.controladorABMUsuarios = controladorABMUsuarios;
		}
	
}
