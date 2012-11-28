package Servidor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import Ventanas.ObtenerDatos;

import util.SocketManager;

public class Gestor_ConexionServidorActualizar {
	SocketManager sm;
	private static String NOMBREFICHERO = "archivoServidorPrincipal/fichero.txt";
	private static int TAMAÑOFICHERO = 26112;
	private String nombre;
	private String pass;

	public Gestor_ConexionServidorActualizar(String nombre, String pass) {
		// TODO Auto-generated constructor stub
		this.nombre=nombre;
		this.pass=pass;
		validarUserPass();
	}
	
	public void validarUserPass(){
		String sentenciaAEnviar = "USER " + this.nombre;
		String sentenciaRecibida="";
		System.out.println("Sentencia enviada:" + sentenciaAEnviar);
		try {
			sm = new SocketManager("127.0.0.1", 3002);
			sm.Escribir(sentenciaAEnviar + '\n');
			sentenciaRecibida = sm.Leer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sentenciaRecibida);
		StringTokenizer token = new StringTokenizer(sentenciaRecibida);
		String codigoError = token.nextToken();
		if (codigoError.equals("210")) {
			sentenciaAEnviar = "PASS " + this.pass;
			System.out.println("Sentencia enviada:" + sentenciaAEnviar);
			try {
				sm.Escribir(sentenciaAEnviar + '\n');
				sentenciaRecibida = sm.Leer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(sentenciaRecibida);
			token = new StringTokenizer(sentenciaRecibida);
			codigoError = token.nextToken();
			if (codigoError.equals("211"))
				try {
					EstablecerComunicacion();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public void EstablecerComunicacion() throws IOException {
		String sentenciaAEnviar = "GET_FICHERO";
		System.out.println("sentencia a enviar: " + sentenciaAEnviar);
		sm.Escribir(sentenciaAEnviar + '\n');
		String s = sm.Leer();
		System.out.println("sentencia recibida: " + s);
		StringTokenizer st = new StringTokenizer(s);
		if (st.nextToken().equals("OK")) {
			FileOutputStream fos = new FileOutputStream(NOMBREFICHERO);
			try {
				recibirBytes(fos);
				fos.close();
				sm.CerrarSocket();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void recibirBytes(FileOutputStream fos) throws Exception {
		byte[] buffer = new byte[TAMAÑOFICHERO];
		int bytes=TAMAÑOFICHERO;
			bytes=sm.Leer(buffer);
			fos.write(buffer, 0, bytes);
	}
}
