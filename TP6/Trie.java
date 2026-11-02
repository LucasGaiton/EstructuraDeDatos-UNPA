package TP6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Trie {
    TrieNode root;
    final int end = 26;
    final int begin = 0;

    public Trie() {
        root = null;
    }

    public int contarPalabras() {
        return contarPalabras(root);
    }

    // Métodos solicitados en el punto 3

    // a) Devolver la cantidad de palabras alamcenadas
    private int contarPalabras(TrieNode node) {
        if (node == null)
            return 0;

        int count = 0;
        if (node.getValueAt(begin) == node)
            count++; // Nodo marcado como final de palabra

        for (int i = 1; i <= end; i++) {
            count += contarPalabras(node.getValueAt(i));
        }
        return count;
    }

    // b) Listar todas las palabras alamcenadas
    public List<String> listarPalabras() {
        List<String> words = new ArrayList<>();
        listarPalabras(root, "", words);
        return words;
    }

    // c) Mostrar todas las palabras almacenadas, que comiencen con un determinado
    // prefijo
    public List<String> palabrasConPrefijo(String prefix) {
        List<String> words = new ArrayList<>();
        if (prefix == null)
            return words;

        String normalized = prefix.toLowerCase(Locale.ROOT);
        TrieNode node = root;

        for (int i = 0; i < normalized.length(); i++) {
            int pos = getPosition(normalized.charAt(i));
            if (node == null || pos < 0 || node.getValueAt(pos) == null) {
                return words; // Prefijo no encontrado
            }
            node = node.getValueAt(pos);
        }

        listarPalabras(node, normalized, words); // Listar palabras desde el nodo del prefijo
        return words;
    }

    // Métodos de implementación básica Punto 2
    public void insertarPalabra(String word) {
        String normalized = normalizarPalabra(word);
        if (normalized.isEmpty())
            throw new IllegalArgumentException("La palabra no puede estar vacía");

        // Validar toda la palabra antes de modificar el trie
        for (int i = 0; i < normalized.length(); i++) {
            if (getPosition(normalized.charAt(i)) < 0)
                throw new IllegalArgumentException("Carácter fuera de rango: " + normalized.charAt(i));
        }

        if (root == null)
            root = new TrieNode(end);
        TrieNode t = root;

        for (int i = 0; i < normalized.length(); i++) {
            int pos = getPosition(normalized.charAt(i));
            if (t.getValueAt(pos) == null)
                t.setValueAt(pos, new TrieNode(end));
            t = t.getValueAt(pos);
        }
        t.setValueAt(begin, t);
    }

    public void eliminarPalabra(String word) {
        if (word == null || word.isEmpty()) {
            System.out.println("No se pudo eliminar: Palabra nulo o vacía");
            return;
        }
        String normalizado = normalizarPalabra(word);
        for (int i = 0; i < normalizado.length(); i++) {
            if (getPosition(normalizado.charAt(i)) < 0) {
                System.out.println("No se pudo eliminar: la palabra contiene caracteres no validos");
                return;
            }
        }
        if (root == null) {
            System.out.println("la palabra \"" + word + "\" no existe en el trie.\"");
            return;
        }
        boolean eliminada = eliminarPalabra(root, normalizado, 0);

        if (eliminada) {
            if (!esNecesario(root)) {
                root = null;
            }
            System.out.println("Palabra eliminada con exito: " + word);

        } else {
            System.out.println("La palabra \"" + word + "\" no existe en el trie.");
        }

    }

    private boolean eliminarPalabra(TrieNode node, String word, int index) {
        if (index == word.length()) {
            if (node.getValueAt(begin) == node) {
                node.setValueAt(begin, null);
                return true;
            }
            return false;
        }

        int pos = getPosition(word.charAt(index));
        TrieNode child = node.getValueAt(pos);

        if (child == null) {
            return false;
        }

        boolean eliminada = eliminarPalabra(child, word, index + 1);

        if (eliminada && !esNecesario(child)) {
            node.setValueAt(pos, null);
        }

        return eliminada;

    }

    private boolean esNecesario(TrieNode node) {

        if (node.getValueAt(begin) == node) {
            return true; // Todavia representa una palabra
        }

        for (int i = 1; i <= end; i++) {
            if (node.getValueAt(i) != null) {
                return true;
            }
        }
        return false;
    }

    private static String normalizarPalabra(String word) {
        if (word == null)
            throw new IllegalArgumentException("La palabra no puede ser null");
        return word.toLowerCase(Locale.ROOT);
    }

    private void listarPalabras(TrieNode node, String prefix, List<String> words) {
        if (node == null)
            return;

        if (node.getValueAt(begin) == node) {
            words.add(prefix); // Agregar palabra completa
        }

        for (int i = 1; i <= end; i++) {
            TrieNode child = node.getValueAt(i);
            if (child != null) {
                listarPalabras(child, prefix + (char) ('a' + i - 1), words);
            }
        }
    }

    public boolean buscarPalabra(String word) {
        if (root == null || word == null || word.isEmpty())
            return false;

        String normalized = word.toLowerCase(Locale.ROOT);
        TrieNode t = root;

        for (int i = 0; i < normalized.length(); i++) {
            int pos = getPosition(normalized.charAt(i));
            if (pos < 0 || t.getValueAt(pos) == null)
                return false;
            t = t.getValueAt(pos);
        }
        return t.getValueAt(begin) == t;
    }

    public static int getPosition(char c) {
        if (c >= 'a' && c <= 'z')
            return c - 'a' + 1;
        return -1;
    }


    //Métodos extras

    public List<String> buscarPalabrasSimilares(String word) {
        List<String> similarWords = new ArrayList<>();
        if (root == null || word == null || word.isEmpty())
            return similarWords;

        String normalized = word.toLowerCase(Locale.ROOT);
        TrieNode node = root;
        StringBuilder prefix = new StringBuilder();

        for (int i = 0; i < normalized.length(); i++) {
            int pos = getPosition(normalized.charAt(i));
            if (pos < 0)
                return similarWords; // Carácter no válido
            if (node == null || node.getValueAt(pos) == null) {
                break;
            }
            prefix.append(normalized.charAt(i));
            node = node.getValueAt(pos);
        }

        // Verificar si alcanzamos al menos la mitad de la palabra (redondeando hacia
        // arriba)
        int threshold = (normalized.length() + 1) / 2;
        if (node != null && prefix.length() >= threshold && prefix.length() > 0) {
            listarPalabras(node, prefix.toString(), similarWords);
        }
        return similarWords;
    }

    public int contadorPrefijos() {
        return contadorPrefijos(root, "");
    }

    private int contadorPrefijos(TrieNode node, String prefix) {
        if (node == null)
            return 0;

        int childCount = 0; // Contador de hijos no nulos
        for (int i = 1; i <= end; i++) { // Iterar sobre los hijos
            if (node.getValueAt(i) != null) {
                childCount++;
            }
        }

        // Si el nodo tiene más de un hijo, es un prefijo compartido
        int prefixCount = 0;
        if (childCount > 1 && !prefix.isEmpty()) {
            prefixCount = 1; // Contamos este nodo como prefijo
        }

        // Sumar los prefijos compartidos en los hijos
        for (int i = 1; i <= end; i++) {
            TrieNode child = node.getValueAt(i);
            if (child != null) {
                char nextChar = (char) ('a' + i - 1);
                prefixCount += contadorPrefijos(child, prefix + nextChar);
            }
        }

        return prefixCount;
    }

    public List<String> listarPrefijos() {
        List<String> prefixes = new ArrayList<>();
        listarPrefijos(root, "", prefixes);
        return prefixes;
    }

    private void listarPrefijos(TrieNode node, String currentPrefix, List<String> prefixes) {
        if (node == null)
            return;

        int childCount = 0; // Contador de hijos no nulos
        for (int i = 1; i <= end; i++) { // Desde 1 porque 0 es el marcador de fin de palabra
            if (node.getValueAt(i) != null) {
                childCount++;
            }
        }

        // Si el nodo tiene más de un hijo, el prefijo actual es compartido
        if (childCount > 1 && !currentPrefix.isEmpty()) {
            prefixes.add(currentPrefix); // Agregar el prefijo a la lista
        }

        // Recorrer los hijos para continuar la búsqueda de prefijos compartidos
        for (int i = 1; i <= end; i++) {
            TrieNode child = node.getValueAt(i);
            if (child != null) {
                char nextChar = (char) ('a' + i - 1); // Obtener el carácter correspondiente
                listarPrefijos(child, currentPrefix + nextChar, prefixes);
            }
        }
    }

    public int contarNodos() {
        return contarNodos(root);
    }

    private int contarNodos(TrieNode node) {
        if (node == null) {
            return 0; // No hay nodos en este camino
        }

        int count = 1; // Contamos el nodo actual

        // Recorrer todos los hijos del nodo actual
        for (int i = 0; i <= end; i++) {
            TrieNode child = node.getValueAt(i);

            // Evitar seguir un nodo que apunta al propio nodo actual
            if (child != null && child != node) {
                count += contarNodos(child); // Sumar los nodos encontrados en el hijo
            }
        }

        return count;
    }

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
                "Python", "pip", "php", "perl"
        };
        for (String palabra : busquedas) {
            System.out.println("  buscarPalabra(\"" + palabra + "\") -> "
                    + trie.buscarPalabra(palabra));
        }

        System.out.println();
        System.out.println("Palabras con prefijo:");
        String[] prefijos = { "x", "xml", "xht", "p", "py", "php", "per", "z", "" };
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

class TrieNode {

    private TrieNode[] chars;

    public TrieNode(int end) {
        chars = new TrieNode[end + 1];
    }

    public TrieNode getValueAt(int pos) {

        if (pos < 0 || pos >= chars.length) {
            return null;
        }
        return chars[pos];
    }

    public void setValueAt(int pos, TrieNode newNode) {
        if (pos >= 0 && pos < chars.length) {
            chars[pos] = newNode;
        }
    }
}
