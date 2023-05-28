import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class English extends Wordle {

    // variables

    public English() { //constructor
        super(); //use the constructor from parent class
        chosenWordListFileName = "dictionary.txt";
        chosenWordListWithoutAccentsFileName = "dictionary.txt";
        userDictionaryWithoutAccentsFileName = "extended-dictionary.txt";
        result = "Result: ";
        youWonMessage = "CONGRATULATIONS! YOU WON! :)";
        youLostMessage = "YOU LOST :( THE WORD CHOSEN BY THE GAME IS: ";
    }

    // METHODS

    // print instructions
    public void printInstructions() {
        System.out.println("The game has chosen a 5-letter word for you to guess.");
        System.out.println("You have 6 tries. In each guess, the game will confirm which letters the chosen word and the guessed word have in common:");
        System.out.println("- Letters highlighted in " + ANSI_GREEN_BACKGROUND + "green" + ANSI_RESET + " are in the correct place.");
        System.out.println("- Letters highlighted in " + ANSI_YELLOW_BACKGROUND + "yellow" + ANSI_RESET + " appear in the chosen word but in a different spot.");
        System.out.println("- Letters highlighted in " + ANSI_GREY_BACKGROUND + "grey" + ANSI_RESET + " do not appear in the chosen word.");
    }

    // ask the user for their first word
    public void askForFirstGuess() {
        System.out.println();
        System.out.println("Please write down your first guess:");
    }

    // verify the validity of the user word by length and check against available options
    public String obtainValidUserWord (List<String> wordList, int index) {
        Scanner myScanner = new Scanner(System.in, StandardCharsets.UTF_8.displayName());  // Create a Scanner object
        String userWord = myScanner.nextLine();  // Read user input
        userWord = userWord.toLowerCase(); // covert to lowercase

        // check the length of the word and if it exists
        while ((userWord.length() != 5) || !(wordList.contains(userWord))) {
            if ((userWord.length() != 5)) {
                System.out.println("The word " + userWord + " does not have 5 letters.");
            } else {
                System.out.println("The word " + userWord + " is not in the word list.");
            }
            // Ask for a new word
            System.out.println("Please, submit a new 5-letter word.");
            System.out.print("--> " + (index + 1) + ") ");
            userWord = myScanner.nextLine();
        }
        return userWord;
    }

    // print the alphabet with the associated colour for each letter
    public void printingColouredAlphabet(List<Character> greenLetters, List<Character> yellowLetters, List<Character> greyLetters) {
        char c;

        for (c = 'A'; c <= 'Z'; ++c) {
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
        System.out.println("The word's definition: https://www.merriam-webster.com/dictionary/" + randomChosenWord);
    }


}
