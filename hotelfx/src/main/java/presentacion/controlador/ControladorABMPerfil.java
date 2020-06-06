package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import modelo.Perfil;
import modelo.PermisoPerfil;
import modelo.Usuario;
import dto.PerfilDTO;
import dto.PermisoPerfilDTO;
import dto.UsuarioDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorABMPerfil implements Initializable {
	
	@FXML
	private Button btnCrearPerfil;
	@FXML
	private Button btnEliminarPerfil;
	@FXML
	private TextField txtNombre;
	
	@FXML
	private CheckBox checkABMUsuarios;
	@FXML
	private CheckBox checkABMClientes;
	@FXML
	private CheckBox checkABMCuartos;
	
	@FXML
	private Button btnAgregarTodos;
	
	@FXML
	private Button btnQuitarTodos;
	
	@FXML
	private ComboBox<PerfilDTO> comboPerfiles;
	
	private Perfil perfil;
	private PermisoPerfil permisoPerfil;
	private Usuario usuario;
	private Alert alert;
	@FXML
	private ObservableList<PerfilDTO> observableList;

	private ArrayList<CheckBox> listaChecks;
	private ArrayList<Integer> listaIdPerfilesUsuarios;
	

	
	@FXML private ControladorMenuPrincipal menuPrincipal;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.alert = new Alert(AlertType.INFORMATION);
		this.perfil = new Perfil(new DAOSQLFactory());
		this.permisoPerfil = new PermisoPerfil(new DAOSQLFactory());
		this.usuario = new Usuario(new DAOSQLFactory());
		
		this.observableList = FXCollections.observableList(perfil.obtenerPerfil());
		this.comboPerfiles.setItems(observableList);
		
		this.listaIdPerfilesUsuarios = new ArrayList<Integer>();
	
		this.listaChecks = new ArrayList<CheckBox>();
		
		this.listaChecks.add(checkABMUsuarios);
		this.listaChecks.add(checkABMClientes);
		this.listaChecks.add(checkABMCuartos);
		
	}
	
	@FXML
	private void crearPerfil() {
		if(!this.txtNombre.getText().equals("")) {
			String nombrePerfil = this.txtNombre.getText();
			
			PerfilDTO nuevoPerfil = new PerfilDTO(0, nombrePerfil);
			
			for(PerfilDTO perfil : this.observableList) {
				if(perfil.getNombre().equals(nombrePerfil)) {
					mostrarMensaje("Este nombre de perfil ya existe");
					return;
				}
			}
			
			this.perfil.agregarPerfil(nuevoPerfil);
			
			mostrarMensaje("Perfil "+nombrePerfil+" creado con exito");
			return;
		}
		
		mostrarMensaje("Nombre inválido");
	}
	
	@FXML
	private void refrescarComboVistaPerfiles() {
		this.observableList = FXCollections.observableList(perfil.obtenerPerfil());
		this.comboPerfiles.setItems(observableList);
		
	}
	
	@FXML
	private void eliminarPerfil() {
		refrescarListaIdPerfilesUsuarios();
		
		int i=1;
		if(this.comboPerfiles.getValue()!=null) {
			
			PermisoPerfilDTO permisoPerfilNuevo = new PermisoPerfilDTO(0,0,0);
			permisoPerfilNuevo.setIdPerfil((this.comboPerfiles.getValue()).getIdPerfil());
			
			if(this.listaIdPerfilesUsuarios.contains(this.comboPerfiles.getValue().getIdPerfil())) {
				mostrarMensaje("Perfil asignado a un usuario, no puede ser borrado");
				return;
			}
			
			for(CheckBox checkPermiso : this.listaChecks) {
				permisoPerfilNuevo.setIdPermiso(i);
				this.permisoPerfil.eliminarPermiso(permisoPerfilNuevo);
				i++;
			}
			
			this.perfil.borrarPerfil(this.comboPerfiles.getValue());
			mostrarMensaje("Perfil "+this.comboPerfiles.getValue()+" borrado con exito");
			this.comboPerfiles.setValue(null);
			return;
		}
		mostrarMensaje("Antes debes seleccionar un perfil");
	}
	
	@FXML
	private void confirmarPermisos() {
		int i=1;
		if(this.comboPerfiles.getValue()!=null) {
			PermisoPerfilDTO permisoPerfilNuevo = new PermisoPerfilDTO(0,0,0);
			permisoPerfilNuevo.setIdPerfil((this.comboPerfiles.getValue()).getIdPerfil());
			
			for(CheckBox checkPermiso : this.listaChecks) {
				if(checkPermiso.isSelected()) {
					permisoPerfilNuevo.setIdPermiso(i);
					this.permisoPerfil.agregarPermiso(permisoPerfilNuevo);
				}
				else {
					permisoPerfilNuevo.setIdPermiso(i);
					this.permisoPerfil.eliminarPermiso(permisoPerfilNuevo);
				}
				i++;
			}
			
			mostrarMensaje("Permisos actualizados con exito");
			return;
		}
		
		mostrarMensaje("Ningún perfil seleccionado");
	
	}
	@FXML
	private void refrescarPermisos() {
		quitarTodos();
		
		int idPerfil = 0;
		
		if(this.comboPerfiles.getValue()!=null) {
			idPerfil = this.comboPerfiles.getValue().getIdPerfil();
		
			for (PermisoPerfilDTO permiso : obtenerPermisosPorIdPerfil(idPerfil)) {
				listaChecks.get(permiso.getIdPermiso()-1).setSelected(true);
			}
		}
	}
	
	private List<PermisoPerfilDTO> obtenerPermisosPorIdPerfil(int id) { 
		return permisoPerfil.buscarPermisos(id); 	
	}
	
	@FXML
	private void agregarTodos() {
		for(CheckBox checkPermiso : this.listaChecks) {
			checkPermiso.setSelected(true);
		}
	}
	
	@FXML
	private void quitarTodos() {
		for(CheckBox checkPermiso : this.listaChecks) {
			checkPermiso.setSelected(false);
		}
	}
	
	private void refrescarListaIdPerfilesUsuarios() {
		for(UsuarioDTO usuario : usuario.obtenerUsuarios()) {
			this.listaIdPerfilesUsuarios.add(usuario.getIdPerfil());
		}
	}
	
	private void mostrarMensaje(String mensaje) {
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);

		alert.showAndWait();
	}
	
}
