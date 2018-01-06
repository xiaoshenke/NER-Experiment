package wuxian.me.ner.service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wuxian on 6/1/2018.
 */
public class EIdGenerator {

    private static AtomicLong eid = new AtomicLong(0);

    private EIdGenerator() {
    }

    public static Long genId() {
        return eid.getAndIncrement();
    }
}
