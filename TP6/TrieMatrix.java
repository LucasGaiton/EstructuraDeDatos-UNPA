package TP6;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class TrieMatrix {
    private static final int ALPHABET_SIZE = 26; // Letras del alfabeto inglés

    TrieNodeMatrix root;

    TrieMatrix() {
        root = null;
    }

    void insertWord(String word) {
        String normalized = normalizeWord(word);
        if (normalized.isEmpty())
            throw new IllegalArgumentException("La palabra no puede estar vacía");

        // Validar toda la palabra antes de modificar el trie
        for (int i = 0; i < normalized.length(); i++) {
            if (getPosition(normalized.charAt(i)) < 0)
                throw new IllegalArgumentException("Carácter fuera de rango: " + normalized.charAt(i));
        }

        if (root == null)
            root = new TrieNodeMatrix(ALPHABET_SIZE);
        TrieNodeMatrix t = root;

        for (int i = 0; i < normalized.length(); i++) {
            int pos = getPosition(normalized.charAt(i));
            if (t.matrix[pos] == null)
                t.matrix[pos] = new TrieNodeMatrix(ALPHABET_SIZE);
            t = t.matrix[pos];
        }

        t.isEndOfWord = true; // Marcar nodo final
    }

    public void insertWordReversed(String word) {
        String normalized = normalizeWord(word);
        if (normalized.isEmpty())
            throw new IllegalArgumentException("La palabra no puede estar vacía");

        String reversedWord = new StringBuilder(normalized).reverse().toString();
        insertWord(reversedWord);
    }

    boolean searchWord(String word) {
        if (root == null || word == null || word.isEmpty())
            return false;

        TrieNodeMatrix t = root;
        String normalized = word.toLowerCase(Locale.ROOT);

        for (int i = 0; i < normalized.length(); i++) {
            int pos = getPosition(normalized.charAt(i));
            if (pos < 0 || pos >= t.matrix.length || t.matrix[pos] == null)
                return false;

            t = t.matrix[pos];
        }

        return t.isEndOfWord;
    }

    public int countWords() {
        return countWordsRecursive(root);
    }

    private int countWordsRecursive(TrieNodeMatrix node) {
        if (node == null)
            return 0;

        int count = node.isEndOfWord ? 1 : 0;
        for (TrieNodeMatrix child : node.matrix) {
            count += countWordsRecursive(child);
        }

        return count;
    }

    List<String> listWords() {
        List<String> words = new ArrayList<>();

        listWordsRecursive(root, "", words);
        return words;
    }
    

    private void listWordsRecursive(TrieNodeMatrix node, String currentWord, List<String> words) {
        if (node == null) return;

        // Si el nodo marca el final de una palabra, añadirla a la lista
        if (node.isEndOfWord) {
            words.add(currentWord);
        }

        // Recorrer los hijos del nodo
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (node.matrix[i] != null) {
                char c = (char) ('a' + i); // Reconstruir la letra correspondiente
                listWordsRecursive(node.matrix[i], currentWord + c, words);
            }
        }
    }
    List<String> listWordsWithPrefix(String prefix) {
        if (prefix == null)
            return new ArrayList<>();

        String normalized = prefix.toLowerCase(Locale.ROOT);
        TrieNodeMatrix t = findNode(normalized);
        if (t == null)
            return new ArrayList<>();

        List<String> words = new ArrayList<>();
        listWordsRecursive(t, normalized, words);
        return words;
    }

    List<String> searchSimilarWords(String word) {
        if (root == null || word == null || word.isEmpty())
            return new ArrayList<>();

        String normalized = word.toLowerCase(Locale.ROOT);
        TrieNodeMatrix t = root;
        int i = 0;

        // Navegar el Trie según el prefijo común
        while (i < normalized.length()) {
            int pos = getPosition(normalized.charAt(i));
            if (pos < 0 || pos >= t.matrix.length)
                return new ArrayList<>(); // Carácter no válido
            if (t.matrix[pos] == null)
                break;
            t = t.matrix[pos];
            i++;
        }

        // Verificar si alcanzamos al menos la mitad de la palabra (redondeando hacia arriba)
        int threshold = (normalized.length() + 1) / 2;
        if (i >= threshold && i > 0) {
            List<String> similarWords = new ArrayList<>();
            listWordsRecursive(t, normalized.substring(0, i), similarWords);
            return similarWords;
        }

        return new ArrayList<>();
    }

    int countPrefixes() {
        return countPrefixesRecursive(root, true);
    }

    private int countPrefixesRecursive(TrieNodeMatrix node, boolean isRoot) {
        if (node == null)
            return 0;

        int childCount = 0;
        for (TrieNodeMatrix child : node.matrix) {
            if (child != null)
                childCount++;
        }

        // Un nodo es un prefijo compartido si tiene más de un hijo.
        int count = (!isRoot && childCount > 1) ? 1 : 0;

        for (TrieNodeMatrix child : node.matrix) {
            count += countPrefixesRecursive(child, false);
        }

        return count;
    }

    private TrieNodeMatrix findNode(String prefix) {
        if (root == null || prefix == null)
            return null;

        TrieNodeMatrix t = root;
        for (int i = 0; i < prefix.length(); i++) {
            int pos = getPosition(prefix.charAt(i));
            if (pos < 0 || pos >= t.matrix.length)
                return null;
            t = t.matrix[pos];
            if (t == null)
                return null;
        }
        return t;
    }

    private String normalizeWord(String word) {
        if (word == null)
            throw new IllegalArgumentException("La palabra no puede ser null");
        return word.toLowerCase(Locale.ROOT);
    }

    int getPosition(char c) {
        if (c >= 'a' && c <= 'z') {
            return c - 'a'; // 'a' es 0, 'b' es 1, ..., 'z' es 25
        }
        return -1; // Carácter no válido
    }
}

class TrieNodeMatrix {
    TrieNodeMatrix[] matrix;
    boolean isEndOfWord;

    TrieNodeMatrix(int alphabetSize) {
        matrix = new TrieNodeMatrix[alphabetSize];
        isEndOfWord = false;
    }

}
