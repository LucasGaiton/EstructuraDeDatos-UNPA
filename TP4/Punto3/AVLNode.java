package TP4.Punto3;

public class AVLNode {
    String content; // Contenido/clave almacenada en el nodo
    int frecuency; //Cantidad de veces que se repite la palabra
    byte balance; // Factor de equilibrio (-2, -1, 0, +1, +2)
    AVLNode left; // Referencia al hijo izquierdo
    AVLNode right; // Referencia al hijo derecho

    public AVLNode(String c) {
        this.content = c;
        this.balance = 0;
        this.frecuency = 1;
        this.left = null;
        this.right = null;
    }
}
