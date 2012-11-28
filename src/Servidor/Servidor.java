package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import util.SocketManager;
import BaseDeDatos.IDBCoches;
import BaseDeDatos.IDBUsuarios;

public class Servidor {

	private IDBCoches bdt;
	private IDBUsuarios bdu;
	private Administrador ad;

	public Servidor(IDBCoches bdt, IDBUsuarios bdu) {
		super();
		this.bdt = bdt;
		this.bdu = bdu;
	}

	public void setBdt(IDBCoches bdt) {
		this.bdt = bdt;
	}

	public void setBdu(IDBUsuarios bdu) {
		this.bdu = bdu;
	}

	public void ConexionServidor() throws ClassNotFoundException, SQLException {
		ad = new Administrador(bdu);
		ad.setVisible(true);
		boolean fin = false;
		ServerSocket ss = null;
		int numConexionesRealizadas = 0;

		try {
			ss = new ServerSocket(3001);
			System.out.println("Arrancando servidor...");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (fin == false) {
			try {
				Socket s = ss.accept();
				SocketManager sm = new SocketManager(s);
				PeticionCliente hilo = new PeticionCliente(ad, sm, this.bdt,
						this.bdu);
				numConexionesRealizadas++;
				System.out.println("SE HAN REALIZADO "
						+ numConexionesRealizadas + " CONEXIONES EN TOTAL");
				hilo.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
		}

	}
}