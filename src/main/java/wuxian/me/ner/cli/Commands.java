package wuxian.me.ner.cli;

import com.google.common.base.Splitter;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.stringtemplate.v4.ST;
import wuxian.me.ner.parser.CallLexer;
import wuxian.me.ner.parser.CallParser;
import wuxian.me.ner.parser.node.ASTNode;
import wuxian.me.ner.parser.node.MyTreeAdaptor;
import wuxian.me.ner.service.*;

import static java.lang.Math.log;
import static java.lang.Math.toIntExact;

import org.apache.commons.lang3.tuple.*;

import java.io.IOException;
import java.util.*;

/**
 * Created by wuxian on 5/1/2018.
 * 封装命令
 */
public class Commands {

    private NerLine nerLine;

    public Commands(NerLine nerLine) {
        this.nerLine = nerLine;
    }

    public boolean command(String cmd) {
        return execute(cmd, true, true);
    }

    private boolean execute(String line, boolean call, boolean entireLineAsCommand) {
        if (line == null || line.length() == 0) {
            return false; // ???
        }

        CharStream input = new ANTLRStringStream(line);
        CallLexer lexer = new CallLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        CallParser parser = new CallParser(tokens);
        parser.setTreeAdaptor(new MyTreeAdaptor());

        CallParser.call_return r = null;

        try {
            r = parser.call();
        } catch (RecognitionException e) {
            return true;
        }

        ASTNode t = (ASTNode) r.getTree();

        if (t.getToken().getType() != CallParser.ID) {
            return false;
        }

        String cmd = t.getText().toLowerCase();
        if (cmd.equals(CMD_UPLOAD)) {
            return handleUpload(t, call);

        } else if (cmd.equals(CMD_EXPORT_WORDS)) {
            return handleExportWords(t, call);

        } else if (cmd.equals(CMD_CONSILIENCE)) {
            return handleCosilience(t, call);

        } else if (cmd.equals(CMD_FREEDOM)) {
            return handleFreedom(t, call);

        } else if (cmd.equals(CMD_PRINT)) {
            return handlePrint(t, call);

        } else if (cmd.equals(CMD_REMOVE)) {
            return handleRemove(t, call);

        } else if (cmd.equals(CMD_SERIES)) {
            return handleSeries(t, call);

        } else if (cmd.equals(CMD_ADD_WORDS)) {
            return handleAddWords(t, call);
        } else {
            nerLine.info("command: " + cmd + " not supported");
        }


        return true;
    }

    private static final String CMD_UPLOAD = "upload";
    private static final String CMD_EXPORT_WORDS = "exportwords";
    private static final String CMD_CONSILIENCE = "consilience";
    private static final String CMD_FREEDOM = "freedom";
    private static final String CMD_PRINT = "print";
    private static final String CMD_REMOVE = "remove";
    private static final String CMD_SERIES = "series";
    private static final String CMD_ADD_WORDS = "addwords";

    private Map<String, String> getParams(ASTNode t) {

        Map<String, String> params = new HashMap<>();
        if (t.getChildCount() == 0) {
            return null;
        }

        for (int i = 0; i < t.getChildCount(); i++) {
            ASTNode child = (ASTNode) t.getChild(i);
            if (child.getToken().getType() != CallParser.ID) {
                continue;
            }
            if (child.getChildCount() == 0) {
                return null;
            }
            params.put(child.getText(), child.getChild(0).getText());
        }
        return params;
    }

