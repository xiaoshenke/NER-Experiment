package wuxian.me.ner;

import jline.console.ConsoleReader;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by wuxian on 31/12/2017.
 */
public class Main {

    //CommonLexer

    public static void main(String[] args) throws IOException {
        PrintStream outputStream = new PrintStream(System.out, true);
        PrintStream err = new PrintStream(System.err, true);
        ConsoleReader reader = new ConsoleReader(System.in, err);
        reader.setExpandEvents(true);
        reader.setBellEnabled(false);
        while (true) {
            String line = reader.readLine("nerline>> ");
            outputStream.println(line);
        }
    }
}
