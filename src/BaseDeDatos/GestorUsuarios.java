package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;

import ClasesBasicas.SensorMedidas;
import ClasesBasicas.Sensor;
import ClasesBasicas.Coche;
import Excepciones.SenaOFFException;
import Excepciones.SenaONException;

public class GestorUsuarios implements IDBCoches {

	public static final String NOMBRE_DRIVER = "org.sqlite.JDBC";
	public static final String CONEXION_URL = "jdbc:sqlite:db/SistemaMercancias.sqlite";
	Connection conexion;

	@Override
	public void inicializarBD() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName(NOMBRE_DRIVER);
		conexion = DriverManager.getConnection(CONEXION_URL);
	}

	@Override
	public void finalizarBD() throws SQLException {
		// TODO Auto-generated method stub
		conexion.close();
	}

	public LinkedList<String> CargarIDCochees() throws SQLException {
		// TODO Auto-generated method stub
		LinkedList<String> IDs = new LinkedList<String>();
		java.sql.Statement s = null;
		String sqlStatementString = null;
		ResultSet resultSet = null;
		s = conexion.createStatement();
		sqlStatementString = "SELECT * FROM Coche";
		resultSet = s.executeQuery(sqlStatementString);
		if (!resultSet.isClosed()) {
			while (resultSet.next()) {
				String i = resultSet.getString("ID_Coche");
				IDs.add(i);
			}
		}
		return IDs;
	}

	@Override
	public Coche CargarCoche(String ID_Coche) throws SQLException {
		// TODO Auto-generated method stub
		java.sql.Statement s = null;
		String sqlStatementString = null;
		ResultSet resultSet = null;
		s = conexion.createStatement();
		Coche tr = new Coche("", "", "", null);
		sqlStatementString = "SELECT * FROM Coche WHERE ID_Coche='" + ID_Coche
				+ "'";
		resultSet = s.executeQuery(sqlStatementString);
		String i = resultSet.getString("ID_Coche");
		String eg = resultSet.getString("EstadoGPS");
		String et = resultSet.getString("EstadoCoche");
		tr.setID_Coche(i);
		tr.setEstadoGPS(eg);
		tr.setEstadoCoche(et);
		LinkedList<Sensor> listaSensores = this.CargarListaSensores(i);
		tr.setLista(listaSensores);
		for (int cont = 0; cont < listaSensores.size(); cont++) {
			Sensor sensor = tr.getLista().get(cont);
			LinkedList<SensorMedidas> ms = this.CargarMedidasSensor(sensor
					.getID_Sensor());
			sensor.setLista(ms);
		}
		return tr;
	}

	// LISTSENSOR
	@Override
	public LinkedList<Sensor> CargarListaSensores(String ID_Coche)
			throws SQLException {
		// TODO Auto-generated method stub
		java.sql.Statement s = null;
		String sqlStatementString = null;
		ResultSet resultSet = null;
		s = conexion.createStatement();
		LinkedList<Sensor> lista = new LinkedList<Sensor>();
		sqlStatementString = " SELECT * FROM Sensor WHERE ID_Coche='" + ID_Coche
				+ "' ";
		resultSet = s.executeQuery(sqlStatementString);

		while (resultSet.next()) {
			String id_Coche = resultSet.getString("id_Coche");
			String id_sensor = resultSet.getString("id_sensor");
			String desc = resultSet.getString("descripcion");
			String est = resultSet.getString("estado");
			Sensor j = new Sensor(id_sensor, id_Coche, desc, est, null);
			lista.add(j);
		}
		return lista;
	}

	// HISTORICO id_sensor
	@Override
	public LinkedList<SensorMedidas> CargarMedidasSensor(String id_sensor)
			throws SQLException {
		// TODO Auto-generated method stub
		java.sql.Statement l = null;
		String sqlStatementString = null;
		ResultSet resultSet = null;
		l = conexion.createStatement();
		LinkedList<SensorMedidas> lista = new LinkedList<SensorMedidas>();
		sqlStatementString = " SELECT * FROM MedidasSensor WHERE id_sensor='"
				+ id_sensor + "' ";
		resultSet = l.executeQuery(sqlStatementString);
		while (resultSet.next()) {
			String id_medidas = resultSet.getString("id_medidas");
			String fech = resultSet.getString("fecha");
			String hor = resultSet.getString("hora");
			String coor = resultSet.getString("coordenadas");
			String val = resultSet.getString("valor");
			SensorMedidas ms = new SensorMedidas(id_medidas, id_sensor, fech,
					hor, coor, val);
			lista.add(ms);
		}
		return lista;
	}

	// ON id_sensor
	@Override
	public void CambiarEstadoSensorAon(String id_sensor) throws SQLException,
			SenaONException {
		// TODO Auto-generated method stub
		String estado = "";
		java.sql.Statement s = null;
		String sqlStatementString = null;
		ResultSet resultSet = null;
		s = conexion.createStatement();
		String comprobarEstadoString = " SELECT * FROM Sensor WHERE id_sensor='"
				+ id_sensor + "' ";
		resultSet = s.executeQuery(comprobarEstadoString);
		estado = resultSet.getString("estado");
		if (estado.equals("OFF")) {
			sqlStatementString = "UPDATE Sensor set Estado='ON'  WHERE id_sensor='"
					+ id_sensor + "'";
			s.executeUpdate(sqlStatementString);
		} else {
			System.out.println("428 ERR Sensor en estado ON");

		}
	}

	// OFF id_sensor
	@Override
	public void CambiarEstadoSensorAoff(String id_sensor) throws SQLException,
			SenaOFFException {
		// TODO Auto-generated method stub
		String estado = "";
		java.sql.Statement s = null;
		String sqlStatementString = null;
		ResultSet resultSet = null;
		s = conexion.createStatement();
		String comprobarEstadoString = "SELECT * FROM Sensor WHERE id_sensor='"
				+ id_sensor + "' ";
		resultSet = s.executeQuery(comprobarEstadoString);
		estado = resultSet.getString("estado");
		if (estado.equals("ON")) {
			sqlStatementString = "UPDATE Sensor set estado='OFF' WHERE id_sensor='"
					+ id_sensor + "'";
			s.executeUpdate(sqlStatementString);
		} else {
			System.out.println("429 ERR Sensor en estado OFF");
		}
	}

	// ONGPS
	@Override
	public void CambiarEstadoGPSaOn(String ID_Coche) throws SQLException,
			SenaONException {
		// TODO Auto-generated method stub
		String estadog = "";
		java.sql.Statement c = null;
		String sqlStatementString = null;
		ResultSet resultSet = null;
		c = conexion.createStatement();
		String comprEstadoString = " SELECT * FROM Coche WHERE ID_Coche='"
				+ ID_Coche + "'";
		resultSet = c.executeQuery(comprEstadoString);
		estadog = resultSet.getString("EstadoGPS");
		if (estadog.equals("OFF")) {
			sqlStatementString = "UPDATE Coche set EstadoGPS='ON'  WHERE ID_Coche='"
					+ ID_Coche + "'";
			c.executeUpdate(sqlStatementString);
		} else {
			throw new SenaONException();
		}
	}

	// OFFGPS
	@Override
	public void CambiarEstadoGPSaOff(String ID_Coche) throws SQLException,
			SenaOFFException {
		// TODO Auto-generated method stub
		String estadog = "";
		java.sql.Statement c = null;
		String sqlStatementString = null;
		ResultSet resultSet = null;
		c = conexion.createStatement();
		String comprEstadoString = " SELECT * FROM Coche WHERE ID_Coche='"
				+ ID_Coche + "'";
		resultSet = c.executeQuery(comprEstadoString);
		estadog = resultSet.getString("EstadoGPS");
		if (estadog.equals("ON")) {
			sqlStatementString = "UPDATE Coche set EstadoGPS='OFF'  WHERE ID_Coche ='"
					+ ID_Coche + "'";
			c.executeUpdate(sqlStatementString);
		} else {
			throw new SenaOFFException();
		}
	}

	// GET_VALACT
	@Override
	public String ObtenerValorSensor() throws SQLException {
		// TODO Auto-generated method stub
		String valorActualSensor = obtenerFecha() + " " + obtenerHora() + " "
				+ obtenerNumeroAleatorio() + "º" + obtenerNumeroAleatorio()
				+ "'" + obtenerNumeroAleatorio() + "."
				+ obtenerNumeroAleatorio() + "''" + " - "
				+ obtenerNumeroAleatorio() + "º" + obtenerNumeroAleatorio()
				+ "'" + obtenerNumeroAleatorio() + "."
				+ obtenerNumeroAleatorio() + ";" + obtenerNumeroAleatorio();
		System.out.println("Valor de sensor: " + valorActualSensor);
		return valorActualSensor;
	}

	// GET_LOC
	@Override
	public String ObtenerCoordenadas() throws SQLException {
		// TODO Auto-generated method stub
		String valorActualGPS = obtenerNumeroAleatorio() + "º"
				+ obtenerNumeroAleatorio() + "'" + obtenerNumeroAleatorio()
				+ "." + obtenerNumeroAleatorio() + "''" + " - "
				+ obtenerNumeroAleatorio() + "º" + obtenerNumeroAleatorio()
				+ "'" + obtenerNumeroAleatorio() + "."
				+ obtenerNumeroAleatorio() + ";" + obtenerNumeroAleatorio();
		System.out.println("Coordenadas GPS: " + valorActualGPS);
		return valorActualGPS;
	}

	private double obtenerNumeroAleatorio() {
		int inferior = 0;
		int superior = 99;
		int numPosibilidades = (superior + 1) - inferior;
		double aleatorio = Math.random() * numPosibilidades;
		aleatorio = Math.floor(aleatorio);
		aleatorio = (inferior + aleatorio);
		return aleatorio;
	}

	private String obtenerFecha() {
		Calendar calendario = Calendar.getInstance();
		int dia = calendario.get(Calendar.DAY_OF_MONTH);
		int mes = calendario.get(Calendar.MONTH);
		int año = calendario.get(Calendar.YEAR);
		return dia + "/" + (mes + 1) + "/" + año;
	}

	private String obtenerHora() {
		Calendar calendario = Calendar.getInstance();
		int hora = calendario.get(Calendar.HOUR_OF_DAY);
		int minuto = calendario.get(Calendar.MINUTE);
		int segundo = calendario.get(Calendar.SECOND);
		return hora + ":" + minuto + ":" + segundo;
	}

	// obtener el estado de un Coche
	public String ObtenerEstCoche(String id_Coche) throws SQLException {
		// TODO Auto-generated method stub
		Boolean bool = false;
		String estado = "";
		java.sql.Statement s = null;
		String sqlStatementString = null;
		ResultSet resultSet = null;
		s = conexion.createStatement();
		String comprobarEstadoString = "SELECT EstadoCoche FROM Coche WHERE ID_Coche='"+ id_Coche + "'";
		resultSet = s.executeQuery(comprobarEstadoString);
		estado = resultSet.getString("EstadoCoche");
		return estado;
	}
}
