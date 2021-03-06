package presentacion.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.Cuarto;
import modelo.ErrorImportar;
import modelo.Validador;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.ReporteContableReservaCuarto;
import presentacion.reportes.ReporteEncuesta;
import presentacion.reportes.ReporteErroresImportar;
import presentacion.reportes.ReporteGraficoContable;
import presentacion.reportes.ReporteOcupacion;

public class ControladorVentanaReportes implements Initializable{
	
	@FXML private Button btnOcupacion;
	@FXML private Button btnContable;
	@FXML private Button btnReservas;
	@FXML private Button btnErrores;
	@FXML private Button btnEncuestas;
	@FXML private Button btnGenerarReporte;
	@FXML private DatePicker fechaDesde;
	@FXML private DatePicker fechaHasta;
	
	private Cuarto cuarto;
	private String reporte;
	
	private ArrayList<Button> listaButtons;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.cuarto = new Cuarto(new DAOSQLFactory());
		listaButtons = new ArrayList<Button>();
		listaButtons.add(btnOcupacion);
		listaButtons.add(btnContable);
		listaButtons.add(btnReservas);
		listaButtons.add(btnErrores);
		listaButtons.add(btnEncuestas);
		
		for(Button boton : listaButtons) {
			boton.setDisable(true);
		}
		
		for(int i=16; i<21; i++) {
			if(ControladorLogin.permisosPorId.contains(i))
				this.listaButtons.get(i-16).setDisable(false);
		}
		cargarIconos();
	}
	
	@FXML
	public void deshabilitarParametrosFechas() {
		fechaDesde.setDisable(true);
		fechaHasta.setDisable(true);
		this.reporte = "ocupacion";
	}
	
	@FXML
	public void elegirError() {
		this.reporte = "error";
	}
	
	@FXML
	public void elegirEncuesta() {
		fechaDesde.setDisable(true);
		fechaHasta.setDisable(true);
		this.reporte = "encuesta";
	}
	
	@FXML
	public void elegirContable() {
		fechaDesde.setDisable(false);
		fechaHasta.setDisable(false);
		this.reporte = "contable";
	}
	
	@FXML
	public void habilitarParametrosFechas() {
		fechaDesde.setDisable(false);
		fechaHasta.setDisable(false);
	}
	
	@FXML
	public void generarReporte() {

		try {
			if(this.reporte.equals("ocupacion")) {
				ReporteOcupacion reporteOcupacion = new ReporteOcupacion(cuarto.obtenerCuartos());
				reporteOcupacion.mostrar();
			}else if(this.reporte.equals("error")) {
				ReporteErroresImportar reporteError = new ReporteErroresImportar(this.getFechaDesde().getValue(),this.getFechaHasta().getValue());
				reporteError.mostrar();
			}else if(this.reporte.equals("encuesta")) {
				ReporteEncuesta reporteEncuesta = new ReporteEncuesta();
				reporteEncuesta.mostrar();
			}else if(this.reporte.equals("contable")) {
				ReporteContableReservaCuarto reporteContable = new ReporteContableReservaCuarto(this.getFechaDesde().getValue(),this.getFechaHasta().getValue());
				ReporteGraficoContable reporteGrafico = new ReporteGraficoContable(this.getFechaDesde().getValue(),this.getFechaHasta().getValue());
				reporteContable.mostrar();
				reporteGrafico.mostrar();
			}	
		} catch (Exception e) {
			// TODO: handle exception
			Validador.mostrarMensaje("Sin valores para reporte");
		}

	}

	public DatePicker getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(DatePicker fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public DatePicker getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(DatePicker fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	

	 private void cargarIconos() {
			
			URL linkOcupacion = getClass().getResource("/img/reporteOcupacion.png");
			URL linkContable = getClass().getResource("/img/reporteContable.png");
			URL linkReservas = getClass().getResource("/img/reporte.png");
			URL linkErrores = getClass().getResource("/img/reporteErrores.png");
			URL linkEncuestas = getClass().getResource("/img/reporteEncuesta.png");
			URL linkGenerar = getClass().getResource("/img/descarga.png");
			
			Image imageOcupacion = new Image(linkOcupacion.toString(),24,24,false,true) ;
			Image imageContable = new Image(linkContable.toString(),24,24,false,true) ;
			Image imageReservas = new Image(linkReservas.toString(),24,24,false,true) ;
			Image imageErrores = new Image(linkErrores.toString(),24,24,false,true) ;
			Image imageEncuestas = new Image(linkEncuestas.toString(),24,24,false,true) ;
			Image imageGenerar = new Image(linkGenerar.toString(),24,24,false,true) ;
		
			this.btnOcupacion.setGraphic(new ImageView(imageOcupacion));
			this.btnContable.setGraphic(new ImageView(imageContable));
			this.btnReservas.setGraphic(new ImageView(imageReservas));
			this.btnErrores.setGraphic(new ImageView(imageErrores));
			this.btnEncuestas.setGraphic(new ImageView(imageEncuestas));
			this.btnGenerarReporte.setGraphic(new ImageView(imageGenerar));
			//this.btnReporte.setGraphic(new ImageView(imageSeleccionar));
			
		}

}
