package wuxian.me.ner.thrift.util;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ThriftHttpServlet extends TServlet {
    public ThriftHttpServlet(TProcessor processor
            , TProtocolFactory inProtocolFactory, TProtocolFactory outProtocolFactory) {
        super(processor, inProtocolFactory, outProtocolFactory);
    }

    public ThriftHttpServlet(TProcessor processor
            , TProtocolFactory protocolFactory) {
        super(processor, protocolFactory);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ThriftHttpServlet.doPost");
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ThriftHttpServlet.doGet");
        super.doGet(request,response);
    }

}
