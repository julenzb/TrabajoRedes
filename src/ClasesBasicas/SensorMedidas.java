package ClasesBasicas;

public class SensorMedidas {
	private String ID_Sensor;
	private String fecha;
	private String hora;
	private String coordenadas;
	private String valor;
	private String ID_Medidas;

	public String getID_Sensor() {
		return ID_Sensor;
	}

	public SensorMedidas(String ID_Medidas, String ID_Sensor, String fecha,
			String hora, String coordenadas, String valor) {
		super();
		this.ID_Sensor = ID_Sensor;
		this.fecha = fecha;
		this.hora = hora;
		this.coordenadas = coordenadas;
		this.valor = valor;
		this.ID_Medidas = ID_Medidas;
	}

	public String getID_Medidas() {
		return ID_Medidas;
	}

	public void setID_Medidas(String ID_Medidas) {
		this.ID_Medidas = ID_Medidas;
	}

	public void setID_Sensor(String ID_Sensor) {
		this.ID_Sensor = ID_Sensor;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
