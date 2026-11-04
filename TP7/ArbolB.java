package TP7;

public class ArbolB {
    private NodoB raiz;
    private final int t;          // aridad: maximo de hijos por nodo
    private final int maxClaves;  // maximo normal de claves por nodo
    private final int minClaves;  // minimo de claves para nodos no raiz

    public ArbolB(int t) {
        if (t < 3) {
            throw new IllegalArgumentException("La aridad debe ser al menos 3.");
        }
        this.t = t;
        this.maxClaves = t - 1;
        this.minClaves = (t - 1) / 2;
        this.raiz = null;
    }

    public void insertar(int clave) {
        if (raiz == null) {
            raiz = new NodoB(t, true);
            raiz.getClaves()[0] = clave;
            raiz.setNClaves(1);
            return;
        }

        Division division = insertar(raiz, clave);

        // Si la raiz se dividio, se crea una nueva raiz.
        if (division != null) {
            NodoB nuevaRaiz = new NodoB(t, false);
            nuevaRaiz.getClaves()[0] = division.claveMedia;
            nuevaRaiz.getHijos()[0] = raiz;
            nuevaRaiz.getHijos()[1] = division.derecha;
            nuevaRaiz.setNClaves(1);
            raiz = nuevaRaiz;
        }
    }

    /**
     * Inserta una clave en el subarbol y devuelve una Division si el nodo
     * supero el maximo de claves. La division se propaga hacia arriba.
     */
    private Division insertar(NodoB nodo, int clave) {
        if (nodo.isHoja()) {
            int i = nodo.getNClaves() - 1;
            while (i >= 0 && clave < nodo.getClaves()[i]) {
                nodo.getClaves()[i + 1] = nodo.getClaves()[i];
                i--;
            }
            nodo.getClaves()[i + 1] = clave;
            nodo.setNClaves(nodo.getNClaves() + 1);
        } else {
            int i = 0;
            while (i < nodo.getNClaves() && clave > nodo.getClaves()[i]) {
                i++;
            }

            Division division = insertar(nodo.getHijos()[i], clave);

            if (division != null) {
                // Hacer lugar para la clave media y el nuevo hijo derecho.
                for (int j = nodo.getNClaves(); j > i; j--) {
                    nodo.getClaves()[j] = nodo.getClaves()[j - 1];
                }
                for (int j = nodo.getNClaves() + 1; j > i + 1; j--) {
                    nodo.getHijos()[j] = nodo.getHijos()[j - 1];
                }

                nodo.getClaves()[i] = division.claveMedia;
                nodo.getHijos()[i + 1] = division.derecha;
                nodo.setNClaves(nodo.getNClaves() + 1);
            }
        }

        if (nodo.getNClaves() > maxClaves) {
            return dividir(nodo);
        }
        return null;
    }

    /**
     * Divide un nodo que tiene maxClaves + 1 claves.
     * La clave media sube al padre; el nodo original queda como izquierda.
     */
    private Division dividir(NodoB nodo) {
        int medio = minClaves;
        int claveMedia = nodo.getClaves()[medio];

        NodoB derecha = new NodoB(t, nodo.isHoja());
        int nDerecha = nodo.getNClaves() - medio - 1;

        for (int i = 0; i < nDerecha; i++) {
            derecha.getClaves()[i] = nodo.getClaves()[medio + 1 + i];
        }

        if (!nodo.isHoja()) {
            for (int i = 0; i <= nDerecha; i++) {
                derecha.getHijos()[i] = nodo.getHijos()[medio + 1 + i];
            }
        }

        derecha.setNClaves(nDerecha);
        nodo.setNClaves(medio);

        return new Division(claveMedia, derecha);
    }

    public void mostrar() {
        mostrar(raiz, 0);
    }

    private void mostrar(NodoB nodo, int nivel) {
        if (nodo != null) {
            System.out.println("Nivel: " + nivel + ": ");
            for (int i = 0; i < nodo.getNClaves(); i++) {
                System.out.print(nodo.getClaves()[i] + " ");
            }
            System.out.println();

            for (int i = 0; i <= nodo.getNClaves(); i++) {
                mostrar(nodo.getHijos()[i], nivel + 1);
            }
        }
    }

    public boolean buscar(int clave) {
        return buscar(raiz, clave);
    }

    private boolean buscar(NodoB nodo, int clave) {
        if (nodo == null) {
            return false;
        }

        int i = 0;
        while (i < nodo.getNClaves() && clave > nodo.getClaves()[i]) {
            i++;
        }

        if (i < nodo.getNClaves() && clave == nodo.getClaves()[i]) {
            return true;
        }
        if (nodo.isHoja()) {
            return false;
        }
        return buscar(nodo.getHijos()[i], clave);
    }

    public int contarClaves() {
        return contarClaves(raiz);
    }

