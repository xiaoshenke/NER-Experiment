package wuxian.me.ner.service;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wuxian on 1/1/2018.
 */
public class ConsilienceManager {

    private ConsilienceManager() {
    }

    static List<Consilience> consilienceList = new ArrayList<>();

    public static Long addConsilience(Long articleId, int len) throws Exception {
        return addConsilience(articleId, len, false);
    }

    public static Long addConsilience(Long articleId, int len, boolean force) throws Exception {
        if (!force) {
            Consilience consilience = findConsilienceByArticleId(articleId, len);
            if (consilience != null) {
                return consilience.articleId;
            }
        }

        Consilience consilience = new Consilience();
        consilience.articleId = articleId;
        consilience.executeId = EIdGenerator.genId();

        consilience.len = len;

        List<String> wordList = ArticleManager.getWordsBy(consilience.articleId);
        if (wordList == null) {
            throw new Exception("no cutted word list of articleId: " + articleId + " is found!");
        }
        consilience.list = new ConsilienceCalculator().calculate(wordList, len);
        consilienceList.add(consilience);
        return consilience.executeId;

    }

    public static void removeByExeId(Long exeId) {
        Iterator<Consilience> iterator = consilienceList.iterator();
        while (iterator.hasNext()) {
            Consilience consilience = iterator.next();
            if (consilience.articleId.equals(exeId)) {
                iterator.remove();
                return;
            }
        }
    }

    public static void removeByArticleId(Long articleId) {
        Iterator<Consilience> iterator = consilienceList.iterator();
        while (iterator.hasNext()) {
            Consilience consilience = iterator.next();
            if (consilience.articleId.equals(articleId)) {
                iterator.remove();
            }
        }
    }

    public static Consilience findConsilienceByArticleId(Long articleId, int len) {
        for (Consilience consilience : consilienceList) {
            if (consilience.articleId.equals(articleId) && consilience.len.equals(len)) {
                return consilience;
            }
        }
        return null;
    }

    public static Consilience findConsilienceByExeId(Long exeId) {
        for (Consilience serie : consilienceList) {
            if (serie.executeId.equals(exeId)) {
                return serie;
            }
        }
        return null;
    }

    public static class Consilience {
        Long executeId;
        Long articleId;
        Integer len;
        public List<Pair<String, Double>> list;
    }
}
