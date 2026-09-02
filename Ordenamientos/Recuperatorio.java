public class Recuperatorio {

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
        int pivote = vec[last]; // El pivote es el último elemento
        int left = first;      
        int right = last - 1; 

        // Partición
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

        interChange(vec, left, last);
        return left; 
    }

    public static void interChange(int[] vec, int pos1, int pos2) {
        int temp = vec[pos1];
        vec[pos1] = vec[pos2];
        vec[pos2] = temp;
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
