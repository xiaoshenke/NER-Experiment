package wuxian.me.ner.cli;

import com.google.common.base.Splitter;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import wuxian.me.ner.parser.CallLexer;
import wuxian.me.ner.parser.CallParser;
import wuxian.me.ner.parser.node.ASTNode;
import wuxian.me.ner.parser.node.MyTreeAdaptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wuxian on 5/1/2018.
 * 封装命令
 */
public class Commands {

    private NerLine nerLine;

    public Commands(NerLine nerLine) {
        this.nerLine = nerLine;
    }

    //Todo
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
        if (cmd.equals("upload")) {
            return handleUpload(t, call);

        } else if (cmd.equals("exportwords")) {
            return handleExportWords(t, call);

        } else if (cmd.equals("consilience")) {
            return handleCosilience(t, call);

        } else if (cmd.equals("freedom")) {
            return handleFreedom(t, call);

        } else if (cmd.equals("print")) {
            return handlePrint(t, call);

        } else if (cmd.equals("remove")) {
            return handleRemove(t, call);

        } else if (cmd.equals("series")) {
            return handleSeries(t, call);
        }

        return false;
    }

    //Todo:
    private boolean handleUpload(ASTNode t, boolean call) {
        return true;
    }

    private boolean handleExportWords(ASTNode t, boolean call) {
        return true;
    }

    private boolean handleCosilience(ASTNode t, boolean call) {
        return true;
    }

    private boolean handleFreedom(ASTNode t, boolean call) {
        return true;
    }

    private boolean handlePrint(ASTNode t, boolean call) {
        return true;
    }

    private boolean handleRemove(ASTNode t, boolean call) {
        return true;
    }

    private boolean handleSeries(ASTNode t, boolean call) {
        return true;
    }


}
