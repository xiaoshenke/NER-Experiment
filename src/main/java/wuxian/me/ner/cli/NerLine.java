package wuxian.me.ner.cli;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.history.FileHistory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by wuxian on 3/1/2018.
 */
public class NerLine {

    private static final int ERRNO_OK = 0;
    private static final int ERRNO_ARGS = 1;
    private static final int ERRNO_OTHER = 2;

    private PrintStream outputStream = new PrintStream(System.out, true);
    private OutputFile recordOutputFile = null;
    private OutputFile scriptOutputFile = null;
    private PrintStream errorStream = new PrintStream(System.err, true);
    private InputStream inputStream = System.in;
    private FileHistory history;
    private ConsoleReader consoleReader;
    private Commands commands = new Commands(this);
    private boolean exit = false;
    private NerLineOpts opts = new NerLineOpts(this, System.getProperties());

    final CommandHandler[] commandHandlers = new CommandHandler[]{
    };

    /**
     * Starts the program.
     */
    public static void main(String[] args) throws IOException {
        mainWithInputRedirection(args, null);
    }

    /**
     * Starts the program with redirected input_simple. For redirected output,
     * setOutputStream() and setErrorStream can be used.
     * Exits with 0 on success, 1 on invalid arguments, and 2 on any other error
     *
     * @param args        same as main()
     * @param inputStream redirected input_simple, or null to use standard input_simple
     */
    public static void mainWithInputRedirection(String[] args, InputStream inputStream)
            throws IOException {
        NerLine beeLine = new NerLine();
        try {
            int status = beeLine.begin(args, inputStream);
            if (!Boolean.getBoolean(NerLineOpts.PROPERTY_NAME_EXIT)) {
                System.exit(status);
            }
        } finally {
            beeLine.close();
        }
    }

    public int begin(String[] args, InputStream inputStream) throws IOException {
        setupHistory();
        addBeelineShutdownHook();
        ConsoleReader reader = initializeConsoleReader(inputStream);
        int code = initArgs(args);
        if (code != 0) {
            return code;
        }
        return execute(reader, false);
    }

    public ConsoleReader initializeConsoleReader(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            // ### NOTE: fix for sf.net bug 879425.
            // Working around an issue in jline-2.1.2, see https://github.com/jline/jline/issues/10
            // by appending a newline to the end of inputstream
            InputStream inputStreamAppendedNewline = new SequenceInputStream(inputStream,
                    new ByteArrayInputStream((new String("\n")).getBytes()));
            consoleReader = new ConsoleReader(inputStreamAppendedNewline, getErrorStream());
            consoleReader.setCopyPasteDetection(true); // jline will detect if <tab> is regular character
        } else {
            consoleReader = new ConsoleReader(getInputStream(), getErrorStream());
        }

        //disable the expandEvents for the purpose of backward compatibility
        consoleReader.setExpandEvents(false);
        try {
            // now set the output for the history
            consoleReader.setHistory(this.history);
        } catch (Exception e) {
            handleException(e);
        }

        if (inputStream instanceof FileInputStream) {
            // from script.. no need to load history and no need of completer, either
            return consoleReader;
        }

