package presentacion.controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dto.CuartoDTO;
import dto.ReservaCuartoDTO;
import dto.UsuarioDTO;
import dto.ReservaCuartoDTO.EstadoReserva;
import dto.ReservaCuartoDTO.FormaPago;
import dto.ReservaCuartoDTO.TipoTarjeta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.Cuarto;
import modelo.ReservaCuarto;
import modelo.Usuario;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.FxmlLoader;

public class ControladorAgregarReservaCuarto1 implements Initializable {
	//botones
	@FXML private Button btnAgregar;
	@FXML private Button btnModificar;
	@FXML private Button btnCerrar;
	@FXML private Button btnAgregarCliente;
	@FXML private Button btnAgregarCuarto;
	@FXML private Button btnConsultarPendientes;
	//campos
	@FXML private TextField usuario;
	@FXML private TextField cuarto;
	@FXML private TextField cliente;
	@FXML private TextField email;
	@FXML private TextField numTarjeta;
	@FXML private TextField fechaVecTarjeta;
	@FXML private TextField codSeguridad;
	@FXML private TextArea observaciones;
	
	@FXML private TextField senia;
	@FXML private TextField montoSenia;
	@FXML private TextField montoCompleto;
	@FXML private TextField cantidadHoras;
	
	//combos
	@FXML private ComboBox<String> cmbBoxEstados;
	@FXML private ObservableList<String> listaCmbBoxEstados;
	@FXML private ComboBox<String> cmbBoxFormaPago;
	@FXML private ObservableList<String> listaCmbBoxFormaPago;
	@FXML private ComboBox<UsuarioDTO> cmbBoxUsuario;
	@FXML private ObservableList<UsuarioDTO> listaCmbBoxUsuario;
	@FXML private ComboBox<String> cmbBoxTiposTarjeta;
	@FXML private ObservableList<String> listaCmbBoxTiposTarjeta;
	@FXML private ComboBox<Integer> cmbBoxHoraReserva;
	@FXML private ComboBox<Integer> cmbBoxHoraIngreso;
	@FXML private ComboBox<Integer> cmbBoxHoraEgreso;
	@FXML private ComboBox<Integer> cmbBoxHoraCheckIn;
	@FXML private ComboBox<Integer> cmbBoxHoraCheckOut;
	
	@FXML private ObservableList<Integer> listaHorasFin;
	@FXML private ObservableList<Integer> listaCmbBoxHorasReserva;
	@FXML private ObservableList<Integer> listaCmbBoxHorasIngreso;
	@FXML private ObservableList<Integer> listaCmbBoxHorasEgreso;
	@FXML private ObservableList<Integer> listaCmbBoxHorasCheckIn;
	@FXML private ObservableList<Integer> listaCmbBoxHorasCheckOut;

