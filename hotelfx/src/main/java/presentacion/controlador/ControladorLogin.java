package presentacion.controlador;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import dto.ConfiguracionDTO;
import dto.EmailDTO;
import dto.PermisoPerfilDTO;
import dto.UsuarioDTO;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Main;
import modelo.Configuracion;
import modelo.PermisoPerfil;
import modelo.Usuario;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorLogin implements Initializable {
	
	public static UsuarioDTO usuarioLogeado;
	public static List<PermisoPerfilDTO> permisos;
	public static Set<Integer> permisosPorId;
	
	@FXML TextField txtUsuario;
	@FXML PasswordField txtPass;
	@FXML Button btnLogin;
	
	private EmailDTO email;
	private Configuracion emailConfig;
	
	@FXML private PermisoPerfil permisoPerfil;
	@FXML private Usuario usuario;
	private List<UsuarioDTO> usuarios;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.usuario = new Usuario(new DAOSQLFactory());
		this.permisoPerfil = new PermisoPerfil(new DAOSQLFactory());
		this.emailConfig = new Configuracion(new DAOSQLFactory());
		permisos = new ArrayList<PermisoPerfilDTO>();
		permisosPorId = new HashSet<Integer>();
		
		ControladorMenuPrincipal.loginStage.setOnCloseRequest(e->onClose());
	}
	
	@FXML
	public void verificarUsuario() {
		
			List<UsuarioDTO> usuarios = this.usuario.buscarUsuarios2(txtUsuario.getText(), txtPass.getText());
			
			try {
			
			usuarioLogeado = usuarios.get(0);
			permisos = this.permisoPerfil.buscarPermisos(usuarioLogeado.getIdPerfil());
			System.out.println(usuarioLogeado.getIdUsuario());
			
			for(PermisoPerfilDTO permiso : permisos) {
				permisosPorId.add(permiso.getIdPermiso());
			}
		
			cerrarVentana();
			
			}catch(Exception e) {
				String eleccion = Validador.mostrarMensajeCampo();
				if(!(eleccion.equals("")) && !(eleccion.equals("Cancelar")) && !(verificarEmail(eleccion)==(null))) {
					List<ConfiguracionDTO> config = emailConfig.obtenerConfiguraciones();
					UsuarioDTO emailUsuario = verificarEmail(eleccion);
					this.email = new EmailDTO(0, null, "Usuario: "+emailUsuario.getNombre()+"\nContraseña: "+emailUsuario.getPassword(), "Credenciales", config.get(0).getUsername(), emailUsuario.getEmail(), true, config.get(0).getPassword());
					email.enviarMsj(this.email);
					Validador.mostrarMensaje("Credenciales enviadas!");
					return;
				}
				
			}
			
	}
	
	@FXML
	private void cerrarVentana() {
		Stage stage = (Stage) btnLogin.getScene().getWindow();
		stage.close();
	}
	
	private void onClose() {
		Platform.exit();
	}
	
	private UsuarioDTO verificarEmail(String email) {
		if(usuario.buscarUsuarios3(email).isEmpty()) {
			Validador.mostrarMensaje("El email ingresado no existe en nuestra base de datos");
			return null;
		}
		
		return usuario.buscarUsuarios3(email).get(0); 
		
	}

}
