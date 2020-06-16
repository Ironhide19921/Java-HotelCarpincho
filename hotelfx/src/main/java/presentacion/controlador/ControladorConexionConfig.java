package presentacion.controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dto.ConexionConfigDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Validador;
import persistencia.conexion.Conexion;

public class ControladorConexionConfig {
	
	@FXML TextField txtUser;
	@FXML TextField txtPass;
	@FXML TextField txtHost;
	@FXML Button btnConfirmar;
	
	public void initialize(URL arg0, ResourceBundle arg1)  {
		
	}
	
	@FXML
	public void escribirFicheroConexion() {
		FileWriter flwriter = null;
		try {
			//crea el flujo para escribir en el archivo
			flwriter = new FileWriter(".\\\\src\\\\main\\\\java\\\\persistencia\\\\conexion\\\\config.txt");
			//crea un buffer o flujo intermedio antes de escribir directamente en el archivo
			BufferedWriter bfwriter = new BufferedWriter(flwriter);
			
			//escribe los datos en el archivo
			bfwriter.write(txtHost.getText());
			bfwriter.newLine();
			bfwriter.write(txtUser.getText());
			bfwriter.newLine();
			bfwriter.write(txtPass.getText());
			
			//cierra el buffer intermedio
			bfwriter.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (flwriter != null) {
				try {//cierra el flujo principal
					flwriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		cerrarVentanaConfig();
	}
	
	public static ConexionConfigDTO leerFicheroConexion() {
	      File archivo = null;
	      FileReader fr = null;
	      BufferedReader br = null;

	      try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         archivo = new File (".\\src\\main\\java\\persistencia\\conexion\\config.txt"); //Cambiar para el instalador
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         String linea;
	         ArrayList lineas = new ArrayList<String>();
	         while((linea=br.readLine())!=null)
	            lineas.add(linea);
	         String host=(String) lineas.get(0);
	         String user=(String) lineas.get(1);
	         String pass=(String) lineas.get(2);
	         
	         ConexionConfigDTO config = new ConexionConfigDTO(0, host, user, pass);
	         return config;
	         
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
		return null;
	   }
	
	private void cerrarVentanaConfig() {
		Stage stage = (Stage) btnConfirmar.getScene().getWindow();
		stage.close();
	}
	
}
