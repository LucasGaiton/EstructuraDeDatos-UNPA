package TP4.Punto3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

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
    public boolean search(String c) {
        AVLNode n = root;
        while (n != null) {
            if (c.compareTo(n.content) == 0) {
                System.out.println("La palabra ya ha sido encontrado y se ha repetido " + n.frecuency+ " veces");
                return true;
            }
            if (c.compareTo(n.content) < 0) {
                n = n.left;
            } else {
                n = n.right;
            }
        }
        System.out.println("La palabra no ha sido encontrada y se ha repetido "+ 0+ " veces");
        return false;

    }

    // Método publico de inserción
    public boolean insert(String c) {
        this.found = false;
        this.grown = true; // Se asume inicialmente que la altura cambiará
        this.root = insert(root, c);
        return !this.found;
    }

    // Método privado recursivo de inserción y rebalanceo
    private AVLNode insert(AVLNode n, String c) {
        if (n == null) {
            return new AVLNode(c);
        }
        if (c.compareTo(n.content) == 0) {
            this.found = true;
            this.grown = false; // No hay cambios si el elemento ya existe
            n.frecuency++;
        } else {
            if (c.compareTo(n.content) < 0) {
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
        String cc = n.content;
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
        String cc = n.content;
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
            System.out.print(n.content + " Frecuencia: " + n.frecuency + " ");
            inorden(n.right);
        }
    }

    // Longitud de la palabra más larga del árbol (para calcular el ancho de columna)
    private int longestWord(AVLNode n) {
        if (n == null) {
            return 0;
        }
        return Math.max(n.content.length(), Math.max(longestWord(n.left), longestWord(n.right)));
    }

    // Tabla de palabras con sus frecuencias, en orden alfabético
    public void mostrarFrecuencias() {
        int ancho = Math.max(longestWord(root), "PALABRA".length());
        String sep = "-".repeat(ancho + 13);
        System.out.printf("%-" + ancho + "s | %s%n", "PALABRA", "FRECUENCIA");
        System.out.println(sep);
        mostrarFrecuencias(root, ancho);
        System.out.println(sep);
    }

    private void mostrarFrecuencias(AVLNode n, int ancho) {
        if (n == null) {
            return;
        }
        mostrarFrecuencias(n.left, ancho);
        System.out.printf("%-" + ancho + "s | %5d%n", n.content, n.frecuency);
        mostrarFrecuencias(n.right, ancho);
    }

    public void loadFromFile(String route) {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(route), "UTF-8"));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] palabras = linea.split("[^a-zA-ZáéíóúÁÉÍÓÚñÑ]+"); // Va a tomar como separador cualquier caracter
                                                                         // que no este en esta lista
                for (String palabra : palabras) {
                    if (!palabra.isEmpty()) {
                        this.insert(palabra);
                    }
                }
            }
            br.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        AVLTree arbol = new AVLTree();
        arbol.loadFromFile("TP4/Punto3/Prueba.txt");
        arbol.mostrarFrecuencias();
        arbol.search("Casa");


    }

}
