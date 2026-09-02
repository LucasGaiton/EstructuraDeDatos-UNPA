
public class TablaHash {
    int[] tablaHash;
    int tam = 101; // tamaño
    public TablaHash(String fisico){
        tablaHash = new int[tam];
        for (int i = 0; i < tam; i++){
            tablaHash[i] = -1;
        }
    }

    //*************** secuencial ***************
    public static int sequentialSearch(int[] vec, int search) {
        int pos = 0;
        while (pos < vec.length) {
            if (vec[pos] == search) {
                return pos;
            }
            pos++;
        }
        return -1;
    }


    //*************** binaria ***************
    public static int binarySearch(int[] vec, int search) {
        int first = 0;
        int mid;
        int last = vec.length - 1;
        while (first <= last) {
            mid = (first + last) / 2;
            if (vec[mid] == search) {
                return mid; 
            }else if (vec[mid] > search) {
                last = mid - 1; 
            }else {
                first = mid + 1;
            }
        }
        return -1;
    }

    //*************** interpolacion ***************
    public static int interpolationSearch(int[] vec, int search) {
        int first = 0;
        int mid;
        int last = vec.length - 1;
        while (search >= vec[first] & search <= vec[last]) {
            mid = first + (int) Math.abs(Math.floor((search - vec[first]) * (last - first) / (vec[last] - vec[first])));
            if (search == vec[mid]) {
                return mid; 
            }else if (search < vec[mid]) {
                last = mid - 1; 
            }else {
                first = mid + 1;
            }
        }
        return -1;
    }

    //*************** fibonacci ***************

    public static int fibonacciSearch(int[] vec, int search) {
    //for(j=1; fib(j)<n; j++)
        int f1 = 1;
        int f2 = 1;
        int faux;
        int mid;
        int length = vec.length - 1;
        while (f1 < length) {//encuentra a f1 tal que f1>=length
            f1 = f1 + f2; //siguiente número Fibonacci
            f2 = f1 - f2; //guarda al f1 anterior
        }
        f1 = f1 - f2; //encuentra el número Fibonacci más chico
        f2 = f2 - f1; //f1=fib(j-2), f2=fib(j-3)
        mid = length - f1 + 1;
        while (search != vec[mid]) //si no lo encuentra
        {
            if (mid < 0 | search > vec[mid]) {//busca en la mitad más baja
                if (f1 == 1) //si no lo encuentra retorna -1
                {
                    return -1;
                }
                mid = mid + f2; //disminuye los números de Fibonacci
                f1 = f1 - f2;
                f2 = f2 - f1;
            } else {//busca en la mitad más alta
                if (f2 == 0) //si no lo encuentra retorna -1
                {
                    return -1;
                }
                mid = mid - f2; //disminuye los números de Fibonacci
                faux = f1 - f2; //esta vez, disminuye mas
                f1 = f2; //para el arreglo más chico
                f2 = faux;
            }
        }
        return mid;
    }

    //*************** restas sucesivas ***************
    private int restasSucesivas(int clave){
        return clave % tam;
    }

    void addRestasSucesivas(int numero){
        int hash = restasSucesivas(numero);
        while (tablaHash[hash] != -1){
            hash = (hash - 1 + tam) % tam; // retrocede en la tabla circular
        }
        tablaHash[hash] = numero;
    }


    //*************** division/modulo ***************

    private int modulo(int clave){
        return clave % tam;
    }
    void addMod(int numero){
        int hash = modulo(numero);
        while (tablaHash[hash] != -1){
            hash = (hash + 1) % tam; // incremento
        }
        tablaHash[hash] = numero;
    }
    //*************** medio cuadrado ***************
    private int medioCuadrado(int clave){
        int cuadrado = clave * clave;
        String cuadradoStr = String.valueOf(cuadrado);
        int medioInicio = (cuadradoStr.length() / 2) - 1;
        int medioFin = medioInicio + 2;
        int digitosCentrales = Integer.parseInt(cuadradoStr.substring(medioInicio, medioFin));
        return digitosCentrales % tam;
    }
    public void addMid(int numero){
        int hash = medioCuadrado(numero);
        while (tablaHash[hash] != -1){
            hash = (hash + 1) % tam;
        }
        tablaHash[hash] = numero;
    }
    //*************** truncamiento ***************
    private int truncamiento(int clave) {
        String claveStr = String.valueOf(clave);
        String trunc = claveStr.substring(0, 3); // usando los primeros 3 dígitos
        return Integer.parseInt(trunc) % tam;
    }
    public void addTrunc(int numero) {
        int hash = truncamiento(numero);
        while (tablaHash[hash] != -1) {
            hash = (hash + 1) % tam;
        }
        tablaHash[hash] = numero;
    }

    //*************** superposicion ***************
    private int superposicion(int clave) {
        String claveStr = String.valueOf(clave);
        int mitad = claveStr.length() / 2;
        String parte1 = claveStr.substring(0, mitad);
        String parte2 = claveStr.substring(mitad);
        return (Integer.parseInt(parte1) + Integer.parseInt(parte2)) % tam;
    }

