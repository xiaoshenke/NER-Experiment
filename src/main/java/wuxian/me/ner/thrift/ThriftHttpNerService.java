package wuxian.me.ner.thrift;


import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;
import wuxian.me.ner.thrift.proto.NerService;
import wuxian.me.ner.thrift.util.ThreadFactoryWithGarbageCleanup;
import wuxian.me.ner.thrift.util.ThreadPoolExecutorWithOomHook;
import wuxian.me.ner.thrift.util.ThriftHttpServlet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class ThriftHttpNerService extends ThriftNerService {

    private Runnable oomHook;

    public ThriftHttpNerService(Runnable oomHook) {
        super();
        this.oomHook = oomHook;
    }

    /**
     * Configure Jetty to serve http requests. Example of a client connection URL:
     * http://localhost:10000/servlets/thrifths2/
     */
    @Override
    public void run() {
        try {
            // Server thread pool
            // Start with minWorkerThreads, expand till maxWorkerThreads and reject subsequent requests
            String threadPoolName = "NerServer-HttpHandler-Pool";
            ExecutorService executorService = new ThreadPoolExecutorWithOomHook(minWorkerThreads,
                    maxWorkerThreads, workerKeepAliveTime, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
                    new ThreadFactoryWithGarbageCleanup(threadPoolName), oomHook);
            ExecutorThreadPool threadPool = new ExecutorThreadPool(executorService);

            // HTTP Server
            httpServer = new Server(threadPool);

            ServerConnector connector;

            final HttpConfiguration conf = new HttpConfiguration();
            // Configure header size
            int requestHeaderSize = 6 * 1024;
            int responseHeaderSize = 6 * 1024;
            conf.setRequestHeaderSize(requestHeaderSize);
            conf.setResponseHeaderSize(responseHeaderSize);
            final HttpConnectionFactory http = new HttpConnectionFactory(conf);

            boolean useSsl = false;
            String schemeName = useSsl ? "https" : "http";

            connector = new ServerConnector(httpServer, http);
            connector.setPort(portNum);
            // Linux:yes, Windows:no
            connector.setReuseAddress(true);
            int maxIdleTime = 1000 * 60 * 30;
            connector.setIdleTimeout(maxIdleTime);

            httpServer.addConnector(connector);

            // Thrift configs
            TProcessor processor = new NerService.Processor<NerService.Iface>(this);
            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();

            TServlet thriftHttpServlet = new ThriftHttpServlet(processor, protocolFactory);

            // Context handler
            final ServletContextHandler context = new ServletContextHandler(
                    ServletContextHandler.SESSIONS);
            context.setContextPath("/");

            final String httpPath = "/ner";
            httpServer.setHandler(context);
            context.addServlet(new ServletHolder(thriftHttpServlet), httpPath);

            // Finally, start the server
            httpServer.start();
            String msg = "Started " + ThriftHttpNerService.class.getSimpleName() + " in " + schemeName
                    + " mode on port " + portNum + " path=" + httpPath + " with " + minWorkerThreads + "..."
                    + maxWorkerThreads + " worker threads";
            //LOG.info(msg);
            System.out.println(msg);
            //httpServer.join();
        } catch (Throwable t) {
            System.exit(-1);
        }

    }

    /**
     * The config parameter can be like "path", "/path", "/path/", "path/*", "/path1/path2/*" and so on.
     * httpPath should end up as "/*", "/path/*" or "/path1/../pathN/*"
     *
     * @param httpPath
     * @return
     */
    private String getHttpPath(String httpPath) {
        if (httpPath == null || httpPath.equals("")) {
            httpPath = "/*";
        } else {
            if (!httpPath.startsWith("/")) {
                httpPath = "/" + httpPath;
            }
            if (httpPath.endsWith("/")) {
                httpPath = httpPath + "*";
            }
            if (!httpPath.endsWith("/*")) {
                httpPath = httpPath + "/*";
            }
        }
        return httpPath;
    }

}
