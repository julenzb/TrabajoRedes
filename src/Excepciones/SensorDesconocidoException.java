package Excepciones;

import javax.swing.JOptionPane;

/**
 * Excepcion que salta cuando el Sensor es desconocido
 * @author Inazio
 *
 */
public class SensorDesconocidoException extends Exception {

	private static final long serialVersionUID = 1L;

	public SensorDesconocidoException(){

	JOptionPane.showMessageDialog(null, "414 ERR Sensor desconocido.", "ERROR", JOptionPane.ERROR_MESSAGE); 

	}
}
