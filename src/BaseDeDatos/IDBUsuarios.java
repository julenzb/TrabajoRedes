package BaseDeDatos;

import java.sql.SQLException;
import java.util.LinkedList;

import ClasesBasicas.Usuario;

public interface IDBUsuarios {
	public void inicializarBD() throws ClassNotFoundException, SQLException;

	public void finalizarBD() throws SQLException;

	public boolean validarUser(String us) throws SQLException;

	public boolean validarPass(String us, String pas) throws SQLException;

	public void GuardarUsuario(Usuario u) throws SQLException;

	public Usuario CargarUsuario(String usuario) throws SQLException;

	public LinkedList<Usuario> CargarUsuarios() throws SQLException;

	public void BorrarUsuario(String u) throws SQLException;

	public boolean obtenerDatosUsuario(String NickJugador, String Password)
			throws SQLException, Excepciones.UsuarioYaRegistrado;

	void ActualizarUsuario(String us, String string) throws SQLException;
}
