package modelo;

import java.util.List;

import dto.PermisoPerfilDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PermisoPerfilDAO;

public class PermisoPerfil {
	
private PermisoPerfilDAO permiso;
	
	public PermisoPerfil(DAOAbstractFactory metodo_persistencia){
		this.permiso = metodo_persistencia.createPermisoPerfilDAO();
	}
	
	public void agregarPermiso(PermisoPerfilDTO nuevoPermiso){
		this.permiso.insert(nuevoPermiso);
	}
	
	public void modificarPermiso(PermisoPerfilDTO permiso) {
		this.permiso.update(permiso);
	}
	
	public List<PermisoPerfilDTO> obtenerpermisos() 	{
		return this.permiso.readAll();
	}
	
	public List<PermisoPerfilDTO> buscarPermisos(int buscar){
		return this.permiso.search(buscar);
	}
	
	public void eliminarPermiso(PermisoPerfilDTO eliminarPermiso){
		this.permiso.delete(eliminarPermiso);
	}


}
