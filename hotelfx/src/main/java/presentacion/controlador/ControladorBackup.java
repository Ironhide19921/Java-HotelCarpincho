package presentacion.controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dto.ConexionConfigDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import modelo.Validador;
import persistencia.conexion.Conexion;

public class ControladorBackup implements Initializable{
	
	//private InputStream is;
	
	@FXML private Button btnSeleccionar;
	@FXML private Button btnBackup;
	@FXML private Button btnRestore;
	@FXML private TextField txtPath;
	
	private File archivoSeleccionado;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarIconos();
		Conexion.getConexion();
		
		if(ControladorLogin.permisosPorId.contains(21)) {
			btnSeleccionar.setDisable(false);
		}
	}
	
	@FXML
	public void seleccionar() {
		File file = new File(".//backups");
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(file);
		fc.getExtensionFilters().addAll(new ExtensionFilter("Archivos SQL", "*.sql"));
		File selectedFile = fc.showOpenDialog(null);
		
		this.archivoSeleccionado = selectedFile;
		
		if(selectedFile!=null) {
			txtPath.setText(selectedFile.getAbsolutePath());
			this.btnRestore.setDisable(false);
		}
	
	}

	
	@FXML
	public void backup() {
		
		ConexionConfigDTO config = new ConexionConfigDTO(0, null, null, null);
		config = ControladorConexionConfig.leerFicheroConexion();

		try {
			Process p = Runtime.getRuntime().exec(".//sql//bin//mysqldump -u "+config.getUser()+" -p"+config.getPass()+" hotel");
			
			Date hoy = new Date(System.currentTimeMillis());
			
			InputStream is = p.getInputStream();
			FileOutputStream fos = new FileOutputStream("backups//Backup "+hoy.toString()+".sql");
			byte[] buffer = new byte[1000];

			int leido = is.read(buffer);
			while (leido > 0) {
				fos.write(buffer, 0, leido);
				leido = is.read(buffer);
			}

			fos.close();
			
			Validador.mostrarMensaje("Backup "+hoy.toString()+".sql"+" creado con ??xito");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void restore() {
		
		ConexionConfigDTO config = new ConexionConfigDTO(0, null, null, null);
		config = ControladorConexionConfig.leerFicheroConexion();
		
		try {

			Conexion.getConexion().cerrarConexion();

			Process p = Runtime.getRuntime().exec(".//sql//bin//mysql -u "+config.getUser()+" -p"+config.getPass()+" hotel");

			OutputStream os = p.getOutputStream();
			FileInputStream fis = new FileInputStream(archivoSeleccionado);
			byte[] buffer = new byte[1000];

			int leido = fis.read(buffer);
			while (leido > 0) {
				os.write(buffer, 0, leido);
				leido = fis.read(buffer);
			}

			os.flush();
			os.close();
			fis.close();

			Validador.mostrarMensaje("Restore completado con ??xito");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public Date fechaUltimoBackup() {
		
			File file = new File(".//backups");
			Date fecha = new Date(file.getAbsoluteFile().lastModified());
			
			return fecha;
			
	}
	
	private void cargarIconos() {
		
		URL linkSeleccionar = getClass().getResource("/img/seleccionar.png");
		URL linkBack = getClass().getResource("/img/database.png");	
		URL linkRestore = getClass().getResource("/img/descarga.png");		

		Image imageSeleccionar = new Image(linkSeleccionar.toString(),24,24,false,true) ;
		Image imageBack = new Image(linkBack.toString(),24,24,false,true) ;
		Image imageRestore = new Image(linkRestore.toString(),24,24,false,true) ;
	
		
		
		this.btnSeleccionar.setGraphic(new ImageView(imageSeleccionar));
		this.btnBackup.setGraphic(new ImageView(imageBack));
		this.btnRestore.setGraphic(new ImageView(imageRestore));
	}

}
