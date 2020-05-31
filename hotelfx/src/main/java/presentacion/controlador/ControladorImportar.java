package presentacion.controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import dto.ClienteDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import modelo.Cliente;
import persistencia.dao.mysql.DAOSQLFactory;

public class ControladorImportar implements Initializable {


	//	@FXML private Controller controller;

	//	@FXML private ControladorMenuPrincipal menuPrincipal;
	@FXML Button btnImportar;
	@FXML private Button btnRefrescar;
	@FXML private Button btnInsertar;
	@FXML private Button btnMail;
	@FXML ListView lstview;
	@FXML private TableView<ClienteDTO> tablaClientesImportar;
	@FXML private TableColumn idCliente;
	@FXML private TableColumn Nombre;
	@FXML private TableColumn Apellido;
	@FXML private TableColumn TipoDocumento;
	@FXML private TableColumn Documento;
	@FXML private TableColumn Email;
	@FXML private TableColumn Telefono;
	@FXML private TableColumn Estado;
	@FXML private TableColumn FechaNacimiento;
	private Cliente cliente;
	private int cantidadNuevosClientes;
	@FXML private Mail mail;
	@FXML private ObservableList<ClienteDTO> activeSession;
	@FXML private ObservableList<ClienteDTO> clientesAcargar;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.cliente = new Cliente(new DAOSQLFactory());
		activeSession = FXCollections.observableArrayList();
		tablaClientesImportar.getItems().clear();
		clientesAcargar = FXCollections.observableArrayList();
		cantidadNuevosClientes = 0;
		mail = new Mail();
//		manejoMail();
		cargarColumnas();
		refrescarTabla();
	}
	
	@FXML
	private void manejoMail() {
//		this.mail= new Mail();
//		this.mail.setearPropiedades();
		this.mail.enviarMsj();
	}
	
	
	private void cargarColumnas() {
		Nombre.setCellValueFactory(new PropertyValueFactory("nombre"));		
		Apellido.setCellValueFactory(new PropertyValueFactory("apellido"));
		TipoDocumento.setCellValueFactory(new PropertyValueFactory("tipoDocumento"));
		Documento.setCellValueFactory(new PropertyValueFactory("numeroDocumento"));
		Email.setCellValueFactory(new PropertyValueFactory("email"));
		Telefono.setCellValueFactory(new PropertyValueFactory("telefono"));
		Estado.setCellValueFactory(new PropertyValueFactory("estado"));
		FechaNacimiento.setCellValueFactory(new PropertyValueFactory("fechaNacimiento"));
		idCliente.setCellValueFactory(new PropertyValueFactory("idCliente"));	

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

	@FXML
	private void refrescarTabla(){
		crearTabla(this.clientesAcargar);
		//		tablaClientesImportar.setRowFactory(row -> new TableRow<ClienteDTO>(){
		//		    public void updateItem(ClienteDTO item, boolean empty){
		//		        super.updateItem(item, empty);
		//
		//		        if (item == null || empty) {
		//		            setStyle("");
		//		        } else {
		//		            //Now 'item' has all the info of the Person in this row
		//		            if (item.getNombre().equals("yop")) {
		//		                //We apply now the changes in all the cells of the row
		//		                for(int i=0; i<getChildren().size();i++){
		//		                    ((Labeled) getChildren().get(i)).setTextFill(Color.RED);
		//		                    ((Labeled) getChildren().get(i)).setStyle("-fx-background-color: yellow");
		//		                }                        
		//		            } else {
		//		                if(getTableView().getSelectionModel().getSelectedItems().contains(item)){
		//		                    for(int i=0; i<getChildren().size();i++){
		//		                        ((Labeled) getChildren().get(i)).setTextFill(Color.WHITE);;
		//		                    }
		//		                }
		//		                else{
		//		                    for(int i=0; i<getChildren().size();i++){
		//		                        ((Labeled) getChildren().get(i)).setTextFill(Color.BLACK);;
		//		                    }
		//		                }
		//		            }
		//		        }
		//		    }
		//		});

		//		crearTabla(getAllClientes());
	}

	private void crearTabla(ObservableList<ClienteDTO> lista) {
		tablaClientesImportar.setItems(lista);
		tablaClientesImportar.setEditable(true);

	}
	
	@FXML
	private void insertarClientesNuevos() {
		for(ClienteDTO c: clientesAcargar) {
			//Si es cliente nuevo, lo agrego a SQL
			if(c.getIdCliente()==0) {
				try {
					guardarCliente(c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		JOptionPane.showMessageDialog(null, "Se insertaron: "+cantidadNuevosClientes+" nuevos clientes.", "Importación Exitosa", JOptionPane.INFORMATION_MESSAGE);
	}

	private <T> ClienteDTO consultarRepetidos(T objeto) {
		if(objeto instanceof ClienteDTO) {
			for(ClienteDTO cli: this.activeSession) {			
				if(cli.getTipoDocumento().equals(((ClienteDTO)objeto).getTipoDocumento().replace("\"", "")) && 
						cli.getNumeroDocumento().equals(((ClienteDTO)objeto).getNumeroDocumento().replace("\"", ""))){				
					return cli;
				}
			}
		}
		return null;
	}


	public void guardarCliente(ClienteDTO nuevoCliente) throws IOException {
		this.cliente.agregarCliente(nuevoCliente);
		cantidadNuevosClientes = cantidadNuevosClientes +1;
		//			cerrarVentanaAgregar();	
	}



	@FXML
	public void importarCSV() {
		//Consulto todos los clientes
		this.activeSession=getAllClientes();
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("Archivos CSV", "*.csv"));
//		ExtensionFilter filter = new ExtensionFilter("*.csv", "csv");
//		fc.setSelectedExtensionFilter(filter);
//		fc.setFileFilter(filter);
//		fc.addChoosableFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
		File selectedFile = fc.showOpenDialog(null);
		if(selectedFile != null) {
			lstview.getItems().add(selectedFile.getAbsolutePath());
			try {
				Scanner scanner = new Scanner(selectedFile);
				//Salteo 1er fila con nombres de columnas
				scanner.nextLine(); 
				while(scanner.hasNext()) {
					String data = scanner.nextLine();
					String[] valores = data.split(",");					
					Boolean estado = true;
					//					System.out.println(valores[7].toString().replace("\"", "").equals("1"));
					if(!valores[7].toString().replace("\"", "").equals("1"))						
						estado = false;
					System.out.println(valores[0]);
					System.out.println(valores[7]);
					System.out.println(valores[8]);					
					java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(valores[8].replace("\"", ""));
					System.out.println(gettedDatePickerDate);

					ClienteDTO clienteParaTabla = new ClienteDTO(0, valores[1], valores[2], valores[3], valores[4], valores[5], valores[6], estado, gettedDatePickerDate);
					
					if(consultarRepetidos(clienteParaTabla)!=null)
						clienteParaTabla.setIdCliente(consultarRepetidos(clienteParaTabla).getIdCliente());
					
					clientesAcargar.add(clienteParaTabla);

					lstview.getItems().add(data);
				}
				crearTabla(clientesAcargar);

				scanner.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("file is not valid");
		}
	}
}
