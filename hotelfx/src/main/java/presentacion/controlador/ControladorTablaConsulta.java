package presentacion.controlador;

import dto.ClienteDTO;
import dto.CuartoDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import modelo.Cliente;
import modelo.Cuarto;
import modelo.Validador;

public class ControladorTablaConsulta {
	@FXML private Button btnAddCliente;
	@FXML private Button btnEditCliente;
	@FXML private Button btnEditarCliente;
	@FXML private Button btnBuscarCliente;
	@FXML private Button btnHabilitaCliente;
	@FXML private Button btnDeshabilitarCliente;
	@FXML private Button btnCerrar;
	@FXML private TextField ingresarCliente;
	@FXML private TableView<ClienteDTO> tablaPersonas;
	@FXML private ObservableList<ClienteDTO> listaClientes;
	@FXML private VBox fondoTabla;
	@FXML private TableColumn nombre;
	@FXML private TableColumn apellido;
	@FXML private TableColumn telefono;
	@FXML private TableColumn email;
	@FXML private TableColumn numeroDocumento;
	@FXML private TableColumn idCliente;

	@FXML private TableView<CuartoDTO> tablaCuartos;
	@FXML private Button btnAgregarCuarto;
	@FXML private Button btnEditar;
	@FXML private Button btnBorrar;
	@FXML private Button btnBuscar;
	@FXML private Button btnReporte;
	@FXML private TableColumn id;
	@FXML private TableColumn piso;
	@FXML private TableColumn habitacion;
	@FXML private TableColumn capacidad;
	@FXML private TableColumn monto;
	@FXML private TableColumn montoSenia;
	@FXML private TableColumn estado;
	@FXML private ObservableList<CuartoDTO> listaCuartos;
	@FXML private TextField txtBuscar;
	@FXML private Button btnLimpiarFiltro;
	@FXML private Button btnHabilitarCuarto;
	@FXML private Button btnDeshabilitarCuarto;
	
		  private Cuarto cuarto;
		  private Cliente cliente;
		  private Validador validador;
}
