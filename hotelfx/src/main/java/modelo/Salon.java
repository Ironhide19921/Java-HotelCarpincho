package modelo;

import java.util.List;

import dto.SalonDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.SalonDAO;

public class Salon {

	private SalonDAO salon;
	
	public Salon(DAOAbstractFactory metodo_persistencia){
		this.salon = metodo_persistencia.createSalonDAO();
	}
	
	public void agregarSalon(SalonDTO nuevoSalon){
		this.salon.insert(nuevoSalon);
	}
	
	public void modificarSalon(SalonDTO salon) {
		this.salon.update(salon);
	}
	
	public List<SalonDTO> obtenerSalones() {
		return this.salon.readAll();
	}
	
	public void modificarEstado(SalonDTO salon) {
		this.salon.cambiarEstado(salon);
	}
}
