
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TablaHash {

    private final int tableSize = 10;
    private int[] tabla;

    public TablaHash() {
        this.tabla = new int[this.tableSize];
        //Inicializamos la hash table con un entero especial que representa los espacios vacios 
        for (int i = 0; i < this.tableSize; i++) {
            this.tabla[i] = -1;
        }
    }

    //Solo modificamos el hash
    private int hash(int key) {
        System.out.println("Esta es la key "+key+" esta es el resultado del hash "+(7 * key + 1) % 10);
       return (7 * key + 1) % 10;
    }
    private int hash2(int key){
        return key%this.tableSize;
    }

    public void insert(int key) {
        int index = this.hash(key); 
        //Este es el framento de codigo que hay que modificar si se quiere modificar el rehashing
        // int cont = 1;

        do{
            if(this.tabla[index] != -1){
                index = this.hash2(key);
                while(this.tabla[index] != -1){
                    index++;
                }
            }
        }while(this.tabla[index] != -1 );

        // while (this.tabla[index] != -1 ) {
        //     index = this.hash2(key);
        // }


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
        for (int i = 0; i <6; i++) {
            int num = 1000 + random.nextInt(9000);
            writer.write(num + "\n");
        }
        writer.close();
        System.out.println("Archivo creado");
    }

    public static void main(String[] args) {
        try {
            String nombre = "nombreDelArchivo";
            archivoNumerosAleatorios(nombre);
            TablaHash tabla = new TablaHash();
            tabla.cargarNumsArchivo(nombre);
            tabla.mostrarHashTable();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
