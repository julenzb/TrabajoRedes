package Excepciones;

import javax.swing.JOptionPane;

/**
 * Excepcion que salta cuando el Sensor se encuentra en estado OFF
 * @author Inazio
 *
 */
public class SensorOFFException extends Exception {

	private static final long serialVersionUID = 1L;

	public SensorOFFException(){

	JOptionPane.showMessageDialog(null, "419 ERR Sensor en estado OFF.", "ERROR", JOptionPane.ERROR_MESSAGE); 

	}
}
