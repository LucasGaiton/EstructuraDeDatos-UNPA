import java.util.List;
import java.util.Scanner;


public class TrieMain {
    public static void main(String[] args) {
        TrieLinkedList trie = new TrieLinkedList();
        Scanner scanner = new Scanner(System.in);
        String option;

        System.out.println("Bienvenido al programa de TrieLinkedList.");

        do {
            System.out.println("\nMenú:");
            System.out.println("1. Insertar palabra");
            System.out.println("2. Buscar palabra");
            System.out.println("3. Mostrar cantidad de palabras almacenadas");
            System.out.println("4. Listar todas las palabras");
            System.out.println("5. Mostrar palabras con un prefijo");
            System.out.println("6. Buscar palabras similares");
            System.out.println("7. Contar prefijos distintos");
            System.out.println("8. Contar nodos");

            System.out.println("9. Salir");
            System.out.print("Elija una opción: ");
            option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.print("Ingrese la palabra a insertar: ");
                    String wordToInsert = scanner.nextLine();
                    trie.insertWord(wordToInsert);
                    System.out.println("Palabra '" + wordToInsert + "' insertada correctamente.");
                    break;

                case "2":
                    System.out.print("Ingrese la palabra a buscar: ");
                    String wordToSearch = scanner.nextLine();
                    if (trie.searchWord(wordToSearch)) {
                        System.out.println("La palabra '" + wordToSearch + "' se encuentra en el trie.");
                    } else {
                        System.out.println("La palabra '" + wordToSearch + "' no se encuentra en el trie.");
                    }
                    break;

                case "3":
                    System.out.println("Cantidad de palabras almacenadas: " + trie.countWords());
                    break;

                case "4":
                    System.out.println("Palabras almacenadas:");
                    for (String word : trie.listWords()) {
                        System.out.println(word);
                    }
                    break;

                case "5":
                    System.out.print("Ingrese el prefijo: ");
                    String prefix = scanner.nextLine();
                    List<String> wordsWithPrefix = trie.wordsWithPrefix(prefix);
                    if (wordsWithPrefix.isEmpty()) {
                        System.out.println("No hay palabras con el prefijo '" + prefix + "'.");
                    } else {
                        System.out.println("Palabras con el prefijo '" + prefix + "':");
                        for (String word : wordsWithPrefix) {
                            System.out.println(word);
                        }
                    }
                    break;

                case "6":
                    System.out.print("Ingrese la palabra para buscar similares: ");
                    String similarWord = scanner.nextLine();
                    List<String> similarWords = trie.similarWords(similarWord);
                    if (similarWords.isEmpty()) {
                        System.out.println("No se encontraron palabras similares.");
                    } else {
                        System.out.println("Palabras similares a '" + similarWord + "':");
                        for (String word : similarWords) {
                            System.out.println(word);
                        }
                    }
                    break;

                case "7":
                    System.out.println("Cantidad de prefijos distintos: " + trie.countPrefixes());
                    break;
                
                case "8":
                    System.out.println("Cantidad de nodo:"+ trie.countNodes());
                    break;

                case "9":
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (!option.equals("8"));

        scanner.close();
    }
}
