package presentacion.controlador;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class VentanaCategoriaCuarto extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtDetalle;
	private JButton btnAgregarCategoriaCuarto;
	private JButton btnEditarCategoriaCuarto;
	private static VentanaCategoriaCuarto INSTANCE;
	
	public static VentanaCategoriaCuarto getInstance(){
		
		if(INSTANCE == null){
			INSTANCE = new VentanaCategoriaCuarto(); 	
			return new VentanaCategoriaCuarto();
		}
		else
			return INSTANCE;
	}

	private VentanaCategoriaCuarto(){
		super();

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		setBounds(100, 100, 523, 547);
		
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(30, 41, 119, 16);
		panel.add(lblNombre);
		
		JLabel lblDetalle = new JLabel("Detalle");
		lblDetalle.setBounds(30, 79, 119, 16);
		panel.add(lblDetalle);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(270, 36, 130, 26);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtDetalle = new JTextField();
		txtDetalle.setBounds(270, 74, 130, 88);
		panel.add(txtDetalle);
		txtDetalle.setColumns(10);
		
		btnAgregarCategoriaCuarto = new JButton("Confirmar");
		btnAgregarCategoriaCuarto.setBounds(68, 173, 117, 29);
		panel.add(btnAgregarCategoriaCuarto);
		
		JButton btnConfirmarEIr = new JButton("Confirmar e ir a cuartos");
		btnConfirmarEIr.setBounds(227, 173, 212, 29);
		panel.add(btnConfirmarEIr);
		
		btnEditarCategoriaCuarto = new JButton("Editar");
		btnEditarCategoriaCuarto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditarCategoriaCuarto.setBounds(68, 173, 117, 29);
		panel.add(btnEditarCategoriaCuarto);
		
		JButton btnEditarEIr = new JButton("Editar e ir a cuartos");
		btnEditarEIr.setBounds(227, 173, 212, 29);
		panel.add(btnEditarEIr);
		
		this.setVisible(false);
		
		
	}
	
	public void mostrarVentana(){
		this.setVisible(true);
	}
	
	public JTextField getTxtNombre(){
		return txtNombre;
	}
	
	public void setTxtNombre(String txtNombre) {
		this.txtNombre.setText(txtNombre);
	}

	public void setTxtDetalle(String txtDetalle) {
		this.txtDetalle.setText(txtDetalle);
	}

	public JTextField getTxtDetalle() {
		return txtDetalle;
	}

	public JButton getBtnAgregarCategoriaCuarto() {
		return btnAgregarCategoriaCuarto;
	}
	
	public JButton getBtnEditarCategoriaCuarto() {
		return btnEditarCategoriaCuarto;
	}

	
	public void limpiar() {
		this.txtNombre.setText("");
		this.txtDetalle.setText("");
	}
	public void cerrar()
	{
		limpiar();
		this.dispose();
	}
}