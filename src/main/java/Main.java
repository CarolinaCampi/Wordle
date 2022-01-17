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

    // METHODS

    // Read the dictionary and assemble the arrayList from which to choose the random chosen word
    public static ArrayList<String> readDictionary() {
        ArrayList<String> wordList = new ArrayList<>();

        try {
            // Open and read the dictionary file
            InputStream in = Main.class.getClassLoader().getResourceAsStream("dictionary.txt");
            assert in != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String strLine;

            //Read file line By line
            while ((strLine = reader.readLine()) != null) {
                wordList.add(strLine);
            }
            //Close the input stream
            in.close();

        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return wordList;
    }

    // get a random word from the dictionary arraylist
    public static String getRandomWord(ArrayList<String> wordList) {
        Random rand = new Random(); //instance of random class
        int upperbound = wordList.size();
        //generate random values from 0 to arrayList size
        int int_random = rand.nextInt(upperbound);
        return wordList.get(int_random);
    }

    // print instructions
    public static void printInstructions() {
        System.out.println("The game has chosen a 5-letter word for you to guess.");
        System.out.println("You have 6 tries. In each guess, the game will confirm which letters the chosen word and the guessed word have in common:");
        System.out.println("- Letters highlighted in " + ANSI_GREEN_BACKGROUND + "green" + ANSI_RESET + " are in the correct place.");
        System.out.println("- Letters highlighted in " + ANSI_YELLOW_BACKGROUND + "yellow" + ANSI_RESET + " appear in the chosen word but in a different spot.");
        System.out.println("- Letters highlighted in " + ANSI_GREY_BACKGROUND + "grey" + ANSI_RESET + " do not appear in the chosen word.");
    }

    // ask the user for a word, check for validity
    public static String obtainValidUserWord (ArrayList<String> wordList, int index) {
        Scanner myScanner = new Scanner(System.in);  // Create a Scanner object
        String userWord = myScanner.nextLine();  // Read user input
        userWord = userWord.toLowerCase(); // covert to lowercase

        // check the length of the word and if it exists
        while ((userWord.length() != 5) || !(wordList.contains(userWord))) {
            if ((userWord.length() != 5)) {
                System.out.println("The word " + userWord + " does not have 5 letters.");
            } else {
                System.out.println("The word " + userWord + " does not exist.");
            }
            // Ask for a new word
            System.out.println("Please, submit a new 5-letter word.");
            System.out.print("--> " + (index + 1) + ") ");
            userWord = myScanner.nextLine();
        }
        return userWord;
    }

    // method that replaces a char in a string at a specific index
    public static String replaceChar(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }

    // printing the alphabet including the colors for a more visual exposition of information
    public static void printingColouredAlphabet(ArrayList<Character> greenLetters, ArrayList<Character> yellowLetters,ArrayList<Character> greyLetters) {
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

    // print definition
    public static void printDefinitionLink (String randomChosenWord) {
        System.out.println("The word's definition: https://www.merriam-webster.com/dictionary/" + randomChosenWord);
    }

    public static void main(String[] args) {

        // Declaring variables and arrays
        String chosenWord;
        ArrayList<Character> greenLetters = new ArrayList<>();
        ArrayList<Character> yellowLetters = new ArrayList<>();
        ArrayList<Character> greyLetters = new ArrayList<>();

        // Open and read the dictionary file
        ArrayList<String> dictionary = readDictionary();

        // Selecting a random word from the dictionary
        chosenWord = getRandomWord(dictionary);

        // Instructions to the game
        printInstructions();

        System.out.println();
        System.out.println("Please write down your first guess:");

        for (int j = 0; j < 6; j++) {

            System.out.print("--> " + (j + 1) + ") ");

            String chosenWordWithoutGreensAndYellows = chosenWord;

            String userWord = obtainValidUserWord(dictionary, j);

            // check if the user won: the userWord is the same as chosenWord
            if (userWord.equals(chosenWord)) {
                System.out.println(("Result: " + ANSI_GREEN_BACKGROUND + userWord.toUpperCase() + ANSI_RESET));
                System.out.println();
                System.out.println("YOU WON! :)");
                System.out.println();
                printDefinitionLink(chosenWord);
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

                // print user word with colors
                for (int i = 0; i < 5; i++) {
                    System.out.print(positionColors[i] + userWord.toUpperCase().charAt(i) + ANSI_RESET);
                }
                System.out.println();

                // print alphabet
                printingColouredAlphabet(greenLetters, yellowLetters, greyLetters);

                }

            // Losing statement
            System.out.println();
            if (j == 5) {
                System.out.println();
                System.out.println("YOU LOST :( The word chosen by the game was: " + chosenWord + ".");
                System.out.println();
                printDefinitionLink(chosenWord);

            }
        }
    }
}


