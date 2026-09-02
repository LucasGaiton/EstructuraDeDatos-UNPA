import java.util.ArrayList;
import java.util.List;

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

    private int contarPalabras(TrieNode node) {
        if (node == null)
            return 0;

        int count = 0;
        if (node.getValueAt(0) == node)
            count++; // Nodo marcado como final de palabra

        for (int i = 1; i <= end; i++) {
            count += contarPalabras(node.getValueAt(i));
        }
        return count;
    }

    public List<String> listarPalabras() {
        List<String> words = new ArrayList<>();
        listarPalabras(root, "", words);
        return words;
    }

    public List<String> palabrasConPrefijo(String prefix) {
        List<String> words = new ArrayList<>();
        TrieNode node = root;
    
        for (char c : prefix.toCharArray()) {
            int pos = getPosition(c);
            if (node == null || node.getValueAt(pos) == null) {
                return words; // Prefijo no encontrado
            }
            node = node.getValueAt(pos);
        }
    
        listarPalabras(node, prefix, words); // Listar palabras desde el nodo del prefijo
        return words;
    }
    
    public List<String> buscarPalabrasSimilares(String word) {
        TrieNode node = root;
        StringBuilder prefix = new StringBuilder();
    
        for (char c : word.toCharArray()) {
            int pos = getPosition(c);
            if (node == null || node.getValueAt(pos) == null) {
                break;
            }
            prefix.append(c);
            node = node.getValueAt(pos);
        }
    
        // Si no se encontró la palabra completa, buscar palabras similares
        List<String> similarWords = new ArrayList<>();
        if (node != null && prefix.length() >= word.length() / 2) {
            listarPalabras(node, prefix.toString(), similarWords);
        }
        return similarWords;
    }
    
    public int contadorPrefijos() {
        return contadorPrefijos(root, "");
    }
    
    private int contadorPrefijos(TrieNode node, String prefix) {
        if (node == null) return 0;
    
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
        if (node == null) return;
    
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
    
    private void listarPalabras(TrieNode node, String prefix, List<String> words) {
        if (node == null)
            return;

        if (node.getValueAt(0) == node) {
            words.add(prefix); // Agregar palabra completa
        }

        for (int i = 1; i <= end; i++) {
            TrieNode child = node.getValueAt(i);
            if (child != null) {
                listarPalabras(child, prefix + (char) ('a' + i - 1), words);
            }
        }
    }

    public void insertarPalabra(String word) {
        TrieNode t;
        int i, pos;
        if (root == null)
            root = new TrieNode(end);
        t = root;
        i = 0;
        // pasar word a minúscula o mayúscula
        while (i < word.length()) {
            pos = getPosition(word.charAt(i));
            // falta validar posición pos
            if (t.getValueAt(pos) == null)
                t.setValueAt(pos, new TrieNode(end));
            t = t.getValueAt(pos);
            i++;
        }
        t.setValueAt(begin, t);
    }

    public boolean buscarPalabra(String word) {
        TrieNode t = root;
        int i = 0;
        if (t == null)
            return false;
        int pos;
        // ver el tema de minúsculas y mayúsculas
        while (i < word.length()) {
            pos = getPosition(word.charAt(i));
            // falta validar posición pos

            if (t.getValueAt(pos) != null) {
                t = t.getValueAt(pos);
                i++;
            } else
                return false;
        }
        if (t.getValueAt(begin) == t)
            return true;
        else
            return false;
    }

    public static int getPosition(char c) {
        if (c == '@')
            return 0;
        if (c >= 'a' && c <= 'z')
            return c - 'a' + 1;
        return -1;
    }

}
