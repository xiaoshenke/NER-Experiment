package wuxian.me.ner.cli;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.history.FileHistory;

import java.io.*;

public class NerLine {

    private PrintStream errorStream = new PrintStream(System.err, true);
    private InputStream inputStream = System.in;
    private FileHistory history;
    private ConsoleReader consoleReader;

    boolean exit = false;

    private static final int ERRNO_OK = 0;
    private static final int ERRNO_ARGS = 1;
    private static final int ERRNO_OTHER = 2;

    private NerLineOpts opts = new NerLineOpts(this, System.getProperties());

    /**
     * Starts the program.
     */
    public static void main(String[] args) throws IOException {
        mainWithInputRedirection(args, null);
    }

    /**
     * Starts the program with redirected input. For redirected output,
     * setOutputStream() and setErrorStream can be used.
     * Exits with 0 on success, 1 on invalid arguments, and 2 on any other error
     *
     * @param args        same as main()
     * @param inputStream redirected input, or null to use standard input
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

    //Todo
    public void close() {

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



    PrintStream getErrorStream() {
        return errorStream;
    }

    InputStream getInputStream() {
        return inputStream;
    }

    ConsoleReader getConsoleReader() {
        return consoleReader;
    }

    //Todo
    void handleException(Throwable e) {

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
        if(completer != null) {
            consoleReader.addCompleter(getCompleter());
        }

        return consoleReader;
    }

    //Todo
    Completer getCompleter() {
        return null;
    }


    //Todo: init parser or so
    int initArgs(String[] args) {
        return 0;
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


    public NerLineOpts getOpts() {
        return opts;
    }


    String getPrompt() {
        return "nerline> ";
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
        //return commands.sql(line, getOpts().getEntireLineAsCommand());
        return true;  //Todo
    }

    private OutputFile scriptOutputFile = null;

}