    private int contarClaves(NodoB nodo) {
        if (nodo == null) {
            return 0;
        }

        int count = nodo.getNClaves();
        for (int i = 0; i <= nodo.getNClaves(); i++) {
            count += contarClaves(nodo.getHijos()[i]);
        }
        return count;
    }

    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(NodoB nodo) {
        if (nodo == null) {
            return 0;
        }

        int count = 1;
        for (int i = 0; i <= nodo.getNClaves(); i++) {
            count += contarNodos(nodo.getHijos()[i]);
        }
        return count;
    }

    public int calcularAltura() {
        return calcularAltura(raiz);
    }

    private int calcularAltura(NodoB nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.isHoja()) {
            return 1;
        }
        return 1 + calcularAltura(nodo.getHijos()[0]);
    }

    public void borrarClave(int clave) {
        if (raiz == null) {
            System.out.println("El arbol esta vacio.");
            return;
        }

        boolean eliminada = borrar(raiz, clave);

        if (!eliminada) {
            System.out.println("La clave " + clave + " no existe en el arbol.");
        }

        // Si la raiz quedo sin claves, se baja un nivel.
        while (raiz != null && raiz.getNClaves() == 0) {
            if (raiz.isHoja()) {
                raiz = null;
            } else {
                raiz = raiz.getHijos()[0];
            }
        }
    }

    private boolean borrar(NodoB nodo, int clave) {
        int idx = encontrarClave(nodo, clave);

        // Caso 1: la clave esta en el nodo actual.
        if (idx < nodo.getNClaves() && nodo.getClaves()[idx] == clave) {
            if (nodo.isHoja()) {
                borrarDeHoja(nodo, idx);
                return true;
            }

            if (nodo.getHijos()[idx].getNClaves() > minClaves) {
                int predecesor = obtenerPredecesor(nodo, idx);
                nodo.getClaves()[idx] = predecesor;
                boolean eliminada = borrar(nodo.getHijos()[idx], predecesor);
                if (nodo.getHijos()[idx].getNClaves() < minClaves) {
                    arreglar(nodo, idx);
                }
                return eliminada;
            } else if (nodo.getHijos()[idx + 1].getNClaves() > minClaves) {
                int sucesor = obtenerSucesor(nodo, idx);
                nodo.getClaves()[idx] = sucesor;
                boolean eliminada = borrar(nodo.getHijos()[idx + 1], sucesor);
                if (nodo.getHijos()[idx + 1].getNClaves() < minClaves) {
                    arreglar(nodo, idx + 1);
                }
                return eliminada;
            } else {
                // Ambos hijos tienen el minimo: se fusionan con la clave del padre.
                NodoB fusionado = fusionar(nodo, idx);
                boolean eliminada = borrar(fusionado, clave);

                // Con aridad impar la fusion puede dejar una clave de mas.
                if (fusionado.getNClaves() > maxClaves) {
                    Division division = dividir(fusionado);
                    insertarEnPadre(nodo, idx, division.claveMedia, division.derecha);
                }
                return eliminada;
            }
        }

        // Caso 2: la clave no esta en el nodo actual.
        if (nodo.isHoja()) {
            return false;
        }

        int hijo = idx;
        boolean eliminada = borrar(nodo.getHijos()[hijo], clave);

        if (nodo.getHijos()[hijo].getNClaves() < minClaves) {
            arreglar(nodo, hijo);
        }
        return eliminada;
    }

    private int encontrarClave(NodoB nodo, int clave) {
        int idx = 0;
        while (idx < nodo.getNClaves() && nodo.getClaves()[idx] < clave) {
            idx++;
        }
        return idx;
    }

    private void borrarDeHoja(NodoB nodo, int idx) {
        for (int i = idx + 1; i < nodo.getNClaves(); i++) {
            nodo.getClaves()[i - 1] = nodo.getClaves()[i];
        }
        nodo.setNClaves(nodo.getNClaves() - 1);
    }

    private int obtenerPredecesor(NodoB nodo, int idx) {
        NodoB actual = nodo.getHijos()[idx];
        while (!actual.isHoja()) {
            actual = actual.getHijos()[actual.getNClaves()];
        }
        return actual.getClaves()[actual.getNClaves() - 1];
    }

    private int obtenerSucesor(NodoB nodo, int idx) {
        NodoB actual = nodo.getHijos()[idx + 1];
        while (!actual.isHoja()) {
            actual = actual.getHijos()[0];
        }
        return actual.getClaves()[0];
    }

    /**
     * Corrige un hijo que quedo con menos claves que el minimo.
     * Primero intenta prestar de un hermano; si no puede, fusiona.
     */
    private void arreglar(NodoB padre, int idx) {
        NodoB hijo = padre.getHijos()[idx];

        if (hijo.getNClaves() >= minClaves) {
            return;
        }

        if (idx > 0 && padre.getHijos()[idx - 1].getNClaves() > minClaves) {
            tomarPrestadoDeAnterior(padre, idx);
        } else if (idx < padre.getNClaves()
                && padre.getHijos()[idx + 1].getNClaves() > minClaves) {
            tomarPrestadoDeSiguiente(padre, idx);
        } else if (idx < padre.getNClaves()) {
            fusionar(padre, idx);
        } else {
            fusionar(padre, idx - 1);
        }
    }

    /**
     * Fusiona el hijo idx, la clave idx del padre y el hijo idx+1.
     * Devuelve el nodo fusionado.
     */
    private NodoB fusionar(NodoB padre, int idx) {
        NodoB hijo = padre.getHijos()[idx];
        NodoB hermano = padre.getHijos()[idx + 1];

        int nHijo = hijo.getNClaves();
        hijo.getClaves()[nHijo] = padre.getClaves()[idx];

        for (int i = 0; i < hermano.getNClaves(); i++) {
            hijo.getClaves()[nHijo + 1 + i] = hermano.getClaves()[i];
        }

        if (!hijo.isHoja()) {
            for (int i = 0; i <= hermano.getNClaves(); i++) {
                hijo.getHijos()[nHijo + 1 + i] = hermano.getHijos()[i];
            }
        }

        for (int i = idx + 1; i < padre.getNClaves(); i++) {
            padre.getClaves()[i - 1] = padre.getClaves()[i];
        }
        for (int i = idx + 2; i <= padre.getNClaves(); i++) {
            padre.getHijos()[i - 1] = padre.getHijos()[i];
        }

        hijo.setNClaves(nHijo + hermano.getNClaves() + 1);
        padre.setNClaves(padre.getNClaves() - 1);

        return hijo;
    }

    private void tomarPrestadoDeAnterior(NodoB padre, int idx) {
        NodoB hijo = padre.getHijos()[idx];
        NodoB hermano = padre.getHijos()[idx - 1];

        for (int i = hijo.getNClaves() - 1; i >= 0; i--) {
            hijo.getClaves()[i + 1] = hijo.getClaves()[i];
        }

        if (!hijo.isHoja()) {
            for (int i = hijo.getNClaves(); i >= 0; i--) {
                hijo.getHijos()[i + 1] = hijo.getHijos()[i];
            }
        }

        hijo.getClaves()[0] = padre.getClaves()[idx - 1];

        if (!hijo.isHoja()) {
            hijo.getHijos()[0] = hermano.getHijos()[hermano.getNClaves()];
        }

        padre.getClaves()[idx - 1] = hermano.getClaves()[hermano.getNClaves() - 1];
        hijo.setNClaves(hijo.getNClaves() + 1);
        hermano.setNClaves(hermano.getNClaves() - 1);
    }

    private void tomarPrestadoDeSiguiente(NodoB padre, int idx) {
        NodoB hijo = padre.getHijos()[idx];
        NodoB hermano = padre.getHijos()[idx + 1];

        hijo.getClaves()[hijo.getNClaves()] = padre.getClaves()[idx];

        if (!hijo.isHoja()) {
            hijo.getHijos()[hijo.getNClaves() + 1] = hermano.getHijos()[0];
        }

        padre.getClaves()[idx] = hermano.getClaves()[0];

        for (int i = 1; i < hermano.getNClaves(); i++) {
            hermano.getClaves()[i - 1] = hermano.getClaves()[i];
        }

        if (!hermano.isHoja()) {
            for (int i = 1; i <= hermano.getNClaves(); i++) {
                hermano.getHijos()[i - 1] = hermano.getHijos()[i];
            }
        }

        hijo.setNClaves(hijo.getNClaves() + 1);
        hermano.setNClaves(hermano.getNClaves() - 1);
    }

    /**
     * Inserta una clave media y su hijo derecho en el padre, en la posicion idx.
     * Se usa cuando un nodo fusionado se vuelve a dividir.
     */
    private void insertarEnPadre(NodoB padre, int idx, int claveMedia, NodoB derecha) {
        int n = padre.getNClaves();

        for (int i = n; i > idx; i--) {
            padre.getClaves()[i] = padre.getClaves()[i - 1];
        }
        for (int i = n + 1; i > idx + 1; i--) {
            padre.getHijos()[i] = padre.getHijos()[i - 1];
        }

        padre.getClaves()[idx] = claveMedia;
        padre.getHijos()[idx + 1] = derecha;
        padre.setNClaves(n + 1);
    }

    private static class Division {
        final int claveMedia;
        final NodoB derecha;

        Division(int claveMedia, NodoB derecha) {
            this.claveMedia = claveMedia;
            this.derecha = derecha;
        }
    }
}
