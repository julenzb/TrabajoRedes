package Ventanas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.SocketManager;

public class TablaDesc extends JFrame implements MouseListener, ActionListener, WindowListener {
	private JLabel a;
	private JTable desc;
	private DefaultTableModel modelotabla;
	private JScrollPane scrolltable;
	private JButton salir;

	private String ddd;
	private SocketManager sm;

	private JPanel norte;
	private JPanel sur;
	private JPanel centro;

	private ObtenerDatos od;

	public TablaDesc(SocketManager sm, String ddd, ObtenerDatos od) {
		this.sm = sm;
		this.od = od;
		this.ddd = ddd;
		a = new JLabel("¿De que sensor quieres obtener el valor actual?");
		modelotabla = new DefaultTableModel();

		modelotabla.addColumn("Descripción");
		desc = new JTable(modelotabla);
		scrolltable = new JScrollPane(desc);

		salir = new JButton("Volver atrás");

		norte = new JPanel();
		centro = new JPanel();
		sur = new JPanel();

		norte.add(a);
		centro.add(scrolltable);
		sur.add(salir);

		this.setLayout(new BorderLayout());
		this.add("North", norte);
		this.add("Center", centro);
		this.add("South", sur);
		this.setSize(700, 600);
		this.setLocationRelativeTo(null);

		desc.addMouseListener(this);
		salir.addActionListener(this);
		this.addWindowListener(this);
		obtenerDesc(ddd);

	}

	public void rellenarTabla(String descr) {
		String[] atributos = { descr };
		modelotabla.addRow(atributos);
	}

	public void obtenerDesc(String id) {
		boolean seguir = true;
		String sentenciaAEnviar = "";
		String senRecibido = "";
		sentenciaAEnviar = "LISTSENSOR " + id;
		try {
			sm.Escribir(sentenciaAEnviar + '\n');
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,
					"ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}

		while (seguir) {
			try {
				senRecibido = sm.Leer();
				System.out.println("Sentencia recibida:" + senRecibido);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,
						"ERROR EL ADMIN LE HA EXPULSADO",
						"Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}

			StringTokenizer token = new StringTokenizer(senRecibido);
			if (token.nextToken().equals("222")) {
				seguir = false;
				System.out.println("FIN, lista de sensores recibida");
			} else {
				token = new StringTokenizer(senRecibido);
				token.nextToken();
				String a2 = token.nextToken();
				rellenarTabla(a2);
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int click = desc.rowAtPoint(e.getPoint());
		String descripcion = ((String) modelotabla.getValueAt(click, 0));

		String sentenciaAEnviar = "";
		String senRecibido = "";
		sentenciaAEnviar = "GET_VALACT";
		try {
			sm.Escribir(sentenciaAEnviar + '\n');
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,
					"ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		System.out.println("Sentencia enviada: " + sentenciaAEnviar);
		try {
			senRecibido = sm.Leer();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,
					"ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}

		System.out.println("Sentencia recibida: " + senRecibido);
		StringTokenizer token = new StringTokenizer(senRecibido);
		token.nextToken();
		token.nextToken();
		String fecha = token.nextToken();
		String hora = token.nextToken();
		String valor1 = token.nextToken();
		String valor = fecha + " " + hora + " " + valor1;
		Sensor g = new Sensor(valor);
		g.setVisible(true);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == salir) {
			this.setVisible(false);
			od.setVisible(true);
		}

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
		od.setVisible(true);
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
