public class ArbolB {
    private NodoB raiz;
    private final int t;

    public ArbolB(int t){
        this.raiz = null;
        this.t = t;
    }

    public void insertar(int clave){
        if (raiz == null) {
            raiz = new NodoB(t, true);
            raiz.getClaves()[0] = clave;
            raiz.setNClaves(1);
        } else {
            if (raiz.getNClaves() == 2 * t - 1) {
                NodoB nuevaRaiz = new NodoB(t, false);
                nuevaRaiz.getHijos()[0] = raiz;
                divHijo(nuevaRaiz, 0, raiz);
                int i = (nuevaRaiz.getClaves()[0] < clave) ? 1 : 0;
                inserNoLleno(nuevaRaiz.getHijos()[i], clave);
                raiz = nuevaRaiz;
            } else {
                inserNoLleno(raiz, clave);
            }
        }
    }
    
    private void inserNoLleno(NodoB nodo, int clave){
        int i = nodo.getNClaves() -1;
        if(nodo.getHoja()){
            while (i >= 0 && clave < nodo.getClaves()[i]){
                nodo.getClaves()[i+1] = nodo.getClaves()[i];
                i--;
            }
            nodo.getClaves()[i+1]=clave;
            nodo.setNClaves(nodo.getNClaves()+ 1);
        }else {
            while(i >= 0 && clave < nodo.getClaves()[i]) i--;
            i++;
            if(nodo.getHijos()[i].getNClaves() == 2*t-1){
                divHijo(nodo, i, nodo.getHijos()[i]);
                if(clave > nodo.getClaves()[i]) i++;
            }
        }
        inserNoLleno(nodo.getHijos()[i], clave);
    }

    private void divHijo(NodoB padre, int i, NodoB hijo){
        NodoB nuevo = new NodoB(t, hijo.getHoja());
        nuevo.setNClaves(t-1);

        System.arraycopy(hijo.getClaves(), t, nuevo.getClaves(), 0, t-1);
        if(!hijo.getHoja()){
            System.arraycopy(hijo.getHijos(), t, nuevo.getHijos(),0,t);
        }
        hijo.setNClaves(t-1);

        System.arraycopy(padre.getHijos(), i + 1, padre.getHijos(), i + 2, padre.getNClaves() - i);
        padre.getHijos()[i + 1] = nuevo;

        System.arraycopy(padre.getClaves(), i, padre.getClaves(), i + 1, padre.getNClaves() - i);
        padre.getClaves()[i] = hijo.getClaves()[t - 1];
        padre.setNClaves(padre.getNClaves() + 1);
    }

    public void mostrar(){
        mostrar(raiz,0);
    }
    private void mostrar(NodoB nodo, int nivel){
        if(nodo != null){
            System.out.println("Nivel: "+nivel+ ": ");
            for (int i = 0; i < nodo.getNClaves(); i++) System.out.print(nodo.getClaves()[i] + " ");
            System.out.println();
            for (int i = 0; i <= nodo.getNClaves(); i++) mostrar(nodo.getHijos()[i], nivel + 1);
        }
    }

    public boolean buscar(int clave){
        return buscar(raiz, clave);
    }

    private boolean buscar(NodoB nodo, int clave){
        if(nodo==null){
            return false;
        }    
        int i=0;
        while (i < nodo.getNClaves() && clave > nodo.getClaves()[i]) i++;
        if (i < nodo.getNClaves() && clave == nodo.getClaves()[i]) return true;
        if (nodo.isHoja()) return false;
        return buscar(nodo.getHijos()[i], clave);

    }

    public int contarClaves() {
        return contarClaves(raiz);
    }

    private int contarClaves(NodoB nodo) {
        if (nodo == null) return 0;
        int count = nodo.getNClaves();
        for (int i = 0; i <= nodo.getNClaves(); i++) count += contarClaves(nodo.getHijos()[i]);
        return count;
    }

    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(NodoB nodo) {
        if (nodo == null) return 0;
        int count = 1;
        for (int i = 0; i <= nodo.getNClaves(); i++) count += contarNodos(nodo.getHijos()[i]);
        return count;
    }

    public int calcularAltura() {
        return calcularAltura(raiz);
    }

    private int calcularAltura(NodoB nodo) {
        if (nodo == null) return 0;
        if (nodo.isHoja()) return 1;
        return 1 + calcularAltura(nodo.getHijos()[0]);
    }
    public void borrarClave(int clave) {
        if (raiz == null) {
            System.out.println("El árbol está vacío.");
            return;
        }
    
        borrarClave(raiz, clave);
    
        // Si el nodo raíz no tiene claves y no es una hoja, la raíz cambia
        if (raiz.getNClaves() == 0) {
            if (raiz.isHoja()) {
                raiz = null; // El árbol se vació
            } else {
                raiz = raiz.getHijos()[0]; // Cambiar la raíz al primer hijo
            }
        }
    }
    