    public void addSuper(int numero) {
        int hash = superposicion(numero);
        while (tablaHash[hash] != -1) {
            hash = (hash + 1) % tam;
        }
        tablaHash[hash] = numero;
    }


    //*************** hashing lineal ***************
    private int hashLineal(int clave) {
        return clave % tam;
    }

    public void addLineal(int numero) {
        int hash = hashLineal(numero);
        while (tablaHash[hash] != -1) {
            hash = (hash + 1) % tam; // si hay colisión, se incrementa linealmente
        }
        tablaHash[hash] = numero;
    }


    //*************** rehashing cuadratico ***************
    
    private int hash(int clave){
        return (7 * clave + 1) % 10;
    }
    void addRehashingC(int numero){
        int hash = hash(numero);
        int i = 1;
        while (tablaHash[hash] != -1){
            hash = (hash + i * i) % tam; //rehashing cuadrático
            i++;
        }
        tablaHash[hash] = numero;
    }

    //*************** doble direccionamiento hash ***************

    private int hash1(int clave){
        return (7 * clave + 1) % tam; //H1(K) = (7K + 1) % 10
    }
    private int hash2(int clave){
        return 1 + (clave % (tam - 1)); //H2(K) = 1 + (K % (tam - 1))
    }
    void addDoubleHash(int numero){
        int hash = hash1(numero);
        int incremento = hash2(numero);
        int i = 0;
        while (tablaHash[hash] != -1){
            hash = (hash + incremento) % tam;
            i++;
        }
        tablaHash[hash] = numero;
    }

    //*************** transformacion de claves ***************
    private int transformarClave(int clave) {
        clave = clave^(clave >> 4);  // XOR con un desplazamiento
        // clave = clave * 2654435761;    // Multiplicación con un número primo
        return clave % tam;
    }
    
    void addTransformacion(int numero) {
        int hash = transformarClave(numero);
        while (tablaHash[hash] != -1) {
            hash = (hash + 1) % tam; // Recorre en caso de colisiones
        }
        tablaHash[hash] = numero;
    }

    //*************** rehashing agrupamiento ***************
    private int rehashingAgrupado(int clave, int bloque) {
        return (clave % tam) / bloque; // Devuelve el grupo
    }
    void addRehashAgrupado(int numero, int bloque) {
        int hash = rehashingAgrupado(numero, bloque);
        while (tablaHash[hash] != -1) {
            hash = (hash + 1) % tam; // En caso de colisión, incrementamos
        }
        tablaHash[hash] = numero;
    }

    //*************** cubos o rehashing cubico ***************
    private int hashCubo(int clave) {
        return (clave * clave * clave) % tam; // Usa el cubo del número como función hash
    }
    void addCubo(int numero) {
        int hash = hashCubo(numero);
        while (tablaHash[hash] != -1) {
            hash = (hash + 1) % tam; // Recorre en caso de colisión
        }
        tablaHash[hash] = numero;
    }
}



//*************** clase de tablas hash abiertas ***************

//import java.util.LinkedList; // AGREGAR PARA LAS TABLAS HASH ABIERTAS
class TablaHashAbierta {
    LinkedList<Integer>[] tablaHash;
    int tam; // tamaño de la tabla

    public TablaHashAbierta(int n) {
        this.tam = n;
        this.tablaHash = new LinkedList[this.tam];
        for (int i = 0; i < this.tam; i++) {
            tablaHash[i] = new LinkedList<>();
        }
    }

    private int hash(int clave) {
        return clave % this.tam;
    }

    void addAbierta(int numero) {
        int hash = hash(numero);
        tablaHash[hash].add(numero); // se agrega a la lista enlazada correspondiente
    }

    boolean searchAbierta(int numero) {
        int hash = hash(numero);
        return tablaHash[hash].contains(numero); // búsqueda en la lista enlazada
    }
}

//*************** clase desbordamiento ***************

// import java.util.ArrayList; // AGREGAR PARA EL DESBORDAMIENTO
class TablaHashDesbordamiento {
    int[] tablaHash;
    ArrayList<Integer> desbordamiento; // arreglo para almacenar valores que no entran en la tabla principal
    int tam = 101;

    public TablaHashDesbordamiento() {
        this.tablaHash = new int[this.tam];
        desbordamiento = new ArrayList<>();
        for (int i = 0; i < this.tam; i++) {
            tablaHash[i] = -1;
        }
    }

    private int hash(int clave) {
        return clave % this.tam;
    }

    void addDesbordamiento(int numero) {
        int hash = hash(numero);
        if (tablaHash[hash] == -1) {
            tablaHash[hash] = numero;
        } else {
            desbordamiento.add(numero);
        }
    }

    boolean searchDesbordamiento(int numero) {
        int hash = hash(numero);
        if (tablaHash[hash] == numero) {
            return true;
        } else {
            return desbordamiento.contains(numero);
        }
    }
}