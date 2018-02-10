package wuxian.me.ner.cli;

import wuxian.me.smartline.SmartLine;
import wuxian.me.smartline.SmartLineOpts;

/**
 * Created by wuxian on 10/2/2018.
 */
public class NerCli {

    public static void main(String[] args) throws Exception {

        SmartLine smartLine = new SmartLine();
        smartLine.loadCommandByPackage("wuxian.me.ner.cli");

        try {
            int status = smartLine.begin(args, null);
            if (!Boolean.getBoolean(SmartLineOpts.PROPERTY_NAME_EXIT)) {
                System.exit(status);
            }
        } finally {
            smartLine.close();
        }

    }
}
