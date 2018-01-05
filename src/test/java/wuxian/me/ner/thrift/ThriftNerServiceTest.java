package wuxian.me.ner.thrift;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ThriftNerServiceTest {

    ThriftNerService service = null;

    @Before
    public void setup() {

        service = new ThriftNerService() {
            @Override
            public void run() {
                System.out.println("run()");
            }
        };
    }

    @Test
    public void testUpload() throws TException {
        service.upload("title", "/Users/dashu/Desktop/a.txt");
    }

}