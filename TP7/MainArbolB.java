package TP7;

import java.util.Scanner;

public class MainArbolB {
    private static final int ARIDAD_POR_DEFECTO = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArbolB arbol = new ArbolB(ARIDAD_POR_DEFECTO);

        System.out.println("Arbol B inicializado con aridad " + ARIDAD_POR_DEFECTO);

        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("--- Arbol B: opciones ---");
            System.out.println("1. Crear/reiniciar arbol");
            System.out.println("2. Insertar clave");
            System.out.println("3. Borrar clave");
            System.out.println("4. Buscar clave");
            System.out.println("5. Mostrar arbol");
            System.out.println("6. Mostrar estadisticas");
            System.out.println("7. Prueba automatica con aridad 5");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = leerEntero(scanner, "");

            switch (opcion) {
                case 1:
                    arbol = crearArbol(scanner);
                    break;
                case 2:
                    System.out.print("Ingrese la clave a insertar: ");
                    int claveInsertar = leerEntero(scanner, "");
                    arbol.insertar(claveInsertar);
                    System.out.println("Clave " + claveInsertar + " insertada.");
                    break;
                case 3:
                    System.out.print("Ingrese la clave a borrar: ");
                    int claveBorrar = leerEntero(scanner, "");
                    arbol.borrarClave(claveBorrar);
                    break;
                case 4:
                    System.out.print("Ingrese la clave a buscar: ");
                    int claveBuscar = leerEntero(scanner, "");
                    if (arbol.buscar(claveBuscar)) {
                        System.out.println("La clave " + claveBuscar + " existe en el arbol.");
                    } else {
                        System.out.println("La clave " + claveBuscar + " NO existe en el arbol.");
                    }
                    break;
                case 5:
                    if (arbol.contarNodos() == 0) {
                        System.out.println("El arbol esta vacio.");
                    } else {
                        arbol.mostrar();
                    }
                    break;
                case 6:
                    mostrarEstadisticas(arbol);
                    break;
                case 7:
                    arbol = pruebaAutomatica();
                    break;
                case 8:
                    salir = true;
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        }

        scanner.close();
    }

    private static ArbolB crearArbol(Scanner scanner) {
        System.out.print("Ingrese la aridad (minimo 3, por defecto " + ARIDAD_POR_DEFECTO + "): ");
        String entrada = scanner.nextLine().trim();

        int aridad = ARIDAD_POR_DEFECTO;
        if (!entrada.isEmpty()) {
            try {
                aridad = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Aridad invalida. Se usara " + ARIDAD_POR_DEFECTO + ".");
                aridad = ARIDAD_POR_DEFECTO;
            }
        }

        if (aridad < 3) {
            System.out.println("La aridad debe ser al menos 3. Se usara " + ARIDAD_POR_DEFECTO + ".");
            aridad = ARIDAD_POR_DEFECTO;
        }

        ArbolB nuevo = new ArbolB(aridad);
        System.out.println("Arbol creado con aridad " + aridad + ".");
        return nuevo;
    }

    private static ArbolB pruebaAutomatica() {
        System.out.println();
        System.out.println("=== PRUEBA AUTOMATICA CON ARIDAD 5 ===");

        ArbolB arbol = new ArbolB(5);
        int[] claves = {190, 57, 89, 90, 121, 170, 35, 48, 91, 22, 126, 132, 80};

        System.out.print("Insertando: ");
        for (int clave : claves) {
            System.out.print(clave + " ");
            arbol.insertar(clave);
        }
        System.out.println();

        System.out.println();
        System.out.println("Arbol despues de insertar la secuencia:");
        arbol.mostrar();
        mostrarEstadisticas(arbol);

        System.out.println();
        System.out.println("Borrando la clave 91...");
        arbol.borrarClave(91);
        arbol.mostrar();
        mostrarEstadisticas(arbol);

        System.out.println();
        System.out.println("Borrando la clave 48...");
        arbol.borrarClave(48);
        arbol.mostrar();
        mostrarEstadisticas(arbol);

        return arbol;
    }

    private static void mostrarEstadisticas(ArbolB arbol) {
        System.out.println("Cantidad de claves: " + arbol.contarClaves());
        System.out.println("Cantidad de nodos: " + arbol.contarNodos());
        System.out.println("Altura: " + arbol.calcularAltura());
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        if (mensaje != null && !mensaje.isEmpty()) {
            System.out.print(mensaje);
        }

        while (true) {
            String linea = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero entero valido: ");
            }
        }
    }
}
