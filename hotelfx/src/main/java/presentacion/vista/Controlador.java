package presentacion.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

import dto.CategoriaCuartoDTO;
import dto.ClienteDTO;
import dto.PerfilDTO;
import dto.PermisoDTO;
import dto.PermisoPerfilDTO;
import dto.CuartoDTO;
import dto.UsuarioDTO;
import modelo.Cuarto;
import modelo.Hotel;
import modelo.Perfil;
import modelo.PermisoPerfil;
import modelo.Usuario;
import presentacion.vista.ABMCliente;
import presentacion.vista.ABMPerfil;
import presentacion.vista.ABMCuarto;
import presentacion.vista.ABMusuario;
import presentacion.vista.VentanaCliente;
import presentacion.vista.VentanaCuarto;
import presentacion.vista.ABMCategoriaCuarto;
import presentacion.vista.VentanaUsuario;
import presentacion.vista.VentanaCategoriaCuarto;

public class Controlador implements ActionListener{
	
	private ABMCliente vistaABMCliente;
	private ABMCategoriaCuarto vistaABMCategoriaCuarto;
	private ABMusuario vistaABMusuario;
	private ABMPerfil vistaABMPerfil;
	private Hotel hotel;
	private VentanaCliente ventanaCliente;
	private VentanaCategoriaCuarto ventanaCategoriaCuarto;
	private VentanaUsuario ventanaUsuario;
	private List<ClienteDTO> clientesEnTabla;
	private List<CategoriaCuartoDTO> categoriasCuartosEnTabla;
	private List<UsuarioDTO> usuariosEnTabla;
	private Usuario usuario;
	private Perfil perfil;
	private PermisoPerfil permisoPerfil;
	private List<PerfilDTO> perfilesEnTabla;
	private Cuarto cuarto;
	private ABMCuarto vistaABMCuarto;
	private VentanaCuarto ventanaCuarto;
	private List<CuartoDTO> cuartosEnTabla;

