package presentacion.controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.joda.time.DateTime;

import dto.CategoriaEventoDTO;
import dto.ClienteDTO;
import dto.ProductoDTO;
import dto.ReservaEventoConNombresDTO;
import dto.ReservaEventoDTO;
import dto.ReservaEventoDTO.EstadoReserva;
import dto.ReservaEventoDTO.FormaPago;
import dto.ReservaEventoDTO.TipoTarjeta;
import dto.SalonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.CategoriaEvento;
import modelo.Cliente;
import modelo.ReservaEvento;
import modelo.Salon;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.FxmlLoader;

public class ControladorAgregarReservaEvento implements Initializable{

	@FXML
	private Button btnAgregar;
	@FXML
	private Button btnModificar;
	@FXML private ObservableList<String> listaSalones;
	@FXML private ComboBox<String> comboSalones;
	@FXML private ObservableList<String> listaCategoriasEvento;
	@FXML private ComboBox<String> comboCategoriasEvento;
	@FXML private ObservableList<String> listaEstados;
	@FXML private ComboBox<String> comboEstados;
	@FXML private ObservableList<String> listaTarjetas;
	@FXML private ComboBox<String> comboTarjetas;
	@FXML private ObservableList<String> listaFormasPago;
	@FXML private ComboBox<String> comboFormasPago;
	@FXML private ObservableList<Integer> listaHoraInicio;
	@FXML private ComboBox<Integer> comboHoraInicio;
	@FXML private ObservableList<Integer> listaHoraFin;
	@FXML private ComboBox<Integer> comboHoraFin;
	@FXML private ObservableList<String> listaClientes;
	@FXML private ComboBox<String> comboClientes;
	@FXML private BorderPane panelBorde;
	
	@FXML private Text txtCabeza;
	@FXML private Text textoDatosTarjeta;
	@FXML private Text textoFechaVencimientoTarjeta;
	
