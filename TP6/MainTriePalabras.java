package TP6;
import java.util.Arrays;

public class MainTriePalabras {
    public static void main(String[] args) {
        String[] palabras = {
                "xml", "xmls", "xhtml", "xsl",
                "py", "pyw", "pyi", "Python",
                "pip", "php", "perl"
        };

        Trie trie = new Trie();

        System.out.println("=== Prueba de Trie ===");
        System.out.println("Insertando palabras:");
        for (String palabra : palabras) {
            trie.insertarPalabra(palabra);
            System.out.println("  insertarPalabra(\"" + palabra + "\")");
        }

        System.out.println();
        System.out.println("Palabras insertadas: " + Arrays.toString(palabras));
        System.out.println("contarPalabras(): " + trie.contarPalabras());
        System.out.println("listarPalabras(): " + trie.listarPalabras());

        System.out.println();
        System.out.println("Busquedas exactas:");
        String[] busquedas = {
                "xml", "xmls", "xhtml", "xsl", "py", "pyw", "pyi",
                "Python", "PYTHON", "pip", "php", "perl",
                "p", "x", "xmlt", "pyx", "perlita", "java", ""
        };
        for (String palabra : busquedas) {
            System.out.println("  buscarPalabra(\"" + palabra + "\") -> "
                    + trie.buscarPalabra(palabra));
        }

        System.out.println();
        System.out.println("Palabras con prefijo:");
        String[] prefijos = {"x", "xml", "xht", "p", "py", "php", "per", "z", ""};
        for (String prefijo : prefijos) {
            System.out.println("  palabrasConPrefijo(\"" + prefijo + "\") -> "
                    + trie.palabrasConPrefijo(prefijo));
        }

        System.out.println();
        System.out.println("Palabras similares:");
        String[] similares = {
                "xml", "xmls", "xhtml", "py", "python",
                "p", "x", "pyt", "pyx", "px", "z", "p1"
        };
        for (String palabra : similares) {
            System.out.println("  buscarPalabrasSimilares(\"" + palabra + "\") -> "
                    + trie.buscarPalabrasSimilares(palabra));
        }

        System.out.println();
        System.out.println("contadorPrefijos(): " + trie.contadorPrefijos());
        System.out.println("listarPrefijos(): " + trie.listarPrefijos());
        System.out.println("contarNodos(): " + trie.contarNodos());
    }
}
