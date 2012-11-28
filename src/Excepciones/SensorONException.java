package Excepciones;

import javax.swing.JOptionPane;

/**
 * Excepcion que salta cuando el Sensor se encuentra en estado ON
 * @author Inazio
 *
 */
public class SensorONException extends Exception {

	private static final long serialVersionUID = 1L;

	public SensorONException(){

	JOptionPane.showMessageDialog(null, "418 ERR Sensor en estado ON.", "ERROR", JOptionPane.ERROR_MESSAGE); 

	}
}
