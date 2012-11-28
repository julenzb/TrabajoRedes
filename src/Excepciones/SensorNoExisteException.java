package Excepciones;

import javax.swing.JOptionPane;

/**
 * Excepcion que salta cuando el Sensor indicado no existe
 * @author Inazio
 *
 */
public class SensorNoExisteException extends Exception {

	private static final long serialVersionUID = 1L;

	public SensorNoExisteException(){

	JOptionPane.showMessageDialog(null, "417 ERR Sensor no existe.", "ERROR", JOptionPane.ERROR_MESSAGE); 

	}
}
