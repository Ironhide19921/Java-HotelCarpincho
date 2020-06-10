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
	@FXML private DatePicker fechaIngresoReserva;
	@FXML private DatePicker fechaEgresoReserva;
	@FXML private TextArea observaciones;
	private ReservaEvento reserva;
	private Cliente cliente;
	private Salon salon;
	private CategoriaEvento categoriaEvento;
	private ClienteDTO clienteActual;
	private int idCliente;
	private int idUsuario = 1;
	private Alert alert;
	private Integer idReservaEvento;
	
	
	
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
			this.listaEstados = FXCollections.observableList(cargarComboEstados());
			this.comboEstados.setItems(listaEstados);
			this.listaFormasPago = FXCollections.observableList(cargarComboFormasPago());
			this.comboFormasPago.setItems(listaFormasPago);
			this.listaTarjetas = FXCollections.observableList(cargarComboTarjetas());
			this.comboTarjetas.setItems(listaTarjetas);
			this.listaHoraInicio = FXCollections.observableList(cargarCombosHoras());
			this.comboHoraInicio.setItems(listaHoraInicio);
			
			
			this.clienteActual = this.cliente.getClientePorId(idCliente);
			this.txtCabeza.setText(clienteActual.getApellido() + " " + clienteActual.getNombre());
			
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
		Timestamp FechaIngreso = null;
		Timestamp FechaEgreso = null;
//		
		
		if(comboHoraFin.getSelectionModel().getSelectedItem() != 23) {
			FechaFinReserva = Timestamp.valueOf(localFinReserva.atTime(LocalTime.of(comboHoraFin.getSelectionModel().getSelectedItem() + 1,0,0)));	
		}
		else {
			localFinReserva = localFinReserva.plusDays(1);
			FechaFinReserva = Timestamp.valueOf(localFinReserva.atTime(LocalTime.of(0,0,0)));
		}
		
		System.out.println("Reserva inicia el " + FechaInicioReserva + " y termina el " + FechaFinReserva);
		
		List<ReservaEventoDTO> reservas = this.reserva.verReservasEntreFechas(FechaInicioReserva, FechaFinReserva);
		
		System.out.println(reservas.size() + " Cantidad de reservas en el array de reservas entre las fechas que indican");
		
		EstadoReserva estado = EstadoReserva.valueOf(comboEstados.getValue());
		String Observaciones = observaciones.getText();
		
//		ReservaEventoDTO nuevaReserva = new ReservaEventoDTO(0, this.idCliente, this.idUsuario, idSalon, idCategoriaEvento, Senia, MontoReservaEvento, MontoTotal, this.fechaGeneracionReserva, FechaInicioReserva, FechaFinReserva, FechaIngreso, FechaEgreso, formaPago, tipoTarjeta, NumeroTarjeta, FechaVencTarjeta, CodSeguridadTarjeta, estado, Observaciones);
//		this.reserva.agregarReservaEvento(nuevaReserva);
//		cerrarVentana();	
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
		Timestamp FechaIngreso = null;
		Timestamp FechaEgreso = null;
		
		EstadoReserva estado = EstadoReserva.valueOf(comboEstados.getValue());
		String Observaciones = observaciones.getText();
		
		ReservaEventoDTO nuevaReserva = new ReservaEventoDTO(this.idReservaEvento, this.idCliente, this.idUsuario, idSalon, idCategoriaEvento, Senia, MontoReservaEvento, MontoTotal, this.fechaGeneracionReserva, FechaInicioReserva, FechaFinReserva, FechaIngreso, FechaEgreso, formaPago, tipoTarjeta, NumeroTarjeta, FechaVencTarjeta, CodSeguridadTarjeta, estado, Observaciones);
		this.reserva.modificarReservaEvento(nuevaReserva);
		cerrarVentana();	
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
		}
		else {
			System.out.println("Deberia ver monto total y senia");
			verMontoTotalySenia();
		}
		
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
			System.out.println(diferenciaHoras + "<-Diferencia de horas");
			
			BigDecimal porcentajeSenia = BigDecimal.valueOf(porcentaje);
			
			BigDecimal montoFinal = BigDecimal.valueOf(Double.valueOf(this.montoReserva.getText())).multiply(BigDecimal.valueOf(diferenciaHoras));
			BigDecimal montoSenia = montoFinal.multiply(porcentajeSenia).divide(BigDecimal.valueOf(100));
			this.montoTotal.setText(String.valueOf(montoFinal));	
			this.senia.setText(String.valueOf(montoSenia));		
			
		}		
		else {
			this.montoTotal.setText(null);
			this.senia.setText(null);
		}
	}
	
	public void setearCamposPantalla(ReservaEventoConNombresDTO reservaEventoConNombresDTO) throws IOException {
		initData(reservaEventoConNombresDTO.getIdCliente());
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
		this.comboHoraFin.setValue(reservaFin.getHour() - 1);
		this.comboEstados.setValue(String.valueOf(reservaEventoConNombresDTO.getEstado()));
		this.comboTarjetas.setValue(String.valueOf(reservaEventoConNombresDTO.getTipoTarjeta()));
		this.codSeguridadTarjeta.setText(reservaEventoConNombresDTO.getCodSeguridadTarjeta());
		this.fechaVencTarjeta.setText(reservaEventoConNombresDTO.getFechaVencTarjeta());
		this.numeroTarjeta.setText(reservaEventoConNombresDTO.getNumeroTarjeta());
		this.comboFormasPago.setValue(String.valueOf(reservaEventoConNombresDTO.getFormaPago()));
		this.montoTotal.setText(String.valueOf(reservaEventoConNombresDTO.getMontoTotal()));
		this.montoReserva.setText(String.valueOf(reservaEventoConNombresDTO.getMontoReservaEvento()));
		this.senia.setText(String.valueOf(reservaEventoConNombresDTO.getSenia()));
		this.fechaGeneracionReserva = reservaEventoConNombresDTO.getFechaGeneracionReserva();
		this.fechaInicioReserva.setValue(LocalDate.of(reservaInicio.getYear(),reservaInicio.getMonthValue(),reservaInicio.getDayOfMonth()));
		this.fechaFinReserva.setValue(LocalDate.of(reservaFin.getYear(), reservaFin.getMonthValue(), reservaFin.getDayOfMonth()));
		if(!this.fechaIngresoReserva.equals(null)){
			this.fechaIngresoReserva.setAccessibleText(String.valueOf(reservaEventoConNombresDTO.getFechaIngreso()));	
		}
		if(!this.fechaEgresoReserva.equals(null)) {
			this.fechaEgresoReserva.setAccessibleText(String.valueOf(reservaEventoConNombresDTO.getFechaEgreso()));
		}
		this.observaciones.setText(reservaEventoConNombresDTO.getObservaciones());
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
	
	public List<String> cargarComboEstados() {
		List<String> lista = new ArrayList<>();
		EstadoReserva[] estados = EstadoReserva.values();
		for(EstadoReserva e : estados) {
			lista.add(e.name());
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
	
	public void setVisibilityFechaIngresoReservaEvento(boolean b) {
		this.fechaIngresoReserva.setVisible(b);
	}

	public void setDisableFechaIngresoReservaEvento(boolean b) {
		this.fechaIngresoReserva.setDisable(b);
	}
	
	public void setVisibilityFechaEgresoReservaEvento(boolean b) {
		this.fechaEgresoReserva.setVisible(b);
	}

	public void setDisableFechaEgresoReservaEvento(boolean b) {
		this.fechaEgresoReserva.setDisable(b);
	}
}
