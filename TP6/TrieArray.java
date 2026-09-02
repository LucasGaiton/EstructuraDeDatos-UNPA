import java.util.ArrayList;

class TrieArray {
    TrieNodeArray root;

    TrieArray() {
        root = new TrieNodeArray();
    }

    void insertWord(String word) {
        if (root == null)
            root = new TrieNodeArray();
        TrieNodeArray t = root;
        word = word.toLowerCase();

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            TrieNodeArray child = t.getChild(c);
            if (child == null) {
                child = new TrieNodeArray(c);
                t.children.add(child);
            }
            t = child;
        }

        t.isEndOfWord = true; // Marcar nodo final
    }
    

    boolean searchWord(String word) {
        if (root == null) return false;
        TrieNodeArray t = root;
        word = word.toLowerCase();

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            t = t.getChild(c);
            if (t == null)
                return false;
        }

        return t.isEndOfWord;
    }
}

class TrieNodeArray {
    char value;
    ArrayList<TrieNodeArray> children;
    boolean isEndOfWord;

    TrieNodeArray() {
        this.children = new ArrayList<>();
        this.isEndOfWord = false;
    }

    TrieNodeArray(char value) {
        this();
        this.value = value;
    }

    TrieNodeArray getChild(char c) {
        for (TrieNodeArray child : children) {
            if (child.value == c) {
                return child;
            }
        }
        return null;
    }
}