	//fechas
	@FXML private DatePicker fechaIngreso;
	@FXML private DatePicker fechaEgreso;
	@FXML private DatePicker fechaCheckIn;
	@FXML private DatePicker fechaCheckOut;
	@FXML private DatePicker fechaReserva;
	//div
	@FXML private Pane infoTarjeta;
	@FXML private Pane izquierda;
	@FXML private Pane derecha;
	@FXML private Pane fechasCheck;
	//otros
	private ReservaCuarto reservaCuarto;
	private Usuario usuarios;
	private Cuarto cuartos;
	private EstadoReserva estados;
	private TipoTarjeta tipoTarjeta;
	private FormaPago formaPago;
	private Validador validador;
	private int idReserva;
	private FxmlLoader fxml;
	private Stage primaryStage;
	@FXML private BigDecimal montoTotal, cantHoras;
	private ReservaCuartoDTO reserva;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		primaryStage = new Stage(); 
		fxml = new FxmlLoader();
		inicializarElementos();
		inicializarModelo();
		inicializarObservableList();
		
		
	}

	private void inicializarElementos() {
		this.btnConsultarPendientes.setVisible(false);
		
		this.fechasCheck.setVisible(false);
		this.fechaReserva.setDisable(true);
		this.cmbBoxHoraReserva.setDisable(true);
		this.usuario.setEditable(false);
		this.cantidadHoras.setEditable(false);
		this.cmbBoxEstados.setDisable(true);
		this.montoSenia.setEditable(false);
		this.senia.setEditable(false);
		this.montoCompleto.setEditable(false);
		this.infoTarjeta.setVisible(false);
		
		
	}
	
	private void inicializarObservableList() {
		this.listaCmbBoxEstados = FXCollections.observableList(cargarCmbBoxEstados());
		this.cmbBoxEstados.setItems(listaCmbBoxEstados);
		this.listaCmbBoxFormaPago = FXCollections.observableList(cargarCmbBoxFormaPago());
		this.cmbBoxFormaPago.setItems(listaCmbBoxFormaPago);
		this.listaCmbBoxTiposTarjeta = FXCollections.observableList(cargarCmbBoxTiposTarjeta());
		this.cmbBoxTiposTarjeta.setItems(listaCmbBoxTiposTarjeta);
		this.listaCmbBoxHorasIngreso = FXCollections.observableList(cargarCombosHoras());
		this.cmbBoxHoraIngreso.setItems(listaCmbBoxHorasIngreso);
		this.listaCmbBoxHorasReserva = FXCollections.observableList(cargarCombosHoras());
		this.cmbBoxHoraReserva.setItems(listaCmbBoxHorasReserva);
		this.listaCmbBoxHorasEgreso = FXCollections.observableList(cargarCombosHoras());
		this.cmbBoxHoraEgreso.setItems(listaCmbBoxHorasEgreso);
		this.listaCmbBoxHorasCheckIn = FXCollections.observableList(cargarCombosHoras());
		this.cmbBoxHoraCheckIn.setItems(listaCmbBoxHorasCheckIn);
		this.listaCmbBoxHorasCheckOut = FXCollections.observableList(cargarCombosHoras());
		this.cmbBoxHoraCheckOut.setItems(listaCmbBoxHorasCheckOut);
	}

	private void inicializarModelo() {
		this.reservaCuarto = new ReservaCuarto(new DAOSQLFactory());
		this.usuarios = new Usuario(new DAOSQLFactory());
		this.cuartos = new Cuarto(new DAOSQLFactory());
	}


	private List<UsuarioDTO> cargarCmbBoxUsuario() {
		List<UsuarioDTO> lista = this.usuarios.obtenerUsuarios();	
		return lista;
	}

	private List<String> cargarCmbBoxFormaPago() {
		List<String> lista = new ArrayList<>();
		for(FormaPago s : FormaPago.values()) {
			lista.add(s.name());
		}
		return lista;
	}

	private List<String> cargarCmbBoxEstados() {
		List<String> lista = new ArrayList<>();
		for(EstadoReserva s : EstadoReserva.values()) {
			lista.add(s.name());
		}
		return lista;
	}
	
	private List<String> cargarCmbBoxTiposTarjeta() {
		List<String> lista = new ArrayList<>();
		for(TipoTarjeta s : TipoTarjeta.values()) {
			lista.add(s.name());
		}
		return lista;
	}

	private UsuarioDTO obtenerUsuarioConID(Integer id) {
		List<UsuarioDTO> lista = this.usuarios.obtenerUsuarios();	
		for(UsuarioDTO u : lista) {
			if(u.getIdUsuario() == id) {
				return u;
			}
		}	
		return null;
	}
	

	private List<Integer> cargarCombosHorasFin() {
		List<Integer> lista = new ArrayList<>();
		int horaMinima = Integer.valueOf(this.cmbBoxHoraIngreso.getSelectionModel().getSelectedItem().toString()) + 1;
		for(int h = horaMinima; h <= 23; h++) {
			lista.add(h);
		}
		return lista;
	}

	private List<Integer> cargarCombosHoras() {
		List<Integer> lista = new ArrayList<Integer>();
		for(int h = 0; h <= 23; h++) {
			lista.add(h);
		}
		return lista;
	}
	

