package presentacion.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dto.ClienteDTO;
import dto.CuartoDTO;
import dto.ReservaCuartoDTO;
import dto.TablaReservaDTO;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.Cliente;
import modelo.Cuarto;
import modelo.ReservaCuarto;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorABMReservaCuarto implements Initializable
{

	@FXML private Button btnAgregarReserva;
	@FXML private Button btnModificarReserva;
	@FXML private Button btnHabDesReserva;
	@FXML private Button btnRefresh;
	@FXML private Button btnBuscar;
	@FXML private TextField ingresarCliente;
	@FXML private TableView<TablaReservaDTO> tablaReservas;
	@FXML private ObservableList<TablaReservaDTO> activeSession;
	@FXML private TableColumn nombreCliente;
	@FXML private TableColumn apellidoCliente;
	@FXML private TableColumn idReserva;
	@FXML private TableColumn estado;
	@FXML private TableColumn fechaReserva;
	@FXML private TableColumn fechaCheckIn;
	@FXML private TableColumn fechaOut;
	@FXML private TableColumn fechaIngreso;
	@FXML private TableColumn fechaEgreso;
	
	private Cliente clientes;
	private ReservaCuarto reserva;
	private Cuarto cuartos;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		this.reserva = new ReservaCuarto(new DAOSQLFactory());
		this.clientes = new Cliente(new DAOSQLFactory());
		this.cuartos =  new Cuarto(new DAOSQLFactory());
		activeSession = FXCollections.observableArrayList();
		cargarColumnas();
		refrescarTabla();
	}

	
	@FXML
	private void refrescarTabla() 
	{
		crearTabla(getAllReservasCuartos());
	}

	private void crearTabla(ObservableList<TablaReservaDTO> allReservasCuartos) 
	{
		tablaReservas.setItems(allReservasCuartos);
	}

	private ObservableList<TablaReservaDTO> getAllReservasCuartos() {
		
		List<ReservaCuartoDTO> reservas = this.reserva.obtenerReservasCuartos();
		List<ClienteDTO> cliente= this.clientes.obtenerClientes();
		List<CuartoDTO> cuarto= this.cuartos.obtenerCuartos();
		ClienteDTO cli = null;
		CuartoDTO cuar = null;
		
		activeSession.clear();
		for(ReservaCuartoDTO r : reservas) {
			for(ClienteDTO c : cliente) {
				if(c.getIdCliente()== r.getIdCliente()) {
					cli = c;
				}
				else {
					continue;
				}
			}
			for(CuartoDTO cu : cuarto) {
				if(cu.getId()== r.getIdCuarto()) {
					cuar = cu;
				}
				else {
					continue;
				}
			}
			TablaReservaDTO tabla = new TablaReservaDTO(r,cli,cuar);
			activeSession.add(tabla);
		 }
		 return activeSession;
	}

	private void cargarColumnas() 
	{
		idReserva.setCellValueFactory(new PropertyValueFactory("idReserva"));		
		nombreCliente.setCellValueFactory(new PropertyValueFactory("nombre"));	
		apellidoCliente.setCellValueFactory(new PropertyValueFactory("apellido"));
		estado.setCellValueFactory(new PropertyValueFactory("estado"));
		fechaReserva.setCellValueFactory(new PropertyValueFactory("fechaReserva"));
		fechaCheckIn.setCellValueFactory(new PropertyValueFactory("fechaCheckIn"));
		fechaOut.setCellValueFactory(new PropertyValueFactory("fechaOut"));	
		fechaIngreso.setCellValueFactory(new PropertyValueFactory("fechaIngreso"));
		fechaEgreso.setCellValueFactory(new PropertyValueFactory("fechaEgreso"));
	}
		
	@FXML
	public void addReservaCuarto() throws Exception  {
		 try { 
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaAgregarReservaCuarto.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
				primaryStage.setScene(new Scene(root));   
				primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
				ControladorAgregarReservaCuarto scene2Controller = fxmlLoader.getController();
				primaryStage.setTitle("Agregar reserva de cuarto");
				primaryStage.sizeToScene();
				primaryStage.show(); 
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	}

}
