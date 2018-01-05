package wuxian.me.ner.thrift;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

final class ThreadPoolExecutorWithOomHook extends ThreadPoolExecutor {
    private final Runnable oomHook;

    public ThreadPoolExecutorWithOomHook(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                         TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                         Runnable oomHook) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.oomHook = oomHook;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Throwable t2) {
                t = t2;
            }
        }
        if (t instanceof OutOfMemoryError) {
            oomHook.run();
        }
    }
}