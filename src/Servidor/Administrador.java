package Servidor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BaseDeDatos.IDBUsuarios;
import ClasesBasicas.Usuario;

public class Administrador extends JFrame implements ActionListener, MouseListener, WindowListener {

	private JButton agregar;
	private JButton borrar;
	private JButton modificar;
	private JButton desconectar;
	private JButton salir;
	private JTextField numMaxCon;

	private int numConexionesActuales;
	private int numConexionesPermitidas;

	private LinkedList<String> UsuariosConectados;
	private IDBUsuarios adm;
	String usuarioSeleccionado;
	String usuarioSeleccionado2;

	private JTable tablaUsuarios;
	private DefaultTableModel modeloTabla;
	private JScrollPane scrolltable;

	private JTable tablaUsuarios2;
	private DefaultTableModel modeloTabla2;
	private JScrollPane scrolltable2;
	private JButton establecer;

	Administrador(IDBUsuarios bdu) throws ClassNotFoundException, SQLException {
		this.adm = bdu;
		adm.inicializarBD();
		this.setSize(550, 500);
		this.setResizable(false);
		this.numConexionesPermitidas = 100;
		this.numConexionesActuales = 0;

		numMaxCon = new JTextField(1);
		modeloTabla2 = new DefaultTableModel();
		modeloTabla2.addColumn("Usuarios registrados");
		tablaUsuarios2 = new JTable(modeloTabla2);
		scrolltable2 = new JScrollPane(tablaUsuarios2);
		JPanel tabla2 = new JPanel();
		tabla2.add(scrolltable2);

		UsuariosConectados = new LinkedList<String>();
		modeloTabla = new DefaultTableModel();
		modeloTabla.addColumn("Usuarios conectados");
		tablaUsuarios = new JTable(modeloTabla);
		scrolltable = new JScrollPane(tablaUsuarios);
		JPanel tabla = new JPanel();
		tabla.add(scrolltable);

		establecer = new JButton("Establecer");
		salir = new JButton("Cerrar servidor");
		agregar = new JButton("      Agregar   usuario       ");
		borrar = new JButton("      Borrar  usuario           ");
		modificar = new JButton("      Modificar  usuario      ");
		desconectar = new JButton("Desconectar usuario");
		borrar.setEnabled(false);
		desconectar.setEnabled(false);
		modificar.setEnabled(false);

		this.setLayout(new BorderLayout());

		JPanel botones = new JPanel();
		botones.setBorder(BorderFactory.createLineBorder((Color.GRAY)));
		botones.add(agregar);
		botones.add(borrar);
		botones.add(modificar);

		JPanel tablaUsuariosRegistrados = new JPanel();
		tablaUsuariosRegistrados.setLayout(new BoxLayout(
				tablaUsuariosRegistrados, BoxLayout.Y_AXIS));
		tablaUsuariosRegistrados.setBorder(BorderFactory
				.createRaisedBevelBorder());
		tablaUsuariosRegistrados.add(tabla2);
		tablaUsuariosRegistrados.add(botones);

		JPanel tablaUsuariosConectados = new JPanel();
		tablaUsuariosConectados.setLayout(new BoxLayout(
				tablaUsuariosConectados, BoxLayout.Y_AXIS));
		tablaUsuariosConectados.setBorder(BorderFactory
				.createRaisedBevelBorder());
		tablaUsuariosConectados.add(tabla);
		JPanel numMax = new JPanel();
		numMax.add(desconectar);
		numMax.add(new JLabel("Número máximo de conexiones:"));
		numMax.add(numMaxCon);
		numMax.add(establecer);
		tablaUsuariosConectados.add(numMax);

		JPanel centro = new JPanel();
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		centro.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		centro.add(tablaUsuariosConectados);
		centro.add(tablaUsuariosRegistrados);

		JPanel salir = new JPanel();
		salir.add(this.salir);

		this.add(centro, BorderLayout.WEST);
		this.add(salir, BorderLayout.SOUTH);

		usuariosRegistrados();

		this.salir.addActionListener(this);
		borrar.addActionListener(this);
		agregar.addActionListener(this);
		desconectar.addActionListener(this);
		modificar.addActionListener(this);
		tablaUsuarios.addMouseListener(this);
		tablaUsuarios2.addMouseListener(this);
		establecer.addActionListener(this);
		this.addWindowListener(this);
	}