	public Controlador(ABMCliente vista, Hotel hotel, ABMusuario vistaUsuario, Usuario usuario, ABMCategoriaCuarto vistaCategoriaCuarto, ABMCuarto vistaCuartos, Cuarto cuarto,Perfil perfil, PermisoPerfil permisoPerfil, ABMPerfil vistaABMPerfil) {
		this.vistaABMCliente = vista;
		this.vistaABMCategoriaCuarto = vistaCategoriaCuarto; 
		this.vistaABMusuario = vistaUsuario;
		this.vistaABMCuarto = vistaCuartos;
		this.hotel = hotel;
		this.usuario = usuario;
		this.permisoPerfil = permisoPerfil;
		this.perfil = perfil;
		this.cuarto = cuarto;
		this.ventanaCliente = VentanaCliente.getInstance();
		this.ventanaUsuario = VentanaUsuario.getInstance();
		this.vistaABMPerfil = ABMPerfil.getInstance();
		this.perfilesEnTabla = perfil.obtenerPerfil();
		//this.permisosPerfilesEnTabla = permisoPerfil.o
		
		//guardo el usuario si apretamos el boton de confirmar
		this.ventanaUsuario = VentanaUsuario.getInstance();
		this.ventanaUsuario.getBtnConfirmarUsuario().addActionListener(g->confirmarUsuario(g));
				
		//guardar cambios usuario si apretamos el boton de confirmar cambios
		this.ventanaUsuario.getBtnConfirmarCambios().addActionListener(h->confirmarCambiosUsuario(h));
			
		this.vistaABMusuario.getBtnBuscar().addActionListener(l -> buscarUsuario(l, this.vistaABMusuario.getTxtBuscar().getText()));
		this.vistaABMusuario.getBtnLimpiarFiltro().addActionListener(a -> limpiarFiltroUsuario(a));
		this.ventanaCategoriaCuarto = VentanaCategoriaCuarto.getInstance();
		this.vistaABMusuario.getBtnAgregar().addActionListener(b->agregarUsuario(b));
		this.vistaABMusuario.getBtnEditar().addActionListener(d->editarUsuario(d));
		
		this.vistaABMCliente.getBtnAgregar().addActionListener(a->agregarCliente(a));
		this.vistaABMCliente.getBtnEditar().addActionListener(e->editarCliente(e));
		
		//guardo el cliente si apretamos el boton de agregar
		this.ventanaCliente.getBtnAgregarCliente().addActionListener(c->guardarCliente(c));
		//guardar cambios cliente si apretamos el boton de agregar
		this.ventanaCliente.getBtnEditarCliente().addActionListener(f->guardarCambiosCliente(f));
		
		this.vistaABMCliente.getBtnBuscar().addActionListener(l -> buscarCliente(l, this.vistaABMCliente.getTxtBuscar().getText()));
		this.vistaABMCliente.getBtnLimpiarFiltro().addActionListener(a -> limpiarFiltro(a));
		
		//Perfil
		this.ventanaUsuario.getBtnABMPerfiles().addActionListener(f -> abrirVentanaPerfil(f));
		this.vistaABMPerfil.getBtnCrear().addActionListener(i -> crearPerfil(i));
		this.vistaABMPerfil.getBtnEliminar().addActionListener(j -> eliminarPerfil(j));
		this.vistaABMPerfil.getBtnConfirmar().addActionListener(k -> confirmarPermisos(k));
		
		this.vistaABMPerfil.getComboBox().addActionListener(z -> refrescarPermisos(z));
		
		
		//CATEGORIA CUARTO
		this.vistaABMCategoriaCuarto.getBtnAgregar().addActionListener(a->agregarCategoriaCuarto(a));
		this.vistaABMCategoriaCuarto.getBtnEditar().addActionListener(e->editarCategoriaCuarto(e));
		this.vistaABMCategoriaCuarto.getBtnBorrar().addActionListener(u->borrarCategoriaCuarto(u));
		
		//guardo el cliente si apretamos el boton de agregar
		this.ventanaCategoriaCuarto.getBtnAgregarCategoriaCuarto().addActionListener(c->guardarCategoriaCuarto(c));
		//guardar cambios cliente si apretamos el boton de agregar
		this.ventanaCategoriaCuarto.getBtnEditarCategoriaCuarto().addActionListener(f->guardarCambiosCategoriaCuarto(f));
		
		//CUARTOS
		this.ventanaCuarto = VentanaCuarto.getInstance();
						
		this.vistaABMCuarto.getBtnAgregar().addActionListener(l -> agregarCuarto(l));
		this.vistaABMCuarto.getBtnEditar().addActionListener(e -> editarCuarto(e));
		this.vistaABMCuarto.getBtnCambiarEstado().addActionListener(o -> cambiarEstado(o));
		this.vistaABMCuarto.getBtnBuscar().addActionListener(l -> buscarCuarto(l, this.vistaABMCuarto.getTxtBuscar().getText()));
		this.vistaABMCuarto.getBtnLimpiarFiltro().addActionListener(l -> limpiarFiltroCuarto(l));
		
		this.ventanaCuarto.getBtnAgregarCuarto().addActionListener(a -> guardarCuarto(a));
		this.ventanaCuarto.getBtnEditarCuarto().addActionListener(l -> guardarCambiosCuarto(l));
		this.ventanaCuarto.getBtnIrACateCuarto().addActionListener(l -> mostrarVistaCategoriasCuarto(l));
		this.ventanaCuarto.getBtnRefrescarCate().addActionListener(l -> refrescarListaCateCuarto(l));
		
	}
	
	private void confirmarPermisos(ActionEvent k) {
		PermisoPerfilDTO permisoPerfilNuevo = new PermisoPerfilDTO(0,0,0);
		permisoPerfilNuevo.setIdPerfil(((PerfilDTO) this.vistaABMPerfil.getComboBox().getSelectedItem()).getIdPerfil());
		
		if(this.vistaABMPerfil.getChckbxAbmClientes().isSelected()) {
			permisoPerfilNuevo.setIdPermiso(1);
			this.permisoPerfil.agregarPermiso(permisoPerfilNuevo);
		}else {
			permisoPerfilNuevo.setIdPermiso(1);
			this.permisoPerfil.eliminarPermiso(permisoPerfilNuevo);
		}
		
		if(this.vistaABMPerfil.getChckbxAbmUsuarios().isSelected()) {
			permisoPerfilNuevo.setIdPermiso(4);
			this.permisoPerfil.agregarPermiso(permisoPerfilNuevo);
		}else {
			permisoPerfilNuevo.setIdPermiso(4);
			this.permisoPerfil.eliminarPermiso(permisoPerfilNuevo);
		}
		
		if(this.vistaABMPerfil.getChckbxAbmCuartos().isSelected()) {
			permisoPerfilNuevo.setIdPermiso(3);
			this.permisoPerfil.agregarPermiso(permisoPerfilNuevo);
		}else {
			permisoPerfilNuevo.setIdPermiso(3);
			this.permisoPerfil.eliminarPermiso(permisoPerfilNuevo);
		}
		
		
	}

