package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import ClasesBasicas.Usuario;


public class GestorCoches implements IDBUsuarios {
	
	public static final String NOMBRE_DRIVER = "org.sqlite.JDBC";
	public static final String CONEXION_URL = "jdbc:sqlite:db/SistemaMercancias.sqlite";
	Connection conexion2;

	
	@Override
	public void inicializarBD() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName(NOMBRE_DRIVER);
		conexion2=DriverManager.getConnection(CONEXION_URL);
		
	}

	@Override
	public void finalizarBD() throws SQLException {
		// TODO Auto-generated method stub
		conexion2.close();
	}
	
	public boolean validarUser(String us) throws SQLException
	{
		// TODO Auto-generated method stub
		Boolean x;
		java.sql.Statement s=null;
		ResultSet resultSet = null;
		s=conexion2.createStatement();
		String comprobarUs = " SELECT * FROM Usuarios WHERE usuario='"+us+"' ";
		resultSet = s.executeQuery(comprobarUs);
		if(!resultSet.isClosed())
			x=true;
		else 
			x=false;
		return x;
	}
	
	
	public boolean validarPass(String us, String pass) throws SQLException{
		{
			// TODO Auto-generated method stub
			Boolean x = false;
			java.sql.Statement s=null;
			ResultSet resultSet = null;
			s=conexion2.createStatement();
			String comprobarUs = " SELECT * FROM Usuarios WHERE contraseña='"+pass+"' AND usuario='"+us+"'";
			resultSet = s.executeQuery(comprobarUs);
			if(!resultSet.isClosed()){
				x=true;
			}
			else 
				x=false;
			return x;
		}

	}
	
	@Override
	public void ActualizarUsuario(String us, String pass) throws SQLException {
		// TODO Auto-generated method stub
		java.sql.Statement s=null;
		String sqlStatementString=null;
		s=conexion2.createStatement();
		System.out.println("Usuario:"+us+" pass:"+pass);
		sqlStatementString="UPDATE Usuarios set contraseña='"+pass+"' WHERE usuario='"+us+"'";  	
		s.executeUpdate(sqlStatementString);
	}
	
	@Override
	public void GuardarUsuario(Usuario u) throws SQLException {
		// TODO Auto-generated method stub
		java.sql.Statement s=null;
		String sqlStatementString=null;
		s=conexion2.createStatement();
		sqlStatementString="INSERT INTO Usuarios (usuario, contraseña) VALUES ('"+u.getNick()+"','"+u.getPass()+"')";  	
		s.executeUpdate(sqlStatementString);
	}

	@Override
	public Usuario CargarUsuario(String usuario) throws SQLException {
		// TODO Auto-generated method stub
		java.sql.Statement s=null;
		String sqlStatementString=null;
		ResultSet resultSet = null;
		s=conexion2.createStatement();
		sqlStatementString = "SELECT * FROM Usuarios WHERE usuario='"+usuario+"'";
		resultSet = s.executeQuery(sqlStatementString);
		Usuario u = new Usuario(resultSet.getString("usuario"),resultSet.getString("contraseña"));
		return u;
	}
	
	@Override
	public LinkedList<Usuario> CargarUsuarios() throws SQLException {
		// TODO Auto-generated method stub
		LinkedList<Usuario> lista=new LinkedList<Usuario>();
		java.sql.Statement s=null;
		String sqlStatementString=null;
		ResultSet resultSet = null;
		s=conexion2.createStatement();
		sqlStatementString = "SELECT * FROM Usuarios";
		resultSet = s.executeQuery(sqlStatementString);
		while(resultSet.next())
		{
			Usuario u = new Usuario(resultSet.getString("usuario"),resultSet.getString("contraseña"));
			lista.add(u);
		}
		return lista;
	}
	
	public void BorrarUsuario(String u) throws SQLException {
		// TODO Auto-generated method stub
		java.sql.Statement s=null;
		String sqlStatementString=null;
		s=conexion2.createStatement();
		sqlStatementString="DELETE FROM Usuarios WHERE usuario = '"+u+"'";  	
		s.executeUpdate(sqlStatementString);
	}
	
	public boolean obtenerDatosUsuario(String NickJugador, String Password)throws SQLException, Excepciones.UsuarioYaRegistrado {
		// TODO Auto-generated method stub
		java.sql.Statement s=null;
		String sqlStatementString=null;
		ResultSet resultSet = null;
		s=conexion2.createStatement();
	
		sqlStatementString = "SELECT * FROM Usuarios WHERE usuario = '"+NickJugador+"'";
		resultSet = s.executeQuery(sqlStatementString);
		if(!resultSet.isClosed())
			throw new Excepciones.UsuarioYaRegistrado(NickJugador);
		else
		return true;
	}
}
