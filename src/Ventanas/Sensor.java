package Ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Sensor extends JFrame implements WindowListener {

	private JLabel Coor;
	private JLayeredPane layer;
	private JLabel fondo;


	public Sensor(String Coordenadas) {

		layer = new JLayeredPane();

		Coor = new JLabel(Coordenadas);
		Coor.setForeground(Color.red);
		Coor.setFont(new Font("TimesRoman", Font.PLAIN, 16));

		fondo = new JLabel();
		fondo = new JLabel(new ImageIcon(getClass().getResource(
				"/Imágenes/Blanco.jpg")));

		this.setLayout(null);
		layer.setBounds(0, 0, 400, 300);
		this.add(layer);

		layer.add(fondo, new Integer(0));
		fondo.setBounds(0, 0, 400, 300);
		layer.add(Coor, new Integer(1));
		Coor.setBounds(75, 200, 300, 15);

		this.setSize(420, 340);
		this.setLocationRelativeTo(null);
		this.addWindowListener(this);
	}

	public static void main(String args[]) {

		Sensor g = new Sensor("Coordenadas");
		g.setVisible(true);
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
