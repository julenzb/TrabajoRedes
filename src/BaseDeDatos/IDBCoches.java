package BaseDeDatos;

import java.sql.SQLException;
import java.util.LinkedList;

import ClasesBasicas.SensorMedidas;
import ClasesBasicas.Sensor;
import ClasesBasicas.Coche;
import Excepciones.SenaOFFException;
import Excepciones.SenaONException;

public interface IDBCoches {

	public void inicializarBD() throws ClassNotFoundException, SQLException;

	public void finalizarBD() throws SQLException;

	public Coche CargarCoche(String IDCoche) throws SQLException;

	public LinkedList<Sensor> CargarListaSensores(String IDCoche)
			throws SQLException;

	public LinkedList<SensorMedidas> CargarMedidasSensor(String IDSensor)
			throws SQLException;

	public String ObtenerValorSensor() throws SQLException;

	public String ObtenerCoordenadas() throws SQLException;

	public void CambiarEstadoGPSaOn(String IDCoche) throws SQLException,
			SenaONException, SenaONException;

	public void CambiarEstadoGPSaOff(String IDCoche) throws SQLException,
			SenaOFFException;

	void CambiarEstadoSensorAoff(String IDSensor) throws SQLException,
			SenaOFFException;

	public void CambiarEstadoSensorAon(String IDSensor) throws SQLException,
			SenaONException;

	public LinkedList<String> CargarIDCochees() throws SQLException;

	public String ObtenerEstCoche(String iDCoche) throws SQLException;
}
