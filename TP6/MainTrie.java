import java.util.List;
import java.util.Scanner;

public class MainTrie{
    public static void main(String[] args) {
        Trie arbol = new Trie();
        
        arbol.insertarPalabra("algorithm");
        arbol.insertarPalabra("animation");
        arbol.insertarPalabra("ant");
        arbol.insertarPalabra("apache");
        arbol.insertarPalabra("append");
        arbol.insertarPalabra("apple");
        arbol.insertarPalabra("application");
        arbol.insertarPalabra("approach");
        //A)
        System.out.println("a) La cantidad de palabras insetadas es de "+arbol.contarPalabras());
        //B)
        System.out.println("B) esta es la lista de todas las palabras insertadas:");
        List<String> list1 = arbol.listarPalabras();
        for (String string : list1) {
            System.out.println(string);
        }
        //C)
        Scanner scanner = new Scanner(System.in);
        System.out.println("C) Ingrese un prefijo");
        String prefijo = scanner.nextLine();
        System.out.println("esta es la lista de las palabras que comienzan con "+prefijo+":");
        List<String> list2 = arbol.palabrasConPrefijo(prefijo);
        for (String string : list2) {
            System.out.println(string);
        }
        //D
        System.out.println("D) Ingrese una palabra");
        String palabra = scanner.nextLine();
        System.out.println("esta es la lista de las palabras similares a "+palabra+":");
        List<String> list3 = arbol.palabrasConPrefijo(palabra);
        for (String string : list3) {
            System.out.println(string);
        }
        scanner.close();
        //E)
        System.out.println("E) La cantidad de prefijos existentes es de "+arbol.contadorPrefijos());
        


    }
}