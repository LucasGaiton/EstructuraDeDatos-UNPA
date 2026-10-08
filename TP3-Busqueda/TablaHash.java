
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TablaHash {

    private int tableSize = 10;
    private int[] tabla;
    private int elements = 0;

    public TablaHash() {
        this.tabla = new int[this.tableSize];
        // Inicializamos la hash table con un entero especial que representa los
        // espacios vacios (Valor centinela)
        for (int i = 0; i < this.tableSize; i++) {
            this.tabla[i] = -1;
        }
    }


    // Hash para punto1
    private int hashDivisiónModulo(int key) {
        return key % tableSize;
    }

    private int rehashingCuadratico(int base, int indice) {
        int nuevoIndice = base + (int) Math.round(Math.pow(indice, 2.0));
        if (nuevoIndice > this.tableSize) {
            int nuevoTamano = siguientePrimo(this.tableSize * 2);
            System.out.println("  El hash no tuvo exito -> redimensiono la tabla de "
                    + this.tableSize + " a " + nuevoTamano);
           // this.redimensionar(nuevoTamano);

        }

        return nuevoIndice;

    }

    private void redimensionar() {
        int[] tablaVieja = this.tabla;
        int tamañoviejo = this.tableSize;

        this.tableSize = this.siguientePrimo(tamañoviejo * 2);
        this.tabla = new int[this.tableSize];
        Arrays.fill(this.tabla, -1);
        this.elements = 0;

        for (int clave : tablaVieja) {
            if (clave != -1) {
                System.out.println("  Reubico la clave " + clave + ":");
                this.insert(clave);
            }
        }
    }

    private int siguientePrimo(int n) {
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

    private int reHashingSecuencia(int place) {
        place++;
        if (place == this.tableSize - 1)
            place = 0; // tratamiento circular
        return place;

    }

    private boolean isFull() {
        return this.elements / tableSize >= 0.7;
    }

    public void insert(int key) {
        int index = this.hashDivisiónModulo(key);
        // Este es el framento de codigo que hay que modificar si se quiere modificar el
        // rehashing
        if (isFull()) {
            System.out.println("Factor de carga alto, se necesita redimensionar");
            this.redimensionar();
        }
        while (this.tabla[index] != -1) {
            index = this.reHashingSecuencia(index);
        }

        this.tabla[index] = key;
        this.elements++;
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
            String nombreArchivo = "miArchivo";
            archivoNumerosAleatorios(nombreArchivo);
            tabla.cargarNumsArchivo(nombreArchivo);



            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