	private void eliminarPerfil(ActionEvent j) {
		this.perfil.borrarPerfil((PerfilDTO) this.vistaABMPerfil.getComboBox().getSelectedItem());
		this.perfilesEnTabla = perfil.obtenerPerfil();
		refrescarCombo();
		
	}

	private void crearPerfil(ActionEvent i) {
		String nombrePerfil = this.vistaABMPerfil.getTxtNombre().getText();
		
		PerfilDTO nuevoPerfil = new PerfilDTO(0, nombrePerfil);
		this.perfil.agregarPerfil(nuevoPerfil);
		
		this.perfilesEnTabla = perfil.obtenerPerfil();
		refrescarComboVistaPerfiles();
		
	}

	private void abrirVentanaPerfil(ActionEvent f) {
		refrescarComboVistaPerfiles();
		this.vistaABMPerfil.show();
//		
//		System.out.println(obtenerPermisosPorIdPerfil(1));
//		System.out.println(obtenerPermisosPorIdPerfil(1).get(0).getIdPermiso());
//		System.out.println(obtenerPermisosPorIdPerfil(1).get(0).getIdPerfil());
		
		
	}

	private void refrescarPermisos(ActionEvent z) {
		this.vistaABMPerfil.getChckbxAbmClientes().setSelected(false);
		this.vistaABMPerfil.getChckbxAbmUsuarios().setSelected(false);
		this.vistaABMPerfil.getChckbxAbmCuartos().setSelected(false);
		
		int idPerfil = 0;
		
		if(this.vistaABMPerfil.getComboBox().getSelectedItem()!=null) {
			idPerfil = ((PerfilDTO) this.vistaABMPerfil.getComboBox().getSelectedItem()).getIdPerfil();
		}
		
		for (PermisoPerfilDTO permiso : obtenerPermisosPorIdPerfil(idPerfil)) {
			if(permiso.getIdPermiso()==1) {
				this.vistaABMPerfil.getChckbxAbmClientes().setSelected(true);
			}else {
				//this.vistaABMPerfil.getChckbxAbmClientes().setSelected(false);
			}
			if(permiso.getIdPermiso()==3) {
				this.vistaABMPerfil.getChckbxAbmCuartos().setSelected(true);
			}else {
				//this.vistaABMPerfil.getChckbxAbmCuartos().setSelected(false);
			}
			if(permiso.getIdPermiso()==4) {
				this.vistaABMPerfil.getChckbxAbmUsuarios().setSelected(true);
			}else {
				//this.vistaABMPerfil.getChckbxAbmUsuarios().setSelected(false);
			}
		}
	}

	private void borrarCategoriaCuarto(ActionEvent u) {
		int id = obtenerIdCategoriaCuarto();
		for (CategoriaCuartoDTO categoriaCuarto : categoriasCuartosEnTabla) {
			if(categoriaCuarto.getIdCategoriaCuarto() == id) {	
				//seteo los campos de la ventana
				this.hotel.borrarCategoriaCuarto(categoriaCuarto);
			}
		}
		refrescarTabla();
	}

	private void guardarCambiosCategoriaCuarto(ActionEvent f) {
		int id = obtenerIdCategoriaCuarto();
		String nombre = this.ventanaCategoriaCuarto.getTxtNombre().getText();
		String detalle = this.ventanaCategoriaCuarto.getTxtDetalle().getText();
		CategoriaCuartoDTO categoriaCuarto = new CategoriaCuartoDTO(id, nombre, detalle);
		this.hotel.modificarCategoriaCuarto(categoriaCuarto);
		
		this.ventanaCategoriaCuarto.cerrar();
		refrescarTabla();
	}

