package wuxian.me.ner.cli;

import org.apache.commons.lang3.tuple.Pair;
import wuxian.me.ner.service.*;
import wuxian.me.smartline.annotation.SmartLineFunc;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.toIntExact;

/**
 * Created by wuxian on 10/2/2018.
 */
public class NerFunctions {

    private static final String CMD_UPLOAD = "upload";
    private static final String CMD_EXPORT_WORDS = "exportwords";
    private static final String CMD_CONSILIENCE = "consilience";
    private static final String CMD_FREEDOM = "freedom";
    private static final String CMD_PRINT = "print";
    private static final String CMD_REMOVE = "remove";
    private static final String CMD_SERIES = "series";
    private static final String CMD_ADD_WORDS = "addwords";

    public NerFunctions() {
    }


    @SmartLineFunc(funcName = CMD_UPLOAD)
    public void upload(String title,String path) {
        title = removeQuote(title);
        path = removeQuote(path);
        try {
            long articleId = ArticleManager.addAritle(title, path);
            info("upload success,articleId: " + articleId);
            return ;
        } catch (Exception e) {
            return ;
        }
    }

    @SmartLineFunc(funcName = CMD_EXPORT_WORDS)
    public void export() {
        Set<String> words = RegWordsManager.getWords();
        for (String s : words) {
            info(s);
        }
    }

    @SmartLineFunc(funcName = "test_func")
    public void test_func() {
        info("a test");
    }

    @SmartLineFunc(funcName = CMD_CONSILIENCE)
    public void consilience(String aid,String len) {
        try {
            long exeId = ConsilienceManager.addConsilience(Long.valueOf(aid), Integer.valueOf(len));
            info("consilience success,executeId: " + exeId);

            return;
        } catch (Exception e) {
            return;
        }
    }

    @SmartLineFunc(funcName = CMD_FREEDOM)
    public void freedom(String aid,String len,String type) {
        try {
            //计算好词汇后 将词汇存入series manager,后序可以进行打印
            long exeId = FreedomManager.addFreedom(Long.valueOf(aid), Integer.valueOf(len), Integer.valueOf(type));

            //nerLine.info("freedom success,executeId: " + exeId);
            return;
        } catch (Exception e) {
            return;
        }
    }

    @SmartLineFunc(funcName = CMD_REMOVE)
    public void remove(String aid) {
        if (aid != null) {
            ArticleManager.removeArticle(Long.parseLong(aid));
            FreedomManager.removeByArticleId(Long.parseLong(aid));
            ConsilienceManager.removeByArticleId(Long.parseLong(aid));
            SeriesManager.removeByArticleId(Long.parseLong(aid));
            info("article of id: " + aid + " removed!");
        }

        return ;
    }

    @SmartLineFunc(funcName = CMD_SERIES)
    public void series(String id,String len) {

        try {
            //计算好词汇后 将词汇存入series manager,后序可以进行打印
            long exeId = SeriesManager.addSeries(Long.valueOf(id), Integer.valueOf(len));

            //nerLine.info("series success,executeId: " + exeId);
            return ;
        } catch (Exception e) {
            return;
        }
    }

    @SmartLineFunc(funcName = CMD_ADD_WORDS)
    public void addword(String type,String list,String eid,String word) {
        if (word != null) {
            word = removeQuote(word);
            RegWordsManager.addWord(word);
        }

        if (type == null || list == null || eid == null) {
            if (word == null) {
                info("params of print not valid!");
            }
            return;
        }

        type = removeQuote(type);
        list = removeQuote(list);

        String[] slist = list.split(",");

        if (type.toLowerCase().equals("series")) {
            SeriesManager.Series series = SeriesManager.findSeriesByExeId(Long.parseLong(eid));

            List<Pair<String, Integer>> wordList = series.list;
            for (String s : slist) {
                Integer i = Integer.parseInt(s);
                if (i < wordList.size()) {
                    RegWordsManager.addWord(wordList.get(i).getKey());
                }
            }

            info("done");
        } else if (type.toLowerCase().equals("consilience")) {
            ConsilienceManager.Consilience consilience = ConsilienceManager.findConsilienceByExeId(Long.parseLong(eid));

            List<Pair<String, Double>> wordList = consilience.list;
            for (String s : slist) {
                Integer i = Integer.parseInt(s);
                if (i < wordList.size()) {
                    RegWordsManager.addWord(wordList.get(i).getKey());
                }
            }
            info("done");

        } else if (type.toLowerCase().equals("freedom")) {
            FreedomManager.Freedom freedom = FreedomManager.findFreedomByExeId(Long.parseLong(eid));

            List<Pair<String, Double>> wordList = freedom.list;
            for (String s : slist) {
                Integer i = Integer.parseInt(s);
                if (i < wordList.size()) {
                    RegWordsManager.addWord(wordList.get(i).getKey());
                }
            }
            info("done");
        }

    }

