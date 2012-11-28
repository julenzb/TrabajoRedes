package ClasesBasicas;

public class Usuario {
	private String nick;
	private String password;

	public Usuario(String nick, String password) {
		super();
		this.nick = nick;
		this.password = password;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getpassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

}