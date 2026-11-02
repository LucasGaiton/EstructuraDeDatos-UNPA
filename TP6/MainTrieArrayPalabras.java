package TP6;
import java.util.Arrays;

public class MainTrieArrayPalabras {
    public static void main(String[] args) {
        String[] palabras = {
                "xml", "xmls", "xhtml", "xsl",
                "py", "pyw", "pyi", "Python",
                "pip", "php", "perl"
        };

        TrieArray trie = new TrieArray();

        System.out.println("=== Prueba de TrieArray ===");
        System.out.println("Insertando palabras:");
        for (String palabra : palabras) {
            trie.insertWord(palabra);
            System.out.println("  insertWord(\"" + palabra + "\")");
        }

        System.out.println();
        System.out.println("Palabras insertadas: " + Arrays.toString(palabras));

        System.out.println();
        System.out.println("Busquedas exactas:");
        String[] busquedas = {
                "xml", "xmls", "xhtml", "xsl", "py", "pyw", "pyi",
                "Python", "PYTHON", "pip", "php", "perl",
                "p", "x", "xmlt", "pyx", "perlita", "java", ""
        };
        for (String palabra : busquedas) {
            System.out.println("  searchWord(\"" + palabra + "\") -> "
                    + trie.searchWord(palabra));
        }

        System.out.println();
        System.out.println("Nota: TrieArray solo implementa insertWord y searchWord.");
    }
}