	private void guardarCategoriaCuarto(ActionEvent c) {
		String nombre = this.ventanaCategoriaCuarto.getTxtNombre().getText();
		String detalle = this.ventanaCategoriaCuarto.getTxtDetalle().getText();
		
		CategoriaCuartoDTO nuevaCategoriaCuarto = new CategoriaCuartoDTO(0, nombre, detalle);
		this.hotel.agregarCategoriaCuarto(nuevaCategoriaCuarto);
		
		this.ventanaCategoriaCuarto.cerrar();
		refrescarTabla();
	}

	private void editarCategoriaCuarto(ActionEvent e) {
		int id = obtenerIdCategoriaCuarto();
		//clientesEnTabla lista de clientes
		for (CategoriaCuartoDTO categoriaCuarto : categoriasCuartosEnTabla) {
			if(categoriaCuarto.getIdCategoriaCuarto() == id) {	
				//seteo los campos de la ventana
				
				this.ventanaCategoriaCuarto.setTxtNombre(categoriaCuarto.getNombre());
				this.ventanaCategoriaCuarto.setTxtDetalle(categoriaCuarto.getDetalle());
			}
		}
		//ocultar botones de agregar
		this.ventanaCategoriaCuarto.getBtnAgregarCategoriaCuarto().setVisible(false);
		
		this.ventanaCategoriaCuarto.mostrarVentana();
	}

	private int obtenerIdCategoriaCuarto() {
		int[] filasSeleccionadas = this.vistaABMCategoriaCuarto.getTablaCategoriaCuarto().getSelectedRows();
		int id = 0;
		for (int fila : filasSeleccionadas)
		{
			id = Integer.parseInt(String.valueOf(this.vistaABMCategoriaCuarto.getModelCategoriasCuartos().getValueAt(fila, 0)));
		}
		return id;
	}

	private void agregarCategoriaCuarto(ActionEvent a) {
		this.ventanaCategoriaCuarto.limpiar();
		this.ventanaCategoriaCuarto.getBtnAgregarCategoriaCuarto().setVisible(true);
		this.ventanaCategoriaCuarto.mostrarVentana();
	}

	private void editarUsuario(ActionEvent d) {
		refrescarCombo();
		int id = obtenerIdUsuario();
		//clientesEnTabla lista de clientes
		for (UsuarioDTO usuario : usuariosEnTabla) {
			if(usuario.getIdUsuario() == id) {	
				//seteo los campos de la ventana
				
				this.ventanaUsuario.setTxtNombreUsuario(usuario.getNombre());
				this.ventanaUsuario.setTxtApellidoUsuario(usuario.getApellido());
				this.ventanaUsuario.setTxtTipoDocUsuario(usuario.getTipoDocumento());
				this.ventanaUsuario.setTxtDocUsuario(usuario.getNumeroDocumento());
				this.ventanaUsuario.setTxtEmailUsuario(usuario.getEmail());
				this.ventanaUsuario.setTxtPassUsuario(usuario.getPassword());
				
				for(PerfilDTO p : perfilesEnTabla) {
					if(p.getIdPerfil() == usuario.getIdPerfil() ) {
						this.ventanaUsuario.getComboBoxPerfiles().setSelectedItem(p);
					}
				}

				if(usuario.getEstado()){
					this.ventanaUsuario.setRdbtnUsuarioDisponible(true);
				}
				else {
					this.ventanaUsuario.setRdbtnUsuarioNoDisponible(true);	
				}
			}
		}
		//ocultar botones de agregar
		this.ventanaUsuario.getBtnConfirmarUsuario().setVisible(false);
		
		this.ventanaUsuario.mostrarVentana();
		
	}

	private void agregarUsuario(ActionEvent b) {
		refrescarCombo();
		this.ventanaUsuario.getBtnConfirmarUsuario().setVisible(true);
		this.ventanaUsuario.mostrarVentana();
	}

