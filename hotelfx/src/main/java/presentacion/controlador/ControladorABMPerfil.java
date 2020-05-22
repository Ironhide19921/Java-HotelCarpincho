package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import modelo.Perfil;
import modelo.PermisoPerfil;
import dto.PerfilDTO;
import dto.PermisoPerfilDTO;
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
	private ComboBox<PerfilDTO> comboPerfiles;
	
	private Perfil perfil;
	private PermisoPerfil permisoPerfil;
	private Alert alert;
	@FXML
	private ObservableList<PerfilDTO> observableList;

	private ArrayList<CheckBox> listaChecks;
	
	@FXML private Controller controller;
	
	@FXML private ControladorMenuPrincipal menuPrincipal;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.alert = new Alert(AlertType.INFORMATION);
		this.perfil = new Perfil(new DAOSQLFactory());
		this.permisoPerfil = new PermisoPerfil(new DAOSQLFactory());
		this.observableList = FXCollections.observableList(perfil.obtenerPerfil());
		this.comboPerfiles.setItems(observableList);
		
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
		if(this.comboPerfiles.getValue()!=null) {
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
		this.checkABMUsuarios.setSelected(false);
		this.checkABMClientes.setSelected(false);
		this.checkABMCuartos.setSelected(false);
		
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
	
	private void mostrarMensaje(String mensaje) {
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);

		alert.showAndWait();
	}
	
}
