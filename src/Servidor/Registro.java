package Servidor;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import BaseDeDatos.IDBUsuarios;
import ClasesBasicas.Usuario;
import Excepciones.UsuarioYaRegistrado;

import util.SpringUtilities;

public class Registro extends JFrame implements ActionListener, KeyListener, WindowListener {

	private JLabel Usu;
	private JLabel Passw;

	private JTextField Us;
	private JPasswordField Pass;

	private IDBUsuarios adm;

	private JButton Ini;

	private JPanel unicoPanel;
	private Administrador administrador;

	public Registro(IDBUsuarios adm, Administrador administrador) {

		this.administrador=administrador;
		unicoPanel = new JPanel();
		unicoPanel.setLayout(new SpringLayout());

		Usu = new JLabel("Introduzca nombre de usuario:  ", JLabel.TRAILING);
		Passw = new JLabel("Introduzca contraseña:  ", JLabel.TRAILING);

		Us = new JTextField(30);
		Pass = new JPasswordField(30);
		this.adm=adm;
		Ini = new JButton("Agregar usuario");

		unicoPanel.add(Usu);
		unicoPanel.add(Us);
		unicoPanel.add(Passw);
		unicoPanel.add(Pass);
		unicoPanel.add(new JLabel());
		unicoPanel.add(Ini);


		SpringUtilities.makeCompactGrid(unicoPanel, 3, 2, // rows, cols
				0, 0, // initX, initY
				0, 0); // xPad, yPad
		this.setLayout(new FlowLayout());
		this.add(unicoPanel);
		this.setVisible(true);
		this.setSize(600, 125);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Iniciar Cuenta");
		this.addWindowListener(this);
		Ini.addActionListener(this);
		Us.addKeyListener(this);
		Pass.addKeyListener(this);

		/**
		 * Desactivo el botón iniciar
		 */
		Ini.setEnabled(false);
	}

	public void vaciarPasswordField() {
		Pass.setText("");
	}

	public void vaciarUser() {
		Us.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ((e.getSource() == Ini)) {
			Usuario u=new Usuario(Us.getText(),Pass.getText());
			boolean error=false;
			boolean guardarUsuario=false;
			try {
				try {
					adm.inicializarBD();
					guardarUsuario=adm.obtenerDatosUsuario(u.getNick(), u.getPass());
					adm.finalizarBD();
				} catch (UsuarioYaRegistrado e1) {
					// TODO Auto-generated catch block
				} 
				if(guardarUsuario==true)
				{
				adm.inicializarBD();
				adm.GuardarUsuario(u);
				adm.finalizarBD();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				error=true;
			} catch (ClassNotFoundException e6) {
				// TODO Auto-generated catch block
				e6.printStackTrace();
			}
			if(error==false&&guardarUsuario==true)
			{
				this.setVisible(false);
				 JOptionPane.showMessageDialog(null, "Usuario agregado","", 0);
				 administrador.setVisible(true);
				 administrador.rellenarTabla();
				 administrador.usuariosRegistrados();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (Pass.getText().isEmpty() || Us.getText().isEmpty())
			Ini.setEnabled(false);
		else
			Ini.setEnabled(true);

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		this.setVisible(false);
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
