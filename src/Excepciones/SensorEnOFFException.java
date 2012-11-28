package Excepciones;

import javax.swing.JOptionPane;

/**
 * Excepcion que salta cuando el Sensor se encuentra en estado OFF
 * @author Inazio
 *
 */
public class SensorEnOFFException extends Exception {

	private static final long serialVersionUID = 1L;

	public SensorEnOFFException(){

	JOptionPane.showMessageDialog(null, "416 ERR Sensor en OFF.", "ERROR", JOptionPane.ERROR_MESSAGE); 

	}
}
