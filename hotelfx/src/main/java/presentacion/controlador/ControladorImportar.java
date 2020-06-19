package presentacion.controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;import javax.annotation.processing.FilerException;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import dto.ClienteDTO;
import dto.ErrorImportarDTO;
import dto.ReservaCuartoDTO;
import dto.RespuestaEncuestaDTO;
import dto.TicketDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import modelo.Cliente;
import modelo.Cuarto;
import modelo.ErrorImportar;
import modelo.ReservaCuarto;
import modelo.SendHttp;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;
import persistencia.dao.mysql.ErrorImportarDAOSQL;

import java.sql.Date;

public class ControladorImportar implements Initializable {


	//	@FXML private Controller controller;

	//	@FXML private ControladorMenuPrincipal menuPrincipal;
	@FXML Button btnImportar;
	@FXML private Button btnInsertar;
	@FXML private Button btnMail;
	@FXML private TableView<ClienteDTO> tablaClientesImportar;
	@FXML private TableColumn idCliente;
	@FXML private TableColumn Nombre;
	@FXML private TableColumn Apellido;
	@FXML private TableColumn TipoDocumento;
	@FXML private TableColumn Documento;
	@FXML private TableColumn Email;
	@FXML private TableColumn Telefono;
	//	@FXML private TableColumn Estado;
	@FXML private TableColumn FechaNacimiento;

	@FXML private TableView<ReservaCuartoDTO> tablaReservasImportar;
	@FXML private TableColumn idCuarto,Senia,MontoReservaCuarto,EmailFacturacion,
	FechaReserva,FechaCheckIn,FechaIngreso,FechaOut,FechaEgreso,
//	Forma,Tipo,
	NumeroTarjeta,FechaVencTarjeta,CodSeguridadTarjeta,Comentarios;


	private Cliente cliente;
	private ReservaCuarto reserva;
	private Cuarto cuarto;
	private int cantidadNuevosClientes;
	private int cantidadNuevosReservas;
	private ArrayList<String> columnas,columnas2;
	
	@FXML private Mail mail;
	@FXML private ErrorImportar errorImportar;
	@FXML private ObservableList<ClienteDTO> activeSession;
	@FXML private ObservableList<ClienteDTO> clientesAcargar;
	@FXML private ObservableList<ReservaCuartoDTO> reservasAcargar;



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.cliente = new Cliente(new DAOSQLFactory());
		this.reserva = new ReservaCuarto(new DAOSQLFactory());
		this.cuarto = new Cuarto(new DAOSQLFactory()); 
		activeSession = FXCollections.observableArrayList();
		tablaClientesImportar.getItems().clear();
		tablaReservasImportar.getItems().clear();
		clientesAcargar = FXCollections.observableArrayList();
		reservasAcargar = FXCollections.observableArrayList();
		cantidadNuevosClientes = 0;
		cantidadNuevosReservas = 0;
		mail = new Mail();
		errorImportar = new ErrorImportar(new DAOSQLFactory());
		//		manejoMail();
		cargarColumnas();
		columnas = new ArrayList<String>();
		//		columnas.add("idCliente");
		columnas.add("Nombre");
		columnas.add("Apellido");
		columnas.add("TipoDocumento");
		columnas.add("Documento");
		columnas.add("Email");
		columnas.add("Telefono");
		//		columnas.add("Estado");
		columnas.add("FechaNacimiento");

		columnas2 = new ArrayList<String>();
		columnas2.add("idCuarto");
		columnas2.add("Senia");
		columnas2.add("MontoReservaCuarto");
		columnas2.add("EmailFacturacion");
		columnas2.add("FechaReserva");
		columnas2.add("FechaCheckIn");
		columnas2.add("FechaIngreso");
		columnas2.add("FechaOut");
		columnas2.add("FechaEgreso");

		columnas2.add("FormaPago");
		columnas2.add("TipoTarjeta");
		columnas2.add("NumeroTarjeta");
		columnas2.add("FechaVencTarjeta");
		columnas2.add("CodSeguridadTarjeta");
		columnas2.add("Comentarios");

