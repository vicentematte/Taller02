#Taller 2 POO - La Fonda del Papu

Programa en Java, hecho con Programación Orientada a Objetos, que lleva el registro de los choripanes y terremotos consumidos por un grupo de amigos. 
Permite ver, agregar, editar y eliminar registros, y los cambios quedan guardados en un archivo txt.

Integrantes: 
  - Vicente Matte Barraza - 21.753.089-K - ITI

Descripcion:
  El programa lee dos archivos de entrada:

1. Participantes.txt: una persona por línea.
2. Consumido.txt: un registro por línea, con dos formatos posibles:
  Choripan;Consumidor;Largo;Ancho;Fecha
  Terremoto;Consumidor;Litros;Fecha

Menu: 
Ver registros
    Choripanes
    Terremotos
    Volver al menu principal
Actualizar registros
    Agregar
    Editar
    Eliminar
    Volver al menu principal
Calcular metricas (incompleto)
Salir
  - Ver registros: muestra en una tabla todos los choripanes o terremotos con su kcal.
  - Actualizar registros: permite agregar, editar y eliminar (con confirmación). Cada cambio se guarda en Consumido.txt.
  - Calcular metricas: Deberia permitir ver las 6 metricas solicitadas.

Formulas:
Consumo	Kcal
Choripán	(Largo * Ancho) / 3
Terremoto	(Litros * 10) / 3

## Estructura del proyecto

```
Taller2/
├── src/
│   └── Taller02/
│       ├── Main.java        (menú, lectura y guardado de archivos)
│       ├── Choripan.java    (clase del dominio)
│       └── Terremoto.java   (clase del dominio)
├── Participantes.txt
├── Consumido.txt
├── Diagrama_UML_Taller2.pdf
└── README.md
```