@FXML
	private ReservaCuartoDTO obtenerDatosReservaValidados() {
		//Datos de pago
		BigDecimal senia = new BigDecimal(this.senia.getText());
		BigDecimal montoReservaCuarto =new BigDecimal(this.montoCompleto.getText());
		BigDecimal montoSenia = new BigDecimal(this.montoSenia.getText());
		String emailFacturacion = this.email.getText();
		TipoTarjeta tipoTarjeta = TipoTarjeta.NO;
		String numeroTarjeta = "0";
		String fechaVencTarjeta = "0";
		String codSeguridadTarjeta = "0";	
		FormaPago formaPago = FormaPago.valueOf(this.cmbBoxFormaPago.getSelectionModel().getSelectedItem());
		if(!(formaPago.equals(FormaPago.EFECTIVO))){
			if(this.cmbBoxTiposTarjeta.getSelectionModel().getSelectedItem() == null || numeroTarjeta == null || fechaVencTarjeta == null || codSeguridadTarjeta == null)
			{
				Validador.mostrarMensaje("Por favor complete todos los campos de la tarjeta.");
				return null;
			}
			tipoTarjeta = TipoTarjeta.valueOf(this.cmbBoxTiposTarjeta.getSelectionModel().getSelectedItem());
			numeroTarjeta = numTarjeta.getText();
			fechaVencTarjeta = fechaVecTarjeta.getText();
			codSeguridadTarjeta = codSeguridad.getText();
		}
		//Obtiene las fechas
		LocalDate localInicioReserva =  this.fechaReserva.getValue();
		LocalDate localInicioIngreso =  this.fechaIngreso.getValue();
		LocalDate localInicioEgreso =  this.fechaEgreso.getValue();
		
		if(ingresoFechas()) {
			Timestamp fechaReserva =Timestamp.valueOf(localInicioReserva.atTime(LocalTime.of(cmbBoxHoraReserva.getSelectionModel().getSelectedItem(),0,0)));
			Timestamp fechaIngreso = Timestamp.valueOf(localInicioIngreso.atTime(LocalTime.of(cmbBoxHoraIngreso.getSelectionModel().getSelectedItem(),0,0)));
			Timestamp fechaEgreso = Timestamp.valueOf(localInicioEgreso.atTime(LocalTime.of(cmbBoxHoraEgreso.getSelectionModel().getSelectedItem()+1,0,0)));
			//Datos de la reserva: estado, cliente, cuarto etc
			EstadoReserva estadoReserva = EstadoReserva.valueOf(this.cmbBoxEstados.getSelectionModel().getSelectedItem());
			String comentarios = this.observaciones.getText();
			boolean estado = true;	
			int idCliente = Integer.parseInt(cliente.getText());
			int idCuarto = Integer.parseInt(cuarto.getText());
			int idUsuario = Integer.parseInt(usuario.getText());
			
			if(idCliente == 0 || idCuarto == 0 || idUsuario == 0) {
				return null;
			}
			//obtengo la reserva con validaciones
			ReservaCuartoDTO reserva = new ReservaCuartoDTO(idCliente, idCuarto, idUsuario, senia, montoReservaCuarto,
					emailFacturacion, numeroTarjeta, formaPago, tipoTarjeta, codSeguridadTarjeta, fechaVencTarjeta,
					fechaReserva, fechaIngreso, fechaEgreso, estadoReserva, comentarios,estado);
			//adicionales a los datos preexistentes de reserva
			reserva.setCantidadDias(this.cantidadHoras.getText());
			reserva.setMontoSenia(montoSenia);
			return reserva;	
		}
		else {
			return null;
		}
		
	}
	
