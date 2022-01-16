import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class Main {

    // Declaring the background colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_GREY_BACKGROUND = "\u001B[100m";

    // method that replaces a char in a string at a specific index
    public static String replaceChar(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }

    public static void main(String[] args) {

        // Declaring variables and arrays
        ArrayList<String> dictionary = new ArrayList<>();
        String chosenWord;
        ArrayList<Character> greenLetters = new ArrayList<>();
        ArrayList<Character> yellowLetters = new ArrayList<>();
        ArrayList<Character> greyLetters = new ArrayList<>();

        try {
            // Open and read the dictionary file
            InputStream in = Main.class.getClassLoader().getResourceAsStream("dictionary.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String strLine;

            //Read file line By line
            while ((strLine = reader.readLine()) != null) {
                dictionary.add(strLine);
                // If you want to print the content on the console: System.out.println(strLine);
            }
            //Close the input stream
            in.close();

        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
            return;
        }

        // Selecting a random word from the arrayList
        Random rand = new Random(); //instance of random class
        int upperbound = dictionary.size();
        //generate random values from 0 to arrayList size
        int int_random = rand.nextInt(upperbound);
        chosenWord = dictionary.get(int_random);

        // Instructions to the game
        System.out.println("The game has chosen a 5-letter word for you to guess.");
        System.out.println("You have 6 tries. In each guess, the game will confirm which letters the chosen word and the guessed word have in common:");
        System.out.println("- Letters highlighted in " + ANSI_GREEN_BACKGROUND + "green" + ANSI_RESET + " are in the correct place.");
        System.out.println("- Letters highlighted in " + ANSI_YELLOW_BACKGROUND + "yellow" + ANSI_RESET + " appear in the chosen word but in a different spot.");
        System.out.println("- Letters highlighted in " + ANSI_GREY_BACKGROUND + "grey" + ANSI_RESET + " do not appear in the chosen word.");
        System.out.println();
        System.out.println("Please write down your first guess:");

        for (int j = 0; j < 6; j++) {

            System.out.print("--> " + (j + 1) + ") ");

            String chosenWordWithoutGreensAndYellows = chosenWord;

            Scanner myScanner = new Scanner(System.in);  // Create a Scanner object
            String userWord = myScanner.nextLine();  // Read user input
            userWord = userWord.toLowerCase(); // covert to lowercase

            // check the length of the word and if it exists
            while ((userWord.length() != 5) || !(dictionary.contains(userWord))) {
                if ((userWord.length() != 5)) {
                    System.out.println("The word " + userWord + " does not have 5 letters.");
                } else {
                    System.out.println("The word " + userWord + " does not exist.");
                }
                // Ask for a new word
                System.out.println("Please, submit a new 5-letter word.");
                System.out.print("--> " + (j + 1) + ") ");
                userWord = myScanner.nextLine();
            }

            // check if the user won: the userWord is the same as chosenWord
            if (userWord.equals(chosenWord)) {
                System.out.println(("Result: " + ANSI_GREEN_BACKGROUND + userWord.toUpperCase() + ANSI_RESET));
                System.out.println();
                System.out.println("YOU WON! :)");
                System.out.println();
                System.out.println("The word's definition: https://www.merriam-webster.com/dictionary/" + chosenWord);
                break;
            } else {

                System.out.print("Result: ");

                // Loop checking every letter

                String userWordWithoutGreensAndYellows = userWord;
                String[] positionColors = new String[5];

                // check for green letters
                for (int i = 0; i < 5; i++) {
                    if (userWord.charAt(i) == chosenWord.charAt(i)) {
                        userWordWithoutGreensAndYellows = replaceChar(userWordWithoutGreensAndYellows, '0', i);
                        chosenWordWithoutGreensAndYellows = replaceChar(chosenWordWithoutGreensAndYellows, '0', i);
                        // System.out.print(ANSI_GREEN_BACKGROUND + userWord.toUpperCase().charAt(i) + ANSI_RESET);
                        greenLetters.add(userWord.toUpperCase().charAt(i));
                        positionColors[i] = ANSI_GREEN_BACKGROUND;
                    }
                }

                // check for yellow letters
                for (int i = 0; i < 5; i++) {
                    if (userWordWithoutGreensAndYellows.charAt(i) == '0') {
                        continue;
                    } else if (chosenWordWithoutGreensAndYellows.indexOf(userWordWithoutGreensAndYellows.charAt(i)) != -1) {
                        chosenWordWithoutGreensAndYellows = replaceChar(chosenWordWithoutGreensAndYellows, '0', chosenWordWithoutGreensAndYellows.indexOf(userWordWithoutGreensAndYellows.charAt(i)));
                        userWordWithoutGreensAndYellows = replaceChar(userWordWithoutGreensAndYellows, '0', i);
                        yellowLetters.add(userWord.toUpperCase().charAt(i));
                        positionColors[i] = ANSI_YELLOW_BACKGROUND;
                    } else {
                        greyLetters.add(userWord.toUpperCase().charAt(i));
                        positionColors[i] = ANSI_GREY_BACKGROUND;
                    }
                }

                for (int i = 0; i < 5; i++) {
                    System.out.print(positionColors[i] + userWord.toUpperCase().charAt(i) + ANSI_RESET);
                }
                System.out.println();

                // assembling the alphabet including the colors for a more visual exposition of information
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
            // Losing statement
            System.out.println();
            if (j == 5) {
                System.out.println();
                System.out.println("YOU LOST :( The word chosen by the game was: " + chosenWord + ".");

                System.out.println();
                System.out.println("The word's definition: https://www.merriam-webster.com/dictionary/" + chosenWord);
            }
        }
    }
}
