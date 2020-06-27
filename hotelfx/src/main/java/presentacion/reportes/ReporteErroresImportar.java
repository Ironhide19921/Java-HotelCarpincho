package presentacion.reportes;

import java.io.File;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dto.CuartoDTO;
import dto.ErrorImportarDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import persistencia.conexion.Conexion;

public class ReporteErroresImportar {
	
	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;
	private Logger log = Logger.getLogger(ReporteErroresImportar.class);
	
	public ReporteErroresImportar(List<ErrorImportarDTO> listaErrores, LocalDate fechaDesde,LocalDate fechaHasta) {
		
		JRBeanCollectionDataSource lista = new JRBeanCollectionDataSource(listaErrores);
		
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		//parametersMap.put("listaErrores", lista);
		
//		Timestamp tDesde = new Timestamp(fechaDesde);
		Timestamp FechaDesde = Timestamp.valueOf(fechaDesde.atTime(LocalTime.of(0,0,0)));
		Timestamp FechaHasta = Timestamp.valueOf(fechaHasta.atTime(LocalTime.of(23,59,59)));
		
		parametersMap.put("FechaDesde", FechaDesde);
		parametersMap.put("FechaHasta", FechaHasta);
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try	{
			this.reporte = (JasperReport) JRLoader.loadObjectFromFile("reportes" + File.separator + "ReporteErroresImportar.jasper");
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, conexion);
			log.info("Se cargó correctamente el reporte");
			
		}
		catch(JRException ex){
			log.error("Ocurrió un error mientras se cargaba el archivo ReporteErroresImportar.Jasper", ex);
		}
		
	}
	
	public void mostrar() {
		this.reporteViewer = new JasperViewer(this.reporteLleno, false);
		this.reporteViewer.setVisible(true);
	}

}
