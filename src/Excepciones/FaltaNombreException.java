package Excepciones;

import javax.swing.JOptionPane;

public class FaltaNombreException extends Exception {

	private static final long serialVersionUID = 1L;

	public FaltaNombreException(){

	JOptionPane.showMessageDialog(null, "401 ERR Falta el nombre de usuario.", "ERROR", JOptionPane.ERROR_MESSAGE); 

	}
}
