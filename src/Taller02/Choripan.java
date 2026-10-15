package Taller02;

public class Choripan {

	private String consumidor;
	private double largo;
	private double ancho;
	private String fecha;

	// Constructor: crea un choripan con todos sus datos
	public Choripan(String consumidor, double largo, double ancho, String fecha) {
		this.consumidor = consumidor;
		this.largo = largo;
		this.ancho = ancho;
		this.fecha = fecha;
	}

	public String getConsumidor() {
		return consumidor;
	}

	public void setConsumidor(String consumidor) {
		this.consumidor = consumidor;
	}

	public double getLargo() {
		return largo;
	}

	public void setLargo(double largo) {
		this.largo = largo;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	// Calcula las kcal del choripan: (largo * ancho) / 3
	public double calcularKcal() {
		return (largo * ancho) / 3;
	}
}