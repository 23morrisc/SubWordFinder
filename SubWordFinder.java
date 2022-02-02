import java.util.*;
import java.io.*;

/**
 * Finds the subwords in a dictionary
 * @author 23morrisc
 * @version 02/1/2022
 */

public class SubWordFinder implements WordFinder {

    private ArrayList<ArrayList<String>> dictionary;
    String alpha = "abcdefghijklmnopqrstuvwxyz";

    /**
     * this makes the dictionary actually have things in it.
     */

    public SubWordFinder() {

        dictionary = new ArrayList<>();
        for (int i = 0; i < alpha.length(); i++) {
            dictionary.add(new ArrayList<>());
        }
        populateDictionary();
    }

    /**
     * File reader, this is the method that SubWordFinder() uses to get things in the dictionary
     */

    public void populateDictionary() {
        try {

            Scanner in = new Scanner(new File("words_all_os.txt"));
            while (in.hasNext()) {
                String word = in.nextLine();
                dictionary.get(alpha.indexOf(word.charAt(0))).add(word);
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int indexOf(String word){
        int index = alpha.indexOf(word.charAt(0));
        ArrayList<String> bucket = dictionary.get(index);
        int low = 0;
        int high = bucket.size()-1;

        while (low <= high) {
           int mid = (low+high)/2;
           if (bucket.get(mid).compareTo(word) == 0)
               return mid;
           else if (bucket.get(mid).compareTo(word) > 0)
               high = mid-1;
           else
               low = mid+1;
        }
        return -1;
    }

    /**
     * determines if a word is in dictionary
     * @param word The item to be searched for in dictionary
     * @return true if word is in dictionary, false otherwise
     */

    public boolean inDictionary(String word) {
        return indexOf(word) != -1;
    }

    /**
     * takes all words and looks to see if they are subwords
     * @return arraylist of subwords
     */

    public ArrayList<SubWord> getSubWords() {
        ArrayList<SubWord> subWords = new ArrayList<>();
        ArrayList<String> prefix = new ArrayList<>();

        for (ArrayList<String> bucket : dictionary) {
            for (String word : bucket) {
                for (int i = 3; i < word.length()-2; i++) {
                    String word1 = word.substring(0,i);
                    String word2 = word.substring(i);

                    if (inDictionary(word1) && inDictionary(word2)) {
                        SubWord e = new SubWord(word, word1, word2);
                        subWords.add(e);


                    }
                }
            }
        }
        return subWords;
    }

    /**
     * prints out the dictionary arraylist
     */

    public void printDictionary() {
        for (ArrayList<String> bucket : dictionary)
            System.out.println(bucket);
    }

    /**
     * prints the subwords found and the amount found
     * @param args
     */

    public static void main(String[] args) {
        SubWordFinder  app = new SubWordFinder();
        //app.printDictionary();
        for (SubWord obj : app.getSubWords()) {
            System.out.println(obj);
        }
        //System.out.println(app.getSubWords().size());
    }
}
