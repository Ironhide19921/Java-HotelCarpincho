package presentacion.controlador;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.ReservaCuarto;
import modelo.Usuario;
import modelo.Validador;
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
	@FXML private Button btnConsultarPendientes;
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
	private Validador validador;
	private int idReserva;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//visualizarBotones();
		this.btnConsultarPendientes.setVisible(false);
		this.reservaCuarto = new ReservaCuarto(new DAOSQLFactory());
		this.usuarios = new Usuario(new DAOSQLFactory());
		this.listaCmbBoxEstados = FXCollections.observableList(cargarCmbBoxEstados());
		this.cmbBoxEstados.setItems(listaCmbBoxEstados);
		this.listaCmbBoxFormaPago = FXCollections.observableList(cargarCmbBoxFormaPago());
		this.cmbBoxFormaPago.setItems(listaCmbBoxFormaPago);
		this.listaCmbBoxUsuario = FXCollections.observableList(cargarCmbBoxUsuario());
		this.cmbBoxUsuario.setItems(listaCmbBoxUsuario);
	}

	private void visualizarBotones() {
		this.btnAgregar.setVisible(true);
		this.btnModificar.setVisible(true);
		this.btnCerrar.setVisible(true);
		this.btnAgregarCliente.setVisible(true);
		this.btnAgregarCuarto.setVisible(true);
		
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

	private UsuarioDTO obtenerUsuarioConID(Integer id) {
		List<UsuarioDTO> lista = this.usuarios.obtenerUsuarios();	
		for(UsuarioDTO u : lista) {
			if(u.getIdUsuario() == id) {
				return u;
			}
		}
		
		return null;
	}
	
	@FXML 
	public void agregarReservaCuarto() 
	{

		ReservaCuartoDTO reserva = obtenerDatosReserva();
		if(validador.formatoMail(reserva.getEmailFacturacion())) 
		{
			this.reservaCuarto.agregarReservaCuarto(reserva);	
			validador.mostrarMensaje("Su reserva ha sido agregada con exito");
		}
		
	}



	private ReservaCuartoDTO obtenerDatosReserva() {
		
		LocalDate localInicioReserva =  this.fechaReserva.getValue();
		LocalDate localInicioCheckIn =  this.fechaCheckIn.getValue();
		LocalDate localInicioCheckOut =  this.fechaCheckOut.getValue();
		LocalDate localInicioIngreso =  this.fechaIngreso.getValue();
		LocalDate localInicioEgreso =  this.fechaEgreso.getValue();
		int idCliente = 0;
		if(!cliente.getText().isEmpty()) {
			 idCliente = Integer.parseInt(cliente.getText());
		}
	
		int idCuarto = 0;
		if(!cuarto.getText().isEmpty()) {
			idCuarto = Integer.parseInt(cuarto.getText());
		}
		int idUsuario = 0;
		if(cmbBoxUsuario.getValue()!=null) {
			idUsuario = this.cmbBoxUsuario.getValue().getIdPerfil();
		}
		BigDecimal senia = new BigDecimal(0);
		if(!this.senia.getText().isEmpty()) {
			senia = new BigDecimal(this.senia.getText());
		}
		BigDecimal montoReservaCuarto =new BigDecimal(0);
		if(!montoSenia.getText().isEmpty()) {
			senia = new BigDecimal(this.senia.getText());
		}
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
		String estadoReserva = "";
		if(this.cmbBoxEstados.getValue()!=null) {
			estadoReserva = this.cmbBoxEstados.getValue();
		}
		if(this.cmbBoxEstados.getValue()==null) {
			this.cmbBoxEstados.getSelectionModel().selectFirst();
			estadoReserva = this.cmbBoxEstados.getValue();
		}
	
		String comentarios = this.observaciones.getText();
		boolean estado = true;

		ReservaCuartoDTO reserva = new ReservaCuartoDTO(idCliente, idCuarto, idUsuario, senia, montoReservaCuarto,
				emailFacturacion, numTarjeta, formaDePago, tipoTarjeta, codSeguridadTarjeta, fechaVencTarjeta,
				fechaReserva, fechaCheckIn, fechaOut, fechaIngreso, fechaEgreso, estadoReserva, comentarios,estado);
		return reserva;
	}
	
	
	public void setearCampos(ReservaCuartoDTO reserva) {
		
		this.cmbBoxUsuario.getSelectionModel().select(obtenerUsuarioConID(reserva.getIdUsuario()));
		this.cuarto.setText(reserva.getIdCuarto().toString());
		this.cliente.setText(reserva.getIdCliente().toString());
		this.email.setText(reserva.getEmailFacturacion());
		this.senia.setText(reserva.getSenia().toString());
		this.montoSenia.setText(reserva.getMontoReservaCuarto().toString());
		this.tipoTarjeta.setText(reserva.getTiposTarjeta());
		this.numTarjeta.setText(reserva.getNumTarjeta());
		this.fechaVecTarjeta.setText(reserva.getFechaVencTarjeta());
		this.codSeguridad.setText(reserva.getCodSeguridadTarjeta());
		this.observaciones.setText(reserva.getComentarios());
		this.cmbBoxEstados.getSelectionModel().select(reserva.getEstadoReserva());
		this.cmbBoxFormaPago.getSelectionModel().select(reserva.getFormasDePago());
		this.fechaReserva.setValue(reserva.getFechaReserva().toLocalDateTime().toLocalDate());
		this.fechaIngreso.setValue(reserva.getFechaIngreso().toLocalDateTime().toLocalDate());
		this.fechaEgreso.setValue(reserva.getFechaEgreso().toLocalDateTime().toLocalDate());
		this.fechaCheckIn.setValue(reserva.getFechaCheckIn().toLocalDateTime().toLocalDate());
		this.fechaCheckOut.setValue(reserva.getFechaOut().toLocalDateTime().toLocalDate());
	}

	@FXML 
	public void modificarReservaCuarto() 
	{
		//Agregar validador
		ReservaCuartoDTO reserva = obtenerDatosReserva();
		this.reservaCuarto.modificarReservaCuarto(reserva);
	}
	
	
	
	@FXML 
	public void consultarCuarto() {
		 try { 
				if(ingresoFechas()) {
			    Stage primaryStage = new Stage(); 
		 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaABMCuarto.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxml);
				Parent root = (Parent) fxmlLoader.load();
				primaryStage.setScene(new Scene(root));   
				primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
				ControladorABMCuarto scene2Controller = fxmlLoader.getController();
				scene2Controller.datosReserva(obtenerDatosReserva());
				LocalDate localInicioIngreso =  this.fechaIngreso.getValue();
				LocalDate localInicioEgreso =  this.fechaEgreso.getValue();
				Timestamp fechaIngreso = Timestamp.valueOf(localInicioIngreso.atTime(LocalTime.of(8,0,0)));
				Timestamp fechaEgreso = Timestamp.valueOf(localInicioEgreso.atTime(LocalTime.of(8,0,0)));
				scene2Controller.consultaReservaCuarto(fechaEgreso,fechaIngreso);
				
				primaryStage.setTitle("Consulta de cuartos disponibles");
				primaryStage.sizeToScene();
				primaryStage.show();
				
				 Stage stage = (Stage) btnAgregarCuarto.getScene().getWindow();
					stage.close();
				}
				else {
					validador.mostrarMensaje("El ingreso de fechas son obligatorias para esta consulta");
				}
		       			
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	}
	
	@FXML 
	public void consultarCliente() {
		 try { 
					if(ingresoFechas()) {
						  Stage primaryStage = new Stage(); 
					 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaABMCliente.fxml");
							FXMLLoader fxmlLoader = new FXMLLoader(fxml);
							Parent root = (Parent) fxmlLoader.load();
							primaryStage.setScene(new Scene(root));   
							primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
							ControladorABMCliente controlador = fxmlLoader.getController();
							controlador.modificarBotonesReserva();
							controlador.datosReserva(obtenerDatosReserva());

							primaryStage.setTitle("Consulta de clientes activos");
							primaryStage.sizeToScene();
							primaryStage.show(); 
							
							 Stage stage = (Stage) btnAgregarCliente.getScene().getWindow();
								stage.close();
					}
					else {
						validador.mostrarMensaje("El ingreso de fechas son obligatorias para esta consulta");
					}
			  
		       
		     } catch(Exception e) { 
		      e.printStackTrace(); 
		     } 
	}

	private boolean ingresoFechas() {
		LocalDate localInicioReserva =  this.fechaReserva.getValue();
		LocalDate localInicioCheckIn =  this.fechaCheckIn.getValue();
		LocalDate localInicioCheckOut =  this.fechaCheckOut.getValue();
		LocalDate localInicioIngreso =  this.fechaIngreso.getValue();
		LocalDate localInicioEgreso =  this.fechaEgreso.getValue();
		 if(localInicioReserva!=null && localInicioCheckIn!=null && 
				 localInicioCheckOut!=null&& localInicioIngreso!=null&& 
				 localInicioEgreso!=null) {
		 return true;
		 }
		 else {
		 return false;
		 }
	}
	


	public void modificarCliente(int idCliente) {
		this.cliente.setText(idCliente+"");
		
	}
	

	public void modificarCuarto(int idCuarto) {
	
		this.cuarto.setText(idCuarto+"");
	}
		
	@FXML
	public void modificarPantallaConsulta(int id) {
		
		ocultarBotonesConsulta();
		camposSoloLectura();
		this.btnConsultarPendientes.setVisible(true);
	
	}
	@FXML
	public void pasarIdReserva(int id) {
		this.idReserva = id;
	}

	private void camposSoloLectura() {
		this.cuarto.setDisable(true);
		this.cliente.setDisable(true);
		this.email.setDisable(true);
		this.senia.setDisable(true);
		this.montoSenia.setDisable(true);
		this.tipoTarjeta.setDisable(true);
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
	public void consultarPendientes() throws IOException {
		 Stage primaryStage = new Stage(); 
	 		URL fxml = getClass().getClassLoader().getResource("presentacion/vista/VentanaABMOrdenPedido.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxml);
			Parent root = (Parent) fxmlLoader.load();
			primaryStage.setScene(new Scene(root));   
			primaryStage.getScene().getStylesheets().add("/CSS/mycss.css");
			ControladorABMOrdenPedido controlador = fxmlLoader.getController();
			controlador.enviarIdReserva(Integer.parseInt(this.cliente.getText()));
			controlador.modificarBotones();
			//controlador.datosReserva(obtenerDatosReserva());
			primaryStage.setTitle("Consulta de orden de pedido del cliente" );
			primaryStage.sizeToScene();
			primaryStage.show(); 
			 Stage stage = (Stage) btnConsultarPendientes.getScene().getWindow();
			stage.close();
	}
	

	
}
