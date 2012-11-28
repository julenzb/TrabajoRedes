package Ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class GPS extends JFrame implements WindowListener {

	private JLabel Coor;
	private JLayeredPane layer;
	private JLabel fondo;

	public GPS(String Coordenadas) {

		layer = new JLayeredPane();

		Coor = new JLabel(Coordenadas);
		Coor.setForeground(Color.white);
		Coor.setFont(new Font("TimesRoman", Font.PLAIN, 16));

		fondo = new JLabel();

		// imagen = new ImageIcon(getClass().getResource("/Imágenes/GPS.jpg"));
		// fondo.setIcon(imagen);
		fondo = new JLabel(new ImageIcon(getClass().getResource(
				"/Imágenes/GPS.jpg")));

		this.setLayout(null);
		layer.setBounds(0, 0, 400, 300);
		this.add(layer);
		this.addWindowListener(this);
		layer.add(fondo, new Integer(0));
		fondo.setBounds(0, 0, 400, 300);
		layer.add(Coor, new Integer(1));
		Coor.setBounds(160, 200, 300, 15);

		this.setSize(420, 340);

	}

	public static void main(String args[]) {

		GPS g = new GPS("Coordenadas");
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