    /*
    @SmartLineFunc(funcName = CMD_REMOVE)
    public void remove(String eid) {
        Map<String, String> params = getParams(t);
        String key1 = "aid";
        String aid = params.containsKey(key1) ? params.get(key1) : null;

        String key2 = "eid";
        String eid = params.containsKey(key2) ? params.get(key2) : null;
        if (aid == null && eid == null) {
            info("params of remove not valid!");
            return true;
        }

        if (aid != null) {
            ArticleManager.removeArticle(Long.parseLong(aid));
            FreedomManager.removeByArticleId(Long.parseLong(aid));
            ConsilienceManager.removeByArticleId(Long.parseLong(aid));
            SeriesManager.removeByArticleId(Long.parseLong(aid));
            info("article of id: " + aid + " removed!");
        }
        if (eid != null) {
            Long leid = Long.parseLong(eid);
            FreedomManager.removeByExeId(leid);
            ConsilienceManager.removeByExeId(leid);
            SeriesManager.removeByExeId(leid);
            info("execution of id: " + eid + " removed!");
        }
        return true;
    }
    */

    @SmartLineFunc(funcName = CMD_PRINT)
    public void print(String type,String id,String min,String max) {

        Long lmin = Long.parseLong(min);
        Long lmax = Long.parseLong(max);

        if (type == null || id == null) {
            info("params of print not valid!");
            return;
        }
        type = removeQuote(type);

        if (type.toLowerCase().equals("series")) {
            SeriesManager.Series series = SeriesManager.findSeriesByExeId(Long.parseLong(id));
            if (series.list != null && series.list.size() != 0) {
                lmin = lmin > series.list.size() ? series.list.size() : lmin;
                lmax = lmax > series.list.size() ? series.list.size() : lmax;
                if (lmin == lmax) {
                    info("params of print not valid,maybe your min is over list's size?");
                    return ;
                }
                info("--result--");
                for (long i = lmin; i < lmax; i++) {
                    Pair<String, Integer> p = series.list.get(toIntExact(i));
                    info(i + ":  " + p.getKey() + "  " + p.getValue());
                }
            } else {
                info("series of exeid: " + id + " seems not ready to print!");
            }
        } else if (type.toLowerCase().equals("consilience")) {
            ConsilienceManager.Consilience consilience = ConsilienceManager.findConsilienceByExeId(Long.parseLong(id));
            if (consilience.list != null && consilience.list.size() != 0) {
                lmin = lmin > consilience.list.size() ? consilience.list.size() : lmin;
                lmax = lmax > consilience.list.size() ? consilience.list.size() : lmax;
                if (lmin == lmax) {
                    info("params of print not valid,maybe your min is over list's size?");
                    return ;
                }
                info("--result--");
                for (long i = lmin; i < lmax; i++) {
                    Pair<String, Double> p = consilience.list.get(toIntExact(i));
                    info(i + ":  " + p.getKey() + "  " + p.getValue());
                }
            } else {
                info("series of exeid: " + id + " seems not ready to print!");
            }
        } else if (type.toLowerCase().equals("freedom")) {
            FreedomManager.Freedom freedom = FreedomManager.findFreedomByExeId(Long.parseLong(id));

            if (freedom.list != null && freedom.list.size() != 0) {
                lmin = lmin > freedom.list.size() ? freedom.list.size() : lmin;
                lmax = lmax > freedom.list.size() ? freedom.list.size() : lmax;
                if (lmin == lmax) {
                    info("params of print not valid,maybe your min is over list's size?");
                    return ;
                }
                info("--result--");
                for (long i = lmin; i < lmax; i++) {
                    Pair<String, Double> p = freedom.list.get(toIntExact(i));
                    info(i + ":  " + p.getKey() + "  " + p.getValue());
                }
            } else {
                info("freedom of exeid: " + id + " seems not ready to print!");
            }
        }
        return;
    }

    private void info(String content) {
        System.out.println(content);
    }

    private String removeQuote(String origin) {
        if (origin == null || origin.length() == 0) {
            return origin;
        }

        char first = origin.charAt(0);
        char last = origin.charAt(origin.length() - 1);
        if ((first == '"' || first == '\'') && (last == '"' || last == '\'')) {
            return origin.substring(1, origin.length() - 1);
        }
        return origin;
    }
}
