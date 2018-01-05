package wuxian.me.ner.cli;

import com.google.common.base.Splitter;
import com.sun.istack.internal.Nullable;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.stringtemplate.v4.ST;
import wuxian.me.ner.parser.CallLexer;
import wuxian.me.ner.parser.CallParser;
import wuxian.me.ner.parser.node.ASTNode;
import wuxian.me.ner.parser.node.MyTreeAdaptor;
import wuxian.me.ner.service.ArticleManager;
import wuxian.me.ner.service.ConsilienceManager;
import wuxian.me.ner.service.FreedomManager;
import wuxian.me.ner.service.SeriesManager;

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

        CharStream input = new ANTLRStringStream("addWord(type=1)");
        CallLexer lexer = new CallLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        CallParser parser = new CallParser(tokens);
        parser.setTreeAdaptor(new MyTreeAdaptor());

        CallParser.call_return r = null;

        try {
            parser.call();
        } catch (RecognitionException e) {
            //Todo: print error message
            return false;
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

        }

        return false;
    }

    private static final String CMD_UPLOAD = "upload";
    private static final String CMD_EXPORT_WORDS = "exportwords";
    private static final String CMD_CONSILIENCE = "consilience";
    private static final String CMD_FREEDOM = "freedom";
    private static final String CMD_PRINT = "print";
    private static final String CMD_REMOVE = "remove";
    private static final String CMD_SERIES = "series";
    private static final String CMD_ADD_WORDS = "addwords";

    @Nullable
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
        try {
            long articleId = ArticleManager.addAritle(title, path);
            return true;
        } catch (Exception e) {
            return false;
        }
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
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //exportwords([path=])
    private boolean handleExportWords(ASTNode t, boolean call) {
        return true;
    }

    //addwords(type=,list=)
    private boolean handleAddWords(ASTNode t, boolean call) {
        return true;
    }

    //exeid
    //print(eid=,[min=,[max=]])
    private boolean handlePrint(ASTNode t, boolean call) {
        return true;
    }

    //remove(eid=)
    private boolean handleRemove(ASTNode t, boolean call) {
        return true;
    }


}
