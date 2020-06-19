package presentacion.controlador;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

import dto.ClienteDTO;
import dto.EncuestaDTO;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Cliente;
import modelo.Encuesta;
import modelo.SendHttp;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorVerEncuesta implements Initializable {

	private Integer id;
	private Cliente hotel;
	private Encuesta encuesta;
	@FXML
	private Label lblNombre,lblApellido,lblTipoDocumento,lblDocumento,lblEncuesta;

	@FXML private ControladorMenuPrincipal menuPrincipal;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.hotel = new Cliente(new DAOSQLFactory());
		this.encuesta = new Encuesta(new DAOSQLFactory());

	}
	
	public void setearCamposPantalla(ClienteDTO clienteSeleccionado) throws IOException {

		lblNombre.setText(clienteSeleccionado.getNombre());
		lblApellido.setText(clienteSeleccionado.getApellido());
		lblTipoDocumento.setText(clienteSeleccionado.getTipoDocumento());
		lblDocumento.setText(clienteSeleccionado.getNumeroDocumento());			  
		id = clienteSeleccionado.getIdCliente();			 
		EncuestaDTO encuestaCompletada =  encuesta.traerEncuestaPorCliente(id);

		try {
			String resultadoEncuesta =SendHttp.traerRespuestas(encuestaCompletada.getRecipiente());
			System.out.println(resultadoEncuesta);
			if(!resultadoEncuesta.equals("")) {
				String[] porPipes = resultadoEncuesta.split("\\|");
				System.out.println(porPipes.length);
				String texto="";
				for(int i=0;i<porPipes.length;i++) {
					String[] porPuntoComa = porPipes[i].split(";");
					texto=texto +"Pregunta: "+porPuntoComa[0]+"\n";
					String[] porAmper = porPuntoComa[1].split("&");
					if(porAmper.length>1) {
						for(int j=0; j<porAmper.length;j++) {
							texto=texto +"Respuesta: "+porAmper[j]+"\n";
						}
					}else {
						texto=texto +"Respuesta: "+porPuntoComa[1]+"\n";
					}
				}

				lblEncuesta.setText(texto);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
