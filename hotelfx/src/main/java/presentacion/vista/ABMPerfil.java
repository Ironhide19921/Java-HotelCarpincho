package presentacion.vista;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import dto.PerfilDTO;
import dto.UsuarioDTO;

import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JCheckBox;

public class ABMPerfil{
	
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField txtNombre;
	//private ArrayList<PerfilDTO> listaPerfiles;
	private JComboBox comboBox;
	private JButton btnConfirmar;
	private JButton btnEliminar;
	private JCheckBox chckbxAbmUsuarios;
	private JCheckBox chckbxAbmClientes;
	private JCheckBox chckbxAbmCuartos;
	private JButton btnCrear;
	private static ABMPerfil INSTANCE;
	
	public ABMPerfil() {
		super();
		initialize();
	}
	
	public static ABMPerfil getInstance(){
		
		if(INSTANCE == null){
			INSTANCE = new ABMPerfil(); 	
			return new ABMPerfil();
		}
		else
			return INSTANCE;
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 516, 395);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNombreDePerfil = new JLabel("Nombre de perfil");
		lblNombreDePerfil.setBounds(21, 22, 108, 14);
		frame.getContentPane().add(lblNombreDePerfil);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(153, 19, 183, 20);
		frame.getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		btnCrear = new JButton("Crear");
		btnCrear.setBounds(384, 18, 89, 23);
		frame.getContentPane().add(btnCrear);
		
		JLabel lblConsultaDePerfil = new JLabel("Consulta de perfil");
		lblConsultaDePerfil.setBounds(21, 85, 108, 14);
		frame.getContentPane().add(lblConsultaDePerfil);
		
		comboBox = new JComboBox();
		comboBox.setBounds(153, 82, 183, 20);
		frame.getContentPane().add(comboBox);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(21, 324, 89, 23);
		frame.getContentPane().add(btnConfirmar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(384, 81, 89, 23);
		frame.getContentPane().add(btnEliminar);
		
		chckbxAbmUsuarios = new JCheckBox("ABM Usuarios");
		chckbxAbmUsuarios.setBounds(21, 153, 120, 23);
		frame.getContentPane().add(chckbxAbmUsuarios);
		
		chckbxAbmClientes = new JCheckBox("ABM Clientes");
		chckbxAbmClientes.setBounds(21, 179, 120, 23);
		frame.getContentPane().add(chckbxAbmClientes);
		
		chckbxAbmCuartos = new JCheckBox("ABM Cuartos");
		chckbxAbmCuartos.setBounds(21, 205, 120, 23);
		frame.getContentPane().add(chckbxAbmCuartos);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public JComboBox getComboBox() {
		return comboBox;
	}

	public void setComboBox(JComboBox comboBox) {
		this.comboBox = comboBox;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}

	public void setBtnConfirmar(JButton btnConfirmar) {
		this.btnConfirmar = btnConfirmar;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public JCheckBox getChckbxAbmUsuarios() {
		return chckbxAbmUsuarios;
	}

	public void setChckbxAbmUsuarios(JCheckBox chckbxAbmUsuarios) {
		this.chckbxAbmUsuarios = chckbxAbmUsuarios;
	}

	public JCheckBox getChckbxAbmClientes() {
		return chckbxAbmClientes;
	}

	public void setChckbxAbmClientes(JCheckBox chckbxAbmClientes) {
		this.chckbxAbmClientes = chckbxAbmClientes;
	}

	public JCheckBox getChckbxAbmCuartos() {
		return chckbxAbmCuartos;
	}

	public void setChckbxAbmCuartos(JCheckBox chckbxAbmCuartos) {
		this.chckbxAbmCuartos = chckbxAbmCuartos;
	}

	public JButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnCrear(JButton btnCrear) {
		this.btnCrear = btnCrear;
	}


	public void show()
	{
		this.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.frame.setVisible(true);
	}
	
}
