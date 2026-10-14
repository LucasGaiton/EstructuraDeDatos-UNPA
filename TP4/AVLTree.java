package TP4;

public class AVLTree {

    private AVLNode root; // Raiz Arbol
    private boolean grown; // Controla si la altura del subárbol aumentó
    private boolean found; // Indica si el elemento ya existia en el árbol

    public AVLTree() {
        this.root = null;
        this.grown = false;
        this.found = false;
    }

    // Metodo para buscar una clave en el árbol
    public boolean search(int c) {
        AVLNode n = root;
        while (n != null) {
            if (c == n.content) {
                return true;
            }
            if (c < n.content) {
                n = n.left;
            } else {
                n = n.right;
            }
        }
        return false;

    }

    // Método publico de inserción
    public boolean insert(int c) {
        this.found = false;
        this.grown = true; // Se asume inicialmente que la altura cambiará
        this.root = insert(root, c);
        return !this.found;
    }

    // Método privado recursivo de inserción y rebalanceo
    private AVLNode insert(AVLNode n, int c) {
        if (n == null) {
            return new AVLNode(c);
        }
        if (c == n.content) {
            this.found = true;
            this.grown = false; // No hay cambios si el elemento ya existe
        } else {
            if (c < n.content) {
                n.left = insert(n.left, c);
                if (this.grown) {
                    n.balance--; // El subárbol izquierdo creció
                }
            } else {
                n.right = insert(n.right, c);
                if (this.grown) {
                    n.balance++; // El subárbol derecho creció
                }
            }
            // Evaluación y reestrucutración según el Factor de equilibrio

            switch (n.balance) {
                case -2: // Desbalance del lado izquierdo
                    if (n.left.balance == +1) {
                        rotateLeft(n.left); // Preparación para rotación doble (Izq-Der)
                    }
                    break;
                case -1:
                case +1:
                    break;
                case 0:
                    this.grown = false;
                    break;
                case +2: // Desbalance del lado derecho
                    if (n.right.balance == -1) {
                        rotateRight(n.right); // Preparación para ratación doble (der-Izq)
                    }
                    rotateLeft(n); // Ratación simple a la izquierda
                    this.grown = false;
                    break;

                default:
                    System.out.println("Error en el factor de equilibrio del nodo: " + n.content);
                    break;
            }
        }
        return n;
    }

    // Rotación simple a la derecha
    private void rotateRight(AVLNode n) {
        AVLNode m = n.left;
        int cc = n.content;
        n.content = m.content;
        m.content = cc;

        n.left = m.left;
        m.left = m.right;
        m.right = n.right;
        n.right = m;

        int bm = 1 + Math.max(-m.balance, 0) + n.balance;
        int bn = 1 + m.balance + Math.max(0, bm);
        n.balance = (byte) bn;
        m.balance = (byte) bm;
    }

    // Rotación simple a la izquierda
    private void rotateLeft(AVLNode n) {
        AVLNode m = n.right;
        int cc = n.content;
        n.content = m.content;
        m.content = cc;

        n.right = m.right;
        m.right = m.left;
        m.left = n.left;
        n.left = m;

        int bm = -(1 + Math.max((int) m.balance, 0) - n.balance);
        int bn = -(1 - m.balance + Math.max(0, -bm));
        n.balance = (byte) bn;
        m.balance = (byte) bm;
    }

    // Recorrido Inorden para mostrar las claves en orden creciente
    public void inorden() {
        inorden(root);
        System.out.println();
    }

    public void inorden(AVLNode n) {
        if (n != null) {
            inorden(n.left);
            System.out.print(n.content + " ");
            inorden(n.right);
        }
    }

    // Ejemplo de prueba de la estructura
    public static void main(String[] args) {

        AVLTree arbol = new AVLTree();
        // Inserción de claves de prueba
        int[] claves = {10, 20, 30, 40, 50, 25};
        for (int clave : claves) {
            arbol.insert(clave);
        }
        System.out.print("Recorrido Inorden del Árbol AVL: ");
        arbol.inorden(); // Salida esperada: 10 20 25 30 40 50
        // System.out.println("¿Existe el 25 en el árbol?: " + [suspicious link removed](25));
        // System.out.println("¿Existe el 99 en el árbol?: " + [suspicious link removed](99));

    }
}
