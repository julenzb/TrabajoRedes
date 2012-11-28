package Servidor;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;

import util.SpringUtilities;

import BaseDeDatos.IDBUsuarios;
import ClasesBasicas.Usuario;
import Ventanas.Gestor_IniciarSesión;

public class ModificarDatos extends JFrame implements ActionListener,
		KeyListener, WindowListener {
	private IDBUsuarios adm;
	private JLabel Usu;
	private JLabel Passw;

	private JTextField Us;
	private JPasswordField Pass;

	private JButton Ini;
	private String usuario;
	private JPanel unicoPanel;
	private Administrador admin;
	

	public ModificarDatos(String usuarioSeleccionado2, IDBUsuarios adm, Administrador administrador) {
		this.adm=adm;
		this.usuario=usuarioSeleccionado2;
		this.admin=administrador;
		unicoPanel = new JPanel();
		unicoPanel.setLayout(new SpringLayout());

		Usu = new JLabel("Introduzca nombre de usuario:  ", JLabel.TRAILING);
		Passw = new JLabel("Introduzca contraseña:  ", JLabel.TRAILING);

		Us = new JTextField(30);
		Us.setEditable(false);
		Pass = new JPasswordField(30);

		Ini = new JButton("Modificar datos");

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

		Ini.addActionListener(this);
		Us.addKeyListener(this);
		Pass.addKeyListener(this);
		this.addWindowListener(this);

		/**
		 * Desactivo el botón iniciar
		 */
		Ini.setEnabled(false);
		
		cargarUsuario(usuario);
	}

	
	public void cargarUsuario(String usuario){
		try {
			adm.inicializarBD();
			Usuario u = adm.CargarUsuario(usuario);
			adm.finalizarBD();
			System.out.println(u.getNick()+u.getPass());
			Us.setText(u.getNick());
			Pass.setText(u.getPass());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			try {
				adm.inicializarBD();
				adm.ActualizarUsuario(Us.getText(),Pass.getText());
				adm.finalizarBD();
				this.setVisible(false);
				 JOptionPane.showMessageDialog(null, "Usuario modificado","", 0);
				 admin.setVisible(true);
				 admin.rellenarTabla();
				 admin.usuariosRegistrados();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
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
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		this.setVisible(false);
		System.exit(0);
	}


	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
