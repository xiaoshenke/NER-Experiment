package wuxian.me.ner.service;

import com.sun.istack.internal.Nullable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wuxian on 1/1/2018.
 */
public class FreedomManager {

    private FreedomManager() {
    }

    static List<Freedom> freedomList = new ArrayList<>();

    public static Long addFreedom(Long articleId, int len, int type) throws Exception {
        return addFreedom(articleId, len, type, false);
    }

    public static Long addFreedom(Long articleId, int len, int type, boolean force) throws Exception {
        if (!force) {
            Freedom freedom = findFreedomByArticleId(articleId, type, len);
            if (freedom != null) {
                return freedom.articleId;
            }
        }

        Freedom freedom = new Freedom();
        freedom.articleId = articleId;
        freedom.executeId = EIdGenerator.genId();
        freedom.type = type;
        freedom.len = len;

        List<String> wordList = ArticleManager.getWordsBy(freedom.articleId);
        if (wordList == null) {
            throw new Exception("no cutted word list of articleId: " + articleId + " is found!");
        }
        freedom.list = new FreedomCalculator().calculate(wordList, len, type);

        freedomList.add(freedom);
        return freedom.executeId;

    }

    public static void removeByExeId(Long exeId) {
        Iterator<Freedom> iterator = freedomList.iterator();
        while (iterator.hasNext()) {
            Freedom freedom = iterator.next();
            if (freedom.articleId.equals(exeId)) {
                iterator.remove();
                return;
            }
        }
    }

    public static void removeByArticleId(Long articleId) {
        Iterator<Freedom> iterator = freedomList.iterator();
        while (iterator.hasNext()) {
            Freedom freedom = iterator.next();
            if (freedom.articleId.equals(articleId)) {
                iterator.remove();
            }
        }
    }

    @Nullable
    public static Freedom findFreedomByArticleId(Long articleId, int type, int len) {
        for (Freedom freedom : freedomList) {
            if (freedom.articleId.equals(articleId) && freedom.type.equals(type) && freedom.len.equals(len)) {
                return freedom;
            }
        }
        return null;
    }

    @Nullable
    public static Freedom findFreedomByExeId(Long exeId) {
        for (Freedom serie : freedomList) {
            if (serie.executeId.equals(exeId)) {
                return serie;
            }
        }
        return null;
    }

    public static class Freedom {
        Long executeId;
        Long articleId;
        Integer len;
        Integer type;     //left:0 right:1
        public List<Pair<String, Double>> list;


    }
}
