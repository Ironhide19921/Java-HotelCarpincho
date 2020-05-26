package presentacion.controlador;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class VentanaUsuario extends JFrame {

	private static VentanaUsuario INSTANCE;
	private JTextField txtNombreUsuario;
	private JTextField txtApellidoUsuario;
	private JTextField txtTipoDocUsuario;
	private JTextField txtDocUsuario;
	private JTextField txtEmailUsuario;
	private JTextField txtPassUsuario;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnConfirmarUsuario;
	private JLabel lblPerfil;
	private JButton btnABMPerfiles;
	private JButton btnConfirmarCambios;
	private JRadioButton rdbtnUsuarioNoDisponible;
	private JRadioButton rdbtnUsuarioDisponible;

	public static VentanaUsuario getInstance(){
			
			if(INSTANCE == null){
				INSTANCE = new VentanaUsuario();
				return new VentanaUsuario();
			}
			else
				return INSTANCE;
		}
	
	private VentanaUsuario() {
		super();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 523, 547);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 356, 483);
		getContentPane().add(panel);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(30, 41, 119, 16);
		panel.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(30, 79, 119, 16);
		panel.add(lblApellido);
		
		JLabel lblTipoDeDocumento = new JLabel("Tipo de documento");
		lblTipoDeDocumento.setBounds(30, 155, 141, 16);
		panel.add(lblTipoDeDocumento);
		
		JLabel lblNumeroDeDocumento = new JLabel("Numero de documento");
		lblNumeroDeDocumento.setBounds(30, 192, 155, 16);
		panel.add(lblNumeroDeDocumento);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(30, 229, 61, 16);
		panel.add(lblEmail);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(30, 274, 61, 16);
		panel.add(lblEstado);
		
		txtNombreUsuario = new JTextField();
		txtNombreUsuario.setColumns(10);
		txtNombreUsuario.setBounds(195, 41, 130, 26);
		panel.add(txtNombreUsuario);
		
		txtApellidoUsuario = new JTextField();
		txtApellidoUsuario.setColumns(10);
		txtApellidoUsuario.setBounds(195, 79, 130, 26);
		panel.add(txtApellidoUsuario);
		
		txtTipoDocUsuario = new JTextField();
		txtTipoDocUsuario.setColumns(10);
		txtTipoDocUsuario.setBounds(195, 155, 130, 26);
		panel.add(txtTipoDocUsuario);
		
		txtDocUsuario = new JTextField();
		txtDocUsuario.setColumns(10);
		txtDocUsuario.setBounds(195, 192, 130, 26);
		panel.add(txtDocUsuario);
		
		txtEmailUsuario = new JTextField();
		txtEmailUsuario.setColumns(10);
		txtEmailUsuario.setBounds(195, 229, 130, 26);
		panel.add(txtEmailUsuario);
		
		btnConfirmarUsuario = new JButton("Confirmar");
		btnConfirmarUsuario.setBounds(30, 431, 155, 29);
		panel.add(btnConfirmarUsuario);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(30, 118, 61, 16);
		panel.add(lblPassword);
		
		txtPassUsuario = new JTextField();
		txtPassUsuario.setColumns(10);
		txtPassUsuario.setBounds(195, 118, 130, 26);
		panel.add(txtPassUsuario);
		
		btnABMPerfiles = new JButton("Perfiles");
		btnABMPerfiles.setBounds(208, 431, 117, 29);
		panel.add(btnABMPerfiles);
		
		lblPerfil = new JLabel("Perfil");
		lblPerfil.setBounds(30, 351, 61, 16);
		panel.add(lblPerfil);
		
		JComboBox comboBoxPerfiles = new JComboBox();
		comboBoxPerfiles.setBounds(195, 349, 130, 20);
		panel.add(comboBoxPerfiles);
		
		rdbtnUsuarioNoDisponible = new JRadioButton("No Disponible");
		buttonGroup.add(rdbtnUsuarioNoDisponible);
		rdbtnUsuarioNoDisponible.setBounds(195, 315, 141, 23);
		panel.add(rdbtnUsuarioNoDisponible);
		
		rdbtnUsuarioDisponible = new JRadioButton("Disponible");
		buttonGroup.add(rdbtnUsuarioDisponible);
		rdbtnUsuarioDisponible.setBounds(195, 271, 141, 23);
		panel.add(rdbtnUsuarioDisponible);
		
		btnConfirmarCambios = new JButton("Confirmar Cambios");
		btnConfirmarCambios.setBounds(30, 431, 155, 29);
		panel.add(btnConfirmarCambios);
		
		
	}
	
	public void limpiar() {
		this.txtNombreUsuario.setText("");
		this.txtApellidoUsuario.setText("");
		this.txtTipoDocUsuario.setText("");
		this.txtDocUsuario.setText("");
		this.txtEmailUsuario.setText("");

	}
	public void cerrar()
	{
		limpiar();
		this.dispose();
	}

	public JRadioButton getRdbtnUsuarioDisponible() {
		return rdbtnUsuarioDisponible;
	}

	public void setRdbtnUsuarioDisponible(boolean b) {
		this.rdbtnUsuarioDisponible.setSelected(b);
	}

	public JRadioButton getRdbtnUsuarioNoDisponible() {
		return rdbtnUsuarioNoDisponible;
	}

	public void setRdbtnUsuarioNoDisponible(boolean b) {
		this.rdbtnUsuarioNoDisponible.setSelected(b);
	}

	public JButton getBtnABMPerfiles() {
		return btnABMPerfiles;
	}

	public void setBtnABMPerfiles(JButton btnABMPerfiles) {
		this.btnABMPerfiles = btnABMPerfiles;
	}

	public JButton getBtnConfirmarCambios() {
		return btnConfirmarCambios;
	}

	public void setBtnConfirmarCambios(JButton btnConfirmarCambios) {
		this.btnConfirmarCambios = btnConfirmarCambios;
	}

	public static VentanaUsuario getINSTANCE() {
		return INSTANCE;
	}

	public static void setINSTANCE(VentanaUsuario iNSTANCE) {
		INSTANCE = iNSTANCE;
	}

	public JTextField getTxtNombreUsuario() {
		return txtNombreUsuario;
	}

	public void setTxtNombreUsuario(String string) {
		this.txtNombreUsuario.setText(string);
	}

	public JTextField getTxtApellidoUsuario() {
		return txtApellidoUsuario;
	}

	public void setTxtApellidoUsuario(String string) {
		this.txtApellidoUsuario.setText(string);
	}

	public JTextField getTxtTipoDocUsuario() {
		return txtTipoDocUsuario;
	}

	public void setTxtTipoDocUsuario(String string) {
		this.txtTipoDocUsuario.setText(string);
	}

	public JTextField getTxtDocUsuario() {
		return txtDocUsuario;
	}

	public void setTxtDocUsuario(String string) {
		this.txtDocUsuario.setText(string);
	}

	public JTextField getTxtEmailUsuario() {
		return txtEmailUsuario;
	}

	public void setTxtEmailUsuario(String string) {
		this.txtEmailUsuario.setText(string);
	}

	public JTextField getTxtPassUsuario() {
		return txtPassUsuario;
	}

	public void setTxtPassUsuario(String string) {
		this.txtPassUsuario.setText(string);;
	}

	public JButton getBtnConfirmarUsuario() {
		return btnConfirmarUsuario;
	}

	public void setBtnConfirmarUsuario(JButton btnConfirmarUsuario) {
		this.btnConfirmarUsuario = btnConfirmarUsuario;
	}

	public JLabel getLblPerfil() {
		return lblPerfil;
	}

	public void setLblPerfil(JLabel lblPerfil) {
		this.lblPerfil = lblPerfil;
	}

	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}

	public void mostrarVentana() {
		this.setVisible(true);
		
	}
}

	

