package presentacion.vista;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import dto.UsuarioDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Usuario;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorABMUsuarios implements Initializable{
	
	@FXML private Button btnAgregar;
	@FXML private Button btnEditar;
	@FXML private Button btnHabilitar;
	@FXML private Button btnDeshabilitar;
	@FXML private Button btnReporte;
	@FXML private Button btnBuscar;
	@FXML private TextField txtBuscar;
	@FXML private Button btnLimpiarFiltro;
	@FXML private Usuario usuarios;
	@FXML private TableView<UsuarioDTO> tablaPersonas;
	@FXML private TableColumn id;
	@FXML private TableColumn documento;
	@FXML private TableColumn nombre;
	@FXML private TableColumn apellido;
	@FXML private TableColumn email;
	@FXML private TableColumn estado;
	@FXML private ObservableList<UsuarioDTO> activeSession;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.usuarios = new Usuario(new DAOSQLFactory());
		cargarColumnas();
		activeSession = FXCollections.observableArrayList();
		tablaPersonas.getItems().clear();
		refrescarTabla(); 
	}
	
	private void cargarColumnas()  {
		nombre.setCellValueFactory(new PropertyValueFactory("nombre"));		
		id.setCellValueFactory(new PropertyValueFactory("idUsuario"));	
		apellido.setCellValueFactory(new PropertyValueFactory("apellido"));
		email.setCellValueFactory(new PropertyValueFactory("email"));
		estado.setCellValueFactory(new PropertyValueFactory("estado"));
	}
	
	 @FXML
	    private void addUsuario(ActionEvent event) throws Exception 
	    {
		     try { 
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarUsuario.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
				primaryStage.setScene(new Scene(root));   
				ControladorAgregarUsuario scene2Controller = fxmlLoader.getController();
				scene2Controller.setVisibilityBtnAgregarUsuario(true);
				scene2Controller.setDisableBtnAgregarUsuario(false);
				scene2Controller.setVisibilityBtnModificarUsuario(false);
				scene2Controller.setDisableBtnModificarUsuario(true);		 
				primaryStage.setTitle("Agregar usuario");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	 @FXML
	    private void editUsuario(ActionEvent event) throws Exception 
	    {
		     try { 
			    Stage primaryStage = new Stage(); 
				URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarUsuario.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
				primaryStage.setScene(new Scene(root));   
				ControladorAgregarUsuario scene2Controller = fxmlLoader.getController();
				scene2Controller.setVisibilityBtnAgregarUsuario(false);
				scene2Controller.setDisableBtnAgregarUsuario(true);
				scene2Controller.setVisibilityBtnModificarUsuario(true);
				scene2Controller.setDisableBtnModificarUsuario(false);		 
				scene2Controller.setearCamposPantalla(tablaPersonas.getSelectionModel().getSelectedItem());
				primaryStage.setTitle("Modificar usuario");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	    }
	 
	 @FXML
		private void refrescarTabla(){
	 		crearTabla(getAllClientes());
		}

		private ObservableList<UsuarioDTO> getAllClientes() {
			List<UsuarioDTO> usuario = this.usuarios.obtenerUsuarios();
			activeSession.clear();
	 		for(UsuarioDTO c : usuario) {
	 			activeSession.add(c);
	 		}
	 		return activeSession;
		}
		
	 @FXML
	private void buscarCliente() {
	 	crearTabla(traerUsuarios());
	}

	 private void crearTabla(ObservableList<UsuarioDTO> lista) {
		tablaPersonas.setItems(lista);
		tablaPersonas.setEditable(true);
	 }
	
	
	 @FXML
	 private void deshabilitarUsuario(){	
		 UsuarioDTO usuarioSeleccionado = tablaPersonas.getSelectionModel().getSelectedItem();
		 usuarioSeleccionado.setEstado(false);
		 this.usuarios.modificarUsuario(usuarioSeleccionado);
		 refrescarTabla();
	 }
	 
	 
	 @FXML
	 private void habilitarUsuario(){	
		 UsuarioDTO usuarioSeleccionado = tablaPersonas.getSelectionModel().getSelectedItem();
		 usuarioSeleccionado.setEstado(true);
		 this.usuarios.modificarUsuario(usuarioSeleccionado);
		 refrescarTabla();
	 }
	 
	 
	 @FXML
	 	private ObservableList<UsuarioDTO> traerUsuarios() {
		 		
		 List<UsuarioDTO> usuarios = this.usuarios.buscarUsuarios(txtBuscar.getText().toString());
		 activeSession.clear();
	 		for(UsuarioDTO c : usuarios) {
	 			activeSession.add(c);
	 		}
	 		return activeSession;
		}

}
