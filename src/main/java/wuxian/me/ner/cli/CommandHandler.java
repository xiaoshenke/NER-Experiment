package wuxian.me.ner.cli;

import jline.console.completer.Completer;

/**
 * Created by wuxian on 5/1/2018.
 */
public interface CommandHandler {
    /**
     * @return the name of the command
     */
    public String getName();


    /**
     * @return all the possible names of this command.
     */
    public String[] getNames();


    /**
     * @return the short help description for this command.
     */
    public String getHelpText();


    /**
     * Check to see if the specified string can be dispatched to this
     * command.
     *
     * @param line the command line to check.
     * @return the command string that matches, or null if it no match
     */
    public String matches(String line);


    /**
     * Execute the specified command.
     *
     * @param line the full command line to execute.
     */
    public boolean execute(String line);


    /**
     * Returns the completors that can handle parameters.
     */
    public Completer[] getParameterCompleters();

    /**
     * Returns exception thrown for last command
     *
     * @return
     */
    public Throwable getLastException();
}