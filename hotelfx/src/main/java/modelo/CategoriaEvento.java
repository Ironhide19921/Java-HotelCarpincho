package modelo;

import java.util.List;

import dto.CategoriaEventoDTO;
import persistencia.dao.interfaz.CategoriaEventoDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class CategoriaEvento {
		private CategoriaEventoDAO categoriaEvento;
		
		public CategoriaEvento(DAOAbstractFactory metodo_persistencia){
			this.categoriaEvento = metodo_persistencia.createCategoriaEventoDAO();
		}
		
		public void agregarCategoriaEvento(CategoriaEventoDTO nuevaCategoriaEvento){
			this.categoriaEvento.insert(nuevaCategoriaEvento);
		}
		
		public void modificarCategoriaEvento(CategoriaEventoDTO categoriaEvento) {
			this.categoriaEvento.update(categoriaEvento);
		}
		
		public List<CategoriaEventoDTO> obtenerCategoriasEvento() {
			return this.categoriaEvento.readAll();
		}
		
		public void borrarCategoriaEvento(CategoriaEventoDTO categoriaEvento) {
			this.categoriaEvento.delete(categoriaEvento);
		}
		
		public CategoriaEventoDTO obtenerCategoriaEventoPorId(int id) {
			return this.categoriaEvento.get(id);
		}
}

