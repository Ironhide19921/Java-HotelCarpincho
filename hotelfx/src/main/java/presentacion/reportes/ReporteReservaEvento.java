package presentacion.reportes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dto.ReservaEventoDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteReservaEvento {
	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint	reporteLleno;
	private Logger log = Logger.getLogger(ReporteReservaEvento.class);
	//Recibe la lista de personas para armar el reporte
    public ReporteReservaEvento(List<ReservaEventoDTO> reservas)
    {
    	//Hardcodeado
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));		
    	try		{
			this.reporte = (JasperReport) JRLoader.loadObjectFromFile( "reportes" + File.separator + "ReporteReservaEvento.jasper" );
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, 
					new JRBeanCollectionDataSource(reservas));
    		log.info("Se cargÃ³ correctamente el reporte");
		}
		catch( JRException ex ) 
		{
			log.error("OcurriÃ³ un error mientras se cargaba el archivo ReporteReservaEvento.jasper", ex);
		}
    }       
    
    public void mostrar()
	{
		this.reporteViewer = new JasperViewer(this.reporteLleno,false);
		this.reporteViewer.setVisible(true);
	}
}
