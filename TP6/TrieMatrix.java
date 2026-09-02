import java.util.ArrayList;
import java.util.List;

class TrieMatrix {
    TrieNodeMatrix root;
    final int end = 26; // Letras del alfabeto inglés

    TrieMatrix() {
        root = null;
    }
    // public int contarNodos() {
    //     return contarNodos(root);
    // }
    // private int contarNodos(TrieNodeMatrix nodo) {
    //     if (nodo == null) {
    //         return 0;
    //     }

    //     int cantidad = 1;
    //     for (char i = 'a'; i <= 'z'; i++) {
    //         cantidad += contarNodos(nodo.getChar(i));
    //     }
    //     if (nodo.getChar('@') != null) {
    //         cantidad += contarNodos(nodo.getChar('@'));
    //     }

    //     return cantidad;
    // }

    void insertWord(String word) {
        if (root == null)
            root = new TrieNodeMatrix(end);
        TrieNodeMatrix t = root;
        word = word.toLowerCase();

        for (int i = 0; i < word.length(); i++) {
            int pos = getPosition(word.charAt(i));
            if (pos < 0 || pos > end)
                throw new IllegalArgumentException("Carácter fuera de rango: " + word.charAt(i));

            if (t.matrix[pos] == null)
                t.matrix[pos] = new TrieNodeMatrix(end);

            t = t.matrix[pos];
        }

        t.isEndOfWord = true; // Marcar nodo final
    }

    public void insertWordReversed(String word) {
        String reversedWord = new StringBuilder(word).reverse().toString();
        insertWord(reversedWord);
    }

    boolean searchWord(String word) {
        if (root == null)
            return false;
        TrieNodeMatrix t = root;
        word = word.toLowerCase();

        for (int i = 0; i < word.length(); i++) {
            int pos = getPosition(word.charAt(i));
            if (pos < 0 || pos > end || t.matrix[pos] == null)
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

        int count = 0;
        if (node.isEndOfWord)
            count++;

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
    

    void listWordsRecursive(TrieNodeMatrix node, String currentWord, List<String> words) {
        if (node == null) return;
    
        // Si el nodo marca el final de una palabra, añadirla a la lista
        if (node.isEndOfWord) {
            words.add(currentWord);
        }
    
        // Recorrer los hijos del nodo
        for (int i = 0; i < node.matrix.length; i++) {
            if (node.matrix[i] != null) {
                char c = (char) ('a' + i); // Reconstruir la letra correspondiente
                listWordsRecursive(node.matrix[i], currentWord + c, words);
            }
        }
    }
    List<String> listWordsWithPrefix(String prefix) {
        TrieNodeMatrix t = root;

        for (int i = 0; i < prefix.length(); i++) {
            int pos = getPosition(prefix.charAt(i));
            if (t.matrix[pos] == null)
                return new ArrayList<>();
            t = t.matrix[pos];
        }

        List<String> words = new ArrayList<>();
        listWordsRecursive(t, prefix, words);
        return words;
    }

    List<String> searchSimilarWords(String word) {
        TrieNodeMatrix t = root;
        int i = 0;

        // Navegar el Trie según el prefijo común
        while (i < word.length()) {
            int pos = getPosition(word.charAt(i));
            if (t.matrix[pos] == null)
                break;
            t = t.matrix[pos];
            i++;
        }

        // Verificar si alcanzamos al menos la mitad de la palabra
        if (i >= word.length() / 2) {
            List<String> similarWords = new ArrayList<>();
            listWordsRecursive(t, word.substring(0, i), similarWords);
            return similarWords;
        }

        return new ArrayList<>();
    }

    int countPrefixes() {
        return countPrefixesRecursive(root);
    }

    int countPrefixesRecursive(TrieNodeMatrix node) {
        if (node == null)
            return 0;

        int count = 1; // Este nodo cuenta como un prefijo
        for (TrieNodeMatrix child : node.matrix) {
            count += countPrefixesRecursive(child);
        }

        return count;
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

    TrieNodeMatrix(int end) {
        matrix = new TrieNodeMatrix[end + 1];
        isEndOfWord = false;
    }

}
