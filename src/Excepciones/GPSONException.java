package Excepciones;

import javax.swing.JOptionPane;

/**
 * Excepcion que salta cuando el GPS se encuentra en estado ON
 * @author Inazio
 *
 */
public class GPSONException extends Exception {

	private static final long serialVersionUID = 1L;

	public GPSONException(){

	JOptionPane.showMessageDialog(null, "419 ERR GPS en estado ON.", "ERROR", JOptionPane.ERROR_MESSAGE); 

	}
}
