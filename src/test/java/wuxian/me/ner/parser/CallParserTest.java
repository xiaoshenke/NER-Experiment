package wuxian.me.ner.parser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.junit.Test;
import wuxian.me.ner.parser.node.ASTNode;
import wuxian.me.ner.parser.node.MyTreeAdaptor;
import wuxian.me.ner.service.FileUtil;

public class CallParserTest {

    @Test
    public void test1() throws Exception {

        String filename = FileUtil.getCurrentPath() + "/src/main/antlr3/input_1";
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

    @Test
    public void test2() throws Exception {
        String filename = FileUtil.getCurrentPath() + "/src/main/antlr3/input_2";
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

    @Test
    public void test3() throws Exception {
        String filename = FileUtil.getCurrentPath() + "/src/main/antlr3/input_3";
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

    @Test
    public void test4() throws Exception {
        String filename = FileUtil.getCurrentPath() + "/src/main/antlr3/input_4";
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

    @Test
    public void test5() throws Exception {
        //new ANTLRNoCaseStringStream(command);

        CharStream input = new ANTLRStringStream("addWord(type=1)");
        CallLexer lexer = new CallLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        CallParser parser = new CallParser(tokens);
        parser.setTreeAdaptor(new MyTreeAdaptor());

        CallParser.call_return r = parser.call();
        ASTNode t = (ASTNode) r.getTree();
        System.out.println(t.toStringTree());

        System.out.println("root token:" + t.getToken().toString());
        System.out.println(t.getText());

    }

}