	@FXML private TextField tipoTarjeta;
	@FXML private TextField codSeguridadTarjeta;
	@FXML private TextField fechaVencTarjeta;
	@FXML private TextField numeroTarjeta;
	@FXML private TextField formaPago;
	@FXML private TextField montoTotal;
	@FXML private TextField montoReserva;
	@FXML private TextField senia;
	private Timestamp fechaGeneracionReserva;
	@FXML private DatePicker fechaInicioReserva;
	@FXML private DatePicker fechaFinReserva;
	@FXML private TextArea observaciones;
	@FXML private TextField cantHoras;
	private ReservaEvento reserva;
	private Cliente cliente;
	private Salon salon;
	private CategoriaEvento categoriaEvento;
	private ClienteDTO clienteActual;
	private int idCliente;
	private int idUsuario = 1;
	private Alert alert;
	private Integer idReservaEvento;
	private Timestamp FechaIngreso = null;
	private Timestamp FechaEgreso = null;
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(this.idCliente != 0) {
			
			this.cliente = new Cliente(new DAOSQLFactory());
			this.reserva = new ReservaEvento(new DAOSQLFactory());
			this.salon = new Salon(new DAOSQLFactory());
			this.categoriaEvento = new CategoriaEvento(new DAOSQLFactory());
			this.listaSalones = FXCollections.observableList(cargarComboSalones());
			this.comboSalones.setItems(listaSalones);	
			this.listaCategoriasEvento = FXCollections.observableList(cargarComboCategoriasEvento());
			this.comboCategoriasEvento.setItems(listaCategoriasEvento);
			
			this.listaClientes = FXCollections.observableList(cargarComboClientes());
			this.comboClientes.setItems(listaClientes);
			
			this.listaFormasPago = FXCollections.observableList(cargarComboFormasPago());
			this.comboFormasPago.setItems(listaFormasPago);
			this.listaTarjetas = FXCollections.observableList(cargarComboTarjetas());
			this.comboTarjetas.setItems(listaTarjetas);
			this.listaHoraInicio = FXCollections.observableList(cargarCombosHoras());
			this.comboHoraInicio.setItems(listaHoraInicio);
			this.listaEstados = FXCollections.observableList(cargarComboEstados());
			this.comboEstados.setItems(listaEstados);
			
			if(this.idCliente != -1) {
				this.clienteActual = this.cliente.getClientePorId(idCliente);
				this.txtCabeza.setText(clienteActual.getApellido() + " " + clienteActual.getNombre());	
			}
			
			this.alert = new Alert(AlertType.INFORMATION);
		}
			
	}
	



	void initData(int id) {
		this.idCliente = id;
		
		initialize(null, null);
		
	}

	@FXML
    public void agregarReservaEventoCliente() throws Exception {
		this.fechaGeneracionReserva = new Timestamp(System.currentTimeMillis());
		
		TipoTarjeta tipoTarjeta = TipoTarjeta.NO;
		String NumeroTarjeta = "0";
		String FechaVencTarjeta = "0";
		String CodSeguridadTarjeta = "0";	
		FormaPago formaPago = FormaPago.valueOf(comboFormasPago.getValue());
		if(!(formaPago.equals(FormaPago.EFECTIVO))){
			tipoTarjeta = TipoTarjeta.valueOf(comboTarjetas.getValue());
			NumeroTarjeta = numeroTarjeta.getText();
			FechaVencTarjeta = fechaVencTarjeta.getText();
			CodSeguridadTarjeta = codSeguridadTarjeta.getText();	
		}

		//QUIEro DEJAR PENDIENTE SELECCIONADO
		
		String comboSalonSeleccionado = this.comboSalones.getSelectionModel().getSelectedItem().toString();
		int idSalon = obtenerSalonSeleccionado(comboSalonSeleccionado);
		
		String comboCategoriaEventoSeleccionado = this.comboCategoriasEvento.getSelectionModel().getSelectedItem().toString();
		int idCategoriaEvento = obtenerCategoriaSeleccionada(comboCategoriaEventoSeleccionado);
		
		
		BigDecimal Senia =  new BigDecimal(senia.getText());
		BigDecimal MontoReservaEvento = new BigDecimal(montoReserva.getText());
		BigDecimal MontoTotal = new BigDecimal(montoTotal.getText());
		
		//la reserva se hace por día, desde las 0800
		LocalDate localInicioReserva = fechaInicioReserva.getValue();
		
		Timestamp FechaInicioReserva = Timestamp.valueOf(localInicioReserva.atTime(LocalTime.of(comboHoraInicio.getSelectionModel().getSelectedItem(),0,0)));
		//la reserva termina a las 0000
		LocalDate localFinReserva = fechaFinReserva.getValue();
		Timestamp FechaFinReserva;
		
		
		if(comboHoraFin.getSelectionModel().getSelectedItem() != 23) {
			FechaFinReserva = Timestamp.valueOf(localFinReserva.atTime(LocalTime.of(comboHoraFin.getSelectionModel().getSelectedItem() + 1,0,0)));	
		}
		else {
			localFinReserva = localFinReserva.plusDays(1);
			FechaFinReserva = Timestamp.valueOf(localFinReserva.atTime(LocalTime.of(0,0,0)));
		}
		EstadoReserva estado = EstadoReserva.valueOf(comboEstados.getValue());
		String Observaciones = observaciones.getText();

		List<ReservaEventoDTO> reservas = this.reserva.verReservasEntreFechas(FechaInicioReserva, FechaFinReserva);
		if(reservas.size() >= 1) {
			List<ReservaEventoDTO> reservasMismoSalon = new ArrayList<ReservaEventoDTO>();;
			
			//si hay una reserva en el array que tenga el mismo salon, la agrego al nuevo array y la muestro, sino inserto lo más bien.
			for(ReservaEventoDTO r : reservas) {
				if(r.getIdSalon() == idSalon)
				reservasMismoSalon.add(r);
			}
			String mensaje = "Encontramos algunas reservas para ese salon\n\n";
			if(!reservasMismoSalon.isEmpty()) {
				for(ReservaEventoDTO r : reservasMismoSalon) {
					mensaje += " - del " + r.getFechaInicioReserva() + " al " + r.getFechaFinReserva() + ".\n\n";
				}
				mensaje += "Por favor elija otra fecha o salon.\n";
				mostrarMensaje(mensaje);
			}
			else {
				mostrarMensaje("Reserva dada de alta correctamente");
				//cuando se agrega hay que enviar un mail al cliente
				ReservaEventoDTO nuevaReserva = new ReservaEventoDTO(0, this.idCliente, this.idUsuario, idSalon, idCategoriaEvento, Senia, MontoReservaEvento, MontoTotal, this.fechaGeneracionReserva, FechaInicioReserva, FechaFinReserva, this.FechaIngreso, this.FechaEgreso, formaPago, tipoTarjeta, NumeroTarjeta, FechaVencTarjeta, CodSeguridadTarjeta, estado, Observaciones);
				this.reserva.agregarReservaEvento(nuevaReserva);
				cerrarVentana();
			}
		}
		else {
			
			ReservaEventoDTO nuevaReserva = new ReservaEventoDTO(0, this.idCliente, this.idUsuario, idSalon, idCategoriaEvento, Senia, MontoReservaEvento, MontoTotal, this.fechaGeneracionReserva, FechaInicioReserva, FechaFinReserva, this.FechaIngreso, this.FechaEgreso, formaPago, tipoTarjeta, NumeroTarjeta, FechaVencTarjeta, CodSeguridadTarjeta, estado, Observaciones);
			System.out.println(this.idCliente);
			
			this.reserva.agregarReservaEvento(nuevaReserva);
			mostrarMensaje("Reserva dada de alta correctamente");
			//cuando se agrega hay que enviar un mail al cliente
			cerrarVentana();
		}
    }
	
	@FXML
	public void modificarReservaEventoCliente() throws IOException {
		
		this.fechaGeneracionReserva = new Timestamp(System.currentTimeMillis());
		
		TipoTarjeta tipoTarjeta = TipoTarjeta.NO;
		String NumeroTarjeta = "0";
		String FechaVencTarjeta = "0";
		String CodSeguridadTarjeta = "0";	
		FormaPago formaPago = FormaPago.valueOf(comboFormasPago.getValue());
		
		if(!(formaPago.equals(FormaPago.EFECTIVO))){
			tipoTarjeta = TipoTarjeta.valueOf(comboTarjetas.getValue());
			NumeroTarjeta = numeroTarjeta.getText();
			FechaVencTarjeta = fechaVencTarjeta.getText();
			CodSeguridadTarjeta = codSeguridadTarjeta.getText();	
		}
		
		String comboSalonSeleccionado = this.comboSalones.getSelectionModel().getSelectedItem().toString();
		int idSalon = obtenerSalonSeleccionado(comboSalonSeleccionado);
		
		String comboCategoriaEventoSeleccionado = this.comboCategoriasEvento.getSelectionModel().getSelectedItem().toString();
		int idCategoriaEvento = obtenerCategoriaSeleccionada(comboCategoriaEventoSeleccionado);
		
		BigDecimal Senia =  new BigDecimal(senia.getText());
		BigDecimal MontoReservaEvento = new BigDecimal(montoReserva.getText());
		BigDecimal MontoTotal = new BigDecimal(montoTotal.getText());
		
		LocalDate localInicioReserva = fechaInicioReserva.getValue();
		Timestamp FechaInicioReserva = Timestamp.valueOf(localInicioReserva.atTime(LocalTime.of(comboHoraInicio.getSelectionModel().getSelectedItem(),0,0)));
		
		LocalDate localFinReserva = fechaFinReserva.getValue();
		Timestamp FechaFinReserva = Timestamp.valueOf(localFinReserva.atTime(LocalTime.of(comboHoraFin.getSelectionModel().getSelectedItem(),0,0)));
		
		if(comboHoraFin.getSelectionModel().getSelectedItem() != 23) {
			FechaFinReserva = Timestamp.valueOf(localFinReserva.atTime(LocalTime.of(comboHoraFin.getSelectionModel().getSelectedItem() + 1,0,0)));	
		}
		else {
			localFinReserva = localFinReserva.plusDays(1);
			FechaFinReserva = Timestamp.valueOf(localFinReserva.atTime(LocalTime.of(0,0,0)));
		}
				
		EstadoReserva estado = EstadoReserva.valueOf(comboEstados.getValue());
		String Observaciones = observaciones.getText();
		
		List<ReservaEventoDTO> reservas = this.reserva.verReservasEntreFechas(FechaInicioReserva, FechaFinReserva);
		if(reservas.size() >= 1) {
			List<ReservaEventoDTO> reservasMismoSalon = new ArrayList<ReservaEventoDTO>();;
			
			//si hay una reserva en el array que tenga el mismo salon, la agrego al nuevo array y la muestro, sino inserto lo más bien.
			//si esa reserva que hay tiene el mismo id que la nuestra(es decir es la misma) no la agrego a la lista de reservas del mismo salon.
			for(ReservaEventoDTO r : reservas) {
				if(r.getIdSalon() == idSalon && r.getIdReservaEvento() != this.idReservaEvento) {
					reservasMismoSalon.add(r);	
				}
			}
			String mensaje = "Encontramos algunas reservas para ese salon\n\n";
			if(!reservasMismoSalon.isEmpty()) {
				for(ReservaEventoDTO r : reservasMismoSalon) {
					mensaje += " - del " + r.getFechaInicioReserva() + " al " + r.getFechaFinReserva() + ".\n\n";
				}
				mensaje += "Por favor elija otra fecha o salon.\n";
				mostrarMensaje(mensaje);
			}
			else {
				mostrarMensaje("Reserva modificada correctamente");
				ReservaEventoDTO nuevaReserva = new ReservaEventoDTO(this.idReservaEvento, this.idCliente, this.idUsuario, idSalon, idCategoriaEvento, Senia, MontoReservaEvento, MontoTotal, this.fechaGeneracionReserva, FechaInicioReserva, FechaFinReserva, FechaIngreso, FechaEgreso, formaPago, tipoTarjeta, NumeroTarjeta, FechaVencTarjeta, CodSeguridadTarjeta, estado, Observaciones);
				this.reserva.modificarReservaEvento(nuevaReserva);
				cerrarVentana();
			}
		}
		else {
			
			ReservaEventoDTO nuevaReserva = new ReservaEventoDTO(this.idReservaEvento, this.idCliente, this.idUsuario, idSalon, idCategoriaEvento, Senia, MontoReservaEvento, MontoTotal, this.fechaGeneracionReserva, FechaInicioReserva, FechaFinReserva, FechaIngreso, FechaEgreso, formaPago, tipoTarjeta, NumeroTarjeta, FechaVencTarjeta, CodSeguridadTarjeta, estado, Observaciones);
			this.reserva.modificarReservaEvento(nuevaReserva);
			mostrarMensaje("Reserva modificada correctamente");
			cerrarVentana();
		}
		

	}
	
	@FXML
	public void verificarFormaPago() throws Exception {
		
		if(!(this.comboFormasPago.getSelectionModel().getSelectedItem().equals(FormaPago.EFECTIVO.name()))) {
			this.comboTarjetas.setVisible(true);
			this.codSeguridadTarjeta.setVisible(true);
			this.fechaVencTarjeta.setVisible(true);
			this.numeroTarjeta.setVisible(true);
			this.textoDatosTarjeta.setVisible(true);
			this.textoFechaVencimientoTarjeta.setVisible(true);
		}
		else{	
			this.comboTarjetas.setVisible(false);
			this.codSeguridadTarjeta.setVisible(false);
			this.fechaVencTarjeta.setVisible(false);
			this.numeroTarjeta.setVisible(false);
			this.textoDatosTarjeta.setVisible(false);
			this.textoFechaVencimientoTarjeta.setVisible(false);
		}	
		
	}
	
	
	

	@FXML
	public void verPrecioSalon() throws Exception {
		String salonCombo = comboSalones.getValue();
		BigDecimal monto = new BigDecimal(0);
		String[] datos = salonCombo.split("-");
		int idSalonSeleccionado = Integer.valueOf(datos[0]);
		
		for(SalonDTO c : this.salon.obtenerSalones()) {
			if(c.getId() == idSalonSeleccionado) {
				monto = c.getMonto();
			}
		}
		this.montoReserva.setText(String.valueOf(monto));
		verificarFechas();
	}
	
	@FXML
	public void verificarFechas() throws Exception {
		LocalDate localFinReserva = fechaFinReserva.getValue();
		LocalDate localInicioReserva = fechaInicioReserva.getValue();
		
		
		if(!(localFinReserva == null) && !(localInicioReserva == null)) {
			if((localFinReserva.isBefore(localInicioReserva))) {
				this.fechaFinReserva.setValue(null);
				this.montoTotal.setText(null);
				this.senia.setText(null);
				this.cantHoras.setText(null);
				this.comboHoraFin.setItems(null);
				this.listaHoraFin = FXCollections.observableList(cargarCombosHoras());
				this.comboHoraFin.setItems(listaHoraFin);
				
				mostrarMensaje("Error fecha de finalización del evento anterior al inicio del mismo.");
			}
			else {
				this.comboHoraFin.setItems(null);
				this.listaHoraFin = FXCollections.observableList(cargarCombosHoras());
				this.comboHoraFin.setItems(listaHoraFin);
				this.montoTotal.setText(null);
				this.senia.setText(null);
				this.cantHoras.setText(null);
				verificarHoras();
			}
			
		}
	}
	
	@FXML
	public void verificarHoras() throws Exception {
		Integer horaInicioCombo = comboHoraInicio.getValue();	
		Integer horaFinCombo = comboHoraFin.getValue();
		
		LocalDate localFinReserva = fechaFinReserva.getValue();
		LocalDate localInicioReserva = fechaInicioReserva.getValue();
		
		if(localInicioReserva.equals(localFinReserva)) {
			//si son iguales entonces condiciono las horas
			if(horaInicioCombo != null && horaFinCombo == null || (horaInicioCombo != null && horaFinCombo != null && horaInicioCombo >= horaFinCombo)) {
				this.comboHoraFin.setItems(null);
				this.listaHoraFin = FXCollections.observableList(cargarCombosHorasFin());
				this.comboHoraFin.setItems(listaHoraFin);
				this.montoTotal.setText(null);
				this.senia.setText(null);	
				this.cantHoras.setText(null);
			}
			else {
				verMontoTotalySenia();
			}
		}
		else if(this.comboHoraFin == null){
			//si no son iguales entonces las horas están s/n
			this.comboHoraFin.setItems(null);
			this.listaHoraFin = FXCollections.observableList(cargarCombosHoras());
			this.comboHoraFin.setItems(listaHoraFin);
			this.montoTotal.setText(null);
			this.senia.setText(null);
			this.cantHoras.setText(null);
		}
		else {
			verMontoTotalySenia();
		}
		
	}

	@FXML
	public void verificarFormaPagoYcampos() throws Exception{
		verificarFormaPago();
		limpiarCamposTarjeta();
	}


	private void limpiarCamposTarjeta() {
		this.listaTarjetas = FXCollections.observableList(cargarComboTarjetas());
		this.comboTarjetas.setItems(listaTarjetas);
		
		this.codSeguridadTarjeta.setText(null);
		this.fechaVencTarjeta.setText(null);
		this.numeroTarjeta.setText(null);
		this.textoDatosTarjeta.setText(null);
		this.textoFechaVencimientoTarjeta.setText(null);
	}

	@FXML
	public void verMontoTotalySenia() throws Exception {
		
		Integer horaInicioCombo = comboHoraInicio.getValue();
		
		
		Integer horaFinCombo = comboHoraFin.getValue();
		
		LocalDate localFinReserva = fechaFinReserva.getValue();
		LocalDate localInicioReserva = fechaInicioReserva.getValue();
	
		if(!(horaInicioCombo == null) && !(horaFinCombo == null)) {
			
			String salonCombo = comboSalones.getValue();
			int porcentaje = 0;
			String[] datos = salonCombo.split("-");
			int idSalonSeleccionado = Integer.valueOf(datos[0]);
			
			for(SalonDTO c : this.salon.obtenerSalones()) {
				if(c.getId() == idSalonSeleccionado) {
					porcentaje = c.getSenia();
				}
			}		
			
			long diferenciaHoras = Duration.between(localInicioReserva.atTime(horaInicioCombo, 0),localFinReserva.atTime(horaFinCombo, 0)).toHours();
			this.cantHoras.setText(String.valueOf(diferenciaHoras));
			BigDecimal porcentajeSenia = BigDecimal.valueOf(porcentaje);
			
			BigDecimal montoFinal = BigDecimal.valueOf(Double.valueOf(this.montoReserva.getText())).multiply(BigDecimal.valueOf(diferenciaHoras));
			BigDecimal montoSenia = montoFinal.multiply(porcentajeSenia).divide(BigDecimal.valueOf(100));
			this.montoTotal.setText(String.valueOf(montoFinal));	
			this.senia.setText(String.valueOf(montoSenia));		
			
		}		
		else {
			this.montoTotal.setText(null);
			this.senia.setText(null);
			this.cantHoras.setText(null);
		}
	}
	
	@FXML
	public void verificarEstado() throws Exception{
		if(this.btnModificar.isVisible()){
			if(this.comboEstados.getValue().equals(String.valueOf(dto.ReservaEventoConNombresDTO.EstadoReserva.EN_CURSO)) && this.FechaIngreso == null) {
				this.setearFechaIngreso();
				this.panelBorde.setDisable(false);
			}
			else if(this.comboEstados.getValue().equals(String.valueOf(dto.ReservaEventoConNombresDTO.EstadoReserva.FINALIZADO)) && this.FechaEgreso == null) {
				setearFechaEgreso();
				
				this.panelBorde.setDisable(false);
			}
			else if(this.comboEstados.getValue().equals(String.valueOf(dto.ReservaEventoConNombresDTO.EstadoReserva.CANCELADO))) {
				this.panelBorde.setDisable(true);
			}
			
			else if(this.comboEstados.getValue().equals(String.valueOf(dto.ReservaEventoConNombresDTO.EstadoReserva.PENDIENTE))) {
				this.FechaEgreso = null;
				this.FechaIngreso = null;
				this.panelBorde.setDisable(false);
			}	
		}
	}
	
	@FXML
	public void verificarCliente() throws Exception{
		String clienteSeleccionado = this.comboClientes.getSelectionModel().getSelectedItem().toString();
		String[] datos = clienteSeleccionado.split("-");
		int idClienteSeleccionado = Integer.valueOf(datos[0]);
		this.idCliente = idClienteSeleccionado;
		this.clienteActual = this.cliente.getClientePorId(idClienteSeleccionado);
		this.txtCabeza.setText(clienteActual.getApellido() + " " + clienteActual.getNombre());	
	}
	
	private void setearFechaIngreso() {
		this.FechaIngreso = new Timestamp(System.currentTimeMillis());
	}

	private void setearFechaEgreso() {
		this.FechaEgreso = new Timestamp(System.currentTimeMillis());
	}

	public void setearCamposPantalla(ReservaEventoConNombresDTO reservaEventoConNombresDTO) throws IOException {
		initData(reservaEventoConNombresDTO.getIdCliente());
		this.FechaIngreso = reservaEventoConNombresDTO.getFechaIngreso();
		this.FechaEgreso = reservaEventoConNombresDTO.getFechaEgreso();
		this.idReservaEvento = reservaEventoConNombresDTO.getIdReservaEvento();
		LocalDateTime reservaInicio = reservaEventoConNombresDTO.getFechaInicioReserva().toLocalDateTime();
		LocalDateTime reservaFin = reservaEventoConNombresDTO.getFechaFinReserva().toLocalDateTime();
		
		for(SalonDTO c : this.salon.obtenerSalones()) {
			if(c.getId() == reservaEventoConNombresDTO.getIdSalon()) {
				this.comboSalones.setValue(String.valueOf(c.getId() + "-" + c.getEstilo()));	
			}
		}
		
		for(CategoriaEventoDTO c : this.categoriaEvento.obtenerCategoriasEvento()) {
			if(c.getId() == reservaEventoConNombresDTO.getIdCategoriaEvento()) {
				this.comboCategoriasEvento.setValue(String.valueOf(c.getId() + "-" + c.getNombre()));
			}
		}
		
		this.comboHoraInicio.setValue(reservaInicio.getHour());
		
		
		
		this.comboEstados.setValue(String.valueOf(reservaEventoConNombresDTO.getEstado()));
		this.comboTarjetas.setValue(String.valueOf(reservaEventoConNombresDTO.getTipoTarjeta()));
		this.codSeguridadTarjeta.setText(reservaEventoConNombresDTO.getCodSeguridadTarjeta());
		this.fechaVencTarjeta.setText(reservaEventoConNombresDTO.getFechaVencTarjeta());
		this.numeroTarjeta.setText(reservaEventoConNombresDTO.getNumeroTarjeta());
		this.comboFormasPago.setValue(String.valueOf(reservaEventoConNombresDTO.getFormaPago()));
		try {
			verificarFormaPago();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		this.fechaGeneracionReserva = reservaEventoConNombresDTO.getFechaGeneracionReserva();
		this.fechaInicioReserva.setValue(LocalDate.of(reservaInicio.getYear(),reservaInicio.getMonthValue(),reservaInicio.getDayOfMonth()));
		this.fechaFinReserva.setValue(LocalDate.of(reservaFin.getYear(), reservaFin.getMonthValue(), reservaFin.getDayOfMonth()));
		this.observaciones.setText(reservaEventoConNombresDTO.getObservaciones());
		
		try {
			verificarFechas();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		this.comboHoraFin.setValue(reservaFin.getHour() - 1);
		
		
		LocalDate localFinReserva = fechaFinReserva.getValue();
		LocalDate localInicioReserva = fechaInicioReserva.getValue();
		long diferenciaHoras = Duration.between(localInicioReserva.atTime(this.comboHoraInicio.getValue(), 0),localFinReserva.atTime(this.comboHoraFin.getValue(), 0)).toHours();
		this.cantHoras.setText(String.valueOf(diferenciaHoras));
		this.montoTotal.setText(String.valueOf(reservaEventoConNombresDTO.getMontoTotal()));
		this.montoReserva.setText(String.valueOf(reservaEventoConNombresDTO.getMontoReservaEvento()));
		this.senia.setText(String.valueOf(reservaEventoConNombresDTO.getSenia()));
	}
	
	
	private int obtenerSalonSeleccionado(String salonCombo) {
		String[] datos = salonCombo.split("-");
		int idSalonSeleccionado = Integer.valueOf(datos[0]);
		
		return idSalonSeleccionado;
	}
	
	private int obtenerCategoriaSeleccionada(String categoriaCombo) {
		String[] datos = categoriaCombo.split("-");
		int idCategoriaEventoSeleccionada = Integer.valueOf(datos[0]);
		
		return idCategoriaEventoSeleccionada;
	}
	
	private List<Integer> cargarCombosHorasFin() {
		List<Integer> lista = new ArrayList<>();
		int horaMinima = Integer.valueOf(this.comboHoraInicio.getSelectionModel().getSelectedItem().toString()) + 1;
		for(int h = horaMinima; h <= 23; h++) {
			lista.add(h);
		}
		return lista;
	}

	private List<Integer> cargarCombosHoras() {
		List<Integer> lista = new ArrayList<>();
		for(int h = 0; h <= 23; h++) {
			lista.add(h);
		}
		return lista;
	}
	
	private List<String> cargarComboFormasPago() {
		List<String> lista = new ArrayList<>();
		FormaPago[] formasPago = FormaPago.values();
		for(FormaPago f : formasPago) {
			lista.add(f.name());
		}
		return lista;
	}

	private List<String> cargarComboTarjetas() {
		List<String> lista = new ArrayList<>();
		TipoTarjeta[] tiposTarjeta = TipoTarjeta.values();
		for(TipoTarjeta t : tiposTarjeta) {
			lista.add(t.name());
		}
		return lista;
	}
	
	public List<String> cargarComboSalones() {
		List<String> lista = new ArrayList<>();
		for(SalonDTO c : this.salon.obtenerSalones()) {
			lista.add(c.getId() + "-" + c.getEstilo());
		}
		return lista;
	}
	
	public List<String> cargarComboCategoriasEvento() {
		List<String> lista = new ArrayList<>();
		for(CategoriaEventoDTO c : this.categoriaEvento.obtenerCategoriasEvento()) {
			lista.add(c.getId() + "-" + c.getNombre());
		}
		return lista;
	}
	
	public List<String> cargarComboClientes() {
		List<String> lista = new ArrayList<>();
		for(ClienteDTO c : this.cliente.obtenerClientes()) {
			lista.add(c.getIdCliente() + "-" + c.getNombre());
		}
		return lista;
	}
	
	public List<String> cargarComboEstados() {
		
		List<String> lista = new ArrayList<>();
		EstadoReserva[] estados = EstadoReserva.values();
		for(EstadoReserva e : estados) {
			if(this.btnModificar.isVisible()) {
				lista.add(e.name());	
			}
			else if(e.equals(dto.ReservaEventoDTO.EstadoReserva.PENDIENTE)) {
				lista.add(e.name());
			}
		}
		return lista;
	}
	
	private void mostrarMensaje(String mensaje) {
	
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);

		alert.showAndWait();
	}
	
	@FXML
	 private void cerrarVentana() {
		Stage stage = (Stage) comboSalones.getScene().getWindow();
		stage.close();
	}

	public void setVisibilityBtnAgregarReservaEvento(boolean b) {
		this.btnAgregar.setVisible(b);
	}

	public void setDisableBtnAgregarReservaEvento(boolean b) {
		this.btnAgregar.setDisable(b);
	}

	public void setVisibilityBtnModificarReservaEvento(boolean b) {
		this.btnModificar.setVisible(b);
	}

	public void setDisableBtnModificarReservaEvento(boolean b) {
		this.btnModificar.setDisable(b);
	}




	public void setVisibilityComboClientes(boolean b) {
		this.comboClientes.setVisible(b);
	}
	
}
