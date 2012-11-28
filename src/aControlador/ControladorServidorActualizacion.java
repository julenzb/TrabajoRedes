package aControlador;
import BaseDeDatos.GestorCoches;
import BaseDeDatos.IDBUsuarios;
import ServidorExterno.ServidorExterno;


public class ControladorServidorActualizacion {
	public static void main(String[]args){
	IDBUsuarios gbu = new GestorCoches();
	ServidorExterno se=new ServidorExterno(gbu);
	se.ConexionServidor();
	}
}
