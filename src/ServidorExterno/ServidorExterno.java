package ServidorExterno;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import BaseDeDatos.IDBUsuarios;

import util.SocketManager;

public class ServidorExterno {

	private IDBUsuarios gbu;
	public ServidorExterno(IDBUsuarios gbu) {
		this.gbu=gbu;
	}

	public void ConexionServidor() {
		boolean fin = false;
		ServerSocket ss = null;
		int numConexiones = 0;

		try {
			ss = new ServerSocket(3002);
			System.out.println("Arrancando servidor actualizacion...");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (fin == false) {
			try {
				Socket s = ss.accept();
				SocketManager sm = new SocketManager(s);
				PeticionServidor pc = new PeticionServidor(sm,gbu);
				Thread hilo = new Thread(pc);
				numConexiones++;
				System.out
						.println("Número de conexiones de servidores simultaneas: "
								+ numConexiones);
				hilo.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
