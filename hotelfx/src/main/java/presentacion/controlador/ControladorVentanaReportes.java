package presentacion.controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import modelo.Cuarto;
import persistencia.dao.mysql.DAOSQLFactory;
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
		
	}
	
	@FXML
	public void deshabilitarParametrosFechas() {
		fechaDesde.setDisable(true);
		fechaHasta.setDisable(true);
		this.reporte = "ocupacion";
	}
	
	@FXML
	public void habilitarParametrosFechas() {
		fechaDesde.setDisable(false);
		fechaHasta.setDisable(false);
	}
	
	@FXML
	public void generarReporteOcupacion() {
		if(this.reporte.equals("ocupacion")) {
			ReporteOcupacion reporteOcupacion = new ReporteOcupacion(cuarto.obtenerCuartos());
			reporteOcupacion.mostrar();
		}		
	}

}
