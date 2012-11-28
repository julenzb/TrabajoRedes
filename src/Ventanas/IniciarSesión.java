package Ventanas;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import util.SpringUtilities;

public class IniciarSesión extends JFrame implements ActionListener,
		KeyListener, WindowListener {

	private JLabel Usu;
	private JLabel Passw;

	private JTextField Us;
	private JPasswordField Pass;

	private JLabel IP;
	private JTextField IPS;

	private JButton Ini;

	private JPanel unicoPanel;

	public IniciarSesión() {

		unicoPanel = new JPanel();
		unicoPanel.setLayout(new SpringLayout());

		Usu = new JLabel("Introduzca nombre de usuario:  ", JLabel.TRAILING);
		Passw = new JLabel("Introduzca contraseña:  ", JLabel.TRAILING);
		IP = new JLabel("Introduzca IP del Servidor: ", JLabel.TRAILING);

		Us = new JTextField(30);
		Pass = new JPasswordField(30);
		IPS = new JTextField(30);

		Ini = new JButton(" Iniciar sesión");

		unicoPanel.add(Usu);
		unicoPanel.add(Us);
		unicoPanel.add(Passw);
		unicoPanel.add(Pass);
		unicoPanel.add(IP);
		unicoPanel.add(IPS);
		unicoPanel.add(new JLabel());
		unicoPanel.add(Ini);

		SpringUtilities.makeCompactGrid(unicoPanel, 4, 2, // rows, cols
				0, 0, // initX, initY
				0, 0); // xPad, yPad
		this.setLayout(new FlowLayout());
		this.add(unicoPanel);
		this.setVisible(true);
		this.setSize(600, 175);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Iniciar Cuenta");
		this.addWindowListener(this);
		
		Ini.addActionListener(this);
		Us.addKeyListener(this);
		Pass.addKeyListener(this);
		IPS.addKeyListener(this);

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

	public static void main(String args[]) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			/** cambio de aspecto a la ventana */
			;
		} catch (Exception e) {

		}
		IniciarSesión v1 = new IniciarSesión();
		v1.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ((e.getSource() == Ini)) {
			System.out.println("EN VENTANA");
			System.out.println("Nombre:" + Us.getText() + " Pass:"
					+ Pass.getText() + " IP: " + IPS.getText());
			Gestor_IniciarSesión gis = new Gestor_IniciarSesión(this,
					Us.getText(), Pass.getText(), IPS.getText());
			gis.EstablecerComunicacion();
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
	public void windowClosing(WindowEvent e) {
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