    private void borrarClave(NodoB nodo, int clave) {
        int idx = encontrarClave(nodo, clave);
    
        // Caso 1: La clave está en el nodo actual
        if (idx < nodo.getNClaves() && nodo.getClaves()[idx] == clave) {
            if (nodo.isHoja()) {
                borrarDeHoja(nodo, idx);
            } else {
                borrarDeNoHoja(nodo, idx);
            }
        } else {
            // Caso 2: La clave no está en el nodo actual
            if (nodo.isHoja()) {
                System.out.println("La clave " + clave + " no existe en el árbol.");
                return;
            }
    
            // Determinar si el hijo donde posiblemente está la clave tiene suficientes claves
            boolean esUltimoHijo = (idx == nodo.getNClaves());
            if (nodo.getHijos()[idx].getNClaves() < t) {
                llenar(nodo, idx);
            }
    
            // Después de llenar, la clave puede haberse movido al nodo actual
            if (esUltimoHijo && idx > nodo.getNClaves()) {
                borrarClave(nodo.getHijos()[idx - 1], clave);
            } else {
                borrarClave(nodo.getHijos()[idx], clave);
            }
        }
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
    
    private void borrarDeNoHoja(NodoB nodo, int idx) {
        int clave = nodo.getClaves()[idx];
    
        if (nodo.getHijos()[idx].getNClaves() >= t) {
            int predecesor = obtenerPredecesor(nodo, idx);
            nodo.getClaves()[idx] = predecesor;
            borrarClave(nodo.getHijos()[idx], predecesor);
        } else if (nodo.getHijos()[idx + 1].getNClaves() >= t) {
            int sucesor = obtenerSucesor(nodo, idx);
            nodo.getClaves()[idx] = sucesor;
            borrarClave(nodo.getHijos()[idx + 1], sucesor);
        } else {
            fusionar(nodo, idx);
            borrarClave(nodo.getHijos()[idx], clave);
        }
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
    
    private void llenar(NodoB nodo, int idx) {
        if (idx != 0 && nodo.getHijos()[idx - 1].getNClaves() >= t) {
            tomarPrestadoDeAnterior(nodo, idx);
        } else if (idx != nodo.getNClaves() && nodo.getHijos()[idx + 1].getNClaves() >= t) {
            tomarPrestadoDeSiguiente(nodo, idx);
        } else {
            if (idx != nodo.getNClaves()) {
                fusionar(nodo, idx);
            } else {
                fusionar(nodo, idx - 1);
            }
        }
    }
    
    private void tomarPrestadoDeAnterior(NodoB nodo, int idx) {
        NodoB hijo = nodo.getHijos()[idx];
        NodoB hermano = nodo.getHijos()[idx - 1];
    
        for (int i = hijo.getNClaves() - 1; i >= 0; i--) {
            hijo.getClaves()[i + 1] = hijo.getClaves()[i];
        }
    
        if (!hijo.isHoja()) {
            for (int i = hijo.getNClaves(); i >= 0; i--) {
                hijo.getHijos()[i + 1] = hijo.getHijos()[i];
            }
        }
    
        hijo.getClaves()[0] = nodo.getClaves()[idx - 1];
    
        if (!nodo.isHoja()) {
            hijo.getHijos()[0] = hermano.getHijos()[hermano.getNClaves()];
        }
    
        nodo.getClaves()[idx - 1] = hermano.getClaves()[hermano.getNClaves() - 1];
        hijo.setNClaves(hijo.getNClaves() + 1);
        hermano.setNClaves(hermano.getNClaves() - 1);
    }
    
    private void tomarPrestadoDeSiguiente(NodoB nodo, int idx) {
        NodoB hijo = nodo.getHijos()[idx];
        NodoB hermano = nodo.getHijos()[idx + 1];
    
        hijo.getClaves()[hijo.getNClaves()] = nodo.getClaves()[idx];
    
        if (!hijo.isHoja()) {
            hijo.getHijos()[hijo.getNClaves() + 1] = hermano.getHijos()[0];
        }
    
        nodo.getClaves()[idx] = hermano.getClaves()[0];
    
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
    
    private void fusionar(NodoB nodo, int idx) {
        NodoB hijo = nodo.getHijos()[idx];
        NodoB hermano = nodo.getHijos()[idx + 1];
    
        hijo.getClaves()[t - 1] = nodo.getClaves()[idx];
    
        for (int i = 0; i < hermano.getNClaves(); i++) {
            hijo.getClaves()[i + t] = hermano.getClaves()[i];
        }
    
        if (!hijo.isHoja()) {
            for (int i = 0; i <= hermano.getNClaves(); i++) {
                hijo.getHijos()[i + t] = hermano.getHijos()[i];
            }
        }
    
        for (int i = idx + 1; i < nodo.getNClaves(); i++) {
            nodo.getClaves()[i - 1] = nodo.getClaves()[i];
        }
    
        for (int i = idx + 2; i <= nodo.getNClaves(); i++) {
            nodo.getHijos()[i - 1] = nodo.getHijos()[i];
        }
    
        hijo.setNClaves(hijo.getNClaves() + hermano.getNClaves() + 1);
        nodo.setNClaves(nodo.getNClaves() - 1);
    }

}