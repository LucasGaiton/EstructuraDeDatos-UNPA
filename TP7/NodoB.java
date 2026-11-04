package TP7;
public class NodoB {
    int [] claves;
    NodoB[] hijos;
    int nClaves;
    boolean hoja;

    public NodoB(int t, boolean hoja){
        // t es la aridad: maximo de hijos por nodo.
        // Se deja una posicion extra de claves para poder dividir nodos llenos.
        this.claves = new int[t];
        this.hijos = new NodoB[t+1];
        this.hoja = hoja;
        this.nClaves = 0;
    }

    public int[] getClaves() {
        return this.claves;
    }

    public void setClaves(int[] claves) {
        this.claves = claves;
    }

    public NodoB[] getHijos() {
        return this.hijos;
    }

    public void setHijos(NodoB[] hijos) {
        this.hijos = hijos;
    }

    public int getNClaves() {
        return this.nClaves;
    }

    public void setNClaves(int nClaves) {
        this.nClaves = nClaves;
    }

    public boolean isHoja() {
        return this.hoja;
    }

    public boolean getHoja() {
        return this.hoja;
    }

    public void setHoja(boolean hoja) {
        this.hoja = hoja;
    }



}
