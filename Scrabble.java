/*
 * RUNI version of the Scrabble game.
 */
public class Scrabble {

    // Note 1: "Class variables", like the five class-level variables declared below,
    // are global variables that can be accessed by any function in the class. It is
    // customary to name class variables using capital letters and underline characters.
    // Note 2: If a variable is declared "final", it is treated as a constant value
    // which is initialized once and cannot be changed later.

    // Dictionary file for this Scrabble game
    static final String WORDS_FILE = "dictionary.txt";

    // The "Scrabble value" of each letter in the English alphabet.
    // 'a' is worth 1 point, 'b' is worth 3 points, ..., z is worth 10 points.
    static final int[] SCRABBLE_LETTER_VALUES = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3,
                                                  1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10 };

    // Number of random letters dealt at each round of this Scrabble game
    static int HAND_SIZE = 10;

    // Maximum number of possible words in this Scrabble game
    static int MAX_NUMBER_OF_WORDS = 100000;

    // The dictionary array (will contain the words from the dictionary file)
    static String[] DICTIONARY = new String[MAX_NUMBER_OF_WORDS];

    // Actual number of words in the dictionary (set by the init function, below)
    static int NUM_OF_WORDS;

    // Populates the DICTIONARY array with the lowercase version of all the words read
    // from the WORDS_FILE, and sets NUM_OF_WORDS to the number of words read from the file.
    public static void init() {
        // Declares the variable in to refer to an object of type In, and initializes it to represent
        // the stream of characters coming from the given file. Used for reading words from the file.
        In in = new In(WORDS_FILE);
        System.out.println("Loading word list from file...");
        NUM_OF_WORDS = 0;
        while (!in.isEmpty()) {
            // Reads the next "token" from the file. A token is defined as a string of
            // non-whitespace characters. Whitespace is either space characters, or
            // end-of-line characters.
            DICTIONARY[NUM_OF_WORDS++] = in.readString().toLowerCase();
        }
        System.out.println(NUM_OF_WORDS + " words loaded.");
    }

    // Adds spaces between letters of a string
    public static String spacedString(String str) {
        if (str.isEmpty()) {
            return "";
        }
        String spaced = "";
        for (int i = 0; i < str.length(); i++) {
            spaced += str.charAt(i);
            if (i < str.length() - 1) {
                spaced += " ";
            }
        }
        return spaced;
    }

    // Removes the letters in str2 from str1
    public static String remove(String str1, String str2) {
        String result = str1;
        for (int i = 0; i < str2.length(); i++) {
            char c = str2.charAt(i);
            int index = result.indexOf(c);
            if (index != -1) {
                result = result.substring(0, index) + result.substring(index + 1);
            }
        }
        return result;
    }

    // Checks if the given word is in the dictionary.
    public static boolean isWordInDictionary(String word) {
        for (int i = 0; i < NUM_OF_WORDS; i++) {
            if (DICTIONARY[i].equals(word)) {
                return true;
            }
        }
        return false;
    }

    // Returns the Scrabble score of the given word.
    // If the length of the word equals the length of the hand, adds 50 points to the score.
    // If the word includes the sequence "runi", adds 1000 points to the game.
    public static int wordScore(String word) {
        String abc = "abcdefghijklmnopqrstuvwxyz";
        int score = 0;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int letterIndex = abc.indexOf(c);
            score += SCRABBLE_LETTER_VALUES[letterIndex];
        }

        score *= word.length();
        if (word.length() == HAND_SIZE) {
            score += 50;
        }
        if (subsetOf("runi", word)) {
            score += 1000;
        }

        return score;
    }

    // Creates a random hand of length (HAND_SIZE - 2) and then inserts
    // into it, at random indexes, the letters 'a' and 'e'
    public static String createHand() {
        String hand = randomStringOfLetters(HAND_SIZE - 2);
        hand = insertRandomly('a', hand);
        hand = insertRandomly('e', hand);
        return hand;
    }

    public static boolean subsetOf(String str1, String str2) {
        for (int i = 0; i < str1.length(); i++) {
            char c = str1.charAt(i);
            int indexInStr2 = str2.indexOf(c);

            if (indexInStr2 == -1) {
                return false;
            }

            str2 = str2.substring(0, indexInStr2) + str2.substring(indexInStr2 + 1);
        }
        return true;
    }

    public static String randomStringOfLetters(int n) {
        String str = "";
        while (str.length() < n) {
            char c = (char) (97 + (Math.random() * 26));
            if (str.indexOf(c) == -1) {
                str += c;
            }
        }
        return str;
    }

    public static String insertRandomly(char ch, String str) {
        int randomIndex = (int) (Math.random() * (str.length() + 1));
        return str.substring(0, randomIndex) + ch + str.substring(randomIndex);
    }

    public static void playHand(String hand) {
        int score = 0;
        In in = new In();
        while (!hand.isEmpty()) {
            System.out.println("Current Hand: " + spacedString(hand));
            System.out.println("Enter a word, or '.' to finish playing this hand:");
            String input = in.readString();

            if (input.equals(".")) {
                break;
            }

            if (isWordInDictionary(input) && subsetOf(input, hand)) {
                int wordScore = wordScore(input);
                score += wordScore;
                System.out.println(input + " earned " + wordScore + " points. Total: " + score);
                hand = remove(hand, input);
            } else {
                System.out.println("Invalid word. Try again.");
            }
        }
        System.out.println("End of hand. Total score: " + score);
    }

    public static void playGame() {
        init();
        In in = new In();

        while (true) {
            System.out.println("Enter 'n' to deal a new hand, or 'e' to end the game:");
            String input = in.readString();

            if (input.equals("n")) {
                playHand(createHand());
            } else if (input.equals("e")) {
                System.out.println("Thanks for playing!");
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    public static void main(String[] args) {
        playGame();
    }
}