        Completer completer = getCompleter();
        if (completer != null) {
            consoleReader.addCompleter(completer);
        }
        return consoleReader;
    }

    private void setupHistory() throws IOException {
        if (this.history != null) {
            return;
        }
        this.history = new FileHistory(new File(getOpts().getHistoryFile()));
    }

    private void addBeelineShutdownHook() throws IOException {

        ShutdownHookManager.addShutdownHook(new Runnable() {
            @Override
            public void run() {
                try {
                    if (history != null) {
                        history.setMaxSize(getOpts().getMaxHistoryRows());
                        history.flush();
                    }
                } catch (IOException e) {
                    //error(e);
                } finally {
                    close();
                }
            }
        });
    }

    private int execute(ConsoleReader reader, boolean exitOnError) {
        int lastExecutionResult = ERRNO_OK;
        while (!exit) {
            try {
                // Execute one instruction; terminate on executing a script if there is an error
                // in silent mode, prevent the query and prompt being echoed back to terminal
                String line = (getOpts().isSilent() && getOpts().getScriptFile() != null) ? reader
                        .readLine(null, ConsoleReader.NULL_MASK) : reader.readLine(getPrompt());
                // trim line
                if (line != null) {
                    line = line.trim();
                }

                if (!dispatch(line)) {
                    lastExecutionResult = ERRNO_OTHER;
                    if (exitOnError) break;
                } else if (line != null) {
                    lastExecutionResult = ERRNO_OK;
                }

            } catch (Throwable t) {
                handleException(t);
                return ERRNO_OTHER;
            }
        }
        return lastExecutionResult;
    }

    boolean dispatch(String line) {
        if (line == null) {
            // exit
            exit = true;
            return true;
        }

        if (line.trim().length() == 0) {
            return true;
        }

        line = line.trim();
        // save it to the current script, if any
        if (scriptOutputFile != null) {
            scriptOutputFile.addLine(line);
        }

        //System.out.println("dispatch: " + line);
        return commands.command(line);
    }

    //暂时先不做
    Completer getCompleter() {
        return null;
    }

    //beeline 支持-e,-f等操作 我这里暂时不做支持
    //若做支持 参考 org.apache.commons.cli.GnuParser http://www.cnblogs.com/xiongmaotailang/p/5255416.html
    //connect to thrift server
    int initArgs(String[] args) {
        return 0;
    }

    //close connection to thrift server
    public void close() {

    }

    PrintStream getErrorStream() {
        return errorStream;
    }

    InputStream getInputStream() {
        return inputStream;
    }

    ConsoleReader getConsoleReader() {
        return consoleReader;
    }

    public NerLineOpts getOpts() {
        return opts;
    }


    String getPrompt() {
        return "nerline> ";
    }

    void handleException(Throwable e) {
        while (e instanceof InvocationTargetException) {
            e = ((InvocationTargetException) e).getTargetException();
        }

        if (e instanceof SQLException) {
            //handleSQLException((SQLException) e);
        } else if (e instanceof EOFException) {
            setExit(true);  // CTRL-D
        } else if (!(getOpts().getVerbose())) {
            if (e.getMessage() == null) {
                //error(e.getClass().getName());
            } else {
                //error(e.getMessage());
            }
        } else {
            e.printStackTrace(getErrorStream());
        }
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    /**
     * Issue the specified error message
     *
     * @param msg the message to issue
     * @return false always
     */
    boolean error(String msg) {
        output(getColorBuffer().red(msg), true, getErrorStream());
        return false;
    }

    void output(ColorBuffer msg) {
        output(msg, true);
    }

    void output(String msg, boolean newline, PrintStream out) {
        output(getColorBuffer(msg), newline, out);
    }


    void output(ColorBuffer msg, boolean newline) {
        output(msg, newline, getOutputStream());
    }

    void output(ColorBuffer msg, boolean newline, PrintStream out) {
        if (newline) {
            out.println(msg.getColor());
        } else {
            out.print(msg.getColor());
        }

        if (recordOutputFile == null) {
            return;
        }

        // only write to the record file if we are writing a line ...
        // otherwise we might get garbage from backspaces and such.
        if (newline) {
            recordOutputFile.addLine(msg.getMono()); // always just write mono
        } else {
            recordOutputFile.print(msg.getMono());
        }
    }

    /**
     * Print the specified message to the console
     *
     * @param msg     the message to print
     * @param newline if false, do not append a newline
     */
    void output(String msg, boolean newline) {
        output(getColorBuffer(msg), newline);
    }

    ColorBuffer getColorBuffer(String msg) {
        return new ColorBuffer(msg, getOpts().getColor());
    }

    ColorBuffer getColorBuffer() {
        return new ColorBuffer(getOpts().getColor());
    }

    PrintStream getOutputStream() {
        return outputStream;
    }

}
