//Vicente Matte 21.753.089-K ITI


package Taller02;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	private static Choripan[] listaChoripanes = new Choripan[200];
	private static Terremoto[] listaTerremotos = new Terremoto[200];
	private static String[] listaParticipantes = new String[200];
	private static int cantidadChoripanes = 0;
	private static int cantidadTerremotos = 0;
	private static int cantidadParticipantes = 0;
	private static Scanner input = new Scanner(System.in);
	private static Scanner s;
	private static File file;


	public static void main(String[] args) {
		leerParticipantes();
		leerConsumido();
		menu();
	}

	// Muestra el menu principal y se repite hasta que el usuario elige Salir
	private static void menu() {
		int opcion = 0;

		do {
			System.out.println();
			System.out.println("============================================");
			System.out.println("           LA FONDA DEL PAPU");
			System.out.println("============================================");
			System.out.println("  Registros cargados: " + (cantidadChoripanes + cantidadTerremotos));
			System.out.println("--------------------------------------------");
			System.out.println("[1] Ver registros");
			System.out.println("[2] Actualizar registros");
			System.out.println("[3] Calcular metricas");
			System.out.println("[4] Salir");
			System.out.println("============================================");
			System.out.print("Seleccione una opcion: ");
			
			// Si escribe letras, deja vacio o un numero muy grande, queda en 0 y cae en el default
			try {
				opcion = Integer.parseInt(input.nextLine());
			} catch (NumberFormatException e) {
				opcion = 0;
			}

			switch (opcion) {
			case 1:
				verRegistros();
				break;
			case 2:
				System.out.println("Actualizar registros (en construccion)");
				break;
			case 3:
				System.out.println("Calcular metricas (en construccion)");
				break;
			case 4:
				System.out.println("Hasta luego...");
				break;
			default:
				System.out.println("Error: debe ingresar un numero entre 1 y 4. Intente nuevamente.");
			}
		} while (opcion != 4);
	}

	// Submenu de Ver registros: muestra choripanes, terremotos o vuelve al menu principal
	private static void verRegistros() {
		int opcion = 0;

		do {
			System.out.println();
			System.out.println("============ VER REGISTROS =================");
			System.out.println("[1] Choripanes");
			System.out.println("[2] Terremotos");
			System.out.println("[3] Volver al menu principal");
			System.out.println("============================================");
			System.out.print("Seleccione una opcion: ");

			try {
				opcion = Integer.parseInt(input.nextLine());
			} catch (NumberFormatException e) {
				opcion = 0;
			}

			switch (opcion) {
			case 1:
				mostrarChoripanes();
				break;
			case 2:
				mostrarTerremotos();
				break;
			case 3:
				break;
			default:
				System.out.println("Error: debe ingresar un numero entre 1 y 3. Intente nuevamente.");
			}
		} while (opcion != 3);
	}

	// Muestra la tabla de choripanes con toda su informacion y sus kcal
	private static void mostrarChoripanes() {
		System.out.println();
		System.out.println("=============== CHORIPANES =================");
		System.out.println("# | Consumidor | Largo | Ancho | Fecha | KCal");

		for (int i = 0; i < cantidadChoripanes; i++) {
			Choripan c = listaChoripanes[i];
			System.out.println((i + 1) + " | " + c.getConsumidor() + " | " + Math.round(c.getLargo() * 100) / 100.0 + " cm | "
					+ Math.round(c.getAncho() * 100) / 100.0 + " cm | " + c.getFecha() + " | " + Math.round(c.calcularKcal() * 100) / 100.0 + " kcal");
		}

		System.out.println("--------------------------------------------");
		System.out.println("Total: " + cantidadChoripanes + " choripanes registrados");
		System.out.println("============================================");
		System.out.print("Presione ENTER para continuar...");
		input.nextLine();
	}

	// Muestra la tabla de terremotos con toda su informacion y sus kcal
	private static void mostrarTerremotos() {
		double totalLitros = 0;

		System.out.println();
		System.out.println("=============== TERREMOTOS =================");
		System.out.println("# | Consumidor | Litros | Fecha | KCal");

		for (int i = 0; i < cantidadTerremotos; i++) {
			Terremoto t = listaTerremotos[i];
			System.out.println((i + 1) + " | " + t.getConsumidor() + " | " + Math.round(t.getLitros() * 100) / 100.0 + " L | "
					+ t.getFecha() + " | " + Math.round(t.calcularKcal() * 100) / 100.0 + " kcal");
			totalLitros = totalLitros + t.getLitros();
		}

		System.out.println("--------------------------------------------");
		System.out.println("Total: " + cantidadTerremotos + " terremotos registrados (" + Math.round(totalLitros * 100) / 100.0 + " L)");
		System.out.println("============================================");
		System.out.print("Presione ENTER para continuar...");
		input.nextLine();
	}



	// Lee Participantes.txt (un nombre por linea) y los guarda en listaParticipantes
	private static void leerParticipantes() {

		file = new File("Participaciones.txt");

		try {
			s = new Scanner(file);

			while (s.hasNextLine()) {
				String linea = s.nextLine();

				// No pasa de 200 participantes
				if (cantidadParticipantes < 200) {
					listaParticipantes[cantidadParticipantes] = linea;
					cantidadParticipantes++;
				}
			}
			s.close();

		} catch (IOException e) {
			System.out.println("No se encontro el archivo 'Participantes.txt'.");
		}
	}
	
	// Lee Consumido.txt y guarda cada linea en listaChoripanes o listaTerremotos
	private static void leerConsumido() {

		file = new File("Consumido.txt");

		try {
			s = new Scanner(file);

			while (s.hasNextLine()) {
				String linea = s.nextLine();
				String[] partes = linea.split(";");

				if (partes.length == 5 && partes[0].equalsIgnoreCase("Choripan")) {

					try {
						String consumidor = partes[1];
						double largo = Double.parseDouble(partes[2]);
						double ancho = Double.parseDouble(partes[3]);
						String fecha = partes[4];

						// Solo se guarda si queda espacio en el vector
						if (cantidadChoripanes < 200) {
							agregarParticipante(consumidor);
							Choripan choripan = new Choripan(consumidor, largo, ancho, fecha);
							listaChoripanes[cantidadChoripanes] = choripan;
							cantidadChoripanes++;
						}
					} catch (NumberFormatException e) {
						// Numero mal escrito: la linea se descarta
					}

				} else if (partes.length == 4 && partes[0].equalsIgnoreCase("Terremoto")) {

					try {
						String consumidor = partes[1];
						double litros = Double.parseDouble(partes[2]);
						String fecha = partes[3];

						// Solo se guarda si queda espacio en el vector
						if (cantidadTerremotos < 200) {
							agregarParticipante(consumidor);
							Terremoto terremoto = new Terremoto(consumidor, litros, fecha);
							listaTerremotos[cantidadTerremotos] = terremoto;
							cantidadTerremotos++;
						}
					} catch (NumberFormatException e) {
						// Numero mal escrito: la linea se descarta
					}
				}
			}
			s.close();

		} catch (IOException e) {
			System.out.println("No se encontro el archivo 'Consumido.txt'.");
			System.out.println("Se iniciara el programa sin los archivos.");
		}
	}

	// Si el consumidor no esta en listaParticipantes, lo agrega
	private static void agregarParticipante(String nombre) {
		for (int i = 0; i < cantidadParticipantes; i++) {
			if (listaParticipantes[i].equalsIgnoreCase(nombre)) {
				return;
			}
		}
		if (cantidadParticipantes < 200) {
			listaParticipantes[cantidadParticipantes] = nombre;
			cantidadParticipantes++;
		}
	}
	
}