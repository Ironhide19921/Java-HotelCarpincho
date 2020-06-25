package presentacion.reportes;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dto.CuartoDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import persistencia.conexion.Conexion;

public class ReporteOcupacion {
	
	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;
	private Logger log = Logger.getLogger(ReporteOcupacion.class);
	
	public ReporteOcupacion(List<CuartoDTO> listaCuartos) {
		
		JRBeanCollectionDataSource lista = new JRBeanCollectionDataSource(listaCuartos);
		
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("ListaCuarto", lista);		
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try	{
			this.reporte = (JasperReport) JRLoader.loadObjectFromFile("reportes" + File.separator + "ReporteOcupacion.jasper");
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, conexion);
			log.info("Se cargó correctamente el reporte");
			
		}
		catch(JRException ex){
			log.error("Ocurrió un error mientras se cargaba el archivo ReporteOcupacion.Jasper", ex);
		}
		
	}
	
	public void mostrar() {
		this.reporteViewer = new JasperViewer(this.reporteLleno, false);
		this.reporteViewer.setVisible(true);
	}

}
