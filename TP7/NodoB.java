public class NodoB {
    int [] claves;
    NodoB[] hijos;
    int nClaves;
    boolean hoja;

    public NodoB(int t, boolean hoja){
        this.claves = new int[2*t-1];
        this.hijos = new NodoB[2*t];
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
