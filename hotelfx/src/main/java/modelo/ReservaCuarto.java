package modelo;

import java.util.List;

import dto.ReservaCuartoDTO;
import persistencia.dao.interfaz.ReservaCuartoDAO;
import persistencia.dao.mysql.DAOSQLFactory;

public class ReservaCuarto{
	
 private ReservaCuartoDAO reserva;
 
	public ReservaCuarto(DAOSQLFactory metodo_persistencia) {
		this.reserva = metodo_persistencia.createReservaCuartoDAO();
	}
	
	public void agregarReservaCuarto(ReservaCuartoDTO nuevoCliente){
		this.reserva.insert(nuevoCliente);
	}
	
	public void modificarReservaCuarto(ReservaCuartoDTO cliente) {
		this.reserva.update(cliente);
	}
	
	public List<ReservaCuartoDTO> obtenerReservasCuartos() 	{
		return this.reserva.readAll();
	}
	
	public List<ReservaCuartoDTO> buscarReservaCuarto(String buscar) 	{
		return this.reserva.search(buscar);
	}
	
	public List<ReservaCuartoDTO> buscarReservaCuartoCliente(int idCliente){
		return this.reserva.buscarReservaCuartoCliente(idCliente);
	}

	public ReservaCuartoDTO obtenerReservaCuartoPorId(int selectedItem) {
		// TODO Auto-generated method stub
		return this.reserva.obtenerReservaCuartoPorId(selectedItem);
	};
}
