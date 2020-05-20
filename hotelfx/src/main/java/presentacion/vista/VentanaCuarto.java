package presentacion.vista;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dto.CategoriaCuartoDTO;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;

public class VentanaCuarto extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JTextField txtCapacidad;
	private JTextField txtMonto;
	private JTextField txtMontoSenia;
	private JTextField txtPiso;
	private JTextField txtHabitacion;
	private JRadioButton radioEstado;
	private JButton btnConfirmarCuarto;
	private JButton btnEditarCuarto;
	private JButton btnIrACateCuarto;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnDisponible;
	private JRadioButton rdbtnNoDisponible;
	private static VentanaCuarto INSTANCE;
	private JComboBox<String> cmbBoxCateCuarto;
	private JButton btnRefrescarCate;
		
	public static VentanaCuarto getInstance(){
		
		if(INSTANCE == null){
			INSTANCE = new VentanaCuarto(); 	
			return new VentanaCuarto();
		}else {
			return INSTANCE;
		}
	}

	private VentanaCuarto(){
		super();

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 523, 547);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblCapacidad = new JLabel("Capacidad");
		lblCapacidad.setBounds(30, 41, 119, 16);
		panel.add(lblCapacidad);
		
		JLabel lblMonto = new JLabel("Monto");
		lblMonto.setBounds(30, 79, 119, 16);
		panel.add(lblMonto);
		
		JLabel lblMontoSenia = new JLabel("MontoSenia");
		lblMontoSenia.setBounds(30, 123, 141, 16);
		panel.add(lblMontoSenia);
		
		JLabel lblHabitacion = new JLabel("Habitacion");
		lblHabitacion.setBounds(30, 206, 155, 16);
		panel.add(lblHabitacion);
		
		JLabel lblPiso = new JLabel("Piso");
		lblPiso.setBounds(30, 171, 61, 16);
		panel.add(lblPiso);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(30, 249, 61, 16);
		panel.add(lblEstado);
		
		txtCapacidad = new JTextField();
		txtCapacidad.setBounds(270, 36, 130, 26);
		panel.add(txtCapacidad);
		txtCapacidad.setColumns(10);
		
		txtMonto = new JTextField();
		txtMonto.setBounds(270, 74, 130, 26);
		panel.add(txtMonto);
		txtMonto.setColumns(10);
		
		txtMontoSenia = new JTextField();
        txtMontoSenia.setBounds(270, 118, 130, 26);
        panel.add(txtMontoSenia);
        txtMontoSenia.setColumns(10);
		
        txtPiso = new JTextField();
		txtPiso.setColumns(10);
		txtPiso.setBounds(270, 166, 130, 26);
		panel.add(txtPiso);
        
		txtHabitacion = new JTextField();
		txtHabitacion.setColumns(10);
		txtHabitacion.setBounds(270, 201, 130, 26);
		panel.add(txtHabitacion);
				
		rdbtnDisponible = new JRadioButton("Disponible");
		buttonGroup.add(rdbtnDisponible);
		rdbtnDisponible.setBounds(270, 245, 141, 23);
		panel.add(rdbtnDisponible);
		
		rdbtnNoDisponible = new JRadioButton("No Disponible");
		buttonGroup.add(rdbtnNoDisponible);
		rdbtnNoDisponible.setBounds(270, 278, 141, 23);
		panel.add(rdbtnNoDisponible);
		
		btnConfirmarCuarto = new JButton("Confirmar");
		btnConfirmarCuarto.setBounds(68, 437, 117, 29);
		panel.add(btnConfirmarCuarto);
		
		btnEditarCuarto = new JButton("Editar");
		btnEditarCuarto.setBounds(69, 437, 117, 29);
		panel.add(btnEditarCuarto);
		
		btnIrACateCuarto = new JButton("Ir a Categorias de cuarto");
		btnIrACateCuarto.setBounds(227, 437, 212, 29);
		panel.add(btnIrACateCuarto);
		
		JLabel lblCateCuarto = new JLabel("Categorias de cuarto");
		lblCateCuarto.setBounds(30, 333, 155, 16);
		panel.add(lblCateCuarto);
		
		cmbBoxCateCuarto = new JComboBox<String>();
		cmbBoxCateCuarto.setBounds(270, 329, 130, 27);
		cmbBoxCateCuarto.setEditable(true);
		panel.add(cmbBoxCateCuarto);
		
		btnRefrescarCate = new JButton("Refrescar Categorias");
		btnRefrescarCate.setBounds(249, 367, 172, 23);
		panel.add(btnRefrescarCate);
				
		this.setVisible(false);
	}
	
	public void mostrarVentana(){
		this.setVisible(true);
	}
	
	public JTextField getTxtCapacidad(){
		return txtCapacidad;
	}
	
	public JTextField getTxtMonto() {
		return txtMonto;
	}
	
	public JTextField getTxtMontoSenia() {
		return txtMontoSenia;
	}

	public JTextField getTxtPiso() {
		return txtPiso;
	}
	
	public JTextField getTxtHabitacion() {
		return txtHabitacion;
	}
	
	public JRadioButton getRadioEstado() {
		return radioEstado;
	}

	public JButton getBtnAgregarCuarto() {
		return btnConfirmarCuarto;
	}
	
	public JButton getBtnEditarCuarto() {
		return btnEditarCuarto;
	}

	public JButton getBtnCateCuarto() {
		return btnConfirmarCuarto;
	}
	
	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}

	public JRadioButton getRdbtnDisponible() {
		return rdbtnDisponible;
	}

	public JRadioButton getRdbtnNoDisponible() {
		return rdbtnNoDisponible;
	}
	
	public JButton getBtnIrACateCuarto() {
		return btnIrACateCuarto;
	}

	public JComboBox<String> getCmbBoxCateCuarto() {
		return cmbBoxCateCuarto;
	}
	
	public JButton getBtnRefrescarCate() {
		return btnRefrescarCate;
	}

	public void setTxtCapacidad(String txtCapacidad) {
		this.txtCapacidad.setText(txtCapacidad);
	}

	public void setTxtMonto(String txtMonto) {
		this.txtMonto.setText(txtMonto);
	}

	public void setTxtMontoSenia(String txtMontoSenia) {
		this.txtMontoSenia.setText(txtMontoSenia);
	}

	public void setTxtPiso(String txtPiso) {
		this.txtPiso.setText(txtPiso);
	}
	
	public void setTxtHabitacion(String txtHabitacion) {
		this.txtHabitacion.setText(txtHabitacion);
	}

	public void setRadioEstado(JRadioButton radioEstado) {
		this.radioEstado = radioEstado;
	}

	public void setBtnAgregarCliente(JButton btnConfirmarCuarto) {
		this.btnConfirmarCuarto = btnConfirmarCuarto;
	}

	public void setRdbtnDisponible(Boolean  estado) {
		this.rdbtnDisponible.setSelected(estado);
	}

	public void setRdbtnNoDisponible(Boolean estado) {
		this.rdbtnNoDisponible.setSelected(estado);
	}

	public void setCmbBoxCateCuarto(JComboBox<String> cmbBoxCateCuarto) {
		this.cmbBoxCateCuarto = cmbBoxCateCuarto;
	}

	public void limpiar() {
		this.txtCapacidad.setText("");
		this.txtMonto.setText("");
		this.txtMontoSenia.setText("");
		this.txtPiso.setText("");
		this.txtHabitacion.setText("");
		
	}
	
	public void cerrar(){
		limpiar();
		this.dispose();
	}
	
	public void rellenarListaCateCuarto(List<CategoriaCuartoDTO> cateCuartos) {
		this.cmbBoxCateCuarto.removeAllItems();
		for(CategoriaCuartoDTO categoria : cateCuartos) {
			this.cmbBoxCateCuarto.addItem(categoria.getIdCategoriaCuarto() + "-" +categoria.getNombre());
		}
	}	
}