@FXML
public void setearCampos(ReservaCuartoDTO reserva) {
	this.cuarto.setText(reserva.getIdCuarto().toString());
	this.cliente.setText(reserva.getIdCliente().toString());
	this.email.setText(reserva.getEmailFacturacion());
	this.senia.setText(reserva.getSenia().toString());
	this.usuario.setText(reserva.getIdUsuario().toString());
	this.montoSenia.setText(calcularMontoSenia(reserva.getMontoReservaCuarto(),reserva.getSenia()));
	this.montoCompleto.setText(reserva.getMontoReservaCuarto().toString());
	this.cmbBoxTiposTarjeta.setValue(reserva.getTipoTarjeta().name());
	this.numTarjeta.setText(reserva.getNumTarjeta());
	this.fechaVecTarjeta.setText(reserva.getFechaVencTarjeta());
	this.codSeguridad.setText(reserva.getCodSeguridadTarjeta());
	this.observaciones.setText(reserva.getComentarios());
	this.cmbBoxEstados.setValue(reserva.getEstadoReserva().name());
	this.cmbBoxFormaPago.setValue(reserva.getFormaPago().name());
	this.fechaReserva.setValue(reserva.getFechaReserva().toLocalDateTime().toLocalDate());
	this.cmbBoxHoraReserva.setValue(reserva.getFechaReserva().toLocalDateTime().getHour());
	this.fechaIngreso.setValue(reserva.getFechaIngreso().toLocalDateTime().toLocalDate());
	this.cmbBoxHoraIngreso.setValue(reserva.getFechaIngreso().toLocalDateTime().getHour());
	this.fechaEgreso.setValue(reserva.getFechaEgreso().toLocalDateTime().toLocalDate());
	this.cmbBoxHoraEgreso.setValue(reserva.getFechaEgreso().toLocalDateTime().getHour());
	if(reserva.getFechaCheckIn()!=null) { 
		this.fechaCheckIn.setValue(reserva.getFechaCheckIn().toLocalDateTime().toLocalDate());
		this.cmbBoxHoraCheckIn.setValue(reserva.getFechaCheckIn().toLocalDateTime().getHour());
	}
	if(reserva.getFechaOut()!=null) {
		this.fechaCheckOut.setValue(reserva.getFechaOut().toLocalDateTime().toLocalDate());
		this.cmbBoxHoraCheckOut.setValue(reserva.getFechaOut().toLocalDateTime().getHour());
		}
	
	this.cantidadHoras.setText(calcularCantidadDias(reserva));
	}


	public String calcularCantidadDias(ReservaCuartoDTO reserva2) {
	
		Integer horaInicioCombo = reserva2.getFechaIngreso().toLocalDateTime().getHour();	
		Integer horaFinCombo = reserva2.getFechaEgreso().toLocalDateTime().getHour();
		LocalDate localFinReserva = reserva2.getFechaEgreso().toLocalDateTime().toLocalDate();
		LocalDate localInicioReserva = reserva2.getFechaIngreso().toLocalDateTime().toLocalDate();
		long diferenciaHoras = Duration.between(localInicioReserva.atTime(horaInicioCombo, 0),localFinReserva.atTime(horaFinCombo, 0)).toHours();
		
	return diferenciaHoras + "";
}

	public String calcularMontoSenia(BigDecimal montoReservaCuarto, BigDecimal senia2) {
	// TODO Auto-generated method stub
		BigDecimal montoSenia = montoReservaCuarto.multiply(senia2.divide(BigDecimal.valueOf(100)));
		return montoSenia.toString();
}

	@FXML 
	public void modificarReservaCuarto() throws Exception 
	{	
		ReservaCuartoDTO reserva = obtenerDatosReservaValidados();
		reserva.setIdReserva(idReserva);
		
		LocalDate localInicioCheckIn =  this.fechaCheckIn.getValue();
		LocalDate localInicioCheckOut =  this.fechaCheckOut.getValue();
		
		Timestamp fechaCheckIn = Timestamp.valueOf(localInicioCheckIn.atTime(LocalTime.of(cmbBoxHoraCheckIn.getSelectionModel().getSelectedItem(),0,0)));
		Timestamp fechaOut = Timestamp.valueOf(localInicioCheckOut.atTime(LocalTime.of(cmbBoxHoraCheckOut.getSelectionModel().getSelectedItem()+1,0,0)));
	
		if(fechaCheckIn!=null) 
		{
			reserva.setFechaCheckIn(fechaCheckIn);
			this.reservaCuarto.modificarReservaCuarto(reserva);
		}	
		if(fechaCheckIn != null && fechaOut != null)
		{
			reserva.setFechaCheckIn(fechaCheckIn);
			reserva.setFechaOut(fechaOut);
			this.reservaCuarto.modificarReservaCuarto(reserva);				
		}	
	}
	
