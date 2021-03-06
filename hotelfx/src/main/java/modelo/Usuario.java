package modelo;

import java.util.List;

import dto.UsuarioDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.UsuarioDAO;

public class Usuario {
	
private UsuarioDAO usuario;
	
	public Usuario(DAOAbstractFactory metodo_persistencia){
		this.usuario = metodo_persistencia.createUsuarioDAO();
	}
	
	public void agregarUsuario(UsuarioDTO nuevoUsuario){
		this.usuario.insert(nuevoUsuario);
	}
	
	public void modificarUsuario(UsuarioDTO usuario) {
		this.usuario.update(usuario);
	}
	
	public List<UsuarioDTO> obtenerUsuarios() 	{
		return this.usuario.readAll();
	}
	
	public List<UsuarioDTO> buscarUsuarios(String buscar) 	{
		return this.usuario.search(buscar);
	}
	
	public List<UsuarioDTO> buscarUsuarios2(String nombre, String pass) 	{
		return this.usuario.search2(nombre, pass);
	}

	public List<UsuarioDTO> buscarUsuarios3(String email) 	{
		return this.usuario.search3(email);
	}

}