	private void limpiarFiltroUsuario(ActionEvent a) {
		this.vistaABMusuario.getTxtBuscar().setText("");
		this.refrescarTablaUsuarios();
	}

	private void buscarUsuario(ActionEvent l, String buscar) {
		this.usuariosEnTabla = usuario.buscarUsuarios(buscar);
		this.vistaABMusuario.llenarTabla(this.usuariosEnTabla);
	}

	private void confirmarCambiosUsuario(ActionEvent h) {
		int id = obtenerIdUsuario();
		String nombre = this.ventanaUsuario.getTxtNombreUsuario().getText();
		String apellido = this.ventanaUsuario.getTxtApellidoUsuario().getText();
		String tipoDoc = this.ventanaUsuario.getTxtTipoDocUsuario().getText();
		String documento = this.ventanaUsuario.getTxtDocUsuario().getText();
		String email = ventanaUsuario.getTxtEmailUsuario().getText();
		Boolean estado = ventanaUsuario.getRdbtnUsuarioDisponible().isSelected();
		String password = this.ventanaUsuario.getTxtPassUsuario().getText();
		
		int perfil = ((PerfilDTO) this.ventanaUsuario.getComboBoxPerfiles().getSelectedItem()).getIdPerfil();
		
		UsuarioDTO nuevoUsuario = new UsuarioDTO(id, perfil, nombre, apellido, tipoDoc, documento, email, password, estado);
		this.usuario.modificarUsuario(nuevoUsuario);
		
		this.ventanaUsuario.cerrar();
		refrescarTablaUsuarios();
	}

	private void confirmarUsuario(ActionEvent g) {
		String nombre = this.ventanaUsuario.getTxtNombreUsuario().getText();
		String apellido = this.ventanaUsuario.getTxtApellidoUsuario().getText();
		String tipoDoc = this.ventanaUsuario.getTxtTipoDocUsuario().getText();
		String documento = this.ventanaUsuario.getTxtDocUsuario().getText();
		String email = ventanaUsuario.getTxtEmailUsuario().getText();
		Boolean estado = ventanaUsuario.getRdbtnUsuarioDisponible().isSelected();
		String password = this.ventanaUsuario.getTxtPassUsuario().getText();
		
		int perfil = ((PerfilDTO) this.ventanaUsuario.getComboBoxPerfiles().getSelectedItem()).getIdPerfil();
		
		UsuarioDTO nuevoUsuario = new UsuarioDTO(0, perfil, nombre, apellido, tipoDoc, documento, email, password, estado);
		this.usuario.agregarUsuario(nuevoUsuario);
		
		this.ventanaUsuario.cerrar();
		refrescarCombo();
		refrescarTablaUsuarios();
	}

	private void limpiarFiltro(ActionEvent a) {
		this.vistaABMCliente.getTxtBuscar().setText("");
		this.refrescarTabla();
	}
	
	public void inicializar(){
		this.refrescarTabla();
		this.refrescarTablaUsuarios();
		this.refrescarTablaCuartos();
		this.vistaABMCliente.show();
		this.vistaABMCategoriaCuarto.show();
		this.vistaABMusuario.show();
		this.vistaABMCuarto.show();
	}
	
	private void refrescarTabla(){
		this.clientesEnTabla = hotel.obtenerClientes();
		this.categoriasCuartosEnTabla = hotel.obtenerCategoriasCuartos();
		
		this.vistaABMCliente.llenarTabla(this.clientesEnTabla);
		this.vistaABMCategoriaCuarto.llenarTabla(this.categoriasCuartosEnTabla);
	}
	
	private void refrescarTablaUsuarios(){
		this.usuariosEnTabla = usuario.obtenerUsuarios();
		this.vistaABMusuario.llenarTabla(this.usuariosEnTabla);
	}
	
	private void agregarCliente(ActionEvent a) {
		this.ventanaCliente.limpiar();
		this.ventanaCliente.getBtnAgregarCliente().setVisible(true);
		this.ventanaCliente.mostrarVentana();
	}

