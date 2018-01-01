package wuxian.me.ner.server;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

/**
 * Created by wuxian on 10/12/2017.
 */
public class SingleServer {
    public static void main(String[] args) throws Exception {
        new SingleServer().launch();
    }

    private void launch() throws Exception {
        Server server = new Server(8089);

        Context context = new Context(server, "/", Context.SESSIONS);
        context.addServlet(new ServletHolder(new NerServlet()), "/ner/*");
        NerServer.launch(new NerServer(server, context));
    }

}
