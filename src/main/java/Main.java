import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("In which language do you want to play? / En qué idioma quiere jugar?");
        System.out.println("A) English");
        System.out.println("B) Español");
        System.out.print("--> ");
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8.displayName());  // Create a Scanner object
        String language = sc.nextLine();  // Read user input
        language = language.toLowerCase(); // covert to lowercase

        Wordle newGame;

        while (! Wordle.languagePossibilities.contains(language) ) {
            System.out.println("The language selected is not one of the options available. / El idioma elegido no es una de las opciones disponibles.");
            System.out.println("Please, pick one of the following options: / Por favor, elija una de las siguientes opciones:");
            System.out.println("A) English");
            System.out.println("B) Español");
            System.out.print("--> ");
            language = sc.nextLine();  // Read user input
            language = language.toLowerCase(); // covert to lowercase
        }

        if (language.equals("english") || language.equals("a")) {
            newGame = new English();
        } else { //(language.equals("español") || language.equals("b"))
            newGame = new Spanish();
        }

        newGame.play();

    }
}


