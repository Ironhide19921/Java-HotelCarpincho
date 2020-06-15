package presentacion.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dto.ClienteDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.Cliente;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.FxmlLoader;

public class ControladorMenuReservaEvento implements Initializable{

	@FXML private ObservableList<String> listaClientes;
	@FXML private ComboBox<String> comboClientes;
	@FXML private Button btnListaReservasEvento;
	@FXML private Button btnListarTodoReservasEvento;
	@FXML private Button btnAgregarReservaEvento;
	@FXML private BorderPane panelActual;
	private Alert alert;
	private Cliente cliente;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.cliente = new Cliente(new DAOSQLFactory());
		this.alert = new Alert(AlertType.INFORMATION);
		this.listaClientes = FXCollections.observableList(cargarComboClientes());
		this.comboClientes.setItems(listaClientes);		
	}
	
	public List<String> cargarComboClientes() {
		List<String> lista = new ArrayList<>();
		for(ClienteDTO c : this.cliente.obtenerClientes()) {
			lista.add(c.getIdCliente() + "-" + c.getTipoDocumento() + "-" + c.getNumeroDocumento());
		}
		return lista;
	}	
	
//	public void addReservaEvento() throws Exception {
//		    Stage primaryStage = new Stage(); 
//	 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarReservaEvento.fxml");
//			FXMLLoader fxmlLoader = new FXMLLoader(fxml);
//			Parent root = (Parent) fxmlLoader.load();
//			primaryStage.setScene(new Scene(root));   
//			ControladorAgregarReservaEvento scene2Controller = fxmlLoader.getController();
			
//			String comboSeleccionado = this.comboClientes.getSelectionModel().getSelectedItem().toString();
//			ClienteDTO c = obtenerClienteSeleccionado(comboSeleccionado);
			
//			verABMReservaEvento();
			//quiero agarrar el item de la lista y poder tener el clienteDTO con el tipo doc y el num doc
//			scene2Controller.agregarReservaEventoCliente(clienteFinal);
//			primaryStage.setTitle("Agregar cuarto");
//			primaryStage.sizeToScene();
//			primaryStage.show(); 
//    }
	
	private int obtenerClienteSeleccionado(String clienteCombo) {
		String[] datos = clienteCombo.split("-");
		int idClienteSeleccionado = Integer.valueOf(datos[0]);
		
		return idClienteSeleccionado;
	}
	
	
	@FXML
	public void verABMReservaEvento() {
		try {
			if(this.comboClientes.getValue()!=null) {
				
				String comboSeleccionado = this.comboClientes.getSelectionModel().getSelectedItem().toString();
				int clienteSeleccionado = obtenerClienteSeleccionado(comboSeleccionado);		

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("presentacion/vista/VentanaABMReservaEvento.fxml"));
				
			    Parent root = (Parent) fxmlLoader.load();
				ControladorABMReservaEvento controller = fxmlLoader.<ControladorABMReservaEvento>getController();
				controller.initData(clienteSeleccionado);

				panelActual.setCenter(root);
			}
			else {
				mostrarMensaje("Debe seleccionar un cliente para ver sus reservas");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void verTodasReservasEventos() {
		try {
			FxmlLoader fxmlLoader = new FxmlLoader();
			Pane view	= fxmlLoader.getPage("VentanaTodasReservasEventos");
			panelActual.setCenter(view);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void addReservaEvento() {
		try {
			if(this.comboClientes.getValue()!=null) {
				FxmlLoader fxmlLoader = new FxmlLoader();
				Pane view	= fxmlLoader.getPage("VentanaAgregarReservaEvento");
				panelActual.setCenter(view);
			}
			else {
				mostrarMensaje("Debe seleccionar un cliente para agregarle una reserva");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void mostrarMensaje(String mensaje) {
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);

		alert.showAndWait();
	}

}
