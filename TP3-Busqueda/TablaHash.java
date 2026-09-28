
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TablaHash {

    private  int tableSize = 14;
    private int[] tabla;

    public TablaHash() {
        this.tabla = new int[this.tableSize];
        // Inicializamos la hash table con un entero especial que representa los
        // espacios vacios
        for (int i = 0; i < this.tableSize; i++) {
            this.tabla[i] = -1;
        }
    }

    // Solo modificamos el hash
    private int hash2(int key) {
        System.out.println("Esta es la key " + key + " esta es el resultado del hash " + (7 * key + 1) % 10);
        return (7 * key + 1) % tableSize;
    }

    // Hash para punto1
    private int hash(int key) {
        return key % tableSize;
    }

    private int rehashingCuadratico(int base, int indice) {
        int nuevoIndice = base + (int) Math.round(Math.pow(indice, 2.0));
        if (nuevoIndice > this.tableSize) {
            int nuevoTamano = siguientePrimo(this.tableSize * 2);
            System.out.println("  El hash no tuvo exito -> redimensiono la tabla de "
                    + this.tableSize + " a " + nuevoTamano);
            this.redimensionar(nuevoTamano);

        }

        return nuevoIndice;

    }

    private void redimensionar(int nuevoTamano) {
        int[] tablaVieja = this.tabla;

        this.tabla = new int[nuevoTamano];
        this.tableSize = nuevoTamano;
        Arrays.fill(this.tabla, -1);

        System.out.println("  Reubico las claves que ya estaban guardadas:");

        for (int clave : tablaVieja) {
            if (clave != -1) {
                System.out.println("  Reubico la clave " + clave + ":");
                this.insert(clave);
            }
        }
    }

    private static int siguientePrimo(int n) {
        int candidato = n;
        while (!esPrimo(candidato)) {
            candidato++;
        }
        return candidato;
    }

    private static boolean esPrimo(int n) {
        if (n < 2) {
            return false;
        }
        for (int d = 2; d * d <= n; d++) {
            if (n % d == 0) {
                return false;
            }
        }
        return true;
    }

    public void insert(int key) {
        int index = this.hash(key);
        // Este es el framento de codigo que hay que modificar si se quiere modificar el
        // rehashing
        int cont = 1;

        while (this.tabla[index] != -1) {
            index = this.rehashingCuadratico(index, cont);

            cont++;
        }

        this.tabla[index] = key;
    }

    public void mostrarHashTable() {
        System.out.println("Tabla Hash:");
        for (int i = 0; i < this.tableSize; i++) {
            if (this.tabla[i] != -1) {
                System.out.println("Índice " + i + ": " + this.tabla[i]);
            } else {
                System.out.println("Índice " + i + ": vacío");
            }
        }
    }

    public void cargarNumsArchivo(String fileName) throws IOException {
        File archivo = new File(fileName);
        Scanner scan = new Scanner(archivo);
        while (scan.hasNextInt()) {
            int num = scan.nextInt();
            this.insert(num);
        }
        scan.close();
    }

    public static void archivoNumerosAleatorios(String fileName) throws IOException {
        Random random = new Random();
        FileWriter writer = new FileWriter(fileName);
        for (int i = 0; i < 6; i++) {
            int num = 1000 + random.nextInt(9000);
            writer.write(num + "\n");
        }
        writer.close();
        System.out.println("Archivo creado");
    }

    public static void main(String[] args) {
        try {
            TablaHash tabla = new TablaHash();
            tabla.insert(125);
            tabla.insert(228);
            tabla.insert(172);
            tabla.insert(264);
            tabla.insert(156);
            tabla.insert(161);
            tabla.insert(358);
            tabla.insert(479);
            tabla.insert(288);
            tabla.insert(110);
            tabla.insert(347);
            tabla.insert(253);
            tabla.insert(217);
            tabla.insert(368);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