    //upload(title="",path="")
    private boolean handleUpload(ASTNode t, boolean call) {
        Map<String, String> params = getParams(t);
        String title = params.containsKey("title") ? params.get("title") : null;
        String path = params.containsKey("path") ? params.get("path") : null;
        if (title == null || path == null) {
            return false;
        }

        title = removeQuote(title);
        path = removeQuote(path);

        try {
            long articleId = ArticleManager.addAritle(title, path);

            nerLine.info("upload success,articleId: " + articleId);
            return true;
        } catch (Exception e) {
            return false;
        }
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


    //series(id=,len=,[force='true'])
    private boolean handleSeries(ASTNode t, boolean call) {
        Map<String, String> params = getParams(t);
        String key1 = "aid";  //article id
        String id = params.containsKey(key1) ? params.get(key1) : null;

        String key2 = "len";
        String len = params.containsKey(key2) ? params.get(key2) : String.valueOf(2);
        if (id == null || len == null) {
            return false;
        }
        try {
            //计算好词汇后 将词汇存入series manager,后序可以进行打印
            long exeId = SeriesManager.addSeries(Long.valueOf(id), Integer.valueOf(len));

            nerLine.info("series success,executeId: " + exeId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //consilience(id=,len=,[force='true'])
    private boolean handleCosilience(ASTNode t, boolean call) {
        Map<String, String> params = getParams(t);
        String key1 = "aid";
        String id = params.containsKey(key1) ? params.get(key1) : null;

        String key2 = "len";
        String len = params.containsKey(key2) ? params.get(key2) : String.valueOf(2);
        if (id == null || len == null) {
            return false;
        }
        try {
            long exeId = ConsilienceManager.addConsilience(Long.valueOf(id), Integer.valueOf(len));
            nerLine.info("consilience success,executeId: " + exeId);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //freedom(id=,len=,type=,[force='true'])
    private boolean handleFreedom(ASTNode t, boolean call) {
        Map<String, String> params = getParams(t);
        String key1 = "aid";
        String id = params.containsKey(key1) ? params.get(key1) : null;

        String key2 = "len";
        String len = params.containsKey(key2) ? params.get(key2) : String.valueOf(2);

        String key3 = "type";
        String type = params.containsKey(key3) ? params.get(key3) : String.valueOf(0); //0:left 1:right
        if (id == null || len == null) {
            return false;
        }

        try {
            //计算好词汇后 将词汇存入series manager,后序可以进行打印
            long exeId = FreedomManager.addFreedom(Long.valueOf(id), Integer.valueOf(len), Integer.valueOf(type));

            nerLine.info("freedom success,executeId: " + exeId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //exportwords([path=])
    private boolean handleExportWords(ASTNode t, boolean call) {
        Set<String> words = RegWordsManager.getWords();
        for (String s : words) {
            info(s);
        }

        return true;
    }

    //addwords(type=,eid=,list=)
    //addwords(word=)
    private boolean handleAddWords(ASTNode t, boolean call) {

        Map<String, String> params = getParams(t);
        String key0 = "type";
        String type = params.containsKey(key0) ? params.get(key0) : null;

        String key1 = "list";
        String list = params.containsKey(key1) ? params.get(key1) : null;

        String key2 = "eid";
        String eid = params.containsKey(key2) ? params.get(key2) : null;

        String key3 = "word";
        String word = params.containsKey(key3) ? params.get(key3) : null;

        if (word != null) {
            word = removeQuote(word);
            RegWordsManager.addWord(word);
        }

        if (type == null || list == null || eid == null) {
            if (word == null) {
                info("params of print not valid!");
            }
            return true;
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


        return true;
    }

    //remove(aid=,eid=)
    private boolean handleRemove(ASTNode t, boolean call) {

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

    //exeid
    //print(type=,eid=,[min=,[max=]])
    private boolean handlePrint(ASTNode t, boolean call) {
        Map<String, String> params = getParams(t);
        String key0 = "type";
        String type = params.containsKey(key0) ? params.get(key0) : null;

        String key1 = "eid";
        String id = params.containsKey(key1) ? params.get(key1) : null;

        String key2 = "min";
        String min = params.containsKey(key2) ? params.get(key2) : String.valueOf(0);
        Long lmin = Long.parseLong(min);

        String key3 = "max";
        String max = params.containsKey(key3) ? params.get(key3) : String.valueOf(50);
        Long lmax = Long.parseLong(max);

        if (type == null || id == null) {
            info("params of print not valid!");
            return true;
        }
        type = removeQuote(type);

        if (type.toLowerCase().equals("series")) {
            SeriesManager.Series series = SeriesManager.findSeriesByExeId(Long.parseLong(id));
            if (series.list != null && series.list.size() != 0) {
                lmin = lmin > series.list.size() ? series.list.size() : lmin;
                lmax = lmax > series.list.size() ? series.list.size() : lmax;
                if (lmin == lmax) {
                    info("params of print not valid,maybe your min is over list's size?");
                    return true;
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
                    return true;
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
                    return true;
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
        return true;
    }

    private void info(String content) {
        if (nerLine != null) {
            nerLine.info(content);
        }
    }

}
