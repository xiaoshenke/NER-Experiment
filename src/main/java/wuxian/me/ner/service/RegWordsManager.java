package wuxian.me.ner.service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wuxian on 6/1/2018.
 */
public class RegWordsManager {

    private static Set<String> words = new HashSet<>();

    private RegWordsManager() {
    }

    public static void addWord(String word) {
        if (word != null && word.length() != 0) {
            words.add(word);
        }
    }

    public static Set<String> getWords() {
        return words;
    }
}
