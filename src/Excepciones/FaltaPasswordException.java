package Excepciones;

import javax.swing.JOptionPane;

/**
 * Excepcion que salta cuando el usuario ha olvidado introducir la password
 * @author Inazio
 *
 */
public class FaltaPasswordException extends Exception {

	private static final long serialVersionUID = 1L;

	public FaltaPasswordException(){

	JOptionPane.showMessageDialog(null, "403 ERR Falta la clave.", "ERROR", JOptionPane.ERROR_MESSAGE); 

	}
}
