/**
 * A library of string functions.
 */
public class MyString {
    public static void main(String args[]) {
        String hello = "hello";
        System.out.println(countChar(hello, 'h'));
        System.out.println(countChar(hello, 'l'));
        System.out.println(countChar(hello, 'z'));

        System.out.println(spacedString(hello));
        System.out.println(spacedString("hello")); // Expected: "h e l l o"
        System.out.println(spacedString("a")); // Expected: "a"
        System.out.println(spacedString(""));

        System.out.println("SubsetOf Tests:");
        System.out.println(subsetOf("sap", "space")); // Expected: true
        System.out.println(subsetOf("pass", "space")); // Expected: false
      
        System.out.println(randomStringOfLetters(5)); // Random output, length: 5
        System.out.println(randomStringOfLetters(10)); // Random output, length: 10

        System.out.println(remove("committee", "meet")); // Expected: "comit"
        System.out.println(remove("abc", "abc")); // Expected: ""

        System.out.println(insertRandomly('x', "hello")); // Random output
        System.out.println(insertRandomly('x', "")); // Expected: "x"
}

    /**
     * Returns the number of times the given character appears in the given string.
     * Example: countChar("Center",'e') returns 2 and countChar("Center",'c') returns 0. 
     * 
     * @param str - a string
     * @param c - a character
     * @return the number of times c appears in str
     */
    public static int countChar(String str, char ch) {
        int count = 0; 

        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == ch){
                count++;
            }
        }
        return count;
    }

    /** Returns true if str1 is a subset string str2, false otherwise
     *  Examples:
     *  subsetOf("sap","space") returns true
     *  subsetOf("spa","space") returns true
     *  subsetOf("pass","space") returns false
     *  subsetOf("c","space") returns true
     *
     * @param str1 - a string
     * @param str2 - a string
     * @return true is str1 is a subset of str2, false otherwise
     */
    public static boolean subsetOf(String str1, String str2) {
        for (int i = 0; i < str1.length(); i++) {
            char c = str1.charAt(i);
            int indexInStr2 = str2.indexOf(c);
            
            // If the character doesn't exist in str2 at all, return false immediately
            if (indexInStr2 == -1) {
                return false;
            }
    
            // Remove the found character from str2 so it can't be reused
            str2 = str2.substring(0, indexInStr2) + str2.substring(indexInStr2 + 1);
        }
    
        // If we successfully found and removed all characters of str1 from str2, return true
        return true;
    }
    


    /** Returns a string which is the same as the given string, with a space
     * character inserted after each character in the given string, except
     * for the last character. 
     * Example: spacedString("silent") returns "s i l e n t"
     * 
     * @param str - a string
     * @return a string consisting of the characters of str, separated by spaces.
     */
    public static String spacedString(String str) {
        if (str.isEmpty()) {
            return "";
        }
        String str1 = "" + str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            str1 += " " + str.charAt(i);
        }
        return str1;
    }
    
    /**
     * Returns a string of n lowercase letters, selected randomly from 
     * the English alphabet 'a', 'b', 'c', ..., 'z'. Note that the same
     * letter can be selected more than once.
     * 
     * Example: randomStringOfLetters(3) can return "zoo"
     * 
     * @param n - the number of letter to select
     * @return a randomly generated string, consisting of 'n' lowercase letters
     */
    public static String randomStringOfLetters(int n) {
        if (n > 26){
            System.err.println("Can not create more than 26 unique charecters");
        }
        String str = "";
        for (int i = 0; i < n; i++){
            char c = (char) (97 + (Math.random() * 26));
            int indexInStr = str.indexOf(c);
            if (indexInStr == -1) {
                str += c;
            }
        }
        return "[a-z]" + "{" + str.length() + "}";
    }

    /**
     * Returns a string consisting of the string str1, minus all the characters in the
     * string str2. Assumes (without checking) that str2 is a subset of str1.
     * Example: remove("meet","committee") returns "comit" 
     * 
     * @param str1 - a string
     * @param str2 - a string
     * @return a string consisting of str1 minus all the characters of str2
     */
    public static String remove(String str1, String str2) {
        String result = str1; // Start with the full string
        
        for (int i = 0; i < str2.length(); i++) {
            char c = str2.charAt(i); // Character to remove
            int index = result.indexOf(c); // Find the first occurrence of this character in result
            if (index != -1) {
                result = result.substring(0, index) + result.substring(index + 1); // Remove the character
            }
        }
        
        return result; // Return the modified string
    }

    /**
     * Returns a string consisting of the given string, with the given 
     * character inserted randomly somewhere in the string.
     * For example, insertRandomly("s","cat") can return "scat", or "csat", or "cast", or "cats".  
     * @param ch - a character
     * @param str - a string
     * @return a string consisting of str with ch inserted somewhere
     */
    public static String insertRandomly(char ch, String str) {
        if (str.isEmpty()) {
            return Character.toString(ch); // Return the character if the string is empty
        }
        // Generate a random index between 0 and str.length()
         int randomIndex = (int) (Math.random() * (str.length() + 1));
         // Insert the character at the random index
         String result = str.substring(0, randomIndex) + ch + str.substring(randomIndex);
         return result;
    }    
}
