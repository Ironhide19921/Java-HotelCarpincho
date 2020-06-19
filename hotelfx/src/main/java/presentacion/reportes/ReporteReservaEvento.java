package presentacion.reportes;

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dto.ReservaEventoDTO;
import dto.TicketDTO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import persistencia.conexion.Conexion;

public class ReporteReservaEvento {
	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint	reporteLleno;
	private Logger log = Logger.getLogger(ReporteReservaEvento.class);
	private int numeroTicket;
	private String path;
	
	//Recibe la lista de personas para armar el reporte
    public ReporteReservaEvento(ReservaEventoDTO reserva, String pathTicket)
    {
    	this.path = pathTicket;
    	//Hardcodeado
    	Map<String, Object> parametersMap = new HashMap<String, Object>();
    	Connection conexion = Conexion.getConexion().getSQLConexion();
		parametersMap.put("idReservaEvento", reserva.getIdReservaEvento());

    	try	{
			this.reporte = (JasperReport) JRLoader.loadObjectFromFile( "reportes" + File.separator + "ReporteReservaEvento.jasper" );
			System.out.println(reserva.getObservaciones());
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, conexion);
    		log.info("Se cargó correctamente el reporte");
		}
		catch( JRException ex ) 
		{
			log.error("Ocurrió un error mientras se cargaba el archivo ReporteReservaEvento.jasper", ex);
		}
    }       
    
    public void mostrar()
	{
		this.reporteViewer = new JasperViewer(this.reporteLleno,false);
		this.reporteViewer.setVisible(true);
	}
    
    public void guardarPdf() {
    	
    	try {
			JasperExportManager.exportReportToPdfFile(this.reporteLleno, this.path);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			System.out.println("Rompe todo");
			e.printStackTrace();
		}
    }
}
