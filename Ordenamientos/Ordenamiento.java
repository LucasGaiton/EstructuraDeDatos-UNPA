
public class Ordenamiento {

    public static void Mostrar(int[] vec) {
        for (int i = 0; i < vec.length; i++) {
            System.out.println(vec[i]);
        }
    }

    public static int[] insertionSort(int[] vec) {
        for (int i = 1; i < vec.length; i++) {
            int aux = vec[i];
            int j = i - 1;
            while (j >= 0 && vec[j] > aux) {
                vec[j + 1] = vec[j];
                j -= 1;
            }
            vec[j + 1] = aux;
        }
        return vec;
    }

    public static int[] selectionSort(int[] vec) {
        for (int i = 0; i < vec.length - 1; i++) {
            int pos = i;
            for (int j = i + 1; j < vec.length; j++) {
                if (vec[j] < vec[pos]) {
                    pos = j;
                }
            }
            int aux = vec[i];
            vec[i] = vec[pos];
            vec[pos] = aux;
        }
        return vec;
    }

    public static int[] bubbleSort(int[] vec) {
        for (int i = vec.length - 1; i >= 1; i--) {
            for (int j = 0; j <= i - 1; j++) {
                if (vec[j] > vec[j + 1]) {
                    int aux = vec[j];
                    vec[j] = vec[j + 1];
                    vec[j + 1] = aux;
                }
            }
        }
        return vec;
    }

    public static int[] bubbleImprovedSort(int[] vec) {
        int i = vec.length - 1;
        boolean ordered;
        do {
            ordered = true;
            for (int j = 0; j <= i - 1; j++) {
                if (vec[j] > vec[j + 1]) {
                    int aux = vec[j];
                    vec[j] = vec[j + 1];
                    vec[j + 1] = aux;
                    ordered = false;
                }
            }
            i--;
        } while (i >= 1 & !ordered);
        return vec;
    }

    public static int[] mergeSort(int[] vec) {
        int[] tmp = new int[vec.length];
        return mergeSort(vec, tmp, 0, vec.length - 1);
    }

    private static int[] mergeSort(int[] vec, int[] tmp, int left, int right) {
        if (left < right) { // valida si tiene 2 elementos o más
            int center = (right + left) / 2;
            mergeSort(vec, tmp, left, center);
            mergeSort(vec, tmp, center + 1, right);
            return merge(vec, tmp, left, center, right);
        }
        return vec;
    }

    private static int[] merge(int[] vec, int[] tmp, int left, int center, int right) {
        int aptr = left;
        int bptr = center + 1;
        int cptr = left;
        while (aptr <= center & bptr <= right) {
            if (vec[aptr] < vec[bptr]) {
                tmp[cptr++] = vec[aptr++];
            } else {
                tmp[cptr++] = vec[bptr++];
            }
        }
        while (aptr <= center) {
            tmp[cptr++] = vec[aptr++];

        }
        while (bptr <= right) {
            tmp[cptr++] = vec[bptr++];

        }
        for (int i = left; i <= right; i++) {
            if (vec[i] != tmp[i] && vec[i] > tmp[i])
                System.out.println("se intercambia el " + vec[i] + " por " + tmp[i]);
            vec[i] = tmp[i];
        }
        return vec;
    }

    public static void heapSort(int[] vec) {
        int index;
        for (index = vec.length - 1; index >= 0; index--) {
            reHeapDown(vec, index, vec.length);
        }
        for (index = vec.length - 1; index > 0; index--) {
            int aux = vec[index];
            vec[index] = vec[0];
            vec[0] = aux;
            // interChange(vec, 0, index);
            reHeapDown(vec, 0, index);
        }
    }

    private static void reHeapDown(int vec[], int length, int index) {
        boolean done = false;
        int aux = vec[length];
        int parent = length;
        int child = 2 * (length + 1) - 1;
        while (child < index & !done) {
            if (child < index - 1) {
                if (vec[child] < vec[child + 1]) {
                    child++;
                }
            }
            if (aux >= vec[child]) {
                done = true;
            } else {
                vec[parent] = vec[child];
                parent = child;
                child = 2 * (parent + 1) - 1;
            }
        }
        vec[parent] = aux;
        for (int i = 0; i < vec.length; i++) {
            System.out.println(vec[i]);
        }

    }

    public static void quickSort(int[] vec) {
        quickSort(vec, 0, vec.length - 1);
    }

    public static void quickSort(int[] vec, int first, int last) {
        if (first < last) {
            int center = division(vec, first, last);
            quickSort(vec, first, center - 1);
            quickSort(vec, center + 1, last);
        }
    }

    private static int division(int[] vec, int first, int last) {
        int pivote = vec[last];
        int left = first;
        int right = last - 1 ;
        
        while (left <= right) {
            while (left <= right && vec[left] <= pivote) {
                left++;
            }
            while (left <= right && vec[right] > pivote) {
                right--;
            }
            if (left < right) {
                interChange(vec, left, right);
            }
        }
        interChange(vec, first, right);
        return right;
    }

    public static void interChange(int[] vec, int pos1, int pos2) {
        int temp = vec[pos1];
        vec[pos1] = vec[pos2];
        vec[pos2] = temp;
    }

    public static void shellSort(int[] vec) {
        int interval = vec.length / 2; // Comenzar con un intervalo de la mitad del tamaño del array
        while (interval > 0) {
            // Realiza la ordenación para cada subarray de tamaño 'interval'
            for (int i = interval; i < vec.length; i++) {
                int temp = vec[i]; // Almacenar el valor actual
                int j = i;

                // Comparar y mover los elementos del subarray
                while (j >= interval && vec[j - interval] > temp) {
                    vec[j] = vec[j - interval]; // Mover el elemento hacia adelante
                    j -= interval; // Mover hacia atrás en el subarray
                }
                vec[j] = temp; // Colocar el elemento actual en su posición correcta
            }
            interval /= 2; // Reducir el intervalo
        }
        for (int i = 0; i < vec.length; i++) {
            System.out.println(vec[i]);
        }

    }

    public static void shakerSort(int[] vec) {
        int left = 1;
        int right = vec.length - 1;
        int aux = vec.length - 1;
        do {
            for (int i = right; i >= left; i--)
                if (vec[i - 1] > vec[i]) {
                    interChange(vec, i - 1, i);
                    aux = i;
                }
            left = aux + 1;
            for (int i = left; i <= right; i++)
                if (vec[i - 1] > vec[i]) {
                    interChange(vec, i - 1, i);
                    aux = i;
                }
            right = aux - 1;
        } while (left < right);
    }

    public static void main(String[] args) {
        int[] vector = { 2, 0, 6, 4, 8, 9, 1 };
        quickSort(vector);

        // Mostrar(vector);
        System.out.println("ordenado");
        for (int num : vector) {
            System.out.print(num + " ");
        }

    }
}