@FXML 
public void consultarCuarto() {
	 try { 
			if(ingresoFechas()) {
			
			primaryStage.setScene(fxml.getScene("VentanaABMCuarto"));   
			FXMLLoader fxmlLoader = fxml.getFXMLLoader();	
			ControladorABMCuarto scene2Controller = fxmlLoader.getController();
			//scene2Controller.datosReserva(obtenerDatosReserva());
			LocalDate localInicioIngreso = this.fechaIngreso.getValue();
			LocalDate localInicioEgreso = this.fechaEgreso.getValue();
			Timestamp fechaIngreso = Timestamp.valueOf(localInicioIngreso.atTime(LocalTime.of(8,0,0)));
			Timestamp fechaEgreso = Timestamp.valueOf(localInicioEgreso.atTime(LocalTime.of(8,0,0)));
			scene2Controller.consultaReservaCuarto(fechaEgreso,fechaIngreso);
			scene2Controller.enviarControlador(this);
			scene2Controller.modificarBotones(true);
			fxml.mostrarStage(primaryStage, "Consulta de cuartos disponible");
			
			}
			else {
				Validador.mostrarMensaje("La fecha y hora de ingreso y egreso son obligatorias para realizar esta consulta.");
			}		
	     } catch(Exception e) { 
	      e.printStackTrace(); 
	     } 
}

	@FXML 
	public void consultarCliente() {
	 try { 
			primaryStage.setScene(fxml.getScene("VentanaABMCliente"));   
			FXMLLoader fxmlLoader = fxml.getFXMLLoader();	
			ControladorABMCliente scene2Controller = fxmlLoader.getController();
			scene2Controller.modificarBotonesReserva();
	 		scene2Controller.getStage(primaryStage,this);
	 		fxml.mostrarStage(primaryStage, "Consulta de clientes disponible");
	       			
	     } catch(Exception e) { 
	      e.printStackTrace(); 
	     } 
	}
	
	
	
	@FXML 
	public void agregarReservaCuarto() throws Exception 
	{
		
		
		if(Validador.validarReserva(this)) {
			ReservaCuartoDTO reserva = obtenerDatosReservaValidados();	
			this.reservaCuarto.agregarReservaCuarto(reserva);
			Validador.mostrarMensaje("Su reserva ha sido agregada con exito");
		}
		else{
			Validador.mostrarMensaje("Complete todos los campos obligatorios (*).");
		}
	}

	@FXML 
	private void mostrarDatosTarjeta() {
		if(this.cmbBoxFormaPago.getValue().equals(FormaPago.EFECTIVO.name())){
			this.infoTarjeta.setVisible(false);
		}
		else {
			this.infoTarjeta.setVisible(true);
		}
	}

	@FXML
	public void consultarPendientes() throws IOException {
		primaryStage.setScene(fxml.getScene("VentanaABMOrdenPedido"));   
		FXMLLoader fxmlLoader = fxml.getFXMLLoader();	
		ControladorABMOrdenPedido controlador = fxmlLoader.getController();
		//controlador.enviarIdReserva(idReserva, this);
		//controlador.modificarBotones();
		fxml.mostrarStage(primaryStage, "Consulta de orden de pedido del cliente");
	}

	@FXML
	public boolean ingresoFechas() {
		LocalDate localInicioReserva =  this.fechaReserva.getValue();
	//	LocalDate localInicioCheckIn =  this.fechaCheckIn.getValue();
	//	LocalDate localInicioCheckOut =  this.fechaCheckOut.getValue();
		LocalDate localInicioIngreso =  this.fechaIngreso.getValue();
		LocalDate localInicioEgreso =  this.fechaEgreso.getValue();
		boolean ingresoFechas = localInicioReserva!=null 
		&& localInicioIngreso!=null
		&& localInicioEgreso!=null
		&& this.cmbBoxHoraIngreso.getSelectionModel().getSelectedItem()!=null	
		&& this.cmbBoxHoraEgreso.getSelectionModel().getSelectedItem()!=null	
		&& this.cmbBoxHoraReserva.getSelectionModel().getSelectedItem()!=null	
		;
		return ingresoFechas;
	}


public void modificarCliente(int idCliente)
{
	this.cliente.setText(idCliente+"");
}


public void modificarCuarto(int idCuarto) 
{
	this.cuarto.setText(idCuarto+"");
}
	

public void modificarPantallaConsulta(ReservaCuartoDTO reserva) {
	
	ocultarBotonesConsulta();
	camposSoloLectura();
	this.btnConsultarPendientes.setVisible(true);
	this.reserva =  reserva;

}

public void pasarIdReserva(int id) {
	this.idReserva = id;
	System.out.print(idReserva);
}

private void camposSoloLectura() {
	this.cuarto.setDisable(true);
	this.cliente.setDisable(true);
	this.email.setDisable(true);
	//this.senia.setDisable(true);
	//this.montoSenia.setDisable(true);
	this.cmbBoxTiposTarjeta.setDisable(true);
	this.numTarjeta.setDisable(true);
	this.fechaVecTarjeta.setDisable(true);
	this.codSeguridad.setDisable(true);
	this.observaciones.setDisable(true);
	this.cmbBoxEstados.setDisable(true);
	this.cmbBoxFormaPago.setDisable(true);
	this.cmbBoxUsuario.setDisable(true);
	this.fechaIngreso.setDisable(true);
	this.fechaEgreso.setDisable(true);
	this.fechaCheckIn.setDisable(true);
	this.fechaCheckOut.setDisable(true);
	this.fechaReserva.setDisable(true);
}

