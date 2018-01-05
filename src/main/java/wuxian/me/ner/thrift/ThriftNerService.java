package wuxian.me.ner.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServer;
import wuxian.me.ner.thrift.proto.NerService;

import java.net.InetAddress;
import java.net.UnknownHostException;

//Todo: portNum,host等通过配置文件配置
public abstract class ThriftNerService implements NerService.Iface, Runnable {

    protected int portNum;
    protected InetAddress serverIPAddress;
    protected String host;
    protected TServer server;
    protected org.eclipse.jetty.server.Server httpServer;

    private boolean isStarted = false;
    protected boolean isEmbedded = false;

    protected int minWorkerThreads;
    protected int maxWorkerThreads;
    protected long workerKeepAliveTime = 60;  //60s

    public ThriftNerService() {
        currentServerContext = new ThreadLocal<ServerContext>();
        init();
    }

    protected ThreadLocal<ServerContext> currentServerContext;

    public int getPortNumber() {
        return portNum;
    }

    public InetAddress getServerIPAddress() {
        return serverIPAddress;
    }

    public synchronized void start() {
        //super.start();
        if (!isStarted && !isEmbedded) {
            new Thread(this).start();
            isStarted = true;
        }
    }

    public synchronized void stop() {
        if (isStarted && !isEmbedded) {
            if (server != null) {
                server.stop();
                //LOG.info("Thrift server has stopped");
            }
            if ((httpServer != null) && httpServer.isStarted()) {
                try {
                    httpServer.stop();
                    //LOG.info("Http server has stopped");
                } catch (Exception e) {
                    //LOG.error("Error stopping Http server: ", e);
                }
            }
            isStarted = false;
        }
    }

    public static InetAddress getHostAddress(String hostname) throws UnknownHostException {
        InetAddress serverIPAddress;
        if (hostname != null && !hostname.isEmpty()) {
            serverIPAddress = InetAddress.getByName(hostname);
        } else {
            serverIPAddress = InetAddress.getLocalHost();
        }
        return serverIPAddress;
    }

    public void init() {
        this.host = "localhost";
        try {
            serverIPAddress = getHostAddress(host);
        } catch (UnknownHostException e) {
        }
        portNum = 8086;
        minWorkerThreads = 5;
        maxWorkerThreads = 500;
    }

    @Override
    //Todo: thrift接口实现 -> 暂时先做成local运行 不进行rpc了,后序有机会再添加thrift接口
    public boolean upload(String title, String path) throws TException {
        System.out.println("ThriftNerService.upload: title: " + title + " path: " + path);
        return true;
    }
}
