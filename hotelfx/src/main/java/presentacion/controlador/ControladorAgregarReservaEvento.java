package presentacion.controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dto.CategoriaEventoDTO;
import dto.ClienteDTO;
import dto.ReservaEventoConNombresDTO;
import dto.ReservaEventoDTO;
import dto.ReservaEventoDTO.EstadoReserva;
import dto.SalonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.CategoriaEvento;
import modelo.Cliente;
import modelo.ReservaEvento;
import modelo.Salon;
import persistencia.dao.mysql.DAOSQLFactory;

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
	
	@FXML private Text txtCabeza;
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
			this.clienteActual = this.cliente.getClientePorId(idCliente);
			this.txtCabeza.setText(clienteActual.getApellido() + " " + clienteActual.getNombre());
		}
		
		
	}
	
	void initData(int id) {
		this.idCliente = id;
		initialize(null, null);
	}

	@FXML
    public void agregarReservaEventoCliente() throws Exception {
		this.fechaGeneracionReserva = new Timestamp(System.currentTimeMillis());
		
		
		String comboSalonSeleccionado = this.comboSalones.getSelectionModel().getSelectedItem().toString();
		int idSalon = obtenerSalonSeleccionado(comboSalonSeleccionado);
		
		String comboCategoriaEventoSeleccionado = this.comboCategoriasEvento.getSelectionModel().getSelectedItem().toString();
		int idCategoriaEvento = obtenerCategoriaSeleccionada(comboCategoriaEventoSeleccionado);
		
		
		BigDecimal Senia =  new BigDecimal(senia.getText());
		BigDecimal MontoReservaEvento = new BigDecimal(montoReserva.getText());
		BigDecimal MontoTotal = new BigDecimal(montoTotal.getText());
		
		//la reserva se hace por día, desde las 0800
		LocalDate localInicioReserva = fechaInicioReserva.getValue();
		
		Timestamp FechaInicioReserva = Timestamp.valueOf(localInicioReserva.atTime(LocalTime.of(8,0,0)));
		//la reserva termina a las 0000
		LocalDate localFinReserva = fechaFinReserva.getValue();
		Timestamp FechaFinReserva = Timestamp.valueOf(localFinReserva.atTime(LocalTime.of(0,0,0)));
		Timestamp FechaIngreso = null;
		Timestamp FechaEgreso = null;
		String FormaPago = formaPago.getText();
		String TipoTarjeta = tipoTarjeta.getText();
		String NumeroTarjeta = numeroTarjeta.getText();
		String FechaVencTarjeta = fechaVencTarjeta.getText();
		String CodSeguridadTarjeta = codSeguridadTarjeta.getText();
		EstadoReserva estado = EstadoReserva.valueOf(comboEstados.getValue());
		String Observaciones = observaciones.getText();
		
		ReservaEventoDTO nuevaReserva = new ReservaEventoDTO(0, this.idCliente, this.idUsuario, idSalon, idCategoriaEvento, Senia, MontoReservaEvento, MontoTotal, this.fechaGeneracionReserva, FechaInicioReserva, FechaFinReserva, FechaIngreso, FechaEgreso, FormaPago, TipoTarjeta, NumeroTarjeta, FechaVencTarjeta, CodSeguridadTarjeta, estado, Observaciones);
		this.reserva.agregarReservaEvento(nuevaReserva);
		cerrarVentana();	
    }
	
	@FXML
	public void modificarReservaEventoCliente() throws IOException {
	
//		String nombre = txtNombre.getText();
//		String apellido = txtApellido.getText();
//		String tipoDoc = this.comboTipoDoc.getValue();
//		String documento =txtNumDocumento.getText();
//		String email = txtEmail.getText();
//		String tel = txtTelefono.getText();
//		java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(txtFecha.getValue());
//		
//		ClienteDTO nuevoCliente = new ClienteDTO(id, nombre, apellido, tipoDoc, documento, email, tel, true,gettedDatePickerDate);
//		this.hotel.modificarCliente(nuevoCliente);
//		cerrarVentana();	
	}
	
	
	
	
	public void setearCamposPantalla(ReservaEventoConNombresDTO reservaEventoConNombresDTO) throws IOException {
		this.comboSalones.setValue(String.valueOf(reservaEventoConNombresDTO.getIdSalon()));
		this.comboCategoriasEvento.setValue(String.valueOf(reservaEventoConNombresDTO.getIdCategoriaEvento()));
		this.comboEstados.setValue(String.valueOf(reservaEventoConNombresDTO.getEstado()));
		this.tipoTarjeta.setText(reservaEventoConNombresDTO.getTipoTarjeta());
		this.codSeguridadTarjeta.setText(reservaEventoConNombresDTO.getCodSeguridadTarjeta());
		this.fechaVencTarjeta.setText(reservaEventoConNombresDTO.getFechaVencTarjeta());
		this.numeroTarjeta.setText(reservaEventoConNombresDTO.getNumeroTarjeta());
		this.formaPago.setText(reservaEventoConNombresDTO.getFormaPago());
		this.montoTotal.setText(String.valueOf(reservaEventoConNombresDTO.getMontoTotal()));
		this.montoReserva.setText(String.valueOf(reservaEventoConNombresDTO.getMontoReservaEvento()));
		this.senia.setText(String.valueOf(reservaEventoConNombresDTO.getSenia()));
		this.fechaGeneracionReserva.setTime(reservaEventoConNombresDTO.getFechaGeneracionReserva().getTime());
		this.fechaInicioReserva.setAccessibleText(String.valueOf(reservaEventoConNombresDTO.getFechaInicioReserva()));
		this.fechaFinReserva.setAccessibleText(String.valueOf(reservaEventoConNombresDTO.getFechaFinReserva()));
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
	
	@FXML
	 private void cerrarVentana() {
		Stage stage = (Stage) comboSalones.getScene().getWindow();
		stage.close();
	}

	public void setVisibilityBtnAgregarReservaEvento(boolean b) {
		// TODO Auto-generated method stub
		this.btnAgregar.setVisible(b);
	}

	public void setDisableBtnAgregarReservaEvento(boolean b) {
		// TODO Auto-generated method stub
		this.btnAgregar.setDisable(b);
	}

	public void setVisibilityBtnModificarReservaEvento(boolean b) {
		// TODO Auto-generated method stub
		this.btnModificar.setVisible(b);
	}

	public void setDisableBtnModificarReservaEvento(boolean b) {
		// TODO Auto-generated method stub
		this.btnModificar.setDisable(b);
	}
	
	public void setVisibilityFechaIngresoReservaEvento(boolean b) {
		// TODO Auto-generated method stub
		this.fechaIngresoReserva.setVisible(b);
	}

	public void setDisableFechaIngresoReservaEvento(boolean b) {
		// TODO Auto-generated method stub
		this.fechaIngresoReserva.setDisable(b);
	}
	
	public void setVisibilityFechaEgresoReservaEvento(boolean b) {
		// TODO Auto-generated method stub
		this.fechaEgresoReserva.setVisible(b);
	}

	public void setDisableFechaEgresoReservaEvento(boolean b) {
		// TODO Auto-generated method stub
		this.fechaEgresoReserva.setDisable(b);
	}
}
