package wuxian.me.ner.parser;

import java.io.*;

import org.antlr.runtime.*;
import wuxian.me.ner.parser.node.ASTNode;
import wuxian.me.ner.parser.node.MyTreeAdaptor;
import wuxian.me.ner.server.service.FileUtil;

public class Main {

    public static void main(String[] args) throws Exception {

        String filename = FileUtil.getCurrentPath() + "/src/main/antlr3/input";
        CharStream input = new ANTLRFileStream(filename);
        CallLexer lexer = new CallLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        CallParser parser = new CallParser(tokens);
        parser.setTreeAdaptor(new MyTreeAdaptor());

        CallParser.call_return r = parser.call();
        ASTNode t = (ASTNode) r.getTree();
        System.out.println(t.toStringTree());

        System.out.println("root token:" + t.getToken().toString());

    }

}