	private void guardarCliente(ActionEvent c) {
		String nombre = this.ventanaCliente.getTxtNombre().getText();
		String apellido = this.ventanaCliente.getTxtApellido().getText();
		String tipoDoc = this.ventanaCliente.getTxtTipoDocumento().getText();
		String documento = this.ventanaCliente.getTxtNumDocumento().getText();
		String email = ventanaCliente.getTxtEmail().getText();
		String tel = ventanaCliente.getTxtTelefono().getText();
		Boolean estado = ventanaCliente.getRdbtnDisponible().isSelected();
		Date fechaNac = ventanaCliente.getFecha();
		
		ClienteDTO nuevoCliente = new ClienteDTO(0, nombre, apellido, tipoDoc, documento, email, tel, estado, fechaNac);
		this.hotel.agregarCliente(nuevoCliente);
		
		this.ventanaCliente.cerrar();
		refrescarTabla();
	}
	
	public void guardarCambiosCliente(ActionEvent f) {
		int id = obtenerIdCliente();
		String nombre = this.ventanaCliente.getTxtNombre().getText();
		String apellido = this.ventanaCliente.getTxtApellido().getText();
		String tipoDoc = this.ventanaCliente.getTxtTipoDocumento().getText();
		String documento = this.ventanaCliente.getTxtNumDocumento().getText();
		String email = ventanaCliente.getTxtEmail().getText();
		String tel = ventanaCliente.getTxtTelefono().getText();
		Boolean estado = ventanaCliente.getRdbtnDisponible().isSelected();
		Date fechaNac = ventanaCliente.getFecha();
		ClienteDTO cliente = new ClienteDTO(id, nombre, apellido, tipoDoc, documento, email, tel, estado, fechaNac);
		this.hotel.modificarCliente(cliente);
		
		this.ventanaCliente.cerrar();
		refrescarTabla();
	}
	
	
	private void editarCliente(ActionEvent e) {
		int id = obtenerIdCliente();
		//clientesEnTabla lista de clientes
		for (ClienteDTO cliente : clientesEnTabla) {
			if(cliente.getIdCliente() == id) {	
				//seteo los campos de la ventana
				
				this.ventanaCliente.setTxtNombre(cliente.getNombre());
				this.ventanaCliente.setTxtApellido(cliente.getApellido());
				this.ventanaCliente.setTxtTipoDocumento(cliente.getTipoDocumento());
				this.ventanaCliente.setTxtNumDocumento(cliente.getNumeroDocumento());
				this.ventanaCliente.setTxtEmail(cliente.getEmail());
				this.ventanaCliente.setTxtTelefono(cliente.getTelefono());
				this.ventanaCliente.setFecha(cliente.getFechaNacimiento());
				if(cliente.getEstado()){
					this.ventanaCliente.setRdbtnDisponible(true);		
				}
				else {
					this.ventanaCliente.setRdbtnNoDisponible(true);	
				}
			}
		}
		//ocultar botones de agregar
		this.ventanaCliente.getBtnAgregarCliente().setVisible(false);
		
		this.ventanaCliente.mostrarVentana();
		
//		this.ventanaCliente.editarCliente(nuevaLocalidad);
//		this.refrescarTablaLocalidades();
	}
	
	public void buscarCliente(ActionEvent l,String buscar) {
		this.clientesEnTabla = hotel.buscarClientes(buscar);
		this.vistaABMCliente.llenarTabla(this.clientesEnTabla);
	}
	
	
	private int obtenerIdCliente() {
		int[] filasSeleccionadas = this.vistaABMCliente.getTablaPersonas().getSelectedRows();
		int id = 0;
		for (int fila : filasSeleccionadas)
		{
			id = Integer.parseInt(String.valueOf(this.vistaABMCliente.getModelClientes().getValueAt(fila, 0)));
		}
		return id;
	}
	
	private int obtenerIdUsuario() {
		int[] filasSeleccionadas = this.vistaABMusuario.getTablaPersonas().getSelectedRows();
		int id = 0;
		for (int fila : filasSeleccionadas)
		{
			id = Integer.parseInt(String.valueOf(this.vistaABMusuario.getModelUsuarios().getValueAt(fila, 0)));
		}
		return id;
	}
	
