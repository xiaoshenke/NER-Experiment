package wuxian.me.ner.server;

import com.google.common.base.Preconditions;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;

/**
 * Created by wuxian on 31/12/2017.
 */
public class NerServer {

    private Server server;
    private Context context;

    public NerServer(Server server, Context context) {

        this.server = server;
        this.context = context;
    }

    public static void launch(final NerServer azkabanNerServer) throws Exception {

        Preconditions.checkNotNull(azkabanNerServer);
        azkabanNerServer.start();
    }

    private void start() throws Exception {
        this.context.setAttribute(Constants.NER_SERVLET_CONTEXT_KEY, this);
        try {
            this.server.start();
        } catch (Exception e) {
            ;
        }
    }


}
