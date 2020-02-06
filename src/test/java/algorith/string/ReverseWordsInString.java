package algorith.string;

import java.util.Arrays;

/**
 * Given an input string, reverse the string word by word.

eg:
 Input: "the sky is blue"
 Output: "blue is sky the"

 eulb si yks eht


 Input: "  hello world!  "
 Output: "world! hello"
 Explanation: Your reversed string should not contain leading or trailing spaces.



 Input: "a good   example"
 Output: "example good a"
 Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.



 1.first reverse all the character
 2.reverse every word
 3.filter space character
 4.append space before word (not first word)
 * @author:ben.gu
 * @Date:2020/2/4 4:51 PM
 */
public class ReverseWordsInString {

    public static void main(String args[]) {
        ReverseWordsInString r = new ReverseWordsInString();
        String s = "  hello world!  ";
        System.err.println("s->" + r.reverseWords(s));


        s = "a good   example";
        System.err.println("s->" + r.reverseWords(s));

        s = "the sky is blue";
        System.err.println("s->" + r.reverseWords(s));
    }

    public String reverseWords(String s) {
        if (s.length() < 1) {
            return s;
        }
        char[] array = s.toCharArray();
        reverse(array, 0, array.length - 1);
        int wordIndex = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] != ' ') {
                if (wordIndex > 0) {
                    array[wordIndex++] = ' ';
                }
                int j = i;
                while (j < array.length && array[j] != ' ') {
                    array[wordIndex++] = array[j++];
                }
                reverse(array, wordIndex - (j - i), wordIndex - 1);
                i = j;
            }
        }

        return new String(array, 0, wordIndex);

    }

    private void reverse(char[] array, int s, int e) {
        while (s < e) {
            char t = array[s];
            array[s] = array[e];
            array[e] = t;
            s++;
            e--;
        }
    }

}
