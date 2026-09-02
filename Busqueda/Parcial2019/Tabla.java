package Parcial2019;

public class Tabla {

    public static class Automovil {

        private int patente;

        public Automovil(int patente) {
            this.patente = patente;
        }
        public int getPatente() {
            return this.patente;
        }
    }

    Automovil[] tablaHash;
    int tam = 101;

    public Tabla() {
        this.tablaHash = new Automovil[tam];
        for (int i = 0; i < tam; i++) {
            this.tablaHash[i] = null;
        }
    }

    private int hash(int key) {
        return key % this.tam;
    }

    public void insertConPruebaCuadratica(Automovil key) {
        int index = this.hash(key.patente);
        int cont = 1;
        while (this.tablaHash[index] != null) {
            cont = (int) Math.pow(cont, 2);
            if (index + cont > this.tam - 1) {
                index = 0;
            } else {
                index = index + cont;
                cont++;
            }
        }
        this.tablaHash[index] = key;
    }
    public int sequentialSearch( int search) {
        int pos = 0;
        while (pos < this.tam) {
            if (this.tablaHash[pos].getPatente() == search) {
                return pos;
            }
            pos++;
        }
        return -1;
    }



    public static void main(String[] args) {
        Tabla t = new Tabla();
        Automovil a1 = new Automovil(123456);
        Automovil a2 = new Automovil(123456);
        Automovil a3 = new Automovil(123456);
        Automovil a4 = new Automovil(123456);
        Automovil a5 = new Automovil(123456);
        t.insertConPruebaCuadratica(a1);
        t.insertConPruebaCuadratica(a2);
        t.insertConPruebaCuadratica(a3);
        t.insertConPruebaCuadratica(a4);
        t.insertConPruebaCuadratica(a5);

        t.sequentialSearch(a1.getPatente());

    }

}
