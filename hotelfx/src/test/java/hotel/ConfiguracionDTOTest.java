package hotel;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dto.ConfiguracionDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Configuracion;
import persistencia.dao.mysql.DAOSQLFactory;

public class ConfiguracionDTOTest {
	
//	private ConfiguracionDTO configuracionDto;
	private Configuracion configuracion;
//	private ObservableList<ConfiguracionDTO> listaConfig;
	
	@Before
	public void init() {
		
		configuracion = new Configuracion(new DAOSQLFactory());
//		this.listaConfig = FXCollections.observableArrayList();
//		this.listaConfig = getAllConfigs();
		
	}	
	
	private List<ConfiguracionDTO> getAllConfigs() {
		List<ConfiguracionDTO> configs = this.configuracion.obtenerConfiguraciones();
		return configs;
	}
	
	@Test
	public void testInsercionPorSqlOk() throws Exception {
		assertEquals(1,getAllConfigs().size());
	
	}
	
	@Test
	public void testEditarMailConfig() throws Exception {
		String mail = "mail@mail.com";
		String password = "1234";
		String provSMTP = "smtp.gmail.com"; 			
		
		ConfiguracionDTO config = new ConfiguracionDTO(1, mail, password, provSMTP);		
		this.configuracion.modificarConfiguracion(config);
		
		assertEquals("mail@mail.com", getAllConfigs().get(0).getUsername());
		assertEquals("1234", getAllConfigs().get(0).getPassword());
		assertEquals("smtp.gmail.com", getAllConfigs().get(0).getProvSMTP());
				
	}
	

}
