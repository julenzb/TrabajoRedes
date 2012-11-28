package ClasesBasicas;

import java.util.HashSet;

public class Coche {
	private String IDCoche;
	private String estadoGPS;
	private String estadoCoche;
	private HashSet<Sensor> lista;

	public Coche(String id, String estadoGPS, String estadoCoche,
			HashSet<Sensor> lista) {
		super();
		this.IDCoche = id;
		this.estadoGPS = estadoGPS;
		this.estadoCoche = estadoCoche;
		this.lista = lista;
	}

	public String getID_Coche() {
		return IDCoche;
	}

	public void setID_Coche(String id) {
		this.IDCoche = id;
	}

	public String getEstadoGPS() {
		return estadoGPS;
	}

	public void setEstadoGPS(String estadoGPS) {
		this.estadoGPS = estadoGPS;
	}

	public String getEstadoCoche() {
		return estadoCoche;
	}

	public void setEstadoCoche(String estadoCoche) {
		this.estadoCoche = estadoCoche;
	}

	public HashSet<Sensor> getLista() {
		return lista;
	}

	public void setLista(HashSet<Sensor> lista) {
		this.lista = lista;
	}

}
