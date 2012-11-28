package ClasesBasicas;

import java.util.HashSet;

public class Sensor {

	private String ID_Sensor;
	private String ID_Coche;
	private String descripcion;
	private String estado;
	private HashSet<SensorMedidas> lista;

	public Sensor(String iD_Sensor, String ID_Coche, String descripcion,
			String estado, HashSet<SensorMedidas> lista) {
		super();
		ID_Sensor = iD_Sensor;
		this.ID_Coche = ID_Coche;
		this.descripcion = descripcion;
		this.estado = estado;
		this.lista = lista;
	}

	public HashSet<SensorMedidas> getLista() {
		return lista;
	}

	public void setLista(HashSet<SensorMedidas> lista) {
		this.lista = lista;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getID_Coche() {
		return ID_Coche;
	}

	public void setID_Coche(String ID_Coche) {
		this.ID_Coche = ID_Coche;
	}

	public String getID_Sensor() {
		return ID_Sensor;
	}

	public void setID_Sensor(String iD_Sensor) {
		ID_Sensor = iD_Sensor;
	}
}
