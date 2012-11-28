package Ventanas;

import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import util.SocketManager;

public class Gestor_IniciarSesión {
	private String nombre;
	private String pass;
	private String ip;
	private SocketManager sm;
	private IniciarSesión is;

	public Gestor_IniciarSesión(IniciarSesión iniciarSesión, String nombre,
			String pass, String ip) {
		super();
		this.is = iniciarSesión;
		this.nombre = nombre;
		this.pass = pass;
		if (ip.equals(""))
			this.ip = "127.0.0.1";
		else
			this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void EstablecerComunicacion() {
		String sentenciaAEnviar = "";
		String sentenciaRecibida = "";
		try {
			System.out.println("EN GESTOR: ");
			System.out.println("Nombre:" + this.getNombre() + " Pass:"
					+ this.getPass() + " IP:" + this.getIp());
			sm = new SocketManager(this.getIp(), 3001);

			sentenciaAEnviar = "USER " + this.getNombre();
			System.out.println("Sentencia enviada:" + sentenciaAEnviar);
			sm.Escribir(sentenciaAEnviar + '\n');
			sentenciaRecibida = sm.Leer();
			System.out.println(sentenciaRecibida);
			StringTokenizer token = new StringTokenizer(sentenciaRecibida);
			String codigoError = token.nextToken();
			if (codigoError.equals("210")) {
				sentenciaAEnviar = "PASS " + this.getPass();
				System.out.println("Sentencia enviada:" + sentenciaAEnviar);
				sm.Escribir(sentenciaAEnviar + '\n');
				sentenciaRecibida = sm.Leer();
				System.out.println(sentenciaRecibida);
				token = new StringTokenizer(sentenciaRecibida);
				codigoError = token.nextToken();
				if (codigoError.equals("211")) {
					ObtenerDatos ob = new ObtenerDatos(sm);
					this.is.setVisible(false);
					ob.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(null,
							"Contraseña incorrecta", "ERROR", 0);
					is.vaciarPasswordField();
				}
			}

			else if (codigoError.equals("411")) {
				JOptionPane.showMessageDialog(null, "Usuario incorrecto",
						"ERROR", 0);
				is.vaciarUser();
				is.vaciarPasswordField();

			}

		} catch (Exception e) {
			System.err.println("main: " + e);
			e.printStackTrace();
		}
	}
}
