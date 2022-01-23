import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.text.Normalizer;


public abstract class Wordle {

    // Declaring variables and arrayLists
    static List<String> languagePossibilities = new ArrayList<>(Arrays.asList("a", "english", "b", "español"));
    protected String chosenWordListFileName;
    protected String chosenWordListWithoutAccentsFileName;
    protected String userDictionaryWithoutAccentsFileName;
    protected String chosenWord;
    protected String chosenWordWithoutAccents;
    protected List<String> chosenWordListWithoutAccents;
    protected List<String> chosenWordList;
    protected List<String> userWordListWithoutAccents;
    protected List<Character> greenLetters = new ArrayList<>();
    protected List<Character> yellowLetters = new ArrayList<>();
    protected List<Character> greyLetters = new ArrayList<>();
    protected String result;
    protected String youWonMessage;
    protected String youLostMessage;

    // Declaring the background colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_GREY_BACKGROUND = "\u001B[100m";

    //constructor
    public Wordle() {
    }

    // METHODS

    // Read the dictionary and assemble the dictionary arrayList from which to choose the random chosen word
    public List<String> readDictionary(String fileName) {

        List<String> wordList = new ArrayList<>();

        try {
            // Open and read the dictionary file
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
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
    public String getRandomWord(List<String> wordList) {
        Random rand = new Random(); //instance of random class
        int upperbound = wordList.size();
        //generate random values from 0 to arrayList size
        int int_random = rand.nextInt(upperbound);
        return wordList.get(int_random);
    }

    public String removeAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    // print instructions
    public abstract void printInstructions();

    // ask for first guess
    public abstract void askForFirstGuess();


    // ask the user for a word, check for validity
    public abstract String obtainValidUserWord (List<String> wordList, int index);

    // method that replaces a char in a string at a specific index
    public String replaceChar(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }

    // print definition
    public abstract void printDefinitionLink (String randomChosenWord);

    public void loopThroughSixGuesses(List<String> wordList) {

        for (int j = 0; j < 6; j++) {

            System.out.print("--> " + (j + 1) + ") ");

            String userWord = obtainValidUserWord(wordList, j);

            String chosenWordWithoutGreensAndYellows = chosenWordWithoutAccents;

            // check if the user won: the userWord is the same as chosenWord
            if (userWord.equals(chosenWordWithoutAccents)) {
                System.out.println((result + ANSI_GREEN_BACKGROUND + userWord.toUpperCase() + ANSI_RESET));
                System.out.println();
                System.out.println(youWonMessage);
                System.out.println();
                printDefinitionLink(chosenWord);
                break;
            } else {

                System.out.print(result);

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
                System.out.println(youLostMessage + chosenWord.toUpperCase() + ".");
                System.out.println();
                printDefinitionLink(chosenWord);

            }
        }
    }


    // printing the alphabet including the colors for a more visual exposition of information
    public abstract void printingColouredAlphabet(List<Character> greenLetters, List<Character> yellowLetters, List<Character> greyLetters);

    // play method that calls on all other methods.
    public void play () {
        // Open and read the dictionary file with accents and without
        chosenWordList = this.readDictionary(chosenWordListFileName);
        chosenWordListWithoutAccents = this.readDictionary(chosenWordListWithoutAccentsFileName);
        userWordListWithoutAccents = this.readDictionary(userDictionaryWithoutAccentsFileName);

        // Selecting a random word from the dictionary
        chosenWord = getRandomWord(chosenWordList);

        // remove the accents from the word, if any
        chosenWordWithoutAccents = removeAccents(chosenWord);

        // Instructions to the game
        this.printInstructions();

        // ask the user for the first guess
        this.askForFirstGuess();

        this.loopThroughSixGuesses(userWordListWithoutAccents);

    }



}