	private void refrescarCombo() {
		this.ventanaUsuario.getComboBoxPerfiles().removeAllItems(); //Vaciar combo perfiles

		for (PerfilDTO objeto : perfilesEnTabla) { //Llenar combo perfiles en ventana usuario
			this.ventanaUsuario.getComboBoxPerfiles().addItem(objeto);
		}
		
	}
	
	private void refrescarComboVistaPerfiles() {
		this.vistaABMPerfil.getComboBox().removeAllItems();
		
		for (PerfilDTO objeto : perfilesEnTabla) { 		
			this.vistaABMPerfil.getComboBox().addItem(objeto); 		
		}
	
	}
	//CUARTOS
	private void refrescarTablaCuartos(){
		this.cuartosEnTabla = cuarto.obtenerCuartos();
		this.vistaABMCuarto.llenarTabla(this.cuartosEnTabla);
	}
	
	private void agregarCuarto(ActionEvent l) {
		this.ventanaCuarto.getRdbtnDisponible().setSelected(true);
		this.ventanaCuarto.getRdbtnDisponible().setEnabled(true);
		this.ventanaCuarto.getRdbtnNoDisponible().setSelected(false);
		this.ventanaCuarto.getRdbtnNoDisponible().setEnabled(true);
		this.ventanaCuarto.getBtnAgregarCuarto().setVisible(true);
		this.ventanaCuarto.getBtnEditarCuarto().setVisible(false);
		this.consultarCateCuarto();
		this.ventanaCuarto.limpiar();
		this.ventanaCuarto.mostrarVentana();		
	}
		
	private void consultarCateCuarto() {
		this.ventanaCuarto.rellenarListaCateCuarto(hotel.obtenerCategoriasCuartos());
	}
		
	private void guardarCuarto(ActionEvent c) {
		String capacidad = this.ventanaCuarto.getTxtCapacidad().getText();
		Double monto = Double.valueOf(this.ventanaCuarto.getTxtMonto().getText());
		int montoSenia = Integer.valueOf(this.ventanaCuarto.getTxtMontoSenia().getText());
		String piso = this.ventanaCuarto.getTxtPiso().getText();
		String habitacion = this.ventanaCuarto.getTxtHabitacion().getText().toUpperCase();
		Boolean estado = this.ventanaCuarto.getRdbtnDisponible().isSelected();
		String itemSeleccionado = String.valueOf(this.ventanaCuarto.getCmbBoxCateCuarto().getSelectedItem());
		String[] itemSpliteado = itemSeleccionado.split("-");
		int idCateCuarto = Integer.valueOf(itemSpliteado[0]);
		
		CuartoDTO nuevoCuarto = new CuartoDTO(0, idCateCuarto, capacidad, monto, montoSenia, piso, habitacion, estado);
		this.cuarto.agregarCuarto(nuevoCuarto);
			
		this.ventanaCuarto.cerrar();
		refrescarTablaCuartos();
	}
		
	private void editarCuarto(ActionEvent e) {
		int[] filasSeleccionadas = this.vistaABMCuarto.getTablaCuartos().getSelectedRows();
		int id = 0;
		if(filasSeleccionadas.length == 1) {
			id = obtenerIdCuarto();
			//oculto boton de agregar
			this.ventanaCuarto.getBtnAgregarCuarto().setVisible(false);
			this.ventanaCuarto.getBtnEditarCuarto().setVisible(true);
			this.consultarCateCuarto();
			cargarDatosCuarto(id);
			this.ventanaCuarto.mostrarVentana();
		}
	}

	private void cargarDatosCuarto(int id) {
		for (CuartoDTO cuarto : cuartosEnTabla) {
			if(cuarto.getId() == id) {
								
				//seteo los campos de la ventana				
				this.ventanaCuarto.setTxtCapacidad(cuarto.getCapacidad());
				this.ventanaCuarto.setTxtMonto(String.valueOf(cuarto.getMonto()));
				this.ventanaCuarto.setTxtMontoSenia(String.valueOf(cuarto.getMontoSenia()));
				this.ventanaCuarto.setTxtPiso(cuarto.getPiso());
				this.ventanaCuarto.setTxtHabitacion(cuarto.getHabitacion());
				if(cuarto.getEstado()){
					this.ventanaCuarto.setRdbtnDisponible(true);		
				}else {
					this.ventanaCuarto.setRdbtnNoDisponible(true);	
				}
				int idCateCuarto = cuarto.getIdCateCuarto();
				for (CategoriaCuartoDTO cc : hotel.obtenerCategoriasCuartos()) {
					if (cc.getIdCategoriaCuarto() == idCateCuarto) {
						this.ventanaCuarto.getCmbBoxCateCuarto().setSelectedItem(cc.getIdCategoriaCuarto()+"-"+cc.getNombre());
					}				
				}									
			}
		}
	}
		
