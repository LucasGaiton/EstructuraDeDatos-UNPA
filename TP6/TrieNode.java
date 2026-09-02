public class TrieNode {

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