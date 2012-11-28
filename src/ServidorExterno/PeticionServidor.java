package ServidorExterno;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;

import util.SocketManager;
import BaseDeDatos.IDBUsuarios;

public class PeticionServidor implements Runnable {

	private SocketManager sm;
	private boolean seguir;
	private static String NOMBREFICHERO = "archivoServidorActualizaciones/fichero.txt";
	private static int TAMAÑOFICHERO = 26112;
	private IDBUsuarios gbu;

	// Constructor
	PeticionServidor(SocketManager sockm, IDBUsuarios gbu) {
		this.sm = sockm;
		this.gbu = gbu;
		seguir = true;
	}

	public boolean isSeguir() {
		return seguir;
	}

	public void setSeguir(boolean seguir) {
		this.seguir = seguir;
	}

	@Override
	public void run() {
		try {
			ProcesarPeticion();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void ProcesarPeticion() throws IOException {
		// TODO Auto-generated method stub

		// Validación de cliente (user y pass)
			String lineaProcesada = sm.Leer();
			System.out.println("EL SERVIDOR HA RECIBIDO: " + lineaProcesada);
			StringTokenizer tokens = new StringTokenizer(lineaProcesada);
			if (tokens.nextToken().equals("USER")) {
				try {
					gbu.inicializarBD();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String nombre = tokens.nextToken();
				try {
					if (gbu.validarUser(nombre)) {
						gbu.finalizarBD();
						System.out.println("Nombre usuario correcto");
						sm.Escribir("210 OK Bienvenido " + nombre + '\n');
						lineaProcesada = sm.Leer();
						System.out.println("EL SERVIDOR HA RECIBIDO: "
								+ lineaProcesada);
						tokens = new StringTokenizer(lineaProcesada);
						if (tokens.nextToken().equals("PASS")) {
							try {
								gbu.inicializarBD();
								String pass = tokens.nextToken();
								if (gbu.validarPass(nombre, pass)) {
									gbu.finalizarBD();
									System.out.println("Pass usuario correcta");
									sm.Escribir("211 OK Bienvenido al sistema" + '\n');
									lineaProcesada = sm.Leer();
									System.out
											.println("EL SERVIDOR HA RECIBIDO: "
													+ lineaProcesada);
									if (lineaProcesada.equals("GET_FICHERO")) {
										FileInputStream fis = new FileInputStream(
												NOMBREFICHERO);
										try {
											enviarBytes(fis);
											fis.close();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
								gbu.finalizarBD();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					gbu.finalizarBD();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	

	private void enviarBytes(FileInputStream fis) throws Exception {
		byte[] buffer = new byte[TAMAÑOFICHERO];
		int bytes = 0;
		System.out.println("OK " + TAMAÑOFICHERO + " bytes transmitiendo...");
		sm.Escribir("OK " + TAMAÑOFICHERO + " bytes transmitiendo..." + '\n');
		while ((bytes = fis.read(buffer)) != -1) {
			System.out.println(bytes + " bytes transmitiendo");
			sm.Escribir(buffer, bytes);
		}
	}

}
