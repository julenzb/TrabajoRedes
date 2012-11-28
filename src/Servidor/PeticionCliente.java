package Servidor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import util.SocketManager;
import BaseDeDatos.IDBCoches;
import BaseDeDatos.IDBUsuarios;
import ClasesBasicas.SensorMedidas;
import ClasesBasicas.Sensor;
import ClasesBasicas.Coche;
import Excepciones.SenaOFFException;
import Excepciones.SenaONException;

public class PeticionCliente extends Thread{
	private boolean seguir;
	private SocketManager sm;
	private IDBCoches bdt;
	private IDBUsuarios bdu;
	private String nombre;
	private String pass;
	private Administrador adm;
	
	  // Constructor
	PeticionCliente(Administrador adm, SocketManager sockm, IDBCoches bdt, IDBUsuarios bdu){
		this.seguir=true;
		this.adm=adm;
		this.sm=sockm;
		this.bdt=bdt;
		this.bdu=bdu;
	}
	
	public void cerrarSocket(){
		try {
			this.sm.CerrarSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog (null, "Usuario expulsado", "", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}

	public void setSeguir(boolean b) {
		// TODO Auto-generated method stub
		this.seguir=b;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ProcesarPeticion();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	private void ProcesarPeticion() throws IOException {
		// TODO Auto-generated method stub
		
		// Validación de cliente (user y pass)
		String lineaProcesada=sm.Leer();
		System.out.println("EL SERVIDOR HA RECIBIDO: "+lineaProcesada);
		StringTokenizer tokens = new StringTokenizer(lineaProcesada);
	if(tokens.nextToken().equals("USER"))
	{
		try {
			bdu.inicializarBD();
			nombre=tokens.nextToken();
			if(bdu.validarUser(nombre))
			{
				bdu.finalizarBD();
				System.out.println("Nombre usuario correcto");
				sm.Escribir("210 OK Bienvenido "+nombre+'\n');
				lineaProcesada=sm.Leer();
				System.out.println("EL SERVIDOR HA RECIBIDO: "+lineaProcesada);
				tokens = new StringTokenizer(lineaProcesada);
				if(tokens.nextToken().equals("PASS"))
				{
					bdu.inicializarBD();
					pass=tokens.nextToken();
					if(bdu.validarPass(nombre, pass))
					{
						bdu.finalizarBD();
						System.out.println("Pass usuario correcta");
						sm.Escribir("211 OK Bienvenido al sistema"+'\n');
						adm.cambiarNombreHiloActual(nombre);
						adm.cargarHilosActivos();
						adm.rellenarTabla();
						if(sm.Leer().equals("IDCoche"))
						{
							bdt.inicializarBD();
							LinkedList <String> l = bdt.CargarIDCochees();
							bdt.finalizarBD();
							int cont=0;
							while(cont<l.size())
							{
								System.out.println(l.get(cont));
								sm.Escribir(l.get(cont)+'\n');
								cont++;
							}
							sm.Escribir("222 Fin de ID_Cochees"+'\n');
						}
	
						
						//a partir de este while se podrán cargar el resto de comandos ya que el usuario ya esta validado
						while(seguir)
						{
						lineaProcesada=sm.Leer();
						StringTokenizer token=new StringTokenizer(lineaProcesada);
						String comando=token.nextToken();
						System.out.println(lineaProcesada);
			
						//Comando SALIR
						if(comando.equals("SALIR")){
							adm.quitarHilo(nombre);
							adm.rellenarTabla();
							sm.Escribir("218 OK Adios" + '\n');
							seguir = false;
						}
						
						//Comando ESTADOCoche
							if(comando.equals("ESTADOCoche")){
					            String IDCoche=token.nextToken();
					            bdt.inicializarBD();
								Coche t = bdt.CargarCoche(IDCoche);
								bdt.finalizarBD();
								System.out.println("El servidor manda: "+t.getEstadoCoche());
								sm.Escribir(t.getEstadoCoche()+'\n');
							
							}
							
						//Comando ESTADOGPS
							if(comando.equals("ESTADOGPS")){
					            String IDCoche=token.nextToken();
					            bdt.inicializarBD();
								Coche t = bdt.CargarCoche(IDCoche);
								bdt.finalizarBD();
								System.out.println("El servidor manda: "+t.getEstadoCoche());
								sm.Escribir(t.getEstadoGPS()+'\n');
								
							}
						
						//Comando LISTSENSOR
						if(comando.equals("LISTSENSOR")){
				            String IDCoche=token.nextToken();
				            bdt.inicializarBD();
							LinkedList <Sensor> s = bdt.CargarListaSensores(IDCoche);
							bdt.finalizarBD();
							int cont=0;
							while(cont<s.size())
							{
								System.out.println(s.get(cont));
								Sensor se= s.get(cont);
								System.out.println("El servidor manda: "+se.getID_Sensor()+" "+se.getDescripcion() + " " + se.getEstado());
								sm.Escribir(se.getID_Sensor()+" "+se.getDescripcion() + " " + se.getEstado()+'\n');
								cont++;
							}
							sm.Escribir("222 OK Lista-Finalizada"+'\n');
							
						}
						
						//Comando HISTORICO
						if(comando.equals("HISTORICO")){
							String IDSen=token.nextToken();
							bdt.inicializarBD();
							LinkedList<SensorMedidas> s = bdt.CargarMedidasSensor(IDSen);
							bdt.finalizarBD();
							System.out.println(s.size());
							int cont=0;
							while(cont<s.size())
							{
								System.out.println(s.get(cont));
								SensorMedidas me= s.get(cont);
								sm.Escribir(me.getFecha() + " "+me.getHora()  +" "+ me.getCoordenadas()+ " " + me.getValor() +'\n');
								cont++;
							}
							sm.Escribir("222 OK Lista Finalizada."+'\n');
						}
						//Comando ON id_sensor
						if(comando.equals("ON")){
							boolean error=false;
				            String IDSen=token.nextToken();
							try {
								bdt.inicializarBD();
								bdt.CambiarEstadoSensorAon(IDSen);
								bdt.finalizarBD();
							} catch (SenaONException e) {
								// TODO Auto-generated catch block
								sm.Escribir("428 ERR Sensor en estado ON");
								error=true;
							} 
							if(error==false)
							sm.Escribir("213 OK Sensor activo."+'\n');
							}
						
						//Comando OFF id_sensor
						if(comando.equals("OFF")){
							boolean error=false;
				            String IDSen=token.nextToken();
							try {
								bdt.inicializarBD();
								bdt.CambiarEstadoSensorAoff(IDSen);
								bdt.finalizarBD();
							} catch (SenaOFFException e) {
								// TODO Auto-generated catch block
								sm.Escribir("429 ERR Sensor en estado OFF"+'\n');
								error=true;
							}
							if(error==false)
							sm.Escribir("214 OK Sensor desactivado."+'\n');
							}
						
						//Comando ONGPS
						if(comando.equals("ONGPS")){
							boolean error=false;
				            String IDSen=token.nextToken();
							try {
								bdt.inicializarBD();
								bdt.CambiarEstadoGPSaOn(IDSen);
								bdt.finalizarBD();
							} catch (SenaONException e) {
								// TODO Auto-generated catch block
								error=true;
								sm.Escribir("429 ERR GPS en estado ON"+'\n');
							}
							if(error==false)
							sm.Escribir("215 OK GPS activado."+'\n');
							}
						
						//Comando OFFGPS
						if(comando.equals("OFFGPS")){
							boolean error=false;
				            String IDCoche=token.nextToken();
							try {
								bdt.inicializarBD();
								bdt.CambiarEstadoGPSaOff(IDCoche);
								bdt.finalizarBD();
							} catch (SenaOFFException e) {
								// TODO Auto-generated catch block
								error=true;
								sm.Escribir("430 ERR GPS en estado OFF"+'\n');
							}
							if(error==false)
							sm.Escribir("216 OK GPS desactivado."+'\n');
							}
						
						//Comando GET_LOC
						if(comando.equals("GET_LOC")){
							bdt.inicializarBD();
							String v= bdt.ObtenerCoordenadas();
							bdt.finalizarBD();
							sm.Escribir("124 OK "+ v +'\n');
							}
						//Comando GET_VALACT
						if(comando.equals("GET_VALACT")){
							bdt.inicializarBD();
							String v = bdt.ObtenerValorSensor();
							bdt.finalizarBD();
							sm.Escribir("124 OK "+ v +'\n');
							}
						
						//Comando ACTUALIZAR
						if(comando.equals("ACTUALIZAR")){
							Gestor_ConexionServidorActualizar gcs=new Gestor_ConexionServidorActualizar(nombre,pass);
						}
						
						//Comando ComprobarEstadoCoche
						if(comando.equals("ComprobarEstadoCoche")){
							 String IDCoche=token.nextToken();
							 bdt.inicializarBD();
							String v = bdt.ObtenerEstCoche(IDCoche);
							bdt.finalizarBD();
							sm.Escribir(v +'\n');
							}
						}		
					}
					else
					{
					bdu.finalizarBD();
					System.out.println("Pass usuario incorrecta");
					sm.Escribir("412 ERR La clave es incorrecta"+'\n');	
					}
				}
				}	
			
			else
			{
				bdu.finalizarBD();
				System.out.println("Nombre usuario incorrecto");
				sm.Escribir("411 ERR Usuario incorrecto"+'\n');
			}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

	

}
	
}