		try {
//			String collector = SendHttp.crearCollector();
//			String mensaje =SendHttp.crearMensaje(collector);
//			String recipientes = SendHttp.crearRecipientes(collector, mensaje);
//			String envio = SendHttp.enviarEncuestasMail(collector, mensaje);
			
			System.out.println(SendHttp.traerRespuestas("5541016993"));
//			ArrayList<RespuestaEncuestaDTO> resultado = SendHttp.consultarRespuestaEncuesta("5541016993");
//			for(RespuestaEncuestaDTO r: resultado) {
//				System.out.println(r.getIdPregunta());
//				for(String s: r.getListaRespuestas()) {
//					 System.out.println(s);
//				}
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void manejoMail() {
//		//		this.mail.setearPropiedades();
//				
//		try {
//			this.mail.enviarEncuesta(SendHttp.getLinkEncuesta());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}


	private void cargarColumnas() {
		idCliente.setCellValueFactory(new PropertyValueFactory("idCliente"));
		Nombre.setCellValueFactory(new PropertyValueFactory("nombre"));		
		Apellido.setCellValueFactory(new PropertyValueFactory("apellido"));
		TipoDocumento.setCellValueFactory(new PropertyValueFactory("tipoDocumento"));
		Documento.setCellValueFactory(new PropertyValueFactory("numeroDocumento"));
		Email.setCellValueFactory(new PropertyValueFactory("email"));
		Telefono.setCellValueFactory(new PropertyValueFactory("telefono"));
		//		Estado.setCellValueFactory(new PropertyValueFactory("estado"));
		FechaNacimiento.setCellValueFactory(new PropertyValueFactory("fechaNacimiento"));


		idCuarto.setCellValueFactory(new PropertyValueFactory("idCuarto"));
		Senia.setCellValueFactory(new PropertyValueFactory("senia"));
		MontoReservaCuarto.setCellValueFactory(new PropertyValueFactory("MontoReservaCuarto"));
		EmailFacturacion.setCellValueFactory(new PropertyValueFactory("MontoReservaCuarto"));
		FechaReserva.setCellValueFactory(new PropertyValueFactory("FechaReserva"));
		FechaCheckIn.setCellValueFactory(new PropertyValueFactory("FechaCheckIn"));
		FechaIngreso.setCellValueFactory(new PropertyValueFactory("FechaIngreso"));
		FechaOut.setCellValueFactory(new PropertyValueFactory("FechaOut"));
		FechaEgreso.setCellValueFactory(new PropertyValueFactory("FechaEgreso"));

//		Forma.setCellValueFactory(new PropertyValueFactory<ReservaCuartoDTO,String>("forma"));
//		System.out.println("valor columna "+Forma.getCellData(0));

//		Tipo.setCellValueFactory(new PropertyValueFactory("tipoTarjeta"));
		NumeroTarjeta.setCellValueFactory(new PropertyValueFactory("numTarjeta"));
		FechaVencTarjeta.setCellValueFactory(new PropertyValueFactory("fechaVencTarjeta"));
		CodSeguridadTarjeta.setCellValueFactory(new PropertyValueFactory("codSeguridadTarjeta"));
		Comentarios.setCellValueFactory(new PropertyValueFactory("comentarios"));

	}

	//	private void cargarColumnas() {
	//		idCliente.setCellValueFactory(new PropertyValueFactory("Nombre"));		
	//		id.setCellValueFactory(new PropertyValueFactory("idCategoriaCuarto"));	
	//		detalle .setCellValueFactory(new PropertyValueFactory("Detalle"));
	//	}

	private ObservableList<ClienteDTO> getAllClientes() {
		List<ClienteDTO> clientes = this.cliente.obtenerClientes();
		activeSession.clear();
		for(ClienteDTO c : clientes) {
			activeSession.add(c);
		}
		return activeSession;
	}


	private void crearTabla(ObservableList<ClienteDTO> lista) {		
		tablaClientesImportar.setItems(lista);
		tablaClientesImportar.setEditable(true);
	}

	private void crearTablaReservas(ObservableList<ReservaCuartoDTO> lista) {
		tablaReservasImportar.setItems(lista);
		tablaReservasImportar.setEditable(true);

		//		System.out.println("lista numtarjeta "+lista.get(0).getNumTarjeta());
		//		System.out.println("lista tipotarjeta "+lista.get(0).getTiposTarjeta());
		//		//tablaReservasImportar.getItems().get(0).setFormasDePago("valor de prueba");
		//		System.out.println("lista tipotarjeta "+lista.get(0).getFormasDePago());
		//		System.out.println(tablaReservasImportar.getItems().get(0).getFormasDePago());
		//		//		for(ReservaCuartoDTO r: lista) {
		//		//			Forma.setCellValueFactory(c-> new SimpleStringProperty(r.getFormasDePago()));
		//		//		}
		//		//		Forma.setCellValueFactory(c-> new SimpleStringProperty(lista.get(0).getFormasDePago()));
		//		System.out.println("valor columna forma de pago luego de cargado "+Forma.getCellData(1));
		//
		//		System.out.println("valor columna numerotarjeta luego de cargado "+NumeroTarjeta.getCellData(1));
		//		System.out.println("valor columna fechavenc luego de cargado "+FechaVencTarjeta.getCellData(1));
		//		System.out.println("valor columna tipo tarjeta luego de cargado "+Tipo.getCellData(1));
	}

	@FXML
	private void insertarClientesyReservasNuevos() {
		for(ClienteDTO c: clientesAcargar) {
			//Si es cliente nuevo, lo agrego a SQL
			if(c.getIdCliente()==0) {
				try {
					guardarCliente(c);
					c = actualizarIdCliente(c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		actualizarIdReservas();

		for(ReservaCuartoDTO r: reservasAcargar) {
			try {				
				guardarReserva(r);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		JOptionPane.showMessageDialog(null, "Se insertaron: "+cantidadNuevosClientes+" nuevos clientes.Se insertaron: "+cantidadNuevosReservas+" nuevas reservas.", "Importación Exitosa", JOptionPane.INFORMATION_MESSAGE);
	}

	private ClienteDTO actualizarIdCliente(ClienteDTO clienteCargado) {
		clienteCargado.setIdCliente(consultarRepetidos(clienteCargado).getIdCliente());
		return clienteCargado;
	}
	
	private void actualizarIdReservas() {
		for (int i=0; i < reservasAcargar.size(); i++) {
			reservasAcargar.get(i).setIdCliente(clientesAcargar.get(i).getIdCliente());
		}
		
	}

	public void guardarCliente(ClienteDTO nuevoCliente) throws IOException {
		this.cliente.agregarCliente(nuevoCliente);
		cantidadNuevosClientes++;
		//			cerrarVentanaAgregar();	
	}

	public void guardarReserva(ReservaCuartoDTO nuevaReserva) throws IOException {
		this.reserva.agregarReservaCuarto(nuevaReserva);
		cantidadNuevosReservas++;
		//			cerrarVentanaAgregar();	
	}



	@FXML
	public void importarCSV() {

		//Consulto todos los clientes
		this.activeSession=getAllClientes();
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("Archivos CSV", "*.csv"));

		boolean tieneErrores = false;
		File selectedFile = fc.showOpenDialog(null);
		if(selectedFile != null) {
			try {
				Scanner scanner = new Scanner(selectedFile);
				String filaColumnas = scanner.nextLine();
				String[] nombresColumnas = filaColumnas.split(",");
				
				//Validación errores
				for(int f=0; f<nombresColumnas.length; f++) {
					if(f<7) {
						if(!nombresColumnas[f].equals(columnas.get(f))) {				
							tieneErrores = true;
							Timestamp timestamp = new Timestamp(System.currentTimeMillis());
							ErrorImportarDTO error = new ErrorImportarDTO(0, timestamp, 1, "Falta columna: "+columnas.get(f));
							errorImportar.agregarError(error);
						}
					} else {
						if(!nombresColumnas[f].equals(columnas2.get(f-7))) {				
							tieneErrores = true;
							Timestamp timestamp = new Timestamp(System.currentTimeMillis());
							ErrorImportarDTO error = new ErrorImportarDTO(0, timestamp, 1, "Falta columna: "+columnas2.get(f-7));
							errorImportar.agregarError(error);
						}
					}
				}

				int fila = 0;
				//Salteo 1er fila con nombres de columnas
				//				scanner.nextLine(); 
				while(scanner.hasNext()) {
					String data = scanner.nextLine();
					String[] valores = data.split(",");
					for(int i=0; i<valores.length; i++){
						if(valores[i].equals("")) {
							tieneErrores = true;
							Timestamp timestamp = new Timestamp(System.currentTimeMillis());						
							if(i<7) {						
								ErrorImportarDTO error = new ErrorImportarDTO(0, timestamp, 1, "Columna: "+ columnas.get(i) +", fila número: " +fila+" , se encuentra vacía.");
								errorImportar.agregarError(error);
							} else { 
								ErrorImportarDTO error = new ErrorImportarDTO(0, timestamp, 1, "Columna: "+ columnas2.get(i-7) +", fila número: " +fila+" , se encuentra vacía.");
								errorImportar.agregarError(error);
							}
						}						
					}

					java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(valores[6].replace("\"", ""));

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					java.util.Date fechaReserva;
					java.sql.Timestamp fechaReservaTime = null;
					try {
						fechaReserva = dateFormat.parse(valores[11].replace("\"", ""));
						System.out.println(new java.sql.Timestamp(fechaReserva.getTime()));
						fechaReservaTime = new java.sql.Timestamp(fechaReserva.getTime());
						System.out.println(fechaReservaTime);

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					java.util.Date fechaCheckIn;
					java.sql.Timestamp fechaCheckInTime = null;;
					try {
						fechaCheckIn = dateFormat.parse(valores[12]);
						System.out.println(new java.sql.Timestamp(fechaCheckIn.getTime()));
						fechaCheckInTime = new java.sql.Timestamp(fechaCheckIn.getTime());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					java.util.Date fechaOut;
					java.sql.Timestamp fechaOutTime = null;;
					try {
						fechaOut = dateFormat.parse(valores[14]);
						System.out.println(new java.sql.Timestamp(fechaOut.getTime()));
						fechaOutTime = new java.sql.Timestamp(fechaOut.getTime());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					java.util.Date fechaIngreso;
					java.sql.Timestamp fechaIngresoTime = null;;
					try {
						fechaIngreso = dateFormat.parse(valores[13]);
						System.out.println(new java.sql.Timestamp(fechaIngreso.getTime()));
						fechaIngresoTime = new java.sql.Timestamp(fechaIngreso.getTime());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					java.util.Date fechaEgreso;
					java.sql.Timestamp fechaEgresoTime = null;;
					try {
						fechaEgreso = dateFormat.parse(valores[15]);
						System.out.println(new java.sql.Timestamp(fechaEgreso.getTime()));
						fechaEgresoTime = new java.sql.Timestamp(fechaEgreso.getTime());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					ClienteDTO clienteParaTabla = new ClienteDTO(0, valores[0], valores[1], valores[2], valores[3], valores[4], valores[5], true, gettedDatePickerDate);
					//Actualización idCliente si ya existe
					if(consultarRepetidos(clienteParaTabla)!=null)
						clienteParaTabla.setIdCliente(consultarRepetidos(clienteParaTabla).getIdCliente());

					//IdUsuario hardcodeado
					ReservaCuartoDTO reservaParaTabla = new ReservaCuartoDTO(clienteParaTabla.getIdCliente(), Integer.parseInt(valores[7]), 1, new BigDecimal(valores[8]), new BigDecimal(valores[9]), valores[10],
							valores[18], valores[16], valores[17], valores[20], valores[19], fechaReservaTime, fechaCheckInTime, fechaOutTime, 
							fechaIngresoTime, fechaEgresoTime, "Pendiente", valores[21], true);
					
					reservaParaTabla.setIdReserva(0);
					
					//Validacion IdCuartoExistente
					if(cuarto.traerCuarto(reservaParaTabla.getIdCuarto()) == null) {
						tieneErrores = true;
						Timestamp timestamp = new Timestamp(System.currentTimeMillis());															
						ErrorImportarDTO error = new ErrorImportarDTO(0, timestamp, 1, "El idCuarto: "+ reservaParaTabla.getIdCuarto() +",de la fila número: " +fila+" , no existe.");
						errorImportar.agregarError(error);
					}
					//Dias hardcodeados
					reservaParaTabla.setCantidadDias("1");
					//	reservaParaTabla.setNumTarjeta(reservaParaTabla.getTiposTarjeta());
					//	reservaParaTabla.setFormasDePago(reservaParaTabla.getFormasDePago());
					//	System.out.println(reservaParaTabla.getFormasDePago());
					//	System.out.println(reservaParaTabla.getTiposTarjeta());
					clientesAcargar.add(clienteParaTabla);
					reservasAcargar.add(reservaParaTabla);
					fila ++;

				}
				crearTabla(clientesAcargar);
				crearTablaReservas(reservasAcargar);

				if(tieneErrores) {
					btnInsertar.setDisable(true);
					Validador.mostrarMensaje("Archivo con errores, por favor verificar el reporte correspondiente.");
				}					

				scanner.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//Date hoy = new Date(System.currentTimeMillis());

			} 

		} else {
			Validador.mostrarMensaje("Archivo no válido");			
		}
	}
	
	private <T> ClienteDTO consultarRepetidos(T objeto) {
		if(objeto instanceof ClienteDTO) {
			activeSession = getAllClientes();			
			for(ClienteDTO cli: this.activeSession) {			
				if(cli.getTipoDocumento().equals(((ClienteDTO)objeto).getTipoDocumento().replace("\"", "")) && 
						cli.getNumeroDocumento().equals(((ClienteDTO)objeto).getNumeroDocumento().replace("\"", ""))){				
					return cli;
				}
			}
		}
		return null;
	}

}
