import java.util.ArrayList;
import java.util.List;

class TrieLinkedList {
    TrieNodeLinkedList root;

    TrieLinkedList() {
        root = new TrieNodeLinkedList();
    }

    void insertWord(String word) {
        if (root == null)
            root = new TrieNodeLinkedList();
        TrieNodeLinkedList t = root;
        word = word.toLowerCase();

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            TrieNodeLinkedList child = t.getChild(c);
            if (child == null) {
                child = new TrieNodeLinkedList(c);
                t.addChild(child);
            }
            t = child;
        }

        t.isEndOfWord = true;
    }

    boolean searchWord(String word) {
        if (root == null) return false;
        TrieNodeLinkedList t = root;
        word = word.toLowerCase();

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            t = t.getChild(c);
            if (t == null)
                return false;
        }

        return t.isEndOfWord;
    }

    // (a) Cantidad de palabras almacenadas
    int countWords() {
        return countWordsHelper(root);
    }

    private int countWordsHelper(TrieNodeLinkedList node) {
        if (node == null) return 0;

        int count = node.isEndOfWord ? 1 : 0;

        TrieNodeLinkedList child = node.firstChild;
        while (child != null) {
            count += countWordsHelper(child);
            child = child.nextSibling;
        }

        return count;
    }

    // (b) Listar todas las palabras almacenadas
    List<String> listWords() {
        List<String> words = new ArrayList<>();
        listWordsHelper(root, "", words);
        return words;
    }

    private void listWordsHelper(TrieNodeLinkedList node, String prefix, List<String> words) {
        if (node == null) return;

        if (node.isEndOfWord) words.add(prefix);

        TrieNodeLinkedList child = node.firstChild;
        while (child != null) {
            listWordsHelper(child, prefix + child.value, words);
            child = child.nextSibling;
        }
    }
    int countNodes() {
        return countNodes(root);
    }
    
    // Método recursivo para contar nodos
    private int countNodes(TrieNodeLinkedList node) {
        if (node == null) {
            return 0;
        }
    
        int count = 1; // Contamos el nodo actual
        TrieNodeLinkedList child = node.firstChild;
        while (child != null) {
            count += countNodes(child); // Sumar los nodos de los hijos
            child = child.nextSibling;
        }
        return count;   
    }


    // (c) Palabras que comienzan con un prefijo
    List<String> wordsWithPrefix(String prefix) {
        TrieNodeLinkedList node = findNode(prefix);
        List<String> words = new ArrayList<>();
        if (node != null) listWordsHelper(node, prefix, words);
        return words;
    }

    private TrieNodeLinkedList findNode(String prefix) {
        TrieNodeLinkedList node = root;
        for (char c : prefix.toLowerCase().toCharArray()) {
            node = node.getChild(c);
            if (node == null) return null;
        }
        return node;
    }

    // (d) Buscar palabras parecidas
    List<String> similarWords(String word) {
        int threshold = word.length() / 2;
        List<String> allWords = listWords();
        List<String> similarWords = new ArrayList<>();

        for (String w : allWords) {
            if (w.length() >= threshold && w.substring(0, threshold).equals(word.substring(0, threshold))) {
                similarWords.add(w);
            }
        }

        return similarWords;
    }

    // (e) Contar prefijos distintos
    int countPrefixes() {
        return countPrefixesHelper(root);
    }

    private int countPrefixesHelper(TrieNodeLinkedList node) {
        if (node == null) return 0;

        int count = 1; // Cuenta el nodo actual como un prefijo único

        TrieNodeLinkedList child = node.firstChild;
        while (child != null) {
            count += countPrefixesHelper(child);
            child = child.nextSibling;
        }

        return count;
    }

    // (f) Buscar por sufijos (idea)
    // Para buscar por sufijos, sería necesario construir un trie invertido, donde las palabras se almacenen al revés.
    // Por ejemplo, "casa" se insertaría como "asac". Esto permitiría usar un prefijo para encontrar palabras por sufijos.
}
class TrieNodeLinkedList {
    char value; // El carácter que representa el nodo
    TrieNodeLinkedList nextSibling; // Nodo hermano (siguiente en el mismo nivel)
    TrieNodeLinkedList firstChild; // Primer nodo hijo
    boolean isEndOfWord; // Indica si este nodo representa el final de una palabra

    // Constructor para el nodo raíz (vacío)
    TrieNodeLinkedList() {
        this.value = '\0';
        this.nextSibling = null;
        this.firstChild = null;
        this.isEndOfWord = false;
    }

    // Constructor para un nodo con un valor específico
    TrieNodeLinkedList(char value) {
        this();
        this.value = value;
    }

    // Método para obtener un hijo dado un carácter
    TrieNodeLinkedList getChild(char c) {
        TrieNodeLinkedList current = firstChild;
        while (current != null) {
            if (current.value == c) {
                return current; // Devuelve el hijo que coincide con el carácter
            }
            current = current.nextSibling; // Avanza al siguiente hermano
        }
        return null; // Retorna null si no se encuentra el carácter
    }

    // Método para agregar un nuevo hijo
    void addChild(TrieNodeLinkedList child) {
        if (firstChild == null) {
            firstChild = child; // Si no hay hijos, el nuevo nodo es el primer hijo
        } else {
            TrieNodeLinkedList current = firstChild;
            while (current.nextSibling != null) {
                current = current.nextSibling; // Avanza hasta el último hermano
            }
            current.nextSibling = child; // Agrega el nuevo nodo como hermano
        }
    }
}


