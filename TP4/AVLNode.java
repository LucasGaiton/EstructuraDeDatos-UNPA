package TP4;

public class AVLNode {
    int content; // Contenido/clave almacenada en el nodo
    byte balance; // Factor de equilibrio (-2, -1, 0, +1, +2)
    AVLNode left; // Referencia al hijo izquierdo
    AVLNode right; // Referencia al hijo derecho

    public AVLNode(int c) {
        this.content = c;
        this.balance = 0;
        this.left = null;
        this.right = null;
    }

}
