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
                    rotateRight(n); // Rotación (simple o doble) a la derecha
                    this.grown = false;
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

    // ------------------------------------------------------------------
    // Dibujo del árbol por consola (Punto 3 del TP)
    // ------------------------------------------------------------------

    // Imprime el árbol completo mostrando, entre paréntesis, el factor
    // de equilibrio de cada nodo. El árbol se dibuja "acostado": la raíz
    // queda a la izquierda, el subárbol derecho hacia arriba y el izquierdo
    // hacia abajo.
    public void printTree() {
        if (root == null) {
            System.out.println("(árbol vacío)");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Árbol AVL (clave (factor de equilibrio)):\n");
        printTree(root, null, false, sb);
        System.out.print(sb);
    }

    // Guarda el prefijo que conecta a un nodo con su padre al dibujar las ramas
    private static class Trunk {
        Trunk prev;
        String str;

        Trunk(Trunk prev, String str) {
            this.prev = prev;
            this.str = str;
        }
    }

    // Recorre recursivamente la cadena de "ramas" hacia la raíz para imprimirla
    private void showTrunks(Trunk p, StringBuilder sb) {
        if (p == null) {
            return;
        }
        showTrunks(p.prev, sb);
        sb.append(p.str);
    }

    // Dibuja el subárbol con raíz en "node". "isLeft" indica si el nodo es
    // hijo izquierdo (se dibuja hacia abajo) o derecho (hacia arriba).
    private void printTree(AVLNode node, Trunk prev, boolean isLeft, StringBuilder sb) {
        if (node == null) {
            return;
        }

        String prevStr = "    ";
        Trunk trunk = new Trunk(prev, prevStr);

        // Se dibuja primero el subárbol derecho (aparece "arriba")
        printTree(node.right, trunk, true, sb);

        if (prev == null) {
            trunk.str = "--> "; // Raíz
        } else if (isLeft) {
            trunk.str = ".-- "; // Hijo derecho
            prevStr = "  | ";
        } else {
            trunk.str = "`-- "; // Hijo izquierdo
            prev.str = "    ";
        }

        showTrunks(trunk, sb);
        sb.append(node.content)
          .append(" (")
          .append(node.balance)
          .append(")\n");

        if (prev != null) {
            prev.str = prevStr;
        }
        trunk.str = "  | ";

        // Luego el subárbol izquierdo (aparece "abajo")
        printTree(node.left, trunk, false, sb);
    }

    // Ejemplo de prueba de la estructura
    public static void main(String[] args) {

        AVLTree arbol = new AVLTree();
        // Punto 3: inserción de la secuencia de claves del enunciado
        int[] claves = {100, 29, 71, 82, 48, 39, 101, 22, 46, 17, 3, 20, 25, 25, 10};
        for (int clave : claves) {
            if (!arbol.insert(clave)) {
                System.out.println("La clave " + clave + " ya existía en el árbol.");
            }
        }

        // Dibujo del árbol AVL resultante (Punto 3)
        arbol.printTree();

        // Recorrido Inorden para verificar el orden creciente de las claves
        System.out.print("Recorrido Inorden del Árbol AVL: ");
        arbol.inorden();
        // System.out.println("¿Existe el 25 en el árbol?: " + arbol.search(25));
        // System.out.println("¿Existe el 99 en el árbol?: " + arbol.search(99));

    }
}