private void ocultarBotonesConsulta() {
	this.btnAgregar.setVisible(false);
//	this.btnModificar.setVisible(false);
	this.btnCerrar.setVisible(false);
	this.btnAgregarCliente.setVisible(false);
	this.btnAgregarCuarto.setVisible(false);
}


@FXML
public void verificarFechasCheck() throws Exception {
	LocalDate localFinReserva = fechaCheckOut.getValue();
	LocalDate localInicioReserva = fechaCheckIn.getValue();
	LocalDate localReserva = fechaReserva.getValue();
	
	if(!(localInicioReserva==null) && !localReserva.isBefore(localInicioReserva)) {
		fechaCheckIn.setValue(null);
		Validador.mostrarMensaje("Fecha de ingreso inválida");
		return;
	}
	if(!(localFinReserva == null) && !(localInicioReserva == null)) {
		if((localFinReserva.isBefore(localInicioReserva))) {
			this.fechaCheckOut.setValue(null);	
			this.cmbBoxHoraCheckOut.setItems(null);
			Validador.mostrarMensaje("Error fecha de finalización de reserva anterior al inicio del mismo.");
			return;
		}
		else {
			this.cmbBoxHoraCheckOut.setItems(null);
			this.listaHorasFin = FXCollections.observableList(cargarCombosHoras());
			this.cmbBoxHoraCheckOut.setItems(listaHorasFin);
			verificarHoras();
		}
	}
}

@FXML
public void verificarFechas() throws Exception {
	LocalDate localFinReserva = fechaEgreso.getValue();
	LocalDate localInicioReserva = fechaIngreso.getValue();
	LocalDate localReserva = fechaReserva.getValue();
	if(!(localInicioReserva==null) && !localReserva.isBefore(localInicioReserva)) {
		fechaIngreso.setValue(null);
		Validador.mostrarMensaje("Fecha de ingreso inválida");
		return;
	}
	if(!(localFinReserva == null) && !(localInicioReserva == null)) {
		if((localFinReserva.isBefore(localInicioReserva))) {
			
			this.fechaEgreso.setValue(null);	
			this.cmbBoxHoraEgreso.setItems(null);
			Validador.mostrarMensaje("Error fecha de finalización de reserva anterior al inicio del mismo.");
			return;
			
		}
		else {
			this.cmbBoxHoraEgreso.setItems(null);
			this.listaCmbBoxHorasEgreso = FXCollections.observableList(cargarCombosHoras());
			this.cmbBoxHoraEgreso.setItems(listaCmbBoxHorasEgreso);
			verificarHoras();
		}
	}
}


@FXML
public void verificarHoras() throws Exception {
	Integer horaInicioCombo = cmbBoxHoraIngreso.getValue();	
	Integer horaFinCombo = cmbBoxHoraEgreso.getValue();
	
	LocalDate localFinReserva = fechaEgreso.getValue();
	LocalDate localInicioReserva = fechaIngreso.getValue();
	
	if(localInicioReserva.equals(localFinReserva)) {
		//si son iguales entonces condiciono las horas
		if(horaInicioCombo != null && horaFinCombo == null || (horaInicioCombo != null && horaFinCombo != null && horaInicioCombo >= horaFinCombo)) {
			this.cmbBoxHoraEgreso.setItems(null);
			this.listaCmbBoxHorasEgreso = FXCollections.observableList(cargarCombosHorasFin());
			this.cmbBoxHoraEgreso.setItems(listaCmbBoxHorasEgreso);
		}
	}
	
	else if(this.cmbBoxHoraEgreso == null){
		//si no son iguales entonces las horas están s/n
		this.cmbBoxHoraEgreso.setItems(null);
		this.listaCmbBoxHorasEgreso = FXCollections.observableList(cargarCombosHoras());
		this.cmbBoxHoraEgreso.setItems(listaCmbBoxHorasEgreso);
	}
}

