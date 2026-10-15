package Taller02;

public class Terremoto {

	private String consumidor;
	private double litros;
	private String fecha;

	//Constructor: crea un terremoto con todos sus datos
	public Terremoto(String consumidor, double litros, String fecha) {
		this.consumidor = consumidor;
		this.litros = litros;
		this.fecha = fecha;
	}

	public String getConsumidor() {
		return consumidor;
	}

	public void setConsumidor(String consumidor) {
		this.consumidor = consumidor;
	}

	public double getLitros() {
		return litros;
	}

	public void setLitros(double litros) {
		this.litros = litros;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public double calcularKcal() {
		return (litros * 10) / 3;
	}
}