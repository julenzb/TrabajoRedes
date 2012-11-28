package Excepciones;

import javax.swing.JOptionPane;

/**
 * Excepcion que salta cuando el usuario inserta una password incorrecta
 * @author Inazio
 *
 */
public class PasswordIncorrectaException extends Exception {

	private static final long serialVersionUID = 1L;

	public PasswordIncorrectaException(){

	JOptionPane.showMessageDialog(null, "402 ERR La clave es incorrecta.", "ERROR", JOptionPane.ERROR_MESSAGE); 

	}
}
