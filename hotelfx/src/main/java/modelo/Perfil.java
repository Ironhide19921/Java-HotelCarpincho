package modelo;

import java.util.List;

import dto.PerfilDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PerfilDAO;

public class Perfil {
	
private PerfilDAO perfil;

	public Perfil(DAOAbstractFactory metodo_persistencia){
		this.perfil = metodo_persistencia.createPerfilDAO();
	}
	
	public void agregarPerfil(PerfilDTO nuevoPerfil){
		this.perfil.insert(nuevoPerfil);
	}
	
	public void modificarPerfil(PerfilDTO perfil) {
		this.perfil.update(perfil);
	}
	
	public List<PerfilDTO> obtenerPerfil() 	{
		return this.perfil.readAll();
	}
	
	public List<PerfilDTO> buscarPerfil(int buscar) 	{
		return this.perfil.search(buscar);
	}
	
	public void borrarPerfil(PerfilDTO perfil_a_eliminar) 
	{
		this.perfil.delete(perfil_a_eliminar);
	}

}
