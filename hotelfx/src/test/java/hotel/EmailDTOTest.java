package hotel;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import dto.EmailDTO;

public class EmailDTOTest {
	
	private static Date hoy;
	private static Date pasadoMañana;
	private static Date hoyMasSiete;
	
	@BeforeClass
	public static void init() {
		hoy = new Date(System.currentTimeMillis());
		pasadoMañana = new Date(System.currentTimeMillis()+ (2000 * 60 * 60 * 24));
		hoyMasSiete = new Date(System.currentTimeMillis()+ (7000 * 60 * 60 * 24));
	}
	
	@Test
	public void testCompararFechas() throws Exception {
		assertEquals(2, EmailDTO.compararFechas(hoy, pasadoMañana));
		assertEquals(0, EmailDTO.compararFechas(hoy, hoy));
		assertEquals(7, EmailDTO.compararFechas(hoy, hoyMasSiete));
	}
}
