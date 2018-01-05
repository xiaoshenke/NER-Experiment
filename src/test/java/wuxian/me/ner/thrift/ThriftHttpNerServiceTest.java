package wuxian.me.ner.thrift;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import wuxian.me.ner.thrift.proto.NerService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ThriftHttpNerServiceTest {

    ThriftHttpNerService httpNerService;
    private TTransport transport;

    @Before
    public void setup() {

        Runnable oom = new Runnable() {
            @Override
            public void run() {
                if (httpNerService != null) {
                    httpNerService.stop();
                }
            }
        };

        httpNerService = new ThriftHttpNerService(oom);
        httpNerService.run();
    }

    @Test
    public void testUpload() throws Exception {

        TTransport transport = createHttpTransport();
        if (!transport.isOpen()) {
            transport.open();
        }

        NerService.Client client = new NerService.Client(new TBinaryProtocol(transport));
        Assert.assertTrue(client.upload("title", "/Users/dashu/Desktop"));

        while (true) {

        }

    }

    private CloseableHttpClient getHttpClient(Boolean useSsl) {

        HttpClientBuilder httpClientBuilder;
        // Request interceptor for any request pre-processing logic
        HttpRequestInterceptor requestInterceptor;
        Map<String, String> additionalHttpHeaders = new HashMap<String, String>();


        httpClientBuilder = HttpClientBuilder.create();

        // In case the server's idletimeout is set to a lower value, it might close it's side of
        // connection. However we retry one more time on NoHttpResponseException
        httpClientBuilder.setRetryHandler(new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount > 1) {
                    //LOG.info("Retry attempts to connect to server exceeded.");
                    return false;
                }
                if (exception instanceof org.apache.http.NoHttpResponseException) {
                    //LOG.info("Could not connect to the server. Retrying one more time.");
                    return true;
                }
                return false;
            }
        });

        // Add the request interceptor to the client builder
        //httpClientBuilder.addInterceptorFirst(requestInterceptor);

        // Add an interceptor to add in an XSRF header
        //httpClientBuilder.addInterceptorLast(new XsrfHttpRequestInterceptor());
        return httpClientBuilder.build();
    }

    private TTransport createHttpTransport() throws TTransportException {
        CloseableHttpClient httpClient;
        boolean useSsl = false;
        // Create an http client from the configs
        httpClient = getHttpClient(useSsl);
        transport = new THttpClient(getServerHttpUrl(useSsl), httpClient);
        return transport;
    }


    private String getServerHttpUrl(boolean useSsl) {
        // Create the http/https url
        // JDBC driver will set up an https url if ssl is enabled, otherwise http
        String schemeName = useSsl ? "https" : "http";
        // http path should begin with "/"
        String httpPath = "/ner";
        if (httpPath == null) {
            httpPath = "/";
        } else if (!httpPath.startsWith("/")) {
            httpPath = "/" + httpPath;
        }
        return schemeName + "://" + "localhost" + ":" + "8086" + httpPath;
    }

}