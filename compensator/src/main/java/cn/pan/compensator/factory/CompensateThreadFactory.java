package cn.pan.compensator.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CompensateThreadFactory implements ThreadFactory {

    private static final String COMPENSATE = "compensate";

    private final static AtomicInteger FACTORY_NUMBER = new AtomicInteger(0);

    private final AtomicInteger threadNumber = new AtomicInteger(0);

    private final String threadPrefix;

    private final boolean daemon;

    public CompensateThreadFactory() {
        this(COMPENSATE, false);
    }

    public CompensateThreadFactory(String threadName) {
        this(threadName, false);
    }

    public CompensateThreadFactory(String threadName, boolean daemon) {
        if (threadName == null) {
            throw new NullPointerException("threadName");
        }
        this.threadPrefix = prefix(threadName, FACTORY_NUMBER.getAndIncrement());
        this.daemon = daemon;
    }

    public static ThreadFactory createThreadFactory(String threadName) {
        return createThreadFactory(threadName, false);
    }

    public static ThreadFactory createThreadFactory(String threadName, boolean daemon) {
        return new CompensateThreadFactory(threadName, daemon);
    }

    @Override
    public Thread newThread(Runnable r) {
        String newThreadName = createThreadName();
        Thread thread = new Thread(r, newThreadName);
        if (daemon) {
            thread.setDaemon(true);
        }
        return thread;
    }

    private String prefix(String threadName, int factoryId) {
        final StringBuilder buffer = new StringBuilder(32);
        buffer.append(threadName);
        buffer.append('(');
        buffer.append(factoryId);
        buffer.append('-');
        return buffer.toString();
    }

    private String createThreadName() {
        StringBuilder buffer = new StringBuilder(threadPrefix.length() + 8);
        buffer.append(threadPrefix);
        buffer.append(threadNumber.getAndIncrement());
        buffer.append(')');
        return buffer.toString();
    }
}