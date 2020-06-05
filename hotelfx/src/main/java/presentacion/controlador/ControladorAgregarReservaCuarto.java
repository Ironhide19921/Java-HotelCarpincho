package presentacion.controlador;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dto.ProductoDTO;
import dto.ReservaCuartoDTO;
import dto.ReservaCuartoDTO.estadosReserva;
import dto.UsuarioDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.ReservaCuarto;
import modelo.Usuario;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorAgregarReservaCuarto implements Initializable{
	
	/*public enum estadosReserva {Pendiente,Cancelado,En_curso,Finalizado};
	private Integer idReserva,idCliente,idCuarto,idUsuario;
	private BigDecimal senia,montoReservaCuarto;
	private String emailFacturacion, numTarjeta, cantidadDias,
	tipoTarjeta, formaDePago, codSeguridadTarjeta,comentarios,fechaVencTarjeta;
	
	private estadosReserva estadoReserva;
	private Timestamp fechaReserva,fechaCheckIn,fechaOut,
	fechaIngreso,fechaEgreso;

	private boolean estado;
*/
	@FXML private Button btnAgregar;
	@FXML private Button btnModificar;
	@FXML private Button btnCerrar;
	@FXML private Button btnAgregarCliente;
	@FXML private Button btnAgregarCuarto;
	@FXML private TextField usuario;
	@FXML private TextField cuarto;
	@FXML private TextField cliente;
	@FXML private TextField email;
	@FXML private TextField senia;
	@FXML private TextField montoSenia;
	@FXML private TextField tipoTarjeta;
	@FXML private TextField numTarjeta;
	@FXML private TextField fechaVecTarjeta;
	@FXML private TextField codSeguridad;
	@FXML private TextArea observaciones;
	@FXML private ComboBox<String> cmbBoxEstados;
	@FXML private ObservableList<String> listaCmbBoxEstados;
	@FXML private ComboBox<String> cmbBoxFormaPago;
	@FXML private ObservableList<String> listaCmbBoxFormaPago;
	@FXML private ComboBox<UsuarioDTO> cmbBoxUsuario;
	@FXML private ObservableList<UsuarioDTO> listaCmbBoxUsuario;
	@FXML private DatePicker fechaIngreso;
	@FXML private DatePicker fechaEgreso;
	@FXML private DatePicker fechaCheckIn;
	@FXML private DatePicker fechaCheckOut;
	@FXML private DatePicker fechaReserva;
	//@FXML private TimerPicker timer;
	private ReservaCuarto reservaCuarto;
	private Usuario usuarios;
	private estadosReserva estados;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.reservaCuarto = new ReservaCuarto(new DAOSQLFactory());
		this.usuarios = new Usuario(new DAOSQLFactory());
		this.listaCmbBoxEstados = FXCollections.observableList(cargarCmbBoxEstados());
		this.cmbBoxEstados.setItems(listaCmbBoxEstados);
		this.listaCmbBoxFormaPago = FXCollections.observableList(cargarCmbBoxFormaPago());
		this.cmbBoxFormaPago.setItems(listaCmbBoxFormaPago);
		this.listaCmbBoxUsuario = FXCollections.observableList(cargarCmbBoxUsuario());
		this.cmbBoxUsuario.setItems(listaCmbBoxUsuario);
	}

	private List<UsuarioDTO> cargarCmbBoxUsuario() {
		List<UsuarioDTO> lista = this.usuarios.obtenerUsuarios();	
		return lista;
	}

	private List<String> cargarCmbBoxFormaPago() {
		List<String> lista = new ArrayList<>();
		lista.add("Efectivo");
		lista.add("Tarjeta débito");
		lista.add("Tarjeta crédito - 1 sólo pago");
		return lista;
	}

	private List<String> cargarCmbBoxEstados() {
		List<String> lista = new ArrayList<>();
		for(estadosReserva s : estados.values()) {
			lista.add(s.name());
		}
		return lista;
	}

	@FXML 
	public void agregarReservaCuarto() 
	{
		ReservaCuartoDTO reserva = obtenerDatosReserva();
		this.reservaCuarto.agregarReservaCuarto(reserva);
	}

	private ReservaCuartoDTO obtenerDatosReserva() {
		LocalDate localInicioReserva =  this.fechaReserva.getValue();
		LocalDate localInicioCheckIn =  this.fechaCheckIn.getValue();
		LocalDate localInicioCheckOut =  this.fechaCheckOut.getValue();
		LocalDate localInicioIngreso =  this.fechaIngreso.getValue();
		LocalDate localInicioEgreso =  this.fechaEgreso.getValue();
		int idCliente = Integer.parseInt(cliente.getText());
		int idCuarto = Integer.parseInt(cuarto.getText());
		int idUsuario = this.cmbBoxUsuario.getValue().getIdPerfil();
		BigDecimal senia = new BigDecimal(this.senia.getText());
		BigDecimal montoReservaCuarto =new BigDecimal(this.montoSenia.getText());
		String emailFacturacion = this.email.getText();
		String numTarjeta = this.numTarjeta.getText();
		String formaDePago = this.cmbBoxFormaPago.getValue();
		String tipoTarjeta = this.tipoTarjeta.getText();
		String codSeguridadTarjeta = this.codSeguridad.getText();
		String fechaVencTarjeta = this.fechaVecTarjeta.getText();
		Timestamp fechaReserva =Timestamp.valueOf(localInicioReserva.atTime(LocalTime.of(8,0,0)));
		Timestamp fechaCheckIn = Timestamp.valueOf(localInicioCheckIn.atTime(LocalTime.of(8,0,0)));
		Timestamp fechaOut = Timestamp.valueOf(localInicioCheckOut.atTime(LocalTime.of(8,0,0)));
		Timestamp fechaIngreso = Timestamp.valueOf(localInicioIngreso.atTime(LocalTime.of(8,0,0)));
		Timestamp fechaEgreso = Timestamp.valueOf(localInicioEgreso.atTime(LocalTime.of(8,0,0)));
		String estadoReserva = this.cmbBoxEstados.getValue();
		String comentarios = this.observaciones.getText();
		boolean estado = true;
	
		ReservaCuartoDTO reserva = new ReservaCuartoDTO(idCliente, idCuarto, idUsuario, senia, montoReservaCuarto,
				emailFacturacion, numTarjeta, formaDePago, tipoTarjeta, codSeguridadTarjeta, fechaVencTarjeta,
				fechaReserva, fechaCheckIn, fechaOut, fechaIngreso, fechaEgreso, estadoReserva, comentarios,estado);
		return reserva;
	}
	
	
	public void setearCampos() {
		
	}
	
	@FXML 
	public void modificarReservaCuarto() 
	{
		ReservaCuartoDTO reserva = obtenerDatosReserva();
		this.reservaCuarto.agregarReservaCuarto(reserva);
	}
	
	
	
	@FXML 
	public void agregarCliente() {
		
	}
	
	@FXML 
	public void agregarCuarto() {
		
	}
	
}