	private void guardarCambiosCuarto(ActionEvent l) {
		int id = obtenerIdCuarto();
			
		String capacidad = this.ventanaCuarto.getTxtCapacidad().getText();
		Double monto = Double.valueOf(this.ventanaCuarto.getTxtMonto().getText());
		int montoSenia = Integer.valueOf(this.ventanaCuarto.getTxtMontoSenia().getText());
		String piso = this.ventanaCuarto.getTxtPiso().getText();
		String habitacion = this.ventanaCuarto.getTxtHabitacion().getText().toUpperCase();
		Boolean estado = this.ventanaCuarto.getRdbtnDisponible().isSelected();
		String itemSeleccionado = String.valueOf(this.ventanaCuarto.getCmbBoxCateCuarto().getSelectedItem());
		String[] itemSpliteado = itemSeleccionado.split("-");
		int idCateCuarto = Integer.valueOf(itemSpliteado[0]);
		CuartoDTO cuarto = new CuartoDTO(id, idCateCuarto, capacidad, monto, montoSenia, piso, habitacion, estado);
		this.cuarto.modificarCuarto(cuarto);
			
		this.ventanaCuarto.cerrar();
		refrescarTablaCuartos();
	}
		
	private int obtenerIdCuarto() {
		int filaSelec = this.vistaABMCuarto.getTablaCuartos().getSelectedRow();  
		return Integer.parseInt(String.valueOf(this.vistaABMCuarto.getModelCuartos().getValueAt(filaSelec, 0)));
	}
		
	private void cambiarEstado(ActionEvent o) {
		int filaSeleccionada = this.vistaABMCuarto.getTablaCuartos().getSelectedRow();
		int[] filasSeleccionadas = this.vistaABMCuarto.getTablaCuartos().getSelectedRows();
		int id = 0;
		CuartoDTO cuartoCambiado = null;
		if(filasSeleccionadas.length == 1) {
			id = obtenerIdCuarto();
			cuartosEnTabla.get(filaSeleccionada);
			CuartoDTO cuartoAux = cuartosEnTabla.get(filaSeleccionada);
			if(cuartoAux.getId() == id) {
				if (cuartoAux.getEstado()) {
					cuartoAux.setEstado(false);		
				}else {
					cuartoAux.setEstado(true);
				}
			}
			cuartoCambiado = new CuartoDTO(cuartoAux.getId(), cuartoAux.getIdCateCuarto(), cuartoAux.getCapacidad(),cuartoAux.getMonto(), cuartoAux.getMontoSenia(), cuartoAux.getPiso(), cuartoAux.getHabitacion(),cuartoAux.getEstado());
			this.cuarto.modificarEstado(cuartoCambiado);
			this.refrescarTablaCuartos();
		}				
	}
		
	public void buscarCuarto(ActionEvent l, String buscar) {
		this.cuartosEnTabla = cuarto.buscarCuartos(buscar);
		this.vistaABMCuarto.llenarTabla(this.cuartosEnTabla);
	}
		
	private void mostrarVistaCategoriasCuarto(ActionEvent a) {
		this.vistaABMCategoriaCuarto.show();
	}
	
	private void refrescarListaCateCuarto(ActionEvent l) {
		consultarCateCuarto();
	}
	
	private void limpiarFiltroCuarto(ActionEvent a) {
		this.vistaABMCuarto.getTxtBuscar().setText("");
		this.refrescarTablaCuartos();
	}
	private List<PermisoPerfilDTO> obtenerPermisosPorIdPerfil(int id) { 
		return permisoPerfil.buscarPermisos(id); 	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