@FXML
public void verificarHorasCheck() throws Exception {
	Integer horaInicioCombo = cmbBoxHoraCheckIn.getValue();	
	Integer horaFinCombo = cmbBoxHoraCheckOut.getValue();
	
	LocalDate localFinReserva = fechaCheckOut.getValue();
	LocalDate localInicioReserva = fechaCheckIn.getValue();
	
	if(localInicioReserva.equals(localFinReserva)) {
		//si son iguales entonces condiciono las horas
		if(horaInicioCombo != null && horaFinCombo == null || (horaInicioCombo != null && horaFinCombo != null && horaInicioCombo >= horaFinCombo)) {
			this.cmbBoxHoraCheckOut.setItems(null);
			this.listaCmbBoxHorasEgreso = FXCollections.observableList(cargarCombosHorasFin());
			this.cmbBoxHoraCheckOut.setItems(listaCmbBoxHorasEgreso);
		}
	}
	
	else if(this.cmbBoxHoraCheckOut == null){
		//si no son iguales entonces las horas están s/n
		this.cmbBoxHoraCheckOut.setItems(null);
		this.listaCmbBoxHorasEgreso = FXCollections.observableList(cargarCombosHoras());
		this.cmbBoxHoraCheckOut.setItems(listaCmbBoxHorasEgreso);
	}
}


@FXML
public void verMontoTotalySenia() {
	Integer horaInicioCombo = cmbBoxHoraIngreso.getValue();
	Integer horaFinCombo = cmbBoxHoraEgreso.getValue();
	LocalDate localFinReserva = fechaEgreso.getValue();
	LocalDate localInicioReserva = fechaIngreso.getValue();
		
	if(!(horaInicioCombo == null) && !(horaFinCombo == null)) {
		long porcentaje = 0;
		
		//calculo porcentaje segun cantidad de reservas
		if(this.cliente.getText().isEmpty()) {
			Validador.mostrarMensaje("Ingrese un cliente valido");
			cmbBoxHoraEgreso.getSelectionModel().clearSelection();
			return;
		}
		
		List<ReservaCuartoDTO> reservas = this.reservaCuarto.buscarReservaCuartoCliente(Integer.parseInt(cliente.getText()));
		
		if(reservas.size()>0) {
			porcentaje = porcentaje + (5 * reservas.size());
		}
		
		List<CuartoDTO> cuarto = this.cuartos.obtenerCuarto(Integer.parseInt(this.cuarto.getText())); 
		if(cuarto.size()>0) {
			this.montoTotal = BigDecimal.valueOf(cuarto.get(0).getMonto());
			this.senia.setText(String.valueOf(cuarto.get(0).getMontoSenia()));		
			long diferenciaHoras = Duration.between(localInicioReserva.atTime(horaInicioCombo, 0),localFinReserva.atTime(horaFinCombo, 0)).toHours();
			int senia = cuarto.get(0).getMontoSenia();
			this.cantHoras = BigDecimal.valueOf(diferenciaHoras);
			BigDecimal porcentajeSenia = BigDecimal.valueOf(cuarto.get(0).getMontoSenia());
			long porcentajeDescuento = 100 - porcentaje;
			BigDecimal montoFinal = montoTotal.multiply(BigDecimal.valueOf(diferenciaHoras));
			BigDecimal montoFinalDescuento = montoFinal.multiply(BigDecimal.valueOf(porcentajeDescuento)).divide(BigDecimal.valueOf(100));
			BigDecimal montoSenia = montoFinalDescuento.multiply(BigDecimal.valueOf(senia).divide(BigDecimal.valueOf(100)));
			this.montoSenia.setText(String.valueOf(montoSenia));	
			this.montoCompleto.setText(String.valueOf(montoFinalDescuento));
			this.cantidadHoras.setText(String.valueOf(cantHoras));
		}
		
	}		
	else {
		this.cantHoras =  BigDecimal.valueOf(0);
		this.cantidadHoras.setText(String.valueOf(cantHoras));
	}
	
}


public void setearCamposAgregar() {
	this.montoSenia.setText("0");
	this.senia.setText("0");
	this.montoCompleto.setText("0");
	this.cantHoras = BigDecimal.valueOf(0);
	this.cantidadHoras.setText("0");
	this.email.setText("-");
	this.cliente.setText("0");
	this.cuarto.setText("0");
	this.cmbBoxFormaPago.setValue(FormaPago.EFECTIVO.name());
}

