//Vicente Matte 21.753.089-K ITI


package Taller02;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
				actualizarRegistros();
				break;
			case 3:
				calcularMetricas();
				break;
			case 4:
				System.out.println("Hasta luego...");
				break;
			default:
				System.out.println("Error: debe ingresar un numero entre 1 y 4. Intente nuevamente.");
			}
		} while (opcion != 4);
	}

	// Muestra el submenu de metricas y se repite hasta que el usuario elige Volver
	private static void calcularMetricas() {
		int opcion = 0;

		do {
			System.out.println();
			System.out.println("--------------------------------------------");
			System.out.println("[1] Choripanes comidos en total");
			System.out.println("[2] Terremotos tomados en total");
			System.out.println("[3] Choripanes comidos por usuario");
			System.out.println("[4] Terremotos tomados por usuario");
			System.out.println("[5] KCal consumidas por usuario");
			System.out.println("[6] Dias ebrio por usuario");
			System.out.println("[7] Volver al menu principal");
			System.out.println("--------------------------------------------");
			System.out.print("Seleccione una opcion: ");

			try {
				opcion = Integer.parseInt(input.nextLine());
			} catch (NumberFormatException e) {
				opcion = 0;
			}

			switch (opcion) {
			case 1:
				totalChoripanes();
				break;
			case 2:
				totalTerremotos();
				break;
			case 3:
				choripanesPorUsuario();
				break;
			case 4:
				System.out.println("Terremotos por usuario (en construccion)");
				break;
			case 5:
				System.out.println("KCal por usuario (en construccion)");
				break;
			case 6:
				System.out.println("Dias ebrio por usuario (en construccion)");
				break;
			//por temas de tiempo las opciones 4,5 7 6 quedaran incompletas, lo siento a quien revise esto...
			case 7:
				break;
			default:
				System.out.println("Error: debe ingresar un numero entre 1 y 7. Intente nuevamente.");
			}
		} while (opcion != 7);
	}

	// Muestra cuantos choripanes se han comido en total
	private static void totalChoripanes() {
		System.out.println("Total de choripanes comidos: " + cantidadChoripanes);
	}

	// Muestra cuantos terremotos se han tomado en total y cuantos litros suman
	private static void totalTerremotos() {
		double totalLitros = 0;

		for (int i = 0; i < cantidadTerremotos; i++) {
			totalLitros = totalLitros + listaTerremotos[i].getLitros();
		}

		System.out.println("Total de terremotos tomados: " + cantidadTerremotos + " ("
				+ Math.round(totalLitros * 100) / 100.0 + " litros en total)");
	}

	// Muestra cuantos choripanes comio cada participante
	private static void choripanesPorUsuario() {
		System.out.println();
		System.out.println("Usuario | Cantidad");
		System.out.println("--------------------------------------------");

		for (int i = 0; i < cantidadParticipantes; i++) {
			int cantidad = 0;

			for (int j = 0; j < cantidadChoripanes; j++) {
				if (listaChoripanes[j].getConsumidor().equalsIgnoreCase(listaParticipantes[i])) {
					cantidad++;
				}
			}

			System.out.println(listaParticipantes[i] + " | " + cantidad);
		}
	}

	// Pregunta si se elimina un choripan o un terremoto
	private static void eliminarRegistro() {
		int tipo = 0;

		System.out.println("[1] Choripan");
		System.out.println("[2] Terremoto");
		System.out.println("[3] Cancelar");
		System.out.print("Que desea eliminar: ");

		try {
			tipo = Integer.parseInt(input.nextLine());
		} catch (NumberFormatException e) {
			tipo = 0;
		}

		switch (tipo) {
		case 1:
			eliminarChoripan();
			break;
		case 2:
			eliminarTerremoto();
			break;
		case 3:
			break;
		default:
			System.out.println("Error: debe ingresar un numero entre 1 y 3.");
		}
	}

	// Muestra los choripanes, pide el numero de uno y lo elimina de la lista
	private static void eliminarChoripan() {

		if (cantidadChoripanes == 0) {
			System.out.println("Error: no hay choripanes registrados.");
			return;
		}

		mostrarChoripanes();

		try {
			System.out.print("Numero del choripan a eliminar (0 para cancelar): ");
			int numero = Integer.parseInt(input.nextLine());

			if (numero == 0) {
				System.out.println("Eliminacion cancelada.");
				return;
			}

			if (numero < 1 || numero > cantidadChoripanes) {
				System.out.println("Error: el numero debe estar entre 1 y " + cantidadChoripanes + ".");
				return;
			}

			// Guarda el choripan para mostrar cual se elimino
			Choripan eliminado = listaChoripanes[numero - 1];

			// Pide confirmacion antes de borrar
			System.out.print("Confirma la eliminacion de \"Choripan | " + eliminado.getConsumidor() + " | "
					+ eliminado.getLargo() + " cm x " + eliminado.getAncho()
					+ " cm | " + eliminado.getFecha() + "\"? (s/n): ");
			String respuesta = input.nextLine();

			if (!respuesta.equalsIgnoreCase("s")) {
				System.out.println("Eliminacion cancelada.");
				return;
			}

			// Corre los choripanes siguientes una posicion hacia atras para tapar el espacio
			for (int i = numero - 1; i < cantidadChoripanes - 1; i++) {
				listaChoripanes[i] = listaChoripanes[i + 1];
			}
			listaChoripanes[cantidadChoripanes - 1] = null;
			cantidadChoripanes--;

			guardarConsumido();
			System.out.println("Choripan eliminado: " + eliminado.getConsumidor() + " | "
					+ eliminado.getLargo() + " cm x " + eliminado.getAncho()
					+ " cm | " + eliminado.getFecha());

		} catch (NumberFormatException e) {
			System.out.println("Error: debe ingresar un numero.");
		}
	}

	// Muestra los terremotos, pide el numero de uno y lo elimina de la lista
	private static void eliminarTerremoto() {

		if (cantidadTerremotos == 0) {
			System.out.println("Error: no hay terremotos registrados.");
			return;
		}

		mostrarTerremotos();

		try {
			System.out.print("Numero del terremoto a eliminar (0 para cancelar): ");
			int numero = Integer.parseInt(input.nextLine());

			if (numero == 0) {
				System.out.println("Eliminacion cancelada.");
				return;
			}

			if (numero < 1 || numero > cantidadTerremotos) {
				System.out.println("Error: el numero debe estar entre 1 y " + cantidadTerremotos + ".");
				return;
			}

			// Guarda el terremoto para mostrar cual se elimino
			Terremoto eliminado = listaTerremotos[numero - 1];

			// Pide confirmacion antes de borrar
			System.out.print("Confirma la eliminacion de \"Terremoto | " + eliminado.getConsumidor() + " | "
					+ eliminado.getLitros() + " L | " + eliminado.getFecha() + "\"? (s/n): ");
			String respuesta = input.nextLine();

			if (!respuesta.equalsIgnoreCase("s")) {
				System.out.println("Eliminacion cancelada.");
				return;
			}

			// Corre los terremotos siguientes una posicion hacia atras para tapar el espacio
			for (int i = numero - 1; i < cantidadTerremotos - 1; i++) {
				listaTerremotos[i] = listaTerremotos[i + 1];
			}
			listaTerremotos[cantidadTerremotos - 1] = null;
			cantidadTerremotos--;

			guardarConsumido();
			System.out.println("Terremoto eliminado: " + eliminado.getConsumidor() + " | "
					+ eliminado.getLitros() + " L | " + eliminado.getFecha());

		} catch (NumberFormatException e) {
			System.out.println("Error: debe ingresar un numero.");
		}
	}

	// Muestra el submenu de ver registros y se repite hasta que el usuario elige Volver
	private static void verRegistros() {
		int opcion = 0;

		do {
			System.out.println();
			System.out.println("--------------------------------------------");
			System.out.println("[1] Choripanes");
			System.out.println("[2] Terremotos");
			System.out.println("[3] Volver al menu principal");
			System.out.println("--------------------------------------------");
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

	// Muestra la tabla con todos los choripanes registrados
	private static void mostrarChoripanes() {
		System.out.println();
		System.out.println("# | Consumidor | Largo | Ancho | Fecha | KCal");
		System.out.println("--------------------------------------------");

		for (int i = 0; i < cantidadChoripanes; i++) {
			Choripan c = listaChoripanes[i];
			System.out.println((i + 1) + " | " + c.getConsumidor() + " | " + Math.round(c.getLargo() * 100) / 100.0
					+ " cm | " + Math.round(c.getAncho() * 100) / 100.0 + " cm | " + c.getFecha() + " | "
					+ Math.round(c.calcularKcal() * 100) / 100.0);
		}

		System.out.println("--------------------------------------------");
		System.out.println("Total: " + cantidadChoripanes + " choripanes registrados");
	}

	// Muestra la tabla con todos los terremotos registrados
	private static void mostrarTerremotos() {
		double totalLitros = 0;

		System.out.println();
		System.out.println("# | Consumidor | Litros | Fecha | KCal");
		System.out.println("--------------------------------------------");

		for (int i = 0; i < cantidadTerremotos; i++) {
			Terremoto t = listaTerremotos[i];
			totalLitros = totalLitros + t.getLitros();
			System.out.println((i + 1) + " | " + t.getConsumidor() + " | " + Math.round(t.getLitros() * 100) / 100.0
					+ " L | " + t.getFecha() + " | " + Math.round(t.calcularKcal() * 100) / 100.0);
		}

		System.out.println("--------------------------------------------");
		System.out.println("Total: " + cantidadTerremotos + " terremotos registrados (" + Math.round(totalLitros * 100) / 100.0 + " L)");
	}

	// Muestra el submenu de actualizar y se repite hasta que el usuario elige Volver
	private static void actualizarRegistros() {
		int opcion = 0;

		do {
			System.out.println();
			System.out.println("--------------------------------------------");
			System.out.println("[1] Agregar registro");
			System.out.println("[2] Editar registro");
			System.out.println("[3] Eliminar registro");
			System.out.println("[4] Volver al menu principal");
			System.out.println("--------------------------------------------");
			System.out.print("Seleccione una opcion: ");

			try {
				opcion = Integer.parseInt(input.nextLine());
			} catch (NumberFormatException e) {
				opcion = 0;
			}

			switch (opcion) {
			case 1:
				agregarRegistro();
				break;
			case 2:
				editarRegistro();
				break;
			case 3:
				eliminarRegistro();
				break;
			case 4:
				break;
			default:
				System.out.println("Error: debe ingresar un numero entre 1 y 4. Intente nuevamente.");
			}
		} while (opcion != 4);
	}

	// Pregunta si se edita un choripan o un terremoto
	private static void editarRegistro() {
		int tipo = 0;

		System.out.println("[1] Choripan");
		System.out.println("[2] Terremoto");
		System.out.print("Que desea editar: ");

		try {
			tipo = Integer.parseInt(input.nextLine());
		} catch (NumberFormatException e) {
			tipo = 0;
		}

		switch (tipo) {
		case 1:
			editarChoripan();
			break;
		case 2:
			editarTerremoto();
			break;
		default:
			System.out.println("Error: debe ingresar 1 o 2.");
		}
	}

	// Muestra los choripanes, pide el numero de uno y le cambia todos sus datos
	private static void editarChoripan() {

		if (cantidadChoripanes == 0) {
			System.out.println("Error: no hay choripanes registrados.");
			return;
		}

		mostrarChoripanes();

		try {
			System.out.print("Numero del choripan a editar: ");
			int numero = Integer.parseInt(input.nextLine());

			if (numero < 1 || numero > cantidadChoripanes) {
				System.out.println("Error: el numero debe estar entre 1 y " + cantidadChoripanes + ".");
				return;
			}

			System.out.print("Nuevo consumidor: ");
			String consumidor = input.nextLine();

			if (consumidor.equals("")) {
				System.out.println("Error: el consumidor no puede estar vacio.");
				return;
			}

			System.out.print("Nuevo largo (cm): ");
			double largo = Double.parseDouble(input.nextLine());
			System.out.print("Nuevo ancho (cm): ");
			double ancho = Double.parseDouble(input.nextLine());

			if (largo <= 0 || ancho <= 0) {
				System.out.println("Error: las medidas deben ser mayores a 0.");
				return;
			}

			System.out.print("Nueva fecha: ");
			String fecha = input.nextLine();

			Choripan c = listaChoripanes[numero - 1];
			c.setConsumidor(consumidor);
			c.setLargo(largo);
			c.setAncho(ancho);
			c.setFecha(fecha);

			agregarParticipante(consumidor);
			guardarConsumido();
			System.out.println("Choripan editado.");

		} catch (NumberFormatException e) {
			System.out.println("Error: debe ingresar un numero.");
		}
	}

	// Muestra los terremotos, pide el numero de uno y le cambia todos sus datos
	private static void editarTerremoto() {

		if (cantidadTerremotos == 0) {
			System.out.println("Error: no hay terremotos registrados.");
			return;
		}

		mostrarTerremotos();

		try {
			System.out.print("Numero del terremoto a editar: ");
			int numero = Integer.parseInt(input.nextLine());

			if (numero < 1 || numero > cantidadTerremotos) {
				System.out.println("Error: el numero debe estar entre 1 y " + cantidadTerremotos + ".");
				return;
			}

			System.out.print("Nuevo consumidor: ");
			String consumidor = input.nextLine();

			if (consumidor.equals("")) {
				System.out.println("Error: el consumidor no puede estar vacio.");
				return;
			}

			System.out.print("Nuevos litros: ");
			double litros = Double.parseDouble(input.nextLine());

			if (litros <= 0) {
				System.out.println("Error: los litros deben ser mayores a 0.");
				return;
			}

			System.out.print("Nueva fecha: ");
			String fecha = input.nextLine();

			Terremoto t = listaTerremotos[numero - 1];
			t.setConsumidor(consumidor);
			t.setLitros(litros);
			t.setFecha(fecha);

			agregarParticipante(consumidor);
			guardarConsumido();
			System.out.println("Terremoto editado.");

		} catch (NumberFormatException e) {
			System.out.println("Error: debe ingresar un numero.");
		}
	}

	// Pregunta si se agrega un choripan o un terremoto
	private static void agregarRegistro() {
		int tipo = 0;

		System.out.println("[1] Choripan");
		System.out.println("[2] Terremoto");
		System.out.print("Que desea agregar: ");

		try {
			tipo = Integer.parseInt(input.nextLine());
		} catch (NumberFormatException e) {
			tipo = 0;
		}

		switch (tipo) {
		case 1:
			agregarChoripan();
			break;
		case 2:
			agregarTerremoto();
			break;
		default:
			System.out.println("Error: debe ingresar 1 o 2.");
		}
	}

	// Pide los datos de un choripan, lo agrega a la lista y guarda el archivo
	private static void agregarChoripan() {

		if (cantidadChoripanes >= 200) {
			System.out.println("Error: no hay espacio para mas choripanes.");
			return;
		}

		System.out.print("Consumidor: ");
		String consumidor = input.nextLine();

		if (consumidor.equals("")) {
			System.out.println("Error: el consumidor no puede estar vacio.");
			return;
		}

		try {
			System.out.print("Largo (cm): ");
			double largo = Double.parseDouble(input.nextLine());
			System.out.print("Ancho (cm): ");
			double ancho = Double.parseDouble(input.nextLine());

			if (largo <= 0 || ancho <= 0) {
				System.out.println("Error: las medidas deben ser mayores a 0.");
				return;
			}

			System.out.print("Fecha: ");
			String fecha = input.nextLine();

			agregarParticipante(consumidor);
			listaChoripanes[cantidadChoripanes] = new Choripan(consumidor, largo, ancho, fecha);
			cantidadChoripanes++;
			guardarConsumido();
			System.out.println("Choripan agregado.");

		} catch (NumberFormatException e) {
			System.out.println("Error: debe ingresar un numero.");
		}
	}

	// Pide los datos de un terremoto, lo agrega a la lista y guarda el archivo
	private static void agregarTerremoto() {

		if (cantidadTerremotos >= 200) {
			System.out.println("Error: no hay espacio para mas terremotos.");
			return;
		}

		System.out.print("Consumidor: ");
		String consumidor = input.nextLine();

		if (consumidor.equals("")) {
			System.out.println("Error: el consumidor no puede estar vacio.");
			return;
		}

		try {
			System.out.print("Litros: ");
			double litros = Double.parseDouble(input.nextLine());

			if (litros <= 0) {
				System.out.println("Error: los litros deben ser mayores a 0.");
				return;
			}

			System.out.print("Fecha: ");
			String fecha = input.nextLine();

			agregarParticipante(consumidor);
			listaTerremotos[cantidadTerremotos] = new Terremoto(consumidor, litros, fecha);
			cantidadTerremotos++;
			guardarConsumido();
			System.out.println("Terremoto agregado.");

		} catch (NumberFormatException e) {
			System.out.println("Error: debe ingresar un numero.");
		}
	}

	// Lee Participantes.txt (un nombre por linea) y los guarda en listaParticipantes
	private static void leerParticipantes() {

		file = new File("Participantes.txt");

		try {
			s = new Scanner(file);

			while (s.hasNextLine()) {
				String linea = s.nextLine();

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

	// Guarda todos los consumos en Consumido.txt (primero los choripanes y luego los terremotos)
	private static void guardarConsumido() {

		try {
			BufferedWriter escritor = new BufferedWriter(new FileWriter("Consumido.txt"));

			for (int i = 0; i < cantidadChoripanes; i++) {
				Choripan c = listaChoripanes[i];
				escritor.write("Choripan;" + c.getConsumidor() + ";" + c.getLargo() + ";"
						+ c.getAncho() + ";" + c.getFecha());
				escritor.newLine();
			}

			for (int i = 0; i < cantidadTerremotos; i++) {
				Terremoto t = listaTerremotos[i];
				escritor.write("Terremoto;" + t.getConsumidor() + ";" + t.getLitros() + ";" + t.getFecha());
				escritor.newLine();
			}

			escritor.close();

		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo 'Consumido.txt'.");
		}
	}



}