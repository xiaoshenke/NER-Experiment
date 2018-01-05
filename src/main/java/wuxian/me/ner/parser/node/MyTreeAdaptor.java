package wuxian.me.ner.parser.node;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;

/**
 * Creates an ASTNode for the given token. The ASTNode is a wrapper around
 * antlr's CommonTree class that implements the Node interface.
 *
 * @param payload The token.
 * @return Object (which is actually an ASTNode) for the token.
 */
public class MyTreeAdaptor extends CommonTreeAdaptor {

    @Override
    public Object create(Token payload) {
        return new ASTNode(payload);
    }

    @Override
    public Object dupNode(Object t) {
        return create(((CommonTree) t).token);
    }

    ;

    @Override
    public Object errorNode(TokenStream input, Token start, Token stop, RecognitionException e) {
        return new ASTErrorNode(input, start, stop, e);
    }

    ;
};
