package presentacion.reportes;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import dto.OrdenPedidoDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import persistencia.conexion.Conexion;

public class ReporteTicketReserva {
	
	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint reporteLleno;
	private Logger log = Logger.getLogger(ReporteTicketReserva.class);
	
	public ReporteTicketReserva(OrdenPedidoDTO ordenPedido) {
		
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("idPedidoAbuscar", ordenPedido.getIdOrdenPedido());
		Connection conexion = Conexion.getConexion().getSQLConexion();
    	try	{
    		this.reporte = (JasperReport) JRLoader.loadObjectFromFile("reportes" + File.separator + "ReporteReservaTicket.jasper");
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, conexion);
    		log.info("Se cargó correctamente el reporte");
		}
		catch( JRException ex ) {
			log.error("Ocurrió un error mientras se cargaba el archivo ReportePedidoTicket.Jasper", ex);
		}
		
	}
	
	public void mostrar() {
		this.reporteViewer = new JasperViewer(this.reporteLleno, false);
		this.reporteViewer.setVisible(true);
	}

}
