import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Spanish extends Wordle { // Spanish child class from parent class Wordle

    public Spanish() { //constructor
        super(); //use the constructor from parent class
        chosenWordListWithoutAccentsFileName = "diccionario-sin-tildes.txt";
        chosenWordListFileName = "diccionario-con-tildes.txt";
        userDictionaryWithoutAccentsFileName = "extended-diccionario-sin-tildes.txt";
        result = "Resultado: ";
        youWonMessage = "FELICITACIONES! HA GANADO! :)";
        youLostMessage = "HA PERDIDO :( LA PALABRA ELEGIDA POR EL JUEGO ES: ";
    }

    // METHODS

    // print instructions
    public void printInstructions() {
        System.out.println("El juego ha elegido una palabra de 5 letras para que adivine.");
        System.out.println("Tiene 6 intentos. En cada uno, el juego le indicará que letras tienen en común la palabra elegida y la adivinada:");
        System.out.println("- Las letras resaltadas en " + ANSI_GREEN_BACKGROUND + "verde" + ANSI_RESET + " están en el lugar correcto.");
        System.out.println("- Las letras resaltadas en " + ANSI_YELLOW_BACKGROUND + "amarillo" + ANSI_RESET + " están contenidas en la palabra elegida, pero es una posición diferente.");
        System.out.println("- Las letras resaltadas en " + ANSI_GREY_BACKGROUND + "gris" + ANSI_RESET + " no figuran en la palabra elegida.");
    }

    // ask the user for their first word
    public void askForFirstGuess() {
        System.out.println();
        System.out.println("Por favor, escriba su primer palabra:");
    }

    // verify the validity of the user word by length and check against available options
    public String obtainValidUserWord (List<String> wordList, int index) {
        Scanner myScanner = new Scanner(System.in, StandardCharsets.UTF_8.displayName());  // Create a Scanner object
        String userWord = myScanner.nextLine();  // Read user input
        String modifiedUserWord = userWord.toLowerCase(); // covert to lowercase
        modifiedUserWord = removeAccents(modifiedUserWord); // remove special characters


        // check the length of the word and if it exists
        while ((modifiedUserWord.length() != 5) || !(wordList.contains(modifiedUserWord))) {
            if ((modifiedUserWord.length() != 5)) {
                System.out.println("La palabra " + userWord + " no tiene 5 letras.");
            } else {
                System.out.println("La palabra " + userWord + " no está contenida dentro del listado de opciones.");
            }
            // Ask for a new word
            System.out.println("Por favor, ingrese una nueva palabra de 5 letras.");
            System.out.print("--> " + (index + 1) + ") ");
            userWord = myScanner.nextLine();
            modifiedUserWord = userWord.toLowerCase();
            modifiedUserWord = removeAccents(modifiedUserWord);

        }
        return modifiedUserWord;
    }

    // prints the coloured alphabet including Ñ
    public void printingColouredAlphabet(List<Character> greenLetters, List<Character> yellowLetters, List<Character> greyLetters) {
        char c;

        for (c = 'A'; c <= 'N'; ++c) {
            if (greenLetters.contains(c)) {
                System.out.print(ANSI_GREEN_BACKGROUND + c + ANSI_RESET + " ");
            } else if (yellowLetters.contains(c)) {
                System.out.print(ANSI_YELLOW_BACKGROUND + c + ANSI_RESET + " ");
            } else if (greyLetters.contains(c)) {
                System.out.print(ANSI_GREY_BACKGROUND + c + ANSI_RESET + " ");
            } else {
                System.out.print(c + " ");
            }
        }

        char enie = 'Ñ';

        if (greenLetters.contains(enie)) {
                System.out.print(ANSI_GREEN_BACKGROUND + enie + ANSI_RESET + " ");
        } else if (yellowLetters.contains(enie)) {
                System.out.print(ANSI_YELLOW_BACKGROUND + enie + ANSI_RESET + " ");
        } else if (greyLetters.contains(enie)) {
                System.out.print(ANSI_GREY_BACKGROUND + enie + ANSI_RESET + " ");
        } else {
                System.out.print(enie + " ");
        }

        for (c = 'O'; c <= 'Z'; ++c) {
            if (greenLetters.contains(c)) {
                System.out.print(ANSI_GREEN_BACKGROUND + c + ANSI_RESET + " ");
            } else if (yellowLetters.contains(c)) {
                System.out.print(ANSI_YELLOW_BACKGROUND + c + ANSI_RESET + " ");
            } else if (greyLetters.contains(c)) {
                System.out.print(ANSI_GREY_BACKGROUND + c + ANSI_RESET + " ");
            } else {
                System.out.print(c + " ");
            }
        }

    }

    public void printDefinitionLink (String randomChosenWord) { // prints the link to the dictionary definition of the chosen word
        System.out.println("La definición de la palabra: https://dle.rae.es/" + randomChosenWord);
    }


}