public void setearCamposModificar() {
	this.fechasCheck.setVisible(true);
	if(this.cmbBoxFormaPago.getValue().equals(FormaPago.EFECTIVO.name())){
		this.infoTarjeta.setVisible(false);
	}
	else {
		this.infoTarjeta.setVisible(true);
	}
	
	
}


public TextField getCuarto() {
	return cuarto;
}

public TextField getCliente() {
	return cliente;
}

public TextField getEmail() {
	return email;
}

public TextField getSenia() {
	return senia;
}

public TextField getMontoSenia() {
	return montoSenia;
}


public TextField getMontoCompleto() {
	return montoCompleto;
}


public TextField getNumTarjeta() {
	return numTarjeta;
}

public TextField getFechaVecTarjeta() {
	return fechaVecTarjeta;
}


public TextField getCodSeguridad() {
	return codSeguridad;
}

public TextArea getObservaciones() {
	return observaciones;
}

public ComboBox<String> getCmbBoxEstados(EstadoReserva pendiente) {
	return cmbBoxEstados;
}

public ComboBox<String> getCmbBoxFormaPago() {
	return cmbBoxFormaPago;
}

public ComboBox<Integer> getCmbBoxHoraEgreso() {
	return this.cmbBoxHoraEgreso;
}
public ComboBox<Integer> getCmbBoxHoraIngreso() {
	return this.cmbBoxHoraIngreso;
}
public ComboBox<Integer> getCmbBoxHoraReserva() {
	return cmbBoxHoraReserva;
}
public ComboBox<Integer> getCmbBoxHoraCheckIn() {
	return cmbBoxHoraCheckIn;
}
public ComboBox<Integer> getCmbBoxHoraCheckOut() {
	return cmbBoxHoraCheckOut;
}

public ComboBox<UsuarioDTO> getCmbBoxUsuario() {
	return cmbBoxUsuario;
}

public DatePicker getFechaIngreso() {
	return fechaIngreso;
}

public DatePicker getFechaEgreso() {
	return fechaEgreso;
}


public DatePicker getFechaCheckIn() {
	return fechaCheckIn;
}

public DatePicker getFechaCheckOut() {
	return fechaCheckOut;
}

public DatePicker getFechaReserva() {
	return fechaReserva;
}

public ReservaCuarto getReservaCuarto() {
	return reservaCuarto;
}

public EstadoReserva getEstados() {
	return EstadoReserva.valueOf(this.cmbBoxEstados.getSelectionModel().getSelectedItem());
}


public void setCmbBoxEstados(EstadoReserva pendiente) {

	this.cmbBoxEstados.setValue(pendiente.name());
}

public void habilitarComboEstados() {
	this.cmbBoxEstados.setDisable(false);
}

public void setFechaReserva(Timestamp timestamp) {

	this.fechaReserva.setValue(timestamp.toLocalDateTime().toLocalDate());
	this.cmbBoxHoraReserva.setValue(timestamp.toLocalDateTime().getHour());
}

public void setCmbBoxUsuarioFirst() {

	this.cmbBoxUsuario.getSelectionModel().selectFirst(); 
}

public BigDecimal getMontoTotal() {
	return montoTotal;
}

public void setMontoTotal(BigDecimal montoTotal) {
	this.montoTotal = montoTotal;
}

public BigDecimal getCantidadHoras() {
	return cantHoras;
}

public void setCantidadHoras(BigDecimal cantidadHoras) {
	this.cantHoras = cantidadHoras;
}


public void setUsuario(UsuarioDTO usuarioLogeado) {
	// TODO Auto-generated method stub
	this.usuario.setText(usuarioLogeado.getIdUsuario() + "");
}

public TipoTarjeta getTipoTarjeta() {
	// TODO Auto-generated method stub
	return TipoTarjeta.valueOf(this.cmbBoxTiposTarjeta.getSelectionModel().getSelectedItem());
}

public String getComentarios() {
	// TODO Auto-generated method stub
	return this.observaciones.getText();
}

public String getUsuario() {
	return this.usuario.getText();
}

public FormaPago getFormaPago() {
	// TODO Auto-generated method stub
	return FormaPago.valueOf(this.cmbBoxFormaPago.getSelectionModel().getSelectedItem());
}



}
