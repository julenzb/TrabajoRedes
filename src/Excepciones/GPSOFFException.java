package Excepciones;

import javax.swing.JOptionPane;

/**
 * Excepcion que salta cuando el GPS se encuentra en estado OF
 * @author Inazio
 *
 */
public class GPSOFFException extends Exception {

	private static final long serialVersionUID = 1L;

	public GPSOFFException(){

	JOptionPane.showMessageDialog(null, "416 ERR GPS en estado OFF.", "ERROR", JOptionPane.ERROR_MESSAGE); 

	}
}
