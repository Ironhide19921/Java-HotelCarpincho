package presentacion.vista;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.CuartoDTO;

import javax.swing.JButton;

import persistencia.conexion.Conexion;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ABMCuarto {

	private JFrame frame;
	private JTable tablaCuartos;
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnBorrar;
	private JButton btnBuscar;
	private JButton btnReporte;
	private DefaultTableModel modelCuartos;
	private  String[] nombreColumnas = {"Id", "Piso", "Habitacion", "Capacidad", "Monto", "Monto Senia", "Estado"};
	private JTextField txtBuscar;
	private JButton btnLimpiarFiltro;
	private JButton btnCambiarEstado;

	public ABMCuarto() {
		super();
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 560, 322);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 534, 273);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane spPersonas = new JScrollPane();
		spPersonas.setBounds(10, 63, 478, 154);
		panel.add(spPersonas);
		
		modelCuartos = new DefaultTableModel(null,nombreColumnas);
		tablaCuartos = new JTable(modelCuartos);
		
		tablaCuartos.getColumnModel().getColumn(0).setPreferredWidth(103);
		tablaCuartos.getColumnModel().getColumn(0).setResizable(false);
		tablaCuartos.getColumnModel().getColumn(1).setPreferredWidth(100);
		tablaCuartos.getColumnModel().getColumn(1).setResizable(false);
		tablaCuartos.getColumnModel().getColumn(2).setPreferredWidth(100);
		tablaCuartos.getColumnModel().getColumn(2).setResizable(false);
		tablaCuartos.getColumnModel().getColumn(3).setPreferredWidth(100);
		tablaCuartos.getColumnModel().getColumn(3).setResizable(false);
		tablaCuartos.getColumnModel().getColumn(4).setPreferredWidth(100);
		tablaCuartos.getColumnModel().getColumn(4).setResizable(false);
		tablaCuartos.getColumnModel().getColumn(5).setPreferredWidth(100);
		tablaCuartos.getColumnModel().getColumn(5).setResizable(false);
		tablaCuartos.getColumnModel().getColumn(6).setPreferredWidth(100);
		tablaCuartos.getColumnModel().getColumn(6).setResizable(false);
		
		spPersonas.setViewportView(tablaCuartos);
		
		btnAgregar = new JButton("Agregar Cuarto");
		btnAgregar.setBounds(10, 228, 125, 23);
		panel.add(btnAgregar);
		
		btnEditar = new JButton("Editar Cuarto");
		btnEditar.setBounds(145, 228, 112, 23);
		panel.add(btnEditar);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(10, 31, 181, 20);
		panel.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBuscar.setBounds(214, 29, 89, 23);
		panel.add(btnBuscar);
		
		btnLimpiarFiltro = new JButton("Refresh");
		btnLimpiarFiltro.setBounds(337, 30, 100, 23);
		panel.add(btnLimpiarFiltro);
		
		btnCambiarEstado = new JButton("Cambiar Estado");
		btnCambiarEstado.setBounds(267, 228, 125, 23);
		panel.add(btnCambiarEstado);
	}
	
	public void show(){
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frame.addWindowListener(new WindowAdapter() 
		{
			@Override
		    public void windowClosing(WindowEvent e) {
		        int confirm = JOptionPane.showOptionDialog(
		             null, "¿Estás seguro que quieres salir de la Agenda 2.0?", 
		             "Confirmación", JOptionPane.YES_NO_OPTION,
		             JOptionPane.QUESTION_MESSAGE, null, null, null);
		        if (confirm == 0) {
		        	Conexion.getConexion().cerrarConexion();
		           System.exit(0);
		        }
		    }
		});
		this.frame.setVisible(true);
	}
	
	public JButton getBtnAgregar(){
		return btnAgregar;
	}
	
	public JButton getBtnEditar() {
		return btnEditar;
	}

	public JTextField getTxtBuscar() {
		return txtBuscar;
	}


	public void setTxtBuscar(JTextField txtBuscar) {
		this.txtBuscar = txtBuscar;
	}

	public JButton getBtnBuscar(){
		return btnBuscar;
	}

	public JButton getBtnBorrar() {
		return btnBorrar;
	}
	
	public JButton getBtnReporte() {
		return btnReporte;
	}
	
	public DefaultTableModel getModelCuartos() {
		return modelCuartos;
	}
	
	public JTable getTablaCuartos(){
		return tablaCuartos;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}
	
	public JButton getBtnLimpiarFiltro() {
		return btnLimpiarFiltro;
	}
	
	public JButton getBtnCambiarEstado() {
		return btnCambiarEstado;
	}


	public void llenarTabla(List<CuartoDTO> cuartosEnTabla) {
		this.getModelCuartos().setRowCount(0); //Para vaciar la tabla
		this.getModelCuartos().setColumnCount(0);
		this.getModelCuartos().setColumnIdentifiers(this.getNombreColumnas());
		
		for (CuartoDTO p : cuartosEnTabla){
			int id = p.getId();
			String capacidad = p.getCapacidad();
			Double monto = p.getMonto();
			int montoSenia = p.getMontoSenia();
			String piso = p.getPiso();
			String habitacion = p.getHabitacion();
			Boolean estado = p.getEstado();
			String desc_estado = "";
			if(estado) {
				desc_estado = "Disponible";
			}else {
				desc_estado = "No disponible";
			}
			Object[] fila = {id, piso, habitacion, capacidad, monto, montoSenia, desc_estado};
			this.getModelCuartos().addRow(fila);
		}
		
	}
	
	public void cerrar(){
		frame.dispose();
	}
}


