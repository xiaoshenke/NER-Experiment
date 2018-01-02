package wuxian.me.ner.cli;

import org.junit.Test;

import static org.junit.Assert.*;

public class NerLineOptsTest {

    @Test
    public void testInit() {

        NerLine nerLine = new NerLine();
        NerLineOpts opts = new NerLineOpts(nerLine, System.getProperties());

        System.out.println(opts.saveDir()); // /Users/dashu/.nerline

        //System.out.println(System.getProperties());
    }

}