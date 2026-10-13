// private int reHashingCuadrado(int place, int cont) {
//     place = place + (cont * cont) % this.tableSize;
//     while (place >= this.tableSize)
//         place = 0 + (place - this.tableSize); // tratamiento circular
//     return place;

// }

// // Hash para Punto 3C)
// private int hashMedioCuadrado(int key) {
//     int cuadrado = (int) Math.pow(key, 2);    
//     String aux = String.valueOf(cuadrado);
//     int particion = (int) aux.length() / 2;
//     String[] digitos = aux.split("");
//     System.out.println(
//             "Esta es la key " + key + " Esta es el cuadrdo " + cuadrado + " y esta es la particion " + particion);    

//     if (digitos.length % 2 == 0) {
//         if (digitos[particion] == "0") {
//             return Integer.parseInt(digitos[particion + 1]);    
//         } else {
//             return Integer.parseInt(digitos[particion] + digitos[particion + 1]);    
//         }
//     } else {
//         return Integer.parseInt(digitos[particion + 1]);    
//     }

// }
// private int reHashingSecuencia(int place) {
    //     place++;
    //     if (place >= this.tableSize)
    //         place = 0 + (place - this.tableSize); // tratamiento circular
//     return place;    

// }

// // Rehashing para punto 4B
package TP3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TablaHash {

    private int tableSize = 101;
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


    // Método Hash para Punto 4
    private int hashPunto4(int key) {
        int newIndex = (7 * key + 1) % 10;

        while (newIndex >= this.tableSize) {
            newIndex = 0 + (newIndex - this.tableSize);
        }    
        
        return newIndex;
    }    
    // Segundo hash para el punto 4C
    private int hashDivisiónModulo(int key) {
        int newIndex = key % this.tableSize; 
        return newIndex;
    }    


    private boolean isFull() {
        return (double) this.elements / tableSize >= 0.7;
    }

    public void insert(int key) {
        int cont = 1; //Contador para incrementar los saltos
        int h1 = this.hashPunto4(key);
        int jump = h1;
        if (isFull()) {
            System.out.println("Factor de carga alto, se necesita redimensionar");
            this.redimensionar();
        }
        while (this.tabla[jump] != -1) {
            this.comparaciones++; // casilla ocupada detectada
            int h2 = 1 + (hashDivisiónModulo(key) % (tableSize - 1));  // salto ∈ 1..100, para evitar que el salto sea multiplo de TableSize o 0
            //Doble direccionamiento Hash
            jump = h1 + h2 * cont ;
            while (jump >= this.tableSize)
                   jump = 0 + (jump - this.tableSize); // tratamiento circular
            cont++;

        }
        this.comparaciones++; // casilla libre encontrada (corta el while)

        this.tabla[jump] = key;
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
            tabla.mostrarHashTable();
            ;
            tabla.mostrarContadores();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
