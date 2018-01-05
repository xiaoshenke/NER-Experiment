package wuxian.me.ner.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;

public class ThriftHttpServlet extends TServlet {
    public ThriftHttpServlet(TProcessor processor
            , TProtocolFactory inProtocolFactory, TProtocolFactory outProtocolFactory) {
        super(processor, inProtocolFactory, outProtocolFactory);
    }

    public ThriftHttpServlet(TProcessor processor
            , TProtocolFactory protocolFactory) {
        super(processor, protocolFactory);
    }


}
