package aControlador;
import java.sql.SQLException;

import BaseDeDatos.GestorCoches;
import BaseDeDatos.GestorUsuarios;
import BaseDeDatos.IDBCoches;
import BaseDeDatos.IDBUsuarios;
import Servidor.Servidor;
import ServidorExterno.ServidorExterno;

public class ControladorServidorPrincipal {

	// Hacer funcionar el servidor pasandole el gestor de base de datos de los
	// trenes y de los usuarios
	public static void main(String[] args) {
		IDBCoches gbt = new GestorUsuarios();
		IDBUsuarios gbu = new GestorCoches();
		Servidor s = new Servidor(gbt, gbu);
		try {
			s.ConexionServidor();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