	public void usuariosRegistrados() {
		// TODO Auto-generated method stub
		try {
			adm.inicializarBD();
			LinkedList<Usuario> lista = adm.CargarUsuarios();
			adm.finalizarBD();
			int cont = 0;
			modeloTabla2.setRowCount(0);
			while (cont < lista.size()) {
				Usuario u = lista.get(cont);
				String nombre = u.getNick();
				String[] array = { nombre };
				modeloTabla2.addRow(array);
				cont++;
			}
			if (lista.size() == 0) {
				borrar.setEnabled(false);
				modificar.setEnabled(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void rellenarTabla() {
		// TODO Auto-generated method stub
		modeloTabla.setNumRows(0);
		for (int i = 0; i < UsuariosConectados.size(); i++) {
			String[] array = { UsuariosConectados.get(i) };
			modeloTabla.addRow(array);
		}
		if (UsuariosConectados.size() == 0) {
			desconectar.setEnabled(false);
		}
	}

	public void cambiarNombreHiloActual(String nombre) {
		Thread t = Thread.currentThread();
		if (numConexionesActuales < numConexionesPermitidas) {
			t.setName("Usuario " + nombre);
		} else {
			((PeticionCliente) t).cerrarSocket();
		}
	}

	public void cargarHilosActivos() {
		// Find the root thread group
		numConexionesActuales = 0;
		borrarTodosElementos();
		System.out.println("mostrando hilos activos...");
		ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
		while (root.getParent() != null) {
			root = root.getParent();
		}
		// Visit each thread group
		visit(root, 0);
	}

	private void visit(ThreadGroup group, int level) {
		// Get threads in the group
		String nombreUsuario;
		int numThreads = group.activeCount();
		Thread[] threads = new Thread[numThreads * 2];
		numThreads = group.enumerate(threads, false);

		// Enumerate each thread in the group
		for (int i = 0; i < numThreads; i++) {
			// Get thread
			Thread thread = threads[i];
			String nombreHilo = thread.getName();
			StringTokenizer st = new StringTokenizer(nombreHilo);
			if (st.nextToken().equals("Usuario")) {
				numConexionesActuales++;
				nombreUsuario = st.nextToken();
				System.out.println("EL USUARIO " + nombreUsuario
						+ " ESTA CONECTADO");
				UsuariosConectados.add(nombreUsuario);
			}
		}

		// Get thread subgroups of group
		int numGroups = group.activeGroupCount();
		ThreadGroup[] groups = new ThreadGroup[numGroups * 2];
		numGroups = group.enumerate(groups, false);

		// Recursively visit each subgroup
		for (int i = 0; i < numGroups; i++) {
			visit(groups[i], 1);
		}
	}

	public void quitarHilo(String nombre) {
		// TODO Auto-generated method stub
		UsuariosConectados.remove(nombre);
	}

	private void borrarTodosElementos() {
		// TODO Auto-generated method stub
		UsuariosConectados.clear();
	}

	public void matarHilo(String nombre) {
		Thread[] array = new Thread[Thread.activeCount()];
		Thread.enumerate(array);
		String nombreUsuario;
		for (int i = 0; i < array.length; i++) {
			StringTokenizer st = new StringTokenizer(array[i].getName());
			if (st.nextToken().equals("Usuario")) {
				nombreUsuario = st.nextToken();
				if (nombre.equals(nombreUsuario)) {
					((PeticionCliente) array[i]).setSeguir(false);
					((PeticionCliente) array[i]).cerrarSocket();
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == desconectar) {
			System.out.println("matando hilo " + usuarioSeleccionado);
			matarHilo(usuarioSeleccionado);
			quitarHilo(usuarioSeleccionado);
			rellenarTabla();
		} else if (e.getSource() == modificar) {
			ModificarDatos r = new ModificarDatos(usuarioSeleccionado2, adm,
					this);
			this.setVisible(false);
			this.borrar.setEnabled(false);
			this.modificar.setEnabled(false);
			r.setVisible(true);
		} else if (e.getSource() == borrar) {
			try {
				adm.inicializarBD();
				adm.BorrarUsuario(usuarioSeleccionado2);
				adm.finalizarBD();
				this.borrar.setEnabled(false);
				this.modificar.setEnabled(false);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			usuariosRegistrados();
		} else if (e.getSource() == agregar) {
			Registro r = new Registro(adm, this);
			this.setVisible(false);
			this.borrar.setEnabled(false);
			this.modificar.setEnabled(false);
			r.setVisible(true);
		} else if (e.getSource() == this.salir) {
			int cont = 0;
			while (cont < UsuariosConectados.size()) {
				String usuarioConectado=UsuariosConectados.get(cont);
				matarHilo(usuarioConectado);
				quitarHilo(usuarioConectado);
				cont++;
			}
			this.setVisible(false);
			System.exit(0);
		} else if (e.getSource() == establecer) {
			int varNumMaxCon = 0;
			int numUsuariosConectados = UsuariosConectados.size();
			try {
				varNumMaxCon = Integer.parseInt(numMaxCon.getText());
			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(null,
						"Tiene que insertar un número", "ERROR",
						JOptionPane.INFORMATION_MESSAGE);
				numMaxCon.setText("");
			}
			numConexionesPermitidas = varNumMaxCon;
			System.out.println("numUsuariosConectados: "
					+ numUsuariosConectados + " numMaxCon: " + varNumMaxCon);
			while (numUsuariosConectados > varNumMaxCon) {
				String usuario = UsuariosConectados
						.get(numUsuariosConectados - 1);
				System.out.println("matando hilo " + usuario);
				matarHilo(usuario);
				quitarHilo(usuario);
				rellenarTabla();
				numUsuariosConectados--;
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == tablaUsuarios) {
			int click = tablaUsuarios.rowAtPoint(e.getPoint()); // fila que
																// selecciono
																// con el ratón
			usuarioSeleccionado = ((String) modeloTabla.getValueAt(click, 0));// quiero
																				// coger
																				// el
																				// nombre
			System.out.println("Usuario seleccionado: " + usuarioSeleccionado);
			desconectar.setEnabled(true);
		} else if (e.getSource() == tablaUsuarios2) {
			int click = tablaUsuarios2.rowAtPoint(e.getPoint()); // fila que
																	// selecciono
																	// con el
																	// ratón
			usuarioSeleccionado2 = ((String) modeloTabla2.getValueAt(click, 0));// quiero
																				// coger
																				// el
																				// nombre
			System.out.println("Usuario seleccionado: " + usuarioSeleccionado2);
			borrar.setEnabled(true);
			modificar.setEnabled(true);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
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
		int cont = 0;
		while (cont < UsuariosConectados.size()) {
			String usuarioConectado=UsuariosConectados.get(cont);
			matarHilo(usuarioConectado);
			quitarHilo(usuarioConectado);
			cont++;
		}
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
