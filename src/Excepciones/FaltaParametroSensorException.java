package Excepciones;

import javax.swing.JOptionPane;

/**
 * Excepcion que salta cuando faltan parametros en el Sensor
 * @author Inazio
 *
 */
public class FaltaParametroSensorException extends Exception {

	private static final long serialVersionUID = 1L;

	public FaltaParametroSensorException(){

	JOptionPane.showMessageDialog(null, "415 ERR Falta parametro id_sensor.", "ERROR", JOptionPane.ERROR_MESSAGE); 

	}
}
