
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TablaHash {

    private int tableSize = 120;
    private int[] tabla;
    private int elements = 0;
    private int comparaciones = 0;
    private int intercambios = 0;

    public TablaHash() {
        this.tabla = new int[this.tableSize];
        // Inicializamos la hash table con un entero especial que representa los
        // espacios vacios (Valor centinela)
        for (int i = 0; i < this.tableSize; i++) {
            this.tabla[i] = -1;
        }
    }


    
    private void redimensionar() {
        int[] tablaVieja = this.tabla;
        int tamañoviejo = this.tableSize;

        this.tableSize = this.siguientePrimo(tamañoviejo * 2);
        this.tabla = new int[this.tableSize];
        Arrays.fill(this.tabla, -1);
        this.elements = 0;

        for (int clave : tablaVieja) {
            this.comparaciones++; // clave != -1
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

    private boolean esPrimo(int n) {
        this.comparaciones++; // n < 2
        if (n < 2) {
            return false;
        }
        for (int d = 2; d * d <= n; d++) {
            this.comparaciones++; // condición d*d <= n de esta iteración
            this.comparaciones++; // n % d == 0
            if (n % d == 0) {
                return false;
            }
        }
        this.comparaciones++; // condición d*d <= n final (corta el for)
        return true;
    }

    private int reHashingSecuencia(int place) {
        place++;
        this.comparaciones++; // place == tableSize - 1
        if (place == this.tableSize - 1)
            place = 0; // tratamiento circular
        return place;

    }
    // Hash para Punto 3B)
    private int hashDivisiónModulo(int key) {
        return key % tableSize;
    }
    
    private boolean isFull() {
        return this.elements / tableSize >= 0.7;
    }

    public void insert(int key) {
        int index = this.hashDivisiónModulo(key);
        this.comparaciones++; // chequeo de isFull (elements/tableSize >= 0.7)
        if (isFull()) {
            System.out.println("Factor de carga alto, se necesita redimensionar");
            this.redimensionar();
        }
        while (this.tabla[index] != -1) {
            this.comparaciones++; // casilla ocupada detectada
            index = this.reHashingSecuencia(index);
        }
        this.comparaciones++; // casilla libre encontrada (corta el while)

        this.tabla[index] = key;
        this.intercambios++; // operación de escritura de la clave
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

    public void mostrarContadores() {
        System.out.println("Comparaciones: " + this.comparaciones);
        System.out.println("Cantidad de escrituras (intercambios): " + this.intercambios);
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
        for (int i = 0; i < 100; i++) {
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
            tabla.mostrarHashTable();;
            tabla.mostrarContadores();



            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
